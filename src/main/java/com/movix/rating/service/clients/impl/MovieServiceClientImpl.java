package com.movix.rating.service.clients.impl;

import com.movix.rating.service.clients.MovieServiceClient;
import com.movix.rating.service.dto.MovieDTO;
import org.springframework.http.ResponseEntity;

public class MovieServiceClientImpl implements MovieServiceClient {
    @Override
    public ResponseEntity<MovieDTO> getMovieById(String movieId) {
        return null;
    }
}
