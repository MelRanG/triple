package com.example.triple.domain.place;

import com.example.triple.domain.review.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Place {
    @Id
    private String placeId;

}
