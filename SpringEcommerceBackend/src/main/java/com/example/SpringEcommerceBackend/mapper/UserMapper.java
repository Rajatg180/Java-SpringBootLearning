
// this is manual mapper class to convert User entity to UserDTO and vice versa. 
// We can also use libraries like MapStruct for this purpose, but here we are doing it manually for simplicity.
package com.example.SpringEcommerceBackend.mapper;


import com.example.SpringEcommerceBackend.dto.RegisterRequest;
import com.example.SpringEcommerceBackend.dto.UserResponse;
import com.example.SpringEcommerceBackend.entity.User;

public class UserMapper {
    // convert RegisterRequest DTO to User entity
    public static User toEntity(RegisterRequest registerRequest){
        return User.builder()
                .name(registerRequest.getName())
                .email(registerRequest.getEmail())
                .password(registerRequest.getPassword())
                .build();
    }

    // convert User entity to UserResponse DTO
    public static UserResponse toResponse(User user){
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}
