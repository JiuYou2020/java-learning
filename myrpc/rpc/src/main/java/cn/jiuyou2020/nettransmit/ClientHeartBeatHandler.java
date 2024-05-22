package cn.jiuyou2020.nettransmit;

import cn.jiuyou2020.EnvContext;
import cn.jiuyou2020.nettransmit.protocolencoding.RpcMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Objects;

/**
 * @author: jiuyou2020
 * @description: 客户端心跳处理器，用于处理客户端的心跳消息，如果是异常消息，直接调用下一个处理器
 */
public class ClientHeartBeatHandler extends ChannelInboundHandlerAdapter {
    private static final Log LOG = LogFactory.getLog(ClientHeartBeatHandler.class);
    private ChannelPromise promise;
    // 发送的心跳包的次数
    private int heartbeats = 0;
    private final long lastDataMessageTime = System.currentTimeMillis();


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (!(msg instanceof RpcMessage rpcMessage)) {
            return;
        }
        if (rpcMessage.getStatusCode() != 0) {
            //调用下一个处理器
            ctx.fireChannelRead(msg);
            return;
        }
        // 如果是心跳消息
        if (rpcMessage.isHeartbeat()) {
            heartbeats++;
            LOG.info("客户端收到心跳消息响应: " + rpcMessage);
        }
    }

    /**
     * 用于处理读空闲或者写空闲的事件，这里用于触发心跳包
     *
     * @param ctx the {@link ChannelHandlerContext} for which the event was fired
     * @param evt the event to handle
     * @throws Exception is thrown if an error occurred
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent event) {
            // 在规定时间内如果读空闲(即没有收到消息)，则触发该事件，并发送心跳包
            if (Objects.requireNonNull(event.state()) == IdleState.READER_IDLE) {
                //构建心跳消息
                RpcMessage heartbeatMessage = new RpcMessage.Builder()
                        .setSerializationType((byte) EnvContext.getSerializationType().getValue())
                        .setIsHeartbeat(true)
                        .setIsOneWay(false)
                        .setIsResponse(false)
                        .setStatusCode((byte) 0)
                        .setMessageId(heartbeats)
                        .setBodySize(0)
                        .setBody(new byte[0])
                        .build();
                LOG.info("客户端发送心跳包: " + heartbeatMessage);
                ctx.writeAndFlush(heartbeatMessage);
                if (System.currentTimeMillis() - lastDataMessageTime > 10000) {
                    LOG.info("长时间未收到数据包，关闭连接");
                    promise.setFailure(new RuntimeException("长时间未收到数据包，关闭连接"));
                    ctx.close();
                }
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }


    public void setPromise(ChannelPromise promise) {
        this.promise = promise;
    }
}
