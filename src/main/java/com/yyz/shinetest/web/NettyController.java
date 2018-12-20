package com.yyz.shinetest.web;

import com.yyz.shinetest.netty.client.Client;
import com.yyz.shinetest.netty.server.Server;
import com.yyz.shinetest.netty.util.NettySocketHolder;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
public class NettyController {


    @RequestMapping("nettyServer")
    protected String messageReceived(String serverStr) {

        new Server(7899);
        return "success";
    }

    @RequestMapping("nettyClient")
    protected String messageRecseived() {
        new Client(7899, "127.0.0.1");

        return "success";
    }

    @RequestMapping("nettySendMessage")
    protected String messagweReceived(String serverStr) {
        NioSocketChannel socketChannel;
        Map<Integer, NioSocketChannel> map = NettySocketHolder.getMAP();
        //发送数据给每一个客户端
        for (Integer key : map.keySet()) {
            socketChannel = NettySocketHolder.get(key);
            if (null == socketChannel) {
                throw new NullPointerException("没有[" + 1 + "]的socketChannel");
            }
            String message = "to Client";
            System.out.println("发送给客户端的数据:" + message);
            ChannelFuture future = socketChannel.writeAndFlush(message);
        }
        return "success";
    }

}
