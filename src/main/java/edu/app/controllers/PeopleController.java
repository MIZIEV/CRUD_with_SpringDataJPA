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
    public String getConcretePersonPage(@PathVariable("id") int id,Model model){
        model.addAttribute("person",personService.getConcretePerson(id));
        return "people/concretePerson";
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
