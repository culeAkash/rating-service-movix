package com.movix.rating.service.services;

import com.movix.rating.service.repositories.RatingRepository;
import com.movix.rating.service.requests.RatingRequest;
import com.movix.rating.service.responses.AverageRatingResponse;
import com.movix.rating.service.responses.RatingResponse;
import org.springframework.stereotype.Service;

@Service
public interface RatingService {
    public RatingResponse createRatingByMovieAndUser(String movieId, String userId, RatingRequest ratingRequest);

    public AverageRatingResponse getAverageRatingOfMovie(String movieId);

    public RatingResponse getRatingOfMovieByUser(String movieId,String userId);
}
