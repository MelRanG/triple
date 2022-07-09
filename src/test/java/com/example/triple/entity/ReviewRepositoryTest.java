package com.example.triple.entity;

import com.example.triple.domain.photo.Photo;
import com.example.triple.domain.review.ReviewRepository;
import com.example.triple.dto.EventDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class ReviewRepositoryTest {

    @Autowired
    ReviewRepository reviewRepository;


    @Test
    public void 리뷰저장() throws JsonProcessingException {
        //준비
        ObjectMapper objectMapper = new ObjectMapper();
        String json = "{\n" +
                "\"type\": \"REVIEW\",\n" +
                "\"action\": \"ADD\",\n" +
                "\"reviewId\": \"240a0658-dc5f-4878-9381-ebb7b2667772\",\n" +
                "\"content\": \"좋아요!\",\n" +
                "\"attachedPhotoIds\": [\"e4d1a64e-a531-46de-88d0-ff0ed70c0bb8\", \"afb0cef2-851d-4a50-bb07-9cc15cbdc332\"],\n" +
                "\"userId\": \"3ede0ef2-92b7-4817-a5f3-0c575361f745\",\n" +
                "\"placeId\": \"2e4baf1c-5acb-4efb-a1af-eddada31b00f\"\n" +
                "}";
        EventDto dto = objectMapper.readValue(json, EventDto.class);

        //
        System.out.println("userId: " + dto.getUserId());
        reviewRepository.save(dto);
        //예상

        //검증
    }
}