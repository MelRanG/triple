package com.example.triple.domain.point;

import com.example.triple.domain.basetime.BaseTime;
import com.example.triple.domain.place.Place;
import com.example.triple.domain.review.Review;
import com.example.triple.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PointHistory extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String review;
    @Enumerated(value = EnumType.STRING)
    private PointType type;
    private int point;

    @Builder
    public PointHistory(User user, String review, PointType type, int point){
        Assert.notNull(user, "user is Not null");
        Assert.notNull(review, "review is Not null");
        Assert.notNull(type, "PointType is Not null");
        Assert.hasText(String.valueOf(point), "point is Not null");

        this.user = user;
        this.review = review;
        this.type = type;
        this.point = point;
    }

}
