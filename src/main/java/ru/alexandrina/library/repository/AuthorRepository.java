package ru.alexandrina.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.alexandrina.library.entity.Author;

public interface AuthorRepository extends JpaRepository<Author, Integer> {

}
