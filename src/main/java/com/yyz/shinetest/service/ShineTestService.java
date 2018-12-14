package com.yyz.shinetest.service;

import com.yyz.shinetest.web.vo.req.ReqShutdownVMVO;
import com.yyz.shinetest.web.vo.resp.ResShutdownVMVO;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

@Service
public class ShineTestService {

    private static ServerSocket server;

    private static Socket socket;

    /**
     * 开启服务端
     *
     * @param port
     * @throws IOException
     */
    public void openServer(Integer port) throws IOException {
        server = new ServerSocket(port);
        socket = server.accept();// 等待客户连接
    }

    /**
     * 开启客户端
     * @param host
     * @param port
     * @throws IOException
     */
    public void opentClient(String host, Integer port) throws IOException {
        //创建Socket类对象
        Socket socket = new Socket(host, port);
        try {
            // 读取服务器端传过来信息的DataInputStream
            DataInputStream in = new DataInputStream(socket.getInputStream());
            // 向服务器端发送信息的DataOutputStream
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            while (true) {
                //将客户端的信息传递给服务器
                out.writeUTF("\"content\": {\"header\":{\"action\":\"shutdownVM\"}}");
                // 读取来自服务器的信息
                String accpet = in.readUTF();
                System.out.println("*************客户端输出提示*************");
                System.out.println("客户端回应消息：" + "\"content\": {\"header\":{\"action\":\"shutdownVM\"}}");//输出键盘输出内容提示 ，也就是客户端向服务器端发送的消息
                System.out.println("来自服务器的消息：" + accpet);//输出来自服务器的信息
            }
        } finally {
            //关闭Socket监听
            socket.close();
        }
    }

    /**
     * 接口测试 关闭虚机
     *
     * @return
     * @throws IOException
     */
    public ResShutdownVMVO shutdownVM(ReqShutdownVMVO reqShutdownVMVO) throws IOException {


        ResShutdownVMVO resShutdownVMVO = new ResShutdownVMVO();
        // 读取客户端传过来信息的DataInputStream
        DataInputStream in = new DataInputStream(socket.getInputStream());
        // 向客户端发送信息的DataOutputStream
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        // 读取来自客户端的信息
        String clientData = in.readUTF();
        /*
        控制台输出提示
         */
        System.out.println("*************服务端输出提示*************");
        System.out.println("报文数据：" + reqShutdownVMVO.getServerSendMessage());
        //输出来自客户端的信息
        System.out.println("客户端应答消息： " + clientData);
        System.out.println("客户端ip为：" + socket.getInetAddress().getHostAddress() + "端口：" + socket.getLocalPort());
        //把服务器端的输入发给客户端
        out.writeUTF(reqShutdownVMVO.getServerSendMessage());
        /*
         *回参准备
         */
        resShutdownVMVO.setTestNum(reqShutdownVMVO.getTestNum() + 1);
        resShutdownVMVO.setServerSendMessage(reqShutdownVMVO.getServerSendMessage());
        resShutdownVMVO.setTestContent(reqShutdownVMVO.getTestContent());
        resShutdownVMVO.setClientReturnMessage(clientData);
        return resShutdownVMVO;
    }

}
