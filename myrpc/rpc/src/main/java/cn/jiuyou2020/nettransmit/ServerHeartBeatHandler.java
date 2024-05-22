package cn.jiuyou2020.nettransmit;

import cn.jiuyou2020.PropertyContext;
import cn.jiuyou2020.nettransmit.protocolencoding.RpcMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author: jiuyou2020
 * @description: 服务端心跳处理器，用于处理服务端的心跳消息
 */
public class ServerHeartBeatHandler extends ChannelInboundHandlerAdapter {
    private static final Log LOG = LogFactory.getLog(ServerHeartBeatHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (!(msg instanceof RpcMessage rpcMessage)) {
            return;
        }
        if (rpcMessage.isHeartbeat()) {
            LOG.info("收到心跳消息" + rpcMessage);
            RpcMessage heartbeatMessage = new RpcMessage.Builder()
                    .setSerializationType((byte) PropertyContext.getSerializationType().getValue())
                    .setIsHeartbeat(true)
                    .setIsOneWay(true)
                    .setIsResponse(true)
                    .setStatusCode((byte) 0)
                    .setMessageId(0)
                    .setBodySize(0)
                    .setBody(new byte[0])
                    .build();
            LOG.info("响应心跳消息：" + heartbeatMessage);
            ctx.writeAndFlush(heartbeatMessage);
        }
    }
}
