package com.moodsensor.stem.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Entity
@Getter
public class MoodData {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @ManyToOne
  @JoinColumn(name = "userId", referencedColumnName = "id", nullable = false)
  private UserData userData;

  @Column(name = "lat", length = 255, nullable = false)
  private String lat;

  @Column(name = "long", length = 255, nullable = false)
  private String longitude;

  @Column(name = "mood", length = 10, nullable = false)
  private String mood;

  @Column(name = "timeStamp", nullable = false)
  private LocalDate timeStamp; //this should be date&time

  @Column(name = "image", length = 255) // Length adjusted for URL/Path
  private String image;


}

