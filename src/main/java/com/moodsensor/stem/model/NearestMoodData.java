package com.moodsensor.stem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NearestMoodData {
  private Double latitude;
  private Double longitude;
  private Double distance;
}
