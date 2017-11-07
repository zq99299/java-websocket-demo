package cn.mrcode.javawebsocketdemo.websocket.ws;

import com.alibaba.fastjson.support.spring.FastjsonSockJsMessageCodec;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * @author : zhuqiang
 * @version : V1.0
 * @date : 2017/10/10 22:32
 */
@Configuration
@EnableWebSocket  // 开启websocket支持
public class WebSocketConfig implements WebSocketConfigurer {
    public WebSocketConfig() {
        System.out.println("xxxxxxxxxxxx");
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // 这个是支持原生websocket的节点
        registry.addHandler(myHandler(), "/myHandler").setAllowedOrigins("*");
        // 对sockjs的支持节点
        registry.addHandler(myHandler(), "/myHandlerSockjs").setAllowedOrigins("*")
                .withSockJS().setMessageCodec(new FastjsonSockJsMessageCodec());

        ;
    }

    @Bean
    public WebSocketHandler myHandler() {
        return new MyHandler();
    }
}
