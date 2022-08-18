package edu.app.controllers;

import edu.app.models.Book;
import edu.app.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BookService bookService;

    @Autowired
    public BooksController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/")
    public String getBooksMainPage(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "books/booksMainPage";
    }

    @GetMapping("/{id}")
    public String getConcretePage(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookService.getConcreteBook(id));
        return "books/concreteBook";
    }

    @DeleteMapping("/{id}")
    public String deleteConcreteBook(@PathVariable("id") int id) {
        bookService.deleteConcreteBook(id);
        return "redirect:/books/";
    }

    @GetMapping("/new")
    public String getNewBookPage(Model model) {
        model.addAttribute("book", new Book());
        return "books/newBook";
    }

    @PutMapping
    public String addNewBook(@ModelAttribute("book") @Valid Book book, BindingResult error) {
        if (error.hasErrors()) {
            return "books/newBook";
        }
        bookService.addNewBook(book);
        return "redirect:/books/";
    }
}
