package br.edu.utfpr.td.cotsi.exchange.producer;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class Producer {

    private final RabbitTemplate rabbitTemplate;
    private final AmqpAdmin amqpAdmin;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final FanoutExchange fanout1 = new FanoutExchange("policia.federal", true, false);
    private final FanoutExchange fanout2 = new FanoutExchange("receita.federal", true, false);

    public Producer(RabbitTemplate rabbitTemplate, AmqpAdmin amqpAdmin) {
        this.rabbitTemplate = rabbitTemplate;
        this.amqpAdmin = amqpAdmin;

        amqpAdmin.declareExchange(fanout1);
        amqpAdmin.declareExchange(fanout2);
    }

    public void enviarEventos(Transacao transacao) {
        try {
            String json = objectMapper.writeValueAsString(transacao);

            rabbitTemplate.convertAndSend(fanout1.getName(), "", json);
            rabbitTemplate.convertAndSend(fanout2.getName(), "", json);

            System.out.println("Transação publicada nos exchanges: " + json);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


