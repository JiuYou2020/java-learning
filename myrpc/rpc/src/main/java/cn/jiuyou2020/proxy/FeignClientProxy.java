package cn.jiuyou2020.proxy;

import org.springframework.web.bind.annotation.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@SuppressWarnings("unchecked")
public class FeignClientProxy implements InvocationHandler {
    private FeignClientFactoryBean feignClientFactoryBean;

    public FeignClientProxy(FeignClientFactoryBean feignClientFactoryBean) {
        this.feignClientFactoryBean = feignClientFactoryBean;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        //获取url,模拟远程调用
        String url = constructApiUrl(method);

        return url;
    }

    /**
     * 构造api的url
     *
     * @param method 方法
     * @return url
     */
    private String constructApiUrl(Method method) {
        String url = feignClientFactoryBean.getUrl();
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
        return url + value;
    }

    public static <T> T createProxy(Class<T> interfaceClass, FeignClientFactoryBean feignClientFactoryBean) {
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(),
                new Class[]{interfaceClass}, new FeignClientProxy(feignClientFactoryBean));
    }
}
