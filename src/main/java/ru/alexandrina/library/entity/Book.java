package ru.alexandrina.library.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;




@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@ToString
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue
    private int id;

    @Column
    private String name;

    @ManyToOne
    @JoinColumn(name = "author_id")
    @JsonIgnore
    private Author author;

    @ManyToOne
    @JoinColumn(name = "publishing_id")
    @JsonIgnore
    private Publishing publishing;
}
