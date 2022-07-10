package com.example.triple.domain.photo;

import com.example.triple.domain.review.Review;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Photo {
    @Id
    private String photoId;
    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;
}
