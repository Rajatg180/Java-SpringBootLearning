//package org.example.hibernatecourse;
//
//import jakarta.persistence.Embeddable;
//
//// this annotation is used to embedded this properties in table by which it is used as type
//@Embeddable
//public class Laptop {
//    private String model;
//    private String brand;
//    private  int ram;
//
//
//    public String getModel() {
//        return model;
//    }
//
//    public void setModel(String model) {
//        this.model = model;
//    }
//
//    public int getRam() {
//        return ram;
//    }
//
//    public void setRam(int ram) {
//        this.ram = ram;
//    }
//
//    public String getBrand() {
//        return brand;
//    }
//
//
//
//    public void setBrand(String brand) {
//        this.brand = brand;
//    }
//
//    @Override
//    public String toString() {
//        return "Laptop{" +
//                "model='" + model + '\'' +
//                ", brand='" + brand + '\'' +
//                ", ram=" + ram +
//                '}';
//    }
//}


package org.example.hibernatecourse;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.util.List;

@Entity
public class Laptop {

    @Id
    private  int id;

    @Override
    public String toString() {
        return "Laptop{" +
                "id=" + id +
                ", model='" + model + '\'' +
                ", brand='" + brand + '\'' +
                ", ram=" + ram +
                '}';
    }

    private String model;
    private String brand;
    private  int ram;
    //    @ManyToOne
    //    private  Student student;

    // Many to many
    @ManyToOne
    private List<Student> student;


    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<Student> getStudent() {
        return student;
    }

    public void setStudent(List<Student> student) {
        this.student = student;
    }

    public int getRam() {
        return ram;
    }

    public void setRam(int ram) {
        this.ram = ram;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
