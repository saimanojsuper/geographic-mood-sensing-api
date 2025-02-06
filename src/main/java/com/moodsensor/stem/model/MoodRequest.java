package com.moodsensor.stem.model;

import com.moodsensor.stem.validator.ValidateString;

import lombok.Data;

@Data
public class MoodRequest {
  private Long userId;
  @ValidateString(acceptedValues = {"happy","sad","neutral"}) //need to take values from the enum
  private String mood;
  private Double latitude;
  private Double longitude;
}
