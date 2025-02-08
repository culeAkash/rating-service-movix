package com.movix.rating.service.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;

@Entity
@Table(name = "ratings")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "rating_id")
    private String ratingId;

    @Column(name = "rating_value", nullable = false)
    private Integer ratingValue;

    @Column(name = "rating_creation_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
    @CreationTimestamp
    private LocalDate ratingCreationDate;

    @Column(name = "rating_update_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
    @UpdateTimestamp
    private LocalDate ratingUpdateDate;

    @Column(name = "rating_user_id",nullable = true)
    private String ratingUserId;

    @Column(name="rating_movie_id",nullable = false)
    private String ratingMovieId;
}
