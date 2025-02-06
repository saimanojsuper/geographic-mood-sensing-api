package com.moodsensor.stem.Enum;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum MoodState {
  HAPPY("happy"), NEUTRAL("neutral"), SAD("sad");
  final String name;

  public String getName() {
    return this.name;
  }
}
