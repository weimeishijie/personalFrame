package com.socket.webSocket2.client;

import java.io.*;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * Created by mj on 2017/11/10.
 * 客户端代码
 */
public class Client5 {
    public static void main(String[] args) {
        Socket client = null;
        Writer writer = null;
        BufferedReader br = null;

        // 要链接的服务端IP地址
        String host = "127.0.0.1";
        // 要链接的服务端对应的监听端口号
        int port = 8899;
        try {
            // 与服务端建立连接
            client = new Socket(host,port);
            // 建立连接后就可以网服务端写数据了
            // 客户你发送给服务端的参数，设置传递参数的编码格式为：GBK
            writer = new OutputStreamWriter(client.getOutputStream(),"GBK");
            writer.write("你好服务端");
            writer.write("eof\n");
            writer.flush();


            // 写完以后进行读操作
            br = new BufferedReader(new InputStreamReader(client.getInputStream(),"UTF-8"));
            // 设置超时间为10秒
            client.setSoTimeout(10*1000);
            StringBuffer sb = new StringBuffer();
            String temp;
            int index;
            try{
                while ((temp = br.readLine()) != null){
                    if ((index = temp.indexOf("eof")) != -1){
                        sb.append(temp.substring(0,index));
                        break;
                    }
                    sb.append(temp);
                }
            }catch (SocketTimeoutException e){
                System.out.println("数据读取超时。");
            }
            System.out.println("服务端： "+sb);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(writer != null){
                    writer.close();
                }
                if(writer != null){
                    br.close();
                    client.close();
                }
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    }
}
