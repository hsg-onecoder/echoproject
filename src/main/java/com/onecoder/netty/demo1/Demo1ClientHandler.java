package com.onecoder.netty.demo1;

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
public class Demo1ClientHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        ByteBuf msg = null;

        //内容+拼接行符号，System.getProperty("line.separator") 获取系统行符号
        byte[] req = ("hello world" + System.getProperty("line.separator")).getBytes();

        // 连续发送

        for (int i = 0; i < 10; i++) {
            // 创建缓冲区
            msg = Unpooled.buffer(req.length);
            msg.writeBytes(req);
            ctx.writeAndFlush(msg);
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
