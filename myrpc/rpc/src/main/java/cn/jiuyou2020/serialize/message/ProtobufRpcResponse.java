package cn.jiuyou2020.serialize.message;

import cn.jiuyou2020.serialize.message.RpcResponseOuterClass.RpcResponseProto;
import com.google.protobuf.InvalidProtocolBufferException;

import java.util.List;

/**
 * @author: jiuyou2020
 * @description:
 */
public class ProtobufRpcResponse extends RpcResponse {
    private byte[] result;
    private String errorMessage;
    private RpcResponseProto responseProto;

    public ProtobufRpcResponse(RpcResponseProto responseProto) {
        this.responseProto = responseProto;
    }

    public byte[] getResult() {
        return result;
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
