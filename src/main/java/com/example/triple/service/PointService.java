package com.example.triple.service;

import com.example.triple.domain.place.Place;
import com.example.triple.domain.place.PlaceRepository;
import com.example.triple.domain.point.PointRepository;
import com.example.triple.domain.review.Review;
import com.example.triple.domain.review.ReviewRepository;
import com.example.triple.dto.EventDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;

@Service
public class PointService {

    @Autowired
    PointRepository pointRepository;
    @Autowired
    PlaceRepository placeRepository;
    @Autowired
    ReviewRepository reviewRepository;

    public EventDto DispatcherType(EventDto dto){
        switch (dto.getAction()){
            case "ADD" :
                return add(dto);
            case "MOD" :
                return mod(dto);
            case "DELETE" :
                return delete(dto);
            default: return null;
        }
    }

    private EventDto delete(EventDto dto) {

        return null;
    }

    private EventDto mod(EventDto dto) {

        return null;
    }

    private EventDto add(EventDto dto) {
        if(isExists(dto)) return null;
        System.out.println("save 실행");
        pointRepository.save(dto);
        return dto;
    }

    private boolean isExists(EventDto dto){
        Review review = reviewRepository.findByPlaceIdAndUserId(dto.getPlaceId(), dto.getUserId());
        System.out.println("review: " + review);
        //return review != null ? true : false;
        if(review != null) return true;
        return false;
    }
}
