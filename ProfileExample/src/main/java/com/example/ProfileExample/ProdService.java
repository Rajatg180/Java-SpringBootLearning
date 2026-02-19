package com.example.ProfileExample;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("prod")
public class ProdService {

     private final String message;

    public ProdService(@Value("${app.message}") String message) {
        this.message = message;
        System.out.println("✅ ProdService bean created");
        System.out.println("Message: " + message);
    }

    public String getMessage() {
        return message;
    }
    
}
