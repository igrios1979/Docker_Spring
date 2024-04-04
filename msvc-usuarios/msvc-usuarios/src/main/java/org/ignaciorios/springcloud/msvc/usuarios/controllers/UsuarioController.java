package org.ignaciorios.springcloud.msvc.usuarios.controllers;


import org.ignaciorios.springcloud.msvc.usuarios.Services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.ignaciorios.springcloud.msvc.usuarios.models.entity.Usuario;
import jakarta.validation.Valid;

import java.util.*;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @GetMapping
    public List<Usuario> listar() {

        return service.listar();

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable(name = "id") Long id) {
        Optional<Usuario> usuarioOptional = service.porId(id);
        if (usuarioOptional.isPresent()) {
            return ResponseEntity.ok(usuarioOptional.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Usuario usuario, BindingResult result) {
        if (result.hasErrors()) {
            return validation2(result);
        } else {
            // Verificamos si ya existe un usuario con el mismo correo electrónico
            Optional<Usuario> usuarioExistente = service.porEmail(usuario.getEmail());
            if (usuarioExistente.isPresent()) {
                return ResponseEntity.badRequest().body(Collections.singletonMap("mensaje", "Ya existe un usuario con el correo proporcionado"));
            }

            // Si no hay errores de validación y no hay usuario existente con el mismo correo electrónico, intentamos guardar el usuario
            Usuario usuarioGuardado = service.guardar(usuario);

            // Verificamos si el usuario se guardó correctamente
            if (usuarioGuardado != null) {
                // Si se guardó correctamente, devolvemos un ResponseEntity con estado CREATED
                // y el usuario guardado en el cuerpo de la respuesta
                return ResponseEntity.status(HttpStatus.CREATED).body(usuarioGuardado);
            } else {
                // Si no se pudo guardar el usuario, devolvemos un ResponseEntity con estado INTERNAL_SERVER_ERROR
                // y un mensaje de error en el cuerpo de la respuesta
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se pudo guardar el usuario");
            }
        }
    }

    private ResponseEntity<?> validation2(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }



    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Usuario usuario, BindingResult result, @PathVariable Long id) {
        if (result.hasErrors()) {
            return validation(result);
        } else {
            Optional<Usuario> o = service.porId(id);
            if (o.isPresent()) {
                Usuario usuarioDB = o.get();
                // Verificamos si el correo electrónico no está vacío y si es diferente del correo electrónico actual del usuarioDB
                if (!usuario.getEmail().isEmpty() && !usuario.getEmail().equals(usuarioDB.getEmail()) && service.porEmail(usuario.getEmail()).isPresent()) {
                    return ResponseEntity.badRequest().body(Collections.singletonMap("mensaje", "Ya existe un usuario con el correo proporcionado"));
                }
                usuarioDB.setNombre(usuario.getNombre());
                usuarioDB.setEmail(usuario.getEmail());
                usuarioDB.setPassword(usuario.getPassword());
                return ResponseEntity.ok().body(service.guardar(usuarioDB)); // Devuelve un ResponseEntity con estado OK
            }
            return ResponseEntity.notFound().build(); // Devuelve un ResponseEntity con estado NOT_FOUND
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable long id) {

        Optional<Usuario> o = service.porId(id);
        if (o.isPresent()) {
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();

    }



    private static ResponseEntity<Map<String, String>> validation(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }
}
