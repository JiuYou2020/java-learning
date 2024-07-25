# 1. 概述

[RabbitMQ](https://www.rabbitmq.com/) 是由 LShift
提供的一个 [Advanced RpcMessage Queuing Protocol (AMQP)](https://zh.wikipedia.org/zh-hans/高级消息队列协议)
的开源实现，由以高性能、健壮以及可伸缩性出名的 [Erlang](https://www.erlang.org/) 写成，因此也是继承了这些优点。

> FROM [《维基百科 —— RabbitMQ》](https://zh.wikipedia.org/wiki/RabbitMQ)
>
> Rabbit 科技有限公司开发了 RabbitMQ ，并提供对其的支持。起初，Rabbit 科技是 LSHIFT 和 CohesiveFT 在 2007 年成立的合资企业，2010
> 年 4 月 被 VMware 的旗下的 SpringSource 收购。RabbitMQ 在 2013 年 5 月成为 GoPivotal 的一部分。

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

- [《RabbitMQ 单机多实例配置》](http://www.iocoder.cn/RabbitMQ/RabbitMQ-single-machine-multi-instance-configuration/?self)
  ，适合本地测试。
- [《RabbitMQ 的安装及集群搭建方法》](http://www.iocoder.cn/RabbitMQ/RabbitMQ-installation-and-cluster-setup-methods/?self)
  的[「2. RabbitMQ 集群搭建方法」](https://www.iocoder.cn/RabbitMQ/install/?self#) 小节，适合生产环境。

>
docker修改rabbitmq密码,参考[Docker修改MySQL,RabbitMQ,Redis密码_docker修改redis密码_JiuYou2020的博客-CSDN博客](https://blog.csdn.net/qq_62656514/article/details/128602307)

# 3. RabbitMQ-SpringBoot

在 Spring 生态中，提供了 [Spring-AMQP](https://spring.io/projects/spring-amqp) 项目，让我们更简便的使用 AMQP 。其官网介绍如下：

- The Spring AMQP project applies core Spring concepts to the development of AMQP-based messaging solutions. Spring-AMQP
  项目将 Spring 核心概念应用于基于 AMQP 的消息传递解决方案的开发。

- It provides a "template" as a high-level abstraction for sending and receiving messages.

  它提供了一个“模板”作为发送消息的高级抽象。

- It also provides support for RpcMessage-driven POJOs with a "listener container".

  它还通过“侦听器容器”为消息驱动的 POJO 提供支持。

- These libraries facilitate management of AMQP resources while promoting the use of dependency injection and
  declarative configuration.

  这些库促进 AMQP 资源的管理，同时促进使用依赖注入和声明性配置。

- In all of these cases, you will see similarities to the JMS support in the Spring Framework.

  在所有这些情况下，您将看到与 Spring 框架中的 JMS 支持的相似之处。

- The project consists of two parts; spring-amqp is the base abstraction, and spring-rabbit is the RabbitMQ
  implementation. 该项目包括两个部分：

    - [`spring-amqp`](https://mvnrepository.com/artifact/org.springframework.amqp/spring-amqp) 是 AMQP 的基础抽象。

    - [`spring-rabbit`](https://mvnrepository.com/artifact/org.springframework.amqp/spring-rabbit) 是基于 RabbitMQ 对
      AMQP 的具体实现。

> Features(功能特性)
>
> - Listener container for asynchronous processing of inbound messages 监听器容器：异步处理接收到的消息
> - [RabbitTemplate](https://github.com/spring-projects/spring-amqp/blob/master/spring-rabbit/src/main/java/org/springframework/amqp/rabbit/core/RabbitTemplate.java)
    for sending and receiving messages RabbitTemplate：发送和接收消息
> - [RabbitAdmin](https://github.com/spring-projects/spring-amqp/blob/master/spring-rabbit/src/main/java/org/springframework/amqp/rabbit/core/RabbitAdmin.java)
    for automatically declaring queues, exchanges and bindings RabbitAdmin：自动创建队列，交换器，绑定器。

在 [Spring-Boot](https://spring.io/projects/spring-boot) 项目中，提供了 AMQP 和 RabbitMQ
的自动化配置，所以我们仅需引入 [`spring-boot-starter-amqp`](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-amqp)
依赖，即可愉快的使用。

![image-20230910212647855](https://img2023.cnblogs.com/blog/3014862/202309/3014862-20230911212219151-1179361959.png)

- AMQP 里主要要说两个组件：Exchange 和 Queue ，绿色的 X 就是 Exchange ，红色的是 Queue ，这两者都在 Server 端，又称作 Broker
  ，这部分是 RabbitMQ 实现的。
- 而蓝色的则是客户端，通常有 Producer 和 Consumer 两种类型（角色）。

## 3.1 快速入门

>
代码地址:[learning/rabbitmq/rabbitmq-springboot-quickstart at master · JiuYou2020/learning (github.com)](https://github.com/JiuYou2020/learning/tree/master/rabbitmq/rabbitmq-springboot-quickstart)

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
- 总结来说，指定 Exchange + routing key ，有且仅会路由到至多一个 Queue 中。😈 极端情况下，如果没有匹配，消息就发送到“空气”中，不会进入任何
  Queue 中。

> 注：Queue 名字 `amqp.gen-S9b…` 和 `amqp.gen-Agl…` 自动生成的。

### 3.1.2 Topic Exchange

前面讲到 Direct Exchange路由规则，是完全匹配 binding key 与routing key，但这种严格的匹配方式在很多情况下不能满足实际业务需求。

Topic Exchange 在匹配规则上进行了扩展，它与 Direct 类型的Exchange **相似**，也是将消息路由到 binding key 与 routing key
相匹配的 Queue 中，但这里的匹配规则有些不同，它约定：

- routing key 为一个句点号 `"."` 分隔的字符串。我们将被句点号`"."`分隔开的每一段独立的字符串称为一个单词，例如 "
  stock.usd.nyse"、"nyse.vmw"、"quick.orange.rabbit"
- binding key 与 routing key 一样也是句点号 `"."` 分隔的字符串。
- binding key 中可以存在两种特殊字符 `"*"` 与 `"#"`，用于做模糊匹配。其中 `"*"` 用于匹配一个单词，`"#"` 用于匹配多个单词（可以是零个）。

![image-20230910233241039](https://img2023.cnblogs.com/blog/3014862/202309/3014862-20230911212220310-1186608925.png)

- `routingKey="quick.orange.rabbit"` 的消息会同时路由到 Q1 与 Q2 。
- `routingKey="lazy.orange.fox"` 的消息会路由到 Q1 。
- `routingKey="lazy.brown.fox"` 的消息会路由到 Q2 。
- `routingKey="lazy.pink.rabbit"` 的消息会路由到Q2（只会投递给 Q2 一次，虽然这个 routingKey 与 Q2 的两个 bindingKey 都匹配）。
- `routingKey="quick.brown.fox"`、`routingKey="orange"`、`routingKey="quick.orange.male.rabbit"` 的消息将会被丢弃，因为它们没有匹配任何
  bindingKey 。

### 3.1.3 Fanout Exchange

> 也称为Publish/Subscribe模式

Fanout Exchange 路由规则非常简单，它会把所有发送到该 Exchange 的消息路由到所有与它绑定的 Queue 中。如下图：

![image-20230910233431998](https://img2023.cnblogs.com/blog/3014862/202309/3014862-20230911212220857-1262381795.png)

- 生产者（P）发送到 Exchange（X）的所有消息都会路由到图中的两个 Queue，并最终被两个消费者（C1 与 C2）消费。
- 总结来说，指定 Exchange ，会路由到**多个**绑定的 Queue 中。

## 3.2 批量发送消息

在一些业务场景下，我们希望使用 Producer 批量发送消息，提高发送性能。RocketMQ 是提供了一个可以批量发送多条消息的 API 。而
Spring-AMQP
提供的批量发送消息，它提供了一个 [MessageBatch](https://github.com/spring-projects/spring-amqp/blob/master/spring-rabbit/src/main/java/org/springframework/amqp/rabbit/batch/MessageBatch.java)
消息收集器，将发送给**相同 Exchange + RoutingKey 的消息们**，“**偷偷**
”收集在一起，当满足条件时候，一次性批量发送提交给 `RabbitMQ Broker` 。

Spring-AMQP
通过 [BatchingRabbitTemplate](https://github.com/spring-projects/spring-amqp/blob/master/spring-rabbit/src/main/java/org/springframework/amqp/rabbit/core/BatchingRabbitTemplate.java)
提供批量发送消息的功能。如下是三个条件，满足**任一**即会批量发送：

- 【数量】`batchSize` ：超过收集的消息数量的最大条数。
- 【空间】`bufferLimit` ：超过收集的消息占用的最大内存。
- 【时间】`timeout` ：超过收集的时间的最大等待时长，单位：毫秒。😈 不过要注意，这里的超时开始计时的时间，是**以最后一次发送时间为起点
  **。也就说，每调用一次发送消息，都以当前时刻开始计时，重新到达 `timeout` 毫秒才算超时。

另外，BatchingRabbitTemplate 提供的批量发送消息的能力**比较弱**。对于同一个 BatchingRabbitTemplate 对象来说，*
*同一时刻只能有一个批次(保证 Exchange + RoutingKey 相同)**，否则会报错。

> 配置文件同[3.1 快速入门](# 3.1 快速入门)
>
>
代码地址:[learning/rabbitmq/rabbitmq-springboot-batch-sending at master · JiuYou2020/learning (github.com)](https://github.com/JiuYou2020/learning/tree/master/rabbitmq/rabbitmq-springboot-batch-sending)

我们已经实现批量发送消息到 RabbitMQ Broker 中。那么，我们来思考一个问题，这批消息在 RabbitMQ Broker 到底是存储**一条**消息，还是
**多条**消息？

- 如果小伙伴使用过 Kafka、RocketMQ 这两个消息队列，那么判断肯定会是**多条**消息。

😭 实际上，RabbitMQ Broker 存储的是**一条**消息。又或者说，**RabbitMQ 并没有提供批量接收消息的 API 接口**。

那么，为什么我们在**批量发送消息**能够实现呢？答案是批量发送消息是 Spring-AMQP
的 [SimpleBatchingStrategy](https://github.com/spring-projects/spring-amqp/blob/master/spring-rabbit/src/main/java/org/springframework/amqp/rabbit/batch/SimpleBatchingStrategy.java)
所封装提供：

- 在 Producer 最终批量发送消息时，SimpleBatchingStrategy
  会通过 [`#assembleMessage()`](https://github.com/spring-projects/spring-amqp/blob/master/spring-rabbit/src/main/java/org/springframework/amqp/rabbit/batch/SimpleBatchingStrategy.java#L141-L156)
  方法，将批量发送的**多条**消息**组装**成一条“批量”消息，然后进行发送。
- 在 Consumer
  拉取到消息时，会根据[`#canDebatch(MessageProperties properties)`](https://github.com/spring-projects/spring-amqp/blob/master/spring-rabbit/src/main/java/org/springframework/amqp/rabbit/batch/SimpleBatchingStrategy.java#L158-L163)
  方法，判断该消息是否为一条“批量”消息？如果是，则调用[`# deBatch(RpcMessage message, Consumer fragmentConsumer)`](https://github.com/spring-projects/spring-amqp/blob/master/spring-rabbit/src/main/java/org/springframework/amqp/rabbit/batch/SimpleBatchingStrategy.java#L165-L194)
  方法，将一条“批量”消息**拆开**，变成**多条**消息。

## 3.3 批量消费消息

### 3.3.1 基于批量发送的批量消费

在 SimpleBatchingStrategy 将一条“批量”消息拆开，变成多条消息后，直接**批量**交给 Consumer 进行消费处理。

> 配置文件同[3.1 快速入门](# 3.1 快速入门)
>
>
代码地址:[learning/rabbitmq/rabbitmq-springboot-batch-consumer at master · JiuYou2020/learning (github.com)](https://github.com/JiuYou2020/learning/tree/master/rabbitmq/rabbitmq-springboot-batch-consumer)

### 3.3.2 基于阻塞的批量消费

刚刚已经学会了依赖于批量发送的批量消费模式,但有点过于苛刻,所以，Spring-AMQP 提供了第二种批量消费消息的方式。

其实现方式是，阻塞等待最多 `receiveTimeout` 秒，拉取 `batchSize` 条消息，进行批量消费。

- 如果在 `receiveTimeout` 秒内已经成功拉取到 `batchSize` 条消息，则直接进行批量消费消息。
- 如果在 `receiveTimeout` 秒还没拉取到 `batchSize` 条消息，不再等待，而是进行批量消费消息。

不过 Spring-AMQP 的阻塞等待时长 `receiveTimeout` 的设计有点“神奇”。

- 它代表的是，每次拉取一条消息，最多阻塞等待 `receiveTimeout` 时长。如果等待不到下一条消息，则进入已获取到的消息的批量消费。😈
  也就是说，极端情况下，可能等待 `receiveTimeout * batchSize` 时长，才会进行批量消费。
-
感兴趣的朋友，可以点击 [`SimpleMessageListenerContainer#doReceiveAndExecute(BlockingQueueConsumer consumer)`](https://github.com/spring-projects/spring-amqp/blob/master/spring-rabbit/src/main/java/org/springframework/amqp/rabbit/listener/SimpleMessageListenerContainer.java#L922)
方法，简单阅读源码，即可快速理解。

> 配置文件同[3.1 快速入门](# 3.1 快速入门)
>
>
代码地址:[learning/rabbitmq/rabbitmq-springboot-batch-consumer2 at master · JiuYou2020/learning (github.com)](https://github.com/JiuYou2020/learning/tree/master/rabbitmq/rabbitmq-springboot-batch-consumer2)

## 3.4 消费重试

在开始本小节之前，小伙伴首先要对 RabbitMQ 的[死信队列](https://www.rabbitmq.com/dlx.html)的机制，有一定的了解。

在消息**消费失败**的时候，Spring-AMQP 会通过**消费重试**机制，重新投递该消息给 Consumer ，让 Consumer 有机会重新消费消息，实现消费成功。

当然，Spring-AMQP 并不会无限重新投递消息给 Consumer 重新消费，而是在默认情况下，达到 N 次重试次数时，Consumer
还是消费失败时，该消息就会进入到**死信队列**。后续，我们可以通过对死信队列中的消息进行重发，来使得消费者实例再次进行消费。

- 在`RocketMQ`中,消费重试和死信队列，是 RocketMQ 自带的功能。
- 而在 RabbitMQ 中，消费重试是由 Spring-AMQP 所封装提供的，死信队列是 RabbitMQ 自带的功能。

那么消费失败到达最大次数的消息，是怎么进入到死信队列的呢？Spring-AMQP
在消息到达最大消费次数的时候，会将该消息进行否定(`basic.nack`)，并且 `requeue=false` ，这样后续就可以利用 RabbitMQ
的[死信队列](https://www.rabbitmq.com/dlx.html)的机制，将该消息转发到死信队列。

另外，每条消息的失败重试，是可以配置一定的**间隔时间**。具体，我们在示例的代码中，来进行具体的解释。

>
代码地址:[learning/rabbitmq/rabbitmq-springboot-consumer-retry at master · JiuYou2020/learning (github.com)](https://github.com/JiuYou2020/learning/tree/master/rabbitmq/rabbitmq-springboot-consumer-retry)

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

- 相比之前的配置文件来说，我们通过**新增** `spring.rabbitmq.simple.retry.enable=true` 配置项，来开启 Spring-AMQP
  的消费重试的功能。同时，通过**新增** `max-attempts` 和 `initial-interval` 配置项，设置重试次数和间隔。

  > `max-attempts` 配置项要注意，是一条消息一共尝试消费总共 `max-attempts` 次，包括首次的正常消费。

- 另外，可以通过添加 `spring.rabbitmq.listener.simple.retry.multiplier` 配置项来实现**递乘**
  的时间间隔，添加 `spring.rabbitmq.listener.simple.retry.max-interval` 配置项来实现**最大**的时间间隔。

在 Spring-AMQP 的消费重试机制中，在消费失败到达最大次数后，会**自动**
抛出 [AmqpRejectAndDontRequeueException](https://github.com/spring-projects/spring-amqp/blob/master/spring-amqp/src/main/java/org/springframework/amqp/AmqpRejectAndDontRequeueException.java)
异常，从而结束该消息的消费重试。这意味着什么呢？如果我们在消费消息的逻辑中，**主动**抛出 AmqpRejectAndDontRequeueException
异常，也能结束该消息的消费重试。😈 结束的方式，Spring-AMQP 是通过我们在上文中提到的 `basic.nack` + `requeue=false`
，从而实现转发该消息到死信队列中。

另外，默认情况下，`spring.rabbitmq.simple.retry.enable=false` ，关闭 Spring-AMQP 的消费重试功能。但是实际上，消费发生异常的消息，还是会一直
**重新消费**。这是为什么呢？Spring-AMQP 会将该消息通过 `basic.nack` + `requeue=true` ，重新投递回**原队列的尾巴**
。如此，我们便会不断拉取到该消息，不断“重试”消费该消息。当然在这种情况下，我们一样可以**主动**抛出
AmqpRejectAndDontRequeueException 异常，也能结束该消息的消费重试。😈 结束的方式，Spring-AMQP
也是通过我们在上文中提到的 `basic.nack` + `requeue=false` ，从而实现转发该消息到死信队列中。

> 简而言之,Spring AMQP的重试机制默认是关闭的,但仍会发生消费重试,这是通过requeue=true实现的。如果想完全停止重试,需要主动抛出特定异常来拒绝重新入队。

这里，我们再来简单说说 Spring-AMQP 是怎么提供**消费重试**的功能的。

- Spring-AMQP 基于 [spring-retry](https://github.com/spring-projects/spring-retry)
  项目提供的 [RetryTemplate](https://github.com/spring-projects/spring-retry/blob/master/src/main/java/org/springframework/retry/support/RetryTemplate.java)
  ，实现重试功能。Spring-AMQP 在获取到消息时，会交给 RetryTemplate 来调用消费者 Consumer 的监听器 Listener(就是我们实现的)
  ，实现该消息的**多次**消费重试。

- 在该消息的**每次消费失败**后，RetryTemplate
  会通过 [BackOffPolicy](https://github.com/spring-projects/spring-retry/blob/master/src/main/java/org/springframework/retry/backoff/BackOffPolicy.java)
  来进行计算，该消息的**下一次重新消费的时间**，通过 `Thread#sleep(...)` 方法，实现重新消费的时间间隔。到达时间间隔后，RetryTemplate
  又会调用消费者 Consumer 的监听器 Listener 来消费该消息。

- 当该消息的重试消费到达**上限**后，RetryTemplate
  会调用 [MethodInvocationRecoverer](https://github.com/spring-projects/spring-retry/blob/master/src/main/java/org/springframework/retry/interceptor/MethodInvocationRecoverer.java)
  回调来实现恢复。而 Spring-AMQP
  自定义实现了 [RejectAndDontRequeueRecoverer](https://github.com/spring-projects/spring-amqp/blob/master/spring-rabbit/src/main/java/org/springframework/amqp/rabbit/retry/RejectAndDontRequeueRecoverer.java)
  来**自动**抛出 AmqpRejectAndDontRequeueException 异常，从而结束该消息的消费重试。😈 结束的方式，Spring-AMQP
  是通过我们在上文中提到的 `basic.nack` + `requeue=false` ，从而实现转发该消息到死信队列中。

- 有一点需要注意，Spring-AMQP 提供的消费重试的**计数**是**客户端**级别的，重启 JVM 应用后，计数是会丢失的。所以，如果想要计数进行持久化，需要自己重新实现下。

  > 😈 RocketMQ 提供的消费重试的计数，目前是**服务端**级别，已经进行持久化。

## 3.5 定时消息

在上小节中，我们看到 Spring-AMQP 基于 RabbitMQ 提供的**死信队列**，通过 `basic.nack` + `requeue=false`
的方式，将重试消费到达上限次数的消息，投递到死信队列中。

本小节，我们还是基于 RabbitMQ 的**死信队列**，实现**定时消息**的功能。RabbitMQ
提供了过期时间 [TTL](https://www.rabbitmq.com/ttl.html) 机制，可以设置消息在队列中的存活时长。在消息到达过期时间时，会从当前队列中删除，并被
RabbitMQ 自动转发到对应的死信队列中。

那么，如果我们创建消费者 Consumer ，来消费该死信队列，是不是就实现了**延迟队列**的效果。😈 如此，我们便实现了定时消息的功能。

> 配置文件:同[3.4 消费重试](# 3.4 消费重试)
>
>
代码地址:[learning/rabbitmq/rabbitmq-springboot-timed-message at master · JiuYou2020/learning (github.com)](https://github.com/JiuYou2020/learning/tree/master/rabbitmq/rabbitmq-springboot-timed-message)

## 3.6 消息模式

在消息队列中，有两种经典的消息模式：「点对点」和「发布订阅」。可以看看[《消息队列两种模式：点对点与发布订阅》](http://www.iocoder.cn/Fight/There-are-two-modes-of-message-queuing-point-to-point-and-publish-subscription/?self)
文章。

如果小伙伴有使用过 RocketMQ 或者 Kafka 消息队列，可能比较习惯的叫法是：

> **集群消费（Clustering）**：对应「点对点」 集群消费模式下，相同 Consumer Group 的每个 Consumer 实例平均分摊消息。
>
> **广播消费（Broadcasting）**：对应「发布订阅」 广播消费模式下，相同 Consumer Group 的每个 Consumer 实例都接收全量的消息。

下文我们统一采用集群消费和广播消费叫法。

### 3.6.1 集群消费

> 每个消息只消费一次

在 RabbitMQ 中，如果多个 Consumer 订阅相同的 Queue ，那么每一条消息有且仅会被一个 Consumer 所消费。这个特性，就为我们实现集群消费提供了基础。

在本示例中，我们会把一个 Queue 作为一个 Consumer Group ，同时创建消费该 Queue 的 Consumer 。这样，在我们启动多个 JVM
进程时，就会有多个 Consumer 消费该 Queue ，从而实现集群消费的效果。

> 配置文件同[3.1 快速入门](# 3.1 快速入门)
>
>
代码地址:[learning/rabbitmq/rabbitmq-springboot-message-model at master · JiuYou2020/learning (github.com)](https://github.com/JiuYou2020/learning/tree/master/rabbitmq/rabbitmq-springboot-message-model)

关于使用的Exchange是Topic类型,为什么?

- 为什么不选择 Exchange 类型是 **Direct** 呢？考虑到集群消费的模式，会存在多 Consumer Group 消费的情况，显然我们要支持一条消息投递到多个
  Queue 中，所以 Direct Exchange 基本就被排除了。

- 为什么不选择 Exchange 类型是 **Fanout** 或者 **Headers** 呢？实际是可以的，看了大佬(
  didi) [Spring Cloud Stream RabbitMQ](https://github.com/spring-cloud/spring-cloud-stream-binder-rabbit)
  是怎么实现的。得知答案是[默认](https://raw.githubusercontent.com/spring-cloud/spring-cloud-stream-binder-rabbit/master/docs/src/main/asciidoc/images/rabbit-binder.png)
  是使用 Topic Exchange 的

### 3.6.2 广播消费

> 消息可能会被消费多次

在[3.6.1 集群消费](# 3.6.1 集群消费)中，我们通过“在 RabbitMQ 中，如果多个 Consumer 订阅相同的 Queue ，那么每一条消息有且仅会被一个
Consumer 所消费”特性，来实现了集群消费。但是，在实现广播消费时，这个特性恰恰成为了一种阻碍。

不过机智的我们，我们可以通过给每个 Consumer 创建一个其**独有** Queue ，从而保证都能接收到全量的消息。同时，RabbitMQ
支持队列的自动删除，所以我们可以在 Consumer 关闭的时候，通过该功能删除其**独有**的 Queue 。

## 3.7 并发消费

在上述的示例中，我们配置的每一个 Spring-AMQP `@RabbitListener` ，都是**串行**消费的。显然，这在监听的 Queue
每秒消息量比较大的时候，会导致消费不及时，导致消息积压的问题。

虽然说，我们可以通过启动多个 JVM 进程，实现**多进程的并发消费**，从而加速消费的速度。但是问题是，否能够实现**多线程**
的并发消费呢？答案是**有**。

在 `@RabbitListener` 注解中，有 `concurrency` 属性，它可以指定并发消费的线程数。例如说，如果设置 `concurrency=4`
时，Spring-AMQP 就会为**该** `@RabbitListener` 创建 4 个线程，进行并发消费。

考虑到让小伙伴能够更好的理解 `concurrency` 属性，我们来简单说说 Spring-AMQP 在这块的实现方式。我们来举个例子：

- 首先，我们来创建一个 Queue 为 `"DEMO"` 。
- 然后，我们创建一个 Demo9Consumer 类，并在其消费方法上，添加 `@RabbitListener(concurrency=2)` 注解。
- 再然后，我们启动项目。Spring-AMQP 会根据 `@RabbitListener(concurrency=2)` 注解，创建 **2** 个 RabbitMQ Consumer 。注意噢，是
  **2** 个 RabbitMQ Consumer 呢！！！后续，每个 RabbitMQ Consumer 会被**单独**分配到一个线程中，进行拉取消息，消费消息。

酱紫讲解一下，小伙伴对 Spring-AMQP 实现**多线程**的并发消费的机制，是否理解了。

>
代码地址:[learning/rabbitmq/rabbitmq-springboot-concurrency at master · JiuYou2020/learning (github.com)](https://github.com/JiuYou2020/learning/tree/master/rabbitmq/rabbitmq-springboot-concurrency)

**配置文件:**

在开始看具体的应用配置文件之前，我们先来了了解下 Spring-AMQP
的两个 [ContainerType](https://github.com/spring-projects/spring-boot/blob/master/spring-boot-project/spring-boot-autoconfigure/src/main/java/org/springframework/boot/autoconfigure/amqp/RabbitProperties.java#L566-L579)
容器类型，枚举如下：

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

① 第一种类型，`SIMPLE`
对应 [SimpleMessageListenerContainer](https://github.com/spring-projects/spring-amqp/blob/master/spring-rabbit/src/main/java/org/springframework/amqp/rabbit/listener/SimpleMessageListenerContainer.java)
消息监听器容器。它一共有两**类**线程：

- Consumer 线程，负责从 RabbitMQ Broker 获取 Queue 中的消息，存储到**内存中**
  的 [BlockingQueue](https://github.com/openjdk-mirror/jdk7u-jdk/blob/master/src/share/classes/java/util/concurrent/BlockingQueue.java)
  阻塞队列中。
- Listener 线程，负责从**内存中**的 BlockingQueue 获取消息，进行消费逻辑。

注意，每一个 Consumer 线程，对应一个 RabbitMQ Consumer ，对应一个 Listener 线程。也就是说，它们三者是**一一对应**的。

② 第二种类型，`DIRECT`
对应 [DirectMessageListenerContainer](https://github.com/spring-projects/spring-amqp/blob/master/spring-rabbit/src/main/java/org/springframework/amqp/rabbit/listener/DirectMessageListenerContainer.java)
消息监听器容器。它只有**一类**线程，即做 `SIMPLE` 的 Consumer 线程的工作，也做 `SIMPLE` 的 Listener 线程工作。

注意，因为只有**一类**线程，所以它要么正在获取消息，要么正在消费消息，也就是**串行**的。

🔥 默认情况下，Spring-AMQP 选择使用第一种类型，即 `SIMPLE` 容器类型。

下面，让我们一起看看 [`application.yaml`](https://github.com/YunaiV/SpringBoot-Labs/blob/master/lab-04-rabbitmq/lab-04-rabbitmq-demo-concurrency/src/main/resources/application.yaml)
配置文件。配置如下：

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

要**注意**，是 `spring.rabbitmq.listener.simple.max-concurrency` 配置，是**限制**每个 `@RabbitListener` 的**允许**
配置的 `concurrency` 最大大小。如果超过，则会抛出 IllegalArgumentException
异常。在具体的消费类中，我们会看到 `@RabbitListener` 注解，有一个 `concurrency` 属性，可以自定义每个 `@RabbitListener`
的并发消费的线程数。

## 3.8 顺序消息

我们先来一起了解下顺序消息的**顺序消息**的定义：

- 普通顺序消息 ：Producer 将相关联的消息发送到相同的消息队列。
- 完全严格顺序 ：在【普通顺序消息】的基础上，Consumer 严格顺序消费。

那么，让我们来思考下，如果我们希望在 RabbitMQ 上，实现顺序消息需要做两个事情。

① **事情一**，我们需要保证 RabbitMQ Producer 发送相关联的消息发送到相同的 Queue 中。例如说，我们要发送用户信息发生变更的
RpcMessage ，那么如果我们希望使用顺序消息的情况下，可以将**用户编号**相同的消息发送到相同的 Queue 中。

② **事情二**，我们在有**且仅启动一个** Consumer 消费该队列，保证 Consumer 严格顺序消费。

不过如果这样做，会存在两个问题，我们逐个来看看。

① **问题一**，正如我们在[3.7 并发消费](# 3.7 并发消费)中提到，如果我们将消息仅仅投递到一个 Queue 中，并且采用单个 Consumer
**串行**消费，在监听的 Queue 每秒消息量比较大的时候，会导致消费不及时，导致消息积压的问题。

此时，我们有两种方案来解决：

- 方案一，在 Producer 端，将 Queue 拆成多个**子** Queue 。假设原先 Queue 是 `QUEUE_USER` ，那么我们就分拆成 `QUEUE_USER_00`
  至 `QUEUE_USER_..${N-1}` 这样 N 个队列，然后基于消息的用户编号取余，路由到对应的**子** Queue 中。
- 方案二，在 Consumer 端，将 Queue 拉取到的消息，将相关联的消息发送到**相同的线程**中来消费。例如说，还是 Queue
  是 `QUEUE_USER` 的例子，我们创建 N 个线程池大小为 1
  的 [ExecutorService](https://github.com/JetBrains/jdk8u_jdk/blob/master/src/share/classes/java/util/concurrent/ExecutorService.java)
  数组，然后基于消息的用户编号取余，提交到对应的 ExecutorService 中的单个线程来执行。

两个方案，并不冲突，可以结合使用。

② **问题二**，如果我们启动相同 Consumer 的**多个进程**，会导致相同 Queue 的消息被分配到多个 Consumer 进行消费，破坏
Consumer 严格顺序消费。

此时，我们有两种方案来解决：

- 方案一，引入 ZooKeeper 来协调，动态设置多个进程中的**相同的** Consumer 的开关，保证有且仅有一个 Consumer 开启对**同一个**
  Queue 的消费。
- 方案二，仅适用于【问题一】的【方案一】。还是引入 ZooKeeper 来协调，动态设置多个进程中的**相同的** Consumer 消费的 Queue
  的分配，保证有且仅有一个 Consumer 开启对**同一个** Queue 的消费。

下面，我们开始本小节的示例。

- 对于问题一，我们采用方案一。因为在 Spring-AMQP
  中，自己定义线程来消费消息，无法和现有的 [MessageListenerContainer](https://github.com/spring-projects/spring-framework/blob/master/spring-jms/src/main/java/org/springframework/jms/listener/MessageListenerContainer.java)
  的实现所结合，除非自定义一个 MessageListenerContainer 实现类。
- 对于问题二，因为实现起来比较复杂，暂时先不提供。

> 配置文件同[3.1 快速入门](# 3.1 快速入门)
>
>
代码地址:[learning/rabbitmq/rabbitmq-springboot-orderly at master · JiuYou2020/learning (github.com)](https://github.com/JiuYou2020/learning/tree/master/rabbitmq/rabbitmq-springboot-orderly)

在执行测试方法时发现:

- 相同编号的消息，被投递到相同的**子** Queue ，被相同的线程所消费。符合预期~

## 3.9 消费者的消息确认

在 RabbitMQ 中，Consumer 有两种消息确认的方式：

- 方式一，自动确认。
- 方式二，手动确认。

对于**自动确认**的方式，RabbitMQ Broker 只要将消息写入到 TCP Socket 中成功，就认为该消息投递成功，而无需 Consumer **手动确认
**。

对于**手动确认**的方式，RabbitMQ Broker 将消息发送给 Consumer 之后，由 Consumer **手动确认**之后，才任务消息投递成功。

实际场景下，因为自动确认存在可能**丢失消息**的情况，所以在对**可靠性**有要求的场景下，我们基本采用手动确认。当然，如果允许消息有一定的丢失，对
**性能**有更高的场景下，我们可以考虑采用自动确认。

😈 更多关于消费者的消息确认的内容，小伙伴可以阅读如下的文章：

- [《Consumer Acknowledgements and Publisher Confirms》](https://www.rabbitmq.com/confirms.html)
  的消费者部分的内容，对应中文翻译为 [《消费者应答（ACK）和发布者确认（Confirm）》](https://blog.bossma.cn/rabbitmq/consumer-ack-and-publisher-confirm/) 。
- [《RabbitMQ 之消息确认机制（事务 + Confirm）》](http://www.iocoder.cn/RabbitMQ/message-confirmation-mechanism-transaction-Confirm/?self)
  文章的[「消息确认（Consumer端）」](https://www.iocoder.cn/Spring-Boot/RabbitMQ/#)小节。

在 Spring-AMQP
中，在 [AcknowledgeMode](https://github.com/spring-projects/spring-amqp/blob/master/spring-amqp/src/main/java/org/springframework/amqp/core/AcknowledgeMode.java)
中，定义了三种消息确认的方式：

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
>
代码地址:[learning/rabbitmq/rabbitmq-springboot-ack at master · JiuYou2020/learning (github.com)](https://github.com/JiuYou2020/learning/tree/master/rabbitmq/rabbitmq-springboot-ack)



测试后,此时，如果我们使用 [RabbitMQ Management](https://static.iocoder.cn/7c5541295505e7a3be4ac7ab2882feeb)
来查看 `"DEMO"`
的该消费者：![ 的消费者列](https://img2023.cnblogs.com/blog/3014862/202309/3014862-20230911212221575-1154240066.png)

- 有 1 条消息的未确认，符合预期~

## 3.10 生产者的发送确认

在 RabbitMQ 中，**默认**情况下，Producer 发送消息的方法，只保证将消息写入到 TCP Socket 中成功，并不保证消息发送到 RabbitMQ
Broker 成功，并且持久化消息到磁盘成功。

也就是说，我们上述的示例，Producer 在发送消息都不是绝对可靠，是存在丢失消息的可能性。

不过不用担心，在 RabbitMQ 中，Producer 采用 Confirm 模式，实现发送消息的确认机制，以保证消息发送的可靠性。实现原理如下：

- 首先，Producer
  通过调用 [`Channel#confirmSelect()`](https://github.com/rabbitmq/rabbitmq-java-client/blob/master/src/main/java/com/rabbitmq/client/Channel.java#L1278-L1283)
  方法，将 Channel 设置为 Confirm 模式。
- 然后，在该 Channel
  发送的消息时，需要先通过 [`Channel#getNextPublishSeqNo()`](https://github.com/rabbitmq/rabbitmq-java-client/blob/master/src/main/java/com/rabbitmq/client/Channel.java#L1285-L1290)
  方法，给发送的消息分配一个唯一的 ID 编号(`seqNo` 从 1 开始递增)，再发送该消息给 RabbitMQ Broker 。
- 之后，RabbitMQ Broker 在接收到该消息，并被路由到相应的队列之后，会发送一个包含消息的唯一编号(`deliveryTag`)的确认给
  Producer 。

通过 `seqNo` 编号，将 Producer 发送消息的“请求”，和 RabbitMQ Broker 确认消息的“响应”串联在一起。

通过这样的方式，Producer 就可以知道消息是否成功发送到 RabbitMQ Broker 之中，保证消息发送的可靠性。不过要注意，整个执行的过程实际是
**异步**
，需要我们调用 [`Channel#waitForConfirms()`](https://github.com/rabbitmq/rabbitmq-java-client/blob/master/src/main/java/com/rabbitmq/client/Channel.java#L1293-L1329)
方法，**同步**阻塞等待 RabbitMQ Broker 确认消息的“响应”。

也因此，Producer 采用 Confirm 模式时，有三种编程方式：

- 【同步】普通 Confirm 模式：Producer 每发送一条消息后，调用 `Channel#waitForConfirms()` 方法，等待服务器端 Confirm 。

- 【同步】批量 Confirm 模式：Producer 每发送一批消息后，调用`Channel#waitForConfirms()` 方法，等待服务器端 Confirm 。

  > 本质上，和「普通 Confirm 模式」是一样的。

- 【异步】异步 Confirm 模式：Producer 提供一个回调方法，RabbitMQ Broker 在 Confirm 了一条或者多条消息后，Producer 会回调这个方法。

😈 更多关于 Producer 的 Confirm 模式的内容，可以阅读如下的文章：

- [《Consumer Acknowledgements and Publisher Confirms》](https://www.rabbitmq.com/confirms.html)
  的生产者部分的内容，对应中文翻译为 [《消费者应答（ACK）和发布者确认（Confirm）》](https://blog.bossma.cn/rabbitmq/consumer-ack-and-publisher-confirm/) 。
- [《RabbitMQ 之消息确认机制（事务 + Confirm）》](http://www.iocoder.cn/RabbitMQ/message-confirmation-mechanism-transaction-Confirm/?self)
  文章的[「Confirm 模式」](https://www.iocoder.cn/Spring-Boot/RabbitMQ/#)小节。

在 Spring-AMQP
中，在 [ConfirmType](https://github.com/spring-projects/spring-amqp/blob/master/spring-rabbit/src/main/java/org/springframework/amqp/rabbit/connection/CachingConnectionFactory.java#L145-L167)
中，定义了三种消息确认的方式：

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

- 在`「14.1 同步 Confirm 模式」` 中，我们会使用 `SIMPLE` 类型，实现同步的 Confirm 模式。
- 在`「14.2 异步 Confirm 模式」` 中，我们会使用 `CORRELATED` 类型，使用异步的 Confirm 模式。

### 3.10.1 同步 Confirm 模式

在本小节中，我们会使用 `ConfirmType.SIMPLE` 类型，实现同步的 Confirm 模式。

要注意，这里的**同步**
，指的是我们通过调用 [`Channel#waitForConfirms()`](https://github.com/rabbitmq/rabbitmq-java-client/blob/master/src/main/java/com/rabbitmq/client/Channel.java#L1293-L1329)
方法，**同步**阻塞等待 RabbitMQ Broker 确认消息的“响应”

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
> - 在该类型下，Spring-AMQP 在创建完 RabbitMQ Channel 之后，会**自动**
    调用 [`Channel#confirmSelect()`](https://github.com/rabbitmq/rabbitmq-java-client/blob/master/src/main/java/com/rabbitmq/client/Channel.java#L1278-L1283)
    方法，将 Channel 设置为 Confirm 模式。
>
>
代码地址:[learning/rabbitmq/rabbitmq-springboot-confirm at master · JiuYou2020/learning (github.com)](https://github.com/JiuYou2020/learning/tree/master/rabbitmq/rabbitmq-springboot-confirm)



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

- 在 RabbitTemplate 提供的 API 方法中，如果 Producer 要使用同步的 Confirm 模式，需要调用 `#invoke(action, acks, nacks)`
  方法。代码如下：

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
> - 我们通过**新增** `spring.rabbitmq.publisher-confirm-type=correlated` 配置项，设置 Confirm
    类型为 `ConfirmType.CORRELATED` 。
> - 在该类型下，Spring-AMQP 在创建完 RabbitMQ Channel 之后，也会**自动**
    调用 [`Channel#confirmSelect()`](https://github.com/rabbitmq/rabbitmq-java-client/blob/master/src/main/java/com/rabbitmq/client/Channel.java#L1278-L1283)
    方法，将 Channel 设置为 Confirm 模式。
>
>
代码地址:[learning/rabbitmq/rabbitmq-springboot-confirm-async at master · JiuYou2020/learning (github.com)](https://github.com/JiuYou2020/learning/tree/master/rabbitmq/rabbitmq-springboot-confirm-async)

### 3.10.3  ReturnCallback

当 Producer 成功发送消息到 RabbitMQ Broker 时，但是在通过 Exchange 进行**匹配不到** Queue 时，Broker 会将该消息回退给
Producer。

> 代码地址同[3.10.2 异步 Confirm 模式](# 3.10.2 异步 Confirm 模式)

## 3.11. 消费异常处理器

在 Spring-AMQP 中可以自定义消费异常时的处理器。目前有两个接口，可以实现对 Consumer 消费异常的处理：

- [`org.springframework.amqp.rabbit.listener.api.RabbitListenerErrorHandler`](https://github.com/spring-projects/spring-amqp/blob/master/spring-rabbit/src/main/java/org/springframework/amqp/rabbit/listener/api/RabbitListenerErrorHandler.java)
  接口
- [`org.springframework.util.ErrorHandler`](https://github.com/spring-projects/spring-framework/blob/master/spring-core/src/main/java/org/springframework/util/ErrorHandler.java)
  接口

下面，我们来搭建一个 RabbitListenerErrorHandler 和 ErrorHandler 的使用示例。

> 配置文件同[3.1 快速入门](# 3.1 快速入门)
>
>
代码地址:[learning/rabbitmq/rabbitmq-springboot-error-handler at master · JiuYou2020/learning (github.com)](https://github.com/JiuYou2020/learning/tree/master/rabbitmq/rabbitmq-springboot-error-handler)

在执行**顺序**上，RabbitListenerErrorHandler **先**于 ErrorHandler 执行。不过这个需要建立在一个前提上，RabbitListenerErrorHandler
需要继续抛出异常。

另外，RabbitListenerErrorHandler 需要每个 `@RabbitListener` 注解上，需要每个手动设置下 `errorHandler` 属性。而 ErrorHandler
是相对全局的，所有 SimpleRabbitListenerContainerFactory 创建的 SimpleMessageListenerContainer 都会生效。

具体选择 ErrorHandler 还是 RabbitLoggingErrorHandler ，我暂时没有答案。不过个人感觉，如果不需要对 Consumer
消费的结果（包括成功和异常）做进一步处理，还是考虑 ErrorHandler 即可。在 ErrorHandler 中，我们可以通过判断 Throwable 异常是不是
ListenerExecutionFailedException 异常，从而拿到 RpcMessage 相关的信息。

# 4. RabbitMQ-SpringCloud

## 4.1 概述

本文我们来学习 [Spring Cloud Stream RabbitMQ](https://github.com/spring-cloud/spring-cloud-stream-binder-rabbit)
组件，基于 [Spring Cloud Stream](https://github.com/spring-cloud/spring-cloud-stream) 的编程模型，接入 RabbitMQ
作为消息中间件，实现消息驱动的微服务。

> RabbitMQ 是一套开源（MPL）的消息队列服务软件，是由 LShift 提供的一个 Advanced RpcMessage Queuing Protocol (AMQP)
> 的开源实现，由以高性能、健壮以及可伸缩性出名的 Erlang 写成。

## 4.2 Spring Cloud Stream 介绍

[Spring Cloud Stream](https://github.com/spring-cloud/spring-cloud-stream) 是一个用于构建基于**消息**
的微服务应用框架，使用 [Spring Integration](https://www.oschina.net/p/spring+integration) 与 Broker 进行连接。

> 友情提示：可能有小伙伴对 Broker 不太了解，我们来简单解释下。
>
> 一般来说，消息队列中间件都有一个 **Broker Server**（代理服务器），消息中转角色，负责存储消息、转发消息。
>
> 例如说在 RocketMQ 中，Broker 负责接收从生产者发送来的消息并存储、同时为消费者的拉取请求作准备。另外，Broker
> 也存储消息相关的元数据，包括消费者组、消费进度偏移和主题和队列消息等。

Spring Cloud Stream 提供了消息中间件的**统一抽象**，推出了 publish-subscribe、consumer groups、partition 这些统一的概念。

Spring Cloud Stream 内部有两个概念：**Binder** 和 **Binding**。

1. *
   *[Binder](https://github.com/spring-cloud/spring-cloud-stream/blob/master/spring-cloud-stream/src/main/java/org/springframework/cloud/stream/binder/Binder.java)
   **，跟消息中间件集成的组件，用来创建对应的 Binding。各消息中间件都有自己的 Binder 具体实现。

```java
public interface Binder<T,
        C extends ConsumerProperties, // 消费者配置
        P extends ProducerProperties> { // 生产者配置

    // 创建消费者的 Binding
    Binding<T> bindConsumer(String name, String group, T inboundBindTarget, C consumerProperties);

    // 创建生产者的 Binding
    Binding<T> bindProducer(String name, T outboundBindTarget, P producerProperties);

}
```

- Kafka
  实现了 [KafkaMessageChannelBinder](https://github.com/spring-cloud/spring-cloud-stream-binder-kafka/blob/master/spring-cloud-stream-binder-kafka/src/main/java/org/springframework/cloud/stream/binder/kafka/KafkaMessageChannelBinder.java)
- RabbitMQ
  实现了 [RabbitMessageChannelBinder](https://github.com/spring-cloud/spring-cloud-stream-binder-rabbit/blob/master/spring-cloud-stream-binder-rabbit/src/main/java/org/springframework/cloud/stream/binder/rabbit/RabbitMessageChannelBinder.java)
- RocketMQ
  实现了 [RocketMQMessageChannelBinder](https://github.com/alibaba/spring-cloud-alibaba/blob/master/spring-cloud-stream-binder-rocketmq/src/main/java/com/alibaba/cloud/stream/binder/rocketmq/RocketMQMessageChannelBinder.java)

2. *
   *[Binding](https://github.com/spring-cloud/spring-cloud-stream/blob/master/spring-cloud-stream/src/main/java/org/springframework/cloud/stream/binder/Binding.java)
   **，包括 Input Binding 和 Output Binding。Binding 在消息中间件与应用程序提供的 Provider 和 Consumer
   之间提供了一个桥梁，实现了开发者只需使用应用程序的 Provider 或 Consumer 生产或消费数据即可，屏蔽了开发者与底层消息中间件的接触。

最终整体交互如下图所示：![Spring Cloud Stream Application](https://img2023.cnblogs.com/blog/3014862/202309/3014862-20230913012507166-1937213626.png)

可能看完之后，小伙伴们对 Spring Cloud Stream 还是有点懵逼，并且觉得概念怎么这么多呢？不要慌，我们先来快速入个门，会有更加具象的感受。

## 4.3 快速入门

>
示例代码对应仓库：[learning/rabbitmq/rabbitmq-springcloud-quickstart at master · JiuYou2020/learning (github.com)](https://github.com/JiuYou2020/learning/tree/master/rabbitmq/rabbitmq-springcloud-quickstart)
>
> 友情提示：这可能是一个信息量有点大的入门内容，请保持耐心~

本小节，我们一起来快速入门下，会创建 2 个项目，分别作为生产者和消费者。最终项目如下图所示：

![image-20230912215258007](https://img2023.cnblogs.com/blog/3014862/202309/3014862-20230913012508015-132672952.png)

> 友情提示：考虑到伙伴们能够有更舒适的入门体验，需要对 RabbitMQ 的基本概念有一定的了解，特别是对 Exchange 的四种类型
> Direct、Topic、Fanout、Headers 噢。

### 4.3.1 搭建生产者

创建`producer`项目，作为生产者。

#### 4.3.1.1 引入依赖

创建 [`pom.xml`](https://github.com/YunaiV/SpringBoot-Labs/blob//master/labx-10-spring-cloud-stream-rabbitmq/labx-10-sc-stream-rabbitmq-producer-demo/pom.xml)
文件中，引入 Spring Cloud Stream RabbitMQ 相关依赖。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>cn.learning</groupId>
        <artifactId>rabbitmq-springcloud-quickstart</artifactId>
        <version>1.0-SNAPSHOT</version>
    </parent>

    <artifactId>producer</artifactId>

    <properties>
        <maven.compiler.source>8</maven.compiler.source>
        <maven.compiler.target>8</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>
    <description>
        版本兼容:https://github.com/alibaba/spring-cloud-alibaba/wiki/%E7%89%88%E6%9C%AC%E8%AF%B4%E6%98%8E
    </description>
    <dependencies>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
        </dependency>
        <!-- 引入 SpringMVC 相关依赖，并实现对其的自动配置 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <!-- 引入 Spring Cloud Stream RabbitMQ 相关依赖，将 RabbitMQ 作为消息队列，并实现对其的自动配置 -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-stream-rabbit</artifactId>
        </dependency>
    </dependencies>
</project>
```

通过引入 [`spring-cloud-starter-stream-rabbit`](https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-starter-stream-rabbit)
依赖，引入并实现 Stream RabbitMQ 的自动配置。在该依赖中，已经帮我们自动引入 RabbitMQ 的大量依赖，非常方便

#### 4.3.1.2 配置文件

创建 [`application.yaml`](https://github.com/YunaiV/SpringBoot-Labs/blob//master/labx-10-spring-cloud-stream-rabbitmq/labx-10-sc-stream-rabbitmq-producer-demo/src/main/resources/application.yml)
配置文件，添加 Spring Cloud Stream RabbitMQ 相关配置。

```yml
spring:
  application:
    name: demo-producer-application
  cloud:
    # Spring Cloud Stream 配置项，对应 BindingServiceProperties 类
    stream:
      # Binder 配置项，对应 BinderProperties Map
      binders:
        rabbit001:
          type: rabbit # 设置 Binder 的类型
          environment: # 设置 Binder 的环境配置
            # 如果是 RabbitMQ 类型的时候，则对应的是 RabbitProperties 类
            spring:
              rabbitmq:
                host: 127.0.0.1 # RabbitMQ 服务的地址
                port: 5672 # RabbitMQ 服务的端口
                username: guest # RabbitMQ 服务的账号
                password: guest # RabbitMQ 服务的密码
      # Binding 配置项，对应 BindingProperties Map
      bindings:
        # 注意,这里的key有通道名-out/in-序号组成,且需要与Controller类中保持一致
        demo01-out-0:
          destination: DEMO-TOPIC-01 # 目的地。这里使用 RabbitMQ Exchange
          content-type: application/json # 内容格式。这里使用 JSON
          binder: rabbit001 # 设置使用的 Binder 名字

server:
  port: 18080
```

> `spring.cloud.stream` 为 Spring Cloud Stream
> 配置项，对应 [BindingServiceProperties](https://github.com/spring-cloud/spring-cloud-stream/blob/master/spring-cloud-stream/src/main/java/org/springframework/cloud/stream/config/BindingServiceProperties.java)
> 类。配置的层级有点深，我们一层一层来看看。

- `spring.cloud.stream.binders` 为 Binder
  配置项，对应 [BinderProperties](https://github.com/spring-cloud/spring-cloud-stream/blob/master/spring-cloud-stream/src/main/java/org/springframework/cloud/stream/config/BinderProperties.java)
  Map。其中 *key* 为 Binder 的名字。这里，我们配置了一个名字为 `rabbit001` 的 Binder。

    - `type`：Binder 的类型。这里，我们设置为了 `rabbit`，表示使用 Spring Cloud Stream RabbitMQ 提供的 Binder 实现。

    - `environment`：Binder 的环境。因为 Spring Cloud Steam RabbitMQ
      底层使用的是 [`spring-rabbit`](https://github.com/spring-projects/spring-amqp/tree/master/spring-rabbit)，所以在使用
      RabbitMQ
      类型的时候，则对应的是 [RabbitProperties](https://github.com/spring-projects/spring-boot/blob/master/spring-boot-project/spring-boot-autoconfigure/src/main/java/org/springframework/boot/autoconfigure/amqp/RabbitProperties.java)
      类。

- `spring.cloud.stream.bindings` 为 Binding
  配置项，对应 [BindingProperties](https://github.com/spring-cloud/spring-cloud-stream/blob/master/spring-cloud-stream/src/main/java/org/springframework/cloud/stream/config/BindingProperties.java)
  Map。其中，*key* 为 Binding 的名字。要注意， Binding 分成 Input 和 Output 两种类型，**并且需要在配置文件中体现出来**
  ,例如produer为`demo-out-0`,consumer为`demo-in-0`(`@Input` 还是 `@Output` 注解已弃用,不再推荐使用).*
  *这里，我们配置了一个名字为 `demo01-out-0:` 的 Binding**。从命名上，我们的意图是想作为 Output Binding，用于生产者发送消息。

- `destination`：目的地。**在 RabbitMQ 中，使用 Exchange 作为目的地，默认为 Topic 类型**。这里我们设置为 `DEMO-TOPIC-01`。

- `content-type`：内容格式。这里使用 JSON 格式，因为稍后我们将发送消息的类型为 POJO，使用 JSON 进行序列化。

- `binder`：使用的 Binder 名字。这里我们设置为 `rabbit001`，就是我们上面刚创建的。

  > 友情提示：如果只有一个 Binder 的情况，可以不进行设置。又或者通过 `spring.cloud.stream.default-binder` 配置项来设置默认的
  Binder 的名字。

#### 4.3.1.3 Demo01Message

创建 Demo01Message 类，示例 RpcMessage 消息。代码如下：

```java
public class Demo01Message {

    /**
     * 编号
     */
    private Integer id;

    public Demo01Message setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Demo01Message{" +
                "id=" + id +
                '}';
    }

}
```

#### 4.3.1.4 Demo01Controller

创建 Demo01Controller 类，提供发送消息的 HTTP 接口。代码如下：

```java
package cn.learning.rabbitmq.cloud.quickstart.producer.controller;

import cn.learning.rabbitmq.cloud.quickstart.producer.message.Demo01Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.RpcMessage;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping("/demo01")
public class Demo01Controller {

    @Autowired
    private StreamBridge streamBridge;

    @GetMapping("/send")
    public boolean send() {
        // 创建 RpcMessage
        Demo01Message message = new Demo01Message()
                .setId(new Random().nextInt());
        // 创建 Spring RpcMessage 对象,使用函数式变成模型的方式而不是加上已弃用的@enableBinding/@output注解,demo01-out-0是配置文件中的spring.cloud.stream.bindings.demo01-out-0
        return streamBridge.send("demo01-out-0", MessageBuilder.withPayload(message).build());
    }

    @GetMapping("/send_tag")
    public boolean sendTag() {
        for (String tag : new String[]{"yunai", "yutou", "tudou"}) {
            // 创建 RpcMessage
            Demo01Message message = new Demo01Message()
                    .setId(new Random().nextInt());
            // 创建 Spring RpcMessage 对象
            RpcMessage<Demo01Message> springMessage = MessageBuilder.withPayload(message)
                    // 设置 Tag
                    .setHeader("tag", tag)
                    .build();
            // 发送消息,使用函数式变成模型的方式而不是加上已弃用的@enableBinding/@output注解
            streamBridge.send("demo01-out-0", springMessage);

        }
        return true;
    }

}
```

#### 4.3.1.5 ProducerApplication

创建 ProducerApplication 类，启动应用。代码如下：

```java

@SpringBootApplication
public class ProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProducerApplication.class, args);
    }

}
```

### 4.3.2 搭建消费者

创建`consumer`项目，作为消费者。

#### 4.3.2.1 引入依赖

创建 [`pom.xml`](https://github.com/YunaiV/SpringBoot-Labs/blob//master/labx-10-spring-cloud-stream-rabbitmq/labx-10-sc-stream-rabbitmq-consumer-demo/pom.xml)
文件中，引入 Spring Cloud Stream RabbitMQ 相关依赖。

> 友情提示：[4.3.1.1 引入依赖](# 4.3.1.1 引入依赖)一致

#### 4.3.2.2 配置文件

创建 [`application.yaml`](https://github.com/YunaiV/SpringBoot-Labs/blob//master/labx-10-spring-cloud-stream-rabbitmq/labx-10-sc-stream-rabbitmq-consumer-demo/src/main/resources/application.yml)
配置文件，添加 Spring Cloud Stream RabbitMQ 相关配置。

```yml
spring:
  application:
    name: demo-consumer-application
  cloud:
    # Spring Cloud Stream 配置项，对应 BindingServiceProperties 类
    stream:
      # Binder 配置项，对应 BinderProperties Map
      binders:
        rabbit001:
          type: rabbit # 设置 Binder 的类型
          environment: # 设置 Binder 的环境配置
            # 如果是 RabbitMQ 类型的时候，则对应的是 RabbitProperties 类
            spring:
              rabbitmq:
                host: 127.0.0.1 # RabbitMQ 服务的地址
                port: 5672 # RabbitMQ 服务的端口
                username: guest # RabbitMQ 服务的账号
                password: guest # RabbitMQ 服务的密码
      # Binding 配置项，对应 BindingProperties Map
      bindings:
        # 注意,这里的key有通道名-out/in-序号组成,且需要与Consumer类中的bean的名称保持一致,例如demo01-in-0可以简写为demo01
        demo01-in-0:
          destination: DEMO-TOPIC-01 # 目的地。这里使用 RabbitMQ Exchange
          content-type: application/json # 内容格式。这里使用 JSON
          group: demo01-consumer-group-DEMO-TOPIC-01 # 消费者分组
          binder: rabbit001  # 设置使用的 Binder 名字

server:
  port: ${random.int[10000,19999]} # 随机端口，方便启动多个消费者

```

总体来说，和之前的是比较接近的，所以我们只说差异点噢。

- `spring.cloud.stream.bindings` 为 Binding 配置项

    - `group`：消费者分组。

      > **消费者组（Consumer Group）**：同一类 Consumer 的集合，这类 Consumer
      通常消费同一类消息且消费逻辑一致。消费者组使得在消息消费方面，实现负载均衡和容错的目标变得非常容易。要注意的是，消费者组的消费者实例必须订阅完全相同的
      Topic。

- 对于消费队列的消费者，会有两种消费模式：集群消费（Clustering）和广播消费（Broadcasting）。

> - **集群消费（Clustering）**：集群消费模式下,相同 Consumer Group 的每个 Consumer 实例平均分摊消息。
> - **广播消费（Broadcasting）**：广播消费模式下，相同 Consumer Group 的每个 Consumer 实例都接收全量的消息。

RabbitMQ 的消费者**两种模式都支持**。因为这里我们配置了消费者组，所以采用**集群消费**。至于如何使用广播消费，我们稍后举例子。

**一定要理解集群消费和广播消费的差异**。我们来举个例子，有一个消费者分组,其中有两个消费者A,B,现在我们发送三条消息

- 集群消费:A消费2条,B消费1条
- 广播消费:A消费3条,B消费3条

通过**集群消费**的机制，我们可以实现针对相同 Topic ，不同消费者分组实现各自的业务逻辑。例如说：用户注册成功时，发送一条 Topic
为 `"USER_REGISTER"` 的消息。然后，不同模块使用不同的消费者分组，订阅该 Topic ，实现各自的拓展逻辑：

- 积分模块：判断如果是手机注册，给用户增加 20 积分。
- 优惠劵模块：因为是新用户，所以发放新用户专享优惠劵。
- 站内信模块：因为是新用户，所以发送新用户的欢迎语的站内信。
- ... 等等

这样，我们就可以将注册成功后的业务拓展逻辑，实现业务上的**解耦**，未来也更加容易拓展。同时，也提高了注册接口的性能，避免用户需要等待业务拓展逻辑执行完成后，才响应注册成功。

同时，相同消费者分组的多个实例，可以实现**高可用**，保证在一个实例意外挂掉的情况下，其它实例能够顶上。并且，多个实例都进行消费，能够提升
**消费速度**。

> 友情提示：如果还不理解的话，没有关系，我们下面会演示下我们上面举的例子。

#### 4.3.2.3 Demo01Message

> 友情提示：和[4.3.1.3 Demo01Message](#_4.3.1.3 Demo01Message)基本一样

#### 4.3.2.4 Demo01Consumer

创建 Demo01Consumer 类，消费消息。代码如下：

```java
package cn.learning.rabbitmq.cloud.quickstart.consumer.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
@Slf4j
public class Demo01Consumer {
    /**
     * bean的名称需要与配置文件中`spring.cloud.stream.bindings.demo01-in-0`对应
     *
     * @return Consumer<String>
     */
    @Bean
    public Consumer<String> demo01() {
        return message -> {
            log.info("[demo01][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), message);
        };
    }
}
```

因为我们消费的消息是 POJO
类型，所以我们需要添加 [`@Payload`](https://github.com/spring-projects/spring-framework/blob/master/spring-messaging/src/main/java/org/springframework/messaging/handler/annotation/Payload.java)
注解，声明需要进行反序列化成 POJO 对象。

#### 4.3.2.5 ConsumerApplication

创建 ConsumerApplication 类，启动应用。代码如下：

```java

@SpringBootApplication
public class ConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }

}
```

### 4.3.3 测试单集群多实例的场景

本小节，我们会在**一个**消费者集群启动**两个**实例，测试在集群消费的情况下的表现。

1. 执行 **Consumer**Application 两次，启动两个**消费者**的实例，从而实现在消费者分组 `demo01-consumer-group-DEMO-TOPIC-01`
   下有两个消费者实例。

> 友情提示：因为 IDEA 默认同一个程序只允许启动 1 次，所以我们需要配置 DemoProviderApplication 为 `Allow parallel run`
> 。如下图所示：![image-20230912223234404](https://img2023.cnblogs.com/blog/3014862/202309/3014862-20230913012508760-1489304070.png)

此时在 IDEA 控制台看到 RabbitMQ 相关的日志如下：

```java
#在 RabbitMQ
声明一个 `DEMO-TOPIC-01.demo01-consumer-group-DEMO-TOPIC-01`队列，并绑定到名字为 `DEMO-TOPIC-01`
的 Exchange
上
2023-09-12 22:33:41.735INFO 20220---[main]c.s.b.r.p.RabbitExchangeQueueProvisioner :
declaring queue for inbound:DEMO-TOPIC-01.demo01-consumer-group-DEMO-TOPIC-01,
bound to:DEMO-TOPIC-01

        #
连接到 RabbitMQ
Broker
2023-09-12 22:33:41.737INFO 20220---[main]o.s.a.r.c.CachingConnectionFactory       :
Attempting to
connect to:[127.0.0.1:5672]
        2023-09-12 22:33:41.895INFO 20220---[main]o.s.a.r.c.CachingConnectionFactory       :Created new connection:rabbitConnectionFactory#1f6917fb:0/SimpleConnection@d1d8e1a [delegate=amqp://guest@127.0.0.1:5672/, localPort= 6488]

        #订阅消费 `DEMO-TOPIC-01.demo01-consumer-group-DEMO-TOPIC-01`队列的消息
2023-09-12 22:33:42.179INFO 20220---[main]o.s.i.a.i.AmqpInboundChannelAdapter      :
started bean 'inbound.DEMO-TOPIC-01.demo01-consumer-group-DEMO-TOPIC-01'
```

重点是第一条日志，为什么呢？在我们添加了 `spring.cloud.stream.bindings.{bindingName}` 配置项时，并且是 Input 类型时，每个
RabbitMQ Binding 都会：

- 【Queue】创建一个 `{destination}.{group}` 队列，例如这里创建的队列是 `DEMO-TOPIC-01.demo01-consumer-group-DEMO-TOPIC-01`。
- 【Exchange】同时创建的还有类型为 Topic 的 Exchange，并进行绑定。

下面，我们打开 RabbitMQ 运维界面，查看下**名字为 `DEMO-TOPIC-01` 的 Exchange**
，会更加好理解。如下图所示：![image-20230912223647290](https://img2023.cnblogs.com/blog/3014862/202309/3014862-20230913012509352-1125187070.png)

2. 执行 **Producer**Application，启动**生产者**的实例。

之后，请求 http://127.0.0.1:18080/demo01/send 接口三次，发送三条消息。此时在 IDEA 控制台看到消费者打印日志如下：

```java
// ConsumerApplication 控制台 01
2023-09-12 22:37:31.541INFO 20220---[DEMO-TOPIC-01-1]c.l.r.c.q.c.consumer.Demo01Consumer      :[demo01][线程编号:36消息内容：{"id":-560689743}]

// ConsumerApplication 控制台 02
        2023-09-12 22:37:29.944INFO 7560---[DEMO-TOPIC-01-1]c.l.r.c.q.c.consumer.Demo01Consumer      :[demo01][线程编号:37消息内容：{"id":-2084140846}]
        2023-09-12 22:37:32.127INFO 7560---[DEMO-TOPIC-01-1]c.l.r.c.q.c.consumer.Demo01Consumer      :[demo01][线程编号:37消息内容：{"id":-169129061}]
```

**符合预期**。从日志可以看出，每条消息仅被消费一次。对了，有点忘记提下，非常关键！当 RabbitMQ Consumer 订阅**相同 Queue**
时，每条消息有且仅被一个 Consumer 消费，通过这样的方式实现**集群消费**，也就是说，Stream RabbitMQ 是通过消费**相同 Queue
实现消费者组**。

> 友情提示：RabbitMQ 本身没有消费组的概念，而是由 Spring Cloud Stream 定义的统一抽象，而后交给不同消息队列的 Spring Cloud
> Stream XXX 去具体实现。例如说，Spring Cloud Stream RabbitMQ 就基于 RabbitMQ 的上述特性，实现消费组的功能。

### 4.3.4 测试多集群多实例的场景

本小节，我们会在**二个**消费者集群**各**启动**两个**实例，测试在集群消费的情况下的表现。

1. 执行 **Consumer**Application 两次，启动两个**消费者**的实例，从而实现在消费者分组 `demo01-consumer-group-DEMO-TOPIC-01`
   下有两个消费者实例。

2. 修改 `consumer` 项目的配置文件，修改 `spring.cloud.stream.bindings.demo01-in-1.group`
   配置项，将消费者分组改成 `X-demo01-consumer-group-DEMO-TOPIC-01`。

然后，执行 **Consumer**Application 两次，再启动两个**消费者**
的实例，从而实现在消费者分组 `X-demo01-consumer-group-DEMO-TOPIC-01` 下有两个消费者实例。

此时，我们打开 RabbitMQ 运维界面，查看下**名字为 `DEMO-TOPIC-01` 的 Exchange**，可以看到**两个消费者的两个队列**。

3. 执行 **Producer**Application，启动**生产者**的实例。

之后，请求 http://127.0.0.1:18080/demo01/send 接口三次，发送三条消息。从日志可以看出，每条消息被**每个**消费者集群都进行了消费，且仅被消费一次。

### 4.3.5 小结

至此，我们已经完成了 Stream RocketMQ 的快速入门，是不是还是蛮简答的噢。现在小伙伴可以在回过头看看 Binder 和 Binding
的概念，是不是就清晰一些了。

## 4.4 定时消息

> 暂未完成,敬请期待~

在 RabbitMQ
中，我们可以通过使用 [`rabbitmq-delayed-message-exchange`](https://github.com/rabbitmq/rabbitmq-delayed-message-exchange)
插件提供的定时消息功能。也可以通过RabbitMQ的[死信队列实现定时消息](# 3.5 定时消息)

这两种实现定时消息的方案，各有优缺点，目前采用 `rabbitmq-delayed-message-exchange` 插件较多，不然 Spring Cloud Stream
RabbitMQ
也不会选择将其集成进来。至于两者的对比，小伙伴可以阅读[《RabbitMQ 延迟队列的两种实现方式》](http://www.iocoder.cn/Fight/RabbitMQ-deferred-queues-are-implemented-in-two-ways/?self)
文章。

> **定时消息**，是指消息发到 Broker 后，不能立刻被 Consumer 消费，要到特定的时间点或者等待特定的时间后才能被消费。

相比定时任务来说，我们可以使用定时消息实现**更细粒度**且**动态**的定时功能。例如说，新创建的订单 2 小时超时关闭的场景：

- 如果使用定时任务，我们需要每秒扫描订单表，是否有超过支付时间的订单。这样会增加对订单表的查询压力，同时定时任务本身是**串行
  **的，需要一个一个处理。
- 如果使用定时消息，我们需要创建订单的时候，同时发送一条检查支付超时的定时消息。这样就无需每秒查询查询订单表，同时多个定时消息可以
  **并行**消费，提升处理速度。

另外，定时消息更有利于**不同环境的隔离**。再举个例子，我们生产和预发布环境使用的是相同的数据库，还是新创建的订单 2
小时超时关闭的场景，假设我们现在修改了超时支付的逻辑：

- 如果使用定时任务，在我们把程序发布到预发布的时候，因为使用相同数据库，会导致所有订单都执行了新的逻辑。如果新的逻辑有问题，将会影响到所有订单。

- 如果使用定时消息，我们只需要把正服和预发布使用**不同的** RabbitMQ
  Exchange，这样预发布发送的延迟消息，只会被预发布的消费者消费，生产发送的延迟消息，只会被生产的消费者消费。如果新的逻辑有问题，只会影响到预发布的订单。

  > 友情提示：建议不同的环境，使用**不同的** RabbitMQ Exchange 噢，例如说 `exchange-01`
  可以带上具体环境的后缀，从而拆分成 `exchange-01-dev`、`exchange-01-prod` 等。

## 4.5 消费重试

>
示例代码对应仓库：[learning/rabbitmq/rabbitmq-springcloud-consumer-retry at master · JiuYou2020/learning (github.com)](https://github.com/JiuYou2020/learning/tree/master/rabbitmq/rabbitmq-springcloud-consumer-retry)
>
> :rotating_light: 请在对死信队列有一定了解之后食用本节

在消息**消费失败**的时候，Spring-AMQP 会通过**消费重试**机制，重新投递该消息给 Consumer ，让 Consumer 有机会重新消费消息，实现消费成功。

> 友情提示：Spring Cloud Stream RabbitMQ 是基于 [Spring-AMQP](https://github.com/spring-projects/spring-amqp) 操作
> RabbitMQ，它仅仅是上层的封装哟。

当然，Spring-AMQP 并不会无限重新投递消息给 Consumer 重新消费，而是在默认情况下，达到 N 次重试次数时，Consumer
还是消费失败时，该消息就会进入到**死信队列**。后续，我们可以通过对死信队列中的消息进行重发，来使得消费者实例再次进行消费。

- 消费重试和死信队列，是 RocketMQ 自带的功能。
- 而在 RabbitMQ 中，消费重试是由 Spring-AMQP 所封装提供的，死信队列是 RabbitMQ 自带的功能。

那么消费失败到达最大次数的消息，是怎么进入到死信队列的呢？Spring-AMQP
在消息到达最大消费次数的时候，会将该消息进行否定(`basic.nack`)，并且 `requeue=false` ，这样后续就可以利用 RabbitMQ
的[死信队列](https://www.rabbitmq.com/dlx.html)的机制，将该消息转发到死信队列。

另外，每条消息的失败重试，是可以配置一定的**间隔时间**。具体，我们在示例的代码中，来进行具体的解释。

下面，我们来实现一个 Consumer
消费重试的示例。最终项目如下图所示：![image-20230913004849688](https://img2023.cnblogs.com/blog/3014862/202309/3014862-20230913012510261-1814979108.png)

### 4.5.1 搭建生产者

直接使用快速入门小节的 `producer`即可

### 4.5.2 搭建消费者

直接使用快速入门小节的 `consumer`即可

#### 4.5.2.1 配置文件

修改 [`application.yml`](https://github.com/YunaiV/SpringBoot-Labs/blob//master/labx-10-spring-cloud-stream-rabbitmq/labx-10-sc-stream-rabbitmq-consumer-retry/src/main/resources/application.yml)
配置文件，增加**消费重试**相关的配置项。最终配置如下：

```yml
spring:
  application:
    name: demo-consumer-application
  cloud:
    # Spring Cloud Stream 配置项，对应 BindingServiceProperties 类
    stream:
      # Binder 配置项，对应 BinderProperties Map
      binders:
        rabbit001:
          type: rabbit # 设置 Binder 的类型
          environment: # 设置 Binder 的环境配置
            # 如果是 RabbitMQ 类型的时候，则对应的是 RabbitProperties 类
            spring:
              rabbitmq:
                host: 127.0.0.1 # RabbitMQ 服务的地址
                port: 5672 # RabbitMQ 服务的端口
                username: guest # RabbitMQ 服务的账号
                password: guest # RabbitMQ 服务的密码
      # Binding 配置项，对应 BindingProperties Map
      bindings:
        # 注意,这里的key有通道名-out/in-序号组成,且需要与Consumer类中的bean的名称保持一致,例如demo01-in-0可以简写为demo01
        demo01-in-0:
          destination: DEMO-TOPIC-01 # 目的地。这里使用 RabbitMQ Exchange
          content-type: application/json # 内容格式。这里使用 JSON
          group: demo01-consumer-group-DEMO-TOPIC-01 # 消费者分组
          binder: rabbit001  # 设置使用的 Binder 名字
          # Consumer 配置项，对应 ConsumerProperties 类
          consumer:
            max-attempts: 3 # 重试次数，默认为 3 次。
            back-off-initial-interval: 3000 # 重试间隔的初始值，单位毫秒，默认为 1000
            back-off-multiplier: 2.0 # 重试间隔的递乘系数，默认为 2.0
            back-off-max-interval: 10000 # 重试间隔的最大值，单位毫秒，默认为 10000
          # RabbitMQ 自定义 Binding 配置项，对应 RabbitBindingProperties Map
      rabbit:
        bindings:
          demo01-in-0:
            # RabbitMQ Consumer 配置项，对应 RabbitConsumerProperties 类
            consumer:
              auto-bind-dlq: true # 是否创建对应的死信队列，并进行绑定，默认为 false。
              republish-to-dlq: true # 消费失败的消息发布到对应的死信队列时，是否添加异常异常的信息到消息头


server:
  port: ${random.int[10000,19999]} # 随机端口，方便启动多个消费者

```

1. `spring.cloud.stream.bindings.<bindingName>.consumer` 为 Spring Cloud Stream Consumer **通用**
   配置项，对应 [ConsumerProperties](https://github.com/spring-cloud/spring-cloud-stream/blob/master/spring-cloud-stream/src/main/java/org/springframework/cloud/stream/binder/ConsumerProperties.java)
   类。

    - `max-attempts`：最大重试次数，默认为 3 次。如果想要禁用掉重试，可以设置为 1。

      > `max-attempts` 配置项要注意，是一条消息一共尝试消费总共 `max-attempts` 次，包括首次的正常消费。

    - `back-off-initial-interval`：重试间隔的初始值，单位毫秒，默认为 1000。

    - `back-off-multiplier`：重试间隔的递乘系数，默认为 2.0。

    - `back-off-max-interval`：重试间隔的最大值，单位毫秒，默认为 10000。

将四个参数组合在一起，我们来看一个消费重试的过程：

- 第一次 00:00:00：首次消费，失败。

- 第二次 00:00:03：3 秒后重试，因为重试间隔的初始值为 `back-off-initial-interval`，等于 3000 毫秒。

- 第三次 00:00:09：6 秒后重试，因为有重试间隔的递乘系数 `back-off-multiplier`，所以是 `2.0 * 3000` 等于 6000 毫秒。
- 第四次，没有，因为到达最大重试次数，等于 3。

2. `spring.cloud.stream.rabbit.bindings.<bindingName>.consumer` 为 Spring Cloud Stream RabbitMQ Consumer **专属**
   配置项，我们新增了两个配置项：

- `auto-bind-dlq`：是否创建对应的死信队列，并进行绑定，默认为`false`。
    - Spring Cloud Stream RabbitMQ 默认会将消息发送到死信队列，如果这里我们不设置为 `true`
      ，那么我们就需要手工去创建 `DEMO-TOPIC-01.demo01-consumer-group-DEMO-TOPIC-01` 对应的死信队列，否则会因为死信队列不存在而报错。
    - 默认情况下，创建的死信队列为原队列添加 `.ldq` 后缀，可以通过 `deadLetterQueueName`
      配置项来自定义。例如说 `DEMO-TOPIC-01.demo01-consumer-group-DEMO-TOPIC-01`
      对应的死信队列为 `DEMO-TOPIC-01.demo01-consumer-group-DEMO-TOPIC-01.ldq`。
- `republish-to-dlq`：消费失败的消息发布到对应的死信队列时，是否添加异常异常的信息到消息头，默认为 `true`
  。通过这样的方式，我们可以知道一条消息消费失败的原因~

#### 4.5.2.2 Demo01Consumer

修改 Demo01Consumer 类，直接抛出异常，模拟消费失败，从而演示消费重试的功能。代码如下：

```java

@Component
@Slf4j
public class Demo01Consumer {
    /**
     * bean的名称需要与配置文件中`spring.cloud.stream.bindings.demo01-in-0`对应
     *
     * @return Consumer<String>
     */
    @Bean
    public Consumer<String> demo01() {
        return message -> {
            log.info("[demo01][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), message);
            // <X> 注意，此处抛出一个 RuntimeException 异常，模拟消费失败
            throw new RuntimeException("我就是故意抛出一个异常");
        };
    }
}
```

### 4.5.3 简单测试

1. 执行 **Consumer**Application，启动一个**消费者**的实例。

我们打开 RabbitMQ 运维界面，查看下名字为 `DLX` 的
Exchange，用于死信队列。如下图所示：![ 的 Exchange](https://img2023.cnblogs.com/blog/3014862/202309/3014862-20230913012511690-1823731907.png)

2. 执行 **Producer**Application，启动**生产者**的实例。

之后，请求 http://127.0.0.1:18080/demo01/send 接口，发送消息。IDEA 控制台输出日志如下：

```java
// 第一次消费
2023-09-13 00:41:06.484INFO 7956---[DEMO-TOPIC-01-1]c.l.r.c.c.c.consumer.Demo01Consumer      :[demo01][线程编号:37消息内容：{"id":979955782}]
// 第二次消费，3 秒后
        2023-09-13 00:41:09.491INFO 7956---[DEMO-TOPIC-01-1]c.l.r.c.c.c.consumer.Demo01Consumer      :[demo01][线程编号:37消息内容：{"id":979955782}]
// 第三次消费，6 秒后
        2023-09-13 00:41:15.502INFO 7956---[DEMO-TOPIC-01-1]c.l.r.c.c.c.consumer.Demo01Consumer      :[demo01][线程编号:37消息内容：{"id":979955782}]

// 内置的 LoggingHandler 打印异常日志
        2023-09-13 00:41:15.504ERROR 7956---[DEMO-TOPIC-01-1]o.s.integration.handler.LoggingHandler   :org.springframework.messaging.MessageHandlingException:
error occurred
in message
handler [org.springframework.cloud.stream.function.FunctionConfiguration$FunctionToDestinationBinder$1@2860f94];
nested exception
is java.lang.RuntimeException:我就是故意抛出一个异常// ... 省略异常堆栈
Caused by:java.lang.RuntimeException:我就是故意抛出一个异常 // ... 省略异常堆栈
```

我们打开 RabbitMQ 运维界面，查看下名字为 `DEMO-TOPIC-01.demo01-consumer-group-DEMO-TOPIC-01.dlq`
的死信队列，并获取一条死信消息，可以从消息头看到具体消费失败的异常堆栈。如下图所示：![image-20230913004537806](https://img2023.cnblogs.com/blog/3014862/202309/3014862-20230913012512665-1915877712.png)

### 4.5.4 另一种重试方案

目前我们看到的重试方案，是通过 [RetryTemplate](https://docs.spring.io/spring-retry/docs/api/current/org/springframework/retry/support/RetryTemplate.html)
来实现**客户端级别**的消费。而 RetryTemplate 又是通过 **sleep** 来实现消费间隔的时候，这样将影响 Consumer 的整体消费速度，毕竟
sleep 会占用掉线程。

实际上，我们可以结合 RabbitMQ 的定时消息，手动将消费失败的消息发送到定时消息的队列，而延迟时间为下一次重试消费的间隔。通过这样的方式，避免使用
RetryTemplate 使用 **sleep** 所带来的影响。

## 4.6. 消费异常处理机制

>
示例代码对应仓库：[learning/rabbitmq/rabbitmq-springcloud-error-handler at master · JiuYou2020/learning (github.com)](https://github.com/JiuYou2020/learning/tree/master/rabbitmq/rabbitmq-springcloud-error-handler)

在 Spring Cloud Stream 中，提供了**通用**的消费异常处理机制，可以拦截到消费者消费消息时发生的异常，进行自定义的处理逻辑。

下面，我们来搭建一个 Spring Cloud Stream
消费异常处理机制的示例。最终项目如下图所示：![image-20230913012248908](https://img2023.cnblogs.com/blog/3014862/202309/3014862-20230913012513603-2109335382.png)

### 4.6.1 搭建生产者

复用上一节的`consumer-retry-producer`

### 4.6.2 搭建消费者

复用上一节的`consumer-retry-consumer`

#### 4.6.2.1 Demo01Consumer

修改 Demo01Consumer 类，增加消费异常处理方法。完整代码如下：

```java
public class Demo01Consumer {
    /**
     * bean的名称需要与配置文件中`spring.cloud.stream.bindings.demo01-in-0`对应
     *
     * @return Consumer<String>
     */
    @Bean
    public Consumer<String> demo01() {
        return message -> {
            log.info("[demo01][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), message);
            // <X> 注意，此处抛出一个 RuntimeException 异常，模拟消费失败
            throw new RuntimeException("我就是故意抛出一个异常");
        };
    }

    @ServiceActivator(inputChannel = "DEMO-TOPIC-01.demo01-consumer-group-DEMO-TOPIC-01.errors")
    public void handleError(ErrorMessage errorMessage) {
        log.error("[handleError][payload：{}]", errorMessage.getPayload().getMessage());
        log.error("[handleError][originalMessage：{}]", errorMessage.getOriginalMessage());
        log.error("[handleError][headers：{}]", errorMessage.getHeaders());
    }

    /**
     * // 指定输入通道名字，这里假设是 "errorChannel"
     *
     * @param errorMessage errorMessage
     */
    @ServiceActivator(inputChannel = IntegrationContextUtils.ERROR_CHANNEL_BEAN_NAME)
    public void globalHandleError(ErrorMessage errorMessage) {
        log.error("[globalHandleError][payload：{}]", errorMessage.getPayload().getMessage());
        log.error("[globalHandleError][originalMessage：{}]", errorMessage.getOriginalMessage());
        log.error("[globalHandleError][headers：{}]", errorMessage.getHeaders());
    }
}
```

1. 在 Spring Integration 的设定中，若 `#demo01()`
   方法消费消息发生异常时，会发送错误消息（[ErrorMessage](https://github.com/spring-projects/spring-framework/blob/master/spring-messaging/src/main/java/org/springframework/messaging/support/ErrorMessage.java)
   ）到对应的**错误 Channel（`<destination>.<group>.errors`）中。同时，所有错误 Channel 都桥接到了 Spring Integration 定义的全局错误
   Channel(`errorChannel`)**。

> 友情提示：先暂时记住 Spring Integration 这样的设定，博主也没去深究 T T，也是一脸懵逼。

因此，我们有**两种**方式来实现异常处理：

- **局部**的异常处理：通过订阅指定**错误 Channel**
- **全局**的异常处理：通过订阅**全局错误 Channel**

2. 在 `#handleError(ErrorMessage errorMessage)`
   方法上，我们声明了 [`@ServiceActivator`](https://github.com/spring-projects/spring-integration/blob/master/spring-integration-core/src/main/java/org/springframework/integration/annotation/ServiceActivator.java)
   注解，订阅**指定错误 Channel**的错误消息，实现 `#demo01()` 方法的**局部**
   异常处理。如下图所示：![image-20230913012120635](https://img2023.cnblogs.com/blog/3014862/202309/3014862-20230913012514545-777821192.png)

3. 在 `#globalHandleError(ErrorMessage errorMessage)` 方法上，我们声明了 `@ServiceActivator` 注解，订阅**全局错误 Channel
   **的错误消息，实现**全局**异常处理。

4. 在**全局**和**局部**异常处理都定义的情况下，错误消息仅会被**符合条件**的**局部**错误异常处理。如果没有符合条件的，错误消息才会被
   **全局**异常处理。

### 4.6.3 简单测试

1. 执行 **Consumer**Application，启动**消费者**的实例。

2. 执行 **Producer**Application，启动**生产者**的实例。

之后，请求 http://127.0.0.1:18080/demo01/send 接口，发送一条消息。

> 😆 不过要注意，如果异常处理方法成功，没有重新抛出异常，会认定为该消息被**消费成功**，所以就不会发到死信队列了噢。

# 5. Bus RabbitMQ-SpringCloud

## 5.1 概述

> 友情提示：在开始本文之前，小伙伴需要对 RabbitMQ 进行简单的学习。
>
> ps: Spring Cloud Bus 在日常开发中，**基本不会使用到**。绝大多数情况下，我们通过使用 Spring Cloud Stream
> 即可实现它所有的功能，并且更加强大和灵活。
>
> 也因此,在这里,仅做快速入门案例

本文我们来学习 [Spring Cloud Bus RabbitMQ](https://github.com/spring-cloud/spring-cloud-bus/blob/master/spring-cloud-starter-bus-amqp)
组件，基于 [Spring Cloud Bus](https://github.com/spring-cloud/spring-cloud-bus) 的编程模型，接入 RabbitMQ 消息队列，实现*
*事件总线**的功能。

## 5.2 SpringBoot事件机制Event

>
在这里,我们需要去简单了解SpringBoot事件机制Event,可以参考:[springboot事件机制event - JiuYou2020 - 博客园 (cnblogs.com)](https://www.cnblogs.com/jiuyou2020/p/17700470.html)

## 5.3 快速入门

在上文中，我们已经了解到，Spring 内置了事件机制，可以实现 **JVM 进程内**的事件发布与监听。但是如果想要**跨 JVM 进程**
的事件发布与监听，此时它就无法满足我们的诉求了。

因此，Spring Cloud Bus 在 Spring 事件机制的基础之上进行**拓展**，结合 RabbitMQ、Kafka、RocketMQ 等等消息队列作为事件的**
“传输器”**
，通过发送事件（消息）到消息队列上，从而广播到订阅该事件（消息）的所有节点上。最终如下图所示：![整体模型](https://img2023.cnblogs.com/blog/3014862/202309/3014862-20230913185350810-676457289.png)

Spring Cloud Bus
定义了 [RemoteApplicationEvent](https://github.com/spring-cloud/spring-cloud-bus/blob/master/spring-cloud-bus/src/main/java/org/springframework/cloud/bus/event/RemoteApplicationEvent.java)
类，远程的 ApplicationEvent 的**抽象基类**。核心代码如下：

```java

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonIgnoreProperties("source") // <2>
public abstract class RemoteApplicationEvent extends ApplicationEvent { // <1>

    private final String originService;

    private final String destinationService;

    private final String id;

    // ... 
}
```

- 显然，我们使用 Spring Cloud Bus 发送的自定义事件，必须要**继承**` RemoteApplicationEvent` 类。

- `<1>` 处，继承 Spring
  事件机制定义的 [ApplicationEvent](https://github.com/spring-projects/spring-framework/blob/master/spring-context/src/main/java/org/springframework/context/ApplicationEvent.java)
  抽象基类。

- `<2>` 处，通过 Jackson 的 `@JsonIgnoreProperties` 注解，设置忽略继承自 ApplicationEvent 的 `source` 属性，避免序列化问题。

- `id` 属性，事件编号。一般情况下，RemoteApplicationEvent 会使用 `UUID.randomUUID().toString()` 代码，自动生成 UUID 即可。

- `originService` 属性，来源服务。Spring Cloud Bus
  提供好了 [`ServiceMatcher#getServiceId()`](https://github.com/spring-cloud/spring-cloud-bus/blob/master/spring-cloud-bus/src/main/java/org/springframework/cloud/bus/ServiceMatcher.java)
  方法，获取服务编号作为 `originService` 属性的值。

  > 友情提示：这个属性非常关键

- `destinationService` 属性，目标服务。该属性的格式是 `{服务名}:{服务实例编号}`。

  > 举个板栗：
  >
  > - 如果想要广播给所有服务的所有实例，则设置为 `**:**`。
  > - 如果想要广播给 `users` 服务的所有实例，则设置为 `users:**`。
  > - 如如果想要广播给 `users` 服务的指定实例，则设置为 `users:bc6d27d7-dc0f-4386-81fc-0b3363263a15`。

下面以一个`Spring Cloud Bus`快速入门的示例来进行讲解

>
代码地址:[learning/rabbitmq/bus-rabbitmq-springcloud-quickstart at master · JiuYou2020/learning (github.com)](https://github.com/JiuYou2020/learning/tree/master/rabbitmq/bus-rabbitmq-springcloud-quickstart)

![image-20230913180808428](https://img2023.cnblogs.com/blog/3014862/202309/3014862-20230913185351875-382999075.png)

- `bus-publisher`：扮演事件**发布器**的角色，使用 Spring Cloud Bus 发送事件。
- `bus_listener`：扮演事件**监听器**的角色，使用 Spring Cloud Bus 监听事件。

### 5.3.1 事件发布器项目

创建 `bus-publisher`项目，扮演事件**发布器**的角色，使用 Spring Cloud Bus 发送事件。

1. 创建 `pom.xml`文件，引入 Spring Cloud Bus 相关依赖：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>cn.learning</groupId>
        <artifactId>bus-rabbitmq-springcloud-quickstart</artifactId>
        <version>1.0-SNAPSHOT</version>
    </parent>

    <artifactId>bus-listener</artifactId>

    <properties>
        <maven.compiler.source>8</maven.compiler.source>
        <maven.compiler.target>8</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>
    <description>
        版本兼容:https://github.com/alibaba/spring-cloud-alibaba/wiki/%E7%89%88%E6%9C%AC%E8%AF%B4%E6%98%8E
    </description>
    <dependencies>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
        </dependency>
        <!-- 引入 SpringMVC 相关依赖，并实现对其的自动配置 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <!-- 引入 Spring Cloud Stream RabbitMQ 相关依赖，将 RabbitMQ 作为消息队列，并实现对其的自动配置 -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-bus-amqp</artifactId>
        </dependency>
    </dependencies>
</project>
```

2. 配置文件

创建 [`application.yml`](https://github.com/YunaiV/SpringBoot-Labs/blob/master/labx-18/labx-18-sc-bus-rabbitmq-demo-publisher/src/main/resources/application.yml)
配置文件，添加 Spring Cloud Bus 相关配置：

```yml
spring:
  application:
    name: publisher-demo

  # RabbitMQ 相关配置项
  rabbitmq:
    host: localhost
    port: 5672
    username: guest
    password: guest

  # Bus 相关配置项，对应 BusProperties
  cloud:
    bus:
      enabled: true # 是否开启，默认为 true
      destination: springCloudBus # 目标消息队列，默认为 springCloudBus
```

- `spring.rabbitmq` 配置项，为 RabbitMQ 相关配置项。
- `spring.cloud.bus` 配置项，为 Spring Cloud Bus
  配置项，对应 [BusProperties](https://github.com/spring-cloud/spring-cloud-bus/blob/master/spring-cloud-bus/src/main/java/org/springframework/cloud/bus/BusProperties.java)
  类。一般情况下，使用默认值即可。

3. 创建 UserRegisterEvent 类，用户注册事件。代码如下：

```java
/**
 * 用户注册事件
 */
public class UserRegisterEvent extends RemoteApplicationEvent {

    /**
     * 用户名
     */
    private String username;

    public UserRegisterEvent() { // 序列化
    }

    public UserRegisterEvent(Object source, String originService, String destinationService, String username) {
        super(source, originService, DEFAULT_DESTINATION_FACTORY.getDestination(destinationService));
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

}
```

- 继承 RemoteApplicationEvent 抽象基类。
- 创建一个空的构造方法，毕竟要序列化。

4. DemoController

创建 DemoController类，提供 `/demo/register` 注册接口，发送 UserRegisterEvent 事件。代码如下：

```java

@RestController
@Slf4j
@RequestMapping("/demo")
public class DemoController {


    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private ServiceMatcher busServiceMatcher;

    @GetMapping("/register")
    public String register(String username) {
        // ... 执行注册逻辑
        log.info("[register][执行用户({}) 的注册逻辑]", username);

        // ... 发布<2>
        applicationEventPublisher.publishEvent(new UserRegisterEvent(this, busServiceMatcher.getBusId(),//<1>
                null, username));
        return "success";
    }
}
```

`<1>` 处，创建 UserRegisterEvent 对象。

- `originService` 属性，通过 `ServiceMatcher#getServiceId()` 方法，获得服务编号。
- `destinationService` 属性，我们传入 `null` 值。RemoteApplicationEvent 会自动转换成 `**`，表示广播给所有监听该消息的实例。

`<2>` 处，和 Spring 事件机制**一样**，通过 ApplicationEventPublisher 的 `#publishEvent(event)` 方法，直接发送事件到 Spring
Cloud Bus
消息总线。好奇的小伙伴，可以打开 [BusAutoConfiguration](https://github.com/spring-cloud/spring-cloud-bus/blob/master/spring-cloud-bus/src/main/java/org/springframework/cloud/bus/BusAutoConfiguration.java#L142-L151)
的代码，如下图所示：![BusAutoConfiguration 源码](https://img2023.cnblogs.com/blog/3014862/202309/3014862-20230913185352942-1687765544.png)

> 友情提示：如果小伙伴仔细看的话，还可以发现 Spring Cloud Bus 是使用 Spring Cloud Stream 进行消息的收发的。

5. PublisherDemoApplication

创建 [PublisherDemoApplication](https://github.com/YunaiV/SpringBoot-Labs/blob/master/labx-18/labx-18-sc-bus-rabbitmq-demo-publisher/src/main/java/cn/iocoder/springcloud/labx18/publisherdemo/PublisherDemoApplication.java)
类，作为启动类。代码如下：

```java

@SpringBootApplication
public class PublisherDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(PublisherDemoApplication.class, args);
    }

}
```

### 5.3.2 事件监听器项目

创建 `bus-listener`项目，扮演事件**监听器**的角色，使用 Spring Cloud Bus 监听事件。

1. 引入依赖

> 与发布器一致

2. 配置文件

> 与发布器一致

3. UserRegisterEvent

> 与发布器一致

4. UserRegisterListener

创建 `UserRegisterListener`类，监听 UserRegisterEvent 事件。代码如下：

```java

@Component
@Slf4j
public class UserRegisterListener implements ApplicationListener<UserRegisterEvent> {


    @Override
    public void onApplicationEvent(UserRegisterEvent event) {
        log.info("[onApplicationEvent][监听到用户({}) 注册]", event.getUsername());
    }

}
```

和 Spring 事件机制**一样**
，只需要监听指定事件即可。好奇的小伙伴，可以打开 [BusAutoConfiguration](https://github.com/spring-cloud/spring-cloud-bus/blob/master/spring-cloud-bus/src/main/java/org/springframework/cloud/bus/BusAutoConfiguration.java#L153-L190)
的代码，如下图所示：![BusAutoConfiguration 源码](https://img2023.cnblogs.com/blog/3014862/202309/3014862-20230913185354009-1046476376.png)

5. ListenerDemoApplication

创建 `ListenerDemoApplication` 类，作为启动类。代码如下：

```java

@SpringBootApplication
@RemoteApplicationEventScan
public class ListenerDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ListenerDemoApplication.class, args);
    }

}
```

在类上，添加 Spring Cloud Bus
定义的 [`@RemoteApplicationEventScan`](https://github.com/spring-cloud/spring-cloud-bus/blob/master/spring-cloud-bus/src/main/java/org/springframework/cloud/bus/jackson/RemoteApplicationEventScan.java)
注解，声明要从 Spring Cloud Bus 监听 RemoteApplicationEvent 事件。

### 5.3.3 简单测试

1. 执行 PublisherDemoApplication 一次，启动一个事件**发布器**。

2. 执行 ListenerDemoApplication **两次**，启动两个事件**监听器**。需要将「Allow parallel run」进行勾选

此时，我们可以在 RabbitMQ 运维界面看到 **springCloudBus** 这个
Exchange，如下图所示：![RabbitMQ 运维界面](https://img2023.cnblogs.com/blog/3014862/202309/3014862-20230913185355041-647450113.png)

3. 调用 http://127.0.0.1:8080/demo/register?username=lihua 接口，进行注册。IDEA 控制台打印日志如下：

```java
#PublisherDemoApplication 控制台
2023-09-13 18:04:57.267INFO 7568---[nio-8080-exec-1]c.l.r.b.q.controller.DemoController      :[register][

执行用户(lihua) 的注册逻辑]

        #
ListenerDemoApplication 控制台 01
        2023-09-13 18:04:58.110INFO 12324---[MK3DbwlRsYcwg-1]c.l.r.b.q.listener.UserRegisterListener  :[onApplicationEvent][

监听到用户(lihua) 注册]

        #
ListenerDemoApplication 控制台 02
        2023-09-13 18:04:58.126INFO 1408---[m28zHyTurUL2w-1]c.l.r.b.q.listener.UserRegisterListener  :[onApplicationEvent][

监听到用户(lihua) 注册]
```

发布的 UserRegisterEvent 事件，被两个事件监听器的进程都监听成功。

----------------基本完成 2023/9/13----------------
