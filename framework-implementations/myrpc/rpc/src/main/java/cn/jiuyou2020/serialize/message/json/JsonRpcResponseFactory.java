package cn.jiuyou2020.serialize.message.json;

import cn.jiuyou2020.serialize.message.RpcResponse;
import cn.jiuyou2020.serialize.message.RpcResponseFactory;

/**
 * @author: jiuyou2020
 * @description: json rpc响应工厂,用于创建{@link JsonRpcResponse}
 */
public class JsonRpcResponseFactory extends RpcResponseFactory {

    @Override
    public RpcResponse createRpcResponse(Object o) {
        JsonRpcResponse jsonRpcResponse = new JsonRpcResponse();
        jsonRpcResponse.setResult(o);
        jsonRpcResponse.setErrorMessage(RpcResponse.SUCCESS);
        return jsonRpcResponse;
    }
}
