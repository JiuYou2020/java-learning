package cn.jiuyou2020.proxy;/*
 * Copyright 2013-2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


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

public class FeignClientFactoryBean implements FactoryBean<Object>, InitializingBean, ApplicationContextAware, BeanFactoryAware {

    /***********************************
     * WARNING! Nothing in this class should be @Autowired. It causes NPEs because of some
     * lifecycle race condition.
     ***********************************/

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

    // 是否忽略404错误
    private boolean dismiss404;

    // 是否继承父上下文
    private boolean inheritParentContext = true;

    // Spring应用上下文
    private ApplicationContext applicationContext;

    // Spring Bean工厂
    private BeanFactory beanFactory;

    // Feign客户端的fallback类
    private Class<?> fallback = void.class;

    // Feign客户端的fallback工厂类
    private Class<?> fallbackFactory = void.class;

    // 是否刷新客户端
    private boolean refreshableClient = false;


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

    public boolean isDismiss404() {
        return dismiss404;
    }

    public void setDismiss404(boolean dismiss404) {
        this.dismiss404 = dismiss404;
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

    public Class<?> getFallback() {
        return fallback;
    }

    public void setFallback(Class<?> fallback) {
        this.fallback = fallback;
    }

    public Class<?> getFallbackFactory() {
        return fallbackFactory;
    }

    public void setFallbackFactory(Class<?> fallbackFactory) {
        this.fallbackFactory = fallbackFactory;
    }

    public void setRefreshableClient(boolean refreshableClient) {
        this.refreshableClient = refreshableClient;
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
        return Objects.equals(applicationContext, that.applicationContext) && Objects.equals(beanFactory, that.beanFactory) && dismiss404 == that.dismiss404 && inheritParentContext == that.inheritParentContext && Objects.equals(fallback, that.fallback) && Objects.equals(fallbackFactory, that.fallbackFactory) && Objects.equals(name, that.name) && Objects.equals(path, that.path) && Objects.equals(type, that.type) && Objects.equals(url, that.url) && Objects.equals(refreshableClient, that.refreshableClient);
    }

    @Override
    public int hashCode() {
        return Objects.hash(applicationContext, beanFactory, dismiss404, inheritParentContext, fallback, fallbackFactory, name, path, type, url, refreshableClient);
    }

    @Override
    public String toString() {
        return new StringBuilder("FeignClientFactoryBean{").append("type=").append(type).append(", ").append("name='").append(name).append("', ").append("url='").append(url).append("', ").append("path='").append(path).append("', ").append("dismiss404=").append(dismiss404).append(", ").append("inheritParentContext=").append(inheritParentContext).append(", ").append("applicationContext=").append(applicationContext).append(", ").append("beanFactory=").append(beanFactory).append(", ").append("fallback=").append(fallback).append(", ").append("fallbackFactory=").append(fallbackFactory).append("}").append("connectTimeoutMillis=").append("}").append("readTimeoutMillis=").append("}").append("followRedirects=").append("refreshableClient=").append(refreshableClient).append("}").toString();
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

}
