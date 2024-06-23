package org.maiaDoroshenko.msvc.cursos.service.abstractService;

import org.maiaDoroshenko.msvc.cursos.models.Usuario;
import org.maiaDoroshenko.msvc.cursos.models.entity.CursosEntity;

import java.util.List;
import java.util.Optional;

public interface ICursosService {
  List<CursosEntity> findAll();
  CursosEntity saveCurso(CursosEntity cursos);
  CursosEntity updateCurso(CursosEntity curso,Long id);
  Optional<CursosEntity>findByid(Long id);
  void deleteCurso(Long id);

  Optional<Usuario>AssignUser(Usuario usuario,Long cursoId);
  Optional<Usuario>createUser(Usuario usuario,Long cursoId);
  Optional<Usuario>unassignUser(Usuario usuario,Long cursoId);


}
