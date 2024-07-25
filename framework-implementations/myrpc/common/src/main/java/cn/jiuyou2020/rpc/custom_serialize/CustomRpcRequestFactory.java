package cn.jiuyou2020.rpc.custom_serialize;

import cn.jiuyou2020.proxy.FeignClientFactoryBean;
import cn.jiuyou2020.serialize.message.RpcRequest;
import cn.jiuyou2020.serialize.message.RpcRequestFactory;

import java.lang.reflect.Method;

/**
 * @author: jiuyou2020
 * @description:
 */
public class CustomRpcRequestFactory extends RpcRequestFactory {
    public RpcRequest createRpcRequest(Method method, Object[] args, FeignClientFactoryBean clientFactoryBean) {
        CustomRpcRequest rpcRequest = new CustomRpcRequest();
        String className = clientFactoryBean.getType().getName();
        String methodName = method.getName();
        rpcRequest.setClassName(className);
        rpcRequest.setMethodName(methodName);
        rpcRequest.setParameterTypes(method.getParameterTypes());
        rpcRequest.setParameters(args);
        return rpcRequest;
    }
}
