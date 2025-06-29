package br.edu.utfpr.td.cotsi.exchange.producer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class Listener {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = "transacoes.suspeitas")
    public void listen(String json) {
        try {
            Transacao transacao = objectMapper.readValue(json, Transacao.class);
            processarTransacao(transacao, json);
        } catch (Exception e) {
            System.err.println("Erro ao processar JSON: " + e.getMessage());
        }
    }

    public void processarTransacao(Transacao transacao, String json) {
        try {
            Thread.sleep(1000);
            System.out.println("Transação suspeita recebida: " + transacao);

            rabbitTemplate.convertAndSend("transacoes.suspeitas.exchange", "", json);

            System.out.println("Transação republicada para Policia Federal e Receita Federal");

        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}

