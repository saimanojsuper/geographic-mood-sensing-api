package com.moodsensor.stem.repo;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.moodsensor.stem.Enum.MoodState;
import com.moodsensor.stem.entity.MoodData;
import com.moodsensor.stem.model.NearestMoodData;

@DataJpaTest
public class MoodDataRepositoryTest {

  @Autowired private MoodDataRepository moodDataRepository;

  @Test
  public void databaseScripts() {
    long count = moodDataRepository.count();

    //In the db scripts 5 users + one in setup method are created
    Assertions.assertEquals(10L, count);
  }

  @Test
  public void testFindByUserDataId() {

    List<MoodData> userDataId = moodDataRepository.findByUserDataId(1L);

    Assertions.assertEquals(2, userDataId.size());
  }

  @Test
  public void testGetNearestMoods() {
    List<NearestMoodData> nearestMoods = moodDataRepository.getNearestMoods(2.352222, 48.856613, 2L,
        MoodState.HAPPY.getName());

    // Need to check why order by is not working correctly
    Assertions.assertEquals(1, nearestMoods.size());
    Assertions.assertEquals(0, nearestMoods.get(0).getDistance());
  }

  @Test
  public void testGetNearestMoodsEmptyForInvalidUser() {
    List<NearestMoodData> nearestMoods = moodDataRepository.getNearestMoods(2.352222, 48.856613, 10L,
        MoodState.HAPPY.getName());

    // Need to check why order by is not working correctly
    Assertions.assertEquals(0, nearestMoods.size());
  }
}
