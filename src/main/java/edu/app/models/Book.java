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

    @Column(name = "title")
    @NotEmpty(message = "This field mustn't be empty")
    @Size(min = 2, max = 100, message = "Name size must be between 2 and 100 characters")
    private String title;

    @Column(name = "author")
    @NotEmpty(message = "This field mustn't be empty.")
    private String author;

    @Column(name = "year_of_published")
    private int yearOfPublished;

    @Column(name = "time_of_creation")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeOfCreation;

    @ManyToOne
    @JoinColumn(name = "person_id",referencedColumnName = "person_id")
    private Person owner;

    public Book() {
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public int getYearOfPublished() {
        return yearOfPublished;
    }

    public void setYearOfPublished(int yearOfPublished) {
        this.yearOfPublished = yearOfPublished;
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
        return id + ") " + title + ", "+author+", " + yearOfPublished;
    }
}