package com.johancastro.cun.edu.persistence;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "book", schema = "biblioteca")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long bookId;
    @Column(name = "isbn", nullable = false)
    private String isbn;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "author", nullable = false)
    private String author;
    @Column(name = "edition", nullable = false)
    private String edition;

}
