package ru.alexandrina.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.alexandrina.library.entity.Author;
import ru.alexandrina.library.entity.Book;

import java.util.Collection;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long>,
        JpaSpecificationExecutor<Book> {
    List<Book> findByAuthor(Author author);
}
