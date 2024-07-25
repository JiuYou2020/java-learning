package cn.jiuyou2020.rpc.custom_serialize;

import cn.jiuyou2020.serialize.SerializationFacade;
import cn.jiuyou2020.serialize.SerializationType;
import cn.jiuyou2020.serialize.message.RpcRequest;
import cn.jiuyou2020.serialize.message.RpcRequestFactory;
import cn.jiuyou2020.serialize.message.RpcResponse;
import cn.jiuyou2020.serialize.message.RpcResponseFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author: jiuyou2020
 * @description:
 */
@Component
public class CustomRpcConfig {
    @Bean
    public void registerCustomRpcRequest() {
        // 注册自定义的rpc请求
        SerializationFacade.addStrategy(SerializationType.CUSTOM.getValue(), new CustomSerializationStrategy());
        RpcRequestFactory.addFactory(SerializationType.CUSTOM.getValue(), new CustomRpcRequestFactory());
        RpcRequest.addRpcRequest(SerializationType.CUSTOM.getValue(), new CustomRpcRequest());
        RpcResponseFactory.addFactory(SerializationType.CUSTOM.getValue(), new CustomRpcResponseFactory());
        RpcResponse.addRpcResponse(SerializationType.CUSTOM.getValue(), new CustomRpcResponse());
    }
}
