package com.movix.rating.service.clients;

import com.movix.rating.service.clients.impl.UserServiceClientImpl;
import com.movix.rating.service.config.FeignConfig;
import com.movix.rating.service.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service",fallback = UserServiceClientImpl.class,configuration = FeignConfig.class)
public interface UserServiceClient {

    @GetMapping("/api/v1/users/getUserById")
    public ResponseEntity<UserDTO> getUserById(@RequestParam("userId") String userId);

}


