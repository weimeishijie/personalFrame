package com.socket.webSocket2.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by mj on 2017/11/10.
 * Socket连接代码示例
 * Socket编程概念有两个：1、ServerSocket（服务端）；2、Socket（客户端）。
 * 服务端与客户端之间通过Socket来建立连接，并通信
 */
public class Server5 {
    public static void main(String[] args) throws IOException {
        // 为了简单起见，所有的异常信息都往外抛
        int port = 8899;
        // 定义一个ServerSocket监听在端口8899上
        ServerSocket serverSocket = new ServerSocket(port);
        while (true){
            // server尝试接收其他Socket的链接请求，server的accept方法是阻塞式的
            // 服务器启动后，或完成一个链接后，会再次在此等候下一个链接
            Socket socket = serverSocket.accept();
            // 每接收到一个Socket就建立一个新的线程来处理它
            new Thread(new Server5Task(socket)).start();
        }

    }
}
