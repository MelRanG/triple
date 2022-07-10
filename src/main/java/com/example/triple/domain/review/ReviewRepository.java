package com.example.triple.domain.review;

import com.example.triple.dto.EventDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, String>{
    @Query(value = "select * from Review where place_id =:placeId and user_id =:userId", nativeQuery = true)
    Review findByPlaceIdAndUserId(String placeId, String userId);

    @Query(value = "select count(*) from Review where place_id =:placeId", nativeQuery = true)
    int findByPlaceId(String placeId);

    //해당 place에 나보다 리뷰를 먼저 작성한 사람이 있는지 조회
    @Query(value = "SELECT count(*) FROM REVIEW where place_id =:placeId and created_date < \n" +
            "(select created_date \n" +
            "from review \n" +
            "where user_id =:userId\n" +
            "and place_id =:placeId);", nativeQuery = true)
    int findByFirstWritten(String placeId, String userId);
}
