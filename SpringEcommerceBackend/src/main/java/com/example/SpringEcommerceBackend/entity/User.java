package com.example.SpringEcommerceBackend.entity;

import com.example.SpringEcommerceBackend.enums.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
// uniqueconstrainte is used to ensure that the emai
@Table(name = "users" , uniqueConstraints = @UniqueConstraint(columnNames = {"email"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// builder annotation is used to implement the builder pattern for the User class, which allows for more flexible and readable object creation. It generates a builder class with methods for setting each field and a build() method to create an instance of the User class.
@Builder 
public class User extends  BaseEntity{
    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
}


/* 

Builder design patter :

With builder Example :
User user = User.builder().name("John Doe")
                .email("xyz@gmail.com")
                .password("password")
                .role(Role.CUSTOMER)
                .build();

Without builder Example :
User user = new User();
user.setName("John Doe");
user.setEmail("xyz@gmail.com");
user.setPassword("password");
user.setRole(Role.CUSTOMER);


*/