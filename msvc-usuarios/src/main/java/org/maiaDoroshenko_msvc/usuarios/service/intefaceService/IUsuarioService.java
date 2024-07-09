package org.maiaDoroshenko_msvc.usuarios.service.intefaceService;

import org.maiaDoroshenko_msvc.usuarios.models.entity.UsuarioEntity;

import java.util.List;
import java.util.Optional;

public interface IUsuarioService {
List<UsuarioEntity>findAll();
Optional<UsuarioEntity> findById(Long id);
UsuarioEntity saveUsuario(UsuarioEntity usuario);
UsuarioEntity updateUsuario(UsuarioEntity usuario,Long id);
void checkIfEmailExists(String email);
void delete(Long id);

List<UsuarioEntity>findAllByIdis(Iterable<Long>idis);




}
