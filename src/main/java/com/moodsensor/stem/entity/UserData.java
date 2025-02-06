package com.moodsensor.stem.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;

@Entity
@Getter
@Data
public class UserData {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(name = "userName", length = 20, nullable = false, unique = true)
  private String userName;

  @Column(name = "apiKey", length = 20, nullable = false)
  private String apiKey;  // Store encrypted value in future

}

