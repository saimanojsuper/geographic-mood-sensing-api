package com.moodsensor.stem.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.moodsensor.stem.model.MoodRequest;
import com.moodsensor.stem.model.NearestMoodData;
import com.moodsensor.stem.service.MoodDataService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/mood/")
public class MoodSensorAppController {

  @GetMapping(value = "hello")
  public String getHello() {
    return "hello from server";
  }

  @Autowired private MoodDataService moodService;

  @PostMapping("/upload")
  public ResponseEntity<String> uploadMood(@RequestBody @Valid MoodRequest moodRequest) {
    moodService.uploadMood(moodRequest.getUserId(), moodRequest.getMood(), moodRequest.getLatitude(),
        moodRequest.getLongitude());
    return ResponseEntity.ok("Mood uploaded successfully.");
  }

  @GetMapping("/frequency/{userId}")
  public ResponseEntity<Map<String,Long>> getMoodFrequency(@PathVariable Long userId) {
    Map<String,Long> moodCount = moodService.getMoodFrequency(userId);
    return ResponseEntity.ok(moodCount);
  }

  @GetMapping("/happy-location/{userId}")
  //Need to check the distance unit
  public ResponseEntity<NearestMoodData> getClosestHappyLocation(
      @PathVariable Long userId, @RequestParam double latitude, @RequestParam double longitude) {
    NearestMoodData happyLocation = moodService.getClosestHappyLocation(userId, latitude, longitude);
    return ResponseEntity.ok(happyLocation);
  }

}
