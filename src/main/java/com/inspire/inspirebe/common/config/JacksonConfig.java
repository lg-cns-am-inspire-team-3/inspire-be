package com.inspire.inspirebe.common.config;

import com.inspire.inspirebe.binding.Update;
import com.inspire.inspirebe.binding.UpdateDeserializer;
import org.springframework.boot.jackson.autoconfigure.JsonMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tools.jackson.databind.JavaType;
import tools.jackson.databind.module.SimpleModule;
import tools.jackson.databind.type.TypeFactory;

@Configuration(proxyBeanMethods = false)
public class JacksonConfig {

    @Bean
    public JsonMapperBuilderCustomizer jsonCustomizer() {
        SimpleModule customModule = new SimpleModule();
        customModule.addDeserializer(Update.class, new UpdateDeserializer<>());

        return builder -> builder.addModule(customModule);
    }
}
