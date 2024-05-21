package cn.jiuyou2020;

import cn.jiuyou2020.nettransmit.NettyClient;
import cn.jiuyou2020.proxy.FeignClientFactoryBean;
import cn.jiuyou2020.serialize.SerializationFacade;
import cn.jiuyou2020.serialize.SerializationType;
import cn.jiuyou2020.serialize.message.RpcRequest;
import cn.jiuyou2020.serialize.message.RpcRequestFactory;
import cn.jiuyou2020.serialize.message.RpcResponseOuterClass;

import java.lang.reflect.Method;

/**
 * @author: jiuyou2020
 * @description: 负责组织数据的序列化，传输和初始化
 */
public class DataTransmitterWrapper {
    private SerializationType serializationType;

    public Object executeDataTransmit(Method method, Object[] args, FeignClientFactoryBean clientFactoryBean) throws Exception {
        serializationType = PropertyContext.getSerializationType();
        //执行序列化
        byte[] serialize = executeSerialize(method, args, clientFactoryBean);
        //进行数据传输
        NettyClient nettyClient = new NettyClient();
        byte[] receivedData = nettyClient.connect(clientFactoryBean.getUrl(), serialize);
        RpcResponseOuterClass.RpcResponseProto rpcResponse = RpcResponseOuterClass.RpcResponseProto.parseFrom(receivedData);
        return SerializationFacade.getStrategy(serializationType).deserialize(rpcResponse.getResult().toByteArray(), method.getReturnType());
    }

    /**
     * 执行序列化
     */
    private byte[] executeSerialize(Method method, Object[] args, FeignClientFactoryBean clientFactoryBean) throws Exception {
        RpcRequest rpcRequest;
        SerializationType type = PropertyContext.getSerializationType();
        rpcRequest = RpcRequestFactory.getFactory(type).createRpcRequest(method, args, clientFactoryBean);
        return SerializationFacade.getStrategy(type).serialize(rpcRequest);
    }

}
