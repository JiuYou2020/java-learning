package cn.southtang.ImportBeanDefinitionRegistrar;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(ExtScannerRegistrar.class)
public @interface ExtScan {
    //默认名称
    String defaultName();
}