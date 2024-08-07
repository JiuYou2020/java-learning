package cn.southtang.b_profile;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author: jiuyou2020
 * @description:
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import({BartenderConfiguration.class})
public @interface EnableTavern {
}
