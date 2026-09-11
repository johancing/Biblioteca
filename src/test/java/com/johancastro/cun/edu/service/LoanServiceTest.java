package com.johancastro.cun.edu.service;

import com.johancastro.cun.edu.model.Libro;
import com.johancastro.cun.edu.model.Prestamo;
import com.johancastro.cun.edu.model.Usuario;
import com.johancastro.cun.edu.persistence.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoanServiceTest {

    @Mock
    private IUserRepository userRepository;

    @Mock
    private IBookRepository bookRepository;

    @Mock
    private ILoanRepository loanRepository;

    @InjectMocks
    private LoanService loanService;

    private Usuario usuarioModelo;
    private Libro libroModelo;
    private Prestamo prestamoModelo;

    private User userEntity;
    private Book bookEntity;
    private Loan loanEntity;

    @BeforeEach
    void setUp() {
        usuarioModelo = Data.getUsuario();
        libroModelo = Data.getLibro();
        userEntity = Data.getUser();
        bookEntity = Data.getBook();
        prestamoModelo = Data.getPrestamo();
        loanEntity = Data.getLoan();
    }

    @Test
    void crear_CuandoTodoEsCorrecto_DebeGuardarYRetornarPrestamoExitoso() {
        when(userRepository.findByDocumentNumber(usuarioModelo.getNumeroDocumento())).thenReturn(Optional.of(userEntity));
        when(bookRepository.findByIsbn(libroModelo.getIsbn())).thenReturn(Optional.of(bookEntity));
        when(loanRepository.findByBookId(bookEntity.getBookId())).thenReturn(Optional.empty());
        when(loanRepository.save(any(Loan.class))).thenReturn(loanEntity);
        Optional<Prestamo> resultado = loanService.crear(prestamoModelo);
        assertTrue(resultado.isPresent());
        assertTrue(resultado.get().getInformacion().contains("Prestamo creado correctamente"));
        verify(loanRepository, times(1)).save(any(Loan.class));
    }

    @Test
    void actualizar_CuandoExistePrestamo_DebeRegistrarDevolucionExitosamente() {
        when(bookRepository.findByIsbn(libroModelo.getIsbn())).thenReturn(Optional.of(bookEntity));
        when(loanRepository.findByBookId(bookEntity.getBookId())).thenReturn(Optional.of(loanEntity));
        when(loanRepository.save(any(Loan.class))).thenReturn(loanEntity);
        Optional<Prestamo> resultado = loanService.actualizar(prestamoModelo);
        assertTrue(resultado.isPresent());
        assertEquals("Libro regresado de forma exitosa.", resultado.get().getInformacion());
        verify(loanRepository, times(1)).save(any(Loan.class));
    }

    @Test
    void buscar_CuandoLibroNoExiste_DebeRetornarMensajeLibroNoRegistrado() {
        String isbn = "978-0134685991";
        when(bookRepository.findByIsbn(isbn)).thenReturn(Optional.empty());
        Optional<Prestamo> resultado = loanService.buscar(isbn);
        assertTrue(resultado.isPresent());
        assertEquals("Libro no registrado en la biblioteca.", resultado.get().getInformacion());
    }

    @Test
    void buscar_CuandoLibroNoEstaPrestado_DebeRetornarMensajeNoPrestado() {
        String isbn = "978-0134685991";
        when(bookRepository.findByIsbn(isbn)).thenReturn(Optional.of(bookEntity));
        when(loanRepository.findByBookId(bookEntity.getBookId())).thenReturn(Optional.empty());
        Optional<Prestamo> resultado = loanService.buscar(isbn);
        assertTrue(resultado.isPresent());
        assertEquals("Libro no registrado como prestamo.", resultado.get().getInformacion());
    }

    @Test
    void buscar_CuandoExistePrestamo_DebeRetornarInformacionDelPrestamo() {
        String isbn = "978-0134685991";
        when(bookRepository.findByIsbn(isbn)).thenReturn(Optional.of(bookEntity));
        when(loanRepository.findByBookId(bookEntity.getBookId())).thenReturn(Optional.of(loanEntity));
        Optional<Prestamo> resultado = loanService.buscar(isbn);
        assertTrue(resultado.isPresent());
        assertNotNull(resultado.get().getInformacion());
        verify(loanRepository, times(1)).findByBookId(bookEntity.getBookId());
    }

}