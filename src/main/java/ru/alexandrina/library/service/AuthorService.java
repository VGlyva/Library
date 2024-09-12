package ru.alexandrina.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.alexandrina.library.entity.Author;
import ru.alexandrina.library.repository.AuthorRepository;

@Service
public class AuthorService {
    @Autowired
    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    /**
     * метод возвращает автора по его Id
     *
     * @param id
     * @return Author
     */

    public Author findAuthor(int id) {
        return authorRepository.findById(id).get();
    }

    /**
     * метод добавляет в БД автора
     *
     * @param author
     * @return метод возвращает добавленного автора
     */

    public Author addAuthor(Author author) {
        return authorRepository.save(author);
    }

    /**
     * метод обновляет автора в БД
     *
     * @param author
     * @return метод возвращает обновленного автора
     */

    public Author editAuthor(Author author) {
        return authorRepository.save(author);
    }

    /**
     * метод удаляет автора из БД
     *
     * @param id
     * @return метод возвращает удалённого автора
     */

    public ResponseEntity<?> removeAuthor(int id) {
        authorRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
