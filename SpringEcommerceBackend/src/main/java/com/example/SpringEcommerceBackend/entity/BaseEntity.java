package com.example.SpringEcommerceBackend.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
// @MappedSuperclass annotation is used to indicate that this class is a base class for other entities 
// and its properties will be inherited by the subclasses. It is not an entity itself and will not be mapped to a database table.
@MappedSuperclass 
public class BaseEntity {
    @Id
    @GeneratedValue
    private UUID id;

    // this time stamp will automatically set the value ,when entity is created
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
}


