package com.onecoder.netty.demo3;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @ClassName: com.onecoder.netty.demo1.Demo2ClientHandler.java
 * <p>@Description TODO</p>
 * @Author hushiguo@onecoder.com.cn 
 * @Date 2019-04-03 18:03
 **/
public class Demo3ClientHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //发送自定义分隔符为“&_”的字符串消息
        String message = "aaaaaaaaaaaaaaaaa&_" +
                "bbbbbbbbbbbbbbbbbb&_" +
                "cccccccccccccccccc&_";
        ByteBuf msg = null;
        // 申请ByteBuf空间
        msg = Unpooled.buffer(message.getBytes().length);
        // 字符串转成字节数组写入ByteBuf
        msg.writeBytes(message.getBytes());
        // 刷入服务器
        ctx.writeAndFlush(msg);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}
