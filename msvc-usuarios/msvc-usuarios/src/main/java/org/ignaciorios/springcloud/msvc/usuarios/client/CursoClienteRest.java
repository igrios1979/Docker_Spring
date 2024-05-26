package org.ignaciorios.springcloud.msvc.usuarios.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="msvc-cursos",url="localhost:8082")
public interface CursoClienteRest {

    @DeleteMapping("/eliminar-curso-usuario/{id}")
   void   eliminarCursoUsuarioPorId(@PathVariable Long id);





}