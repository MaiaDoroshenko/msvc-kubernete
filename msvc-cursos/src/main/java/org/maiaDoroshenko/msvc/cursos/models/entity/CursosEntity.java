package org.maiaDoroshenko.msvc.cursos.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.maiaDoroshenko.msvc.cursos.models.User;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@Builder
@Table(name = "cursos")
public class CursosEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String name;
    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "curso_id")
    private List<CursoUsuarioEntity> cursoUsuarios;
    @Transient
    private List<User>usuarios;




    public CursosEntity(){
        cursoUsuarios=new ArrayList<>();
        usuarios=new ArrayList<>();
    }

    public void addCursoUsuario(CursoUsuarioEntity cursoUsuario){
        cursoUsuarios.add(cursoUsuario);
    }
    public void removeCursoUsuario(CursoUsuarioEntity cursoUsuario){
        cursoUsuarios.remove(cursoUsuario);
    }

}
