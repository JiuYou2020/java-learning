package cn.jiuyou2020;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class EchoServer {

    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws InterruptedException {
        new EchoServer(8080).start(); // 启动服务端，监听8080端口
    }

    public void start() throws InterruptedException {
        // 创建两个EventLoopGroup实例，bossGroup用来接收连接，workerGroup用来处理连接
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            // 创建ServerBootstrap实例来设置服务端
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup) // 设置EventLoopGroup
                    .channel(NioServerSocketChannel.class) // 指定使用NioServerSocketChannel来接收连接
                    .handler(new LoggingHandler(LogLevel.INFO)) // 添加日志处理器，记录服务端的日志
                    .childHandler(new ChannelInitializer<SocketChannel>() { // 设置子通道处理器
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            p.addLast(new EchoServerHandler()); // 添加自定义的处理器到管道
                        }
                    });

            // 绑定端口并开始接收连接
            ChannelFuture f = b.bind(port).sync();
            // 等待服务端关闭
            f.channel().closeFuture().sync();
        } finally {
            // 优雅地关闭EventLoopGroup，释放所有资源
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}


class EchoServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 当接收到消息时，将消息写回给发送者
        ctx.write(msg);
        ctx.flush(); // 刷新管道中的数据，使其立即发送
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 捕捉异常并打印堆栈跟踪，关闭上下文
        cause.printStackTrace();
        ctx.close();
    }
}