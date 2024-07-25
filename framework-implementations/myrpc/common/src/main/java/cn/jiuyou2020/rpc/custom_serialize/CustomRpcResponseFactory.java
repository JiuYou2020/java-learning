package cn.jiuyou2020.rpc.custom_serialize;

import cn.jiuyou2020.serialize.message.RpcResponse;
import cn.jiuyou2020.serialize.message.RpcResponseFactory;

/**
 * @author: jiuyou2020
 * @description:
 */
public class CustomRpcResponseFactory extends RpcResponseFactory {
    @Override
    public RpcResponse createRpcResponse(Object o) {
        CustomRpcResponse customRpcResponse = new CustomRpcResponse();
        customRpcResponse.setResult(o);
        customRpcResponse.setErrorMessage(RpcResponse.SUCCESS);
        return customRpcResponse;
    }
}
