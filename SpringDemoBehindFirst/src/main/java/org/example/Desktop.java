package org.example;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

// this annotation will tell this class that its objects are manged by spring ioc container
// now we don't need to do any configuration now
@Component
// primary used to give preference when there are multiple beans of same type
@Primary
@Scope("prototype")
public class Desktop implements  Computer{

    public  Desktop(){
        System.out.println("Desktop objected created");
    }
    @Override
    public void compile(){
        System.out.println("Compiling using Desktop");
    }
}
