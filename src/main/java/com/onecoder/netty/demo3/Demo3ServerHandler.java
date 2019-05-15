package com.onecoder.netty.demo3;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @ClassName: com.onecoder.netty.demo1.Demo2ServerHandler.java
 * <p>@Description
 * UDP有边界协议，所以没有断包、黏包问题。
 * 解释：用UDP发送的时候 ，是可以按照一个一个数据包去发送的， 一个数据包就是一个明确的边界。
 * TCP有断包、黏包问题。
 * 解释：TCP并没有数据包的概念，是完全流式的 ，他会开辟一个缓冲区 ，发送端往其中写入数据，每过一段时间就发送出去，然后接收端接收到这些数据，
 * 但是并不是说我发送了一次数据就肯定发送出去， 数据会在缓冲区中，有可能后续发送的数据和之前发送的数据同时存在缓冲区中随后一起发送，这就是粘包的一种形式
 * 原因一：发送方的原因，TCP默认会使用Nagle算法
 * 原因二：接收方的原因，TCP接收到数据放入缓存中，应用程序从缓存中读取，接收端处理速度比发送端发送速度慢的时候，如果来不及消费会堆积数据
 * </p>
 * @Author hushiguo@onecoder.com.cn 
 * @Date 2019-04-03 18:04
 **/
public class Demo3ServerHandler extends ChannelInboundHandlerAdapter{

    private int counter;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String body = (String) msg;
        System.out.println("服务端收到消息内容为：" + body + "，收到次数为：" + ++counter);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
