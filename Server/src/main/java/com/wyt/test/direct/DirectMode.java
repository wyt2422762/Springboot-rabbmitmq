package com.wyt.test.direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.Scanner;

/**
 * rabbitmq - 直连队列模式 - 发送测试
 *
 * @author wyt
 */
public class DirectMode {
    private static final String QUEUE_NAME = "demoQueue";

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

        Scanner sc = new Scanner(System.in);
        // hasNextLine()方法判断当前是否有输入，当键盘有输入后执行循环
        while(sc.hasNextLine()) {
            //读取字符串型输入
            String msg = sc.nextLine();

            /**
             * 发送消息
             * 参数一：交换机名称
             * 参数二：发往消息的队列名称
             * 参数三：传递消息的额外设置 eg：MessageProperties.PERSISTENT_TEXT_PLAIN 消息持久化  前提是队列持久化
             * 参数四：消息的具体内容  字节类型
             */
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());

            System.out.println("消息发送完毕");
        }
    }

}
