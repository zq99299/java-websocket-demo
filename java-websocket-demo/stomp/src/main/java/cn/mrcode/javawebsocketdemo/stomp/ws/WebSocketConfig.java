package cn.mrcode.javawebsocketdemo.stomp.ws;

import com.alibaba.fastjson.support.spring.FastjsonSockJsMessageCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

/**
 * @author : zhuqiang
 * @version : V1.0
 * @date : 2017/10/10 22:32
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {
    private Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/stomp").setAllowedOrigins("*")
                .addInterceptors(new HttpSessionHandshakeInterceptor())
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

    /**
     * 以下是注册自定义身份验证拦截器的示例服务器端配置。
     * 请注意，拦截器只需要认证并设置CONNECT上的用户头Message。
     * Spring将注意并保存已验证的用户，并将其与后续STOMP消息相关联在一起：
     * @param registration
     */
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.setInterceptors(new ChannelInterceptorAdapter() {

            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor =
                        MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);


                // 只要链接一次。后面每次都是根据类型走，而且用户都会存在
                StompCommand command = accessor.getCommand();
                if (StompCommand.CONNECT.equals(command)) {
                    MyPrincipal principal = new MyPrincipal(accessor.getLogin());
                    accessor.setUser(principal);
                }
                log.info("请求用户:{}", accessor.getUser());
                return message;
            }
        });
    }
}
