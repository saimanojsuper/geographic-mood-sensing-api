package com.moodsensor.stem.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stem/")
public class MoodSensorAppController {

  @GetMapping(value = "hello")
  public String getHello() {
    return "hello from server";
  }

}
