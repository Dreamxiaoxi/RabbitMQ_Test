package com.itheima.publisher;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.UUID;

@Slf4j
@SpringBootTest
public class PublisherTest {
    @Autowired
    private RabbitTemplate rabbitTemplate;



    @Test
    void TestSimpleQueue() {
        String queueName = "simple.queue";
        String message = "Hello springamqp";

        //创建correlationData
        CorrelationData cd = new CorrelationData(UUID.randomUUID().toString());
        cd.getFuture().addCallback(new ListenableFutureCallback<CorrelationData.Confirm>() {
            @Override
            public void onFailure(Throwable ex) {
                log.error("Spring amqp处理异常");
            }

            @Override
            public void onSuccess(CorrelationData.Confirm result) {
                if(result.isAck()){
                    log.debug("收到ComfirmCallback 消息发送成功");
                }else {
                    log.error("收到ComfirmCallback 消息发送失败");
                }
            }
        });

        rabbitTemplate.convertAndSend(queueName, message);

    }


    @Test
    void TestWorkQueue() {
        String queueName = "work.queue";
        String message = "Hello springamqp";

        for (int i = 0; i < 50; i++)
            rabbitTemplate.convertAndSend(queueName, message + i);
    }


    @Test
    void TestFanoutQueue() {
        String exchangeName = "hmall.fanout";
        String message = "Hello everyone";

        rabbitTemplate.convertAndSend(exchangeName, null, message);

    }

}

