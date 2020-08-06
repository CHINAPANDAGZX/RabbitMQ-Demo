package com.gzx.rabbitmq.consumer.timeToLiveExchange;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

import static com.gzx.rabbitmq.consumer.timeToLiveExchange.TimeToLiveRabbitConfig.DEAD_LETTER_QUEUEA_NAME;
import static com.gzx.rabbitmq.consumer.timeToLiveExchange.TimeToLiveRabbitConfig.DEAD_LETTER_QUEUEB_NAME;

/**
 * @Author: GZX
 * @DateTime: 2020/8/6 20:34
 * @Description: 死信队列消费者
 */
@Slf4j
@Component
public class TimeToLiveRabbitReciver {

    @RabbitListener(queues = DEAD_LETTER_QUEUEA_NAME)
    public void receiveA(Message message, Channel channel) throws IOException {
        String msg = new String(message.getBody());
        log.info("当前时间：{},死信队列A收到消息：{}", new Date().toString(), msg);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    @RabbitListener(queues = DEAD_LETTER_QUEUEB_NAME)
    public void receiveB(Message message, Channel channel) throws IOException {
        String msg = new String(message.getBody());
        log.info("当前时间：{},死信队列B收到消息：{}", new Date().toString(), msg);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
