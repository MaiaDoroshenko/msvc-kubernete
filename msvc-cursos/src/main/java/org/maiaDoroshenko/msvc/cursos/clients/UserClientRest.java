package org.maiaDoroshenko.msvc.cursos.clients;

import org.maiaDoroshenko.msvc.cursos.models.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "msvc-usuarios", url = "localhost:8001")//indico que microservicio va a consumir
public interface UserClientRest {

    @PostMapping("create")
    User creteUser(@RequestBody User user);

    @PutMapping("/update/{id}")
    User updateUser(@RequestBody User user, @PathVariable Long id);

    @GetMapping("/findAll")
    List<User> findAllUsers();

    @GetMapping("/findById/{id}")
    User findById(@PathVariable Long id);

    @DeleteMapping("/delete/{id}")
    void deleteUser(@PathVariable Long id);

    @GetMapping("/find-all-users-by-idis")
    List<User> findAllUsersByIdis(@RequestParam Iterable<Long> idis);


}
