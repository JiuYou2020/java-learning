package cn.jiuyou2020.serialize.message;

import cn.jiuyou2020.serialize.message.RpcRequestOuterClass.RpcRequestProto;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;

import java.util.List;

public class ProtobufRpcRequest extends RpcRequest {
    private RpcRequestProto requestProto;

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
    public String[] getParameterTypes() {
        return requestProto.getParameterTypesList().toArray(new String[0]);
    }

    @Override
    public List<byte[]> getParameters() {
        List<ByteString> parametersList = requestProto.getParametersList();
        return parametersList.stream().map(ByteString::toByteArray).toList();
    }

    public byte[] toByteArray() {
        return requestProto.toByteArray();
    }

    public static ProtobufRpcRequest parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return new ProtobufRpcRequest(RpcRequestProto.parseFrom(data));
    }
}
