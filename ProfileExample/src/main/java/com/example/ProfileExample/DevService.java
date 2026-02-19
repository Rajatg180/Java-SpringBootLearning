package com.example.ProfileExample;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("dev") // this will only be active when 'dev' profile is active
public class DevService {

    private final String message;
    
    // @Value is used to inject the value of 'app.message' from application.yaml ( based on active profile)
    public DevService(@Value("${app.message}") String message) {
        this.message = message;
        System.out.println(" ✅ DevService bean created");
        System.out.println("Message: " + message);
    }

    public String getMessage() {
        return message;
    }
}



