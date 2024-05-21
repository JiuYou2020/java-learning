package cn.jiuyou2020.serialize.message;

import cn.jiuyou2020.proxy.FeignClientFactoryBean;
import cn.jiuyou2020.serialize.SerializationType;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public abstract class RpcRequestFactory {
    private static final Map<SerializationType, RpcRequestFactory> map = new HashMap<>();

    public static void addFactory(SerializationType type, RpcRequestFactory requestFactory) {
        map.put(type, requestFactory);
    }

    public static RpcRequestFactory getFactory(SerializationType serializationType) {
        return map.get(serializationType);
    }

    public abstract RpcRequest createRpcRequest(Method method, Object[] args, FeignClientFactoryBean clientFactoryBean) throws Exception;
}