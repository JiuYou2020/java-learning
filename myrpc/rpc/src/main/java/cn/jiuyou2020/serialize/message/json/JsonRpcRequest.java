package cn.jiuyou2020.serialize.message.json;

import cn.jiuyou2020.serialize.message.RpcRequest;

/**
 * @author: jiuyou2020
 * @description: json rpc请求
 */
public class JsonRpcRequest extends RpcRequest {
    String className;
    String methodName;
    Class<?>[] parameterTypes;
    Object[] parameters;

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public String getMethodName() {
        return methodName;
    }

    @Override
    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    @Override
    public Object[] getParameters() {
        return parameters;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }
}
