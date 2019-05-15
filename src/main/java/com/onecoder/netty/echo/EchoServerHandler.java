package com.onecoder.netty.echo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: com.onecoder.netty.echo.Demo2ServerHandler.java
 * <p>@Description 生命周期：channelRegistered=>channelActive=>channelInactive=>channelUnregistered
 *
 * UDP有边界协议，所以没有断包、黏包问题。
 * 解释：用UDP发送的时候 ，是可以按照一个一个数据包去发送的， 一个数据包就是一个明确的边界。
 * TCP有断包、黏包问题。
 * 解释：TCP并没有数据包的概念，是完全流式的 ，他会开辟一个缓冲区 ，发送端往其中写入数据，每过一段时间就发送出去，然后接收端接收到这些数据，
 * 但是并不是说我发送了一次数据就肯定发送出去， 数据会在缓冲区中，有可能后续发送的数据和之前发送的数据同时存在缓冲区中随后一起发送，这就是粘包的一种形式
 * 原因一：发送方的原因，TCP默认会使用Nagle算法
 * 原因二：接收方的原因，TCP接收到数据放入缓存中，应用程序从缓存中读取，接收端处理速度比发送端发送速度慢的时候，如果来不及消费会堆积数据
 * </p>
 * @Author hushiguo@onecoder.com.cn 
 * @Date 2019-04-01 10:22
 **/
public class EchoServerHandler extends ChannelInboundHandlerAdapter{

    /**
     * @Description ChannelHandlerContext 是连接ChannelHandler和ChannelPipeline的桥梁
     * 1，InboundHandler之间数据传递通过ctx.fireChannelRead(msg);
     * 2，InboundHandler通过ctx.write(msg)，传递到outboundHandler
     * 3，ctx.write(msg)传递消息，Inbound需要放在结尾，在Outbound之后，不然outboundHandler不会执行
     * ch.pipeline().addLast(new OutboundHandler1());
     * ch.pipeline().addLast(new OutboundHandler2());
     * ch.pipeline().addLast(new InboundHandler1());
     * 4,Inbound 和 outbound 谁先执行，针对客户端和服务端而言，客户端是发起请求再接收数据，先outbound再inbound,服务端则相反
     * ch.pipeline().addLast(new InboundHandler2());
     * @Param
     * @return
     * @Author hushiguo@onecoder.com.cn
     * @Date 2019-04-03 10:28
     **/
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        ByteBuf data = (ByteBuf) msg;

        System.out.println(" 服务器接收到的数据：" +  data.toString(CharsetUtil.UTF_8));

        ctx.writeAndFlush(data);

        Map<String,Object> maps = new HashMap<String, Object>();

    }

    /**
     * @Description TODO
     * @Param  
     * @return 
     * @Author hushiguo@onecoder.com.cn 
     * @Date 2019-04-01 9:54
     **/
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Demo2ServerHandler channelReadComplete");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("Demo2ServerHandler exceptionCaught");
        cause.printStackTrace();
        ctx.close();
    }

    /**
     * @Description TODO
     * @Param * @param ctx 
     * @return void
     * @Author hushiguo@onecoder.com.cn 
     * @Date 2019/3/30 17:42
     **/
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Demo2ServerHandler channelRegistered");
    }

    /**
     * @Author hushiguo
     * @Description channel已经创建，但是未注册到一个EventLoop里面，也就是没有和Selector绑定
     * @Date 2019/3/30 17:08
     * @Param [ctx]
     * @return void
     **/
    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Demo2ServerHandler channelUnregistered");
    }

    /**
     * @Author hushiguo
     * @Description 变为活跃状态(连接到了远程主机)，可以接收和发送数据
     * @Date 2019/3/30 17:09
     * @Param [ctx]
     * @return void
     **/
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Demo2ServerHandler channelActive");
    }

    /**
     * @Author hushiguo
     * @Description channel处于非活跃状态(没有连接到了远程主机)
     * @Date 2019/3/30 17:10
     * @Param [ctx]
     * @return void
     **/
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
         System.out.println("Demo2ServerHandler channelInactive");
    }



}
