package org.maiaDoroshenko.msvc.cursos.repository;

import org.maiaDoroshenko.msvc.cursos.models.entity.CursosEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursosRepository extends CrudRepository<CursosEntity,Long> {
}
