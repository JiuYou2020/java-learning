package cn.jiuyou2020.springlearning.aop.aspectj;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class LoggingAspect {

    @Before("execution(* cn.jiuyou2020.springlearning.aop.aspectj.DemoService.performTask(..))")
    public void logBefore() {
        System.out.println("Log before method execution");
    }
}
