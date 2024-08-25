package com.ecole.scolaire.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    public static final String ADMIN = "admin";
    public static final String USER = "user";
    public static final String SUPER = "super";


    private final JwtAuthConverter jwtAuthConverter;

    @Bean
    public SecurityFilterChain resourceServerFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/login")
                .permitAll()

                .requestMatchers("/api/patient/**")
                .hasAuthority(USER)


                .requestMatchers("/api/docteur/**")
                .hasAuthority(ADMIN)

                .requestMatchers("/api/rv/**")
                .hasAuthority(SUPER)

                /*.requestMatchers("/keycloak/**")
                .hasAuthority(SUPER)*/

                .anyRequest()
                .authenticated());

        http.oauth2ResourceServer((oauth2) -> oauth2
                .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter)
                        .jwkSetUri("http://localhost:8083/realms/projet-scolaire221-niass-realm/protocol/openid-connect/certs")));


        http.csrf(AbstractHttpConfigurer::disable);
        http.sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer.
                                sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }


}