package com.movix.rating.service.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.movix.rating.service.exceptions.GenericException;
import com.movix.rating.service.exceptions.ResourceNotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;


@Component
@Slf4j
public class CustomErrorDecoder implements ErrorDecoder {

    private final ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public Exception decode(String s, Response response) {
        try {
            Map<String,Object> errorMap = objectMapper.readValue(response.body().asInputStream(),Map.class);
            String message = (String) errorMap.get("message");
            return switch (response.status()) {
                case 400 -> new GenericException(message, HttpStatusCode.valueOf(400));
                case 404 -> new ResourceNotFoundException(message);
                case 500 -> new Exception(message);
                default -> new Exception("Unexpected error: " + message);
            };
        } catch (IOException e) {
            throw new RuntimeException("Error decoding response body",e);
        }
    }
}
