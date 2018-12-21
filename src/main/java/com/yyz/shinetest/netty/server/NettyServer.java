package com.yyz.shinetest.netty.server;

import com.yyz.shinetest.common.utils.ReplaceBlank;
import com.yyz.shinetest.netty.util.MessageList;
import com.yyz.shinetest.netty.util.NettySocketHolder;
import com.yyz.shinetest.web.MinaController;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class NettyServer {

    private final static Logger log = Logger.getLogger(NettyServer.class);


    //服务端要建立两个group，一个负责接收客户端的连接，一个负责处理数据传输
    //连接处理group
    private EventLoopGroup boss = new NioEventLoopGroup();
    //事件处理group
    private EventLoopGroup work = new NioEventLoopGroup();

    public Boolean bind(int serverPort) {
        //初始化客户端数量
       if( NettySocketHolder.getMAP().size()!=0){
           NettySocketHolder.getMAP().clear();
       }
        Thread thread = new Thread(() -> {
            ServerBootstrap bootstrap = new ServerBootstrap();
            // 绑定处理group
            bootstrap.group(boss, work).channel(NioServerSocketChannel.class)
                    //保持连接数
                    .option(ChannelOption.SO_BACKLOG, 128)
                    //有数据立即发送
                    .option(ChannelOption.TCP_NODELAY, true)
                    //保持连接
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    //处理新连接
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel sc) throws Exception {
                            // 增加任务处理
                            ChannelPipeline p = sc.pipeline();
                            p.addLast(
//										//使用了netty自带的编码器和解码器
                                    new StringDecoder(Charset.forName("utf-8")),
                                    new StringEncoder(Charset.forName("utf-8")),
                                    //new MessageDecoder(),
                                    //new MessageEncoder(),
                                    //自定义的处理器
                                    new ServerHandler());
                        }
                    });

            //绑定端口，同步等待成功
            ChannelFuture future;
            try {
                future = bootstrap.bind(serverPort).sync();
                if (future.isSuccess()) {
                    log.info("netty 服务端开启成功->"+serverPort);
                } else {
                    log.error("netty 服务端开启失败->"+serverPort);
                }

                //等待服务监听端口关闭,就是由于这里会将线程阻塞，导致无法发送信息，所以我这里开了线程
                future.channel().closeFuture().sync();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                //优雅地退出，释放线程池资源
                boss.shutdownGracefully();
                work.shutdownGracefully();
            }
        });
        thread.start();
        return true;
    }

    /**
     * netty 服务端发送消息
     *
     * @param SendMessage
     * @return
     */
    public Boolean nettySendMessage(String SendMessage) {
        //清理接收数据
        MessageList.clear();
        //处理换行等
        SendMessage= ReplaceBlank.replaceBlank(SendMessage);
        NioSocketChannel socketChannel;
        Map<Integer, NioSocketChannel> map = NettySocketHolder.getMAP();
        //发送数据给每一个客户端
        for (Integer key : map.keySet()) {
            socketChannel = NettySocketHolder.get(key);
            if (null == socketChannel) {
                log.error("netty 没有[" + key + "]的socketChannel");
                throw new NullPointerException("netty 没有[" + key + "]的socketChannel");
            }
            log.info("netty 服务端发送数据:" + SendMessage);
            socketChannel.writeAndFlush(SendMessage);

        }

        return true;
    }

    /**
     * 获取netty客户端数量
     *
     * @return
     */
    public int getnettyClientNum() {

        return NettySocketHolder.getMAP().size();
    }

    /**
     * netty 客户端返回的数据
     *
     * @return
     */
    public String nettyReturnMessage() throws InterruptedException {
        Vector<String> message = MessageList.getMessage();
        while (message.size()==0){
            Thread.sleep(10);
            message = MessageList.getMessage();
        }
        return message.get(0);
    }
}
