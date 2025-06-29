package br.edu.utfpr.td.cotsi.transacoes.producer;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.amqp.core.AmqpAdmin;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("br.edu.utfpr.td.cotsi.transacoes.producer")
public class TransacoesProducerApp {

	@Autowired
	private AmqpAdmin amqpAdmin;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	private Queue filaTransacoes;

	public static void main(String[] args) throws Exception {
		SpringApplication.run(TransacoesProducerApp.class, args);
	}

	@PostConstruct
	public void criarFila() {
		filaTransacoes = new Queue("transacoes.financeiras", true);
		amqpAdmin.declareQueue(filaTransacoes);
		processarArquivotransacoes();
	}

	public void processarArquivotransacoes() {
	    List<Transacao> transacoes = new LeitorArquivo().lerArquivo();
	    ObjectMapper objectMapper = new ObjectMapper();

	    for (Transacao transacao : transacoes) {
	        try {
	            String json = objectMapper.writeValueAsString(transacao);
	            rabbitTemplate.convertAndSend(this.filaTransacoes.getName(), json);
	            System.out.println("Transação enviada: " + json);
	        } catch (Exception e) {
	            System.err.println("Erro ao converter transação para JSON: " + e.getMessage());
	        }
	    }
	}
}
