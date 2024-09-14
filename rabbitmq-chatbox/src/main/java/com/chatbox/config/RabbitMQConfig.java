package com.chatbox.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Value("${rabbitmq.queue.message.request}")
    private String queueMsgRequest;

    @Value("${rabbitmq.queue.messageFile.request}")
    private String queueMsgFileRequest;

    @Value("${rabbitmq.queue.message.response}")
    private String queueMsgReponse;

    @Value("${rabbitmq.queue.notification.request}")
    private String queueNotificationRequest;

    @Value("${rabbitmq.queue.notification.response}")
    private String queueNotificationReponse;

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routingkey.msg.request}")
    private String routingKeyMsgRequest;

    @Value("${rabbitmq.routingkey.msgFile.request}")
    private String routingKeyMsgFileRequest;

    @Value("${rabbitmq.routingkey.msg.response}")
    private String routingKeyMsgResponse;

    @Value("${rabbitmq.routingkey.notification.request}")
    private String routingKeyNotificationRequest;

    @Value("${rabbitmq.routingkey.notification.response}")
    private String routingKeyNotificationResponse;

    @Bean
    public Queue queueMsgRequest() {
        return new Queue(queueMsgRequest);
    }

    @Bean
    public Queue queueMsgFileRequest() {
        return new Queue(queueMsgFileRequest);
    }

    @Bean
    public Queue queueNotificationRequest() {
        return new Queue(queueNotificationRequest);
    }

    @Bean
    public Queue queueMsgResponse() {
        return new Queue(queueMsgReponse);
    }

    @Bean
    public Queue queueNotificationResponse() {
        return new Queue(queueNotificationReponse);
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(exchange);
    }

    @Bean
    public Binding bindingMsgRequest() {
        return BindingBuilder
                .bind(queueMsgRequest())
                .to(exchange())
                .with(routingKeyMsgRequest);
    }

    @Bean
    public Binding bindingMsgFileRequest() {
        return BindingBuilder
                .bind(queueMsgFileRequest())
                .to(exchange())
                .with(routingKeyMsgFileRequest);
    }

    @Bean
    public Binding bindingNotificationRequest() {
        return BindingBuilder
                .bind(queueNotificationRequest())
                .to(exchange())
                .with(routingKeyNotificationRequest);
    }

    @Bean
    public Binding bindingMsgResponse() {
        return BindingBuilder
                .bind(queueMsgResponse())
                .to(exchange())
                .with(routingKeyMsgResponse);
    }

    @Bean
    public Binding bindingNotificationResponse() {
        return BindingBuilder
                .bind(queueNotificationResponse())
                .to(exchange())
                .with(routingKeyNotificationResponse);
    }

    @Bean
    public MessageConverter converter(){
        return  new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
}
