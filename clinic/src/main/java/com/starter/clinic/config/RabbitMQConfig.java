package com.starter.clinic.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String SCHEDULE_EXCHANGE = "hospital.schedule";

    public static final String CONSULTATION_REQUEST_QUEUE = "consultation.request.queue";

    public static final String CONSULTATION_REQUEST_KEY = "consultation.requested";

    @Bean
    public TopicExchange scheduleExchange() {
        return new TopicExchange(SCHEDULE_EXCHANGE);
    }

    @Bean
    public Queue consultationRequestQueue() {
        return QueueBuilder.durable(CONSULTATION_REQUEST_QUEUE).build();
    }

    @Bean
    public Binding consultationRequestBinding() {
        return BindingBuilder
                .bind(consultationRequestQueue())
                .to(scheduleExchange())
                .with(CONSULTATION_REQUEST_KEY);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}
