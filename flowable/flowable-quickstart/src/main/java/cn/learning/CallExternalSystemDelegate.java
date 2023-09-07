package cn.learning;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

/**
 * {@code @Author: } JiuYou2020
 * <br>
 * {@code @Date: } 2023/9/7
 * <br>
 * {@code @Description: } todo 12. 在之前,我们还没有实现请求获得批准时将执行的自动逻辑,在BPMN 2.0 XML中,我们使用了一个服务任务来实现这一点,而服务任务指向的就是这个类
 */
public class CallExternalSystemDelegate implements JavaDelegate {
    public void execute(DelegateExecution execution) {
        System.out.println("为 " + execution.getVariable("employee") + " 申请了 " + execution.getVariable("nrOfHolidays") + " 天假期。");
    }
}