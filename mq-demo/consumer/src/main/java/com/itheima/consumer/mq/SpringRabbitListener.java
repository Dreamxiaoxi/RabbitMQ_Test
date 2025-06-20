package com.itheima.consumer.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SpringRabbitListener {

    @RabbitListener(queues = "simple.queue")
    public void ListenSimpleQueue(String message) {
        log.info("监听到simple.queue的消息:【{}】",message);
    }

    @RabbitListener(queues = "work.queue")
    public void ListenWorkQueue1(String message) throws InterruptedException {
        Thread.sleep(25);
        System.out.println("消费者1监听到work.queue的消息:"+message);
    }


    @RabbitListener(queues = "work.queue")
    public void ListenWorkQueue2(String message) throws InterruptedException {
        Thread.sleep(200);
        System.err.println("消费者2监听到work.queue的消息:"+message);
    }

    @RabbitListener(queues = "fanout.queue1")
    public void ListenFanoutQueue1(String message) {
        log.info("消费者1监听到fanout.queue1的消息:【{}】",message);
    }

    @RabbitListener(queues = "fanout.queue2")
    public void ListenFanoutQueue2(String message) {
        log.info("消费者2监听到fanout.queue2的消息:【{}】",message);
    }

}
