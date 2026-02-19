package com.example.SpringBootEcommerceBackend.api.users;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.SpringBootEcommerceBackend.domain.user.User;
import com.example.SpringBootEcommerceBackend.infrastructure.repo.UserRepository;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping("api/users")

public class UserController {
    
    private final UserRepository userRepository;

    UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    // for testing purpose , we are creating records for createuserrequest and userresponse 
    // record classes are a new feature in Java 16 that allow us to create immutable data classes with less boilerplate code, we can use them to create simple DTOs (Data Transfer Objects) for our API requests and responses
    // this only has fields , no methods and the constructor, getters, equals, hashcode and toString methods are generated automatically by the compiler
    // also all fields are final and the class is immutable, which means that once we create an instance of this class, we cannot change its state, this is a good practice for DTOs because it makes them thread-safe and easier to reason about
    record CreateUserRequest( @Email @NotBlank String email, @NotBlank String fullName){}

    record UserResponse(Long id, String email, String fullName){}

    @PostMapping
    public UserResponse create(@RequestBody CreateUserRequest req) {
        var user = userRepository.save(new User(req.email(), req.fullName()));
        return new UserResponse(user.getId(), user.getEmail(), user.getFullName());
    }
}
