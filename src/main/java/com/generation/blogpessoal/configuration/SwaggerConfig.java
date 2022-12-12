package com.generation.blogpessoal.configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.aspectj.apache.bcel.classfile.Module;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {


    @Bean
    public OpenAPI springBlogPessoalOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("projeto Blog Pessoal")
                        .description("Projeto Blog Pessoal - Generation Brasil")
                        .version("v0.0.1")
                        .license(new License()
                                .name("Generation Brasil")
                                .url("https://brazil.generation.org/"))
                                .contact(new Contact()
                                        .name("Deivison Rocha")
                                        .url("https://github.com/DeiviDev1")
                                        .email("rocha00rocha@gmail.com")))
                .externalDocs(new ExternalDocumentation()
                        .description("Github")
                        .url("https://github.com/conteudoGeneration/")
                );

    }

    @Bean
    public OpenApiCustomiser customiserGlobalHeaderOpenApiCustomiser(){

        return openApi -> {
            openApi.getPaths().values().forEach(pathItem -> pathItem.readOperations().forEach(operation -> {

                ApiResponses apiResponses = operation.getResponses();

                apiResponses.addApiResponse("200", createApiResponse("Sucesso"));
                apiResponses.addApiResponse("201", createApiResponse("Objeto Persistido!"));
                apiResponses.addApiResponse("204", createApiResponse("Objeto Excluído!"));
                apiResponses.addApiResponse("400", createApiResponse("Erro na Requisição!"));
                apiResponses.addApiResponse("401", createApiResponse("Acesso não autorizado"));
                apiResponses.addApiResponse("404", createApiResponse("Ojeto Não encontrado!"));
                apiResponses.addApiResponse("500", createApiResponse("Erro na Aplicação'"));
            }));
        };

    }

    private ApiResponse createApiResponse(String message){

        return new ApiResponse().description(message);
    }


}
