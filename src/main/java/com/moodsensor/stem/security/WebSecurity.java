package com.moodsensor.stem.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.moodsensor.stem.entity.UserData;
import com.moodsensor.stem.repo.UserDataRepository;

@Service("webSecurity")
public class WebSecurity {

  @Autowired UserDataRepository userDataRepository;

  // Check if the userId in the URL matches the authenticated user's ID
  public boolean checkUserId(Authentication authentication, String userId) {
    // Get the authenticated user's ID (assuming it's stored in the principal object)
    ApiKeyAuthentication apiKeyAuthentication = (ApiKeyAuthentication)authentication;
    Optional<UserData> user = userDataRepository.findByUserName(apiKeyAuthentication.getUserName());

    // Check if the authenticated user's ID matches the given userId
    return user.isPresent() && userId.equals(user.get().getId().toString());
  }
}
