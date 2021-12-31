package com.wyt.test.direct;

import com.rabbitmq.client.*;

import java.io.IOException;
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
        //开启消息confirm
        channel.confirmSelect();
        //消息confirm listener
        channel.addConfirmListener(new ConfirmListener() {
            //消息发功成功
            @Override
            public void handleAck(long deliveryTag, boolean multiple) {
                System.out.println("消息发送成功");
            }

            //消息发功失败
            @Override
            public void handleNack(long deliveryTag, boolean multiple) {
                System.out.println("消息发送失败");
            }
        });
        //消息return listener
        channel.addReturnListener(new ReturnListener() {
            @Override
            public void handleReturn(int replyCode, String replyText, String exchange, String routingKey, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消息被退回");
                System.out.println("replyCode = " + replyCode);
                System.out.println("replyText = " + replyText);
                System.out.println("exchange = " + exchange);
                System.out.println("routingKey = " + routingKey);
                System.out.println("msg = " + new String(body));
            }
        });

        Scanner sc = new Scanner(System.in);
        // hasNextLine()方法判断当前是否有输入，当键盘有输入后执行循环
        while (sc.hasNextLine()) {
            //读取字符串型输入
            String msg = sc.nextLine();

            /*
             * exchange：交换机名称
             * routingKey：路由键
             * props：消息属性字段，比如消息头部信息等等
             * mandatory：当mandatory标志位设置为true时，
               如果exchange根据自身类型和消息routingKey无法找到一个合适的queue存储消息，
               那么broker会调用basic.return方法将消息返还给生产者;
               当mandatory设置为false时，出现上述情况broker会直接将消息丢弃;
               通俗的讲，mandatory标志告诉broker代理服务器至少将消息route到一个队列中，否则就将消息return给发送者;
               默认false;
             * body：消息主体部分
             */
            //channel.basicPublish("amq.direct", QUEUE_NAME, null, msg.getBytes());
            channel.basicPublish("amq.direct", QUEUE_NAME, true, null, msg.getBytes());

            //System.out.println("消息发送完毕");
        }
    }

}
