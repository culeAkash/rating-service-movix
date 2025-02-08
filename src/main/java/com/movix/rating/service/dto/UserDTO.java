package com.movix.rating.service.dto;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UserDTO {
    private String userId;
    private String username;
    private String email;
}
