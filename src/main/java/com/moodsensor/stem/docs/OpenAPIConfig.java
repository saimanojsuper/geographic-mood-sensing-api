package com.moodsensor.stem.docs;

import static com.moodsensor.stem.security.AuthenticationService.AUTH_TOKEN_CLIENT_NAME;
import static com.moodsensor.stem.security.AuthenticationService.AUTH_TOKEN_HEADER_NAME;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;

@Configuration
@OpenAPIDefinition(info = @Info(title = "API Documentation", version = "1.0", description = "Sample API"))
@SecuritySchemes(
    value = {
        @SecurityScheme(
            name = AUTH_TOKEN_HEADER_NAME,
            type = SecuritySchemeType.APIKEY,
            in = SecuritySchemeIn.HEADER,
            description = "API key of the client"
        ),
        @SecurityScheme(
            name = AUTH_TOKEN_CLIENT_NAME,
            type = SecuritySchemeType.APIKEY,
            in = SecuritySchemeIn.HEADER,
            description = "Name of the client"
        ),

    }
)
public class OpenAPIConfig {


}
