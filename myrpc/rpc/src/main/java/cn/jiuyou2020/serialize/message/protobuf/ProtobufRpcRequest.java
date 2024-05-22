package cn.jiuyou2020.serialize.message.protobuf;

import cn.jiuyou2020.serialize.SerializationFacade;
import cn.jiuyou2020.serialize.SerializationType;
import cn.jiuyou2020.serialize.message.RpcRequest;
import cn.jiuyou2020.serialize.message.protobuf.RpcRequestOuterClass.RpcRequestProto;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;

import java.util.List;
/**
 * @author: jiuyou2020
 * @description: protobuf rpc请求
 */
public class ProtobufRpcRequest extends RpcRequest {
    private final RpcRequestProto requestProto;
    private Class<?>[] parameterTypes;

    public ProtobufRpcRequest(RpcRequestProto requestProto) {
        this.requestProto = requestProto;
    }

    @Override
    public String getClassName() {
        return requestProto.getClassName();
    }

    @Override
    public String getMethodName() {
        return requestProto.getMethodName();
    }

    @Override
    public Class<?>[] getParameterTypes() throws ClassNotFoundException {
        if (parameterTypes != null) {
            return parameterTypes;
        }
        List<String> parameterTypesList = requestProto.getParameterTypesList();
        Class<?>[] classes = new Class<?>[parameterTypesList.size()];
        getTypes(parameterTypesList, classes);
        parameterTypes = classes;
        return classes;
    }

    @Override
    public Object[] getParameters() throws Exception {
        List<ByteString> parametersList = requestProto.getParametersList();
        Object[] objects = new Object[parametersList.size()];
        getParams(parametersList, objects);
        return objects;
    }

    private static void getTypes(List<String> parameterTypesList, Class<?>[] classes) throws ClassNotFoundException {
        for (int i = 0; i < parameterTypesList.size(); i++) {
            //如果是基本类型，直接获取对应的Class对象
            switch (parameterTypesList.get(i)) {
                case "int" -> {
                    classes[i] = int.class;
                    continue;
                }
                case "long" -> {
                    classes[i] = long.class;
                    continue;
                }
                case "short" -> {
                    classes[i] = short.class;
                    continue;
                }
                case "byte" -> {
                    classes[i] = byte.class;
                    continue;
                }
                case "float" -> {
                    classes[i] = float.class;
                    continue;
                }
                case "double" -> {
                    classes[i] = double.class;
                    continue;
                }
                case "boolean" -> {
                    classes[i] = boolean.class;
                    continue;
                }
                case "char" -> {
                    classes[i] = char.class;
                    continue;
                }
            }
            classes[i] = Class.forName(parameterTypesList.get(i));
        }
    }


    private void getParams(List<ByteString> parametersList, Object[] objects) throws Exception {
        if (parameterTypes == null) {
            getParameterTypes();
        }
        for (int i = 0; i < parametersList.size(); i++) {
            objects[i] = SerializationFacade.getStrategy(SerializationType.JSON.getValue()).deserialize(parametersList.get(i).toByteArray(), parameterTypes[i]);
        }
    }

    public byte[] toByteArray() {
        return requestProto.toByteArray();
    }

    public static ProtobufRpcRequest parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return new ProtobufRpcRequest(RpcRequestProto.parseFrom(data));
    }
}
