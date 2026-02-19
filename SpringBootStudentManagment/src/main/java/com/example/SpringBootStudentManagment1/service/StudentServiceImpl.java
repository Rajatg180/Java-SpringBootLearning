package com.example.SpringBootStudentManagment1.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.SpringBootStudentManagment1.dto.StudentCreateRequest;
import com.example.SpringBootStudentManagment1.dto.StudentUpdateRequest;
import com.example.SpringBootStudentManagment1.entity.Student;
import com.example.SpringBootStudentManagment1.exception.BadRequestException;
import com.example.SpringBootStudentManagment1.exception.ResourceNotFoundException;
import com.example.SpringBootStudentManagment1.repository.StudentRepository;


// @Service annotation is used to indicate that this class is a service component in the Spring context, which contains business logic and can be injected into controllers or other services.
// the bean is created by Spring and can be injected into other components (like controllers) using @Autowired or constructor injection.
@Service
public class StudentServiceImpl implements StudentService{
    StudentRepository studentRepository;

    // this is constuctor injection , where its optinal to use @Autowired annotation, but since Spring 4.3, if the class has only one constructor, Spring will automatically use it for dependency injection, so we can omit the @Autowired annotation here.
    // if there are multiple constructors, we would need to annotate the one we want Spring to use with @Autowired.
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student create(StudentCreateRequest request) {
       if(studentRepository.findByEmail(request.getEmail()).isPresent()) {
           throw new BadRequestException("Email already exists" + request.getEmail());
       }

       Student student = new Student();
        student.setName(request.getName());
        student.setEmail(request.getEmail());
        student.setAge(request.getAge());
        return studentRepository.save(student); // save method will return the saved student with generated id
    }

    @Override
    public List<Student> getAll() {
       return studentRepository.findAll();
    }

    @Override
    public Student getById(Long id) {
        return studentRepository.findById(id).orElseThrow(() ->  new ResourceNotFoundException("Student not found with id: " + id));
    }

    @Override
    public Student update(Long id, StudentUpdateRequest request) {
        Student student = getById(id);
        // if email is being updated, check if the new email already exists for another student
        if(!student.getEmail().equals(request.getEmail()) && studentRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new BadRequestException("Email already exists: " + request.getEmail());
        }
        student.setName(request.getName());
        student.setEmail(request.getEmail());
        student.setAge(request.getAge());
        return studentRepository.save(student);
    }

    @Override
    public void delete(Long id) {
        Student student = getById(id); 
        studentRepository.delete(student);
        /*
        OR 
        studentRepository.deleteById(id);
        */
    }
}
