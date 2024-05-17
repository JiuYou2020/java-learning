package cn.jiuyou2020.serialize.strategy;

/**
 * 序列化策略
 */
public interface SerializationStrategy {
    byte[] serialize(Object object) throws Exception;

    <T> T deserialize(byte[] data, Class<T> clazz) throws Exception;
}
