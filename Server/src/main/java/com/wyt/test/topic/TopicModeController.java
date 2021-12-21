package com.wyt.test.topic;

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
@RequestMapping("topicMode")
public class TopicModeController {
    //交换机name
    private static final String EXCHANGE_NAME = "amq.topic";

    //topics
    //#匹配一个或多个词
    //*仅匹配一个词。
    private static final String TOPIC_PEOPLE = "china.people.life";
    private static final String TOPIC_OTHER = "china.other";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送消息 - 直连队列模式
     *
     * @param msg 消息
     * @return res
     */
    @GetMapping("send")
    public String send(@RequestParam("msg") String msg, @RequestParam("topic") String topic) {
        //发送消息
        System.out.println("消息内容：" + msg);
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, topic, msg);
        return "发送成功";
    }

}
