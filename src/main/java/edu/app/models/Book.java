package edu.app.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "Book")
public class Book {

    @Id
    @Column(name = "book_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "book_name")
    @NotEmpty(message = "This field mustn't be empty")
    @Size(min = 2, max = 100, message = "Name size must be between 2 and 100 characters")
    private String name;

    @Column(name = "date_of_published")
    @Temporal(TemporalType.DATE)
    private Date dateOfPublishing;

    @Column(name = "time_of_creation")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeOfCreation;

    @ManyToOne
    @JoinColumn(name = "person_id",referencedColumnName = "person_id")
    private Person owner;

    public Book() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateOfPublishing() {
        return dateOfPublishing;
    }

    public void setDateOfPublishing(Date dateOfPublishing) {
        this.dateOfPublishing = dateOfPublishing;
    }

    public Date getTimeOfCreation() {
        return timeOfCreation;
    }

    public void setTimeOfCreation(Date timeOfCreation) {
        this.timeOfCreation = timeOfCreation;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return id + ") " + name + ", " + dateOfPublishing;
    }
}