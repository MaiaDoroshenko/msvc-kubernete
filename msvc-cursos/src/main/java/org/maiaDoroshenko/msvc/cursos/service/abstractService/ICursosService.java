package org.maiaDoroshenko.msvc.cursos.service.abstractService;

import org.maiaDoroshenko.msvc.cursos.models.User;
import org.maiaDoroshenko.msvc.cursos.models.entity.CursosEntity;

import java.util.List;
import java.util.Optional;

public interface ICursosService {
  List<CursosEntity> findAll();
  CursosEntity saveCurso(CursosEntity cursos);
  CursosEntity updateCurso(CursosEntity curso,Long id);
  Optional<CursosEntity>findByid(Long id);
  void deleteCurso(Long id);

  Optional<User>AssignUser(User usuario, Long cursoId);
  Optional<User>createUser(User usuario, Long cursoId);
  Optional<User>unassignUser(User usuario, Long cursoId);
  Optional<CursosEntity> findAllUsersByIdis(Long id);
  void deleteCursoUsuarioById(Long id);


}
