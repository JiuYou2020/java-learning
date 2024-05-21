package cn.jiuyou2020.nettransmit;

import cn.jiuyou2020.nettransmit.protocolencoding.RpcMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.timeout.IdleStateEvent;

public class ClientHandler extends ChannelInboundHandlerAdapter {
    private ChannelPromise promise;
    private RpcMessage response;


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (!(msg instanceof RpcMessage)) {
            return;
        }
        if (promise != null) {
            response = (RpcMessage) msg;
            promise.setSuccess();
        }
        // 关闭连接
        ctx.close();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            // 发送心跳消息
//            System.out.println("Sending heartbeat");
//            ctx.writeAndFlush("HEARTBEAT");
        } else {
//            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
        throw new RuntimeException(cause);
    }

    public void setPromise(ChannelPromise promise) {
        this.promise = promise;
    }

    public RpcMessage getResponse() {
        return response;
    }
}
