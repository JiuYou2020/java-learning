package cn.jiuyou2020.springlearning.core_context.assemble.c_conditional;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author: jiuyou2020
 * @description:
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import({Boss.class, BarImportSelector.class})
public @interface EnableTavern {
}
