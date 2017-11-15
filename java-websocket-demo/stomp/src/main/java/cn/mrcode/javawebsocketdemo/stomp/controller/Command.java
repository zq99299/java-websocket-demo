package cn.mrcode.javawebsocketdemo.stomp.controller;

/**
 * <pre>
 *  Version         Date            Author          Description
 * ---------------------------------------------------------------------------------------
 *  1.0.0           2017/11/15     zhuqiang        -
 * </pre>
 * @author zhuqiang
 * @version 1.0.0 2017/11/15 11:18
 * @date 2017/11/15 11:18
 * @since 1.0.0
 */
public class Command {
    private int type;
    private String body;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "Command{" +
                "type=" + type +
                ", body='" + body + '\'' +
                '}';
    }
}
