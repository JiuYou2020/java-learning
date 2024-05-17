package cn.jiuyou2020.proxy;

import cn.jiuyou2020.annonation.RemoteService;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
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

    /**
     * 注册远程服务的BeanDefinition，注意，这里仅仅是将BeanDefinition注册到Spring容器，Spring容器并不会立即实例化BeanDefinition
     *
     * @param metadata 注解元数据
     * @param registry BeanDefinition注册表
     */
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
                // 注册Feign客户端
                registerFeignClient(registry, annotationMetadata, attributes);
            }
        }
    }

    /**
     * 注册Feign客户端的BeanDefinition
     * <p>
     * 另一种将BeanDefinition注册到Spring容器的方式是使用BeanDefinitionReader，BeanDefinitionReader是Spring提供的用于读取BeanDefinition的工具类 例如：
     * <pre>
     * {@code
     * BeanDefinitionHolder holder = new BeanDefinitionHolder(beanDefinition, className, qualifiers);
     * BeanDefinitionReaderUtils.registerBeanDefinition(holder, registry);
     * }
     * </pre>
     *
     * @param registry           BeanDefinition注册表
     * @param annotationMetadata 注解元数据
     * @param attributes         注解属性
     */
    private void registerFeignClient(BeanDefinitionRegistry registry, AnnotationMetadata annotationMetadata, Map<String, Object> attributes) {
        // 创建 FeignClientFactoryBean 的 BeanDefinition
        BeanDefinitionBuilder definition = BeanDefinitionBuilder.genericBeanDefinition(FeignClientFactoryBean.class);
        String className = addBeanProperty(annotationMetadata, attributes, definition);

        AbstractBeanDefinition beanDefinition = definition.getBeanDefinition();
        Class<?> type = ClassUtils.resolveClassName(className, null);
        beanDefinition.setAttribute(FactoryBean.OBJECT_TYPE_ATTRIBUTE, type);
        // 获取primary属性值
        boolean primary = (Boolean) attributes.get("primary");
        beanDefinition.setPrimary(primary);
        // 注册Feign客户端的代理类
        registry.registerBeanDefinition(className, beanDefinition);
    }

    /**
     * 添加Bean属性
     *
     * @param annotationMetadata 注解元数据
     * @param attributes         注解属性
     * @param definition         Bean定义
     * @return 类名
     */
    private String addBeanProperty(AnnotationMetadata annotationMetadata, Map<String, Object> attributes, BeanDefinitionBuilder definition) {
        String className = annotationMetadata.getClassName();
        definition.addPropertyValue("type", className);

        String name = (String) attributes.get("name");
        definition.addPropertyValue("name", name);

        definition.addPropertyValue("url", getUrl(attributes));

        String contextId = getContextId(attributes);
        definition.addPropertyValue("contextId", contextId);

        definition.addPropertyValue("path", attributes.get("path"));

        definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
        String[] qualifiers = getQualifiers(attributes);
        if (ObjectUtils.isEmpty(qualifiers)) {
            qualifiers = new String[]{contextId + "FeignClient"};
        }
        // 设置 Bean 的限定符（qualifiers）,qualifiers的作用是为Bean定义添加限定符，以便在注入时进行区分
        definition.addPropertyValue("qualifiers", qualifiers);
        return className;
    }

    /**
     * 获取代理客户端的url,并完善
     *
     * @param attributes 注解属性
     * @return 完整的url
     */
    private static Object getUrl(Map<String, Object> attributes) {
        String url;
        Object oUrl = attributes.get("url");
        if (!(oUrl instanceof String)) {
            throw new IllegalStateException("url must be string type");
        }
        if (!StringUtils.hasText((String) oUrl)) {
            throw new IllegalStateException("url must be set");
        }
        url = (String) oUrl;
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "http://" + url;
        }
        return url;
    }

    /**
     * 获取代理客户端的contextId
     *
     * @param attributes 注解属性
     * @return contextId
     */
    private static String getContextId(Map<String, Object> attributes) {
        String contextId;
        contextId = (String) attributes.get("contextId");
        if (!StringUtils.hasText(contextId)) {
            contextId = (String) attributes.get("name");
        }
        return contextId;
    }

    /**
     * 获取代理客户端的限定符,限定符用于区分不同的Bean定义，与别名相关
     *
     * @param attributes 注解属性
     * @return 限定符数组
     */
    private String[] getQualifiers(Map<String, Object> attributes) {
        if (attributes == null) {
            return null;
        }
        List<String> qualifierList = new ArrayList<>(Arrays.asList((String[]) attributes.get("qualifiers")));
        qualifierList.removeIf(qualifier -> !StringUtils.hasText(qualifier));
        if (qualifierList.isEmpty() && getQualifier(attributes) != null) {
            qualifierList = Collections.singletonList(getQualifier(attributes));
        }
        return !qualifierList.isEmpty() ? qualifierList.toArray(new String[0]) : null;
    }

    /**
     * 获取代理客户端的限定符
     *
     * @param attributes 注解属性
     * @return 限定符
     */
    private String getQualifier(Map<String, Object> attributes) {
        if (attributes == null) {
            return null;
        }
        String qualifier = (String) attributes.get("qualifier");
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
