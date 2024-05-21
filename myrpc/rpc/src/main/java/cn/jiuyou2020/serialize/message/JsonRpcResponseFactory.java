package cn.jiuyou2020.serialize.message;

/**
 * @author: jiuyou2020
 * @description:
 */
public class JsonRpcResponseFactory extends RpcResponseFactory {

    @Override
    public RpcResponse createRpcResponse(Object o) throws Exception {
        JsonRpcResponse jsonRpcResponse = new JsonRpcResponse();
        jsonRpcResponse.setResult(o);
        jsonRpcResponse.setErrorMessage(RpcResponse.SUCCESS);
        return jsonRpcResponse;
    }
}
