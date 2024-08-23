package com.example.kafkaDemo.Config;

import io.cloudevents.CloudEvent;
import io.cloudevents.kafka.CloudEventSerializer;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.admin.Admin;
import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

/***
 * This abstract class needs to be extended and the following annotation needs
 * to be added in the extending class.
 *
 * @author Shyam Sundar G
 */
public abstract class KafkaCloudEventProducer {

    @Autowired
    Environment env;

    public ProducerFactory<String, CloudEvent> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, getBootstrapServerConfig());
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, CloudEventSerializer.class);
        if (isSecurityEnabled()) {
            configProps.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, securityProtocol());
        }
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    public KafkaTemplate<String, CloudEvent> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    public Admin kafkaAdmin() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, getBootstrapServerConfig());
        if (isSecurityEnabled()) {
            configProps.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, securityProtocol());
        }

        return KafkaAdminClient.create(configProps);
    }

    /***
     * This method can be overridden to change bootstrap-servers config
     *
     * @return configured kafka bootstrap servers
     */
    public String getBootstrapServerConfig() {
        return env.getProperty("kafka.bootstrap.servers");
    }

    private String securityProtocol() {
        String protocol = env.getProperty("kafka.ssl.protocol");
        return protocol;
    }

    private boolean isSecurityEnabled() {
        return "true".equalsIgnoreCase(env.getProperty("kafka.ssl.enabled"));
    }
}
