package edu.app.controllers;

import edu.app.models.Book;
import edu.app.models.Person;
import edu.app.services.BookService;
import edu.app.services.PersonService;
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
    private final PersonService personService;

    @Autowired
    public BooksController(BookService bookService, PersonService personService) {
        this.bookService = bookService;
        this.personService = personService;
    }

    @GetMapping("/")
    public String getBooksMainPage(Model model,
                                   @RequestParam(value = "page", required = false) Integer page,
                                   @RequestParam(value = "book_per_page", required = false) Integer bookPerPage,
                                   @RequestParam(value = "sort_by_year", required = false) boolean sortByYear) {

        if (page == null || bookPerPage == null) {
            model.addAttribute("books", bookService.getAllBooks(sortByYear));
        } else {
            model.addAttribute("books", bookService.findWithPagination(page, bookPerPage, sortByYear));
        }
        return "books/booksMainPage";
    }

    @GetMapping("/{id}")
    public String getConcretePage(@PathVariable("id") int id, @ModelAttribute("person") Person person,
                                  Model model) {
        model.addAttribute("book", bookService.getConcreteBook(id));

        Person owner = bookService.getBookOwner(id);

        if (owner != null) {
            model.addAttribute("owner", owner);
        } else {
            model.addAttribute("people", personService.getAllPeople());
        }

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

    @GetMapping("/{id}/edit")
    public String getEditBookPage(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookService.getConcreteBook(id));
        return "books/editBook";
    }

    @PatchMapping("/{id}")
    public String updateBook(@PathVariable("id") int id,
                             @ModelAttribute("book") @Valid Book book, BindingResult error) {
        if (error.hasErrors()) {
            return "books/editBook";
        }
        bookService.updateBook(book);
        return "redirect:/books/";
    }

    @GetMapping("/search")
    public String getSearchPage() {
        return "/books/search";
    }

    @PostMapping("/search")
    public String makeSearchBooks(Model model, @RequestParam("query") String query) {
        model.addAttribute("books", bookService.searchByTitle(query));
        System.out.println("result is ....." + model.toString());
        return "/books/search";
    }

    @PatchMapping("/{id}/addOwner")
    public String addOwner(@PathVariable("id") int id, @ModelAttribute("person") Person selectedPerson) {
        bookService.setNewOwner(id, selectedPerson);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/deleteOwner")
    public String deleteOwner(@PathVariable("id") int id) {
        bookService.deleteOwner(id);
        return "redirect:/books/" + id;
    }
}