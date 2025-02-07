package com.moodsensor.stem.security;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;

import com.moodsensor.stem.service.MoodDataService;

import jakarta.servlet.http.HttpServletRequest;

public class AuthenticationService {

  private static final String AUTH_TOKEN_HEADER_NAME = "X-API-KEY";
  private static final String AUTH_TOKEN_CLIENT_NAME = "CLIENT_NAME";

  public static Authentication getAuthentication(
      HttpServletRequest request,
      MoodDataService moodDataService) {

    String apiKey = request.getHeader(AUTH_TOKEN_HEADER_NAME);
    String userName = request.getHeader(AUTH_TOKEN_CLIENT_NAME);
    if (apiKey == null || !moodDataService.isValidApiKey(userName, apiKey)) {
      throw new BadCredentialsException("Invalid API Key");
    }

    return new ApiKeyAuthentication(apiKey, AuthorityUtils.NO_AUTHORITIES);
  }

}
