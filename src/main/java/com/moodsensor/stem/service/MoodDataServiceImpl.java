package com.moodsensor.stem.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moodsensor.stem.Enum.MoodState;
import com.moodsensor.stem.entity.MoodData;
import com.moodsensor.stem.entity.UserData;
import com.moodsensor.stem.exception.DataServicesException;
import com.moodsensor.stem.exception.InvalidUserException;
import com.moodsensor.stem.model.NearestMoodData;
import com.moodsensor.stem.repo.MoodDataRepository;
import com.moodsensor.stem.repo.UserDataRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MoodDataServiceImpl implements MoodDataService {
  @Autowired private MoodDataRepository moodDataRepository;
  @Autowired private UserDataRepository userDataRepository;

  @Override
  public void uploadMood(Long userId, String mood, double latitude, double longitude) {
    Optional<UserData> userData = userDataRepository.findById(userId);
    if (!userData.isPresent()) {
      throw new InvalidUserException("The given user id doesn't exist");
    }
    MoodData moodData = new MoodData();
    moodData.setUserData(userData.get());
    moodData.setMood(mood);
    moodData.setLatitude(latitude);
    moodData.setLongitude(longitude);
    moodData.setTimeStamp(LocalDateTime.now());

    moodDataRepository.save(moodData);
  }

  @Override
  public Map<String,Long> getMoodFrequency(Long userId) {
    // Instead of returning all the mood data group by can be performed in sql query which reduces latency of data transfer
    Map<String,Long> moodCount = null;
    try {
      List<MoodData> moods = moodDataRepository.findByUserDataId(userId);
      moodCount = moods.stream()
          .map(MoodData::getMood)
          .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    } catch (Exception e) {
      log.error(e.getMessage());
      throw new DataServicesException(e.getMessage());
    }

    return moodCount;
  }

  @Override
  public NearestMoodData getClosestHappyLocation(Long userId, double latitude, double longitude) {
    List<NearestMoodData> nearestMoods = null;
    try {
      nearestMoods = moodDataRepository.getNearestMoods(longitude, latitude, userId,
          MoodState.HAPPY.getName());
    } catch (Exception e) {
      log.error(e.getMessage());
      throw new DataServicesException(e.getMessage());
    }
    return nearestMoods.isEmpty() ? null : nearestMoods.get(0);
  }

  @Override
  public Boolean isValidApiKey(String userName, String apiKey) {
    Optional<UserData> userData = userDataRepository.findByUserName(userName);
    return userData.isPresent() && userData.get().getApiKey().equals(apiKey);
  }
}
