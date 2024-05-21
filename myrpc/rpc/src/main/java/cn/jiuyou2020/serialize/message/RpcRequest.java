package cn.jiuyou2020.serialize.message;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: jiuyou2020
 * @description:
 */
public abstract class RpcRequest {
    private static final Map<Integer, RpcRequest> map = new HashMap<>();

    public static void addRpcRequest(Integer type, RpcRequest request) {
        map.put(type, request);
    }

    public static RpcRequest getRpcRequest(Integer serializationType) {
        return map.get(serializationType);
    }

    public abstract String getClassName();

    public abstract String getMethodName();

    public abstract <T> T getParameters() throws Exception;

    public abstract <T> T getParameterTypes() throws ClassNotFoundException;

}
