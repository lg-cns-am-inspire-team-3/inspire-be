package com.inspire.inspirebe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.data.redis.autoconfigure.DataRedisAutoConfiguration;
import org.springframework.boot.data.redis.autoconfigure.DataRedisRepositoriesAutoConfiguration;

@SpringBootApplication
@ConfigurationPropertiesScan
public class InspireBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(InspireBeApplication.class, args);
    }

}
