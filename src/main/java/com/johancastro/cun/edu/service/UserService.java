package com.johancastro.cun.edu.service;

import com.johancastro.cun.edu.persistence.User;
import com.johancastro.cun.edu.model.Usuario;
import com.johancastro.cun.edu.persistence.IUserRepository;
import com.johancastro.cun.edu.util.GeneralMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IService<Usuario, String> {

    private final IUserRepository userRepository;

    @Autowired
    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<Usuario> crear(Usuario usuario) {
        Optional<User> userOptional = userRepository.findByDocumentNumber(usuario.getNumeroDocumento());
        if (userOptional.isPresent()) {
            return Optional.empty();
        }
        return Optional.of(GeneralMapper.userToUsuario(userRepository.save(GeneralMapper.usuarioToUser(usuario))));
    }

    @Override
    public Optional<Usuario> actualizar(Usuario usuario) {
        Optional<User> userOptional = userRepository.findByDocumentNumber(usuario.getNumeroDocumento());
        if (userOptional.isPresent()) {
            User newUser = GeneralMapper.actualizarUsuario(usuario, userOptional.get());
            newUser = userRepository.save(newUser);
            return Optional.of(GeneralMapper.userToUsuario(newUser));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Usuario> buscar(String s) {
        Optional<User> userOptional = userRepository.findByDocumentNumber(s);
        return userOptional.map(GeneralMapper::userToUsuario);
    }

    @Override
    public Iterable<Usuario> listar() {
        List<User> users = userRepository.findAll();
        List<Usuario> usuarios = new ArrayList<>();
        users.forEach(user ->
                usuarios.add(GeneralMapper.userToUsuario(user))
        );
        return usuarios;
    }

    @Override
    public boolean eliminar(String eliminar) {
        int response = userRepository.deleteByDocumentNumber(eliminar);
        return (response > 0);
    }
}
