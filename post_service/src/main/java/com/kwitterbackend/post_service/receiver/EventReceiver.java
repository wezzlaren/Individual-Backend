package com.kwitterbackend.post_service.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class EventReceiver {

    @RabbitListener(queues = "${kwitter.rabbitmq.queue}")
    public void receive(String event) {
        System.out.println("received the event!"+ event);
        // Convert to object.


    }
}
