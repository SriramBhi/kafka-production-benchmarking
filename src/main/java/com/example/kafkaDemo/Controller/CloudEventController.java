package com.example.kafkaDemo.Controller;

import com.example.kafkaDemo.Service.CloudEventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.cloudevents.CloudEvent;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1")
public class CloudEventController {
    @Autowired
    private CloudEventService cloudEventService;
    private static final Logger logger = LoggerFactory.getLogger(CloudEventController.class);
    @PostMapping("/ingest")
    public ResponseEntity<Map<String,String>> consumeCloudEvent(@RequestBody CloudEvent event) {
        logger.info("Publishing records - /v1/ingest");
        logger.info("Cloud received from request body : {}",event);
        Map<String, String> response = new HashMap<>();
        boolean resp = cloudEventService.CloudEventPublish(event);
        if(resp) {
            response.put("message", "CloudEvent received successfully!");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            response.put("message", "CloudEvent validation failed");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
