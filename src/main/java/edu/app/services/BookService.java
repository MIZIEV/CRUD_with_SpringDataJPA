package edu.app.services;

import edu.app.models.Book;
import edu.app.models.Person;
import edu.app.repositories.BookRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {

    private final BookRepositories bookRepositories;

    @Autowired
    public BookService(BookRepositories bookRepositories) {
        this.bookRepositories = bookRepositories;
    }

    @Transactional(readOnly = false)
    public void addNewBook(Book newBook) {
        newBook.setTimeOfCreation(new Date());
        bookRepositories.save(newBook);
    }

    public List<Book> getAllBooks() {
        return bookRepositories.findAll();
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
        bookRepositories.save(book);
    }

    @Transactional(readOnly = false)
    public void deleteOwner(int id) {
        Book book = bookRepositories.findById(id).get();
        book.setOwner(null);
        bookRepositories.save(book);
    }
}