package cn.mrcode.javawebsocketdemo.stomp.controller;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
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
}
