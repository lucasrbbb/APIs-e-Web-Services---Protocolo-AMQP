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

    private final FanoutExchange fanout;

    public Producer(RabbitTemplate rabbitTemplate, AmqpAdmin amqpAdmin) {
        this.rabbitTemplate = rabbitTemplate;
        this.amqpAdmin = amqpAdmin;

        this.fanout = new FanoutExchange("transacoes.suspeitas.exchange", true, false);
        amqpAdmin.declareExchange(fanout);
    }

    public void enviarEventos(Transacao transacao) {
        try {
            String json = objectMapper.writeValueAsString(transacao);

            rabbitTemplate.convertAndSend(fanout.getName(), "", json);

            System.out.println("Transação publicada no exchange fanout: " + json);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



