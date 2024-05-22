package cn.jiuyou2020.serialize.strategy;

/**
 * 序列化策略，如果要实现新的序列化方式，需要实现此接口
 */
public interface SerializationStrategy {
    byte[] serialize(Object object) throws Exception;

    <T> T deserialize(byte[] data, Class<T> clazz) throws Exception;
}
