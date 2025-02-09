package com.movix.rating.service.clients.impl;

import com.movix.rating.service.clients.UserServiceClient;
import com.movix.rating.service.dto.UserDTO;
import org.springframework.http.ResponseEntity;

public class UserServiceClientImpl implements UserServiceClient {
    @Override
    public ResponseEntity<UserDTO> getUserById(String userId) {
        return null;
    }
}
