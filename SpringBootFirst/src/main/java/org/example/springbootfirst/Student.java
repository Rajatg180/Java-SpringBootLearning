package org.example.springbootfirst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// this annotation will create the Student class Object in IOC Container , which will then injected when we required it
@Component
public class Student {

    // we dont have access to IOC Container here we are using this annotation
    // AutoWired is used to give access to bean in Container(automatically inject dependencies)
    @Autowired
    Laptop laptop;

    public void code(){
        laptop.greet();
    }
}
