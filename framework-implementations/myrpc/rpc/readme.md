# 序列化

如果需要自定义拓展序列化方式，请实现以下几个类

1. [cn.jiuyou2020.serialize.SerializationFacade](./src/main/java/cn/jiuyou2020/serialize/strategy/SerializationStrategy.java)
2. [cn.jiuyou2020.serialize.message.RpcRequest](./src/main/java/cn/jiuyou2020/serialize/message/RpcRequest.java)
3. [cn.jiuyou2020.serialize.message.RpcRequestFactory](./src/main/java/cn/jiuyou2020/serialize/message/RpcRequestFactory.java)
4. [cn.jiuyou2020.serialize.message.RpcResponse](./src/main/java/cn/jiuyou2020/serialize/message/RpcResponse.java)
5. [cn.jiuyou2020.serialize.message.RpcResponseFactory](./src/main/java/cn/jiuyou2020/serialize/message/RpcResponseFactory.java)

第2 - 5的实现请参考[json](./src/main/java/cn/jiuyou2020/serialize/message/json)
包和[protobuf](./src/main/java/cn/jiuyou2020/serialize/message/protobuf)包下的实现。
此外，请将你自己实现的实现的序列化方式添加到上述5个包的map中，在本例，是通过[AutoConfigurationConfig](./src/main/java/cn/jiuyou2020/AutoConfigurationConfig.java)
类来实现自动配置的。

本项目提供一个参考示例，在common项目下新建一个包[custom_serialize](../common/src/main/java/cn/jiuyou2020/rpc/custom_serialize)
，实现上述功能。为了简单起见，这里还是采用json序列化，但是改个名字，统一加上前缀Custom。