package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.beans.ConstructorProperties;

//@Component
public class Alien {

    @Value("40")
    private int age;
    private  int salary;


    // injection can be done in 3 ways
    // 1) constructor injection , 2) field injection , 3) setter injection

    // field injection
//    @Autowired
    // now it has two object of types Computer (i.e Laptop and Desktop which to pick ?)
    // so we used Qualifier annotation and the bean name will be class name just first letter in small (Laptop = laptop)
    //we are using lap here because we have given name to it , we didnt give name the default will be class name
//    @Qualifier("lap")
//        @Qualifier("desktop")
    private  Computer com;


    // Constructor injection
//    @Autowired ---> no need to do configuration in JavaConfig
    public Alien(Computer comp){
        System.out.println("Alien object created using comp dependency injection");
        this.com = comp;
    }


    public Alien(){
        System.out.println("Alien Object created with default param ");
    }

    // if alien constructor is taking paramter
    public Alien(int age,int salary){
        System.out.println("Alien object create using aga and salary paramter");
        this.age = age;
        this.salary = salary;
    }


    // will run After bean is created and dependencies injected
//    @PostConstruct
//    public void init() {
//        System.out.println("PostConstruct method called");
//    }

    // will run before Just before bean is destroyed
//    @PreDestroy
//    public void destroy() {
//        System.out.println("PreDestroy method called");
//    }

    // used to specify that a class's properties should be populated via its constructor arguments rather than using traditional setter methods
    @ConstructorProperties({"age","laptop","salary"})
    public Alien(int age,Computer com,int salary){
        System.out.println("Value is assigned using constructor");
        this.age = age;
        this.com = com;
        this.salary = salary;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        System.out.println("Value is set to age using setter ");
        this.age = age;
    }


    public Computer getCom() {
        return com;
    }

    // setter injection
    //    @Autowired
    //    @Qualifier("lap")
    public void setCom(Computer com) {
        System.out.println("Value is set to com using setter ");
        this.com = com;
    }


    public void code(){
        System.out.println(salary);
        com.compile();
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }
}
