package edu.app.services;

import edu.app.models.Book;
import edu.app.models.Person;
import edu.app.repositories.BookRepositories;
import edu.app.repositories.PersonRepositories;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {

    private final BookRepositories bookRepositories;
    private final PersonRepositories personRepositories;

    @Autowired
    public BookService(BookRepositories bookRepositories, PersonRepositories personRepositories) {
        this.bookRepositories = bookRepositories;
        this.personRepositories = personRepositories;
    }

    @Transactional(readOnly = false)
    public void addNewBook(Book newBook) {
        newBook.setTimeOfCreation(new Date());
        bookRepositories.save(newBook);
    }

    public List<Book> getAllBooks(boolean sort) {

        if (sort) {
            return bookRepositories.findAll(Sort.by("yearOfPublished"));
        } else {
            return bookRepositories.findAll();
        }
    }

    public List<Book> findWithPagination(Integer page, Integer booksPerPage, boolean sort) {
        if (sort) {
            return bookRepositories.findAll(PageRequest.of(page, booksPerPage, Sort.by("yearOfPublished"))).getContent();
        } else {
            return bookRepositories.findAll(PageRequest.of(page, booksPerPage)).getContent();
        }
    }

    public List<Book> getBooksByPersonId(int id) {
        Optional<Person> person = personRepositories.findById(id);

        if (person.isPresent()) {
            Hibernate.initialize(person.get().getBookList());

            person.get().getBookList().forEach(book -> {
                long diffInMiles = Math.abs(book.getTimeOfCreation().getTime() - new Date().getTime());
                if (diffInMiles > 864000000) {
                    book.setExpired(true);
                }
            });
            return person.get().getBookList();
        } else {
            return Collections.emptyList();
        }
    }

    public List<Book> searchByTitle(String title){
        return bookRepositories.findByTitleStartingWith(title);
    }
    public Book getConcreteBook(int id) {
        return bookRepositories.getById(id);
    }

    @Transactional(readOnly = false)
    public void deleteConcreteBook(int id) {
        bookRepositories.deleteById(id);
    }

    @Transactional(readOnly = false)
    public void updateBook(Book updatedBook) {
        bookRepositories.save(updatedBook);
    }

    public Person getBookOwner(int id) {
        return bookRepositories.findById(id).map(Book::getOwner).orElse(null);
    }

    @Transactional(readOnly = false)
    public void setNewOwner(int id, Person newOwner) {
        Book book = bookRepositories.findById(id).get();
        book.setOwner(newOwner);
        book.setTimeOfCreation(null);
        bookRepositories.save(book);
    }

    @Transactional(readOnly = false)
    public void deleteOwner(int id) {
        Book book = bookRepositories.findById(id).get();
        book.setOwner(null);
        book.setTimeOfCreation(null);
        bookRepositories.save(book);
    }
}