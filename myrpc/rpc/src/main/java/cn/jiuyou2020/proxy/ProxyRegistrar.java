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
import org.springframework.context.ApplicationContext;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @author jiuyou2020
 * @description 远程服务代理，用于创建远程服务代理对象
 * <p>
 * 通过实现<a href="https://docs.spring.io/spring-framework/reference/core/aot.html#aot.bestpractices.bean-registration">ImportBeanDefinitionRegistrar</a>接口，可以在Spring容器启动时动态注册BeanDefinition
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
//                String name = getClientName(attributes);
                // 获取Feign客户端的类名
                String className = annotationMetadata.getClassName();
                // 注册Feign客户端的配置信息，如果有的话
//                registerClientConfiguration(registry, name, className, attributes.get("configuration"));

                // 注册Feign客户端
                registerFeignClient(registry, annotationMetadata, attributes);
            }
        }
    }

    /**
     * 注册Feign客户端的BeanDefinition
     *
     * @param registry           BeanDefinition注册表
     * @param annotationMetadata 注解元数据
     * @param attributes         注解属性
     */
    private void registerFeignClient(BeanDefinitionRegistry registry, AnnotationMetadata annotationMetadata, Map<String, Object> attributes) {
        // 创建 FeignClientFactoryBean 的 BeanDefinition
        BeanDefinitionBuilder definition = BeanDefinitionBuilder.genericBeanDefinition(FeignClientFactoryBean.class);
        definition.addPropertyValue("url", attributes.get("url"));
        definition.addPropertyValue("path", attributes.get("path"));
        String name = (String) attributes.get("name");
        definition.addPropertyValue("name", name);
        String contextId = (String) attributes.get("name");
        definition.addPropertyValue("contextId", contextId);
        String className = annotationMetadata.getClassName();
        definition.addPropertyValue("type", className);
        definition.addPropertyValue("dismiss404", Boolean.parseBoolean(String.valueOf(attributes.get("dismiss404"))));
        Object fallback = attributes.get("fallback");
        if (fallback != null) {
            definition.addPropertyValue("fallback",
                    (fallback instanceof Class ? fallback : ClassUtils.resolveClassName(fallback.toString(), null)));
        }
        Object fallbackFactory = attributes.get("fallbackFactory");
        if (fallbackFactory != null) {
            definition.addPropertyValue("fallbackFactory", fallbackFactory instanceof Class ? fallbackFactory
                    : ClassUtils.resolveClassName(fallbackFactory.toString(), null));
        }
        definition.addPropertyValue("fallbackFactory", attributes.get("fallbackFactory"));
        definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
        String[] qualifiers = getQualifiers(attributes);
        if (ObjectUtils.isEmpty(qualifiers)) {
            qualifiers = new String[]{contextId + "FeignClient"};
        }
        // 设置 Bean 的限定符（qualifiers）,qualifiers的作用是为Bean定义添加限定符，以便在注入时进行区分
        definition.addPropertyValue("qualifiers", qualifiers);
        AbstractBeanDefinition beanDefinition = definition.getBeanDefinition();
        Class<?> type = ClassUtils.resolveClassName(className, null);
        beanDefinition.setAttribute(FactoryBean.OBJECT_TYPE_ATTRIBUTE, type);
        // 获取primary属性值
        boolean primary = (Boolean) attributes.get("primary");
        beanDefinition.setPrimary(primary);
        // 注册Feign客户端的代理类
        registry.registerBeanDefinition(name, beanDefinition);
//        BeanDefinitionHolder holder = new BeanDefinitionHolder(beanDefinition, className, qualifiers);
//        BeanDefinitionReaderUtils.registerBeanDefinition(holder, registry);
//        System.out.println(11);
    }

    private String[] getQualifiers(Map<String, Object> client) {
        if (client == null) {
            return null;
        }
        List<String> qualifierList = new ArrayList<>(Arrays.asList((String[]) client.get("qualifiers")));
        qualifierList.removeIf(qualifier -> !StringUtils.hasText(qualifier));
        if (qualifierList.isEmpty() && getQualifier(client) != null) {
            qualifierList = Collections.singletonList(getQualifier(client));
        }
        return !qualifierList.isEmpty() ? qualifierList.toArray(new String[0]) : null;
    }

    private String getQualifier(Map<String, Object> client) {
        if (client == null) {
            return null;
        }
        String qualifier = (String) client.get("qualifier");
        if (StringUtils.hasText(qualifier)) {
            return qualifier;
        }
        return null;
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
}
