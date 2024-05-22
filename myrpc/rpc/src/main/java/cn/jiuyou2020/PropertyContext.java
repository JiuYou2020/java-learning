package cn.jiuyou2020;

import cn.jiuyou2020.serialize.SerializationType;
import jakarta.annotation.Nonnull;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

/**
 * @author: jiuyou2020
 * @description: 用于获取环境变量
 */
public class PropertyContext implements EnvironmentAware {
    private static Environment environment = null;

    @Override
    public void setEnvironment(@Nonnull Environment environment) {
        PropertyContext.environment = environment;
    }

    @SuppressWarnings("unused")
    public String getProperty(String key) {
        return environment.getProperty(key);
    }

    /**
     * 获取序列化类型
     *
     * @return 序列化类型, 如果没有设置，默认为protobuf
     */
    public static SerializationType getSerializationType() {
        if (environment == null) {
            throw new IllegalArgumentException("环境变量未初始化");
        }
        SerializationType serializationType;
        if (environment.containsProperty("rpc.serialization.type")) {
            String type = environment.getProperty("rpc.serialization.type");
            if (SerializationType.JSON.getName().equals(type)) {
                serializationType = SerializationType.JSON;
            } else if (SerializationType.PROTOBUF.getName().equals(type)) {
                serializationType = SerializationType.PROTOBUF;
            } else {
                throw new IllegalArgumentException("不支持的序列化类型：" + type);
            }
        } else {
            serializationType = SerializationType.PROTOBUF;
        }
        return serializationType;
    }
}
