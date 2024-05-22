package cn.jiuyou2020;

import cn.jiuyou2020.serialize.SerializationFacade;
import cn.jiuyou2020.serialize.SerializationType;
import cn.jiuyou2020.serialize.message.*;
import cn.jiuyou2020.serialize.message.json.JsonRpcRequest;
import cn.jiuyou2020.serialize.message.json.JsonRpcRequestFactory;
import cn.jiuyou2020.serialize.message.json.JsonRpcResponse;
import cn.jiuyou2020.serialize.message.json.JsonRpcResponseFactory;
import cn.jiuyou2020.serialize.message.protobuf.*;
import cn.jiuyou2020.serialize.strategy.JsonSerializationStrategy;
import cn.jiuyou2020.serialize.strategy.ProtobufSerializationStrategy;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

public class AutoConfigurationConfig {

    @PostConstruct
    @ConditionalOnProperty(name = "rpc.serialization.type", havingValue = "protobuf", matchIfMissing = true)
    public void registerProtobufStrategy() {
        SerializationFacade.addStrategy(SerializationType.PROTOBUF.getValue(), new ProtobufSerializationStrategy());
        RpcRequestFactory.addFactory(SerializationType.PROTOBUF.getValue(), new ProtobufRpcRequestFactory());
        RpcRequest.addRpcRequest(SerializationType.PROTOBUF.getValue(), new ProtobufRpcRequest(RpcRequestOuterClass.RpcRequestProto.newBuilder().build()));
        RpcResponseFactory.addFactory(SerializationType.PROTOBUF.getValue(), new ProtobufRpcResponseFactory());
        RpcResponse.addRpcResponse(SerializationType.PROTOBUF.getValue(), new ProtobufRpcResponse(RpcResponseOuterClass.RpcResponseProto.newBuilder().build()));
    }

    @PostConstruct
    public void registerJsonStrategy() {
        SerializationFacade.addStrategy(SerializationType.JSON.getValue(), new JsonSerializationStrategy());
    }

    @PostConstruct
    @ConditionalOnProperty(name = "rpc.serialization.type", havingValue = "json")
    public void registerJsonRequest() {
        RpcRequestFactory.addFactory(SerializationType.JSON.getValue(), new JsonRpcRequestFactory());
        RpcRequest.addRpcRequest(SerializationType.JSON.getValue(), new JsonRpcRequest());
        RpcResponseFactory.addFactory(SerializationType.JSON.getValue(), new JsonRpcResponseFactory());
        RpcResponse.addRpcResponse(SerializationType.JSON.getValue(), new JsonRpcResponse());
    }
}
