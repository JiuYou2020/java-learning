package cn.jiuyou2020.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@SuppressWarnings("unchecked")
public class FeignClientProxy implements InvocationHandler {
    private FeignClientFactoryBean feignClientFactoryBean;

    public FeignClientProxy(FeignClientFactoryBean feignClientFactoryBean) {
        this.feignClientFactoryBean = feignClientFactoryBean;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        //获取url,模拟远程调用
        String url = feignClientFactoryBean.getUrl() + "/";
        return url + method.getName();
    }

    public static <T> T createProxy(Class<T> interfaceClass, FeignClientFactoryBean feignClientFactoryBean) {
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(),
                new Class[]{interfaceClass}, new FeignClientProxy(feignClientFactoryBean));
    }
}
