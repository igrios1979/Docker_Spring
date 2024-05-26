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
import java.util.stream.Collectors;

@Service
public class CursoServiceImpl implements CursoService {

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
    @Transactional(readOnly = true)
    public Optional<Curso> porIdConcuss(Long id) {
        Optional<Curso> o = repository.findById(id);
        if(o.isPresent()){
            Curso curso = o.get();
             if(!curso.getCursosUsuarios().isEmpty()){
                 List<Long> ids = curso.getCursosUsuarios().stream().map(cu->cu.getUsId())
                                  .collect(Collectors.toList());

                 List<Usuario> usuarios = cliente.obtenerAlumnosPorCurso(ids);
                 curso.setUsuarios(usuarios);

             }

            return Optional.of(curso);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Curso guardar(Curso curso) {
        return repository.save(curso);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public void eliminarCursoUsuarioPorId(Long id) {
        repository.eliminarCursoUsuarioPorid(id);

    }

    @Override
    @Transactional
    public Optional<Usuario> asignarUsuario(Usuario usuario, Long cursoId) {
        Optional<Curso> cursoOpt = repository.findById(cursoId);
        if (cursoOpt.isPresent()) {
            Usuario usuarioMsvc = cliente.detalle(usuario.getId());
            Curso curso = cursoOpt.get();
            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsId(usuarioMsvc.getId());
            curso.addCursoUsuario(cursoUsuario);
            repository.save(curso);
            return Optional.of(usuarioMsvc);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Usuario> crearUsuario(Usuario usuario, Long cursoId) {
        Optional<Curso> cursoOpt = repository.findById(cursoId);
        if (cursoOpt.isPresent()) {
            Usuario usuarioNuevo = cliente.crear(usuario);
            Curso curso = cursoOpt.get();
            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsId(usuarioNuevo.getId());
            curso.addCursoUsuario(cursoUsuario);
            repository.save(curso);
            return Optional.of(usuarioNuevo);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Usuario> eliminarUsuario(Usuario usuario, Long cursoId) {
        Optional<Curso> cursoOpt = repository.findById(cursoId);
        if (cursoOpt.isPresent()) {
            Usuario usuarioMsvc = cliente.detalle(usuario.getId());
            Curso curso = cursoOpt.get();
            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsId(usuarioMsvc.getId());
            curso.removeCursoUsuario(cursoUsuario);
            repository.save(curso);
            return Optional.of(usuarioMsvc);
        }
        return Optional.empty();
    }
}
