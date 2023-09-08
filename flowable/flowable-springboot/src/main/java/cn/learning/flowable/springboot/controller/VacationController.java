package cn.learning.flowable.springboot.controller;

import cn.learning.flowable.springboot.pojo.ResponseBean;
import cn.learning.flowable.springboot.pojo.VacationApproveVo;
import cn.learning.flowable.springboot.pojo.VacationRequestVo;
import cn.learning.flowable.springboot.service.VacationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * {@code @Author: } JiuYou2020
 * <br>
 * {@code @Date: } 2023/9/8
 * <br>
 * {@code @Description: } 请假流程测试
 */
@RequestMapping("vacation")
@RestController
public class VacationController {
    @Autowired
    VacationService vacationService;

    /**
     * 请假条新增页面
     *
     * @return ModelAndView
     */
    @GetMapping("/add")
    public ModelAndView add() {
        return new ModelAndView("vacation");
    }

    /**
     * 请假条审批列表
     *
     * @return ModelAndView
     */
    @GetMapping("/aList")
    public ModelAndView aList() {
        return new ModelAndView("list");
    }

    /**
     * 请假条查询列表
     *
     * @return ModelAndView
     */
    @GetMapping("/sList")
    public ModelAndView sList() {
        return new ModelAndView("search");
    }

    /**
     * 请假请求方法
     *
     * @param vacationRequestVO 请假请求参数
     * @return ModelAndView
     */
    @PostMapping
    public ResponseBean askForLeave(@RequestBody VacationRequestVo vacationRequestVO) {
        return vacationService.askForLeave(vacationRequestVO);
    }

    /**
     * 获取待审批列表
     *
     * @param identity 用户名
     * @return ResponseBean
     */
    @GetMapping("/list")
    public ResponseBean leaveList(String identity) {
        return vacationService.leaveList(identity);
    }

    /**
     * 拒绝或同意请假
     *
     * @param vacationVO 请假请求参数
     * @return ResponseBean
     */
    @PostMapping("/handler")
    public ResponseBean askForLeaveHandler(@RequestBody VacationApproveVo vacationVO) {
        return vacationService.askForLeaveHandler(vacationVO);
    }

    /**
     * 请假查询
     *
     * @param name 请假人
     * @return ResponseBean
     */
    @GetMapping("/search")
    public ResponseBean searchResult(String name) {
        return vacationService.searchResult(name);
    }
}