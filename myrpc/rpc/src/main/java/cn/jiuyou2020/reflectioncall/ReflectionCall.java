package cn.jiuyou2020.reflectioncall;

import cn.jiuyou2020.nettransmit.protocolencoding.RpcMessage;
import cn.jiuyou2020.serialize.SerializationFacade;
import cn.jiuyou2020.serialize.SerializationType;
import cn.jiuyou2020.serialize.message.RpcRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author: jiuyou2020
 * @description: 反射调用
 */
@Component
public class ReflectionCall {
    private static final Log LOG = LogFactory.getLog(ReflectionCall.class);

    private ApplicationContext applicationContext;

    public ReflectionCall(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public Object call(RpcMessage rpcMessage) throws Exception {
        RpcRequest rpcRequest;
        rpcRequest = deserialize(rpcMessage);
        String className = rpcRequest.getClassName();
        String methodName = rpcRequest.getMethodName();
        Class<?>[] parameterTypes = rpcRequest.getParameterTypes();
        Object[] parameters = rpcRequest.getParameters();
        // 反射调用,获取该接口的所有实现类
        Class<?> type = Class.forName(className);
        String[] beanNamesForType = applicationContext.getBeanNamesForType(type);
        if (beanNamesForType.length == 0) {
            throw new RuntimeException("No implementation found for " + className);
        }
        if (beanNamesForType.length > 1) {
            throw new RuntimeException("More than one implementation found for " + className);
        }
        String beanName = beanNamesForType[0];
        Object bean = applicationContext.getBean(beanName);
        // 返回结果
        return bean.getClass().getMethod(methodName, parameterTypes).invoke(bean, parameters);
    }

    private RpcRequest deserialize(RpcMessage rpcMessage) throws Exception {
        byte[] data = rpcMessage.getBody();
        byte serializationType = rpcMessage.getSerializationType();
        SerializationType type = SerializationType.getSerializationType(serializationType);
        RpcRequest rpcRequest = RpcRequest.getRpcRequest(type.getValue());
        Class<? extends RpcRequest> aClass = rpcRequest.getClass();
        return SerializationFacade.getStrategy(type.getValue()).deserialize(data, aClass);
    }
}
