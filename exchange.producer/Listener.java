package br.edu.utfpr.td.cotsi.transacoes.consumer;

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
            
            transacoeSuspeitas(transacao);
            
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
    
    public void transacoeSuspeitas(Transacao transacao) {
        if (transacao.getValor() >= 5000) {
            System.out.println("Transação suspeita: " + transacao);
            try {
                String json = objectMapper.writeValueAsString(transacao);
                
                rabbitTemplate.convertAndSend("transacoes.suspeitas", json);
                
                System.out.println("Mensagem enviada para a fila transacoes.suspeitas: " + json);
            } catch (Exception e) {
                System.err.println("Erro ao enviar mensagem suspeita: " + e.getMessage());
            }
        }
    }
    
}
