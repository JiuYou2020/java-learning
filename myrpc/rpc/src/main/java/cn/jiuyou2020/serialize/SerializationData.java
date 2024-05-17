package cn.jiuyou2020.serialize;

import java.util.Arrays;

/**
 * 待序列化对象，主要有四部分内容，分别是调用的远程方法的方法名、参数类型、参数值、url
 */
@SuppressWarnings("unused")
public class SerializationData {
    private String methodName;
    private String url;
    private Class<?>[] parameterTypes;
    private Object[] args;

    public SerializationData() {
    }

    public SerializationData(String methodName, Class<?>[] parameterTypes, Object[] args, String url) {
        this.methodName = methodName;
        this.parameterTypes = parameterTypes;
        this.args = args;
        this.url = url;
    }

    public String getMethodName() {
        return methodName;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "SerializationData{" +
                "methodName='" + methodName + '\'' +
                ", url='" + url + '\'' +
                ", parameterTypes=" + Arrays.toString(parameterTypes) +
                ", args=" + Arrays.toString(args) +
                '}';
    }
}
