package com.example.triple.domain.review;

import com.example.triple.domain.photo.Photo;
import com.example.triple.domain.place.Place;
import com.example.triple.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Review {
    @Id
    @Column(name="review_id")
    private String reviewId;
    private String content;
    @OneToMany(mappedBy = "review")
    private List<Photo> attachedPhotoIds = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="place_id")
    private Place place;

    @Builder
    public Review(String reviewId, String content, User user, Place place){
        Assert.hasText(reviewId, "reviewId is null");
        Assert.hasText(content, "content is null");
        Assert.notNull(user, "user is null");
        Assert.notNull(place, "place is null");

        this.reviewId = reviewId;
        this.content = content;
        this.user = user;
        this.place = place;
    }
}
