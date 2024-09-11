package com.johancastro.cun.edu.controller;

import com.johancastro.cun.edu.model.Libro;
import com.johancastro.cun.edu.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/cun/biblioteca/book")
public class BookController {

    private static final String EXIST = "El libro ya existe.";
    private static final String NO_EXIST = "El libro no existe.";

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody Libro libro) {
        Optional<Libro> libroOptional = bookService.crear(libro);
        return libroOptional.map(l -> ResponseEntity.ok(l.toString())).orElseGet(() -> ResponseEntity.ok(EXIST));
    }

    @GetMapping("/all")
    public ResponseEntity<String> all() {
        Iterable<Libro> libros = bookService.listar();
        StringBuilder respuesta = new StringBuilder();
        for (Libro libro : libros) {
            respuesta.append(libro.toString()).append("\n");
        }
        return ResponseEntity.ok(respuesta.toString());
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<String> buscar(@PathVariable String isbn) {
        Optional<Libro> libro = bookService.buscar(isbn);
        return libro.map(value -> ResponseEntity.ok(value.toString())).orElseGet(() -> ResponseEntity.ok(NO_EXIST));
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateUser(@RequestBody Libro libro) {
        Optional<Libro> book = bookService.actualizar(libro);
        return book.map(value -> ResponseEntity.ok(value.toString())).orElseGet(() -> ResponseEntity.ok(NO_EXIST));
    }

    @DeleteMapping("/{isbn}")
    public ResponseEntity<String> eliminar(@PathVariable String isbn) {
        if (bookService.eliminar(isbn))
            return ResponseEntity.ok("Libro eliminado correctamente");
        return ResponseEntity.ok(NO_EXIST);
    }
}
