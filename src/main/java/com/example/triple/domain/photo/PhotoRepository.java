package com.example.triple.domain.photo;

import com.example.triple.domain.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface PhotoRepository extends JpaRepository<Photo, String> {
    @Query(value = "select * from photo where review_id =:reviewId", nativeQuery = true)
    List<Photo> findByReviewId(String reviewId);

    @Modifying
    @Query(value = "delete from photo where review_id =:reviewId", nativeQuery = true)
    int deleteByReviewId(String reviewId);
}
