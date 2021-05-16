package com.intive.patronative.studentrecord.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI patronativeStudentRecordOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Patronative Student Record API")
                        .description("Student record module")
                        .version("v0.0.1"));
    }

}