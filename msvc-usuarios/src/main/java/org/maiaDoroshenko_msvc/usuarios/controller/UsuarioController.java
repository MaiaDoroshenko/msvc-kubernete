package org.maiaDoroshenko_msvc.usuarios.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.maiaDoroshenko_msvc.usuarios.models.entity.UsuarioEntity;
import org.maiaDoroshenko_msvc.usuarios.service.implementationService.UsuarioServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@AllArgsConstructor
@Slf4j
public class UsuarioController {

    private final UsuarioServiceImpl usuarioService;


    @PostMapping("/create")
    public ResponseEntity<?> createUser(@Valid @RequestBody UsuarioEntity usuario, BindingResult result) {
        if (result.hasErrors()) {
            return validar(result);
        }
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.saveUsuario(usuario));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrió un error al procesar la solicitud.");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UsuarioEntity usuario, BindingResult result, @PathVariable Long id) {
        if (result.hasErrors()) {
            return validar(result);
        }
        try {
            return ResponseEntity.status(HttpStatus.OK).body(usuarioService.updateUsuario(usuario, id));
        } catch (NoSuchElementException e) {
            log.warn("Usuario no encontrado con id {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado con id: " + id);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<Map<String,List<UsuarioEntity>>> findAll() {
        Map<String, List<UsuarioEntity>> response = Collections.singletonMap("Users", usuarioService.findAll());
        return ResponseEntity.ok(response);
    }



    @GetMapping("/findById/{id}")
    public ResponseEntity<UsuarioEntity> findById(@PathVariable Long id) {
        Optional<UsuarioEntity> usuario = usuarioService.findById(id);
        return usuario.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (usuarioService.findById(id).isPresent()) {
            usuarioService.delete(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @GetMapping("/find-all-users-by-idis")
    public ResponseEntity<?> findAllByIdis(@RequestParam List<Long> idis) {
        return ResponseEntity.ok(usuarioService.findAllByIdis((idis)));
    }

    private static ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errores.put(err.getField(), " el campo " + " " + err.getField() +  err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }


}
