package toy.com.config;

import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {


    @Bean
    public OpenAPI openAPI() {

        List<Server> servers = Collections.singletonList(new Server()
                                                            .url("")
                                                            .description("swagger"));
        Info info = new Info()
                        .title("Shinity")
                        .version("0.0.1")
                        .description("Shinity 토이프로젝트 스웨거입니다");

        SecurityScheme apiKeyAuth = new SecurityScheme()
                                        .type(SecurityScheme.Type.APIKEY)
                                        .name("Authorization")
                                        .in(SecurityScheme.In.HEADER)
                                        .scheme("apiKey");

        SecurityRequirement securityRequirement = new SecurityRequirement()
                                                      .addList("apiKeyAuth");
        return new OpenAPI()
                   .components(new Components()
                                   .addSecuritySchemes("apiKeyAuth", apiKeyAuth))
                   .addSecurityItem(securityRequirement)
                   .info(info)
                   .servers(servers);
    }
}
