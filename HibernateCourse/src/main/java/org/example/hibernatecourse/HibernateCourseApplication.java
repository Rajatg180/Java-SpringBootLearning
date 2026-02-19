package org.example.hibernatecourse;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

public class HibernateCourseApplication {
    public static void main(String[] args) {
        Student s1 = new Student();
        s1.setName("Raja");
        s1.setAge(25);

        Student s2 = new Student();
        s2.setName("Ashwani");
        s1.setAge(21);

        Laptop l1 = new Laptop();
        Laptop l2 = new Laptop();
        l2.setBrand("Deel");
        l2.setModel("WER");
        l2.setRam(12);
        l2.setId(2);
        l1.setBrand("Lenovo");
        l1.setModel("Gaming");
        l1.setRam(8);
        l1.setId(1);
        Student s3 = new Student();
        s3.setName("ZStudent");
        s3.setAge(12);
        s3.setLaptop(List.of(l1,l2));
        l2.setStudent(List.of(a1,a2,a3));
        l1.setStudent(s3);

        // configuration
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        cfg.addAnnotatedClass(Student.class);
        cfg.addAnnotatedClass(Laptop.class);
        SessionFactory factory = cfg.buildSessionFactory();
        Session session = factory.openSession();

        // save data with to db with embedded types (Laptop)
         Transaction t = session.beginTransaction();
         session.persist(l1);
         session.persist(l2);
         session.persist(s3);
         t.commit();


        // save data to database
        //        session.beginTransaction();
        //        session.persist(s1);
        //        session.persist(s2);
        //        session.getTransaction().commit();


        // get all data using HQL
        // Hibernate query language -> HQL
        // HIBERNATE convert hql to sql internally
        //        List<Student> students = session.createQuery("from Student ",Student.class).list();
        //        for(Student s : students){
        //            System.out.println(s.getName());
        //        }

        // getting data using primary key
        //        Student s = session.find(Student.class,1);
        //        System.out.println(s.getName());

        // update data
        //        Transaction t = session.beginTransaction();
        //        Student su = session.find(Student.class,2);
        //        su.setName("Yashwant updated");
        //        t.commit();

        // update using merge()
        //        Student student = new Student();
        //        student.setRollNumber(1);
        //        student.setName("Updated Name");
        //        student.setAge(26);
        //        Transaction t = session.beginTransaction();
        //        session.merge(student);
        //        t.commit();


        // delete
        //        Transaction t = session.beginTransaction();
        //        Student ds = session.find(Student.class,1);
        //        session.remove(ds);
        //        t.commit();

        session.close();
        factory.close();
    }
}
