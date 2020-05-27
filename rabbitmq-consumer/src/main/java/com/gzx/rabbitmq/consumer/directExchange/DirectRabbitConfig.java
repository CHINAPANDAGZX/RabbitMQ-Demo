package com.gzx.rabbitmq.consumer.directExchange;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @Author: GZX
 * @DateTime: 2020/5/26 14:13
 * @Description: 纯接收端可以不用配置
 */
@Configuration
public class DirectRabbitConfig {

    /**
    * @Author: GZX
    * @Description: 队列 起名：TestDirectQueue
    * @DateTime:  2020年5月26日14:15:26
    */
    @Bean
    public Queue TestDirectQueue() {
        return new Queue("TestDirectQueue",true);
    }

    /**
    * @Author: GZX
    * @Description: Direct交换机 起名：TestDirectExchange
    * @DateTime: 2020年5月26日14:15:36
    */
    @Bean
    DirectExchange TestDirectExchange() {
        return new DirectExchange("TestDirectExchange");
    }

    /**
    * @Author: GZX
    * @Description: 绑定  将队列和交换机绑定, 并设置用于匹配键：TestDirectRouting
    * @DateTime:  2020年5月26日14:17:42
    */
    @Bean
    Binding bindingDirect() {
        return BindingBuilder.bind(TestDirectQueue()).to(TestDirectExchange()).with("TestDirectRouting");
    }
}
