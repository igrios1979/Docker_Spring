package org.ignaciorios.springcloud.msvc.cursos.Entity;

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
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CursoUsuario)) {
            return false;
        }
        CursoUsuario o = (CursoUsuario) obj;
        return this.UsId != null && this.UsId.equals(o.UsId);

    }
}
