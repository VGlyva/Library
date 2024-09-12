package ru.alexandrina.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.alexandrina.library.entity.Book;

public interface BookRepository extends JpaRepository<Book, Integer> {
}
