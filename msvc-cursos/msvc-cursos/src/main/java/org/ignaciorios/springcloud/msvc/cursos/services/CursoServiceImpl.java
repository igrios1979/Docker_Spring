package org.ignaciorios.springcloud.msvc.cursos.services;

import org.ignaciorios.springcloud.msvc.cursos.clients.UsuarioClientRest;
import org.ignaciorios.springcloud.msvc.cursos.models.Usuario;
import org.ignaciorios.springcloud.msvc.cursos.models.entity.Curso;
import org.ignaciorios.springcloud.msvc.cursos.models.entity.CursoUsuario;
import org.ignaciorios.springcloud.msvc.cursos.repositories.CursoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CursoServiceImpl implements CursoService{

    @Autowired
    private CursoRepositorio repository;
    @Autowired
    private UsuarioClientRest cliente;

    @Override
    @Transactional(readOnly = true)
    public List<Curso> listar() {
      return (List<Curso>) repository.findAll();

    }
    @Override
    @Transactional(readOnly = true)
    public Optional<Curso> porId(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Curso guardar(Curso curso) {
        return (Curso) repository.save(curso);

    }
    @Override
    @Transactional
    public void eliminar(Long id) {
        repository.deleteById(id);

    }

    @Override
    public Optional<Usuario> asignarUsuario(Usuario usuario, Long cursoId) {
        // Busca el curso por ID
        Optional<Curso> optionalCurso = repository.findById(cursoId);

        // Verifica si el curso existe
        if (optionalCurso.isPresent()) {
            // Obtiene detalles del usuario desde otro microservice
            Usuario usuarioMsvc = cliente.detalle(usuario.getId());

            // Obtiene el curso de la respuesta Optional
            Curso curso = optionalCurso.get();

            // Crea una nueva entidad CursoUsuario para relacionar usuario con curso
            CursoUsuario cursoUsuario = new CursoUsuario();

            // Configura el ID del usuario, probablemente debería ser configurado de otra forma, como el usuario completo o su ID
            cursoUsuario.setId(usuarioMsvc.getId());

            // Agrega la relación a la colección del curso
            curso.addCursoUsuario(cursoUsuario);

            // Persiste los cambios en la base de datos
            repository.save(curso);

            // Retorna el usuario envuelto en un Optional si todo fue exitoso
            return Optional.of(usuarioMsvc);
        }

        // Retorna un Optional vacío si el curso no fue encontrado
        return Optional.empty();
    }


    @Override
    public Optional<Usuario> crearUsuario(Usuario usuario, Long cursoId) {
        return Optional.empty();
    }

    @Override
    public Optional<Usuario> eliminarUsuario(Usuario usuario, Long cursoId) {
        return Optional.empty();
    }
}
