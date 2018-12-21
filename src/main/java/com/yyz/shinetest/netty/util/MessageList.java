package com.yyz.shinetest.netty.util;

import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Vector;

public class MessageList {
    static Vector<String> message=new  Vector();

    public static Vector<String> getMessage() {
        return message;
    }

    public static void setMessage(Vector<String> message) {
        MessageList.message = message;
    }

    public static void add(String str) {
        message.add(str);
    }

    public static void clear() {
        message.clear();
    }
}
