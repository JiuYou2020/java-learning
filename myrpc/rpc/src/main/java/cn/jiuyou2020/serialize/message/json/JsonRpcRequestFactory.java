package cn.jiuyou2020.serialize.message.json;

import cn.jiuyou2020.proxy.FeignClientFactoryBean;
import cn.jiuyou2020.serialize.message.RpcRequest;
import cn.jiuyou2020.serialize.message.RpcRequestFactory;

import java.lang.reflect.Method;

/**
 * @author: jiuyou2020
 * @description: json rpc请求工厂,用于创建{@link JsonRpcRequest}
 */
public class JsonRpcRequestFactory extends RpcRequestFactory {
    public RpcRequest createRpcRequest(Method method, Object[] args, FeignClientFactoryBean clientFactoryBean) {
        JsonRpcRequest rpcRequest = new JsonRpcRequest();
        String className = clientFactoryBean.getType().getName();
        String methodName = method.getName();
        rpcRequest.setClassName(className);
        rpcRequest.setMethodName(methodName);
        rpcRequest.setParameterTypes(method.getParameterTypes());
        rpcRequest.setParameters(args);
        return rpcRequest;
    }
}
