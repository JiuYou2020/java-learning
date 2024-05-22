package cn.jiuyou2020.serialize.message.protobuf;

import cn.jiuyou2020.serialize.SerializationFacade;
import cn.jiuyou2020.serialize.SerializationType;
import cn.jiuyou2020.serialize.message.RpcResponse;
import cn.jiuyou2020.serialize.message.protobuf.RpcResponseOuterClass.RpcResponseProto;
import com.google.protobuf.InvalidProtocolBufferException;

/**
 * @author: jiuyou2020
 * @description: protobuf rpc响应
 */
@SuppressWarnings("unused")
public class ProtobufRpcResponse extends RpcResponse {
    private byte[] result;
    private String errorMessage;
    private final RpcResponseProto responseProto;

    public ProtobufRpcResponse(RpcResponseProto responseProto) {
        this.responseProto = responseProto;
    }

    public Object getResult() throws Exception {
        byte[] byteArray = responseProto.getResult().toByteArray();
        return SerializationFacade.getStrategy(SerializationType.JSON.getValue()).deserialize(byteArray, Object.class);
    }

    @Override
    public Object getResult(Class<?> returnType) throws Exception {
        byte[] byteArray = responseProto.getResult().toByteArray();
        return SerializationFacade.getStrategy(SerializationType.JSON.getValue()).deserialize(byteArray, returnType);
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setResult(byte[] result) {
        this.result = result;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public byte[] toByteArray() {
        return responseProto.toByteArray();
    }

    public static ProtobufRpcResponse parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return new ProtobufRpcResponse(RpcResponseProto.parseFrom(data));
    }
}
