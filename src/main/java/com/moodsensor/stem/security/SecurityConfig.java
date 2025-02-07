package com.moodsensor.stem.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.moodsensor.stem.service.MoodDataService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Autowired MoodDataService moodDataService;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    // using this to authorize based on the userId
    // WebExpressionAuthorizationManager authorization = new WebExpressionAuthorizationManager("@webSecurity.checkUserId(authentication,#userId)");

    http.csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(
            authorizationManagerRequestMatcherRegistry -> authorizationManagerRequestMatcherRegistry.requestMatchers(
                "/**").authenticated())
        .httpBasic(Customizer.withDefaults())
        .sessionManagement(
            httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(
                SessionCreationPolicy.STATELESS))
        .addFilterBefore(new AuthenticationFilter(moodDataService),
            UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

}
