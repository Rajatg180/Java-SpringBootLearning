package com.example.ProfileExample;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
@Profile("default") // this will only be active when 'default' profile is active (optinal as 'default' is the fallback profile)
public class DefaualtService {

    // lifecycle 
    // 1) constructor -> 2) dependency injection (in this case value of message will set ) -> 3) @PostConstruct method will be called (init method in this case)
    
    @Value("${app.message}")
    private String message;
    
    public DefaualtService() {
        System.out.println("DefaualtService bean created ");
    }

    @PostConstruct
    public void init() {
        System.out.println("Message: " + message);
    }

    public String getMessage() {
        return message;
    }

    
}
