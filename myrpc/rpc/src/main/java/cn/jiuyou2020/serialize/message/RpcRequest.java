package cn.jiuyou2020.serialize.message;

import cn.jiuyou2020.serialize.SerializationType;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: jiuyou2020
 * @description:
 */
public abstract class RpcRequest {
    private static final Map<SerializationType, RpcRequest> map = new HashMap<>();

    public static void addRpcRequest(SerializationType type, RpcRequest request) {
        map.put(type, request);
    }

    public static RpcRequest getRpcRequest(SerializationType serializationType) {
        return map.get(serializationType);
    }

    public abstract String getClassName();

    public abstract String getMethodName();

    public abstract <T> T getParameters();

    public abstract <T> T getParameterTypes();

    public abstract <T> T getReturnType();
}
