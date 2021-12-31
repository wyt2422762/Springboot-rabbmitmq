package com.wyt.test.direct;

import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 直连队列模式 - 发送端
 *
 * @author wyt
 */
@RestController
@RequestMapping("directMode")
public class DirectModeController implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnsCallback {
    private static final String QUEUE_NAME = "demoQueue";

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
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setReturnsCallback(this);
        rabbitTemplate.convertAndSend("amq.direct", QUEUE_NAME, msg);
        return "发送成功";
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        System.out.println("ack = " + ack);
        if(ack) {
            System.out.println("消息发送成功");
        } else {
            System.out.printf("消息发送失败，失败原因：%s\n", cause);
        }
    }

    @Override
    public void returnedMessage(ReturnedMessage returned) {
        System.out.println("replyCode = " + returned.getReplyCode());
        System.out.println("replyText = " + returned.getReplyText());
        System.out.println("exchange = " + returned.getExchange());
        System.out.println("routingKey = " + returned.getRoutingKey());
        System.out.println("msg = " + returned.getMessage());
    }
}
