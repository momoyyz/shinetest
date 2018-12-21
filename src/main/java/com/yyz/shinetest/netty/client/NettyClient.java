package com.yyz.shinetest.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
@Service
public class NettyClient {

    private final static Logger log = Logger.getLogger(NettyClient.class);


    SocketChannel socketChannel;


    public Boolean start(String host,int port) {

        Thread thread = new Thread(() -> {
            EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.channel(NioSocketChannel.class)
                    // 保持连接
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    // 有数据立即发送
                    .option(ChannelOption.TCP_NODELAY, true)
                    // 绑定处理group
                    .group(eventLoopGroup).remoteAddress(host, port)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            // 初始化编码器，解码器，处理器
                            socketChannel.pipeline().addLast(
                                    //new MessageDecoder(),
                                    //new MessageEncoder(),
                                        new StringDecoder(Charset.forName("utf-8")),
                                        new StringEncoder(Charset.forName("utf-8")),
                                    new ClientHandler());
                        }
                    });
            // 进行连接
            ChannelFuture future;
            try {
                future = bootstrap.connect(host, port).sync();
                // 判断是否连接成功
                if (future.isSuccess()) {
                    // 得到管道，便于通信
                    socketChannel = (SocketChannel) future.channel();
                    log.info("netty 客户端开启成功!->"+port);

                }
                else{
                    log.error("netty 客户端开启失败!->"+port);
                }
                // 等待客户端链路关闭，就是由于这里会将线程阻塞，导致无法发送信息，所以我这里开了线程
                future.channel().closeFuture().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                //优雅地退出，释放相关资源
               eventLoopGroup.shutdownGracefully();
            }
        });
        thread.start();
        return true;
    }



}
