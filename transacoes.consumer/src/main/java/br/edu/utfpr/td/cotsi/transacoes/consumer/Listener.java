package br.edu.utfpr.td.cotsi.transacoes.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class Listener {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @RabbitListener(queues = "transacoes.financeiras")
    public void listen(String json) {
        try {
            Transacao transacao = objectMapper.readValue(json, Transacao.class);
            processarTransacao(transacao);
        } catch (Exception e) {
            System.err.println("Erro ao processar JSON: " + e.getMessage());
        }
    }

    public void processarTransacao(Transacao transacao) {
        try {
            Thread.sleep(1000);
            System.out.println("Transação recebida: " + transacao);
            chamar a classe aqui
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
    
}
