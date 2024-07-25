package cn.southtang.jdk;

import java.lang.reflect.Proxy;

public class JDKProxyDemo {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        UserServiceInvocationHandler handler = new UserServiceInvocationHandler(userService);

        UserService proxyInstance = (UserService) Proxy.newProxyInstance(
                userService.getClass().getClassLoader(),
                userService.getClass().getInterfaces(),
                handler
        );

        proxyInstance.addUser();
    }
}
