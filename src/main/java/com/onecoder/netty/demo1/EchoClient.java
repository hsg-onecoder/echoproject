package com.onecoder.netty.demo1;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * @ClassName: com.onecoder.netty.demo1.EchoClient.java
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
 * @Date 2019-04-03 18:28
 **/
public class EchoClient {

    private String host;

    private int port;

    public EchoClient(String host,int port){
        this.host = host;
        this.port = port;
    }

    public void start() throws InterruptedException{

        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)

                     .channel(NioSocketChannel.class)

                     .remoteAddress(new InetSocketAddress(host,port))

                     //Nagle算法，true：高实时性，关闭Nagle算法，有数据时马上发送；false:减少发送次数，累积一定大小后再发送；
                     .option(ChannelOption.TCP_NODELAY,true)

                     .handler(new ChannelInitializer<SocketChannel>() {
                         @Override
                         protected void initChannel(SocketChannel ch) {
                             ch.pipeline().addLast(new Demo1ClientHandler());
                         }
                     });

          // 连接到服务器，connect是异步连接，再调用同步等待sync，等待连接成功
          ChannelFuture channelFuture = bootstrap.connect().sync();

          //阻塞直到客户端通道关闭
          channelFuture.channel().closeFuture().sync();

        }finally {
            group.shutdownGracefully();
        }

    }

    public static void main(String[] args) throws InterruptedException {
        new EchoClient("127.0.0.1",8080).start();
    }

}
