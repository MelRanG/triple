package com.example.triple.domain.review;

import com.example.triple.domain.basetime.BaseTime;
import com.example.triple.domain.photo.Photo;
import com.example.triple.domain.place.Place;
import com.example.triple.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Review extends BaseTime {
    @Id
    @Column(name="review_id")
    private String reviewId;
    private String content;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name="place_id")
    private Place place;

    @Builder
    public Review(String reviewId, String content, User user, Place place){
        Assert.hasText(reviewId, "reviewId is Not null");
        Assert.notNull(user, "user is Not null");
        Assert.notNull(place, "place is Not null");

        this.reviewId = reviewId;
        this.content = content;
        this.user = user;
        this.place = place;
    }

    public void setPlace(Place place){
        this.place = place;
    }

    public void setUser(User user){
        this.user = user;
    }
}
