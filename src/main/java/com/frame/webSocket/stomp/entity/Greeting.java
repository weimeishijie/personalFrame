package com.frame.webSocket.stomp.entity;

/**
 * Created by mj on 2017/11/7.
 */
public class Greeting {
    private String content;

    public Greeting(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
