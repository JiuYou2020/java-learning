package cn.learning;

import org.flowable.engine.*;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * {@code @Author: } JiuYou2020
 * <br>
 * {@code @Date: } 2023/9/7
 * <br>
 * {@code @Description: }
 */
public class HolidayRequest {
    public static void main(String[] args) {
        //todo 1.
        //这段代码主要作用就是使用Flowable提供的API完成流程引擎的配置、创建工作,为后续的流程部署与执行等操作做准备。
        //通过构建ProcessEngine对象,我们就可以进一步地使用Flowable的流程引擎来开发业务流程应用程序了。
        ProcessEngineConfiguration cfg = new StandaloneProcessEngineConfiguration().setJdbcUrl("jdbc:h2:mem:flowable;DB_CLOSE_DELAY=-1").setJdbcUsername("sa").setJdbcPassword("").setJdbcDriver("org.h2.Driver").setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);

        ProcessEngine processEngine = cfg.buildProcessEngine();
        //todo 4.
        //先去完成日志文件的设置和流程图的编写,再接下来看下面的内容
        //进程引擎会将xml文件存储到数据库中,以便需要时获取,接下来将流程部署到引擎中,即使用数据库服务
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deployment = repositoryService.createDeployment().addClasspathResource("holiday-request.bpmn20.xml").deploy();
        //todo 5.
        //现在，我们可以通过 API 查询流程定义来验证引擎是否知道流程定义（并了解一些有关 API 的信息）。
        //这是通过 RepositoryService 创建一个新的 ProcessDefinitionQuery 对象来完成的。
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).singleResult();
        System.out.println("流程定义如下 : " + processDefinition.getName());
        //todo 6.
        //现在，我们已将流程定义部署到流程引擎，因此可以使用此流程定义作为“蓝图”启动流程实例
        //要启动流程实例，我们需要提供一些初始流程变量。通常，当流程被自动触发时，将通过呈现给用户的表单或通过REST API获得这些信息。
        //在本例中，我们将保持简单，并使用java. util. Scanner类在命令行上简单地输入一些数据
        Scanner scanner = new Scanner(System.in);

        System.out.println("你的名字是?");
        String employee = scanner.nextLine();

        System.out.println("你需要请假多少天?");
        Integer nrOfHolidays = Integer.valueOf(scanner.nextLine());

        System.out.println("你为什么需要请假?");
        String description = scanner.nextLine();
        //todo 7.
        //接下来，我们可以通过RuntimeService启动一个流程实例。收集的数据作为java. util. Map实例传递，其中key是将用于稍后检索变量的标识符。
        //使用key启动流程实例。此键与BPMN 2. 0 XML文件中设置的id属性匹配，在本例中为holidayRequest。除了使用密钥之外，还有许多启动流程实例的方法
        //当流程实例启动时，会创建一个执行并放入start事件中。从那里开始，这个执行遵循序列流程到用户任务，以获得管理员的批准，并执行用户任务行为。
        //此行为将在数据库中创建一个任务，以后可以通过查询找到该任务。用户任务处于等待状态，引擎将停止执行任何进一步操作，返回API调用。
        RuntimeService runtimeService = processEngine.getRuntimeService();
        Map<String, Object> variables = new HashMap<>();
        variables.put("employee", employee);
        variables.put("nrOfHolidays", nrOfHolidays);
        variables.put("description", description);
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("holidayRequest", variables);
        //todo 10.
        //获取实际的任务列表,通过TaskService创建一个新的TaskQuery对象,并使用taskCandidateGroup方法过滤出候选组为managers的任务
        TaskService taskService = processEngine.getTaskService();
        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("managers").list();
        System.out.println("你有 " + tasks.size() + " 个任务:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ") " + tasks.get(i).getName());
        }
        //使用任务标识符,我们现在可以获取特定任务的详细信息,并将其打印到控制台
        System.out.println("你想要处理哪个任务?");
        int taskIndex = Integer.parseInt(scanner.nextLine());
        Task task = tasks.get(taskIndex - 1);
        Map<String, Object> processVariables = taskService.getVariables(task.getId());
        System.out.println(processVariables.get("employee") + " 想要请假 " + processVariables.get("nrOfHolidays") + " 天,你是否同意?");
        //todo 11.
        //现在，我们可以使用TaskService完成任务。实际上,这通常意味着表单是由用户去提交的,然后,表单中的数据将作为流程传递变量使用
        //在这里,我们将通过在任务完成时传递带有“approved”变量的映射来模拟这一点（注意,名称很重要，因为它稍后会在序列流的条件中使用！
        boolean approved = "y".equalsIgnoreCase(scanner.nextLine());
        variables = new HashMap<>();
        variables.put("approved", approved);
        taskService.complete(task.getId(), variables);
        //任务已经完成,并根据"已批准"流程变量选择离开独占网关的序列流程

        //todo 13.
        //查询历史数据
        HistoryService historyService = processEngine.getHistoryService();
        List<HistoricActivityInstance> activities = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstance.getId()).finished().orderByHistoricActivityInstanceEndTime().asc().list();

        for (HistoricActivityInstance activity : activities) {
            System.out.println(activity.getActivityId() + " 花了 " + activity.getDurationInMillis() + " 毫秒");
        }
    }
}
