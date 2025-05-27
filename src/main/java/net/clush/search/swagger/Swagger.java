package net.clush.search.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class Swagger {
	
	 @Bean
	    public OpenAPI openAPI() {
	        return new OpenAPI()
	                .info(new Info()
	                        .title("Search API Documentation")
	                        .description("Search API 서비스 문서")
	                        .version("v1.0.0"));
	    }

}
