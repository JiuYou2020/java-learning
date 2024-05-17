package cn.jiuyou2020;

import cn.jiuyou2020.proxy.FeignClientFactoryBean;
import cn.jiuyou2020.serialize.SerializationData;
import cn.jiuyou2020.serialize.SerializationFacade;
import cn.jiuyou2020.serialize.SerializationFacadeImpl;
import cn.jiuyou2020.serialize.strategy.JsonSerializationStrategy;
import org.springframework.web.bind.annotation.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author: jiuyou2020
 * @description: 负责组织数据的序列化，传输和初始化
 */
public class DataTransmitterWrapper {
    private final SerializationFacade serializationFacade;

    public DataTransmitterWrapper() {
        this.serializationFacade = new SerializationFacadeImpl(new JsonSerializationStrategy());
    }

    public Object executeDataTransmit(Method method, Object[] args, FeignClientFactoryBean clientFactoryBean) throws Exception {
        //执行序列化
        byte[] serialize = executeSerialize(method, args, clientFactoryBean);

        SerializationData deserialize = serializationFacade.deserialize(serialize, SerializationData.class);
        return deserialize.toString();
    }

    /**
     * 执行序列化
     */
    private byte[] executeSerialize(Method method, Object[] args, FeignClientFactoryBean clientFactoryBean) throws Exception {
        constructApiUrl(clientFactoryBean, method);
        String methodName = method.getName();
        Class<?>[] parameterTypes = method.getParameterTypes();
        SerializationData serializationData = new SerializationData(methodName, parameterTypes, args, clientFactoryBean.getUrl());
        return serializationFacade.serialize(serializationData);
    }

    /**
     * 构造api的url
     *
     * @param method 方法
     */
    private void constructApiUrl(FeignClientFactoryBean clientFactoryBean, Method method) {
        String url = clientFactoryBean.getUrl();
        //获取方法上的Mapping注解的value以补全url
        String value = "";
        for (Annotation annotation : method.getAnnotations()) {
            if (annotation instanceof RequestMapping) {
                value = ((RequestMapping) annotation).value()[0];
                break;
            } else if (annotation instanceof GetMapping) {
                value = ((GetMapping) annotation).value()[0];
                break;
            } else if (annotation instanceof PostMapping) {
                value = ((PostMapping) annotation).value()[0];
                break;
            } else if (annotation instanceof PutMapping) {
                value = ((PutMapping) annotation).value()[0];
                break;
            } else if (annotation instanceof DeleteMapping) {
                value = ((DeleteMapping) annotation).value()[0];
                break;
            }
        }
        if (!value.startsWith("/")) {
            throw new IllegalArgumentException("url must start with /");
        }
        url += value;
        clientFactoryBean.setUrl(url);
    }

}
