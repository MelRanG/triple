package com.example.triple.domain.place;

import com.example.triple.domain.review.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Place {
    @Id
    private String placeId;
    @OneToMany(mappedBy = "place")
    List<Review> reviews = new ArrayList<>();

    public void addReview(Review review){
        this.reviews.add(review);
        review.setPlace(this);
    }

    public void setPlaceId(String placeId){
        this.placeId = placeId;
    }

}
