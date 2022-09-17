package com.generation.blogpessoal.configuration;

import org.springdoc.core.customizers.OpenApiCustomiser;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;

 //Classe é do tipo configuração, ou seja, define uma Classe como fonte de definições de beans.
@Configuration
public class SwaggerConfig {
	
// utilizada em Métodos de uma Classe, geralmente marcada com @Configuration, indica ao Spring que ele deve invocar aquele Método e
//gerenciar o objeto retornado por ele, ou seja, 
// agora este objeto pode ser injetado em qualquer ponto da sua aplicação.
	@Bean
	public OpenAPI springBlogpessoalOpenAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("Projeto Blog Pessoal")
						.description("Projeto Blog Pessoal - Generation Brasil")
						.version("v0.0.1")
					.license(new License()
							.name("Generation Brasil")
							.url("https://brazil.generation.org/"))
						.contact(new Contact()
							.name ("Conteúdo Generation")
							.url("https://github.com/conteudoGeneration")
							.email("conteudogeneration@gmail.com")))
						.externalDocs(new ExternalDocumentation()
								.description("Github")
								.url("https://github.com/conteudoGeneration/"));
	}
	
	@Bean
	public OpenApiCustomiser customerGlobalHeaderOpenApiCustomiser() {
		
		return openApi -> {
			openApi.getPaths().values().forEach(pathItem -> pathItem.readOperations().forEach(operation -> {
				
				ApiResponses apiResponses = operation.getResponses();
				
				apiResponses.addApiResponse("200", createApiResponse("Sucesso!"));
				apiResponses.addApiResponse("201", createApiResponse("Objeto Persistido"));
				apiResponses.addApiResponse("204", createApiResponse("Objeto Excluido"));
				apiResponses.addApiResponse("400", createApiResponse("Erro na Requisição"));
				apiResponses.addApiResponse("401", createApiResponse("Acesso não Autorizado"));
				apiResponses.addApiResponse("404", createApiResponse("Objeto nao Encontrado"));
				apiResponses.addApiResponse("500", createApiResponse("Erro na Aplicação"));
			}));
		};
	}
	
	private ApiResponse createApiResponse(String message) {
		
		return new ApiResponse().description(message);
	}
}
