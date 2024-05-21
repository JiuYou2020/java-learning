package cn.jiuyou2020;

import cn.jiuyou2020.nettransmit.NettyClient;
import cn.jiuyou2020.proxy.FeignClientFactoryBean;
import cn.jiuyou2020.serialize.SerializationFacade;
import cn.jiuyou2020.serialize.message.RpcRequest;
import cn.jiuyou2020.serialize.message.RpcRequestFactory;
import cn.jiuyou2020.serialize.message.RpcResponse;

import java.lang.reflect.Method;

/**
 * @author: jiuyou2020
 * @description: 负责组织数据的序列化，传输和初始化
 */
public class DataTransmitterWrapper {
    private int serializationType;

    public Object executeDataTransmit(Method method, Object[] args, FeignClientFactoryBean clientFactoryBean) throws Exception {
        serializationType = PropertyContext.getSerializationType().getValue();
        //执行序列化
        byte[] serialize = executeSerialize(method, args, clientFactoryBean);
        //进行数据传输
        NettyClient nettyClient = new NettyClient();
        byte[] receivedData = nettyClient.connect(clientFactoryBean.getUrl(), serialize);
        //执行反序列化
        RpcResponse response = SerializationFacade.getStrategy(serializationType)
                .deserialize(receivedData, RpcResponse.getRpcResponse(serializationType).getClass());
        return response.getResult(method.getReturnType());
    }

    /**
     * 执行序列化
     */
    private byte[] executeSerialize(Method method, Object[] args, FeignClientFactoryBean clientFactoryBean) throws Exception {
        RpcRequest rpcRequest;
        rpcRequest = RpcRequestFactory.getFactory(serializationType).createRpcRequest(method, args, clientFactoryBean);
        return SerializationFacade.getStrategy(serializationType).serialize(rpcRequest);
    }

}
