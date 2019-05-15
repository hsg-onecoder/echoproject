package com.onecoder.netty.echo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

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

                     // Nagle算法，true：高实时性，关闭Nagle算法，有数据时马上发送；false:减少发送次数，累积一定大小后再发送；
                     .option(ChannelOption.TCP_NODELAY,true)

                     .handler(new ChannelInitializer<SocketChannel>() {
                         @Override
                         protected void initChannel(SocketChannel ch) {
                             ch.pipeline().addLast(new EchoClientHandler());
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
