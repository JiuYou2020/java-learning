package cn.jiuyou2020.proxy;

import org.springframework.beans.factory.FactoryBean;

/**
 * @author jiuyou2020
 * @description 用于注册api接口的实例bean
 * @date 2024/4/25 上午1:32
 */
public class RemoteServiceFactoryBean implements FactoryBean<Object>{
    @Override
    public Object getObject() throws Exception {
        return null;
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }
}
