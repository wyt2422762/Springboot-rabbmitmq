package com.wyt.test.topic;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * rabbitmq - 直连队列模式 - 接收测试
 *
 * @author wyt
 */
public class TopicMode {
    //交换机name
    private static final String EXCHANGE_NAME = "amq.topics";

    //队列名称
    private static final String QUEUE_NAME = "topicsQueue1";

    public static void main(String[] args) throws Exception {
        //连接设置
        ConnectionFactory factory = new ConnectionFactory();
        // ip
        factory.setHost("47.105.71.238");
        //设置虚拟主机，类似于MySQL的数据库名称
        factory.setVirtualHost("my_vhost");
        //用户名
        factory.setUsername("test");
        //密码
        factory.setPassword("test");

        //建立连接
        Connection connection = factory.newConnection();
        //建立通道
        Channel channel = connection.createChannel();

        channel.basicConsume(QUEUE_NAME, false, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("收到消息：" + new String(body));
                //手动应答
                channel.basicAck(envelope.getDeliveryTag(), false);
                //休眠5秒
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
