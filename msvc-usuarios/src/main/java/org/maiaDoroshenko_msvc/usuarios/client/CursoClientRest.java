package org.maiaDoroshenko_msvc.usuarios.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
    @FeignClient(name = "msvc-cursos", url = "host.docker.internal:8002")
    public interface CursoClientRest {
        @DeleteMapping("/curso/delete-user-by-id/{id}")
        void deleteUserById(@PathVariable("id") Long id);

}
