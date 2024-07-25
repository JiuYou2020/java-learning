package cn.jiuyou2020.serialize.message.json;

import cn.jiuyou2020.serialize.message.RpcResponse;

/**
 * @author: jiuyou2020
 * @description: json rpc响应
 */
public class JsonRpcResponse extends RpcResponse {
    private Object result;
    private String errorMessage;

    public JsonRpcResponse() {
    }

    @Override
    public Object getResult(Class<?> returnType) {
        return result;
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
