# AOP Learning

spring_aop请参考如下两个链接：

1. [Spring AOP原理](https://segmentfault.com/a/1190000007469968)
2. [Spring AOP实战](https://segmentfault.com/a/1190000007469982)

Spring AOP（Aspect-Oriented Programming）和 AspectJ 都是面向切面编程的实现，它们有一定的关系但也有显著的区别。以下是它们的关系和区别：

### Spring AOP

**概念**：Spring AOP 是 Spring 框架中的一个模块，用于实现面向切面编程。它基于代理机制，可以对 Spring
管理的对象进行横切关注点（如日志、事务）的分离。

**特点**：

- **基于代理**：Spring AOP 使用 JDK 动态代理或者 CGlib 动态代理来生成代理对象。
- **运行时增强**：Spring AOP 在运行时进行增强，不需要修改字节码。
- **局限性**：只能对 Spring 管理的 bean 进行代理，只能对 public 方法进行代理。

### AspectJ

**概念**：AspectJ 是一个功能强大的面向切面编程框架。它可以通过编译时、加载时、运行时织入来实现对代码的增强。

**特点**：

- **编译时、加载时、运行时增强**：AspectJ 提供了多种织入方式，可以在编译时、类加载时或运行时进行增强。
- **更强的功能**：AspectJ 提供了比 Spring AOP 更强的功能，可以对任意类的任意方法（包括 private 方法）进行增强。
- **语法支持**：AspectJ 使用专门的语法（如 `@Aspect` 注解）来定义切面。

### 关系

Spring AOP 是 Spring 框架的一部分，提供了一种基于代理的简单 AOP 实现，而 AspectJ 是一个独立的、功能强大的 AOP 框架。Spring
AOP 可以与 AspectJ 集成，使用 AspectJ 的注解和织入机制，以提供更强大的 AOP 能力。

### 区别

- **代理机制**：Spring AOP 基于代理，而 AspectJ 可以进行编译时、加载时和运行时织入。
- **增强范围**：Spring AOP 只能对 Spring 管理的 bean 进行增强，而 AspectJ 可以对任意类进行增强。
- **性能**：AspectJ 在编译时织入的性能更高，而 Spring AOP 在运行时织入，可能有一定的性能开销。
- **功能**：AspectJ 功能更强大，支持更复杂的切面定义和切点表达式。

### 总结

- **Spring AOP**：简单易用，适用于 Spring 管理的 bean 的代理和增强。
- **AspectJ**：功能强大，适用于需要复杂 AOP 功能的场景。

选择 Spring AOP 还是 AspectJ 取决于具体的需求和应用场景。Spring AOP 更适合简单的代理和增强需求，而 AspectJ
则适合需要更强大功能和复杂场景的需求。