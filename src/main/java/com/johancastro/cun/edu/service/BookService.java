package com.johancastro.cun.edu.service;

import com.johancastro.cun.edu.model.Libro;
import com.johancastro.cun.edu.persistence.Book;
import com.johancastro.cun.edu.persistence.IBookRepository;
import com.johancastro.cun.edu.util.GeneralMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService implements IService<Libro, String> {

    private final IBookRepository bookRepository;

    @Autowired
    public BookService(IBookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Optional<Libro> crear(Libro libro) {
        Optional<Book> bookOptional = bookRepository.findByIsbn(libro.getIsbn());
        if (bookOptional.isPresent()) {
            return Optional.empty();
        }
        return Optional.of(GeneralMapper.bookToLibro(bookRepository.save(GeneralMapper.libroToBook(libro))));
    }

    @Override
    public Optional<Libro> actualizar(Libro libro) {
        Optional<Book> bookOptional = bookRepository.findByIsbn(libro.getIsbn());
        if (bookOptional.isPresent()) {
            Book newBook = GeneralMapper.actualizarBook(libro, bookOptional.get());
            newBook = bookRepository.save(newBook);
            return Optional.of(GeneralMapper.bookToLibro(newBook));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Libro> buscar(String s) {
        Optional<Book> book = bookRepository.findByIsbn(s);
        return book.map(GeneralMapper::bookToLibro);
    }

    @Override
    public Iterable<Libro> listar() {
        List<Book> books = bookRepository.findAll();
        List<Libro> libros = new ArrayList<>();
        books.forEach(book ->
                libros.add(GeneralMapper.bookToLibro(book))
        );
        return libros;
    }

    @Override
    public boolean eliminar(String eliminar) {
        int response = bookRepository.deleteByIsbn(eliminar);
        return (response > 0);
    }
}
