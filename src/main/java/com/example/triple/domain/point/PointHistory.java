package com.example.triple.domain.point;

import com.example.triple.domain.review.Review;
import com.example.triple.domain.user.User;

import javax.persistence.*;

public class PointHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;
    private Review review;
    private int point;

}
