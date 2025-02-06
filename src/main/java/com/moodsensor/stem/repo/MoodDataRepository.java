package com.moodsensor.stem.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.moodsensor.stem.entity.MoodData;
import com.moodsensor.stem.model.NearestMoodData;

@Repository
public interface MoodDataRepository extends JpaRepository<MoodData,Long> {
  List<MoodData> findByUserDataId(Long userId);

  String getNearestMoodQuery =
      "SELECT new com.moodsensor.stem.model.NearestMoodData(md.latitude, md.longitude, ST_Distance(ST_SetSRID(ST_Point(:lon, :lat)" +
          ", 4326), ST_SetSRID(ST_Point(longitude, latitude), 4326)) AS distance) " + "FROM MoodData md " +
          "WHERE md.userData.id = :userId AND mood = :mood " + "ORDER BY distance " + "LIMIT 1";

  @Query(getNearestMoodQuery)
  List<NearestMoodData> getNearestMoods(
      @Param("lon") Double lon,
      @Param("lat") Double lat,
      @Param("userId") Long userId,
      @Param("mood") String mood); //distance in meters
}
