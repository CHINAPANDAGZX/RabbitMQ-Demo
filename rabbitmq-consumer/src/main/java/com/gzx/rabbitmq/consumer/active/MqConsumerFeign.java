package com.gzx.rabbitmq.consumer.active;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description: TODO
 * @Auther: zhouxw
 * @Date: 2019/11/26 16:24
 * @company：CTTIC
 */
@RestController
public class MqConsumerFeign {

    @Autowired
    private CachingConnectionFactory connectionFactory;

    @Autowired
    private QueueService queueService;

    @GetMapping("add_new_listener")
    public String addNewListener(String queueName) {
        SimpleMessageListenerContainer container = mqMessageContainer(queueName);
        List<String> queueNameList = queueService.getMQJSONArray();
        int count = 0;
        while(!queueNameList.contains(queueName)){
            queueNameList = queueService.getMQJSONArray();
            count++;
            try {
                Thread.sleep(1000);
            }catch (Exception e){
                e.printStackTrace();
            }
            if(count >=5){
                return "动态添加监听失败";
            }
        }
        container.addQueueNames(queueName);
        container.start();
        long consumerCount = container.getActiveConsumerCount();
        return "修改成功:现有队列监听者["+consumerCount+"]个";
    }

    public SimpleMessageListenerContainer mqMessageContainer(String queueName) throws AmqpException {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        List<String> list = queueService.getMQJSONArray();
//        container.setQueueNames(queueName);
        container.setExposeListenerChannel(true);
        container.setPrefetchCount(1);//设置每个消费者获取的最大的消息数量
        container.setConcurrentConsumers(1);//消费者个数
        container.setAcknowledgeMode(AcknowledgeMode.AUTO);//设置确认模式为手工确认
        MessageConsumerHandler activeReceiver = new MessageConsumerHandler(queueName);
        container.setMessageListener(activeReceiver);//监听处理类
        return container;
    }
}
