package cn.mrcode.javawebsocketdemo.stomp.controller;

/**
 * 订阅参数
 * <pre>
 *  Version         Date            Author          Description
 * ---------------------------------------------------------------------------------------
 *  1.0.0           2017/11/15     zhuqiang        -
 * </pre>
 * @author zhuqiang
 * @version 1.0.0 2017/11/15 14:17
 * @date 2017/11/15 14:17
 * @since 1.0.0
 */
public class SubscribeParams {
    private boolean comic; // 动漫
    private boolean gossip; // 八卦

    public boolean isComic() {
        return comic;
    }

    public void setComic(boolean comic) {
        this.comic = comic;
    }

    public boolean isGossip() {
        return gossip;
    }

    public void setGossip(boolean gossip) {
        this.gossip = gossip;
    }
}
