package com.starter.clinic.messaging.consumer;

import com.starter.clinic.config.RabbitMQConfig;
import com.starter.clinic.messaging.event.CreateConsultationEvent;
import com.starter.clinic.service.ConsultationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ConsultationConsumer {

    private final ConsultationService consultationService;

    @RabbitListener(queues = RabbitMQConfig.CONSULTATION_REQUEST_QUEUE)
    public void handleCreateConsultationRequest(CreateConsultationEvent event) {
        log.info("Recebendo solicitação de agendamento de consulta");

        try {
            consultationService.create(event.id());

            log.info("Consulta agendada com sucesso");
        } catch (Exception e) {
            log.error("Erro ao processar solicitação de agendamento de consulta: {}", e.getMessage(), e);
        }
    }
}
