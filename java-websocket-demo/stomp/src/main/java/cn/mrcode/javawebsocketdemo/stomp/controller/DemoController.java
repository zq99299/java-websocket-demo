package cn.mrcode.javawebsocketdemo.stomp.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : zhuqiang
 * @version : V1.0
 * @date : 2017/10/10 22:14
 */
@RestController
@RequestMapping(produces = BaseController.CHARSET_UTF8_JSON)
public class DemoController extends BaseController {
    @RequestMapping("/test")
    public String test() {
        return JSON.toJSONString("hello");
    }
}
