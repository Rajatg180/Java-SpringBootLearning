package com.example.SpringBootStudentManagment1.service;

import java.util.List;

import com.example.SpringBootStudentManagment1.dto.StudentCreateRequest;
import com.example.SpringBootStudentManagment1.dto.StudentUpdateRequest;
import com.example.SpringBootStudentManagment1.entity.Student;

// this is the service interface that defines the contract for student management operations , which will be implemented by the StudentServiceImpl class. It includes methods for creating, retrieving, updating, and deleting students.
public interface StudentService {
    Student create(StudentCreateRequest request);
    List<Student> getAll();
    Student getById(Long id);
    Student update(Long id, StudentUpdateRequest request);
    void delete(Long id);
}
