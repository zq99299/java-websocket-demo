package cn.mrcode.javawebsocketdemo.stomp.ws;

import com.alibaba.fastjson.support.spring.FastjsonSockJsMessageCodec;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

/**
 * @author : zhuqiang
 * @version : V1.0
 * @date : 2017/10/10 22:32
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/stomp").setAllowedOrigins("*")
                .withSockJS()
                .setMessageCodec(new FastjsonSockJsMessageCodec())
//                .setClientLibraryUrl("//cdn.jsdelivr.net/sockjs/1/sockjs.min.js");
        ;
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // 前端发送消息和主动访问对应的消息地址前缀
        config.setApplicationDestinationPrefixes("/app");
        // 开启消息代理
        config.enableSimpleBroker("/topic", "/queue");
        // 点对点支持前缀
        config.setUserDestinationPrefix("/user/");
    }

}
