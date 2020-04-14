package br.com.user.security.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;

@Slf4j
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${server.documentation.title:Rest API}")
    private String documentationTitle;

    @Value("${server.documentation.description:Rest API Documentation}")
    private String documentationDescrition;

    @PostConstruct
    public void init() {
        log.info("Loading Swagger2 Configuration");
    }

    @Bean
    public Docket api() {
        log.info("Configuring Swagger2 with: [{}][{}]", documentationTitle, documentationDescrition);

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("br.com"))
                .paths(PathSelectors.any())
                .build().apiInfo(metaData());
    }

    private ApiInfo metaData() {
        return new ApiInfoBuilder()
                .title(documentationTitle)
                .description(documentationDescrition)
                .build();
    }
}
