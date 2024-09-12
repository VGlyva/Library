package ru.alexandrina.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.alexandrina.library.entity.Book;
import ru.alexandrina.library.repository.BookRepository;

@Service
public class BookService {
    @Autowired
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * метод возвращает книгу по её Id
     *
     * @param id
     * @return Book
     */
    public Book findBook(int id) {
        return bookRepository.findById(id).get();
    }

    /**
     * метод добавляет в БД книгу
     *
     * @param book
     * @return book
     */

    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    /**
     * метод обновляет книгу в БД
     *
     * @param book
     * @return метод возвращает обновленного автора
     */

    public Book editBook(Book book) {
        return bookRepository.save(book);
    }

    /**
     * метод удаляет книгу из БД
     *
     * @param id
     * @return метод возвращает удалённую книгу
     */

    public ResponseEntity<?> removeBook(int id) {
        bookRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
