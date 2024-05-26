package org.ignaciorios.springcloud.msvc.cursos.repositories;

import org.ignaciorios.springcloud.msvc.cursos.models.entity.Curso;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CursoRepositorio extends CrudRepository<Curso,Long>{

    @Modifying
    @Query("delete from CursoUsuario cu where cu.id=?1")
    void eliminarCursoUsuarioPorid(Long id);


}
