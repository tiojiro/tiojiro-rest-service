package br.com.zukeran.payroll;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("br.com.zukeran.payroll"))
                //Especifico para o controller de Employees, se retirar o path irá buscar todos os controllers do pacote indicado acima.
                .paths(regex("/employees.*")) 
                .build()
        		.apiInfo(metaData());
    }
    
    private ApiInfo metaData() {
        ApiInfo apiInfo = new ApiInfo(
                "Tiojiro Rest Service Payroll",
                "Spring Boot REST API",
                "1.0",
                "Terms of service",
                new Contact("Tiojiro", "https://spring.io/", "tiojiro@gmail.com"),
                "Swagger Tutorial",
                "https://dzone.com/articles/spring-boot-restful-api-documentation-with-swagger");
        return apiInfo;
    }
}
