package cn.jiuyou2020.proxy;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class FeignClientProxy implements InvocationHandler {
    private FeignClientFactoryBean feignClientFactoryBean;

    public FeignClientProxy(FeignClientFactoryBean feignClientFactoryBean) {
        this.feignClientFactoryBean = feignClientFactoryBean;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        //获取url

        // 在这里根据方法名称和参数实现具体的业务逻辑
        if (method.getName().equals("getUser")) {
            // 执行获取用户的业务逻辑
            return getUser(args);
        }
        // 其他方法的处理逻辑
        return null;
    }

    private Object getUser(Object[] args) {
        // 实现获取用户的逻辑
        // 这里可以调用你的业务服务或者模拟返回用户数据
        return "User information";
    }

    public static <T> T createProxy(Class<T> interfaceClass, FeignClientFactoryBean feignClientFactoryBean) {
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(),
                new Class[]{interfaceClass}, new FeignClientProxy(feignClientFactoryBean));
    }
}
