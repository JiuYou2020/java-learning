package cn.southtang.a_module;

import jakarta.annotation.Nonnull;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author: jiuyou2020
 * @description: 使用DeferredImportSelector接口实现延迟加载，它可以与@Conditional注解结合使用，根据条件选择性的加载配置类
 */
public class WaiterDeferredImportSelector implements DeferredImportSelector {
    @Override
    @Nonnull
    public String[] selectImports(@Nonnull AnnotationMetadata importingClassMetadata) {
        System.out.println("WaiterDeferredImportSelector invoke...");
        return new String[]{Waiter.class.getName()};
    }
}
