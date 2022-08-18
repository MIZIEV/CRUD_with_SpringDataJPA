package edu.app.repositories;

import edu.app.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepositories extends JpaRepository<Book, Integer> {
}