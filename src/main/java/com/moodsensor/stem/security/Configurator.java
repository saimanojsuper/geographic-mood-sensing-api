package com.moodsensor.stem.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.access.expression.DefaultHttpSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;

@Configuration
public class Configurator {

  @Bean
  public DefaultHttpSecurityExpressionHandler defaultHttpSecurityExpressionHandler(){
    return new DefaultHttpSecurityExpressionHandler();
  }

  @Bean
  public WebExpressionAuthorizationManager webExpressionAuthorizationManager() {
    return new WebExpressionAuthorizationManager("@webSecurity.checkUserId(authentication, #userId)");
  }
}
