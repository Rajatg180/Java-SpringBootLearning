package com.example.SpringBootUserManagement.entity;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import com.example.SpringBootUserManagement.constants.Role;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true,length = 190)
    private String email;

    @Column(nullable = false)
    private String passwordHash;


    // @ElementCollection is colletion of basic types or embeddable types. It is used to define a collection of elements that are not entities but are still part of the entity's state. In this case, we are using it to define a collection of roles for the user.'
    // EAGER fetch type means that when we load a User entity, the associated roles will be loaded immediately along with it. This is useful when we know that we will always need the roles whenever we load a user, as it avoids the need for additional queries to fetch the roles later on.
    @ElementCollection(fetch = FetchType.EAGER)
    // @CollectionTable is used to specify the table that will be used to store the collection of roles. In this case, we are creating a separate table called "user_roles" that will have a foreign key relationship with the "users" table. The joinColumns attribute specifies the column in the "user_roles" table that will be used to join it with the "users" table, which is "user_id".
    @CollectionTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id")
    )
    // @Enumerated(EnumType.STRING) is used to specify that the Role enum should be stored as a string in the database. This means that when we save a User entity with a set of roles, the roles will be stored as their string representations (e.g., "USER", "ADMIN") in the "user_roles" table. This makes it easier to read and understand the roles when looking at the database directly.
    @Enumerated(EnumType.STRING)
    // The @Column annotation is used to specify the column name and constraints for the roles collection. In this case, we are naming the column "role" and setting it to be non-nullable, which means that every entry in the "user_roles" table must have a valid role associated with it.
    @Column(name = "role", nullable = false)
    private Set<Role> roles = new HashSet<>();


    @Column(nullable = false,updatable = false)
    private Instant createdAt = Instant.now();

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
    
}
