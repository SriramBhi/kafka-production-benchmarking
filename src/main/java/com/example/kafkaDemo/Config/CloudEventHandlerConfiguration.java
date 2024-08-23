package com.example.kafkaDemo.Config;

import io.cloudevents.spring.mvc.CloudEventHttpMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.http.converter.HttpMessageConverter;

import java.util.List;

@Configuration
public class CloudEventHandlerConfiguration implements WebMvcConfigurer {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(0, new CloudEventHttpMessageConverter());
    }
}
