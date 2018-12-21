package com.yyz.shinetest.mina;


import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

public class ClientHandler extends IoHandlerAdapter{

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        System.out.println("mina 客户端连接出现异常");
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
        System.out.println("mina 客户端接收到数据：" + msg);
        //应答消息
        session.write("\"content\": {\"header\":{\"action\":\" shutdownVMFinish \"}}"); // 用于写入数据并发送
    }

    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
       // System.out.println("客户端发送数据");
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        System.out.println("客户端session关闭");
    }

//    @Override
//    public void sessionCreated(IoSession session) throws Exception {
//        System.out.println("客户端创建Session");
//    }
//
//    @Override
//    public void sessionIdle(IoSession session, IdleStatus status)
//            throws Exception {
//        System.out.println("客户端处于多长时间是空闲状态");
//    }

    @Override
    public void sessionOpened(IoSession session) throws Exception {
        System.out.println("mina 客户端打开Session用于读写数据");
    }

}
