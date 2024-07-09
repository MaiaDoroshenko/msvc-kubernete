package org.maiaDoroshenko.msvc.cursos.service.ImplService;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.maiaDoroshenko.msvc.cursos.clients.UserClientRest;
import org.maiaDoroshenko.msvc.cursos.models.User;
import org.maiaDoroshenko.msvc.cursos.models.entity.CursoUsuarioEntity;
import org.maiaDoroshenko.msvc.cursos.models.entity.CursosEntity;
import org.maiaDoroshenko.msvc.cursos.repository.CursosRepository;
import org.maiaDoroshenko.msvc.cursos.service.abstractService.ICursosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Data
@Slf4j
public class CursosServiceImpl implements ICursosService {

    @Autowired
    private CursosRepository cursosRepository;
    @Autowired
    private UserClientRest clientRest;


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


   //Se consumen del microservici Usuarios
    @Override
    @Transactional
    public Optional<User> AssignUser(User usuario, Long cursoId) {
        Optional<CursosEntity> optional = cursosRepository.findById(cursoId);
        if (optional.isPresent()) {
            User userMsvc = clientRest.findById(usuario.getId());//valida q el usuario exista en el msvc Usuario por el id
            log.info("Usuario encontrado: {}", userMsvc);

            CursosEntity curso = optional.get();
            CursoUsuarioEntity cursoUsuario = new CursoUsuarioEntity();
            cursoUsuario.setUsuarioId(userMsvc.getId());
            log.info("Asignando usuarioId: {}", cursoUsuario.getUsuarioId());

            curso.addCursoUsuario(cursoUsuario);
            cursosRepository.save(curso);
            log.info("Curso después de asignar usuario: {}", curso);
            return Optional.of(userMsvc);
        }
        log.warn("Curso con id {} no encontrado", cursoId);
        return Optional.empty();
    }
    @Override
    @Transactional
    public Optional<User> createUser(User usuario, Long cursoId) {
        Optional<CursosEntity> optional = cursosRepository.findById(cursoId);
        if (optional.isPresent()) {
            User newUserMsvc = clientRest.creteUser(usuario);

            CursosEntity curso = optional.get();
            CursoUsuarioEntity cursoUser = new CursoUsuarioEntity();
            cursoUser.setUsuarioId(newUserMsvc.getId());

            curso.addCursoUsuario(cursoUser);
            cursosRepository.save(curso);
            return Optional.of(newUserMsvc);
        }
        return Optional.empty();
    }
    @Override
    @Transactional
    public Optional<User> unassignUser(User usuario, Long cursoId) {
        Optional<CursosEntity> optional = cursosRepository.findById(cursoId);
        if (optional.isPresent()) {
            User userMsvc = clientRest.findById(usuario.getId());

            CursosEntity curso = optional.get();
            CursoUsuarioEntity cursoUsuario = new CursoUsuarioEntity();
            cursoUsuario.setUsuarioId(userMsvc.getId());

            curso.removeCursoUsuario(cursoUsuario);
            return Optional.of(userMsvc);
        }
        return Optional.empty();
    }
    @Override
    @Transactional(readOnly = true)
    public Optional<CursosEntity> findAllUsersByIdis(Long id) {
        Optional<CursosEntity> optional = cursosRepository.findById(id);
        if (optional.isPresent()) {
            CursosEntity curso = optional.get();
            if (!curso.getCursoUsuarios().isEmpty()) {
                List<Long> ids = curso.getCursoUsuarios().stream().map(cu -> cu.getUsuarioId())
                        .collect(Collectors.toList());
                List<User> users = clientRest.findAllUsersByIdis(ids);
                curso.setUsuarios(users);
            }
            return Optional.of(curso);
        }
        return Optional.empty();
    }
    @Override
    @Transactional
    public void deleteCursoUsuarioById(Long id) {
        Optional<CursosEntity> cursoUsuarioOpt = cursosRepository.findById(id);
        if (cursoUsuarioOpt.isPresent()) {
            cursosRepository.deleteCursoUsuarioById(id);
        } else {
            throw new NoSuchElementException("Usuario no asignado a ningún curso");
        }
    }
}
