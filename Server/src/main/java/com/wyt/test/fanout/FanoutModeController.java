package com.wyt.test.fanout;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 直连队列模式 - 发送端
 * @author wyt
 */
@RestController
@RequestMapping("fanoutMode")
public class FanoutModeController {
    //交换机name
    private static final String EXCHANGE_NAME = "amq.fanout";

    //ROUTING_KEY
    private static final String ROUTING_KEY1 = "fanoutQueue1";
    private static final String ROUTING_KEY2 = "fanoutQueue2";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送消息 - 直连队列模式
     *
     * @param msg 消息
     * @return res
     */
    @GetMapping("send")
    public String send(@RequestParam("msg") String msg) {
        //发送消息
        System.out.println("消息内容：" + msg);
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, "", msg);
        return "发送成功";
    }

}
