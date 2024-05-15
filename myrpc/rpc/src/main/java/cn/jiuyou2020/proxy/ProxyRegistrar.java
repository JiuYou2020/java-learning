package cn.jiuyou2020.proxy;

import cn.jiuyou2020.annonation.RemoteService;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author jiuyou2020
 * @description 远程服务代理，用于创建远程服务代理对象
 *
 * 通过实现<a href="https://docs.spring.io/spring-framework/reference/core/aot.html#aot.bestpractices.bean-registration">ImportBeanDefinitionRegistrar</a>接口，可以在Spring容器启动时动态注册BeanDefinition
 *
 */
public class ProxyRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware, EnvironmentAware {
    private ResourceLoader resourceLoader;

    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        registerRemoteServices(importingClassMetadata, registry);
    }

    private void registerRemoteServices(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
        //扫描带有@RemoteService注解的接口
        LinkedHashSet<BeanDefinition> candidateComponents = new LinkedHashSet<>();
        ClassPathScanningCandidateComponentProvider scanner = getScanner();
        scanner.addIncludeFilter(new AnnotationTypeFilter(RemoteService.class));
        scanner.setResourceLoader(this.resourceLoader);

        //获取扫描的包路径
        Set<String> basePackages = getBasePackages(metadata);
        for (String basePackage : basePackages) {
            Set<BeanDefinition> components = scanner.findCandidateComponents(basePackage);
            candidateComponents.addAll(components);
        }

        //遍历扫描到的接口，创建代理对象并注册到Spring容器
        for (BeanDefinition candidateComponent : candidateComponents) {
            if (candidateComponent instanceof AnnotatedBeanDefinition beanDefinition) {
                // 验证被注解的类是否为接口
                AnnotationMetadata annotationMetadata = beanDefinition.getMetadata();
                Assert.isTrue(annotationMetadata.isInterface(), "@FeignClient can only be specified on an interface");
                // 获取@FeignClient注解的属性值,getCanonicalName()返回类的规范名称
                Map<String, Object> attributes = annotationMetadata
                        .getAnnotationAttributes(RemoteService.class.getCanonicalName());
                // 获取Feign客户端的名称
                // String name = getClientName(attributes);
                // 获取Feign客户端的类名
                // String className = annotationMetadata.getClassName();
                // 注册Feign客户端的配置信息，如果有的话
                // registerClientConfiguration(registry, name, className, attributes.get("configuration"));

                // 注册Feign客户端
                registerFeignClient(registry, annotationMetadata, attributes);
            }
        }
    }

    private void registerFeignClient(BeanDefinitionRegistry registry, AnnotationMetadata annotationMetadata, Map<String, Object> attributes) {
        String className = annotationMetadata.getClassName();
        BeanDefinitionBuilder definition = BeanDefinitionBuilder.genericBeanDefinition(className);
        definition.addPropertyValue("name", attributes.get("value"));
        definition.addPropertyValue("url", attributes.get("url"));
        AbstractBeanDefinition beanDefinition = definition.getBeanDefinition();
        Class<?> type = ClassUtils.resolveClassName(className, null);
        beanDefinition.setAttribute(FactoryBean.OBJECT_TYPE_ATTRIBUTE, type);
        BeanDefinitionHolder holder = new BeanDefinitionHolder(beanDefinition, className);
        BeanDefinitionReaderUtils.registerBeanDefinition(holder, registry);
        System.out.println("是否有Bean：" + registry.containsBeanDefinition(className));
        System.out.println("Bean的类型：" + registry.getBeanDefinition(className).getBeanClassName());
        BeanDefinition definition1 = registry.getBeanDefinition(className);
        System.out.println("Bean的属性：" + definition1.getPropertyValues());
    }

    private String getClientName(Map<String, Object> attributes) {
        if (attributes.isEmpty()) {
            return null;
        }
        String name = (String) attributes.get("name");
        if (!StringUtils.hasText(name)) {
            name = (String) attributes.get("value");
        }
        if (StringUtils.hasText(name)) {
            return name;
        }
        throw new IllegalStateException("FeignClient name must be set");
    }

    /**
     * 获取扫描的包路径
     *
     * @param metadata 注解元数据
     * @return 包路径集合
     */
    private Set<String> getBasePackages(AnnotationMetadata metadata) {
        HashSet<String> basePackages = new HashSet<>();
        basePackages.add(ClassUtils.getPackageName(metadata.getClassName()));
        return basePackages;
    }

    /**
     * 获取扫描器,ClassPathScanningCandidateComponentProvider是Spring提供的用于扫描类路径的工具类
     *
     * @return 扫描器
     */
    protected ClassPathScanningCandidateComponentProvider getScanner() {
        return new ClassPathScanningCandidateComponentProvider(false, this.environment) {
            @Override
            //是否有资格成为候选组件
            protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
                boolean isCandidate = false;
                // 判断候选组件是否独立
                if (beanDefinition.getMetadata().isIndependent()) {
                    // 如果候选组件不是注解类型，则视为合格的候选组件
                    if (!beanDefinition.getMetadata().isAnnotation()) {
                        isCandidate = true;
                    }
                }
                return isCandidate;
            }
        };
    }


    @Component
    public static class MyFeignClientScanner {

        public MyFeignClientScanner() {
            ClassPathScanningCandidateComponentProvider scanner =
                    new ClassPathScanningCandidateComponentProvider(false);

            scanner.addIncludeFilter(new AnnotationTypeFilter(RemoteService.class));

            for (BeanDefinition bd : scanner.findCandidateComponents("cn.jiuyou2020")) {
                try {
                    Class<?> clazz = Class.forName(bd.getBeanClassName());
                    RemoteService annotation = clazz.getAnnotation(RemoteService.class);
                    if (annotation != null) {
                        Object proxy = createProxy(clazz, annotation);
                        // 注册到 Spring 容器
                        // 省略实现
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

        }

        private Object createProxy(Class<?> serviceInterface, RemoteService annotation) {
            String name = annotation.value();
            String url = annotation.url();

            InvocationHandler handler = (proxy, method, args) -> {
                // 远程调用逻辑
                // 省略实现，可以使用 HttpClient 或其他 HTTP 客户端库发送 HTTP 请求到指定的远程服务
                return "Result from remote service";
            };

            return Proxy.newProxyInstance(
                    serviceInterface.getClassLoader(),
                    new Class<?>[]{serviceInterface},
                    handler
            );
        }
    }
}
