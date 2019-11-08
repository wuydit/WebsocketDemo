package com.dl.websocketdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;


/**
 * <p>
 * 消息 前端控制器
 * </p>
 *
 * @author wuyd
 * @since 2019-11-07
 */
@Controller
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class MsgCenterController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Scheduled(fixedRate=5000)
    public void setMsg() {
        simpMessagingTemplate.convertAndSendToUser("wuydit","/queue/sysMsg", "1");
        simpMessagingTemplate.convertAndSend("/topic/horn","1");

    }

}

