package cn.jiuyou2020;

import cn.jiuyou2020.serialize.SerializationFacade;
import cn.jiuyou2020.serialize.SerializationType;
import cn.jiuyou2020.serialize.message.RpcRequest;
import cn.jiuyou2020.serialize.message.RpcRequestFactory;
import cn.jiuyou2020.serialize.message.RpcResponse;
import cn.jiuyou2020.serialize.message.RpcResponseFactory;
import cn.jiuyou2020.serialize.message.json.JsonRpcRequest;
import cn.jiuyou2020.serialize.message.json.JsonRpcRequestFactory;
import cn.jiuyou2020.serialize.message.json.JsonRpcResponse;
import cn.jiuyou2020.serialize.message.json.JsonRpcResponseFactory;
import cn.jiuyou2020.serialize.message.protobuf.*;
import cn.jiuyou2020.serialize.strategy.JsonSerializationStrategy;
import cn.jiuyou2020.serialize.strategy.ProtobufSerializationStrategy;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: jiuyou2020
 * @description: 自动配置类，用于注册序列化策略，如果需要实现新的序列化方式，需要手动添加，例如
 * <pre>
 * SerializationFacade.addStrategy(SerializationType.PROTOBUF.getValue(), new ProtobufSerializationStrategy());
 * RpcRequestFactory.addFactory(SerializationType.PROTOBUF.getValue(), new ProtobufRpcRequestFactory());
 * RpcRequest.addRpcRequest(SerializationType.PROTOBUF.getValue(), new ProtobufRpcRequest(RpcRequestOuterClass.RpcRequestProto.newBuilder().build()));
 * RpcResponseFactory.addFactory(SerializationType.PROTOBUF.getValue(), new ProtobufRpcResponseFactory());
 * RpcResponse.addRpcResponse(SerializationType.PROTOBUF.getValue(), new ProtobufRpcResponse(RpcResponseOuterClass.RpcResponseProto.newBuilder().build()));
 * </pre>
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties({RpcProperties.class})
public class AutoConfigurationConfig {

    @Bean
    @ConditionalOnProperty(name = "rpc.type", havingValue = "protobuf")
    public void registerProtobufStrategy() {
        SerializationFacade.addStrategy(SerializationType.PROTOBUF.getValue(), new ProtobufSerializationStrategy());
        RpcRequestFactory.addFactory(SerializationType.PROTOBUF.getValue(), new ProtobufRpcRequestFactory());
        RpcRequest.addRpcRequest(SerializationType.PROTOBUF.getValue(), new ProtobufRpcRequest(RpcRequestOuterClass.RpcRequestProto.newBuilder().build()));
        RpcResponseFactory.addFactory(SerializationType.PROTOBUF.getValue(), new ProtobufRpcResponseFactory());
        RpcResponse.addRpcResponse(SerializationType.PROTOBUF.getValue(), new ProtobufRpcResponse(RpcResponseOuterClass.RpcResponseProto.newBuilder().build()));
    }

    @Bean
    public void registerJsonStrategy() {
        SerializationFacade.addStrategy(SerializationType.JSON.getValue(), new JsonSerializationStrategy());
    }

    @Bean
    @ConditionalOnProperty(name = "rpc.type", havingValue = "json", matchIfMissing = true)
    public void registerJsonRequest() {
        RpcRequestFactory.addFactory(SerializationType.JSON.getValue(), new JsonRpcRequestFactory());
        RpcRequest.addRpcRequest(SerializationType.JSON.getValue(), new JsonRpcRequest());
        RpcResponseFactory.addFactory(SerializationType.JSON.getValue(), new JsonRpcResponseFactory());
        RpcResponse.addRpcResponse(SerializationType.JSON.getValue(), new JsonRpcResponse());
    }
}
