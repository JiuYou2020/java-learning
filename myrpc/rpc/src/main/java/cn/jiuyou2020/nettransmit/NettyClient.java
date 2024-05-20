package cn.jiuyou2020.nettransmit;

import cn.jiuyou2020.protocolencoding.RpcEncoder;
import cn.jiuyou2020.serialize.SerializationDataOuterClass;
import cn.jiuyou2020.serialize.SerializationFacadeImpl;
import cn.jiuyou2020.serialize.strategy.ProtobufSerializationStrategy;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class NettyClient {

    public void connect(String host, int port) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            // 初始化一个Bootstrap实例，Netty客户端启动器
            Bootstrap bootstrap = new Bootstrap();

            // 配置Bootstrap使用的线程组，EventLoopGroup处理所有的事件（如连接、读、写事件）
            bootstrap.group(group)
                    // 设置通道类型为NioSocketChannel，用于非阻塞传输的客户端通道类型
                    .channel(NioSocketChannel.class)
                    // 设置通道选项，SO_KEEPALIVE表示是否启用TCP Keep-Alive机制，保持长连接
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    // 设置用于初始化新连接的ChannelHandler
                    .handler(new ChannelInitializer<SocketChannel>() {
                        // 当一个新的连接被接受时，这个方法会被调用
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            // 向管道中添加一个IdleStateHandler，用于检测空闲状态
                            // readerIdleTime：读超时（秒），0表示不检测
                            // writerIdleTime：写超时（秒），4秒内没有写操作则触发IdleStateEvent
                            // allIdleTime：读或写超时（秒），0表示不检测
                            ch.pipeline().addLast(new IdleStateHandler(0, 4, 0, TimeUnit.SECONDS));

                            ch.pipeline().addLast(new StringDecoder());

                            ch.pipeline().addLast(new RpcEncoder());

                            // 向管道中添加一个StringDecoder，用于将接收到的字节数据解码为字符串
//                            ch.pipeline().addLast(new StringDecoder());

                            // 向管道中添加一个StringEncoder，用于将发送的字符串编码为字节数据
//                            ch.pipeline().addLast(new StringEncoder());

                            // 向管道中添加自定义的处理器ClientHandler，用于处理业务逻辑
                            ch.pipeline().addLast(new ClientHandler());
                        }
                    });

            // 连接到服务器
            ChannelFuture f = bootstrap.connect(host, port).sync();
            System.out.println("Connected to server");

            // 发送消息
            SerializationFacadeImpl serializationFacade = new SerializationFacadeImpl(new ProtobufSerializationStrategy());
            SerializationDataOuterClass.SerializationData test = SerializationDataOuterClass.SerializationData.newBuilder()
                    .setUrl("http://localhost:8080")
                    .setMethodName("test")
                    .addAllParameterTypes(List.of("java.lang.String"))
                    .addAllArgs(List.of("hello"))
                    .build();
            byte[] serialize = serializationFacade.serialize(test);
            f.channel().writeAndFlush(serialize);

            // 等待连接关闭
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        new NettyClient().connect("localhost", 8080);
    }
}
