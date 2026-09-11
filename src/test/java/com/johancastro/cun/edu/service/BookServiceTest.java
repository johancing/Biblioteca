package com.johancastro.cun.edu.service;

import com.johancastro.cun.edu.model.Libro;
import com.johancastro.cun.edu.persistence.Book;
import com.johancastro.cun.edu.persistence.IBookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private IBookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    private Libro libroModelo;
    private Book bookEntity;

    @BeforeEach
    void setUp() {
        libroModelo = Data.getLibro();
        bookEntity = Data.getBook();
    }

    @Test
    void crear_CuandoIsbnNoExiste_DebeGuardarYRetornarLibro() {
        when(bookRepository.findByIsbn(libroModelo.getIsbn())).thenReturn(Optional.empty());
        when(bookRepository.save(any(Book.class))).thenReturn(bookEntity);
        Optional<Libro> resultado = bookService.crear(libroModelo);
        assertTrue(resultado.isPresent());
        assertEquals(libroModelo.getIsbn(), resultado.get().getIsbn());
        verify(bookRepository, times(1)).findByIsbn(libroModelo.getIsbn());
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void crear_CuandoIsbnYaExiste_DebeRetornarOptionalVacio() {
        when(bookRepository.findByIsbn(libroModelo.getIsbn())).thenReturn(Optional.of(bookEntity));
        Optional<Libro> resultado = bookService.crear(libroModelo);
        assertFalse(resultado.isPresent());
        verify(bookRepository, times(1)).findByIsbn(libroModelo.getIsbn());
        verify(bookRepository, never()).save(any(Book.class));
    }

    // --- PRUEBAS DE ACTUALIZAR ---

    @Test
    void actualizar_CuandoExisteIsbn_DebeActualizarYRetornarLibro() {
        when(bookRepository.findByIsbn(libroModelo.getIsbn())).thenReturn(Optional.of(bookEntity));
        when(bookRepository.save(any(Book.class))).thenReturn(bookEntity);
        Optional<Libro> resultado = bookService.actualizar(libroModelo);
        assertTrue(resultado.isPresent());
        assertEquals(libroModelo.getIsbn(), resultado.get().getIsbn());
        verify(bookRepository, times(1)).findByIsbn(libroModelo.getIsbn());
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void actualizar_CuandoNoExisteIsbn_DebeRetornarOptionalVacio() {
        when(bookRepository.findByIsbn(libroModelo.getIsbn())).thenReturn(Optional.empty());
        Optional<Libro> resultado = bookService.actualizar(libroModelo);
        assertFalse(resultado.isPresent());
        verify(bookRepository, times(1)).findByIsbn(libroModelo.getIsbn());
        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    void buscar_CuandoExisteIsbn_DebeRetornarLibro() {
        String isbn = "978-0134685991";
        when(bookRepository.findByIsbn(isbn)).thenReturn(Optional.of(bookEntity));
        Optional<Libro> resultado = bookService.buscar(isbn);
        assertTrue(resultado.isPresent());
        assertEquals(isbn, resultado.get().getIsbn());
        verify(bookRepository, times(1)).findByIsbn(isbn);
    }

    @Test
    void buscar_CuandoNoExisteIsbn_DebeRetornarOptionalVacio() {
        String isbn = "000-0000000000";
        when(bookRepository.findByIsbn(isbn)).thenReturn(Optional.empty());
        Optional<Libro> resultado = bookService.buscar(isbn);
        assertFalse(resultado.isPresent());
        verify(bookRepository, times(1)).findByIsbn(isbn);
    }

    @Test
    void listar_DebeRetornarListaDeLibros() {
        when(bookRepository.findAll()).thenReturn(List.of(bookEntity));
        Iterable<Libro> resultado = bookService.listar();
        assertNotNull(resultado);
        List<Libro> listaLibros = (List<Libro>) resultado;
        assertEquals(1, listaLibros.size());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void eliminar_CuandoEliminaRegistro_DebeRetornarTrue() {
        String isbn = "978-0134685991";
        when(bookRepository.deleteByIsbn(isbn)).thenReturn(1);
        boolean resultado = bookService.eliminar(isbn);
        assertTrue(resultado);
        verify(bookRepository, times(1)).deleteByIsbn(isbn);
    }

    @Test
    void eliminar_CuandoNoEliminaNada_DebeRetornarFalse() {
        String isbn = "000-0000000000";
        when(bookRepository.deleteByIsbn(isbn)).thenReturn(0);
        boolean resultado = bookService.eliminar(isbn);
        assertFalse(resultado);
        verify(bookRepository, times(1)).deleteByIsbn(isbn);
    }
}