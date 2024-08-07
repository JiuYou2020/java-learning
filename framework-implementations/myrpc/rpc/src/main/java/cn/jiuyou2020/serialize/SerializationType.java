package cn.jiuyou2020.serialize;

/**
 * @author: jiuyou2020
 * @description: 序列化类型，用于标识不同的序列化方式，如果要实现新的序列化方式，需要往3个map中加入实现的类
 */
public enum SerializationType {
    JSON(0, "json"),
    PROTOBUF(1, "protobuf"),
    //供外界自定义序列化方式
    CUSTOM(3, "custom");

    private final int value;
    private final String name;

    SerializationType(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public static SerializationType getSerializationType(String value) {
        for (SerializationType serializationType : SerializationType.values()) {
            if (serializationType.getName().equals(value)) {
                return serializationType;
            }
        }
        throw new IllegalArgumentException("不支持的序列化类型：" + value);
    }

    public static SerializationType getSerializationType(int value) {
        for (SerializationType serializationType : SerializationType.values()) {
            if (serializationType.getValue() == value) {
                return serializationType;
            }
        }
        throw new IllegalArgumentException("不支持的序列化类型：" + value);
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "SerializationType{" +
                "value=" + value +
                ", name='" + name + '\'' +
                '}';
    }
}
