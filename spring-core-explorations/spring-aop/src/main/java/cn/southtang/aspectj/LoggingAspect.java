package cn.southtang.aspectj;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class LoggingAspect {

    @Before("execution(* cn.southtang.aspectj.DemoService.performTask(..))")
    public void logBefore() {
        System.out.println("Log before method execution");
    }
}
