package com.movix.rating.service.repositories;

import com.movix.rating.service.entities.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, String> {
    public Optional<Rating> findByRatingUserIdAndRatingMovieId(String ratingUserId,String ratingMovieId);

    public List<Rating> findByRatingMovieId(String ratingMovieId);
}
