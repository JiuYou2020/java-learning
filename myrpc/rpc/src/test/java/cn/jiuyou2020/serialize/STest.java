package cn.jiuyou2020.serialize;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.List;

/**
 * @author: jiuyou2020
 * @description: ]
 */
public class STest {
    public static void main(String[] args) throws Exception {
        // 使用 JSON 序列化
        SerializationStrategy jsonStrategy = new JsonSerializationStrategy();
        RpcRequest jsonRequest = new RpcRequest("myMethod", Arrays.asList("param1", 123), jsonStrategy);
        byte[] jsonData = jsonRequest.serialize();
        RpcRequest deserializedJsonRequest = RpcRequest.deserialize(jsonData, jsonStrategy);

        // 打印结果验证
        System.out.println(deserializedJsonRequest.getMethodName());
        System.out.println(deserializedJsonRequest.getParameters());

        // 使用 Protobuf 序列化
        SerializationStrategy protobufStrategy = new ProtobufSerializationStrategy();
        RpcRequest protobufRequest = new RpcRequest("myMethod", Arrays.asList("param1", 123), protobufStrategy);
        byte[] protobufData = protobufRequest.serialize();
        RpcRequest deserializedProtobufRequest = RpcRequest.deserialize(protobufData, protobufStrategy);

        // 打印结果验证
        System.out.println(deserializedProtobufRequest.getMethodName());
        System.out.println(deserializedProtobufRequest.getParameters());
    }
}
interface SerializationStrategy {
    byte[] serialize(Object obj) throws Exception;
    <T> T deserialize(byte[] data, Class<T> clazz) throws Exception;
}

class JsonSerializationStrategy implements SerializationStrategy {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(Object obj) throws Exception {
        return objectMapper.writeValueAsBytes(obj);
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clazz) throws Exception {
        return objectMapper.readValue(data, clazz);
    }
}
class ProtobufSerializationStrategy implements SerializationStrategy {
    @Override
    public byte[] serialize(Object obj) throws Exception {
        if (obj instanceof com.google.protobuf.Message) {
            return ((com.google.protobuf.Message) obj).toByteArray();
        } else {
            throw new IllegalArgumentException("Object is not a protobuf message");
        }
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clazz) throws Exception {
        if (com.google.protobuf.Message.class.isAssignableFrom(clazz)) {
            java.lang.reflect.Method parseMethod = clazz.getMethod("parseFrom", byte[].class);
            return clazz.cast(parseMethod.invoke(null, data));
        } else {
            throw new IllegalArgumentException("Class is not a protobuf message");
        }
    }
}
class RpcRequest {
    private String methodName;
    private List<Object> parameters;
    private SerializationStrategy serializationStrategy;

    @JsonCreator
    public RpcRequest(
            @JsonProperty("methodName") String methodName,
            @JsonProperty("parameters") List<Object> parameters,
            @JsonProperty("serializationStrategy") SerializationStrategy serializationStrategy) {
        this.methodName = methodName;
        this.parameters = parameters;
        this.serializationStrategy = serializationStrategy;
    }

    // Getters and setters

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public List<Object> getParameters() {
        return parameters;
    }

    public void setParameters(List<Object> parameters) {
        this.parameters = parameters;
    }

    public SerializationStrategy getSerializationStrategy() {
        return serializationStrategy;
    }

    public void setSerializationStrategy(SerializationStrategy serializationStrategy) {
        this.serializationStrategy = serializationStrategy;
    }

    public byte[] serialize() throws Exception {
        return serializationStrategy.serialize(this);
    }

    public static RpcRequest deserialize(byte[] data, SerializationStrategy serializationStrategy) throws Exception {
        return serializationStrategy.deserialize(data, RpcRequest.class);
    }
}
