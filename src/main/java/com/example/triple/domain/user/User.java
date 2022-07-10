package com.example.triple.domain.user;

import com.example.triple.domain.review.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="users")
public class User {
    @Id
    private String userId;

//    @OneToMany(mappedBy = "place")
//    List<Review> reviews = new ArrayList<>();
//
//    public void addReview(Review review){
//        this.reviews.add(review);
//        review.setUser(this);
//    }

    public void setUserId(String userId){
        this.userId = userId;
    }
}
