package cn.jiuyou2020.serialize.strategy;

import cn.jiuyou2020.serialize.SerializationDataOuterClass.SerializationData;
import com.google.protobuf.InvalidProtocolBufferException;

/**
 * @author: jiuyou2020
 * @description: protobuf序列化方式
 */
public class ProtobufSerializationStrategy implements SerializationStrategy {
    @Override
    public byte[] serialize(Object object) throws Exception {
        if (object instanceof SerializationData) {
            return ((SerializationData) object).toByteArray();
        }
        throw new Exception("protobuf序列化失败");
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clazz) throws InvalidProtocolBufferException {
        if (clazz == SerializationData.class) {
            return (T) SerializationData.parseFrom(data);
        }
        throw new InvalidProtocolBufferException("protobuf反序列化失败");
    }
}
