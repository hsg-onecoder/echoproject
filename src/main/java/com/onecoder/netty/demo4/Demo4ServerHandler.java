package com.onecoder.netty.demo4;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @ClassName: com.onecoder.netty.demo4.Demo4ServerHandler.java
 * <p>@Description TODO</p>
 * @Author hushiguo@onecoder.com.cn
 * @Date 2019-05-09 11:15
 **/
public class Demo4ServerHandler extends ChannelInboundHandlerAdapter{



    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Message body = (Message) msg;
        System.out.println("服务端收到消息内容为：" + body.getBody());
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
