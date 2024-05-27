package cn.jiuyou2020.rpc.custom_serialize;

import cn.jiuyou2020.serialize.message.RpcResponse;

/**
 * @author: jiuyou2020
 * @description:
 */
public class CustomRpcResponse extends RpcResponse {
    private Object result;
    private String errorMessage;

    public CustomRpcResponse() {
    }

    @Override
    public Object getResult(Class<?> returnType) {
        return result;
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
