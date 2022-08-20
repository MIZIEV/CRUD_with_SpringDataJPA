package edu.app.controllers;

import edu.app.models.Person;
import edu.app.services.PersonService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonService personService;

    public PeopleController(PersonService personService) {
        this.personService = personService;
    }


    @GetMapping("/")
    public String getPeopleMainPage(Model model) {
        model.addAttribute("people", personService.getAllPeople());
        return "people/peopleMainPage";
    }

    @GetMapping("/{id}")
    public String getConcretePersonPage(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personService.getConcretePerson(id));
        model.addAttribute("books", personService.getAllBook(id));
        return "people/concretePerson";
    }

    @GetMapping("/{id}/edit")
    public String getEditPage(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personService.getConcretePerson(id));
        return "people/editPerson";
    }

    @PatchMapping("/{id}")
    public String updatePerson(@PathVariable("id") int id, @ModelAttribute("person") @Valid Person person,
                               Model model, BindingResult error) {

        if (error.hasErrors()) {
            return "people/editPerson";
        }
        model.addAttribute("person", personService.updatePerson(person));
        return "redirect:/people/";
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id") int id) {
        personService.deleteConcretePerson(id);
        return "redirect:/people/";
    }

    @GetMapping("/new")
    public String getNewPersonPage(Model model) {
        model.addAttribute("person", new Person());
        return "people/newPerson";
    }

    @PostMapping
    public String annNewPerson(@ModelAttribute("person") @Valid Person person, BindingResult errors) {
        if (errors.hasErrors()) {
            return "people/newPerson";
        }
        personService.addNewPerson(person);
        return "redirect:/people/";
    }
}