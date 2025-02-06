package com.moodsensor.stem.repo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class MoodDataRepositoryTest {

  @Autowired private MoodDataRepository moodDataRepository;

  @Test
  public void databaseScripts() {
    long count = moodDataRepository.count();

    //In the db scripts 5 users + one in setup method are created
    Assertions.assertEquals(count, 10L);
  }
}
