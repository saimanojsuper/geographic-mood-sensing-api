package com.moodsensor.stem.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.moodsensor.stem.Enum.MoodState;
import com.moodsensor.stem.entity.MoodData;
import com.moodsensor.stem.entity.UserData;
import com.moodsensor.stem.exception.DataServicesException;
import com.moodsensor.stem.exception.InvalidUserException;
import com.moodsensor.stem.model.NearestMoodData;
import com.moodsensor.stem.repo.MoodDataRepository;
import com.moodsensor.stem.repo.UserDataRepository;

public class MoodDataServiceTest {

  @Mock private UserDataRepository userDataRepository;
  @Mock private MoodDataRepository moodDataRepository;

  @InjectMocks private MoodDataServiceImpl moodDataService;
  private Long userId = 1L;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void testUploadMood() {

    when(moodDataRepository.save(any())).thenReturn(any());
    when(userDataRepository.findById(userId)).thenReturn(Optional.of(new UserData()));

    moodDataService.uploadMood(userId, MoodState.HAPPY.getName(), 0.0, 0.0);

    verify(userDataRepository, times(1)).findById(userId);
    verify(moodDataRepository, times(1)).save(any());
  }

  @Test
  public void testUploadMoodInvalidDBError() {

    when(moodDataRepository.save(any())).thenReturn(any());
    when(userDataRepository.findById(userId)).thenThrow(new DataServicesException(""));

    Assertions.assertThrows(DataServicesException.class,
        () -> moodDataService.uploadMood(userId, MoodState.HAPPY.getName(), 0.0, 0.0));

    verify(userDataRepository, times(1)).findById(userId);
    verify(moodDataRepository, times(0)).save(any());
  }

  @Test
  public void testUploadMoodInvalidUserError() {

    when(moodDataRepository.save(any())).thenReturn(any());
    when(userDataRepository.findById(userId)).thenReturn(Optional.empty());

    Assertions.assertThrows(InvalidUserException.class,
        () -> moodDataService.uploadMood(userId, MoodState.HAPPY.getName(), 0.0, 0.0));

    verify(userDataRepository, times(1)).findById(userId);
    verify(moodDataRepository, times(0)).save(any());
  }

  @Test
  public void testGetMoodFrequency() {

    when(moodDataRepository.findByUserDataId(userId)).thenReturn(
        List.of(mockMoodData(MoodState.HAPPY.getName()), mockMoodData(MoodState.HAPPY.getName()),
            mockMoodData(MoodState.SAD.getName()), mockMoodData(MoodState.HAPPY.getName())));

    Map<String,Long> data = moodDataService.getMoodFrequency(userId);

    Assertions.assertEquals(3, data.get(MoodState.HAPPY.getName()));
    Assertions.assertEquals(1, data.get(MoodState.SAD.getName()));
  }

  @Test
  public void testGetMoodFrequencyException() {
    when(moodDataRepository.findByUserDataId(userId)).thenThrow(new DataServicesException(""));

    Assertions.assertThrows(DataServicesException.class, () -> moodDataService.getMoodFrequency(userId));
  }

  @Test
  public void testGetClosestHappyLocation() {
    when(moodDataRepository.getNearestMoods(0.0, 0.0, userId, "happy")).thenReturn(
        List.of(new NearestMoodData(0.0, 0.0, 0.0)));

    NearestMoodData closestHappyLocation = moodDataService.getClosestHappyLocation(userId, 0.0, 0.0);

    Assertions.assertEquals(0.0, closestHappyLocation.getLatitude());
    Assertions.assertEquals(0, closestHappyLocation.getDistance());

  }

  @Test
  public void testGetClosestHappyLocationException() {
    when(moodDataRepository.getNearestMoods(0.0, 0.0, userId, "happy")).thenThrow(
        new DataServicesException(""));

    Assertions.assertThrows(DataServicesException.class,
        () -> moodDataService.getClosestHappyLocation(userId, 0.0, 0.0));
  }

  private MoodData mockMoodData(String mood) {
    return MoodData.builder().mood(mood).build();
  }
}
