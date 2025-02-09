package com.movix.rating.service.requests;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RatingRequest {
    @NotNull
    @Min(value = 1,message = "Rating Value must not be less than 1")
    @Max(value = 10,message = "Rating Value must not be greater than 10")
    private Integer ratingValue;
}
