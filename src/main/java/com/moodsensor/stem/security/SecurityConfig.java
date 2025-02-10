package com.moodsensor.stem.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.DefaultHttpSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.moodsensor.stem.service.MoodDataService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Autowired MoodDataService moodDataService;

  @Autowired WebExpressionAuthorizationManager authorizationManager;
  @Autowired DefaultHttpSecurityExpressionHandler expressionHandler;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http, ApplicationContext context) throws Exception {

    authorizationManager.setExpressionHandler(expressionHandler);

    http.csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(
            authorizationManagerRequestMatcherRegistry -> authorizationManagerRequestMatcherRegistry
                //                    .requestMatchers("/api/mood/**").authenticated()
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**",
                    "/swagger-resources")
                .permitAll()

                .requestMatchers("/api/mood/frequency/{userId}/**")
                .access(authorizationManager)
                .anyRequest()
                .authenticated())

        .httpBasic(Customizer.withDefaults())
        .sessionManagement(
            httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(
                SessionCreationPolicy.STATELESS))
        .addFilterBefore(new AuthenticationFilter(moodDataService),
            UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

}
