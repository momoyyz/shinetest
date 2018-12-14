package com.yyz.shinetest.web;

import com.yyz.shinetest.netty.NettyClientFilter;
import com.yyz.shinetest.netty.NettyServerFilter;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class NettyController {
    /**
     * server
     */
    private static final int port = 6789; //设置服务端端口
    private static  EventLoopGroup group=new NioEventLoopGroup();;   // 通过nio方式来接收连接和处理连接
    private static ServerBootstrap  b = new ServerBootstrap();
    private static ChannelFuture f;

    /**
     *client
     */
    public static String host = "127.0.0.1";  //ip地址
    /// 通过nio方式来接收连接和处理连接
    private static EventLoopGroup group1 ;
    private static  Bootstrap b1;
    private static ChannelFuture ch;

    /**
     *
     *
     */
     public  static String newServerStr;

    @RequestMapping("test")
    public String test() {
       // NettyServerHandler.sendMessage(NettyServerHandler.myCtx,"11236**-----------");
        return "success";
    }
    @RequestMapping("nettyServer")
    protected String messageReceived(String serverStr){
        newServerStr=serverStr;
        try {
            if(f==null){
            b.group(group);
            b.channel(NioServerSocketChannel.class);
            // 服务器绑定端口监听

                b.childHandler(new NettyServerFilter(newServerStr)); //设置过滤器
                f = b.bind(port).sync();
                if(f.isSuccess()){
                    System.out.println("服务端启动成功...");

                }else{
                    System.out.println("服务端启动失败...");
                }
            }else{
                System.out.println("else");
                System.out.println(serverStr);
                b.childHandler(new NettyServerFilter(newServerStr)); //设置过滤器

            }
            // 监听服务器关闭监听
           f.channel().closeFuture().sync();

        } catch (Exception e){
            System.out.println("服务器错误");
            System.out.println(e.getMessage());
        } finally{
            group.shutdownGracefully(); ////关闭EventLoopGroup，释放掉所有资源包括创建的线程
        }

        return "success";
    }

    @RequestMapping("nettyClient")
    protected String messageRecseived(){
        group1 = new NioEventLoopGroup();
        b1 = new Bootstrap();
        try {
            // 进行连接
            ChannelFuture future;

            b1.group(group1);
            b1.channel(NioSocketChannel.class);
            b1.handler(new NettyClientFilter());
            System.out.println("客户端准备启动...");
            ///if(ch==null){
                System.out.println("客户端成功启动...");
                ch = b1.connect(host, port).sync();
           // }
            // 监听服务器关闭监听
            //ch.closeFuture().sync();
        } catch (Exception e){
            System.out.println("客户器错误");
            System.out.println(e.getMessage());
        } finally{
          //  group1.shutdownGracefully(); ////关闭EventLoopGroup，释放掉所有资源包括创建的线程
        }

    return "success";
    }

    @RequestMapping("nettyNest")
    protected String messagweReceived(String serverStr){
        newServerStr=serverStr;
        try {
            b.childHandler(new NettyServerFilter(newServerStr)); //设置过滤器
            b1.handler(new NettyClientFilter());
            ch = b1.connect(host, port).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "success";
    }

}
