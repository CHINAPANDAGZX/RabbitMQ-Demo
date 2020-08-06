package com.gzx.rabbitmq.provider.timeToLiveExchange;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.gzx.rabbitmq.provider.timeToLiveExchange.TimeToLiveRabbitConfig.*;

/**
 * @Author: GZX
 * @DateTime: 2020/8/6 20:38
 * @Description: 死信队列生产者
 */
@Component
public class TimeToLiveRabbitProvider {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMsg(String msg, int type){
        switch (type){
            case 1:
                rabbitTemplate.convertAndSend(DELAY_EXCHANGE_NAME, DELAY_QUEUEA_ROUTING_KEY, msg);
                break;
            case 2:
                rabbitTemplate.convertAndSend(DELAY_EXCHANGE_NAME, DELAY_QUEUEB_ROUTING_KEY, msg);
                break;
            default:
                break;
        }
    }

}
