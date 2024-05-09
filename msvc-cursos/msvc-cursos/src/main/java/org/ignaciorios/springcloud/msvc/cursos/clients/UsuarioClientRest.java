package org.ignaciorios.springcloud.msvc.cursos.clients;

import org.ignaciorios.springcloud.msvc.cursos.models.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name="msvc-usuarios",url="localhost:8001")
public interface UsuarioClientRest {

    @GetMapping("/{id}")
    public Usuario detalle(@PathVariable Long id);
    @PostMapping("/")
    public  Usuario crear(@RequestBody Usuario usuario);

@GetMapping
List<Usuario> obtenerAlumnosPorCurso(@RequestParam Iterable<Long> ids);

}
