package cn.jiuyou2020.serialize.message;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: jiuyou2020
 * @description:
 */
public abstract class RpcResponse {
    public static final String SUCCESS = "success";
    public static final String ERROR = "error";

    private static final Map<Integer, RpcResponse> map = new HashMap<>();

    public static void addRpcResponse(Integer type, RpcResponse request) {
        map.put(type, request);
    }

    public static RpcResponse getRpcResponse(Integer serializationType) {
        return map.get(serializationType);
    }

    public abstract <T> T getResult();

    public abstract String getErrorMessage();
}
