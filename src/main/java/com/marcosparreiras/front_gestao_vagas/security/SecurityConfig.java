package com.marcosparreiras.front_gestao_vagas.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http)
    throws Exception {
    http
      .authorizeHttpRequests(auth -> {
        auth.requestMatchers("/styles/**").permitAll();
        auth.requestMatchers("/candidate/login").permitAll();
        auth.requestMatchers("/candidate/sing-in").permitAll();
        auth.requestMatchers("/candidate/create").permitAll();
        auth.requestMatchers("/company/login").permitAll();
        auth.requestMatchers("/company/create").permitAll();

        auth.anyRequest().authenticated();
      })
      .formLogin(form -> {
        form.loginPage("/candidate/login");
      });

    return http.build();
  }
}
