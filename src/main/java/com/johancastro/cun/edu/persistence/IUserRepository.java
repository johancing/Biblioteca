package com.johancastro.cun.edu.persistence;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {

    Optional<User> findByDocumentNumber(String documentNumber);
    @Transactional
    @Modifying
    int deleteByDocumentNumber(String documentNumber);

}
