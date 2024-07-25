package cn.jiuyou2020.rpc.custom_serialize;

import cn.jiuyou2020.serialize.strategy.SerializationStrategy;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author: jiuyou2020
 * @description:
 */
public class CustomSerializationStrategy implements SerializationStrategy {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public CustomSerializationStrategy() {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    }

    @Override
    public byte[] serialize(Object object) throws Exception {
        return objectMapper.writeValueAsBytes(object);
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clazz) throws Exception {
        return objectMapper.readValue(data, clazz);
    }
}
