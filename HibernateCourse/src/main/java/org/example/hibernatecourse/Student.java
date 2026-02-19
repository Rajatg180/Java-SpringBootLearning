package org.example.hibernatecourse;
import jakarta.persistence.*;

import java.util.List;

@Entity
// changing entity name
//@Entity(name = "student_entity")
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rollNumber;

    // change colum name
    // @Column(name = "name_student")
    private String name;
    // if we don't want to store any column in database use following annotation
    // @Transient
    private int age;
    //  @OneToOne
    //  private  Laptop laptop;

    // now this is mapped by student from laptop
    // if we don't write mappedBy it will create a new table
    //    @OneToMany(mappedBy = "student")
    //    private List<Laptop> laptop;

    // many to many
    @ManyToOne
    private Laptop laptop;

    public int getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(int rollNumber) {
        this.rollNumber = rollNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    @Override
    public String toString() {
        return "Student{" +
                "rollNumber=" + rollNumber +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", laptop=" + laptop +
                '}';
    }
}
