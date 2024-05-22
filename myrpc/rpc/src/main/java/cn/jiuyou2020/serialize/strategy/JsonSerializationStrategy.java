package cn.jiuyou2020.serialize.strategy;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author: jiuyou2020
 * @description: json序列化策略
 */
public class JsonSerializationStrategy implements SerializationStrategy {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(Object object) throws Exception {
        return objectMapper.writeValueAsBytes(object);
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clazz) throws Exception {
        return objectMapper.readValue(data, clazz);
    }
}
