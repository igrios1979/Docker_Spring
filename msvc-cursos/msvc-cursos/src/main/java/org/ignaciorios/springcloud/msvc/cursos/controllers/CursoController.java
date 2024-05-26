package org.ignaciorios.springcloud.msvc.cursos.controllers;


import feign.FeignException;
import jakarta.validation.Valid;
import org.ignaciorios.springcloud.msvc.cursos.models.Usuario;
import org.ignaciorios.springcloud.msvc.cursos.models.entity.Curso;
import org.ignaciorios.springcloud.msvc.cursos.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class CursoController {

    @Autowired
    private CursoService service;

    @GetMapping
    public ResponseEntity<List<Curso>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id) {
        Optional<Curso> o = service.porIdConcuss(id);
        if (o.isPresent()) {
            return ResponseEntity.ok(o.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/")
    public ResponseEntity<?> crear(@Valid @RequestBody Curso curso, BindingResult result) {
        ResponseEntity<?> response = validarYProcesarResultados(result);
        if (response != null) {
            return response; // Si hay errores de validación, devolver el ResponseEntity con los errores
        }

        Curso cursoDb = service.guardar(curso);
        return ResponseEntity.status(HttpStatus.CREATED).body(cursoDb);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Curso curso, BindingResult result, @PathVariable Long id) {
        ResponseEntity<?> response = validarYProcesarResultados(result);
        if (response != null) {
            return response; // Si hay errores de validación, devolver el ResponseEntity con los errores
        }

        Optional<Curso> o = service.porId(id);
        if (o.isPresent()) {
            Curso cursoDB = o.get();
            cursoDB.setNombre(curso.getNombre());
            return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(cursoDB));
        }
        return ResponseEntity.notFound().build();
    }

    // Otros métodos del controlador...

@PutMapping("/asignar-usuario/{cursoId}")
private ResponseEntity<?> asignarUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId){

    Optional<Usuario> o ;
            try{
            o =    service.asignarUsuario(usuario, cursoId);
            }catch(FeignException f){
                return ResponseEntity.
                        status(HttpStatus.NOT_FOUND).
                        body(Collections.singletonMap("Mensaje","no existe usuario por " +
                                "ID o error communication: " + f.getMessage()));
            }
      if(o.isPresent())
      {
          return ResponseEntity.status(HttpStatus.CREATED).body(o.get());

      }
      return ResponseEntity.notFound().build();

}

    @DeleteMapping("/eliminar-usuario/{cursoId}")
    private ResponseEntity<?> eliminarUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId){

        Optional<Usuario> o = null;
        try{
            o =    service.eliminarUsuario(usuario, cursoId);
        }catch(FeignException f){
            return ResponseEntity.
                    status(HttpStatus.NOT_FOUND).
                    body(Collections.singletonMap("Mensaje","no existe usuario por " +
                            "ID o error communication: " + f.getMessage()));
        }
        if(o.isPresent())
        {
            return ResponseEntity.status(HttpStatus.OK).body(o.get());

        }
        return ResponseEntity.notFound().build();

    }



    @PostMapping("/crear-usuario/{cursoId}")
    private ResponseEntity<?> crearUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId){

        Optional<Usuario> o ;
        try{
            o =    service.crearUsuario(usuario, cursoId);
        }catch(FeignException f){
            return ResponseEntity.
                    status(HttpStatus.NOT_FOUND).
                    body(Collections.singletonMap("Mensaje","no se pudo crear usuario  " +
                            " o error communication: " + f.getMessage()));
        }
        if(o.isPresent())
        {
            return ResponseEntity.status(HttpStatus.CREATED).body(o.get());

        }
        return ResponseEntity.notFound().build();

    }





    private ResponseEntity<?> validarYProcesarResultados(BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(err -> {
                errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
            });
            return ResponseEntity.badRequest().body(errores); // Devolver un ResponseEntity con errores de validación
        }
        return null; // No hay errores de validación
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id ){
        Optional<Curso> o = service.porId(id);
        if(o.isPresent()){
            service.eliminar(o.get().getId());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();

}

@DeleteMapping("/eliminar-curso-usuario/{id}")
public ResponseEntity<?> eliminarCursoUsuarioPorId(@PathVariable Long id){
        service.eliminarCursoUsuarioPorId(id);
        return ResponseEntity.noContent().build();

}




}
