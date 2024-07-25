package cn.jiuyou2020;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

// 服务接口
interface Service {
    String sayHello(String name);
}

// 服务实现类
class ServiceImpl implements Service {
    @Override
    public String sayHello(String name) {
        return "Hello, " + name;
    }
}

// 动态代理处理器
class RpcInvocationHandler implements InvocationHandler {
    private final Object target;

    public RpcInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 在这里加入远程调用逻辑
        System.out.println("Calling remote service...");

        // 模拟远程调用，实际调用本地的服务实现
        Object result = method.invoke(target, args);

        System.out.println("Remote service called successfully.");
        return result;
    }
}

// 客户端代码
class RpcClient {
    public static void main(String[] args) {
        // 创建服务实现
        Service service = new ServiceImpl();

        // 创建动态代理
        Service proxy = (Service) Proxy.newProxyInstance(service.getClass().getClassLoader(), service.getClass().getInterfaces(), new RpcInvocationHandler(service));

        // 通过代理调用服务
        String response = proxy.sayHello("Alice");
        System.out.println("Response: " + response);
    }
}
