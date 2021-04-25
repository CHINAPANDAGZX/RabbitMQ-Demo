package com.gzx.rabbitmq.consumer.active;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

/**
 * @Description: TODO
 * @Auther: zhouxw
 * @Date: 2019/11/26 14:38
 * @company：CTTIC
 */
public class MessageConsumerHandler implements ChannelAwareMessageListener {

    private String Qname;

    public MessageConsumerHandler(String qname) {
        Qname = qname;
    }

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        System.out.format("收到来自通道： %s 的消息", Qname);
        System.out.println("消息内容: " + message.getBody().toString());
        System.out.println("消息详情: " + message.toString());
    }


}
