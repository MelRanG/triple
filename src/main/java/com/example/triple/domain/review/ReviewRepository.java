package com.example.triple.domain.review;

import com.example.triple.dto.EventDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, String>, ReviewRepositoryExt{

    @Override
    public void save(EventDto dto);
}
