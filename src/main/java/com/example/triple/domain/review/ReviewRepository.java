package com.example.triple.domain.review;

import com.example.triple.dto.EventDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, String>{
    @Query(value = "select * from Review where place_id =:placeId and user_id =:userId", nativeQuery = true)
    Review findByPlaceIdAndUserId(String placeId, String userId);

}
