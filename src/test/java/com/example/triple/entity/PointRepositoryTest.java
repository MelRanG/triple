package com.example.triple.entity;

import com.example.triple.domain.photo.Photo;
import com.example.triple.domain.point.PointRepository;
import com.example.triple.domain.review.Review;
import com.example.triple.domain.review.ReviewRepository;
import com.example.triple.domain.user.UserRepository;
import com.example.triple.dto.EventDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PointRepositoryTest {

    public static final String json = "{\n" +
            "\"type\": \"REVIEW\",\n" +
            "\"action\": \"ADD\",\n" +
            "\"reviewId\": \"240a0658-dc5f-4878-9381-ebb7b2667772\",\n" +
            "\"content\": \"좋아요!\",\n" +
            "\"attachedPhotoIds\": [\"e4d1a64e-a531-46de-88d0-ff0ed70c0bb8\", \"afb0cef2-851d-4a50-bb07-9cc15cbdc332\"],\n" +
            "\"userId\": \"3ede0ef2-92b7-4817-a5f3-0c575361f745\",\n" +
            "\"placeId\": \"2e4baf1c-5acb-4efb-a1af-eddada31b00f\"\n" +
            "}";

    @Autowired
    PointRepository pointRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ReviewRepository reviewRepository;


    @Test
    @DisplayName("리뷰저장 후 review엔티티, user엔티티 확인")
    public void 리뷰저장() throws JsonProcessingException {
        //준비
        ObjectMapper objectMapper = new ObjectMapper();
        EventDto dto = objectMapper.readValue(json, EventDto.class);
        pointRepository.save(dto);
        //예상
        String reviewExpected = dto.getReviewId();
        String userExpected = dto.getUserId();
        //검증
        String reviewResult = reviewRepository.findById(dto.getReviewId()).orElse(null).getReviewId();
        String userResult = userRepository.findById(dto.getUserId()).orElse(null).getId();
        assertEquals(reviewExpected, reviewResult, "리뷰ID체크");
        assertEquals(userExpected, userResult, "유저ID체크");
    }


}