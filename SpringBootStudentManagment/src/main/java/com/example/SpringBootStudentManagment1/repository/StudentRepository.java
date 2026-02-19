
// Spring data JPA 
package com.example.SpringBootStudentManagment1.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.SpringBootStudentManagment1.entity.Student;
import java.util.Optional;


// StudentRepository is an interface that extends JpaRepository, which provides CRUD operations for the Student entity.
// the first parameter (Student) is the type of the entity, and the second parameter (Long) is the type of the primary key (id).

public interface StudentRepository extends JpaRepository<Student, Long> {
    // this is derived query method, Spring Data JPA will automatically generate the implementation based on the method name. The "findByEmail" part tells Spring Data JPA to look for a student with a specific email address.
    // This method allows you to find a student by their email address. It returns an Optional<Student>, which means it may or may not contain a Student object (if no student with the given email is found).
    Optional<Student> findByEmail(String email);
}


 /*
    JpaRepository<Student, Long> gives you CRUD for free:
    save()
    findById()
    findAll()
    deleteById()
    */