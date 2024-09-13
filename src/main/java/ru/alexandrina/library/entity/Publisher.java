package ru.alexandrina.library.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "publisher")
public class Publisher {

    @Id
    @GeneratedValue
    private Long id;


    @Column(nullable = false, length = 32)
    private String title;

    @ManyToMany
    @JoinTable(
            name = "publishers_to_books",
            joinColumns = @JoinColumn(name = "publisher_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private Collection<Author> authors;
}
