package org.example.JavaConfig;

import org.example.Alien;
import org.example.Computer;
import org.example.Desktop;
import org.example.Laptop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.*;

// this annotation is for telling configuration
@Configuration
// it will scan all file of packages  , which class has Component annotation on it and will be managed by spring now (no need of configuration)
//@ComponentScan("org.example")
public class JavaConfig {

    // this below object will be managed by IOC Container
    // we are creating Desktop object here
    // now this bean can be accessed with below name as well as method name
//     @Bean(name = {"Beast","desktop","desk"})
    @Bean
    // scope changing to prototype to create more than one objects , default is singleton means only one object
    // @Scope("prototype")
    public Desktop desktop1(){
        return new Desktop();
    }

    // creating bean which taking argument
//    @Lazy  // ---> we create bean only when required
//        @Bean
//        @Primary
//        public Alien alienwithparam(){
//            return  new Alien(1231,1231);
//        }


    // creating bean of Alien which taking other bean as argument
//        @Bean
        // way 1
//        public Alien alien(@Qualifier("desktop") Desktop desktop){
//            return  new Alien(desktop);
//        }
        // way 2
//        public Alien alien(){
//            return  new Alien(desktop1());
//        }


    @Bean
    // passing type as parameter , means alien will depend on Computer type (loosely coupled) , then it will look for computer object
    // Autowired is optional , if we remove it , it will still work
    // Qualifier used to specify which object it will refer to (object name must match )
    public Alien alien(@Qualifier("desktop1") @Autowired Computer comp){
        // setting values of prototyp
        Alien obj = new Alien();
        obj.setAge(25);
        // setting computer obj
        // it will give object of desktop as its type is Computer (interface)
        obj.setCom(comp);
        return obj;
    }

    @Bean
//    @Primary // used this to give preference to use this object
    public Laptop laptop(){
        return new Laptop();
    }


}
