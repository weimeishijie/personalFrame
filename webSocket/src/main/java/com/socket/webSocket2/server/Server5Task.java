package com.socket.webSocket2.server;

import java.io.*;
import java.net.Socket;

/**
 * Created by mj on 2017/11/10.
 * 用来处理socket请求
 */
public class Server5Task implements Runnable{

    private Socket socket;

    // 构造函数
    public Server5Task(Socket socket) {
        this.socket = socket;
    }

    // 实现Runnable 接口应该由那些打算通过某一线程执行其实例的类来实现。
    // 类必须定义一个称为 run 的无参数方法。
    public void run() {
        // 调用与客户端实现的方法
        try {
            handleSocket();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 跟客户端Socket进行通信
    private void handleSocket(){
        BufferedReader br = null;
        Writer writer = null;

        try {
            br = new BufferedReader(new InputStreamReader(socket.getInputStream(),"GBK"));
            StringBuilder sb = new StringBuilder();
            String temp;
            int index;
            /**
             * BufferedReader的readLine方法是一次读一行的，这个方法是阻塞的，
             * 直到它读到了一行数据为止程序才会继续往下执行，那么readLine什么时候才会读到一行呢？
             * 直到程序遇到了换行符或者是对应流的结束符readLine方法才会认为读到了一行，才会结束其阻塞，让程序继续往下执行。
             * 所以我们在使用BufferedReader的readLine读取数据的时候一定要记得在对应的输出流里面一定要写入换行符
             * （流结束之后会自动标记为结束，readLine可以识别），写入换行符之后一定记得如果输出流不是马上关闭的情况下记得flush一下，
             * 这样数据才会真正的从缓冲区里面写入
             */
            while ((temp = br.readLine()) != null){
                System.out.println(temp);
                // 遇到 eof 时就结束接受 方法indexOf()的判断：如果字符串中没有此字符则返回 -1，有则返回其对应的下标
                if((index = temp.indexOf("eof")) != -1){
                    sb.append(temp.substring(0,index));
                    break;// 跳出循环
                }
                sb.append(temp);
            }
            System.out.println("客户端： "+ sb);



             // 服务端下发给客户端的参数，设置传递参数的编码格式为：UTF-8
            writer = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
            writer.write("你好，客户端");
            writer.write("eof\n");
            // 是当前线程休眠，单位为毫秒值。可测试客户端代码里面的超时设置
            Thread.sleep(12*100);
            // 写完后要记得flush
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(writer != null){
                    writer.close();
                }
                if(br != null){
                    br.close();
                }
                if(socket != null){
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
