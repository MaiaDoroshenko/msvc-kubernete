package org.maiaDoroshenko.msvc.cursos.controller;

import feign.FeignException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.maiaDoroshenko.msvc.cursos.models.User;
import org.maiaDoroshenko.msvc.cursos.models.entity.CursosEntity;
import org.maiaDoroshenko.msvc.cursos.service.abstractService.ICursosService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("curso")
@AllArgsConstructor
public class CursosController {
    private final ICursosService cursosService;



    @PostMapping("create")
    public ResponseEntity<?> saveCurso(@Valid @RequestBody CursosEntity cursos, BindingResult result) {
        if (result.hasErrors()) {
            return validar(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(cursosService.saveCurso(cursos));
    }

    @PutMapping("update/{id}")
    public ResponseEntity<?> updateCurso(@Valid @RequestBody CursosEntity cursos, BindingResult result, @PathVariable Long id) {
        if (result.hasErrors()) {
            return validar(result);
        }
        Optional<CursosEntity> existingCurso = cursosService.findByid(id);
        if (existingCurso.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(cursosService.updateCurso(cursos, id));
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<CursosEntity>> findAll() {
        return ResponseEntity.ok(cursosService.findAll());
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<CursosEntity> findById(@PathVariable Long id) {
        Optional<CursosEntity> curso = cursosService.findAllUsersByIdis(id);//cursosService.findByid(id);
        if (curso.isPresent()) {
            return ResponseEntity.ok(curso.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Optional<CursosEntity> existingCurso = cursosService.findByid(id);
        if (existingCurso.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        cursosService.deleteCurso(id);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/delete-user-by-id/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        try {
            cursosService.deleteCursoUsuarioById(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no asignado a ningún curso: " + e.getMessage());
        }
    }

    @PutMapping("/assign-user/{cursoId}")
    public ResponseEntity<?> assignUser(@RequestBody User user, @PathVariable Long cursoId) {
        Optional<User> optional;
        try {
            optional = cursosService.AssignUser(user, cursoId);
        } catch (FeignException fe) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: , No existe el usuarios por el id o error en la comunicacion" + fe.getMessage());
        }
        if (optional.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(optional.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }


    @PostMapping("/create-user/{cursoId}")
    public ResponseEntity<?> createUser(@RequestBody User user, @PathVariable Long cursoId) {
        Optional<User> optional;
        try {
            optional = cursosService.createUser(user, cursoId);
        } catch (FeignException fe) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error en la comunicacion" + fe.getMessage());
        }
        if (optional.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(optional.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/unassignUser-user/{cursoId}")
    public ResponseEntity<?>deleteUser(@RequestBody User user ,@PathVariable Long  cursoId){
        Optional<User> optional;
        try {
            optional = cursosService.unassignUser(user, cursoId);
        } catch (FeignException fe) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: , No existe el usuarios por el id o error en la comunicacion" + fe.getMessage());
        }
        if (optional.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(optional.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    private static ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errores.put(err.getField(), " el campo " + " " + err.getField() + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }


}
