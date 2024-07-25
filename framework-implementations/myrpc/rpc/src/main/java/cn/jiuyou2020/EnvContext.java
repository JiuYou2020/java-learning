package cn.jiuyou2020;

import cn.jiuyou2020.serialize.SerializationType;
import jakarta.annotation.Nonnull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

/**
 * @author: jiuyou2020
 * @description: 用于获取环境变量
 */
@Configuration
public class EnvContext implements BeanFactoryAware, ApplicationContextAware {
    private static BeanFactory beanFactory = null;
    private static RpcProperties rpcProperties = null;
    private static ApplicationContext applicationContext = null;

    /**
     * 获取序列化类型
     *
     * @return 序列化类型, 如果没有设置，默认为protobuf
     */
    public static SerializationType getSerializationType() {
        if (rpcProperties == null) {
            rpcProperties = beanFactory.getBean(RpcProperties.class);
        }
        return SerializationType.getSerializationType(rpcProperties.getType());
    }

    public static int getPort() {
        if (rpcProperties == null) {
            rpcProperties = beanFactory.getBean(RpcProperties.class);
        }
        return rpcProperties.getPort();
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public void setApplicationContext(@Nonnull ApplicationContext applicationContext) throws BeansException {
        EnvContext.applicationContext = applicationContext;
    }

    @Override
    public void setBeanFactory(@Nonnull BeanFactory beanFactory) throws BeansException {
        EnvContext.beanFactory = beanFactory;
    }
}
