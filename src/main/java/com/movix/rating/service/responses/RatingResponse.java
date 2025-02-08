package com.movix.rating.service.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.movix.rating.service.dto.MovieDTO;
import com.movix.rating.service.dto.UserDTO;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RatingResponse {
    private String ratingId;
    private Integer ratingValue;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
    private LocalDate ratingCreationDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
    private LocalDate ratingUpdateDate;

    private UserDTO user;
    private MovieDTO movie;
}
