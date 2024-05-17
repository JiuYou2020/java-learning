package cn.jiuyou2020.proxy;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.Assert;

import java.util.Objects;

@SuppressWarnings("unused")
public class FeignClientFactoryBean implements FactoryBean<Object>, InitializingBean, ApplicationContextAware, BeanFactoryAware {

    // 日志对象，用于记录日志
    private static final Log LOG = LogFactory.getLog(FeignClientFactoryBean.class);

    // Feign客户端的类型
    private Class<?> type;

    // Feign客户端的名称
    private String name;

    // Feign客户端的URL
    private String url;

    // Feign客户端的上下文ID
    private String contextId;

    // Feign客户端的路径
    private String path;

    // 是否继承父上下文
    private boolean inheritParentContext = true;

    // Spring应用上下文
    private ApplicationContext applicationContext;

    // Spring Bean工厂
    private BeanFactory beanFactory;

    // 修饰符数组
    private String[] qualifiers = new String[]{};

    // For AOT testing
    public FeignClientFactoryBean() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Creating a FeignClientFactoryBean.");
        }
    }

    /**
     * Check the properties and initialize the Feign builder.
     */
    @Override
    public void afterPropertiesSet() {
        Assert.hasText(contextId, "Context id must be set");
        Assert.hasText(name, "Name must be set");
    }


    @Override
    public Object getObject() {
        return getTarget();
    }

    /**
     * @param <T> the target type of the Feign client
     * @return a remote client created with the specified data and the context
     * information
     */
    @SuppressWarnings("unchecked")
    <T> T getTarget() {
        return (T) FeignClientProxy.createProxy(type, this);
    }

    @Override
    public Class<?> getObjectType() {
        return type;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContextId() {
        return contextId;
    }

    public void setContextId(String contextId) {
        this.contextId = contextId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isInheritParentContext() {
        return inheritParentContext;
    }

    public void setInheritParentContext(boolean inheritParentContext) {
        this.inheritParentContext = inheritParentContext;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        applicationContext = context;
        beanFactory = context;
    }

    public String[] getQualifiers() {
        return qualifiers;
    }

    public void setQualifiers(String[] qualifiers) {
        this.qualifiers = qualifiers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FeignClientFactoryBean that = (FeignClientFactoryBean) o;
        return Objects.equals(applicationContext, that.applicationContext) && Objects.equals(beanFactory, that.beanFactory) && inheritParentContext == that.inheritParentContext && Objects.equals(name, that.name) && Objects.equals(path, that.path) && Objects.equals(type, that.type) && Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(applicationContext, beanFactory, inheritParentContext, name, path, type, url);
    }

    @Override
    public String toString() {
        return "FeignClientFactoryBean{" + "type=" + type + ", " + "name='" + name + "', " + "url='" + url + "', " + "path='" + path + "', " + "inheritParentContext=" + inheritParentContext + ", " + "applicationContext=" + applicationContext + ", " + "beanFactory=" + beanFactory + "}" + "connectTimeoutMillis=" + "}" + "readTimeoutMillis=" + "}";
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

}
