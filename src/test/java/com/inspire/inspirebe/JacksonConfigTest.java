package com.inspire.inspirebe;

import com.inspire.inspirebe.common.config.JacksonConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.jackson.autoconfigure.JacksonAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;

@ExtendWith(SpringExtension.class)
@Import(JacksonConfig.class)
@ImportAutoConfiguration(JacksonAutoConfiguration.class)
public class JacksonConfigTest {

    @Autowired
    JsonMapper objectMapper;

    @Test
    void moduleRegistered() {
        String[] jsons = new String[]{
                "{\"t1\":\"value1\"}",
                "{\"t2\":\"value2\"}",
                "{\"t3\":\"null\"}",
                "{\"t1\":\"value1\", \"t2\":\"null\"}",
                "{\"t1\":\"value1\", \"t3\":\"value3\"}",
                "{\"t2\":\"null\", \"t3\":\"value3\"}",
                "{\"t1\":\"value1\", \"t2\":\"null\", \"t3\":\"value3\"}",
                "{}"
        };

        for(String json : jsons) {
            UpdateTestDTO dto = objectMapper.readValue(json, UpdateTestDTO.class);
            System.out.println("json: " + json);
            System.out.println("dto: " + dto);
        }
    }
}
