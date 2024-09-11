package com.johancastro.cun.edu.controller;

import com.johancastro.cun.edu.model.Usuario;
import com.johancastro.cun.edu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/cun/biblioteca/user")
public class UserController {

    private static final String USER = "El usuario ya existe.";
    private static final String NO_USER = "El usuario no existe.";

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody Usuario user) {
        Optional<Usuario> userOptional = userService.crear(user);
        return userOptional.map(usuario -> ResponseEntity.ok(usuario.toString())).orElseGet(() -> ResponseEntity.ok(USER));
    }

    @GetMapping("/all")
    public ResponseEntity<String> listarTodos() {
        Iterable<Usuario> usuarios = userService.listar();
        StringBuilder respuesta = new StringBuilder();
        for (Usuario usuario : usuarios) {
            respuesta.append(usuario.toString()).append("\n");
        }
        return ResponseEntity.ok(respuesta.toString());
    }

    @GetMapping("/{numeroDocumento}")
    public ResponseEntity<String> buscar(@PathVariable String numeroDocumento) {
        Optional<Usuario> usuario = userService.buscar(numeroDocumento);
        return usuario.map(value -> ResponseEntity.ok(value.toString())).orElseGet(() -> ResponseEntity.ok(NO_USER));
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateUser(@RequestBody Usuario user) {
        Optional<Usuario> usuario = userService.actualizar(user);
        return usuario.map(value -> ResponseEntity.ok(value.toString())).orElseGet(() -> ResponseEntity.ok(NO_USER));
    }

    @DeleteMapping("/{numeroDocumento}")
    public ResponseEntity<String> eliminar(@PathVariable String numeroDocumento) {
        if (userService.eliminar(numeroDocumento))
            return ResponseEntity.ok("Usuario eliminado correctamente");
        return ResponseEntity.ok(NO_USER);
    }
}
