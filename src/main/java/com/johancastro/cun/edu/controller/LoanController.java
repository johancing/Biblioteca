package com.johancastro.cun.edu.controller;

import com.johancastro.cun.edu.model.Libro;
import com.johancastro.cun.edu.model.Prestamo;
import com.johancastro.cun.edu.model.SolicitudPrestamo;
import com.johancastro.cun.edu.persistence.Loan;
import com.johancastro.cun.edu.service.LoanService;
import com.johancastro.cun.edu.util.GeneralMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cun/biblioteca/loan")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> creatPrestamo(@RequestBody SolicitudPrestamo solicitudPrestamo) {
        Optional<Prestamo> prestamo = loanService.crear(GeneralMapper.solicitdToPrestamo(solicitudPrestamo));
        return ResponseEntity.ok(prestamo.get().getInformacion());
    }

    @PutMapping("/return_book")
    public ResponseEntity<String> returnBook(@RequestBody SolicitudPrestamo solicitudPrestamo) {
        Optional<Prestamo> prestamo = loanService.actualizar(GeneralMapper.solicitdToPrestamo(solicitudPrestamo));
        return ResponseEntity.ok(prestamo.get().getInformacion());
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<String> buscar(@PathVariable String isbn) {
        Optional<Prestamo> prestamo = loanService.buscar(isbn);
        return ResponseEntity.ok(prestamo.get().getInformacion());
    }

    @GetMapping("/all")
    public ResponseEntity<String> allPrestamos() {
        Iterable<Prestamo> prestamos = loanService.listar();
        StringBuilder respuesta = new StringBuilder();
        for (Prestamo prestamo : prestamos) {
            respuesta.append(prestamo.toString()).append("\n");
        }
        return ResponseEntity.ok(respuesta.toString());
    }
}
