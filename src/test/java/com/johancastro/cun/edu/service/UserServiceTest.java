package com.johancastro.cun.edu.service;

import com.johancastro.cun.edu.model.Usuario;
import com.johancastro.cun.edu.persistence.IUserRepository;
import com.johancastro.cun.edu.persistence.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private IUserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private Usuario usuarioModelo;
    private User userEntity;

    @BeforeEach
    void setUp() {
        usuarioModelo = Data.getUsuario();
        userEntity = Data.getUser();
    }

    @Test
    void crear_CuandoDocumentoNoExiste_DebeGuardarYRetornarUsuario() {
        when(userRepository.findByDocumentNumber(usuarioModelo.getNumeroDocumento())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(userEntity);
        Optional<Usuario> resultado = userService.crear(usuarioModelo);
        assertTrue(resultado.isPresent());
        assertEquals(usuarioModelo.getNumeroDocumento(), resultado.get().getNumeroDocumento());
        verify(userRepository, times(1)).findByDocumentNumber(usuarioModelo.getNumeroDocumento());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void crear_CuandoDocumentoYaExiste_DebeRetornarOptionalVacio() {
        when(userRepository.findByDocumentNumber(usuarioModelo.getNumeroDocumento())).thenReturn(Optional.of(userEntity));
        Optional<Usuario> resultado = userService.crear(usuarioModelo);
        assertFalse(resultado.isPresent());
        verify(userRepository, times(1)).findByDocumentNumber(usuarioModelo.getNumeroDocumento());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void actualizar_CuandoExisteDocumento_DebeActualizarYRetornarUsuario() {
        when(userRepository.findByDocumentNumber(usuarioModelo.getNumeroDocumento())).thenReturn(Optional.of(userEntity));
        when(userRepository.save(any(User.class))).thenReturn(userEntity);
        Optional<Usuario> resultado = userService.actualizar(usuarioModelo);
        assertTrue(resultado.isPresent());
        assertEquals(usuarioModelo.getNumeroDocumento(), resultado.get().getNumeroDocumento());
        verify(userRepository, times(1)).findByDocumentNumber(usuarioModelo.getNumeroDocumento());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void actualizar_CuandoNoExisteDocumento_DebeRetornarOptionalVacio() {
        when(userRepository.findByDocumentNumber(usuarioModelo.getNumeroDocumento())).thenReturn(Optional.empty());
        Optional<Usuario> resultado = userService.actualizar(usuarioModelo);
        assertFalse(resultado.isPresent());
        verify(userRepository, times(1)).findByDocumentNumber(usuarioModelo.getNumeroDocumento());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void buscar_CuandoExisteDocumento_DebeRetornarUsuario() {
        String doc = "123456789";
        when(userRepository.findByDocumentNumber(doc)).thenReturn(Optional.of(userEntity));
        Optional<Usuario> resultado = userService.buscar(doc);
        assertTrue(resultado.isPresent());
        assertEquals(doc, resultado.get().getNumeroDocumento());
        verify(userRepository, times(1)).findByDocumentNumber(doc);
    }

    @Test
    void buscar_CuandoNoExisteDocumento_DebeRetornarOptionalVacio() {
        String doc = "000000000";
        when(userRepository.findByDocumentNumber(doc)).thenReturn(Optional.empty());
        Optional<Usuario> resultado = userService.buscar(doc);
        assertFalse(resultado.isPresent());
        verify(userRepository, times(1)).findByDocumentNumber(doc);
    }

    @Test
    void listar_DebeRetornarListaDeUsuarios() {
        when(userRepository.findAll()).thenReturn(List.of(userEntity));
        Iterable<Usuario> resultado = userService.listar();
        assertNotNull(resultado);
        List<Usuario> listaUsuarios = (List<Usuario>) resultado;
        assertEquals(1, listaUsuarios.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void eliminar_CuandoEliminaRegistro_DebeRetornarTrue() {
        String doc = "123456789";
        when(userRepository.deleteByDocumentNumber(doc)).thenReturn(1);
        boolean resultado = userService.eliminar(doc);
        assertTrue(resultado);
        verify(userRepository, times(1)).deleteByDocumentNumber(doc);
    }

    @Test
    void eliminar_CuandoNoEliminaNada_DebeRetornarFalse() {
        String doc = "000000000";
        when(userRepository.deleteByDocumentNumber(doc)).thenReturn(0);
        boolean resultado = userService.eliminar(doc);
        assertFalse(resultado);
        verify(userRepository, times(1)).deleteByDocumentNumber(doc);
    }
}