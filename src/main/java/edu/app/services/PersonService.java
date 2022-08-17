package edu.app.services;

import edu.app.models.Person;
import edu.app.repositories.PersonRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class PersonService {

    private final EntityManager entityManager;
    private final PersonRepositories personRepositories;

    @Autowired
    public PersonService(EntityManager entityManager, PersonRepositories personRepositories) {
        this.entityManager = entityManager;
        this.personRepositories = personRepositories;
    }

    @Transactional(readOnly = false)
    public void addNewPerson(Person newPerson) {
        newPerson.setTimeOfCreation(new Date());
        personRepositories.save(newPerson);
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
}