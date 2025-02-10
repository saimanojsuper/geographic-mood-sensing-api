package com.moodsensor.stem.security;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class ApiKeyAuthentication extends AbstractAuthenticationToken {
  private final String apiKey;
  private final String userName;

  public ApiKeyAuthentication(
      String apiKey, Collection<? extends GrantedAuthority> authorities, String userName) {
    super(authorities);
    this.apiKey = apiKey;
    this.userName = userName;
    setAuthenticated(true);
  }

  @Override
  public Object getCredentials() {
    return null;
  }

  @Override
  public Object getPrincipal() {
    return apiKey;
  }

  public String getUserName() {
    return userName;
  }
}
