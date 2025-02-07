package com.moodsensor.stem.service;

import java.util.Map;

import com.moodsensor.stem.model.NearestMoodData;

public interface MoodDataService {
  void uploadMood(Long userId, String mood, double latitude, double longitude);

  Map<String,Long> getMoodFrequency(Long userId);

  NearestMoodData getClosestHappyLocation(Long userId, double latitude, double longitude);

  Boolean isValidApiKey(String userName, String apiKey);
}
