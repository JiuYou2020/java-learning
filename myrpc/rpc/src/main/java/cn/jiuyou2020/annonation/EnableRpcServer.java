package cn.jiuyou2020.annonation;

import cn.jiuyou2020.PropertyContext;
import cn.jiuyou2020.nettransmit.MessageReceiver;
import cn.jiuyou2020.AutoConfigurationConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author: jiuyou2020
 * @description: 导入配置，用于启动rpc服务端
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import({PropertyContext.class, AutoConfigurationConfig.class, MessageReceiver.class})
public @interface EnableRpcServer {
}
