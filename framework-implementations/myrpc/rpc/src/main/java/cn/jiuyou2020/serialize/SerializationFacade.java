package cn.jiuyou2020.serialize;

import cn.jiuyou2020.serialize.strategy.SerializationStrategy;

import java.util.HashMap;
import java.util.Map;

/**
 * 序列化外观类, 用于封装序列化策略，对外提供序列化和反序列化方法
 */
public class SerializationFacade {
    private static final Map<Integer, SerializationStrategy> map = new HashMap<>();

    public static void addStrategy(Integer type, SerializationStrategy strategy) {
        map.put(type, strategy);
    }

    public static SerializationStrategy getStrategy(Integer type) {
        return map.get(type);
    }

}
