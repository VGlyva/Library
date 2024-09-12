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
@Table(name = "author")
public class Author {

    @Id
    @GeneratedValue
    private int id;

    @Column
    private String name;

    @Column
    private int age;

    @OneToMany(mappedBy = "author")
    private List<Book> books;

}
