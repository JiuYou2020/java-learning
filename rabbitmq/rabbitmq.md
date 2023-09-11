# 1. 概述

[RabbitMQ](https://www.rabbitmq.com/) 是由 LShift 提供的一个 [Advanced Message Queuing Protocol (AMQP)](https://zh.wikipedia.org/zh-hans/高级消息队列协议) 的开源实现，由以高性能、健壮以及可伸缩性出名的 [Erlang](https://www.erlang.org/) 写成，因此也是继承了这些优点。

> FROM [《维基百科 —— RabbitMQ》](https://zh.wikipedia.org/wiki/RabbitMQ)
>
> Rabbit 科技有限公司开发了 RabbitMQ ，并提供对其的支持。起初，Rabbit 科技是 LSHIFT 和 CohesiveFT 在 2007 年成立的合资企业，2010 年 4 月 被 VMware 的旗下的 SpringSource 收购。RabbitMQ 在 2013 年 5 月成为 GoPivotal 的一部分。

- 这么一看，Spring Cloud 在消息队列主推 RabbitMQ ，可能还是有原因的，嘿嘿。



# 2. 安装RabbitMQ

1. 单机docker安装rabbitmq

```shell
//下载RabbitMQ镜像
docker pull rabbitmq:management
//启动
docker run --name rabbit --restart=always -p 15672:15672 -p 5672:5672  -d  rabbitmq:management
RabbitMQ,默认guest用户，密码也是guest。java
```

2. 集群部署

- [《RabbitMQ 单机多实例配置》](http://www.iocoder.cn/RabbitMQ/RabbitMQ-single-machine-multi-instance-configuration/?self) ，适合本地测试。
- [《RabbitMQ 的安装及集群搭建方法》](http://www.iocoder.cn/RabbitMQ/RabbitMQ-installation-and-cluster-setup-methods/?self)的[「2. RabbitMQ 集群搭建方法」](https://www.iocoder.cn/RabbitMQ/install/?self#) 小节，适合生产环境。

>docker修改rabbitmq密码,参考[Docker修改MySQL,RabbitMQ,Redis密码_docker修改redis密码_JiuYou2020的博客-CSDN博客](https://blog.csdn.net/qq_62656514/article/details/128602307)

# 3. RabbitMQ-SpringBoot

在 Spring 生态中，提供了 [Spring-AMQP](https://spring.io/projects/spring-amqp) 项目，让我们更简便的使用 AMQP 。其官网介绍如下：

- The Spring AMQP project applies core Spring concepts to the development of AMQP-based messaging solutions. Spring-AMQP 项目将 Spring 核心概念应用于基于 AMQP 的消息传递解决方案的开发。

- It provides a "template" as a high-level abstraction for sending and receiving messages. 

  它提供了一个“模板”作为发送消息的高级抽象。

- It also provides support for Message-driven POJOs with a "listener container". 

  它还通过“侦听器容器”为消息驱动的 POJO 提供支持。

- These libraries facilitate management of AMQP resources while promoting the use of dependency injection and declarative configuration. 

  这些库促进 AMQP 资源的管理，同时促进使用依赖注入和声明性配置。

- In all of these cases, you will see similarities to the JMS support in the Spring Framework. 

  在所有这些情况下，您将看到与 Spring 框架中的 JMS 支持的相似之处。

- The project consists of two parts; spring-amqp is the base abstraction, and spring-rabbit is the RabbitMQ implementation. 该项目包括两个部分：

  - [`spring-amqp`](https://mvnrepository.com/artifact/org.springframework.amqp/spring-amqp) 是 AMQP 的基础抽象。

  - [`spring-rabbit`](https://mvnrepository.com/artifact/org.springframework.amqp/spring-rabbit) 是基于 RabbitMQ 对 AMQP 的具体实现。

> Features(功能特性)
>
> - Listener container for asynchronous processing of inbound messages 监听器容器：异步处理接收到的消息
> - [RabbitTemplate](https://github.com/spring-projects/spring-amqp/blob/master/spring-rabbit/src/main/java/org/springframework/amqp/rabbit/core/RabbitTemplate.java) for sending and receiving messages RabbitTemplate：发送和接收消息
> - [RabbitAdmin](https://github.com/spring-projects/spring-amqp/blob/master/spring-rabbit/src/main/java/org/springframework/amqp/rabbit/core/RabbitAdmin.java) for automatically declaring queues, exchanges and bindings RabbitAdmin：自动创建队列，交换器，绑定器。

在 [Spring-Boot](https://spring.io/projects/spring-boot) 项目中，提供了 AMQP 和 RabbitMQ 的自动化配置，所以我们仅需引入 [`spring-boot-starter-amqp`](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-amqp) 依赖，即可愉快的使用。

![image-20230910212647855](https://img2023.cnblogs.com/blog/3014862/202309/3014862-20230911212219151-1179361959.png)

- AMQP 里主要要说两个组件：Exchange 和 Queue ，绿色的 X 就是 Exchange ，红色的是 Queue ，这两者都在 Server 端，又称作 Broker ，这部分是 RabbitMQ 实现的。
- 而蓝色的则是客户端，通常有 Producer 和 Consumer 两种类型（角色）。



## 3.1 快速入门

> 代码地址:[learning/rabbitmq/rabbitmq-springboot-quickstart at master · JiuYou2020/learning (github.com)](https://github.com/JiuYou2020/learning/tree/master/rabbitmq/rabbitmq-springboot-quickstart)

配置文件:

```yml
spring:
  rabbitmq:
    host: localhost
    port: 5672
    username: guest
    password: guest
```

### 3.1.1 Direct Exchange

Direct 类型的 Exchange 路由规则比较简单，它会把消息路由到那些 binding key 与 routing key 完全匹配的 Queue 中。以下图的配置为例：

![image-20230910233223954](https://img2023.cnblogs.com/blog/3014862/202309/3014862-20230911212219806-1990678444.png)

- 我们以 `routingKey="error"` 发送消息到 Exchange ，则消息会路由到 Queue1(`amqp.gen-S9b…`) 。
- 我们以 `routingKey="info"` 或 `routingKey="warning"` 来发送消息，则消息只会路由到 Queue2(`amqp.gen-Agl…`) 。
- 如果我们以其它 routingKey 发送消息，则消息不会路由到这两个 Queue 中。
- 总结来说，指定 Exchange + routing key ，有且仅会路由到至多一个 Queue 中。😈 极端情况下，如果没有匹配，消息就发送到“空气”中，不会进入任何 Queue 中。

> 注：Queue 名字 `amqp.gen-S9b…` 和 `amqp.gen-Agl…` 自动生成的。





### 3.1.2 Topic Exchange

前面讲到 Direct Exchange路由规则，是完全匹配 binding key 与routing key，但这种严格的匹配方式在很多情况下不能满足实际业务需求。

Topic Exchange 在匹配规则上进行了扩展，它与 Direct 类型的Exchange **相似**，也是将消息路由到 binding key 与 routing key 相匹配的 Queue 中，但这里的匹配规则有些不同，它约定：

- routing key 为一个句点号 `"."` 分隔的字符串。我们将被句点号`"."`分隔开的每一段独立的字符串称为一个单词，例如 "stock.usd.nyse"、"nyse.vmw"、"quick.orange.rabbit"
- binding key 与 routing key 一样也是句点号 `"."` 分隔的字符串。
- binding key 中可以存在两种特殊字符 `"*"` 与 `"#"`，用于做模糊匹配。其中 `"*"` 用于匹配一个单词，`"#"` 用于匹配多个单词（可以是零个）。

![image-20230910233241039](https://img2023.cnblogs.com/blog/3014862/202309/3014862-20230911212220310-1186608925.png)

- `routingKey="quick.orange.rabbit"` 的消息会同时路由到 Q1 与 Q2 。
- `routingKey="lazy.orange.fox"` 的消息会路由到 Q1 。
- `routingKey="lazy.brown.fox"` 的消息会路由到 Q2 。
- `routingKey="lazy.pink.rabbit"` 的消息会路由到Q2（只会投递给 Q2 一次，虽然这个 routingKey 与 Q2 的两个 bindingKey 都匹配）。
- `routingKey="quick.brown.fox"`、`routingKey="orange"`、`routingKey="quick.orange.male.rabbit"` 的消息将会被丢弃，因为它们没有匹配任何 bindingKey 。







### 3.1.3 Fanout Exchange

> 也称为Publish/Subscribe模式

Fanout Exchange 路由规则非常简单，它会把所有发送到该 Exchange 的消息路由到所有与它绑定的 Queue 中。如下图：

![image-20230910233431998](https://img2023.cnblogs.com/blog/3014862/202309/3014862-20230911212220857-1262381795.png)

- 生产者（P）发送到 Exchange（X）的所有消息都会路由到图中的两个 Queue，并最终被两个消费者（C1 与 C2）消费。
- 总结来说，指定 Exchange ，会路由到**多个**绑定的 Queue 中。





## 3.2 批量发送消息

在一些业务场景下，我们希望使用 Producer 批量发送消息，提高发送性能。RocketMQ 是提供了一个可以批量发送多条消息的 API 。而 Spring-AMQP 提供的批量发送消息，它提供了一个 [MessageBatch](https://github.com/spring-projects/spring-amqp/blob/master/spring-rabbit/src/main/java/org/springframework/amqp/rabbit/batch/MessageBatch.java) 消息收集器，将发送给**相同 Exchange + RoutingKey 的消息们**，“**偷偷**”收集在一起，当满足条件时候，一次性批量发送提交给 `RabbitMQ Broker` 。

Spring-AMQP 通过 [BatchingRabbitTemplate](https://github.com/spring-projects/spring-amqp/blob/master/spring-rabbit/src/main/java/org/springframework/amqp/rabbit/core/BatchingRabbitTemplate.java) 提供批量发送消息的功能。如下是三个条件，满足**任一**即会批量发送：

- 【数量】`batchSize` ：超过收集的消息数量的最大条数。
- 【空间】`bufferLimit` ：超过收集的消息占用的最大内存。
- 【时间】`timeout` ：超过收集的时间的最大等待时长，单位：毫秒。😈 不过要注意，这里的超时开始计时的时间，是**以最后一次发送时间为起点**。也就说，每调用一次发送消息，都以当前时刻开始计时，重新到达 `timeout` 毫秒才算超时。

另外，BatchingRabbitTemplate 提供的批量发送消息的能力**比较弱**。对于同一个 BatchingRabbitTemplate 对象来说，**同一时刻只能有一个批次(保证 Exchange + RoutingKey 相同)**，否则会报错。

> 配置文件同[3.1 快速入门](# 3.1 快速入门)
>
> 代码地址:[learning/rabbitmq/rabbitmq-springboot-batch-sending at master · JiuYou2020/learning (github.com)](https://github.com/JiuYou2020/learning/tree/master/rabbitmq/rabbitmq-springboot-batch-sending)

我们已经实现批量发送消息到 RabbitMQ Broker 中。那么，我们来思考一个问题，这批消息在 RabbitMQ Broker 到底是存储**一条**消息，还是**多条**消息？

- 如果胖友使用过 Kafka、RocketMQ 这两个消息队列，那么判断肯定会是**多条**消息。

😭 实际上，RabbitMQ Broker 存储的是**一条**消息。又或者说，**RabbitMQ 并没有提供批量接收消息的 API 接口**。

那么，为什么我们在**批量发送消息**能够实现呢？答案是批量发送消息是 Spring-AMQP 的 [SimpleBatchingStrategy](https://github.com/spring-projects/spring-amqp/blob/master/spring-rabbit/src/main/java/org/springframework/amqp/rabbit/batch/SimpleBatchingStrategy.java) 所封装提供：

- 在 Producer 最终批量发送消息时，SimpleBatchingStrategy 会通过 [`#assembleMessage()`](https://github.com/spring-projects/spring-amqp/blob/master/spring-rabbit/src/main/java/org/springframework/amqp/rabbit/batch/SimpleBatchingStrategy.java#L141-L156) 方法，将批量发送的**多条**消息**组装**成一条“批量”消息，然后进行发送。
- 在 Consumer 拉取到消息时，会根据[`#canDebatch(MessageProperties properties)`](https://github.com/spring-projects/spring-amqp/blob/master/spring-rabbit/src/main/java/org/springframework/amqp/rabbit/batch/SimpleBatchingStrategy.java#L158-L163) 方法，判断该消息是否为一条“批量”消息？如果是，则调用[`# deBatch(Message message, Consumer fragmentConsumer)`](https://github.com/spring-projects/spring-amqp/blob/master/spring-rabbit/src/main/java/org/springframework/amqp/rabbit/batch/SimpleBatchingStrategy.java#L165-L194) 方法，将一条“批量”消息**拆开**，变成**多条**消息。





## 3.3 批量消费消息

### 3.3.1 基于批量发送的批量消费

在 SimpleBatchingStrategy 将一条“批量”消息拆开，变成多条消息后，直接**批量**交给 Consumer 进行消费处理。

> 配置文件同[3.1 快速入门](# 3.1 快速入门)
>
> 代码地址:[learning/rabbitmq/rabbitmq-springboot-batch-consumer at master · JiuYou2020/learning (github.com)](https://github.com/JiuYou2020/learning/tree/master/rabbitmq/rabbitmq-springboot-batch-consumer)

### 3.3.2 基于阻塞的批量消费

刚刚已经学会了依赖于批量发送的批量消费模式,但有点过于苛刻,所以，Spring-AMQP 提供了第二种批量消费消息的方式。

其实现方式是，阻塞等待最多 `receiveTimeout` 秒，拉取 `batchSize` 条消息，进行批量消费。

- 如果在 `receiveTimeout` 秒内已经成功拉取到 `batchSize` 条消息，则直接进行批量消费消息。
- 如果在 `receiveTimeout` 秒还没拉取到 `batchSize` 条消息，不再等待，而是进行批量消费消息。

不过 Spring-AMQP 的阻塞等待时长 `receiveTimeout` 的设计有点“神奇”。

- 它代表的是，每次拉取一条消息，最多阻塞等待 `receiveTimeout` 时长。如果等待不到下一条消息，则进入已获取到的消息的批量消费。😈 也就是说，极端情况下，可能等待 `receiveTimeout * batchSize` 时长，才会进行批量消费。
- 感兴趣的朋友，可以点击 [`SimpleMessageListenerContainer#doReceiveAndExecute(BlockingQueueConsumer consumer)`](https://github.com/spring-projects/spring-amqp/blob/master/spring-rabbit/src/main/java/org/springframework/amqp/rabbit/listener/SimpleMessageListenerContainer.java#L922) 方法，简单阅读源码，即可快速理解。

> 配置文件同[3.1 快速入门](# 3.1 快速入门)
>
> 代码地址:[learning/rabbitmq/rabbitmq-springboot-batch-consumer2 at master · JiuYou2020/learning (github.com)](https://github.com/JiuYou2020/learning/tree/master/rabbitmq/rabbitmq-springboot-batch-consumer2)







## 3.4 消费重试

在开始本小节之前，胖友首先要对 RabbitMQ 的[死信队列](https://www.rabbitmq.com/dlx.html)的机制，有一定的了解。

在消息**消费失败**的时候，Spring-AMQP 会通过**消费重试**机制，重新投递该消息给 Consumer ，让 Consumer 有机会重新消费消息，实现消费成功。

当然，Spring-AMQP 并不会无限重新投递消息给 Consumer 重新消费，而是在默认情况下，达到 N 次重试次数时，Consumer 还是消费失败时，该消息就会进入到**死信队列**。后续，我们可以通过对死信队列中的消息进行重发，来使得消费者实例再次进行消费。

- 在`RocketMQ`中,消费重试和死信队列，是 RocketMQ 自带的功能。
- 而在 RabbitMQ 中，消费重试是由 Spring-AMQP 所封装提供的，死信队列是 RabbitMQ 自带的功能。

那么消费失败到达最大次数的消息，是怎么进入到死信队列的呢？Spring-AMQP 在消息到达最大消费次数的时候，会将该消息进行否定(`basic.nack`)，并且 `requeue=false` ，这样后续就可以利用 RabbitMQ 的[死信队列](https://www.rabbitmq.com/dlx.html)的机制，将该消息转发到死信队列。

另外，每条消息的失败重试，是可以配置一定的**间隔时间**。具体，我们在示例的代码中，来进行具体的解释。

> 代码地址:[learning/rabbitmq/rabbitmq-springboot-consumer-retry at master · JiuYou2020/learning (github.com)](https://github.com/JiuYou2020/learning/tree/master/rabbitmq/rabbitmq-springboot-consumer-retry)

配置文件:

```yml
spring:
  # RabbitMQ 配置项，对应 RabbitProperties 配置类
  rabbitmq:
    host: 127.0.0.1 # RabbitMQ 服务的地址
    port: 5672 # RabbitMQ 服务的端口
    username: guest # RabbitMQ 服务的账号
    password: guest # RabbitMQ 服务的密码
    listener:
      simple:
        # 对应 RabbitProperties.ListenerRetry 类
        retry:
          enabled: true # 开启消费重试机制
          max-attempts: 3 # 最大重试次数。默认为 3 。
          initial-interval: 1000 # 重试间隔，单位为毫秒。默认为 1000 。
```

- 相比之前的配置文件来说，我们通过**新增** `spring.rabbitmq.simple.retry.enable=true` 配置项，来开启 Spring-AMQP 的消费重试的功能。同时，通过**新增** `max-attempts` 和 `initial-interval` 配置项，设置重试次数和间隔。

  > `max-attempts` 配置项要注意，是一条消息一共尝试消费总共 `max-attempts` 次，包括首次的正常消费。

- 另外，可以通过添加 `spring.rabbitmq.listener.simple.retry.multiplier` 配置项来实现**递乘**的时间间隔，添加 `spring.rabbitmq.listener.simple.retry.max-interval` 配置项来实现**最大**的时间间隔。

在 Spring-AMQP 的消费重试机制中，在消费失败到达最大次数后，会**自动**抛出 [AmqpRejectAndDontRequeueException](https://github.com/spring-projects/spring-amqp/blob/master/spring-amqp/src/main/java/org/springframework/amqp/AmqpRejectAndDontRequeueException.java) 异常，从而结束该消息的消费重试。这意味着什么呢？如果我们在消费消息的逻辑中，**主动**抛出 AmqpRejectAndDontRequeueException 异常，也能结束该消息的消费重试。😈 结束的方式，Spring-AMQP 是通过我们在上文中提到的 `basic.nack` + `requeue=false` ，从而实现转发该消息到死信队列中。

另外，默认情况下，`spring.rabbitmq.simple.retry.enable=false` ，关闭 Spring-AMQP 的消费重试功能。但是实际上，消费发生异常的消息，还是会一直**重新消费**。这是为什么呢？Spring-AMQP 会将该消息通过 `basic.nack` + `requeue=true` ，重新投递回**原队列的尾巴**。如此，我们便会不断拉取到该消息，不断“重试”消费该消息。当然在这种情况下，我们一样可以**主动**抛出 AmqpRejectAndDontRequeueException 异常，也能结束该消息的消费重试。😈 结束的方式，Spring-AMQP 也是通过我们在上文中提到的 `basic.nack` + `requeue=false` ，从而实现转发该消息到死信队列中。

> 简而言之,Spring AMQP的重试机制默认是关闭的,但仍会发生消费重试,这是通过requeue=true实现的。如果想完全停止重试,需要主动抛出特定异常来拒绝重新入队。

这里，我们再来简单说说 Spring-AMQP 是怎么提供**消费重试**的功能的。

- Spring-AMQP 基于 [spring-retry](https://github.com/spring-projects/spring-retry) 项目提供的 [RetryTemplate](https://github.com/spring-projects/spring-retry/blob/master/src/main/java/org/springframework/retry/support/RetryTemplate.java) ，实现重试功能。Spring-AMQP 在获取到消息时，会交给 RetryTemplate 来调用消费者 Consumer 的监听器 Listener(就是我们实现的)，实现该消息的**多次**消费重试。

- 在该消息的**每次消费失败**后，RetryTemplate 会通过 [BackOffPolicy](https://github.com/spring-projects/spring-retry/blob/master/src/main/java/org/springframework/retry/backoff/BackOffPolicy.java) 来进行计算，该消息的**下一次重新消费的时间**，通过 `Thread#sleep(...)` 方法，实现重新消费的时间间隔。到达时间间隔后，RetryTemplate 又会调用消费者 Consumer 的监听器 Listener 来消费该消息。

- 当该消息的重试消费到达**上限**后，RetryTemplate 会调用 [MethodInvocationRecoverer](https://github.com/spring-projects/spring-retry/blob/master/src/main/java/org/springframework/retry/interceptor/MethodInvocationRecoverer.java) 回调来实现恢复。而 Spring-AMQP 自定义实现了 [RejectAndDontRequeueRecoverer](https://github.com/spring-projects/spring-amqp/blob/master/spring-rabbit/src/main/java/org/springframework/amqp/rabbit/retry/RejectAndDontRequeueRecoverer.java) 来**自动**抛出 AmqpRejectAndDontRequeueException 异常，从而结束该消息的消费重试。😈 结束的方式，Spring-AMQP 是通过我们在上文中提到的 `basic.nack` + `requeue=false` ，从而实现转发该消息到死信队列中。

- 有一点需要注意，Spring-AMQP 提供的消费重试的**计数**是**客户端**级别的，重启 JVM 应用后，计数是会丢失的。所以，如果想要计数进行持久化，需要自己重新实现下。

  > 😈 RocketMQ 提供的消费重试的计数，目前是**服务端**级别，已经进行持久化。





## 3.5 定时消息

在上小节中，我们看到 Spring-AMQP 基于 RabbitMQ 提供的**死信队列**，通过 `basic.nack` + `requeue=false` 的方式，将重试消费到达上限次数的消息，投递到死信队列中。

本小节，我们还是基于 RabbitMQ 的**死信队列**，实现**定时消息**的功能。RabbitMQ 提供了过期时间 [TTL](https://www.rabbitmq.com/ttl.html) 机制，可以设置消息在队列中的存活时长。在消息到达过期时间时，会从当前队列中删除，并被 RabbitMQ 自动转发到对应的死信队列中。

那么，如果我们创建消费者 Consumer ，来消费该死信队列，是不是就实现了**延迟队列**的效果。😈 如此，我们便实现了定时消息的功能。

> 配置文件:同[3.4 消费重试](# 3.4 消费重试)
>
> 代码地址:[learning/rabbitmq/rabbitmq-springboot-timed-message at master · JiuYou2020/learning (github.com)](https://github.com/JiuYou2020/learning/tree/master/rabbitmq/rabbitmq-springboot-timed-message)





## 3.6 消息模式

在消息队列中，有两种经典的消息模式：「点对点」和「发布订阅」。可以看看[《消息队列两种模式：点对点与发布订阅》](http://www.iocoder.cn/Fight/There-are-two-modes-of-message-queuing-point-to-point-and-publish-subscription/?self)文章。

如果胖友有使用过 RocketMQ 或者 Kafka 消息队列，可能比较习惯的叫法是：

> **集群消费（Clustering）**：对应「点对点」 集群消费模式下，相同 Consumer Group 的每个 Consumer 实例平均分摊消息。
>
> **广播消费（Broadcasting）**：对应「发布订阅」 广播消费模式下，相同 Consumer Group 的每个 Consumer 实例都接收全量的消息。

下文我们统一采用集群消费和广播消费叫法。

### 3.6.1 集群消费

> 每个消息只消费一次

在 RabbitMQ 中，如果多个 Consumer 订阅相同的 Queue ，那么每一条消息有且仅会被一个 Consumer 所消费。这个特性，就为我们实现集群消费提供了基础。

在本示例中，我们会把一个 Queue 作为一个 Consumer Group ，同时创建消费该 Queue 的 Consumer 。这样，在我们启动多个 JVM 进程时，就会有多个 Consumer 消费该 Queue ，从而实现集群消费的效果。

> 配置文件同[3.1 快速入门](# 3.1 快速入门)
>
> 代码地址:[learning/rabbitmq/rabbitmq-springboot-message-model at master · JiuYou2020/learning (github.com)](https://github.com/JiuYou2020/learning/tree/master/rabbitmq/rabbitmq-springboot-message-model)

关于使用的Exchange是Topic类型,为什么?

- 为什么不选择 Exchange 类型是 **Direct** 呢？考虑到集群消费的模式，会存在多 Consumer Group 消费的情况，显然我们要支持一条消息投递到多个 Queue 中，所以 Direct Exchange 基本就被排除了。

- 为什么不选择 Exchange 类型是 **Fanout** 或者 **Headers** 呢？实际是可以的，看了大佬(didi) [Spring Cloud Stream RabbitMQ](https://github.com/spring-cloud/spring-cloud-stream-binder-rabbit) 是怎么实现的。得知答案是[默认](https://raw.githubusercontent.com/spring-cloud/spring-cloud-stream-binder-rabbit/master/docs/src/main/asciidoc/images/rabbit-binder.png)是使用 Topic Exchange 的

### 3.6.2 广播消费

> 消息可能会被消费多次

在[3.6.1 集群消费](# 3.6.1 集群消费)中，我们通过“在 RabbitMQ 中，如果多个 Consumer 订阅相同的 Queue ，那么每一条消息有且仅会被一个 Consumer 所消费”特性，来实现了集群消费。但是，在实现广播消费时，这个特性恰恰成为了一种阻碍。

不过机智的我们，我们可以通过给每个 Consumer 创建一个其**独有** Queue ，从而保证都能接收到全量的消息。同时，RabbitMQ 支持队列的自动删除，所以我们可以在 Consumer 关闭的时候，通过该功能删除其**独有**的 Queue 。



## 3.7 并发消费

在上述的示例中，我们配置的每一个 Spring-AMQP `@RabbitListener` ，都是**串行**消费的。显然，这在监听的 Queue 每秒消息量比较大的时候，会导致消费不及时，导致消息积压的问题。

虽然说，我们可以通过启动多个 JVM 进程，实现**多进程的并发消费**，从而加速消费的速度。但是问题是，否能够实现**多线程**的并发消费呢？答案是**有**。

在 `@RabbitListener` 注解中，有 `concurrency` 属性，它可以指定并发消费的线程数。例如说，如果设置 `concurrency=4` 时，Spring-AMQP 就会为**该** `@RabbitListener` 创建 4 个线程，进行并发消费。

考虑到让胖友能够更好的理解 `concurrency` 属性，我们来简单说说 Spring-AMQP 在这块的实现方式。我们来举个例子：

- 首先，我们来创建一个 Queue 为 `"DEMO"` 。
- 然后，我们创建一个 Demo9Consumer 类，并在其消费方法上，添加 `@RabbitListener(concurrency=2)` 注解。
- 再然后，我们启动项目。Spring-AMQP 会根据 `@RabbitListener(concurrency=2)` 注解，创建 **2** 个 RabbitMQ Consumer 。注意噢，是 **2** 个 RabbitMQ Consumer 呢！！！后续，每个 RabbitMQ Consumer 会被**单独**分配到一个线程中，进行拉取消息，消费消息。

酱紫讲解一下，胖友对 Spring-AMQP 实现**多线程**的并发消费的机制，是否理解了。

> 代码地址:[learning/rabbitmq/rabbitmq-springboot-concurrency at master · JiuYou2020/learning (github.com)](https://github.com/JiuYou2020/learning/tree/master/rabbitmq/rabbitmq-springboot-concurrency)

**配置文件:**

在开始看具体的应用配置文件之前，我们先来了了解下 Spring-AMQP 的两个 [ContainerType](https://github.com/spring-projects/spring-boot/blob/master/spring-boot-project/spring-boot-autoconfigure/src/main/java/org/springframework/boot/autoconfigure/amqp/RabbitProperties.java#L566-L579) 容器类型，枚举如下：



```java
// RabbitProperties.java

public enum ContainerType {

	/**
	 * Container where the RabbitMQ consumer dispatches messages to an invoker thread.
	 */
	SIMPLE,

	/**
	 * Container where the listener is invoked directly on the RabbitMQ consumer
	 * thread.
	 */
	DIRECT

}
```



① 第一种类型，`SIMPLE` 对应 [SimpleMessageListenerContainer](https://github.com/spring-projects/spring-amqp/blob/master/spring-rabbit/src/main/java/org/springframework/amqp/rabbit/listener/SimpleMessageListenerContainer.java) 消息监听器容器。它一共有两**类**线程：

- Consumer 线程，负责从 RabbitMQ Broker 获取 Queue 中的消息，存储到**内存中**的 [BlockingQueue](https://github.com/openjdk-mirror/jdk7u-jdk/blob/master/src/share/classes/java/util/concurrent/BlockingQueue.java) 阻塞队列中。
- Listener 线程，负责从**内存中**的 BlockingQueue 获取消息，进行消费逻辑。

注意，每一个 Consumer 线程，对应一个 RabbitMQ Consumer ，对应一个 Listener 线程。也就是说，它们三者是**一一对应**的。

② 第二种类型，`DIRECT` 对应 [DirectMessageListenerContainer](https://github.com/spring-projects/spring-amqp/blob/master/spring-rabbit/src/main/java/org/springframework/amqp/rabbit/listener/DirectMessageListenerContainer.java) 消息监听器容器。它只有**一类**线程，即做 `SIMPLE` 的 Consumer 线程的工作，也做 `SIMPLE` 的 Listener 线程工作。

注意，因为只有**一类**线程，所以它要么正在获取消息，要么正在消费消息，也就是**串行**的。

🔥 默认情况下，Spring-AMQP 选择使用第一种类型，即 `SIMPLE` 容器类型。

下面，让我们一起看看 [`application.yaml`](https://github.com/YunaiV/SpringBoot-Labs/blob/master/lab-04-rabbitmq/lab-04-rabbitmq-demo-concurrency/src/main/resources/application.yaml) 配置文件。配置如下：

```yml
spring:
  # RabbitMQ 配置项，对应 RabbitProperties 配置类
  rabbitmq:
    host: 127.0.0.1 # RabbitMQ 服务的地址
    port: 5672 # RabbitMQ 服务的端口
    username: guest # RabbitMQ 服务的账号
    password: guest # RabbitMQ 服务的密码
    listener:
      type: simple # 选择的 ListenerContainer 的类型。默认为 simple 类型
      simple:
        concurrency: 2 # 每个 @ListenerContainer 的并发消费的线程数
        max-concurrency: 10 # 每个 @ListenerContainer 允许的并发消费的线程数
#      direct:
#        consumers-per-queue: 2 # 对于每一个 @RabbitListener ，一个 Queue ，对应创建几个 Consumer 。
```

额外三个参数：

- `spring.rabbitmq.listener.type`
- `spring.rabbitmq.listener.simple.concurrency`
- `spring.rabbitmq.listener.simple.max-concurrency`

要**注意**，是 `spring.rabbitmq.listener.simple.max-concurrency` 配置，是**限制**每个 `@RabbitListener` 的**允许**配置的 `concurrency` 最大大小。如果超过，则会抛出 IllegalArgumentException 异常。在具体的消费类中，我们会看到 `@RabbitListener` 注解，有一个 `concurrency` 属性，可以自定义每个 `@RabbitListener` 的并发消费的线程数。







## 3.8 顺序消息

我们先来一起了解下顺序消息的**顺序消息**的定义：

- 普通顺序消息 ：Producer 将相关联的消息发送到相同的消息队列。
- 完全严格顺序 ：在【普通顺序消息】的基础上，Consumer 严格顺序消费。

那么，让我们来思考下，如果我们希望在 RabbitMQ 上，实现顺序消息需要做两个事情。

① **事情一**，我们需要保证 RabbitMQ Producer 发送相关联的消息发送到相同的 Queue 中。例如说，我们要发送用户信息发生变更的 Message ，那么如果我们希望使用顺序消息的情况下，可以将**用户编号**相同的消息发送到相同的 Queue 中。

② **事情二**，我们在有**且仅启动一个** Consumer 消费该队列，保证 Consumer 严格顺序消费。

不过如果这样做，会存在两个问题，我们逐个来看看。

① **问题一**，正如我们在[3.7 并发消费](# 3.7 并发消费)中提到，如果我们将消息仅仅投递到一个 Queue 中，并且采用单个 Consumer **串行**消费，在监听的 Queue 每秒消息量比较大的时候，会导致消费不及时，导致消息积压的问题。

此时，我们有两种方案来解决：

- 方案一，在 Producer 端，将 Queue 拆成多个**子** Queue 。假设原先 Queue 是 `QUEUE_USER` ，那么我们就分拆成 `QUEUE_USER_00` 至 `QUEUE_USER_..${N-1}` 这样 N 个队列，然后基于消息的用户编号取余，路由到对应的**子** Queue 中。
- 方案二，在 Consumer 端，将 Queue 拉取到的消息，将相关联的消息发送到**相同的线程**中来消费。例如说，还是 Queue 是 `QUEUE_USER` 的例子，我们创建 N 个线程池大小为 1 的 [ExecutorService](https://github.com/JetBrains/jdk8u_jdk/blob/master/src/share/classes/java/util/concurrent/ExecutorService.java) 数组，然后基于消息的用户编号取余，提交到对应的 ExecutorService 中的单个线程来执行。

两个方案，并不冲突，可以结合使用。

② **问题二**，如果我们启动相同 Consumer 的**多个进程**，会导致相同 Queue 的消息被分配到多个 Consumer 进行消费，破坏 Consumer 严格顺序消费。

此时，我们有两种方案来解决：

- 方案一，引入 ZooKeeper 来协调，动态设置多个进程中的**相同的** Consumer 的开关，保证有且仅有一个 Consumer 开启对**同一个** Queue 的消费。
- 方案二，仅适用于【问题一】的【方案一】。还是引入 ZooKeeper 来协调，动态设置多个进程中的**相同的** Consumer 消费的 Queue 的分配，保证有且仅有一个 Consumer 开启对**同一个** Queue 的消费。

下面，我们开始本小节的示例。

- 对于问题一，我们采用方案一。因为在 Spring-AMQP 中，自己定义线程来消费消息，无法和现有的 [MessageListenerContainer](https://github.com/spring-projects/spring-framework/blob/master/spring-jms/src/main/java/org/springframework/jms/listener/MessageListenerContainer.java) 的实现所结合，除非自定义一个 MessageListenerContainer 实现类。
- 对于问题二，因为实现起来比较复杂，暂时先不提供。

> 配置文件同[3.1 快速入门](# 3.1 快速入门)
>
> 代码地址:[learning/rabbitmq/rabbitmq-springboot-orderly at master · JiuYou2020/learning (github.com)](https://github.com/JiuYou2020/learning/tree/master/rabbitmq/rabbitmq-springboot-orderly)

在执行测试方法时发现:

- 相同编号的消息，被投递到相同的**子** Queue ，被相同的线程所消费。符合预期~



## 3.9 消费者的消息确认

在 RabbitMQ 中，Consumer 有两种消息确认的方式：

- 方式一，自动确认。
- 方式二，手动确认。

对于**自动确认**的方式，RabbitMQ Broker 只要将消息写入到 TCP Socket 中成功，就认为该消息投递成功，而无需 Consumer **手动确认**。

对于**手动确认**的方式，RabbitMQ Broker 将消息发送给 Consumer 之后，由 Consumer **手动确认**之后，才任务消息投递成功。

实际场景下，因为自动确认存在可能**丢失消息**的情况，所以在对**可靠性**有要求的场景下，我们基本采用手动确认。当然，如果允许消息有一定的丢失，对**性能**有更高的场景下，我们可以考虑采用自动确认。

😈 更多关于消费者的消息确认的内容，胖友可以阅读如下的文章：

- [《Consumer Acknowledgements and Publisher Confirms》](https://www.rabbitmq.com/confirms.html) 的消费者部分的内容，对应中文翻译为 [《消费者应答（ACK）和发布者确认（Confirm）》](https://blog.bossma.cn/rabbitmq/consumer-ack-and-publisher-confirm/) 。
- [《RabbitMQ 之消息确认机制（事务 + Confirm）》](http://www.iocoder.cn/RabbitMQ/message-confirmation-mechanism-transaction-Confirm/?self) 文章的[「消息确认（Consumer端）」](https://www.iocoder.cn/Spring-Boot/RabbitMQ/#)小节。

在 Spring-AMQP 中，在 [AcknowledgeMode](https://github.com/spring-projects/spring-amqp/blob/master/spring-amqp/src/main/java/org/springframework/amqp/core/AcknowledgeMode.java) 中，定义了三种消息确认的方式：



```java
// AcknowledgeMode.java

/**
 * No acks - {@code autoAck=true} in {@code Channel.basicConsume()}.
 */
NONE, // 对应 Consumer 的自动确认

/**
 * Manual acks - user must ack/nack via a channel aware listener.
 */
MANUAL, // 对应 Consumer 的手动确认，由开发者在消费逻辑中，手动进行确认。

/**
 * Auto - the container will issue the ack/nack based on whether
 * the listener returns normally, or throws an exception.
 * <p><em>Do not confuse with RabbitMQ {@code autoAck} which is
 * represented by {@link #NONE} here</em>.
 */
AUTO; // 对应 Consumer 的手动确认，在消费消息完成（包括正常返回、和抛出异常）后，由 Spring-AMQP 框架来“自动”进行确认。
```



- 实际上，就是将**手动确认**进一步细分，提供了由 Spring-AMQP 提供 Consumer 级别的自动确认。

**在上述的示例中，我们都采用了 Spring-AMQP 默认的 `AUTO` 模式**。下面，我们来搭建一个 `MANUAL` 模式，手动确认的示例

> 配置文件:
>
> ```yml
> spring:
>   # RabbitMQ 配置项，对应 RabbitProperties 配置类
>   rabbitmq:
>     host: 127.0.0.1 # RabbitMQ 服务的地址
>     port: 5672 # RabbitMQ 服务的端口
>     username: guest # RabbitMQ 服务的账号
>     password: guest # RabbitMQ 服务的密码
>     listener:
>       simple:
>         acknowledge-mode: manual # 配置 Consumer 手动提交
> ```
>
> 代码地址:[learning/rabbitmq/rabbitmq-springboot-ack at master · JiuYou2020/learning (github.com)](https://github.com/JiuYou2020/learning/tree/master/rabbitmq/rabbitmq-springboot-ack)



测试后,此时，如果我们使用 [RabbitMQ Management](https://static.iocoder.cn/7c5541295505e7a3be4ac7ab2882feeb) 来查看 `"DEMO"` 的该消费者：![ 的消费者列](https://img2023.cnblogs.com/blog/3014862/202309/3014862-20230911212221575-1154240066.png)

- 有 1 条消息的未确认，符合预期~

## 3.10 生产者的发送确认

在 RabbitMQ 中，**默认**情况下，Producer 发送消息的方法，只保证将消息写入到 TCP Socket 中成功，并不保证消息发送到 RabbitMQ Broker 成功，并且持久化消息到磁盘成功。

也就是说，我们上述的示例，Producer 在发送消息都不是绝对可靠，是存在丢失消息的可能性。

不过不用担心，在 RabbitMQ 中，Producer 采用 Confirm 模式，实现发送消息的确认机制，以保证消息发送的可靠性。实现原理如下：

- 首先，Producer 通过调用 [`Channel#confirmSelect()`](https://github.com/rabbitmq/rabbitmq-java-client/blob/master/src/main/java/com/rabbitmq/client/Channel.java#L1278-L1283) 方法，将 Channel 设置为 Confirm 模式。
- 然后，在该 Channel 发送的消息时，需要先通过 [`Channel#getNextPublishSeqNo()`](https://github.com/rabbitmq/rabbitmq-java-client/blob/master/src/main/java/com/rabbitmq/client/Channel.java#L1285-L1290) 方法，给发送的消息分配一个唯一的 ID 编号(`seqNo` 从 1 开始递增)，再发送该消息给 RabbitMQ Broker 。
- 之后，RabbitMQ Broker 在接收到该消息，并被路由到相应的队列之后，会发送一个包含消息的唯一编号(`deliveryTag`)的确认给 Producer 。

通过 `seqNo` 编号，将 Producer 发送消息的“请求”，和 RabbitMQ Broker 确认消息的“响应”串联在一起。

通过这样的方式，Producer 就可以知道消息是否成功发送到 RabbitMQ Broker 之中，保证消息发送的可靠性。不过要注意，整个执行的过程实际是**异步**，需要我们调用 [`Channel#waitForConfirms()`](https://github.com/rabbitmq/rabbitmq-java-client/blob/master/src/main/java/com/rabbitmq/client/Channel.java#L1293-L1329) 方法，**同步**阻塞等待 RabbitMQ Broker 确认消息的“响应”。

也因此，Producer 采用 Confirm 模式时，有三种编程方式：

- 【同步】普通 Confirm 模式：Producer 每发送一条消息后，调用 `Channel#waitForConfirms()` 方法，等待服务器端 Confirm 。

- 【同步】批量 Confirm 模式：Producer 每发送一批消息后，调用`Channel#waitForConfirms()` 方法，等待服务器端 Confirm 。

  > 本质上，和「普通 Confirm 模式」是一样的。

- 【异步】异步 Confirm 模式：Producer 提供一个回调方法，RabbitMQ Broker 在 Confirm 了一条或者多条消息后，Producer 会回调这个方法。

😈 更多关于 Producer 的 Confirm 模式的内容，可以阅读如下的文章：

- [《Consumer Acknowledgements and Publisher Confirms》](https://www.rabbitmq.com/confirms.html) 的生产者部分的内容，对应中文翻译为 [《消费者应答（ACK）和发布者确认（Confirm）》](https://blog.bossma.cn/rabbitmq/consumer-ack-and-publisher-confirm/) 。
- [《RabbitMQ 之消息确认机制（事务 + Confirm）》](http://www.iocoder.cn/RabbitMQ/message-confirmation-mechanism-transaction-Confirm/?self) 文章的[「Confirm 模式」](https://www.iocoder.cn/Spring-Boot/RabbitMQ/#)小节。

在 Spring-AMQP 中，在 [ConfirmType](https://github.com/spring-projects/spring-amqp/blob/master/spring-rabbit/src/main/java/org/springframework/amqp/rabbit/connection/CachingConnectionFactory.java#L145-L167) 中，定义了三种消息确认的方式：



```java
// CachingConnectionFactory#ConfirmType.java

public enum ConfirmType {

	/**
	 * Use {@code RabbitTemplate#waitForConfirms()} (or {@code waitForConfirmsOrDie()}
	 * within scoped operations.
	 */
	SIMPLE, // 使用同步的 Confirm 模式

	/**
	 * Use with {@code CorrelationData} to correlate confirmations with sent
	 * messsages.
	 */
	CORRELATED, // 使用异步的 Confirm 模式

	/**
	 * Publisher confirms are disabled (default).
	 */
	NONE // 不使用 Confirm 模式

}
```



**在上述的示例中，我们都采用了 Spring-AMQP 默认的 `NONE` 模式**。下面，我们来搭建两个示例：

- 在[「14.1 同步 Confirm 模式」](https://www.iocoder.cn/Spring-Boot/RabbitMQ/#) 中，我们会使用 `SIMPLE` 类型，实现同步的 Confirm 模式。
- 在[「14.2 异步 Confirm 模式」](https://www.iocoder.cn/Spring-Boot/RabbitMQ/#) 中，我们会使用 `CORRELATED` 类型，使用异步的 Confirm 模式。

### 3.10.1 同步 Confirm 模式

在本小节中，我们会使用 `ConfirmType.SIMPLE` 类型，实现同步的 Confirm 模式。

要注意，这里的**同步**，指的是我们通过调用 [`Channel#waitForConfirms()`](https://github.com/rabbitmq/rabbitmq-java-client/blob/master/src/main/java/com/rabbitmq/client/Channel.java#L1293-L1329) 方法，**同步**阻塞等待 RabbitMQ Broker 确认消息的“响应”

> 配置文件:
>
> ```yml
> spring:
>   # RabbitMQ 配置项，对应 RabbitProperties 配置类
>   rabbitmq:
>     host: 127.0.0.1 # RabbitMQ 服务的地址
>     port: 5672 # RabbitMQ 服务的端口
>     username: guest # RabbitMQ 服务的账号
>     password: guest # RabbitMQ 服务的密码
>     publisher-confirm-type: simple # 设置 Confirm 类型为 SIMPLE 。
> ```
>
> - 在该类型下，Spring-AMQP 在创建完 RabbitMQ Channel 之后，会**自动**调用 [`Channel#confirmSelect()`](https://github.com/rabbitmq/rabbitmq-java-client/blob/master/src/main/java/com/rabbitmq/client/Channel.java#L1278-L1283) 方法，将 Channel 设置为 Confirm 模式。
>
> 代码地址:[learning/rabbitmq/rabbitmq-springboot-confirm at master · JiuYou2020/learning (github.com)](https://github.com/JiuYou2020/learning/tree/master/rabbitmq/rabbitmq-springboot-confirm)



**说一下`producer`类**

```Java
@Component
@Slf4j
public class DemoProducer {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    public void syncSend(Integer id) {
        // 创建 DemoMessage 消息
        DemoMessage message = new DemoMessage();
        message.setId(id);
        // 同步发送消息
        rabbitTemplate.invoke(
                operations -> {
                    // 同步发送消息
                    operations.convertAndSend(DemoMessage.EXCHANGE, DemoMessage.ROUTING_KEY, message);
                    log.info("[doInRabbit][发送消息完成]");
                    // 等待确认
                    operations.waitForConfirms(0); // timeout 参数，如果传递 0 ，表示无限等待
                    log.info("[doInRabbit][等待 Confirm 完成]");
                    return null;
                },
                (deliveryTag, multiple) -> log.info("[handle][Confirm 成功]"),
                (deliveryTag, multiple) -> log.info("[handle][Confirm 失败]"));
    }
}
```

- 在 RabbitTemplate 提供的 API 方法中，如果 Producer 要使用同步的 Confirm 模式，需要调用 `#invoke(action, acks, nacks)` 方法。代码如下：

  ```java
  // RabbitOperations.java
  // RabbitTemplate 实现了 RabbitOperations 接口
  
  /**
   * Invoke operations on the same channel.
   * If callbacks are needed, both callbacks must be supplied.
   * @param action the callback.
   * @param acks a confirm callback for acks.
   * @param nacks a confirm callback for nacks.
   * @param <T> the return type.
   * @return the result of the action method.
   * @since 2.1
   */
  @Nullable
  <T> T invoke(OperationsCallback<T> action, @Nullable com.rabbitmq.client.ConfirmCallback acks,
  		@Nullable com.rabbitmq.client.ConfirmCallback nacks);
  ```

  

  - 因为 Confirm 模式需要基于**相同** Channel ，所以我们需要使用该方法。
  - 在方法参数 `action` 中，我们可以自定义操作。
  - 在方法参数 `acks` 中，定义接收到 RabbitMQ Broker 的成功“响应”时的成回调。
  - 在方法参数 `nacks` 中，定义接收到 RabbitMQ Broker 的失败“响应”时的成回调。

  > - 当消息最终得到确认之后，生产者应用便可以通过回调方法来处理该确认消息。
  > - 如果 RabbitMQ 因为自身内部错误导致消息丢失，就会发送一条 nack 消息，生产者应用程序同样可以在回调方法中处理该 nack 消息。

### 3.10.2 异步 Confirm 模式

在本小节中，我们会使用 `ConfirmType.SIMPLE` 类型，实现异步的 Confirm 模式。

> 配置文件:
>
> ```yml
> spring:
>   # RabbitMQ 配置项，对应 RabbitProperties 配置类
>   rabbitmq:
>     host: 127.0.0.1 # RabbitMQ 服务的地址
>     port: 5672 # RabbitMQ 服务的端口
>     username: guest # RabbitMQ 服务的账号
>     password: guest # RabbitMQ 服务的密码
>     publisher-confirm-type: correlated # 设置 Confirm 类型为 CORRELATED 。
> ```
>
> - 我们通过**新增** `spring.rabbitmq.publisher-confirm-type=correlated` 配置项，设置 Confirm 类型为 `ConfirmType.CORRELATED` 。
> - 在该类型下，Spring-AMQP 在创建完 RabbitMQ Channel 之后，也会**自动**调用 [`Channel#confirmSelect()`](https://github.com/rabbitmq/rabbitmq-java-client/blob/master/src/main/java/com/rabbitmq/client/Channel.java#L1278-L1283) 方法，将 Channel 设置为 Confirm 模式。
>
> 代码地址:[learning/rabbitmq/rabbitmq-springboot-confirm-async at master · JiuYou2020/learning (github.com)](https://github.com/JiuYou2020/learning/tree/master/rabbitmq/rabbitmq-springboot-confirm-async)

### 3.10.3  ReturnCallback

当 Producer 成功发送消息到 RabbitMQ Broker 时，但是在通过 Exchange 进行**匹配不到** Queue 时，Broker 会将该消息回退给 Producer。

>  代码地址同[3.10.2 异步 Confirm 模式](# 3.10.2 异步 Confirm 模式)

## 3.11. 消费异常处理器

在 Spring-AMQP 中可以自定义消费异常时的处理器。目前有两个接口，可以实现对 Consumer 消费异常的处理：

- [`org.springframework.amqp.rabbit.listener.api.RabbitListenerErrorHandler`](https://github.com/spring-projects/spring-amqp/blob/master/spring-rabbit/src/main/java/org/springframework/amqp/rabbit/listener/api/RabbitListenerErrorHandler.java) 接口
- [`org.springframework.util.ErrorHandler`](https://github.com/spring-projects/spring-framework/blob/master/spring-core/src/main/java/org/springframework/util/ErrorHandler.java) 接口

下面，我们来搭建一个 RabbitListenerErrorHandler 和 ErrorHandler 的使用示例。

> 配置文件同[3.1 快速入门](# 3.1 快速入门)
>
> 代码地址:[learning/rabbitmq/rabbitmq-springboot-error-handler at master · JiuYou2020/learning (github.com)](https://github.com/JiuYou2020/learning/tree/master/rabbitmq/rabbitmq-springboot-error-handler)

在执行**顺序**上，RabbitListenerErrorHandler **先**于 ErrorHandler 执行。不过这个需要建立在一个前提上，RabbitListenerErrorHandler 需要继续抛出异常。

另外，RabbitListenerErrorHandler 需要每个 `@RabbitListener` 注解上，需要每个手动设置下 `errorHandler` 属性。而 ErrorHandler 是相对全局的，所有 SimpleRabbitListenerContainerFactory 创建的 SimpleMessageListenerContainer 都会生效。

具体选择 ErrorHandler 还是 RabbitLoggingErrorHandler ，我暂时没有答案。不过个人感觉，如果不需要对 Consumer 消费的结果（包括成功和异常）做进一步处理，还是考虑 ErrorHandler 即可。在 ErrorHandler 中，我们可以通过判断 Throwable 异常是不是 ListenerExecutionFailedException 异常，从而拿到 Message 相关的信息。