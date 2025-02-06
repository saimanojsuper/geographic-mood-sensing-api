package com.moodsensor.stem.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.moodsensor.stem.entity.MoodData;

@Repository
public interface MoodDataRepository extends JpaRepository<MoodData,Integer> {
}
