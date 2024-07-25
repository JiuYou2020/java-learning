package cn.jiuyou2020.serialize.message;

import cn.jiuyou2020.proxy.FeignClientFactoryBean;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: jiuyou2020
 * @description: rpc请求工厂的抽象类，如果要实现新的序列化方式，需要继承此类，用于创建{@link RpcRequest}
 */
public abstract class RpcRequestFactory {
    private static final Map<Integer, RpcRequestFactory> map = new HashMap<>();

    public static void addFactory(Integer type, RpcRequestFactory requestFactory) {
        map.put(type, requestFactory);
    }

    public static RpcRequestFactory getFactory(Integer serializationType) {
        return map.get(serializationType);
    }

    public abstract RpcRequest createRpcRequest(Method method, Object[] args, FeignClientFactoryBean clientFactoryBean) throws Exception;
}