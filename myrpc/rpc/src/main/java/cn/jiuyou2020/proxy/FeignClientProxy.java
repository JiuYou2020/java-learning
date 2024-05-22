package cn.jiuyou2020.proxy;

import cn.jiuyou2020.DataTransmitterWrapper;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author: jiuyou2020
 * @description: 代理对象，用于调用远程方法
 */
@SuppressWarnings("unchecked")
public class FeignClientProxy implements InvocationHandler {
    private final FeignClientFactoryBean clientFactoryBean;
    private final DataTransmitterWrapper dataTransmitterWrapper;

    public FeignClientProxy(FeignClientFactoryBean clientFactoryBean) {
        this.clientFactoryBean = clientFactoryBean;
        dataTransmitterWrapper = new DataTransmitterWrapper();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Exception {
        return dataTransmitterWrapper.executeDataTransmit(method, args, clientFactoryBean);
    }

    public static <T> T createProxy(Class<T> interfaceClass, FeignClientFactoryBean feignClientFactoryBean) {
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(),
                new Class[]{interfaceClass}, new FeignClientProxy(feignClientFactoryBean));
    }
}
