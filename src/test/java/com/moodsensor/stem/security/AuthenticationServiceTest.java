package com.moodsensor.stem.security;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;

import com.moodsensor.stem.service.MoodDataService;

import jakarta.servlet.http.HttpServletRequest;

public class AuthenticationServiceTest {

  @Mock private MoodDataService moodDataService;
  @Mock HttpServletRequest servletRequest;

  private Long userId = 1L;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void testValidAuthentication() {
    when(moodDataService.isValidApiKey(any(), any())).thenReturn(true);
    when(servletRequest.getHeader(any())).thenReturn("some-client");

    Authentication authentication = AuthenticationService.getAuthentication(servletRequest, moodDataService);

    Assertions.assertTrue(authentication.isAuthenticated());

  }

  @Test
  public void testInValidAuthentication() {
    when(moodDataService.isValidApiKey(any(), any())).thenReturn(true);
    when(servletRequest.getHeader(any())).thenReturn(null);

    Assertions.assertThrows(BadCredentialsException.class,
        () -> AuthenticationService.getAuthentication(servletRequest, moodDataService));
  }
}
