package org.maiaDoroshenko_msvc.usuarios.service.implementationService;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.maiaDoroshenko_msvc.usuarios.models.entity.UsuarioEntity;
import org.maiaDoroshenko_msvc.usuarios.repository.UsuarioRepository;
import org.maiaDoroshenko_msvc.usuarios.service.intefaceService.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Data
@Slf4j
public class UsuarioServiceImpl implements IUsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioEntity> findAll() {
        return (List<UsuarioEntity>) usuarioRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UsuarioEntity> findById(Long id) {
        Optional<UsuarioEntity> usuario = usuarioRepository.findById(id);
        if (usuario.isPresent()) {
            return usuario;
        } else {
            log.warn("Usuario con id {} no encontrado", id);
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public UsuarioEntity saveUsuario(UsuarioEntity usuario) {
        checkIfEmailExists(usuario.getEmail());
        return usuarioRepository.save(usuario);
    }

    @Override
    public UsuarioEntity updateUsuario(UsuarioEntity usuario, Long id) {
        Optional<UsuarioEntity> existingUserWithEmail = usuarioRepository.findByEmail(usuario.getEmail());
        if (existingUserWithEmail.isPresent() && !existingUserWithEmail.get().getId().equals(id)) {
            throw new IllegalArgumentException("El correo electrónico " + usuario.getEmail() + " ya está en uso por otro usuario.");
        }

        UsuarioEntity usuarioUpdated = usuarioRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Usuario no encontrado con id: " + id));

        usuarioUpdated.setName(usuario.getName());
        usuarioUpdated.setLastName(usuario.getLastName());
        usuarioUpdated.setEmail(usuario.getEmail());
        usuarioUpdated.setPassword(usuario.getPassword());

        return usuarioRepository.save(usuarioUpdated);
    }
    @Override
    public void checkIfEmailExists(String email) {
        if (usuarioRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("El usuario con el correo electrónico " + email + " ya existe.");
        }

    }
    @Override
    @Transactional
    public void delete(Long id) {
        usuarioRepository.deleteById(id);
    }
}
