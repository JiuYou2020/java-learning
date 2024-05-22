package cn.jiuyou2020.nettransmit;

import cn.jiuyou2020.nettransmit.protocolencoding.RpcMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author: jiuyou2020
 * @description:
 */
public class ClientExceptionHandler extends ChannelInboundHandlerAdapter {
    private static final Log LOG = LogFactory.getLog(ClientExceptionHandler.class);
    private ChannelPromise promise;


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!(msg instanceof RpcMessage rpcMessage)) {
            return;
        }
        // 如果是异常消息
        if (rpcMessage.getStatusCode() != 0) {
            String exception = new String(rpcMessage.getBody());
            LOG.error("客户端收到异常消息: " + exception);
            ctx.close();
            promise.setFailure(new RuntimeException("客户端收到异常消息: " + rpcMessage));
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
        LOG.error("客户端发生异常，关闭连接", cause);
        promise.setFailure(cause);
        throw new RuntimeException(cause);
    }

    public void setPromise(ChannelPromise promise) {
        this.promise = promise;
    }
}
