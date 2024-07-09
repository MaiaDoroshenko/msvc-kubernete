package org.maiaDoroshenko.msvc.cursos.repository;

import org.maiaDoroshenko.msvc.cursos.models.entity.CursosEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursosRepository extends CrudRepository<CursosEntity,Long> {
    @Modifying//indica que no es una consulta de solo lectura , q modifica los datos
    @Query("delete from CursoUsuarioEntity cu where cu.usuarioId=?1")
    void deleteCursoUsuarioById(Long id);
}
