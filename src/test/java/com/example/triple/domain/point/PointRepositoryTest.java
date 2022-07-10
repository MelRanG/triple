package com.example.triple.domain.point;

import com.example.triple.domain.review.Review;
import com.example.triple.domain.review.ReviewRepository;
import com.example.triple.domain.user.User;
import com.example.triple.domain.user.UserRepository;
import com.example.triple.dto.EventDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PointRepositoryTest {

    @Autowired
    PointRepository pointRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ReviewRepository reviewRepository;


    @Test
    @DisplayName("리뷰저장 후 review엔티티, user엔티티 확인")
    public void 리뷰저장() throws JsonProcessingException {
        String json = "{\n" +
                "\"type\": \"REVIEW\",\n" +
                "\"action\": \"ADD\",\n" +
                "\"reviewId\": \"240a0658-dc5f-4878-9381-ebb7b2667772\",\n" +
                "\"content\": \"좋아요!\",\n" +
                "\"attachedPhotoIds\": [\"e4d1a64e-a531-46de-88d0-ff0ed70c0bb8\", \"afb0cef2-851d-4a50-bb07-9cc15cbdc332\"],\n" +
                "\"userId\": \"3ede0ef2-92b7-4817-a5f3-0c575361f745\",\n" +
                "\"placeId\": \"2e4baf1c-5acb-4efb-a1af-eddada31b00f\"\n" +
                "}";
        //준비
        ObjectMapper objectMapper = new ObjectMapper();
        EventDto dto = objectMapper.readValue(json, EventDto.class);
        pointRepository.save(dto);
        //예상
        String reviewExpected = dto.getReviewId();
        String userExpected = dto.getUserId();
        //검증
        String reviewResult = reviewRepository.findById(dto.getReviewId()).orElse(null).getReviewId();
        String userResult = userRepository.findById(dto.getUserId()).orElse(null).getUserId();
        assertEquals(reviewExpected, reviewResult, "리뷰ID체크");
        assertEquals(userExpected, userResult, "유저ID체크");
    }

    @Test
    @DisplayName("같은 장소에 2명의 유저가 저장 후 포인트 조회")
    public void 같은장소에저장() throws JsonProcessingException {
        String json1 = "{\n" +
                "\"type\": \"REVIEW\",\n" +
                "\"action\": \"ADD\",\n" +
                "\"reviewId\": \"240a0658-dc5f-4878-9381-ebb7b2667772\",\n" +
                "\"content\": \"좋아요!\",\n" +
                "\"attachedPhotoIds\": [\"e4d1a64e-a531-46de-88d0-ff0ed70c0bb8\", \"afb0cef2-851d-4a50-bb07-9cc15cbdc332\"],\n" +
                "\"userId\": \"3ede0ef2-92b7-4817-a5f3-0c575361f745\",\n" +
                "\"placeId\": \"2e4baf1c-5acb-4efb-a1af-eddada31b00f\"\n" +
                "}";
        String json2 = "{\n" +
                "\"type\": \"REVIEW\",\n" +
                "\"action\": \"ADD\",\n" +
                "\"reviewId\": \"240a0658-dc5f-4878-9381-ebb7b26677723\",\n" +
                "\"content\": \"좋아요!\",\n" +
                "\"attachedPhotoIds\": [\"e4d1a64e-a531-46de-88d0-ff0ed70c0bb8\", \"afb0cef2-851d-4a50-bb07-9cc15cbdc332\"],\n" +
                "\"userId\": \"3ede0ef2-92b7-4817-a5f3-0c575361f7453\",\n" +
                "\"placeId\": \"2e4baf1c-5acb-4efb-a1af-eddada31b00f\"\n" +
                "}";
        //준비
        ObjectMapper objectMapper = new ObjectMapper();
        EventDto dto = objectMapper.readValue(json1, EventDto.class);
        pointRepository.save(dto);
        dto = objectMapper.readValue(json2, EventDto.class);
        pointRepository.save(dto);
        //예상
        int expectedFirstUser = 3;
        int expectedSecondUser = 2;
        //검증
        int firstUser = userRepository.findById("3ede0ef2-92b7-4817-a5f3-0c575361f745").orElse(null).getPoint();
        int secoundUser = userRepository.findById("3ede0ef2-92b7-4817-a5f3-0c575361f7453").orElse(null).getPoint();
        assertEquals(expectedFirstUser, firstUser);
        assertEquals(expectedSecondUser, secoundUser);
    }

    @Test
    @DisplayName("첫리뷰 + 사진없이 저장 후 포인트 조회")
    public void 사진없이저장() throws JsonProcessingException {
        String json = "{\n" +
                "\"type\": \"REVIEW\",\n" +
                "\"action\": \"ADD\",\n" +
                "\"reviewId\": \"240a0658-dc5f-4878-9381-ebb7b26677723\",\n" +
                "\"content\": \"좋아요!\",\n" +
                "\"attachedPhotoIds\": [],\n" +
                "\"userId\": \"3ede0ef2-92b7-4817-a5f3-0c575361f7453\",\n" +
                "\"placeId\": \"2e4baf1c-5acb-4efb-a1af-eddada31b00f\"\n" +
                "}";
        //준비
        ObjectMapper objectMapper = new ObjectMapper();
        EventDto dto = objectMapper.readValue(json, EventDto.class);
        pointRepository.save(dto);
        //예상
        int expected = 2;
        //검증
        int user = userRepository.findById("3ede0ef2-92b7-4817-a5f3-0c575361f7453").orElse(null).getPoint();
        assertEquals(expected, user);
    }

    @Test
    @DisplayName("리뷰수정 사진+텍스트+최초작성리뷰에서 텍스트+최초작성리뷰로")
    public void 리뷰사진수정() throws JsonProcessingException {
        String json1 = "{\n" +
                "\"type\": \"REVIEW\",\n" +
                "\"action\": \"ADD\",\n" +
                "\"reviewId\": \"240a0658-dc5f-4878-9381-ebb7b2667772\",\n" +
                "\"content\": \"좋아요!\",\n" +
                "\"attachedPhotoIds\": [\"e4d1a64e-a531-46de-88d0-ff0ed70c0bb8\", \"afb0cef2-851d-4a50-bb07-9cc15cbdc332\"],\n" +
                "\"userId\": \"3ede0ef2-92b7-4817-a5f3-0c575361f745\",\n" +
                "\"placeId\": \"2e4baf1c-5acb-4efb-a1af-eddada31b00f\"\n" +
                "}";
        String json2 = "{\n" +
                "\"type\": \"REVIEW\",\n" +
                "\"action\": \"MOD\",\n" +
                "\"reviewId\": \"240a0658-dc5f-4878-9381-ebb7b2667772\",\n" +
                "\"content\": \"좋아요!\",\n" +
                "\"attachedPhotoIds\": [],\n" +
                "\"userId\": \"3ede0ef2-92b7-4817-a5f3-0c575361f745\",\n" +
                "\"placeId\": \"2e4baf1c-5acb-4efb-a1af-eddada31b00f\"\n" +
                "}";
        //준비
        ObjectMapper objectMapper = new ObjectMapper();
        EventDto dto = objectMapper.readValue(json1, EventDto.class);
        pointRepository.save(dto);
        dto = objectMapper.readValue(json2, EventDto.class);
        pointRepository.update(dto);
        //예상
        int expected = 2;
        //검증
        User user = userRepository.findById("3ede0ef2-92b7-4817-a5f3-0c575361f745").orElse(null);
        assertEquals(expected, user.getPoint());
    }

    @Test
    @DisplayName("1,2번유저 삽입 후 1번유저 값 수정")
    public void MOD유저수정() throws JsonProcessingException {
        String json1 = "{\n" +
                "\"type\": \"REVIEW\",\n" +
                "\"action\": \"ADD\",\n" +
                "\"reviewId\": \"1\",\n" +
                "\"content\": \"\",\n" +
                "\"attachedPhotoIds\": [\"e4d1a64e-a531-46de-88d0-ff0ed70c0bb8\",\"e4d1a6-a531-46de-88d0-ff0ed70c0bb8\"],\n" +
                "\"userId\": \"1\",\n" +
                "\"placeId\": \"1\"\n" +
                "}";
        String json2 = "{\n" +
                "\"type\": \"REVIEW\",\n" +
                "\"action\": \"ADD\",\n" +
                "\"reviewId\": \"2\",\n" +
                "\"content\": \"좋아요!\",\n" +
                "\"attachedPhotoIds\": [\"e4d1ae-a531-46de-88d0-ff0ed70c0bb8\",\"e4d1a531-46de-88d0-ff0ed70c0bb8\"],\n" +
                "\"userId\": \"2\",\n" +
                "\"placeId\": \"1\"\n" +
                "}";

        //준비
        ObjectMapper objectMapper = new ObjectMapper();
        EventDto dto = objectMapper.readValue(json1, EventDto.class);
        pointRepository.save(dto);
        dto = objectMapper.readValue(json2, EventDto.class);
        pointRepository.save(dto);

        //수정json
        String updateJson1 = "{\n" +
                "\"type\": \"REVIEW\",\n" +
                "\"action\": \"MOD\",\n" +
                "\"reviewId\": \"1\",\n" +
                "\"content\": \"좋아요!\",\n" +
                "\"attachedPhotoIds\": [\"e4d1a64e-a531-46de-88d0-ff0ed70c0bb8\",\"e4d1a64e-a531-46de-88d0-ff0ed70c0bb8\"],\n" +
                "\"userId\": \"1\",\n" +
                "\"placeId\": \"1\"\n" +
                "}";

        dto = objectMapper.readValue(updateJson1, EventDto.class);
        pointRepository.update(dto);
        //예상
        int expected = 3;

        //검증
        User user = userRepository.findById("1").orElse(null);
        assertEquals(expected, user.getPoint());
    }

    @Test
    @DisplayName("리뷰 삭제")
    public void 리뷰삭제() throws JsonProcessingException {
        String json = "{\n" +
                "\"type\": \"REVIEW\",\n" +
                "\"action\": \"ADD\",\n" +
                "\"reviewId\": \"1\",\n" +
                "\"content\": \"\",\n" +
                "\"attachedPhotoIds\": [\"e4d1a64e-a531-46de-88d0-ff0ed70c0bb8\",\"e4d1a6-a531-46de-88d0-ff0ed70c0bb8\"],\n" +
                "\"userId\": \"1\",\n" +
                "\"placeId\": \"1\"\n" +
                "}";

        //준비
        ObjectMapper objectMapper = new ObjectMapper();
        EventDto dto = objectMapper.readValue(json, EventDto.class);
        pointRepository.save(dto);
        pointRepository.delete(dto);

        //예상
        String expected = null;
        //검증
        Review review = reviewRepository.findById(dto.getReviewId()).orElse(null);
        assertEquals(expected, review);
    }
}