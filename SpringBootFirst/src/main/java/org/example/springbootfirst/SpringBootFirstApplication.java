package org.example.springbootfirst;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringBootFirstApplication {

    public static void main(String[] args) {

        // getting the application context (run method is returning Application Context) , to get access to IOC Container
        ApplicationContext context =  SpringApplication.run(SpringBootFirstApplication.class, args);
        // we are accessing the bean from ioc container , so student class object will get injected
        Student s1 = context.getBean(Student.class);
        s1.code();
    }

}
