package cn.learning.flowable.springboot.service;

import cn.learning.flowable.springboot.pojo.ResponseBean;
import cn.learning.flowable.springboot.pojo.VacationApproveVo;
import cn.learning.flowable.springboot.pojo.VacationInfo;
import cn.learning.flowable.springboot.pojo.VacationRequestVo;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.task.api.Task;
import org.flowable.variable.api.history.HistoricVariableInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * {@code @Author: } JiuYou2020
 * <br>
 * {@code @Date: } 2023/9/8
 * <br>
 * {@code @Description: } 请假流程测试
 */
@Service
public class VacationService {
    @Autowired
    RuntimeService runtimeService;

    @Autowired
    TaskService taskService;

    @Autowired
    HistoryService historyService;

    @Autowired
    private RepositoryService repositoryService;

    @PostConstruct
    public void init() {
        //部署流程
        repositoryService.createDeployment().addClasspathResource("processess/vacationRequest.bpmn20.xml").deploy();
    }

    /**
     * 申请请假
     *
     * @param vacationRequestVO 请假请求参数
     * @return ResponseBean
     */
    @Transactional
    public ResponseBean askForLeave(VacationRequestVo vacationRequestVO) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("name", vacationRequestVO.getName());
        variables.put("days", vacationRequestVO.getDays());
        variables.put("reason", vacationRequestVO.getReason());
        try {
            //指定业务流程
            runtimeService.startProcessInstanceByKey("vacationRequest", vacationRequestVO.getName(), variables);
            return ResponseBean.ok("已提交请假申请");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseBean.error("提交申请失败");
    }

    /**
     * 审批列表
     *
     * @param identity 审批人
     * @return ResponseBean
     */
    public ResponseBean leaveList(String identity) {
        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup(identity).list();
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            Map<String, Object> variables = taskService.getVariables(task.getId());
            variables.put("id", task.getId());
            list.add(variables);
        }
        return ResponseBean.ok("加载成功", list);
    }

    /**
     * 操作审批
     *
     * @param vacationVO 审批参数
     * @return ResponseBean
     */
    public ResponseBean askForLeaveHandler(VacationApproveVo vacationVO) {
        try {
            boolean approved = vacationVO.getApprove();
            Map<String, Object> variables = new HashMap<String, Object>();
            variables.put("approved", approved);
            variables.put("employee", vacationVO.getName());
            Task task = taskService.createTaskQuery().taskId(vacationVO.getTaskId()).singleResult();
            taskService.complete(task.getId(), variables);
            if (approved) {
                //如果是同意，还需要继续走一步
                Task t = taskService.createTaskQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
                taskService.complete(t.getId());
            }
            return ResponseBean.ok("操作成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseBean.error("操作失败");
    }

    /**
     * 请假列表
     *
     * @param name 请假人
     * @return ResponseBean
     */
    public ResponseBean searchResult(String name) {
        List<VacationInfo> vacationInfos = new ArrayList<>();
        List<HistoricProcessInstance> historicProcessInstances = historyService
                .createHistoricProcessInstanceQuery()
                .processInstanceBusinessKey(name)
                .finished()
                .orderByProcessInstanceEndTime()
                .desc()
                .list();
        for (HistoricProcessInstance historicProcessInstance : historicProcessInstances) {
            VacationInfo vacationInfo = new VacationInfo();
            Date startTime = historicProcessInstance.getStartTime();
            Date endTime = historicProcessInstance.getEndTime();
            List<HistoricVariableInstance> historicVariableInstances = historyService.createHistoricVariableInstanceQuery()
                    .processInstanceId(historicProcessInstance.getId())
                    .list();
            for (HistoricVariableInstance historicVariableInstance : historicVariableInstances) {
                String variableName = historicVariableInstance.getVariableName();
                Object value = historicVariableInstance.getValue();
                if ("reason".equals(variableName)) {
                    vacationInfo.setReason((String) value);
                } else if ("days".equals(variableName)) {
                    vacationInfo.setDays(Integer.parseInt(value.toString()));
                } else if ("approved".equals(variableName)) {
                    vacationInfo.setStatus((Boolean) value);
                } else if ("name".equals(variableName)) {
                    vacationInfo.setName((String) value);
                }
            }
            vacationInfo.setStartTime(startTime);
            vacationInfo.setEndTime(endTime);
            vacationInfos.add(vacationInfo);
        }
        return ResponseBean.ok("ok", vacationInfos);
    }
}