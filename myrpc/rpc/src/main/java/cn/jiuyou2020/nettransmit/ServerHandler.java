package cn.jiuyou2020.nettransmit;

import cn.jiuyou2020.nettransmit.protocolencoding.RpcMessage;
import cn.jiuyou2020.reflectioncall.ReflectionCall;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ServerHandler extends ChannelInboundHandlerAdapter {
    // 反射调用
    private final ReflectionCall reflectionCall;

    public ServerHandler(ReflectionCall reflectionCall) {
        this.reflectionCall = reflectionCall;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!(msg instanceof RpcMessage rpcMessage)) {
            return;
        }
        Object call = reflectionCall.call(rpcMessage);
        ctx.writeAndFlush(call);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
        throw new RuntimeException(cause);
    }
}
