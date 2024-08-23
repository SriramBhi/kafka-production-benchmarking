package com.example.kafkaDemo.Config;


import io.cloudevents.CloudEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
public class KafkaProducer extends KafkaCloudEventProducer {

    @Bean
    public ProducerFactory<String, CloudEvent> producerFactory() {
        return super.producerFactory();
    }

    @Bean
    public KafkaTemplate<String, CloudEvent> kafkaTemplate() {
        return super.kafkaTemplate();
    }

}
