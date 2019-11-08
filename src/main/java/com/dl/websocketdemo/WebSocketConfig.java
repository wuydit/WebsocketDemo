package com.dl.websocketdemo;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * @author wuyd
 * 创建时间：2019/10/22 13:48
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * 注册 Stomp的端点 配置对外暴露访问的端点
     * @param registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //添加STOMP协议的端点。
        registry.addEndpoint("/endpoint")
                // 这个URL是供WebSocket客户端或SockJS客户端连接服务端访问的地址。
                //添加允许跨域访问
                .setAllowedOrigins("*")
                //指定端点使用SockJS协议
                .withSockJS();
    }

    /**
     * 配置消息代理
     * @param registry
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {


        // 自定义调度器，用于控制心跳线程
//        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
//        // 线程池线程数，心跳连接开线程
//        taskScheduler.setPoolSize(1);
//        // 线程名前缀
//        taskScheduler.setThreadNamePrefix("websocket-heartbeat-thread-");
//        // 初始化
//        taskScheduler.initialize();


        //启动简单Broker，客户端请求地址符合配置的前缀，消息才会发送到这个broker
        //客户端订阅当前服务端时需要添加以下请求前缀，topic一般用于广播推送，queue用于点对点推送
        registry.enableSimpleBroker("/topic","/user");
//                .setHeartbeatValue(new long[]{10000,10000});
//                .setTaskScheduler(taskScheduler);
        registry.setApplicationDestinationPrefixes("/app");


        /*
         *  "/app" 为配置应用服务器的地址前缀，表示所有以/app 开头的客户端消息或请求
         *  都会路由到带有@MessageMapping 注解的方法中
         */
        registry.setApplicationDestinationPrefixes("/app");

        /*
         *  1. 配置一对一消息前缀， 客户端接收一对一消息需要配置的前缀 如“'/user/'+userid + '/message'”，
         *     是客户端订阅一对一消息的地址 stompClient.subscribe js方法调用的地址
         *  2. 使用@SendToUser发送私信的规则不是这个参数设定，在框架内部是用UserDestinationMessageHandler处理，
         *     而不是而不是 AnnotationMethodMessageHandler 或  SimpleBrokerMessageHandler
         *     or StompBrokerRelayMessageHandler，是在@SendToUser的URL前加“user+sessionId"组成
         */
        registry.setUserDestinationPrefix("/user");
    }
}
