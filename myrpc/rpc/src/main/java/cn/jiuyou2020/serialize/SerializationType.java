package cn.jiuyou2020.serialize;

public enum SerializationType {
    JSON(0, "json"),
    PROTOBUF(1, "protobuf");

    private final int value;
    private final String name;

    SerializationType(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static SerializationType getSerializationType(int value) {
        for (SerializationType serializationType : SerializationType.values()) {
            if (serializationType.getValue() == value) {
                return serializationType;
            }
        }
        throw new IllegalArgumentException("不支持的序列化类型：" + value);
    }

    @Override
    public String toString() {
        return "SerializationType{" +
                "value=" + value +
                ", name='" + name + '\'' +
                '}';
    }
}
