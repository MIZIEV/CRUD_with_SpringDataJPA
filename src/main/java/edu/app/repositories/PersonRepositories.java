package edu.app.repositories;

import edu.app.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepositories extends JpaRepository<Person,Integer> {
    Optional<Person> findByFullName(String fullName);
}