package com.johancastro.cun.edu.service;

import com.johancastro.cun.edu.model.Prestamo;
import com.johancastro.cun.edu.persistence.*;
import com.johancastro.cun.edu.util.GeneralMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class LoanService implements IService<Prestamo, String> {

    private final IUserRepository userRepository;
    private final IBookRepository bookRepository;
    private final ILoanRepository loanRepository;

    public LoanService(IUserRepository userRepository, IBookRepository bookRepository, ILoanRepository loanRepository) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.loanRepository = loanRepository;
    }

    @Override
    public Optional<Prestamo> crear(Prestamo prestamo) {
        Optional<User> user = userRepository.findByDocumentNumber(prestamo.getUsuario().getNumeroDocumento());
        if (user.isEmpty()) {
            prestamo.setInformacion("El usuario no existe");
            return Optional.of(prestamo);
        }
        Optional<Book> book = bookRepository.findByIsbn(prestamo.getLibro().getIsbn());
        if (book.isEmpty()) {
            prestamo.setInformacion("Libro no registrado en la biblioteca.");
            return Optional.of(prestamo);
        }
        Optional<Loan> loan = loanRepository.findByBookId(book.get().getBookId());
        if (loan.isPresent()) {
            prestamo.setInformacion("El libro ya se encuentra en prestamo. Fecha de reintegro " + loan.get().getEstimatedDateReturn());
            return Optional.of(prestamo);
        }
        Loan active = loanRepository.save(GeneralMapper.prestamoToLoan(prestamo, user.get(), book.get()));
        prestamo.setInformacion("Prestamo creado correctamente. Fecha de reintegro " + prestamo.getFechaEstimadaReintegro());
        return Optional.of(prestamo);
    }

    @Override
    public Optional<Prestamo> actualizar(Prestamo prestamo) {
        Optional<Book> book = bookRepository.findByIsbn(prestamo.getLibro().getIsbn());
        if (book.isEmpty()) {
            prestamo.setInformacion("Libro no registrado en la biblioteca.");
            return Optional.of(prestamo);
        }
        Optional<Loan> loan = loanRepository.findByBookId(book.get().getBookId());
        if (loan.isEmpty()) {
            prestamo.setInformacion("Libro no registrado como prestamo.");
            return Optional.of(prestamo);
        }
        loan.get().setReturnDate(new Date());
        Loan active = loanRepository.save(loan.get());
        prestamo = GeneralMapper.loanToPrestamo(active);
        prestamo.setInformacion("Libro regresado de forma exitosa.");
        return Optional.of(prestamo);
    }

    @Override
    public Optional<Prestamo> buscar(String isbn) {
        Prestamo prestamo = new Prestamo();
        Optional<Book> book = bookRepository.findByIsbn(isbn);
        if (book.isEmpty()) {
            prestamo.setInformacion("Libro no registrado en la biblioteca.");
            return Optional.of(prestamo);
        }
        Optional<Loan> loan = loanRepository.findByBookId(book.get().getBookId());
        if (loan.isEmpty()) {
            prestamo.setInformacion("Libro no registrado como prestamo.");
            return Optional.of(prestamo);
        }
        prestamo.setInformacion(GeneralMapper.loanToPrestamo(loan.get()).toString());
        return Optional.of(prestamo);
    }

    @Override
    public Iterable<Prestamo> listar() {
        List<Loan> loans = loanRepository.findAll();
        List<Prestamo> prestamos = new ArrayList<>();
        loans.forEach(l -> prestamos.add(GeneralMapper.loanToPrestamo(l)));
        return prestamos;
    }

    @Override
    public boolean eliminar(String eliminar) {
        return false;
    }
}
