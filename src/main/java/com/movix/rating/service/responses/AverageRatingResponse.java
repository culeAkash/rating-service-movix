package com.movix.rating.service.responses;

import com.movix.rating.service.dto.MovieDTO;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AverageRatingResponse {
    private Double averageRating;
    private Long usersRated;
    private MovieDTO movie;
}
