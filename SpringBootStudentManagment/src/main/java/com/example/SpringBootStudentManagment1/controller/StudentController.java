package com.example.SpringBootStudentManagment1.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.SpringBootStudentManagment1.dto.StudentCreateRequest;
import com.example.SpringBootStudentManagment1.dto.StudentUpdateRequest;
import com.example.SpringBootStudentManagment1.entity.Student;
import com.example.SpringBootStudentManagment1.service.StudentService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

@RestController // return JSON response and handle HTTP requests
@RequestMapping("/api/students")
public class StudentController {
    
    // why private final StudentService studentService?
    // 1) Encapsulation: By declaring studentService as private, we restrict direct access to it from outside the StudentController class. This promotes encapsulation and helps maintain the integrity of the controller's internal state.
    private final StudentService studentService;

    @Autowired // this optinal since we are using constructor injection 
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    // @RequestBody annotation is used to indicate that the method parameter (StudentCreateRequest) should be bound to the body of the HTTP request. This allows Spring to automatically deserialize the incoming JSON request body into a StudentCreateRequest object, which can then be used in the method to create a new student.
    // @Valid annotation is used to trigger validation on the StudentCreateRequest object based on the constraints defined in the DTO class. If the request body does not meet the validation criteria, Spring Boot will automatically return a 400 Bad Request response with details about the validation errors.
    public Student creaStudent(@Valid @RequestBody StudentCreateRequest request) {
        return studentService.create(request);
    }

    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAll();
    }

    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable Long id) {
        return studentService.getById(id);
    }

    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable Long id, @Valid @RequestBody StudentUpdateRequest request) {
        return studentService.update(id, request);
    }
    

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStudent(@PathVariable Long id) {
        studentService.delete(id);
    }

}
