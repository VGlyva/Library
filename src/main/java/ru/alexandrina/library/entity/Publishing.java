package ru.alexandrina.library.entity;


import jakarta.persistence.*;
import lombok.*;


import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@ToString
@Table(name = "publishing")
public class Publishing {

    @Id
    @GeneratedValue
    private int id;

    @Column
    private String name;

    @OneToMany(mappedBy = "publishing")
    private List<Book> books;

    @OneToMany(mappedBy = "publishing")
    private List<Book> booksPublic;
}
