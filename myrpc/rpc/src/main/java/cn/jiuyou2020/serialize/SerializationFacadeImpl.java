package cn.jiuyou2020.serialize;

import cn.jiuyou2020.serialize.strategy.SerializationStrategy;

public class SerializationFacadeImpl extends SerializationFacade {
    public SerializationFacadeImpl(SerializationStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public byte[] serialize(Object object) throws Exception {
        return strategy.serialize(object);
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clazz) throws Exception {
        return strategy.deserialize(data, clazz);
    }
}
