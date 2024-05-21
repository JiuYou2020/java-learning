package cn.jiuyou2020.reflectioncall;

import cn.jiuyou2020.nettransmit.protocolencoding.RpcMessage;
import cn.jiuyou2020.serialize.SerializationFacade;
import cn.jiuyou2020.serialize.SerializationType;
import cn.jiuyou2020.serialize.message.RpcRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author: jiuyou2020
 * @description: 反射调用
 */
public class ReflectionCall {
    private static final Log LOG = LogFactory.getLog(ReflectionCall.class);

    private SerializationFacade serializationFacade;

    public Object call(RpcMessage rpcMessage) throws Exception {
        RpcRequest rpcRequest;
        rpcRequest = deserialize(rpcMessage);
        String className = rpcRequest.getClassName();
        String methodName = rpcRequest.getMethodName();
        Object parameterTypes = rpcRequest.getParameterTypes();
        Object parameters = rpcRequest.getParameters();
        return null;
    }

    private RpcRequest deserialize(RpcMessage rpcMessage) throws Exception {
        byte[] data = rpcMessage.getBody();
        byte serializationType = rpcMessage.getSerializationType();
        SerializationType type = SerializationType.getSerializationType(serializationType);
        return SerializationFacade.getStrategy(type).deserialize(data, RpcRequest.getRpcRequest(type).getClass());
    }
}
