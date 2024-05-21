package cn.jiuyou2020.serialize.strategy;

import cn.jiuyou2020.serialize.message.ProtobufRpcRequest;
import com.google.protobuf.Message;

/**
 * @author: jiuyou2020
 * @description: protobuf序列化方式
 */
public class ProtobufSerializationStrategy implements SerializationStrategy {

    @Override
    public byte[] serialize(Object object) throws Exception {
        if (object instanceof Message) {
            return ((ProtobufRpcRequest) object).toByteArray();
        }
        throw new Exception("不支持的序列化对象");
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clazz) throws Exception {
        if (Message.class.isAssignableFrom(clazz)) {
            Message.Builder builder = (Message.Builder) clazz.getMethod("newBuilder").invoke(null);
            return (T) builder.mergeFrom(data).build();
        }
        throw new Exception("不支持的序列化对象");
    }
}
