package ru.alexandrina.library.base;

import com.github.javafaker.Faker;
import ru.alexandrina.library.dto.AuthorDto;
import ru.alexandrina.library.dto.PublisherDto;
import ru.alexandrina.library.dto.BookRequestDto;

import ru.alexandrina.library.entity.Author;
import ru.alexandrina.library.entity.Book;
import ru.alexandrina.library.entity.Publisher;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class Generator {

    protected final Faker faker = new Faker();

    protected Author generateAuthor() {
        Author author = new Author();
        author.setName(faker.name().firstName());
        author.setSurname(faker.name().lastName());
        author.setBirthDate(LocalDate.ofInstant(faker.date().birthday().toInstant(), ZoneOffset.UTC));
        return author;
    }

    protected AuthorDto generateAuthorDto() {
        AuthorDto authorDto = new AuthorDto();
        authorDto.setName(faker.name().firstName());
        authorDto.setSurname(faker.name().lastName());
        authorDto.setBirthDate(LocalDate.ofInstant(faker.date().birthday().toInstant(), ZoneOffset.UTC));
        return authorDto;
    }

    protected Book generateBook() {
        Book book = new Book();

        book.setTitle(faker.book().title());
        book.setYear(LocalDate.ofInstant(faker.date().birthday().toInstant(), ZoneOffset.UTC).getYear());
        book.setPages(faker.random().nextInt(100, 300));

        int genres = faker.random().nextInt(1, 3);
        int authors = faker.random().nextInt(1, 2);
        book.setAuthors(
                Stream.generate(this::generateAuthor)
                        .limit(authors)
                        .collect(Collectors.toList())
        );
        book.setPublishers(
                Stream.generate(this::generatePublisher)
                        .limit(genres)
                        .collect(Collectors.toList())
        );
        return book;
    }

    protected BookRequestDto generateBookRequestDto() {
        BookRequestDto bookRequestDto = new BookRequestDto();

        bookRequestDto.setTitle(faker.book().title());
        bookRequestDto.setYear(LocalDate.ofInstant(faker.date().birthday().toInstant(), ZoneOffset.UTC).getYear());
        bookRequestDto.setPages(faker.random().nextInt(100, 300));
        return bookRequestDto;
    }

    protected Publisher generatePublisher() {
        Publisher publisher = new Publisher();

        publisher.setId(faker.random().nextLong(10));
        publisher.setTitle(faker.book().genre());
        return publisher;
    }

    protected PublisherDto generatePublisherDto() {
        PublisherDto publisherDto = new PublisherDto();

        publisherDto.setTitle(faker.book().publisher());
        return publisherDto;
    }
}
