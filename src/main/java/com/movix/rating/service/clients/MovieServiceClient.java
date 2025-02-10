package com.movix.rating.service.clients;


import com.movix.rating.service.clients.impl.MovieServiceClientImpl;
import com.movix.rating.service.clients.impl.UserServiceClientImpl;
import com.movix.rating.service.config.FeignConfig;
import com.movix.rating.service.dto.MovieDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "movie-service",fallback = MovieServiceClientImpl.class,configuration = FeignConfig.class)
public interface MovieServiceClient {
    @GetMapping("/api/v1/movies/getMovieById")
    public ResponseEntity<MovieDTO> getMovieById(@RequestParam("movieId") String movieId);
}
