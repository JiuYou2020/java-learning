package cn.jiuyou2020.serialize;

import cn.jiuyou2020.serialize.strategy.SerializationStrategy;

/**
 * 序列化外观类, 用于封装序列化策略，对外提供序列化和反序列化方法
 */
public abstract class SerializationFacade {
    protected SerializationStrategy strategy;

    public abstract byte[] serialize(Object object) throws Exception;

    public abstract <T> T deserialize(byte[] data, Class<T> clazz) throws Exception;

    public void setStrategy(SerializationStrategy strategy) {
        this.strategy = strategy;
    }
}
