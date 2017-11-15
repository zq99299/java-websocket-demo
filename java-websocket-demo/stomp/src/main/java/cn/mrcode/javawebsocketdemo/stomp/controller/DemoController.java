package cn.mrcode.javawebsocketdemo.stomp.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author : zhuqiang
 * @version : V1.0
 * @date : 2017/10/10 22:14
 */
@RestController
@RequestMapping(produces = BaseController.CHARSET_UTF8_JSON)
public class DemoController extends BaseController {
    @Autowired
    private SimpMessagingTemplate template;

    {
        new Thread(() -> {
            mockPublicNews();
        }).start();

        new Thread(() -> {
            mockSubscribeNews();
        }).start();
    }

    /**
     * 模拟公共新闻推送
     */
    public void mockPublicNews() {
        while (true) {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            List<String> list = new ArrayList<>();

            // 模拟产生3条
            list.add("清华大学16位学霸为一事现身PK 简历吓坏网友 - " + randomInt());
            list.add("闹市区树上长出6斤多蘑菇 保安大叔炒着吃了(图) - " + randomInt());
            list.add("女司机第2次上路撞死过路老人 教练被抓走 司法频道 - " + randomInt());


            // 广播消息，注意记住这里的地址
            template.convertAndSend("/topic/public_news", JSON.toJSONString(list));
        }
    }

    private int randomInt() {
        return RandomUtils.nextInt(100, 1000);
    }

    /**
     * 记住这里的地址
     */
    @MessageMapping("/queue/other")
    public void otherSubscribe(@Payload Command command, Principal principal, SimpMessageHeaderAccessor headerAccessor) {
        int type = command.getType();
        if (type == 1) { // 订阅
            subscribeNews(principal.getName(), command.getBody());
        } else {
            unsubscribeNews(principal.getName(), command.getBody());
        }
        System.out.println(command);
        System.out.println(principal);
    }


    private Map<String, SubscribeParams> subscribeMap = new HashMap<>();

    /**
     * 订阅线程处理
     */
    private void mockSubscribeNews() {
        while (true) {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            subscribeMap.forEach((user, params) -> {
                JSONObject result = new JSONObject();
                if (params.isComic()) {
                    result.put("comics", buildComic());
                }
                if (params.isGossip()) {
                    result.put("gossips", buildGossip());
                }
                template.convertAndSendToUser(user, "/queue/other", result.toJSONString());
            });
        }
    }

    private List<String> buildComic() {
        List<String> list = new ArrayList<>();
        list.add("久保带人大爆八卦内幕：「死神」差点拍好莱坞版？想休息15年不画新作品？ - " + randomInt());
        list.add("1月霸权番预定？「DARLING in the FRANXX」首曝PV，户松遥等参演！ - " + randomInt());
        return list;
    }

    private List<String> buildGossip() {
        List<String> list = new ArrayList<>();
        // 模拟产生3条
        list.add("言承旭自卑感作祟感情路不顺 羡慕周杰伦完美成家 - " + randomInt());
        list.add("贺涵老卓再合作？靳东晒和陈道明自拍笑出一脸褶 - " + randomInt());
        list.add("林心如被娶走改追回林志玲？言承旭经纪人打脸回应 - " + randomInt());
        return list;
    }


    /**
     * 接受处理参数
     * @param user     用户名
     * @param newsType 订阅的新闻类别是什么
     */
    private void subscribeNews(String user, String newsType) {
        SubscribeParams subscribeParams = null;
        if (subscribeMap.containsKey(user)) {
            subscribeParams = subscribeMap.get(user);
        } else {
            subscribeParams = new SubscribeParams();
            subscribeMap.put(user, subscribeParams);
        }

        if ("comic".equals(newsType)) {
            subscribeParams.setComic(true);
        } else {
            subscribeParams.setGossip(true);
        }
    }

    /**
     * 取消订阅
     * @param user
     * @param newsType
     */
    private void unsubscribeNews(String user, String newsType) {
        SubscribeParams subscribeParams = null;
        if (subscribeMap.containsKey(user)) {
            subscribeParams = subscribeMap.get(user);
        } else {
            subscribeParams = new SubscribeParams();
            subscribeMap.put(user, subscribeParams);
        }

        if ("comic".equals(newsType)) {
            subscribeParams.setComic(false);
        } else {
            subscribeParams.setGossip(false);
        }
    }
}
