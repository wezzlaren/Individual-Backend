package com.kwitterbackend.post_service.configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageBrokerConfiguration {

    @Value("${kwitter.rabbitmq.queue}")
    private String queueName;

    @Value("${kwitter.rabbitmq.queuedelete}")
    private String queueDelete;

    @Value("${kwitter.rabbitmq.exchange}")
    private String exchange;
    @Value("${kwitter.rabbitmq.routingkey}")
    private String routingKey;
    @Value("${kwitter.rabbitmq.routingkeydelete}")
    private String routingKeyDelete;

    @Bean
    public Queue queue() {
        return new Queue(queueName);
    }

    @Bean
    public Queue queueDelete() {return new Queue(queueDelete);}

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(exchange);
    }

    @Bean
    Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }

    @Bean
    Binding bindingDelete(Queue queueDelete, DirectExchange exchange) {
        return BindingBuilder.bind(queueDelete).to(exchange).with(routingKeyDelete);
    }
}
