package com.wyt.test.topic;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * fanout模式 - 接收端
 *
 * @author wyt
 */
@Component
public class TopicModeReceiver {
    //交换机name
    private static final String EXCHANGE_NAME = "amq.topics";

    //队列名称
    private static final String QUEUE_NAME = "topicsQueue1";

    /**
     * 消息消费
     *
     * RabbitHandler 代表此方法为接受到消息后的处理方法
     */
    @RabbitListener(queues = QUEUE_NAME)
    @RabbitHandler
    public void recieve(Message msg, Channel channel) throws IOException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String timeStr = dtf.format(LocalDateTime.now());
        System.out.printf("%s - %s - [%s]接收消息: %s\n", this.getClass().getName(), timeStr, QUEUE_NAME, msg);

        try {
            //消息确认
            channel.basicAck(msg.getMessageProperties().getDeliveryTag(), false);
            System.out.println("应答成功");
        } catch (Exception e) {
            //拒绝消息，requeue位true表示将消息重新放回队列，false表示不再重新入队，如果配置了死信队列则进入死信队列
            //channel.basicReject(msg.getMessageProperties().getDeliveryTag(), false);
            //拒绝消息，与basic.reject区别就是同时支持多个消息
            //channel.basicNack(msg.getMessageProperties().getDeliveryTag(), false, false);
            System.out.println("应答失败");
        }
    }

    /**
     * 消息消费
     *
     * RabbitHandler 代表此方法为接受到消息后的处理方法
     */
    @RabbitListener(queues = QUEUE_NAME)
    @RabbitHandler
    public void recieve1(Message msg, Channel channel) throws IOException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String timeStr = dtf.format(LocalDateTime.now());
        System.out.printf("%s - %s - [%s]接收消息: %s\n", this.getClass().getName(), timeStr, QUEUE_NAME, msg);

        //拒绝消息，requeue位true表示将消息重新放回队列，false表示不再重新入队，如果配置了死信队列则进入死信队列
        channel.basicReject(msg.getMessageProperties().getDeliveryTag(), false);
        //拒绝消息，与basic.reject区别就是同时支持多个消息
        //channel.basicNack(msg.getMessageProperties().getDeliveryTag(), false, false);

        System.out.println("应答成功 - 拒绝消息");
    }

    /**
     * 消息消费
     *
     * RabbitHandler 代表此方法为接受到消息后的处理方法
     */
    /*@RabbitHandler
    public void recieve(String msg) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String timeStr = dtf.format(LocalDateTime.now());
        System.out.printf("%s - %s - [%s]接收消息: %s\n", this.getClass().getName(), timeStr, QUEUE_NAME, msg);

    }*/

}
