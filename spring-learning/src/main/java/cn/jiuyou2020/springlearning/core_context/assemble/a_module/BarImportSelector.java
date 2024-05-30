package cn.jiuyou2020.springlearning.core_context.assemble.a_module;

import jakarta.annotation.Nonnull;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author: jiuyou2020
 * @description: 实现ImportSelector接口，既可以导入普通类，也可以导入配置类
 * <p>
 * 可能会奇怪为什么要返回现有类的全限定名，这种设计似乎使得复杂度变高了！但是ImportSelector的设计是可以使开发者采用更灵活的声明式编程方式向IOC容器中注入Bean。
 * <p>
 * 由于传入的是全限定名，那么如果这些全限定名以配置文件的形式存在，那么就可以实现动态注入Bean的功能。
 * <p>
 * 在springboot的自动装配中，底层就是利用了ImportSelector的设计，将所有的自动装配类的全限定名放在了META-INF/spring.factories文件中，这样就可以实现自动装配。
 */
public class BarImportSelector implements ImportSelector {
    @Override
    @Nonnull
    public String[] selectImports(@Nonnull AnnotationMetadata importingClassMetadata) {
        System.out.println("BarImportSelector invoke...");
        return new String[]{Bar.class.getName(), BarConfiguration.class.getName()};
    }
}
