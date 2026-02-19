/*
Create DTOs (Request objects  and Response objects) + Validation
Why DTO?
1) Don’t expose Entity directly in requests and responses
2) Allows validation and clean API
*/
package com.example.SpringBootStudentManagment1.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/*
what happend if request object is invalid?
1) Spring Boot will automatically return a 400 Bad Request response with a JSON body containing the validation error
2) this validation is triggered when the controller method is called with a @Valid annotation on the request body parameter. If the validation fails, Spring Boot will handle the error and return an appropriate response to the client.
*/
public class StudentCreateRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotNull(message = "Age is required")
    @Min(value = 1, message = "Age must be at least 1")
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
