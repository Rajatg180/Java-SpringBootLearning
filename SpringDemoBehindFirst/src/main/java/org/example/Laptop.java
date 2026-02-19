package org.example;

import org.springframework.stereotype.Component;

// giving different name to access this bean
@Component("lap")
public class Laptop implements Computer {

    public Laptop(){
        System.out.println("Laptop object created");
    }

    @Override
    public void compile(){
        System.out.println("Compiling using Laptop");
    }
}
