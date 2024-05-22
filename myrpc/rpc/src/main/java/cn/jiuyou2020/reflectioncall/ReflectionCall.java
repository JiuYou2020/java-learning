package cn.jiuyou2020.reflectioncall;

import cn.jiuyou2020.nettransmit.protocolencoding.RpcMessage;
import cn.jiuyou2020.serialize.SerializationFacade;
import cn.jiuyou2020.serialize.SerializationType;
import cn.jiuyou2020.serialize.message.RpcRequest;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author: jiuyou2020
 * @description: 反射调用
 */
@Component
public class ReflectionCall {
    private final ApplicationContext applicationContext;

    public ReflectionCall(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * 反射调用
     *
     * @param rpcMessage rpc消息
     * @return 返回结果
     * @throws Exception 异常
     */
    public Object call(RpcMessage rpcMessage) throws Exception {
        byte serializationType = rpcMessage.getSerializationType();
        RpcRequest rpcRequest;
        rpcRequest = deserialize(rpcMessage, serializationType);
        String className = rpcRequest.getClassName();
        String methodName = rpcRequest.getMethodName();
        Class<?>[] parameterTypes = rpcRequest.getParameterTypes();
        Object[] parameters = rpcRequest.getParameters();
        // 反射调用,获取该接口的所有实现类
        Class<?> type = Class.forName(className);
        String[] beanNamesForType = applicationContext.getBeanNamesForType(type);
        if (beanNamesForType.length == 0) {
            throw new RuntimeException("未找到实现类 " + className);
        }
        if (beanNamesForType.length > 1) {
            throw new RuntimeException("实现类多于1个： " + className);
        }
        String beanName = beanNamesForType[0];
        Object bean = applicationContext.getBean(beanName);
        // 返回结果
        return bean.getClass().getMethod(methodName, parameterTypes).invoke(bean, parameters);
    }

    private RpcRequest deserialize(RpcMessage rpcMessage, int serializationType) throws Exception {
        byte[] data = rpcMessage.getBody();
        SerializationType type = SerializationType.getSerializationType(serializationType);
        RpcRequest rpcRequest = RpcRequest.getRpcRequest(type.getValue());
        Class<? extends RpcRequest> aClass = rpcRequest.getClass();
        return SerializationFacade.getStrategy(type.getValue()).deserialize(data, aClass);
    }
}
