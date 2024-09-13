package ru.alexandrina.library.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 32)
    private String title;

    @Column(name = "year")
    private Integer year;

    private Integer pages;

    @ManyToMany
    @JoinTable(
            name = "authors_to_books",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private Collection<Author> authors;

    @ManyToMany
    @JoinTable(
            name = "publishers_to_books",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "publisher_id")
    )
    private Collection<Publisher> publishers;
}
