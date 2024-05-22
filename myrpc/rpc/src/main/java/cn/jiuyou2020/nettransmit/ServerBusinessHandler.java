package cn.jiuyou2020.nettransmit;

import cn.jiuyou2020.nettransmit.protocolencoding.RpcMessage;
import cn.jiuyou2020.reflectioncall.ReflectionCall;
import cn.jiuyou2020.serialize.SerializationFacade;
import cn.jiuyou2020.serialize.message.RpcResponse;
import cn.jiuyou2020.serialize.message.RpcResponseFactory;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * @author: jiuyou2020
 * @description: 服务端业务处理器，用于处理服务端的业务逻辑，如果是心跳消息或者状态码不为0的消息，直接调用下一个处理器
 */
public class ServerBusinessHandler extends ChannelInboundHandlerAdapter {
    private static final Log LOG = LogFactory.getLog(ServerBusinessHandler.class);
    // 反射调用
    private final ReflectionCall reflectionCall;

    public ServerBusinessHandler(ReflectionCall reflectionCall) {
        this.reflectionCall = reflectionCall;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!(msg instanceof RpcMessage rpcMessage)) {
            return;
        }
        if (rpcMessage.isHeartbeat()) {
            ctx.fireChannelRead(msg);
            return;
        }
        Object call = reflectionCall.call(rpcMessage);
        // 模拟耗时
        // Thread.sleep(10000);
        // 模拟异常
        // throw new RuntimeException("模拟异常");
        int serializationType = rpcMessage.getSerializationType();
        RpcResponse rpcResponse = RpcResponseFactory.getFactory(serializationType).createRpcResponse(call);
        byte[] respData = SerializationFacade.getStrategy(serializationType).serialize(rpcResponse);
        // 构建返回消息
        RpcMessage resp = new RpcMessage.Builder()
                .setSerializationType(rpcMessage.getSerializationType())
                .setIsHeartbeat(false)
                .setIsOneWay(true)
                .setIsResponse(true)
                .setStatusCode((byte) 0)
                .setMessageId(rpcMessage.getMessageId())
                .setBodySize(respData.length)
                .setBody(respData)
                .build();
        LOG.info("服务端返回数据消息: " + resp);
        ctx.writeAndFlush(resp);
        ctx.close();
    }
}
