package cn.jiuyou2020;

import cn.jiuyou2020.serialize.SerializationType;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

/**
 * @author: jiuyou2020
 * @description:
 */
public class PropertyContext implements EnvironmentAware {
    private static Environment environment = null;

    @Override
    public void setEnvironment(Environment environment) {
        PropertyContext.environment = environment;
    }

    public String getProperty(String key) {
        return environment.getProperty(key);
    }

    public static SerializationType getSerializationType() {
        if (environment == null) {
            throw new IllegalArgumentException("environment is null");
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
            serializationType = SerializationType.JSON;
        }
        return serializationType;
    }
}
