package com.moodsensor.stem.model;

import com.moodsensor.stem.validator.ValidateMoodString;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MoodRequest {
  @NotNull
  private Long userId;
  @ValidateMoodString(acceptedValues = {"happy","sad","neutral"}) //need to take values from the enum
  private String mood;
  @NotNull
  private Double latitude;
  @NotNull
  private Double longitude;
}
