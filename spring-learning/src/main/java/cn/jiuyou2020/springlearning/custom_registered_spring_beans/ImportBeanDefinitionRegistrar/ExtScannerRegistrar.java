package cn.jiuyou2020.springlearning.custom_registered_spring_beans.ImportBeanDefinitionRegistrar;

import cn.jiuyou2020.springlearning.custom_registered_spring_beans.Ext;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

public class ExtScannerRegistrar implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        // 获取到ExtScan注解的defaultName属性
        AnnotationAttributes scanAttrs = AnnotationAttributes.fromMap(importingClassMetadata.getAnnotationAttributes(ExtScan.class.getName()));
        String defaultName = scanAttrs.getString("defaultName");
        // 构造Ext的bean定义
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(Ext.class);
        // 给name属性赋值defaultName
        builder.addPropertyValue("name", defaultName);
        // print方法的参数值
        // 加入到bean容器
        registry.registerBeanDefinition("ext", builder.getBeanDefinition());
    }
}