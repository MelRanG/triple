package com.example.triple.domain.point;

import com.example.triple.domain.photo.Photo;
import com.example.triple.domain.photo.PhotoRepository;
import com.example.triple.domain.place.Place;
import com.example.triple.domain.place.PlaceRepository;
import com.example.triple.domain.review.Review;
import com.example.triple.domain.review.ReviewRepository;
import com.example.triple.domain.user.User;
import com.example.triple.domain.user.UserRepository;
import com.example.triple.dto.EventDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
@Slf4j
public class PointRepository {
    @Autowired
    private PlaceRepository placeRepository;
    @Autowired
    private PhotoRepository photoRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private PointHistoryRepository pointHistoryRepository;

    public void save(EventDto dto) {
        //장소 생성
        Place place = new Place(dto.getPlaceId());
        placeRepository.save(place);

        //유저 생성
        User pointUser = userRepository.findById(dto.getUserId()).orElse(null);
        int basicPoint = pointUser != null ? pointUser.getPoint() : 0;
        System.out.println("BasicPoint: " + basicPoint);
        int addPoint = getUserPointScore(dto);
        User user = new User(dto.getUserId(), basicPoint + addPoint);
        userRepository.save(user);

        //리뷰 생성
        Review review = Review.builder()
                .reviewId(dto.getReviewId())
                .place(place)
                .user(user)
                .content(dto.getContent())
                .build();
        reviewRepository.save(review);

        saveAttachedPhoto(dto.getAttachedPhotoIds(), review);

        //포인트 기록 등록
        PointHistory pointHistory = PointHistory.builder()
                .review(review.getReviewId())
                .user(user)
                .type(PointType.ADD)
                .point(addPoint)
                .build();
        pointHistoryRepository.save(pointHistory);
    }

    public void update(EventDto dto){
        //장소와 유저는 그대로고 content와 photo만 바뀜
        Place place = placeRepository.findById(dto.getPlaceId()).orElse(null);
        User user = userRepository.findById(dto.getUserId()).orElse(null);

        //기존에 가진 포인트를 구함
        String content = reviewRepository.findById(dto.getReviewId()).orElse(null).getContent();
        List<Photo> photos = photoRepository.findByReviewId(dto.getReviewId());
        int point = updatePointScore(content, photos);

        //새로 수정된 포인트를 구함
        int updatePoint = updatePointScore(dto);

        //둘의 차이만큼 유저칼럼의 포인트 값 수정
        int userPoint = user.getPoint() - (point - updatePoint);
        user.setPoint(userPoint);
        System.out.println("point : " + point + " updatePoint: " + updatePoint +" userPoint: " + user.getPoint());
        userRepository.save(user);

        Review review = Review.builder()
                .reviewId(dto.getReviewId())
                .place(place)
                .user(user)
                .content(dto.getContent())
                .build();
        reviewRepository.save(review);
        saveAttachedPhoto(dto.getAttachedPhotoIds(), review);

        updatePoint += isFirstWritten(dto);
        PointHistory pointHistory = PointHistory.builder()
                .review(review.getReviewId())
                .user(user)
                .type(PointType.UPDATE)
                .point(updatePoint)
                .build();
        pointHistoryRepository.save(pointHistory);
    }

    public void delete(EventDto dto){
        //포인트 수정
        User user = userRepository.findById(dto.getUserId()).orElse(null);
        int point = isFirstWritten(dto) + updatePointScore(dto);
        user.setPoint(user.getPoint() - point);
        userRepository.save(user);

        Review review = Review.builder()
                .reviewId(dto.getReviewId())
                .place(placeRepository.findById(dto.getPlaceId()).orElse(null))
                .user(user)
                .content(dto.getContent())
                .build();

        //로그 등록
        PointHistory pointHistory = PointHistory.builder()
                .review(review.getReviewId())
                .user(user)
                .type(PointType.DELETE)
                .point(point)
                .build();
        pointHistoryRepository.save(pointHistory);

        //이미지 삭제
        photoRepository.deleteByReviewId(dto.getReviewId());
        //리뷰 삭제
        reviewRepository.delete(review);

    }

    private int updatePointScore(String content, List<Photo> photos){
        int score = 0;
        score += content.length() > 0 ? 1 : 0;
        score += photos.size() > 0 ? 1 :0;
        return score;
    }

    private int updatePointScore(EventDto dto){
        int score = 0;
        score += dto.getContent().length() > 0 ? 1 : 0;
        score += dto.getAttachedPhotoIds().size() > 0 ? 1 :0;
        return score;
    }

    private int isFirstWritten(EventDto dto){
        int firstWritten = reviewRepository.findByFirstWritten(dto.getPlaceId(), dto.getUserId());
        return firstWritten > 0 ? 0 : 1;
    }

    private int getUserPointScore(EventDto dto) {
        int score = 0;
        score += dto.getContent().length() > 0 ? 1 : 0;
        score += dto.getAttachedPhotoIds().size() > 0 ? 1 :0;
        //해당 장소에 첫 리뷰 작성자인지 체크
        score += ifPlaceExists(dto.getPlaceId()) ? 0 : 1;
        return score;
    }

    private boolean ifPlaceExists(String placeId){
        int size = reviewRepository.findByPlaceId(placeId);
        return size > 0 ? true : false;
    }

    public void saveAttachedPhoto(List<String> photoIds, Review review){
        photoIds.forEach(i -> {
            Photo photo = new Photo(i, review);
            photoRepository.save(photo);
        });
    }
}
