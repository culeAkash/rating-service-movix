package com.movix.rating.service.controllers;


import com.movix.rating.service.requests.RatingRequest;
import com.movix.rating.service.responses.APIResponse;
import com.movix.rating.service.responses.AverageRatingResponse;
import com.movix.rating.service.responses.RatingResponse;
import com.movix.rating.service.services.RatingService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("/api/v1/ratings")
@AllArgsConstructor
public class RatingController {

    private final RatingService ratingService;

    @PostMapping("/changeRating")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<APIResponse<RatingResponse>> changeRating(
            @RequestParam("movieId") String movieId,
            @RequestParam("userId") String userId,
            @RequestBody @Valid RatingRequest ratingRequest) {

        RatingResponse ratingResponse = this.ratingService.createRatingByMovieAndUser(movieId, userId, ratingRequest);
        APIResponse<RatingResponse> ratingResponseAPIResponse = new APIResponse<>(
                "success",
                String.format("Rating is created for movie with movieId: %s by user with userId: %s",movieId,userId),
                HttpStatus.CREATED.value(),
                ratingResponse
        );
        return new ResponseEntity<>(ratingResponseAPIResponse, HttpStatus.CREATED);
    }

    @GetMapping("/getAverageRatingOfMovie")
    public ResponseEntity<APIResponse<AverageRatingResponse>> getAverageRatingOfMovie(
            @RequestParam("movieId") String movieId
    ){
        AverageRatingResponse ratingResponse = this.ratingService.getAverageRatingOfMovie(movieId);
        APIResponse<AverageRatingResponse> ratingResponseAPIResponse = new APIResponse<>(
                "success",
                String.format("Average Rating is fetched for movie with movieId: %s",movieId),
                HttpStatus.OK.value(),
                ratingResponse
        );
        return new ResponseEntity<>(ratingResponseAPIResponse, HttpStatus.OK);
    }

    @GetMapping("/getRatingOfMovieByUser")
    public ResponseEntity<APIResponse<RatingResponse>> getRatingOfMovieByUser(
            @RequestParam("movieId") String movieId,
            @RequestParam("userId") String userId
    ){
        RatingResponse ratingResponse = this.ratingService.getRatingOfMovieByUser(movieId, userId);
        APIResponse<RatingResponse> ratingResponseAPIResponse = new APIResponse<>(
                "success",
                String.format("Average Rating is fetched for movie with movieId: %s",movieId),
                HttpStatus.OK.value(),
                ratingResponse
        );
        return new ResponseEntity<>(ratingResponseAPIResponse, HttpStatus.OK);
    }

}
