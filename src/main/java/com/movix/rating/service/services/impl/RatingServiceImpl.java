package com.movix.rating.service.services.impl;

import com.movix.rating.service.clients.MovieServiceClient;
import com.movix.rating.service.clients.UserServiceClient;
import com.movix.rating.service.dto.MovieDTO;
import com.movix.rating.service.dto.UserDTO;
import com.movix.rating.service.entities.Rating;
import com.movix.rating.service.exceptions.GenericException;
import com.movix.rating.service.exceptions.ResourceNotFoundException;
import com.movix.rating.service.repositories.RatingRepository;
import com.movix.rating.service.requests.RatingRequest;
import com.movix.rating.service.responses.AverageRatingResponse;
import com.movix.rating.service.responses.RatingResponse;
import com.movix.rating.service.services.RatingService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;

    private final MovieServiceClient movieServiceClient;

    private final UserServiceClient userServiceClient;

    private final ModelMapper modelMapper;


    @Override
    public RatingResponse createRatingByMovieAndUser(String movieId, String userId, RatingRequest request) {
        // check if movie exists with movieId
        MovieDTO movieDTO = checkIfMovieExists(movieId);
        log.info("Movie : {}", movieDTO.toString());
        // check if user exists with userId
        UserDTO userDTO = checkIfUserExists(userId);
        log.info("User : {}", userDTO.toString());



        // check if user has already rated the movie
        Optional<Rating> presentRating = this.ratingRepository
                .findByRatingUserIdAndRatingMovieId(userDTO.getUserId(), movieDTO.getMovieId());
        Rating ratingResponse;
        if(presentRating.isPresent()) {
            Rating updatedRating = presentRating.get();
            updatedRating.setRatingValue(request.getRatingValue());
            ratingResponse = this.ratingRepository.save(updatedRating);
        }
        else{
            ratingResponse = this.ratingRepository.save(Rating.builder()
                    .ratingUserId(userDTO.getUserId())
                    .ratingMovieId(movieDTO.getMovieId())
                    .ratingValue(request.getRatingValue())
                    .build());
        }
        return RatingResponse.builder()
                .user(userDTO)
                .ratingId(ratingResponse.getRatingId())
                .ratingValue(ratingResponse.getRatingValue())
                .ratingCreationDate(ratingResponse.getRatingCreationDate())
                .ratingUpdateDate(ratingResponse.getRatingUpdateDate())
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
                .orElseThrow(()->
                        new GenericException(String.format("No rating has been given by user with userId : %s for movie with movieId : %s",userDTO.getUserId(),movieDTO.getMovieId()), HttpStatus.NOT_FOUND));

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
