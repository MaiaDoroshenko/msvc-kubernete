package org.maiaDoroshenko.msvc.cursos.service.ImplService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.maiaDoroshenko.msvc.cursos.models.Usuario;
import org.maiaDoroshenko.msvc.cursos.models.entity.CursosEntity;
import org.maiaDoroshenko.msvc.cursos.repository.CursosRepository;
import org.maiaDoroshenko.msvc.cursos.service.abstractService.ICursosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Data
@Slf4j
public class CursosServiceImpl implements ICursosService {


    private final CursosRepository cursosRepository;
    @Autowired
    public CursosServiceImpl(CursosRepository cursosRepository) {
        this.cursosRepository = cursosRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CursosEntity> findAll() {
        return (List<CursosEntity>) cursosRepository.findAll();
    }
    @Override
    @Transactional
    public CursosEntity saveCurso(CursosEntity cursos) {
        return cursosRepository.save(cursos);
    }
    @Override
    @Transactional
    public CursosEntity updateCurso(CursosEntity curso, Long id) {
        CursosEntity cursosToUpdate = cursosRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Curso no encontrado"));
        cursosToUpdate.setName(curso.getName());
        return cursosRepository.save(cursosToUpdate);
    }
    @Override
    @Transactional(readOnly = true)
    public Optional<CursosEntity> findByid(Long id) {
        Optional<CursosEntity> cursos = cursosRepository.findById(id);
        if (cursos.isPresent()) {
            return cursos;
        } else {
            log.warn("curso con id {} no encontrado", id);
            return Optional.empty();
        }
    }
    @Override
    @Transactional
    public void deleteCurso(Long id) {
        CursosEntity cursoToDelete = cursosRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("curso con id no encontrado"));
        cursosRepository.delete(cursoToDelete);
    }

    @Override
    public Optional<Usuario> AssignUser(Usuario usuario, Long cursoId) {
        return Optional.empty();
    }

    @Override
    public Optional<Usuario> createUser(Usuario usuario, Long cursoId) {
        return Optional.empty();
    }

    @Override
    public Optional<Usuario> unassignUser(Usuario usuario, Long cursoId) {
        return Optional.empty();
    }


}
