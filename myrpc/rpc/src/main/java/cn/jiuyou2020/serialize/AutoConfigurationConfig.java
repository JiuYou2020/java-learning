package cn.jiuyou2020.serialize;

import cn.jiuyou2020.serialize.message.*;
import cn.jiuyou2020.serialize.strategy.JsonSerializationStrategy;
import cn.jiuyou2020.serialize.strategy.ProtobufSerializationStrategy;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

public class AutoConfigurationConfig {

    @PostConstruct
    @ConditionalOnProperty(name = "rpc.serialization.type", havingValue = "protobuf", matchIfMissing = true)
    public void registerProtobufStrategy() {
        SerializationFacade.addStrategy(SerializationType.PROTOBUF, new ProtobufSerializationStrategy());
        RpcRequestFactory.addFactory(SerializationType.PROTOBUF, new RpcRequestProtoFactory());
        RpcRequest.addRpcRequest(SerializationType.PROTOBUF, new ProtobufRpcRequest(RpcRequestOuterClass.RpcRequestProto.newBuilder().build()));
    }

    @PostConstruct
    public void registerJsonStrategy() {
        SerializationFacade.addStrategy(SerializationType.JSON, new JsonSerializationStrategy());
        RpcRequestFactory.addFactory(SerializationType.JSON, new RpcRequestJsonFactory());
        RpcRequest.addRpcRequest(SerializationType.JSON, new JsonRpcRequest());
    }
}
