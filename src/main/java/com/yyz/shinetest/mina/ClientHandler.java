package com.yyz.shinetest.mina;


import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

import java.util.Random;

public class ClientHandler extends IoHandlerAdapter{

    private final static Logger log = Logger.getLogger(ClientHandler.class);

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        log.info("mina 客户端连接出现异常");
    }

    /**
     * 接收消息与应答回馈
     * @param session
     * @param message
     * @throws Exception
     */
    @Override
    public void messageReceived(IoSession session, Object message)
            throws Exception {
        String msg = (String)message;
        log.info("mina 客户端接收到数据：" + msg);
        //应答消息
        Random random=new Random();
        session.write("应答消息"+random.nextInt(100)); // 用于写入数据并发送
    }

    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
       // System.out.println("客户端发送数据");
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        log.info("客户端session关闭");
    }

    @Override
    public void sessionOpened(IoSession session) throws Exception {
        log.info("mina 客户端打开Session用于读写数据");
    }

}
