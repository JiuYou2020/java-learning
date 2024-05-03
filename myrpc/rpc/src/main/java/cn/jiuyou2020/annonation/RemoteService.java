package cn.jiuyou2020.annonation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * @author jiuyou2020
 * @description 远程服务注解
 * @date 2024/4/24 下午8:19
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RemoteService {
    String value() default "";

    String url() default "";
}
