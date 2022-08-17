package edu.app.repositories;

import edu.app.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepositories extends JpaRepository<Person,Integer> {
}