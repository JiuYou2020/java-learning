package cn.jiuyou2020.springlearning.context.custom_registered_spring_beans.ImportBeanDefinitionRegistrar;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(ExtScannerRegistrar.class)
public @interface ExtScan {
    String defaultName(); //默认名称
}