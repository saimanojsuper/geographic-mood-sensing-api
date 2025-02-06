package com.moodsensor.stem.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class MoodData {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "userId", referencedColumnName = "id", nullable = false)
  private UserData userData;

  @Column(name = "latitude", nullable = false)
  private Double latitude;  // Latitude

  @Column(name = "longitude", nullable = false)
  private Double longitude;  // Longitude

  @Column(name = "mood", length = 10, nullable = false)
  private String mood;

  @Column(name = "timeStamp", nullable = false)
  private LocalDateTime timeStamp; //this should be date&time

//  @Column(name = "image", length = 255) // Length adjusted for URL/Path
//  private String image;


}

