package br.edu.utfpr.td.cotsi.transacoes.consumer;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.FanoutExchange;
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
		filaTransacoes = new Queue("transacoes.suspeitas", true);
		amqpAdmin.declareQueue(filaTransacoes);
	}
}
