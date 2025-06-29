package br.edu.utfpr.td.cotsi.exchange.consumer.fila2;

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
public class ExchangeConsumerApp {

    @Autowired
    private AmqpAdmin amqpAdmin;

    public static void main(String[] args) {
        SpringApplication.run(ExchangeConsumerApp.class, args);
    }

    @PostConstruct
    public void configurarCanais() {
    	Queue fila2 = new Queue("exemplo.exchange.fila2", true);
        amqpAdmin.declareQueue(fila2);

        FanoutExchange fanoutPolicia = new FanoutExchange("receita.federal", true, false);
        amqpAdmin.declareExchange(fanoutPolicia);
        
        Binding bindingPolicia = BindingBuilder.bind(fila2).to(fanoutPolicia);
        amqpAdmin.declareBinding(bindingPolicia);

        System.out.println("Fila, Exchange e Binding criados para consumidor!");
    }
}


