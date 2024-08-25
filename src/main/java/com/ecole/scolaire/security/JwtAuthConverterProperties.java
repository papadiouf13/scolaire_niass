package com.ecole.scolaire.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Data
@Configuration
@ConfigurationProperties(prefix = "token.auth.converter")
public class JwtAuthConverterProperties {

    private String resourceId;
    private String principalAttribute;


}
