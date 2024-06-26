package cn.jiuyou2020.serialize.message;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: jiuyou2020
 * @description: rpc响应工厂的抽象类，如果要实现新的序列化方式，需要继承此类，用于创建{@link RpcResponse}
 */
public abstract class RpcResponseFactory {
    private static final Map<Integer, RpcResponseFactory> map = new HashMap<>();

    public static void addFactory(Integer type, RpcResponseFactory requestFactory) {
        map.put(type, requestFactory);
    }

    public static RpcResponseFactory getFactory(Integer serializationType) {
        return map.get(serializationType);
    }

    public abstract RpcResponse createRpcResponse(Object o) throws Exception;
}
