package cn.jiuyou2020.serialize.message.protobuf;

import cn.jiuyou2020.serialize.SerializationFacade;
import cn.jiuyou2020.serialize.SerializationType;
import cn.jiuyou2020.serialize.message.RpcResponse;
import cn.jiuyou2020.serialize.message.RpcResponseFactory;
import cn.jiuyou2020.serialize.message.protobuf.RpcResponseOuterClass.RpcResponseProto;

/**
 * @author: jiuyou2020
 * @description: protobuf rpc响应工厂,用于创建{@link ProtobufRpcResponse}
 */
public class ProtobufRpcResponseFactory extends RpcResponseFactory {

    @Override
    public RpcResponse createRpcResponse(Object o) throws Exception {
        RpcResponseProto.Builder respBuilder = RpcResponseProto.newBuilder();
        byte[] serialize = SerializationFacade.getStrategy(SerializationType.JSON.getValue()).serialize(o);
        respBuilder.setResult(com.google.protobuf.ByteString.copyFrom(serialize));
        respBuilder.setErrorMessage(RpcResponse.SUCCESS);
        return new ProtobufRpcResponse(respBuilder.build());
    }
}
