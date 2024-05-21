package cn.jiuyou2020.annonation;

import cn.jiuyou2020.PropertyContext;
import cn.jiuyou2020.proxy.ProxyRegistrar;
import cn.jiuyou2020.serialize.AutoConfigurationConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author: jiuyou2020
 * @description: 导入配置
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import({ProxyRegistrar.class, PropertyContext.class, AutoConfigurationConfig.class})
public @interface EnableMyRpc {

}
