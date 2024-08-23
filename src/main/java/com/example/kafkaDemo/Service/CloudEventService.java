package com.example.kafkaDemo.Service;

import io.cloudevents.CloudEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.util.Objects;

@Service
public class CloudEventService {

    private static final Logger log = LoggerFactory.getLogger(CloudEventService.class);

    @Value("${app.kafka.topic}")
    private String universalTopicName;

    @Autowired
    private KafkaTemplate<String,CloudEvent> kafkaTemplate;

    public boolean CloudEventPublish(CloudEvent cloudEvent) {
            String data = new String(Objects.requireNonNull(cloudEvent.getData()).toBytes());
            String eventName = Objects.requireNonNull(cloudEvent.getExtension("event")).toString();
            log.info("event_name : {}",eventName);
            log.info("data : {}",data);
            log.info("Thread called: {}", Thread.currentThread().getName());
                    try{
                        kafkaTemplate.send(universalTopicName,eventName,cloudEvent);
                        return true;
                    }catch (Exception e) {
                        log.error("Error while producing the message : {}", e.getMessage());
                    }
             return false;
    }
}
