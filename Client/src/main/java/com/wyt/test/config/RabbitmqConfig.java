package com.wyt.test.config;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * rabbmitmq配置
 * @author wyt
 */
@Configuration
public class RabbitmqConfig {
    @Autowired
    private CachingConnectionFactory connectionFactory;

    /**
     * 创建 RabbitTemplate bean 连接 rabbitmq
     * @return
     */
    @Bean
    public RabbitTemplate rabbitTemplate(){
        return new RabbitTemplate(connectionFactory);
    }

}
