package edu.app.services;

import edu.app.models.Book;
import edu.app.repositories.BookRepositories;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class BookService {

    private final BookRepositories bookRepositories;

    public BookService(BookRepositories bookRepositories) {
        this.bookRepositories = bookRepositories;
    }

    @Transactional(readOnly = false)
    public void addNewBook(Book newBook) {
        newBook.setTimeOfCreation(new Date());
        bookRepositories.save(newBook);
    }

    public List<Book> getAllBooks(){
        return bookRepositories.findAll();
    }
}