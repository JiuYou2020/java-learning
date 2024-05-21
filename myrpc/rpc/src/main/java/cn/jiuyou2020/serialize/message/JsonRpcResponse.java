package cn.jiuyou2020.serialize.message;

/**
 * @author: jiuyou2020
 * @description:
 */
public class JsonRpcResponse extends RpcResponse {
    private Object result;
    private String errorMessage;

    @Override
    public Object getResult() {
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
