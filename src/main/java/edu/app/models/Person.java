package edu.app.models;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Person")
public class Person {

    @Id
    @Column(name = "person_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "full_name")
    @NotEmpty(message = "Full name field couldn't be empty")
    @Size(min = 2, max = 100, message = "Full name size must be between 2 and 100 characters")
    private String fullName;

    @Column(name = "age")
    @Min(value = 0,message = "Age mustn't be smaller than 0" )
    private int age;

    @Column(name = "time_of_creation")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeOfCreation;


    @OneToMany(mappedBy = "owner")
    private List<Book> bookList;

    public Person() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getTimeOfCreation() {
        return timeOfCreation;
    }

    public void setTimeOfCreation(Date timeOfCreation) {
        this.timeOfCreation = timeOfCreation;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    @Override
    public String toString() {
        return id + ") " + fullName + ", " + age + ", " + timeOfCreation;
    }
}