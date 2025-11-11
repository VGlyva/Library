package ru.alexandrina.library.service;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.alexandrina.library.entity.Author;
import ru.alexandrina.library.entity.Book;
import ru.alexandrina.library.entity.Publisher;
import ru.alexandrina.library.repository.AuthorRepository;
import ru.alexandrina.library.repository.BookRepository;
import ru.alexandrina.library.repository.PublisherRepository;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class GenerateService {

    private final Faker faker = new Faker();
    private final AuthorRepository authorRepository;
    private final PublisherRepository publisherRepository;
    private final BookRepository bookRepository;

    @Autowired
    public GenerateService(AuthorRepository authorRepository, PublisherRepository publisherRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.publisherRepository = publisherRepository;
        this.bookRepository = bookRepository;
    }

    public void generateAuthors(int count) {
        Stream.generate(this::generateAuthor)
                .limit(count)
                .forEach(authorRepository::save);
    }

    public void generateBooks(int count) {
        Stream.generate(this::generateBook)
                .limit(count)
                .forEach(bookRepository::save);
    }

    public void generatePublishers(int count) {
        Stream.generate(this::generatePublisher)
                .limit(count)
                .forEach(publisherRepository::save);
    }

    public Author generateAuthor() {
        Author author = new Author();
        author.setName(faker.name().firstName());
        author.setSurname(faker.name().lastName());
        author.setBirthDate(LocalDate.ofInstant(faker.date().birthday().toInstant(), ZoneOffset.UTC));
        return author;
    }

    public Book generateBook() {
        Book book = new Book();
        book.setTitle(faker.book().genre());
        book.setYear(LocalDate.ofInstant(faker.date().birthday().toInstant(), ZoneOffset.UTC).getYear());
        book.setPages(faker.number().numberBetween(100, 500));


        int generateAuthors = faker.random().nextInt(1, 3);
        int generatePublishers = faker.random().nextInt(1, 4);

        book.setAuthors(
                Stream.generate(() ->
                                randomAuthor((int) authorRepository.count())
                        )
                        .limit(generateAuthors)
                        .distinct()
                        .collect(Collectors.toList())
        );

        book.setPublishers(
                Stream.generate(() ->
                                randomPublisher((int) publisherRepository.count())
                        )
                        .limit(generatePublishers)
                        .distinct()
                        .collect(Collectors.toList())
        );
        return book;
    }

    public Publisher generatePublisher() {
        Publisher publisher = new Publisher();
        publisher.setTitle(faker.book().publisher());
        return publisher;
    }

    private Author randomAuthor(int totalAuthors) {
        return authorRepository.findAll(PageRequest.of(faker.random().nextInt(totalAuthors), 1))
                .getContent().get(0);
    }

    private Publisher randomPublisher(int totalPublishers) {
        return publisherRepository.findAll(PageRequest.of(faker.random().nextInt(totalPublishers), 1))
                .getContent().get(0);
    }
}
