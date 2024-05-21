package cn.jiuyou2020.serialize.message;

import cn.jiuyou2020.proxy.FeignClientFactoryBean;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

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