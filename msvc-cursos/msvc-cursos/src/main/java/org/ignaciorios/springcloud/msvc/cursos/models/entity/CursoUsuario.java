package org.ignaciorios.springcloud.msvc.cursos.models.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "cursos_usuarios")
public class CursoUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario_id", unique = true)
    private Long UsId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsId() {
        return UsId;
    }

    public void setUsId(Long usId) {
        UsId = usId;
    }

    @Override
    public boolean equals(Object obj) {
        // Verifica si los dos objetos son la misma instancia en la memoria
        if (this == obj) {
            return true;
        }

        // Verifica si el objeto pasado como argumento es una instancia de la clase CursoUsuario
        if (!(obj instanceof CursoUsuario)) {
            return false;
        }

        // Convierte el objeto pasado como argumento en un objeto de la clase CursoUsuario
        CursoUsuario o = (CursoUsuario) obj;

        // Compara los atributos de los dos objetos para determinar si son iguales
        // En este caso, compara el atributo UsId de ambos objetos
        // Devuelve true si el atributo UsId de ambos objetos no es nulo y son iguales
        return this.UsId != null && this.UsId.equals(o.UsId);
    }

}
