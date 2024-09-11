package com.johancastro.cun.edu.persistence;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface IBookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByIsbn(String isbn);
    @Transactional
    @Modifying
    int deleteByIsbn(String isbn);

}
