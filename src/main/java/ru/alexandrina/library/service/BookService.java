package ru.alexandrina.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.alexandrina.library.dto.BookRequestDto;
import ru.alexandrina.library.dto.BookResponseDto;
import ru.alexandrina.library.entity.Book;
import ru.alexandrina.library.exception.AuthorNotFoundException;
import ru.alexandrina.library.exception.BookNotFoundException;
import ru.alexandrina.library.exception.PublisherNotFoundException;
import ru.alexandrina.library.filter.BookFilter;
import ru.alexandrina.library.mapper.BookMapper;
import ru.alexandrina.library.repository.AuthorRepository;
import ru.alexandrina.library.repository.BookRepository;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import ru.alexandrina.library.repository.PublisherRepository;

import java.util.ArrayList;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final PublisherRepository publisherRepository;
    private final BookMapper bookMapper;

    @Autowired
    public BookService(BookRepository bookRepository, AuthorRepository authorRepository, PublisherRepository publisherRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.publisherRepository = publisherRepository;
        this.bookMapper = bookMapper;
    }

    @Transactional
    public BookResponseDto create(BookRequestDto bookRequestDto) {
        Book book = bookMapper.toEntity(bookRequestDto);

        fillAuthorsAndPublishers(book, bookRequestDto);
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Transactional
    public BookResponseDto update(Long id, BookRequestDto bookRequestDto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        bookMapper.fillEntity(bookRequestDto, book);
        fillAuthorsAndPublishers(book, bookRequestDto);

        return bookMapper.toDto(bookRepository.save(book));
    }

    @Transactional(readOnly = true)
    public BookResponseDto get(Long id) {
        return bookMapper.toDto(
                bookRepository.findById(id)
                        .orElseThrow(() -> new BookNotFoundException(id))
        );
    }


    @Transactional
    public BookResponseDto delete(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        return bookMapper.toDto(book);
    }


    @Transactional(readOnly = true)
    public List<BookResponseDto> list(BookFilter bookFilter) {
        Specification<Book> specification = Specification.where((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (bookFilter.getTitle() != null) {
                predicates.add(criteriaBuilder.like(root.get("title"), "%" + bookFilter.getTitle() + "%"));
            }
            if (bookFilter.getYear() != null) {
                predicates.add(criteriaBuilder.equal(root.get("year"), bookFilter.getYear()));
            }
            if (bookFilter.getPagesFrom() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("pages"), bookFilter.getPagesFrom()));
            }
            if (bookFilter.getPagesTo() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("pages"), bookFilter.getPagesTo()));
            }
            if (bookFilter.getAuthor() != null) {
                predicates.add(root.get("authors").get("name").in(bookFilter.getAuthor()));
            }
            if (bookFilter.getPublisher() != null) {
                predicates.add(root.get("publishers").get("title").in(bookFilter.getPublisher()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
        });
        return bookRepository.findAll(specification).stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    private void fillAuthorsAndPublishers(Book book, BookRequestDto bookRequestDto) {
        book.setAuthors(
                bookRequestDto.getAuthorsIds().stream()
                        .map(authorId -> authorRepository.findById(authorId)
                                .orElseThrow(() -> new AuthorNotFoundException(authorId))
                        )
                        .collect(Collectors.toList())
        );
        book.setPublishers(
                bookRequestDto.getPublishersIds().stream()
                        .map(genreId -> publisherRepository.findById(genreId)
                                .orElseThrow(() -> new PublisherNotFoundException(genreId))
                        )
                        .collect(Collectors.toList())
        );
    }
}
