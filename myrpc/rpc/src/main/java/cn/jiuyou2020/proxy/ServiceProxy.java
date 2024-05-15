package cn.jiuyou2020.proxy;

import cn.jiuyou2020.annonation.RemoteService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author jiuyou2020
 * @description 远程服务代理，用于创建远程服务代理对象
 */
public class ServiceProxy {
    @SuppressWarnings("unchecked")
    public static <T> T createProxy(Class<T> serviceInterface) {
        RemoteService annotation = serviceInterface.getAnnotation(RemoteService.class);
        if (annotation == null) {
            throw new IllegalArgumentException("Service interface must be annotated with @RemoteService");
        }

        String name = annotation.value();
        String url = annotation.url();

        InvocationHandler handler = new RemoteInvocationHandler(name, url);
        return (T) Proxy.newProxyInstance(
                serviceInterface.getClassLoader(),
                new Class<?>[]{serviceInterface},
                handler
        );
    }

    private static class RemoteInvocationHandler implements InvocationHandler {
        private final String name;
        private final String url;

        public RemoteInvocationHandler(String name, String url) {
            this.name = name;
            this.url = url;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            // Simulate remote method invocation
            // Replace this with your actual remote service invocation logic
            System.out.println("Calling remote service: " + name + "." + method.getName() + ",\nurl: " + url);
            // For demonstration purposes, returning a hardcoded result
            return "Result from remote service";
        }
    }
}
