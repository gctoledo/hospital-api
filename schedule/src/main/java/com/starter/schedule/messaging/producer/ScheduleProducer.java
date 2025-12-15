package com.starter.schedule.messaging.producer;

import com.starter.schedule.config.RabbitMQConfig;
import com.starter.schedule.messaging.event.CreateConsultationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ScheduleProducer {

    private final RabbitTemplate rabbitTemplate;

    public void sendConsultationRequest(CreateConsultationEvent event) {
        log.info("Enviando agendamento de consulta para MS Clinic: {}", event);

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.SCHEDULE_EXCHANGE,
                RabbitMQConfig.CONSULTATION_REQUEST_KEY,
                event
        );

        log.info("Agendamento de consulta enviado para MS Clinic: {}", event);
    }
}
