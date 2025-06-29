package br.edu.utfpr.td.cotsi.exchange.producer;

import javax.annotation.PostConstruct;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("br.edu.utfpr.td.cotsi.exchange")
public class ExchangeProducerApp {

	@Autowired
	private AmqpAdmin amqpAdmin;

	public static void main(String[] args) throws Exception {
		SpringApplication.run(ExchangeProducerApp.class, args);
	}

	@PostConstruct
	public void configurarCanais() {
	    FanoutExchange fanout = new FanoutExchange("transacoes.suspeitas.exchange", true, false);
	    amqpAdmin.declareExchange(fanout);

	    Queue filaPolicia = new Queue("policia.federal", true);
	    amqpAdmin.declareQueue(filaPolicia);
	    amqpAdmin.declareBinding(BindingBuilder.bind(filaPolicia).to(fanout));

	    Queue filaReceita = new Queue("receita.federal", true);
	    amqpAdmin.declareQueue(filaReceita);
	    amqpAdmin.declareBinding(BindingBuilder.bind(filaReceita).to(fanout));
	}
}
