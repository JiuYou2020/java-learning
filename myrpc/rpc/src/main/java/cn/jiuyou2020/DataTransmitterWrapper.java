package cn.jiuyou2020;

import cn.jiuyou2020.nettransmit.NettyClient;
import cn.jiuyou2020.proxy.FeignClientFactoryBean;
import cn.jiuyou2020.serialize.SerializationDataOuterClass.SerializationData;
import cn.jiuyou2020.serialize.SerializationFacade;
import cn.jiuyou2020.serialize.SerializationFacadeImpl;
import cn.jiuyou2020.serialize.strategy.ProtobufSerializationStrategy;
import org.springframework.web.bind.annotation.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author: jiuyou2020
 * @description: 负责组织数据的序列化，传输和初始化
 */
public class DataTransmitterWrapper {
    private final SerializationFacade serializationFacade;

    public DataTransmitterWrapper() {
        this.serializationFacade = new SerializationFacadeImpl(new ProtobufSerializationStrategy());
    }

    public Object executeDataTransmit(Method method, Object[] args, FeignClientFactoryBean clientFactoryBean) throws Exception {
        //执行序列化
        byte[] serialize = executeSerialize(method, args, clientFactoryBean);
        //进行数据传输
        NettyClient nettyClient = new NettyClient();
        byte[] receivedData = nettyClient.connect(clientFactoryBean.getUrl(), serialize);
        return serializationFacade.deserialize(receivedData, method.getReturnType());
    }

    /**
     * 执行序列化
     */
    private byte[] executeSerialize(Method method, Object[] args, FeignClientFactoryBean clientFactoryBean) throws Exception {
        String methodName = method.getName();
        String[] params = Arrays.stream(method.getParameterTypes()).map(Class::getName).toArray(String[]::new);
        String[] paramsData = Arrays.stream(args).map(Object::toString).toArray(String[]::new);
        SerializationData serializationData = SerializationData.newBuilder()
                .setClassName(clientFactoryBean.getType().getName())
                .setMethodName(methodName)
                .addAllParameterTypes(Arrays.asList(params))
                .addAllArgs(Arrays.asList(paramsData))
                .build();

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
