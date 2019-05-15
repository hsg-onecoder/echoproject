package com.onecoder.netty.demo4;

/**
 * <p>Title: Messge</p>
 * <p>Description: 消息数据包</p>
 *
 * @author hushiguo
 * @date 2019/4/3 19:41
 */
public class Message {

    /**
     * 可以指整个消息的长度，也可以指消息体的长度
     */
    private int length;

    /**
     * 消息体
     */
    private String body;

    public Message() {
    }

    public Message(int length, String body) {
        this.length = length;
        this.body = body;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
