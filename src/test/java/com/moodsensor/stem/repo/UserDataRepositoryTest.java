package com.moodsensor.stem.repo;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.moodsensor.stem.entity.UserData;

@DataJpaTest
public class UserDataRepositoryTest {

  @Autowired private UserDataRepository userRepository;

  @Autowired private TestEntityManager entityManager;
  private static final String USER_ONE = "userOne";

  @BeforeEach
  public void setUp() {
    UserData user = new UserData();
    user.setUserName(USER_ONE);
    user.setApiKey("apiKey1");
    entityManager.persist(user);
  }

  @Test
  public void databaseScripts() {
    long count = userRepository.count();

    //In the db scripts 5 users + one in setup method are created
    Assertions.assertEquals(count, 6L);
  }

  @Test
  public void testFindUserDataById() {

    Optional<UserData> found = userRepository.findById(2L);

    Assertions.assertTrue(found.isPresent());
  }

  @Test
  public void testFindUserDataByName() {

    Optional<UserData> found = userRepository.findByUserName(USER_ONE);

    Assertions.assertTrue(found.isPresent());
    Assertions.assertEquals(found.get().getUserName(), USER_ONE);
  }

}
