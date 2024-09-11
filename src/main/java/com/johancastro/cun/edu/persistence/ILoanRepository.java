package com.johancastro.cun.edu.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ILoanRepository extends JpaRepository<Loan, Long> {

    @Query(value = "SELECT * FROM biblioteca.loans WHERE book_id = ?1 AND return_date is null", nativeQuery = true)
    Optional<Loan> findByBookId(long bookId);
}
