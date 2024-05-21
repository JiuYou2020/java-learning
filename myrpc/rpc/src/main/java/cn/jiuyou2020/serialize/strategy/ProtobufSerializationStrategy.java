package cn.jiuyou2020.serialize.strategy;

import cn.jiuyou2020.serialize.message.ProtobufRpcRequest;
import cn.jiuyou2020.serialize.message.ProtobufRpcResponse;

/**
 * @author: jiuyou2020
 * @description: protobuf序列化方式
 */
public class ProtobufSerializationStrategy implements SerializationStrategy {

    @Override
    public byte[] serialize(Object object) throws Exception {
        if (object instanceof ProtobufRpcRequest request) {
            return request.toByteArray();
        }
        if (object instanceof ProtobufRpcResponse response) {
            return response.toByteArray();
        }
        throw new Exception("不支持的序列化对象");
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clazz) throws Exception {
        if (ProtobufRpcRequest.class.isAssignableFrom(clazz)) {
            return (T) ProtobufRpcRequest.parseFrom(data);
        }
        if (ProtobufRpcResponse.class.isAssignableFrom(clazz)) {
            return (T) ProtobufRpcResponse.parseFrom(data);
        }
        throw new Exception("不支持的序列化对象");
    }
}
