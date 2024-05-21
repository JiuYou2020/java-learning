package cn.jiuyou2020.serialize.message;

import cn.jiuyou2020.proxy.FeignClientFactoryBean;

import java.lang.reflect.Method;

/**
 * @author: jiuyou2020
 * @description:
 */
public class RpcRequestJsonFactory extends RpcRequestFactory {
    public RpcRequest createRpcRequest(Method method, Object[] args, FeignClientFactoryBean clientFactoryBean) {
        JsonRpcRequest rpcRequest = new JsonRpcRequest();
        String className = clientFactoryBean.getType().getName();
        String methodName = method.getName();
        rpcRequest.setClassName(className);
        rpcRequest.setMethodName(methodName);
        rpcRequest.setParameterTypes(method.getParameterTypes());
        rpcRequest.setParameters(args);
        rpcRequest.setReturnType(method.getReturnType());
        return rpcRequest;
    }
}
