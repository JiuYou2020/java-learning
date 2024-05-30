package cn.jiuyou2020.springlearning.core_context.assemble.a_module;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author: jiuyou2020
 * @description:
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import({Boss.class, BartenderConfiguration.class, BarImportSelector.class, WaiterRegistrar.class, WaiterDeferredImportSelector.class})
public @interface EnableTavern {
}
