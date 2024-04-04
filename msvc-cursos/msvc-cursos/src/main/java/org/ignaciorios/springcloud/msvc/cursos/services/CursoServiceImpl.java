package org.ignaciorios.springcloud.msvc.cursos.services;

import org.ignaciorios.springcloud.msvc.cursos.Entity.Curso;
import org.ignaciorios.springcloud.msvc.cursos.repositories.CursoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CursoServiceImpl implements CursoService{

    @Autowired
    private CursoRepositorio repository;

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
}
