package cn.jiuyou2020.springlearning.context.custom_registered_spring_beans.BeanDefinitionRegistryPostProcessor;

import cn.jiuyou2020.springlearning.context.custom_registered_spring_beans.Ext;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.stereotype.Component;

@Component // 本身首先是个bean
public class ExtRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        // 构造Ext的bean定义
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(Ext.class);
        // 加入到bean容器
        registry.registerBeanDefinition("ext", builder.getBeanDefinition());
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // 不用
    }
}