package com.socket.webSocket1.client;

import java.io.*;
import java.net.Socket;

/**
 * Created by mj on 2017/11/10.
 * 注意：
 * Socket 输出流写数据方法是 writeUTF 时， 输入流读取相关数据要用 readUTF。否则会抛 EOFException 异常
 */
public class Client {
    public static final String IP_ADDR = "localhost";// 服务端地址
    public static final int PORT = 12345;// 服务器端口号

    public static void main(String[] args) {
        System.out.println("客户端启动...");
        System.out.println("当接收到服务器端字符 \"OK\" 的时候，客户端将终止\n");
        while (true){
            Socket socket = null;
            try {
                // 创建一个流套接字并将其链接到制定主机上的制定端口号
                socket = new Socket(IP_ADDR,PORT);

                // 读取服务器端数据
                DataInputStream input = new DataInputStream(socket.getInputStream());
                // 向服务器端发送数据
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());

                System.out.println("请输入：\t");
                String str = new BufferedReader(new InputStreamReader(System.in)).readLine();
                out.writeUTF(str);

                String ret = input.readUTF();
                System.out.println("服务器端返回过来的是： "+ret);
                // 如果收到 “OK” 则断开链接
                if("OK".equals(ret)){
                    System.out.println("客户端将关闭连接");
                    Thread.sleep(500);
                    break;
                }

                out.close();
                input.close();
            } catch (Exception e) {
                System.out.println("客户端异常： "+e.getMessage());
                e.printStackTrace();
            } finally {
                if(socket != null){
                    try {
                        socket.close();
                    } catch (IOException e) {
                        socket = null;
                        System.out.println("客户端 finally 异常："+e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}



















