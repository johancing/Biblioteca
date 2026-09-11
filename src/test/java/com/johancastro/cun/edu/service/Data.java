package com.johancastro.cun.edu.service;

import com.johancastro.cun.edu.model.Libro;
import com.johancastro.cun.edu.model.Prestamo;
import com.johancastro.cun.edu.model.Usuario;
import com.johancastro.cun.edu.persistence.Book;
import com.johancastro.cun.edu.persistence.Loan;
import com.johancastro.cun.edu.persistence.User;

import java.util.Date;

public class Data {

    public static Libro getLibro() {
        Libro libroModelo = new Libro();
        libroModelo.setIsbn("978-0134685991");
        libroModelo.setTitulo("Effective Java");
        libroModelo.setAutor("James Artiller");
        libroModelo.setEdicion("4 ed");
        return libroModelo;
    }

    public static Book getBook() {
        Book bookEntity = new Book();
        bookEntity.setIsbn("978-0134685991");
        bookEntity.setName("Effective Java");
        bookEntity.setAuthor("James Artiller");
        bookEntity.setEdition("4 ed");
        return bookEntity;
    }

    public static Usuario getUsuario() {
        Usuario usuarioModelo = new Usuario();
        usuarioModelo.setId(1L);
        usuarioModelo.setTipoDocumento("CC");
        usuarioModelo.setNumeroDocumento("123456789");
        usuarioModelo.setNombre("Johan");
        usuarioModelo.setApellido("Castro");
        usuarioModelo.setFechaNacimiento(new Date());
        usuarioModelo.setDireccion("Calle 123");
        usuarioModelo.setNumeroTelefono("3001234567");
        usuarioModelo.setEmail("johan@cun.edu.co");
        return usuarioModelo;
    }

    public static User getUser() {
        User userEntity = new User();
        userEntity.setUserId(1L);
        userEntity.setDocumentType("CC");
        userEntity.setDocumentNumber("123456789");
        userEntity.setName("Johan");
        userEntity.setLastName("Castro");
        userEntity.setBirthdate(new Date());
        userEntity.setAddress("Calle 123");
        userEntity.setPhoneNumber("3001234567");
        userEntity.setEmail("johan@cun.edu.co");
        return userEntity;
    }

    public static Prestamo getPrestamo() {
        Prestamo prestamoModelo = new Prestamo();
        prestamoModelo.setUsuario(getUsuario());
        prestamoModelo.setLibro(getLibro());
        prestamoModelo.setFechaEstimadaReintegro(new Date());
        return prestamoModelo;
    }

    public static Loan  getLoan() {
        Loan loanEntity = new Loan();
        loanEntity.setLoanId(100L);
        loanEntity.setUserId(getUser());
        loanEntity.setBookId(getBook());
        loanEntity.setEstimatedDateReturn(new Date());
        return loanEntity;
    }

    private Data(){}
}
