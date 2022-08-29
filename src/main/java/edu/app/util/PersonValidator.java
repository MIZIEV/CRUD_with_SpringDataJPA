package edu.app.util;

import edu.app.models.Person;
import edu.app.services.PersonService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {

    private final PersonService personService;

    public PersonValidator(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;

        if(personService.getPersonByFullName(person.getFullName()).isPresent()){
            errors.rejectValue("fullName","","Person with this full name exists!!!");
        }
    }
}