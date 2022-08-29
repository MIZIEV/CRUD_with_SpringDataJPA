package edu.app.services;

import edu.app.models.Book;
import edu.app.models.Person;
import edu.app.repositories.PersonRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PersonService {

    private final PersonRepositories personRepositories;

    @Autowired
    public PersonService(PersonRepositories personRepositories) {
        this.personRepositories = personRepositories;
    }

    @Transactional(readOnly = false)
    public void addNewPerson(Person newPerson) {
        newPerson.setTimeOfCreation(new Date());
        personRepositories.save(newPerson);
    }

    public Optional<Person> getPersonByFullName(String fullName){
        return personRepositories.findByFullName(fullName);
    }

    public List<Person> getAllPeople(){
        return personRepositories.findAll();
    }

    public Person getConcretePerson(int id){
        return personRepositories.getById(id);
    }

    @Transactional(readOnly = false)
    public void deleteConcretePerson(int id){
        personRepositories.deleteById(id);
    }

    @Transactional(readOnly = false)
    public Person updatePerson(Person updatedPerson){
        return personRepositories.save(updatedPerson);
    }

    public List<Book> getAllBook(int id){
        return personRepositories.findById(id).map(Person::getBookList).orElse(null);
    }
}