package com.johancastro.cun.edu.util;

import com.johancastro.cun.edu.model.Libro;
import com.johancastro.cun.edu.model.Prestamo;
import com.johancastro.cun.edu.model.SolicitudPrestamo;
import com.johancastro.cun.edu.persistence.Book;
import com.johancastro.cun.edu.persistence.Loan;
import com.johancastro.cun.edu.persistence.User;
import com.johancastro.cun.edu.model.Usuario;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

public class GeneralMapper {

    public static User usuarioToUser(Usuario usuario) {
        User user = new User();
        user.setAddress(usuario.getDireccion());
        user.setBirthdate(usuario.getFechaNacimiento());
        user.setEmail(usuario.getEmail());
        user.setName(usuario.getNombre());
        user.setLastName(usuario.getApellido());
        user.setDocumentType(usuario.getTipoDocumento());
        user.setDocumentNumber(usuario.getNumeroDocumento());
        user.setPhoneNumber(usuario.getNumeroTelefono());
        return user;
    }

    public static Usuario userToUsuario(User user) {
        Usuario usuario = new Usuario();
        usuario.setId(user.getUserId());
        usuario.setTipoDocumento(user.getDocumentType());
        usuario.setNumeroDocumento(user.getDocumentNumber());
        usuario.setNombre(user.getName());
        usuario.setApellido(user.getLastName());
        usuario.setDireccion(user.getAddress());
        usuario.setEmail(user.getEmail());
        usuario.setNumeroTelefono(user.getPhoneNumber());
        return usuario;
    }

    public static Libro bookToLibro(Book book) {
        Libro libro = new Libro();
        libro.setId(book.getBookId());
        libro.setIsbn(book.getIsbn());
        libro.setTitulo(book.getName());
        libro.setAutor(book.getAuthor());
        libro.setEdicion(book.getEdition());
        return libro;
    }

    public static Book libroToBook(Libro libro) {
        Book book = new Book();
        book.setBookId(libro.getId());
        book.setIsbn(libro.getIsbn());
        book.setName(libro.getTitulo());
        book.setAuthor(libro.getAutor());
        book.setEdition(libro.getEdicion());
        return book;
    }

    public static User actualizarUsuario(Usuario usuario, User user) {
        user.setName(usuario.getNombre());
        user.setLastName(usuario.getApellido());
        user.setBirthdate(usuario.getFechaNacimiento());
        user.setAddress(usuario.getDireccion());
        user.setEmail(usuario.getEmail());
        user.setPhoneNumber(usuario.getNumeroTelefono());
        return user;
    }

    public static Book actualizarBook(Libro libro, Book book) {
        book.setName(libro.getTitulo());
        book.setAuthor(libro.getAutor());
        book.setEdition(libro.getEdicion());
        return book;
    }

    public static Prestamo solicitdToPrestamo(SolicitudPrestamo solicitudPrestamo) {
        Prestamo prestamo = new Prestamo();
        if (solicitudPrestamo.getNumeroDocumento() != null) {
            Usuario usuario = new Usuario();
            usuario.setNumeroDocumento(solicitudPrestamo.getNumeroDocumento());
            prestamo.setUsuario(usuario);
        }
        if (solicitudPrestamo.getDiasPrestamo() > 0) {
            Date fechaInicio = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(fechaInicio);
            cal.add(Calendar.DATE, solicitudPrestamo.getDiasPrestamo());
            Date fechaRegreso = cal.getTime();
            prestamo.setFechaPrestamo(fechaInicio);
            prestamo.setFechaEstimadaReintegro(fechaRegreso);
        }
        Libro libro = new Libro();
        libro.setIsbn(solicitudPrestamo.getIsbn());
        prestamo.setLibro(libro);
        return prestamo;
    }

    public static Loan prestamoToLoan(Prestamo prestamo, User user, Book book) {
        Loan loan = new Loan();
        loan.setUserId(user);
        loan.setBookId(book);
        loan.setLoanDate(prestamo.getFechaPrestamo());
        loan.setEstimatedDateReturn(prestamo.getFechaEstimadaReintegro());
        return loan;
    }

    public static Prestamo loanToPrestamo(Loan active) {
        Prestamo prestamo = new Prestamo();
        prestamo.setPrestamoId(active.getLoanId());
        prestamo.setUsuario(GeneralMapper.userToUsuario(active.getUserId()));
        prestamo.setLibro(GeneralMapper.bookToLibro(active.getBookId()));
        prestamo.setFechaPrestamo(active.getLoanDate());
        prestamo.setFechaEstimadaReintegro(active.getEstimatedDateReturn());
        prestamo.setFechaReintegro(active.getReturnDate());
        return prestamo;
    }

    private GeneralMapper() {}

}
