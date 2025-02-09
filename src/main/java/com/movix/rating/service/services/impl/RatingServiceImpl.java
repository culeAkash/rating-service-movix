package com.movix.rating.service.services.impl;

import com.movix.rating.service.clients.MovieServiceClient;
import com.movix.rating.service.clients.UserServiceClient;
import com.movix.rating.service.dto.MovieDTO;
import com.movix.rating.service.dto.UserDTO;
import com.movix.rating.service.entities.Rating;
import com.movix.rating.service.exceptions.ResourceNotFoundException;
import com.movix.rating.service.repositories.RatingRepository;
import com.movix.rating.service.requests.RatingRequest;
import com.movix.rating.service.responses.AverageRatingResponse;
import com.movix.rating.service.responses.RatingResponse;
import com.movix.rating.service.services.RatingService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;

    private final MovieServiceClient movieServiceClient;

    private final UserServiceClient userServiceClient;

    private final ModelMapper modelMapper;


    @Override
    public RatingResponse createRatingByMovieAndUser(String movieId, String userId, RatingRequest request) {
        // check if movie exists with movieId
        MovieDTO movieDTO = checkIfMovieExists(movieId);

        // check if user exists with userId
        UserDTO userDTO = checkIfUserExists(userId);

        // check if user has already rated the movie
        Optional<Rating> presentRating = this.ratingRepository
                .findByRatingUserIdAndRatingMovieId(userDTO.getUserId(), movieDTO.getMovieId());

        if(presentRating.isPresent()) {
            Rating updatedRating = presentRating.get();
            updatedRating.setRatingValue(request.getRatingValue());
            Rating updatedNewRating = this.ratingRepository.save(updatedRating);
            return this.modelMapper.map(updatedNewRating, RatingResponse.class);
        }

        Rating createdRating = this.ratingRepository.save(Rating.builder()
                        .ratingUserId(userDTO.getUserId())
                        .ratingMovieId(movieDTO.getMovieId())
                        .ratingValue(request.getRatingValue())
                .build());

        return RatingResponse.builder()
                .user(userDTO)
                .ratingId(createdRating.getRatingId())
                .ratingValue(createdRating.getRatingValue())
                .ratingCreationDate(createdRating.getRatingCreationDate())
                .ratingUpdateDate(createdRating.getRatingUpdateDate())
                .movie(movieDTO)
                .build();
    }

    @Override
    public AverageRatingResponse getAverageRatingOfMovie(String movieId) {
        //check if movie with movieId exists
        MovieDTO movieDTO = checkIfMovieExists(movieId);

        List<Rating> ratingList = this.ratingRepository.findByRatingMovieId(movieDTO.getMovieId());

        Double averageRating = ratingList.stream()
                .mapToDouble(Rating::getRatingValue)
                .average()
                .orElse(0.0);

        return AverageRatingResponse.builder()
                .averageRating(averageRating)
                .movie(movieDTO)
                .usersRated((long) ratingList.size())
                .build();
    }

    @Override
    public RatingResponse getRatingOfMovieByUser(String movieId, String userId) {
        MovieDTO movieDTO = checkIfMovieExists(movieId);
        UserDTO userDTO = checkIfUserExists(userId);

        Rating rating = this.ratingRepository.findByRatingUserIdAndRatingMovieId(userDTO.getUserId(), movieDTO.getMovieId())
                .orElse(Rating.builder()
                        .ratingUserId(userDTO.getUserId())
                        .ratingMovieId(movieDTO.getMovieId())
                        .ratingValue(0).build());

        return RatingResponse.builder()
                .user(userDTO)
                .ratingId(rating.getRatingId())
                .ratingValue(rating.getRatingValue())
                .ratingCreationDate(rating.getRatingCreationDate())
                .ratingUpdateDate(rating.getRatingUpdateDate())
                .movie(movieDTO)
                .build();
    }

    private MovieDTO checkIfMovieExists(String movieId) {
        return Optional.ofNullable(movieServiceClient.getMovieById(movieId).getBody())
                .orElseThrow(()->new ResourceNotFoundException("Movie","movieId",movieId));
    }

    private UserDTO checkIfUserExists(String userId) {
        return Optional.ofNullable(userServiceClient.getUserById(userId).getBody())
                .orElseThrow(()->new ResourceNotFoundException("User","userId",userId));
    }
}
