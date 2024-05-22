package cn.jiuyou2020.nettransmit;

import cn.jiuyou2020.PropertyContext;
import cn.jiuyou2020.nettransmit.protocolencoding.RpcMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author: jiuyou2020
 * @description:
 */
public class ServerExceptionHandler extends ChannelInboundHandlerAdapter {
    private static final Log LOG = LogFactory.getLog(ServerExceptionHandler.class);

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 构建异常消息
        int value = PropertyContext.getSerializationType().getValue();
        byte[] bytes = cause.getMessage().getBytes();

        RpcMessage rpcMessage = new RpcMessage.Builder()
                .setSerializationType((byte) value)
                .setIsHeartbeat(false)
                .setIsOneWay(true)
                .setIsResponse(true)
                .setStatusCode((byte) 1)
                .setMessageId(0)
                .setBodySize(bytes.length)
                .setBody(bytes)
                .build();
        LOG.error("发生异常，连接关闭，并发送异常消息", cause);
        ctx.writeAndFlush(rpcMessage);
        ctx.close();
        throw new RuntimeException(cause);
    }
}
