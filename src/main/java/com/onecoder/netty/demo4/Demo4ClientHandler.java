package com.onecoder.netty.demo4;

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
public class Demo4ClientHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelActive(ChannelHandlerContext ctx) {

        Message message = new Message();
        message.setLength(14);
        message.setBody("HELLO, WORLD");

        // 刷入服务器
        ctx.writeAndFlush(message);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}
