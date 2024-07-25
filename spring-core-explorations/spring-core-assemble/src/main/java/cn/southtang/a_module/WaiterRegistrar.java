package cn.southtang.a_module;

import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author: jiuyou2020
 * @description: 使用编程式方式将Waiter注入到IOC容器中
 */
public class WaiterRegistrar implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(@Nonnull AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        System.out.println("WaiterRegistrar invoke...");
        registry.registerBeanDefinition("waiter", new RootBeanDefinition(Waiter.class));
    }
}
