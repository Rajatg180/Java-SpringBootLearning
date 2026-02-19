package org.example;
import org.example.JavaConfig.JavaConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

//        *************************** BELOW CODE IF JAVA BASED CONFIGURATION ********************************
        ApplicationContext context = new AnnotationConfigApplicationContext(JavaConfig.class);

        // accessing the desktop bean
//        Desktop dt =  context.getBean(Desktop.class);
//        dt.compile();

        // getting bean using name ( so by default bean name is method name) , if we don't specify name in Bean annotation
//        Desktop dt1 = context.getBean("desktop", Desktop.class);
//        dt1.compile();
//        Desktop dt2 = context.getBean("desktop1", Desktop.class);
//        dt2.compile();


        Alien alien = context.getBean(Alien.class);
        System.out.println( alien.getAge());
        alien.code();











//         *********************************** BELOW CODE IF FOR XML BASED CONFIGURATION ************************************
//        // this will help us to work with IOC container
//        // we created the spring ioc container and the configuration to manage the beans is written in spring.xml
//        // and this will create all the objects mentioned in spring.xml file
//        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
//        // with help of this context we can ask for bean
//        // getBean will return the Object as return type , but we want Alien as type so we are typecasting this
//        /*
//        * SINGLETON SCOPE
//        * - even we are creating two different object reference , still it will go for same bean as id is same (singleton scope)
//        * - when scope is singleton (default scope) while creating bean , the object is created while loading spring.xml
//        *
//        * PROTOTYPE SCOPE
//        * - it will create two different object when we called getBean , as scope is prototype
//        * - it will create object in container only when we called getBean
//        * */
//        // now it will creat two different object as scope = prototype
//        Alien alien1 = (Alien) context.getBean("alien");
//        alien1.code();
//        System.out.println(alien1.getAge());
//        System.out.println(alien1.getSalary());
//
//        // when we will use this then only it will create the object of Desktop in IOC Container , as it is lazy-init
//        // object is created when we want it but still its singleton scope
//        // no need to typecase when we add type which we want
//        // Desktop desktop =context.getBean("com",Desktop.class);
//        // desktop.compile();
//
//        // accessing bean without id
//        Desktop desktop =context.getBean("com",Desktop.class);
//        desktop.compile();
//
//        // here we have two beans of Type Computer, but it will take , Laptop object because , it is mention primary
//        Computer computer = context.getBean(Computer.class);
//        System.out.println("Computer class : ");
//        computer.compile();


    }
}
