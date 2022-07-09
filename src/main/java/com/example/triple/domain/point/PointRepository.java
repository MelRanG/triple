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
        //User, Place테이블에는 값이 들어있지만 테스트를 위해 저장함.
        User user = new User(dto.getUserId());
        Place place = new Place(dto.getPlaceId());

        userRepository.save(user);
        placeRepository.save(place);
        Review review = Review.builder()
                .reviewId(dto.getReviewId())
                .place(place)
                .user(user)
                .content(dto.getContent())
                .build();
        reviewRepository.save(review);
        saveAttachedPhoto(dto.getAttachedPhotoIds(), review);

    }

    public void saveAttachedPhoto(List<String> photoIds, Review review){

        photoIds.forEach(i -> {
            Photo photo = new Photo(i, review);
            photoRepository.save(photo);
        });
    }
}
