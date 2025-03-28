package com.moodsensor.stem.security;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import com.moodsensor.stem.service.MoodDataService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthenticationFilter extends GenericFilterBean {

  @Autowired private final MoodDataService moodDataService;

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
      throws IOException, ServletException {
    try {
      String path = ((HttpServletRequest) request).getRequestURI();
      if (path.startsWith("/swagger-ui") || path.startsWith("/v3/api-docs") || path.startsWith("/swagger-resources")) {
        filterChain.doFilter(request, response); // Skip authentication for Swagger
        return;
      }
      Authentication authentication = AuthenticationService.getAuthentication((HttpServletRequest)request,
          moodDataService);
      SecurityContextHolder.getContext().setAuthentication(authentication);
    } catch (Exception exp) {
      HttpServletResponse httpResponse = (HttpServletResponse)response;
      httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      httpResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
      PrintWriter writer = httpResponse.getWriter();
      writer.print(exp.getMessage());
      writer.flush();
      writer.close();
    }

    filterChain.doFilter(request, response);
  }
}
