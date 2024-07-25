> 简单总结一下`Spring Boot`的核心`@SpringBootApplication`和自动装配机制

- `@SpringBootApplication`包含`@ComponentScan`注解，可以默认扫描当前包及其子包下的所有组件，并且可以排除指定的组件或者自动配置类
- `@EnableAutoConfiguration`中包含`@AutoConfigurationPackage`注解，可以记录下最外层根包的位置，供第三方框架整合使用。
- `@EnableAutoConfiguration`导入的`AutoConfigurationImportSelector`
  可以利用SpringFramework的SPI机制加载所有的自动配置类，即META-INF/spring.factories文件中的配置
