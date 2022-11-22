package wc.configuration;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
        name = "BearerToken",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer"
)
public class OpenApiConfiguration {

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .info(new Info().title("WebSocket Chat").contact(new Contact().email("mr-skyu@yandex.ru")))
                .addServersItem(new Server().url("/api"));
    }
}
