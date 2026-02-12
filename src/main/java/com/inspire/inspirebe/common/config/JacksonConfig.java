package com.inspire.inspirebe.common.config;

import com.inspire.inspirebe.binding.Update;
import com.inspire.inspirebe.binding.UpdateDeserializer;
import org.springframework.boot.jackson.autoconfigure.JsonMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tools.jackson.databind.module.SimpleModule;

@Configuration(proxyBeanMethods = false)
public class JacksonConfig {

    @Bean
    public JsonMapperBuilderCustomizer jsonCustomizer() {
        return builder -> {
            SimpleModule module = new SimpleModule();
            module.addDeserializer(Update.class, new UpdateDeserializer<>());
            builder.addModule(module);
        };
    }
}