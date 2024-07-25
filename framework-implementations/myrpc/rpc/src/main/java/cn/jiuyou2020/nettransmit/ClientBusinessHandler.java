package cn.jiuyou2020.nettransmit;

import cn.jiuyou2020.nettransmit.protocolencoding.RpcMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author: jiuyou2020
 * @description: 客户端业务处理器，用于处理客户端的业务逻辑，如果是心跳消息或者状态码不为0的消息，直接调用下一个处理器
 */
public class ClientBusinessHandler extends ChannelInboundHandlerAdapter {
    private static final Log LOG = LogFactory.getLog(ClientBusinessHandler.class);
    private ChannelPromise promise;
    private RpcMessage response;


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (!(msg instanceof RpcMessage rpcMessage)) {
            return;
        }
        if (rpcMessage.isHeartbeat() || rpcMessage.getStatusCode() != 0) {
            //调用下一个处理器
            ctx.fireChannelRead(msg);
            return;
        }
        LOG.info("客户端收到数据消息: " + rpcMessage);
        if (promise != null) {
            response = rpcMessage;
            promise.setSuccess();
        }
        ctx.close();
    }

    public void setPromise(ChannelPromise promise) {
        this.promise = promise;
    }

    public RpcMessage getResponse() {
        return response;
    }
}
