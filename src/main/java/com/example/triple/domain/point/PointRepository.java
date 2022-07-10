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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class PointRepository {
    @Autowired
    private PlaceRepository placeRepository;
    @Autowired
    private PhotoRepository photoRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    public void save(EventDto dto) {
        //장소 생성
        Place place = new Place(dto.getPlaceId());
        placeRepository.save(place);

        int point = getUserPointScore(dto);
        User user = new User(dto.getUserId(), point);
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
    }

    public void update(EventDto dto){

    }

    private int getUserPointScore(EventDto dto) {
        User user = userRepository.findById(dto.getUserId()).orElse(null);
        int score = user != null ? user.getPoint() : 0;

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
