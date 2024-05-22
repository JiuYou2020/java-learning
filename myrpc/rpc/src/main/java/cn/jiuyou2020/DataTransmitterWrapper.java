package cn.jiuyou2020;

import cn.jiuyou2020.nettransmit.MessageSender;
import cn.jiuyou2020.proxy.FeignClientFactoryBean;
import cn.jiuyou2020.serialize.SerializationFacade;
import cn.jiuyou2020.serialize.message.RpcRequest;
import cn.jiuyou2020.serialize.message.RpcRequestFactory;
import cn.jiuyou2020.serialize.message.RpcResponse;
import cn.jiuyou2020.serialize.strategy.SerializationStrategy;

import java.lang.reflect.Method;

/**
 * @author: jiuyou2020
 * @description: 负责组织数据的序列化，传输和初始化
 */
public class DataTransmitterWrapper {

    public Object executeDataTransmit(Method method, Object[] args, FeignClientFactoryBean clientFactoryBean) throws Exception {
        int serializationType = EnvContext.getSerializationType().getValue();
        //执行序列化
        RpcRequest rpcRequest = RpcRequestFactory.getFactory(serializationType).createRpcRequest(method, args, clientFactoryBean);
        SerializationStrategy strategy = SerializationFacade.getStrategy(serializationType);
        byte[] serialize = strategy.serialize(rpcRequest);
        //进行数据传输
        MessageSender messageSender = new MessageSender();
        byte[] receivedData = messageSender.connect(clientFactoryBean.getUrl(), serialize);
        //执行反序列化
        RpcResponse rpcResponse = strategy
                .deserialize(receivedData, RpcResponse.getRpcResponse(serializationType).getClass());
        return rpcResponse.getResult(method.getReturnType());
    }
}
