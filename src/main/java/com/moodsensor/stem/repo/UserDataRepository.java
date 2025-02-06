package com.moodsensor.stem.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.moodsensor.stem.entity.UserData;

public interface UserDataRepository extends JpaRepository<UserData,Integer> {
  Optional<UserData> findById(Integer id);
  Optional<UserData> findByUserName(String userName);
}
