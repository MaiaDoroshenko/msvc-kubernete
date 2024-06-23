package org.maiaDoroshenko.msvc.cursos.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "curso-usuario")
public class CursoUsuarioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    @Column(name = "usuario_id",unique = true)
    private Long usuarioId;

    @Override
    public boolean equals(Object obj) {
        if(this == obj){
            return true;
        }
        if(!(obj instanceof CursoUsuarioEntity)){
            return false;
        }
        CursoUsuarioEntity o = (CursoUsuarioEntity) obj;
        return this.usuarioId !=null && this.usuarioId.equals(o.usuarioId);
    }
}
