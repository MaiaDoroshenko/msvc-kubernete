package org.maiaDoroshenko_msvc.usuarios.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "usuarios")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    @Column(name = "last-name")
    private String lastName;
    @Email
    @NotBlank
    @Column(unique = true)
    private String email;
    @NotBlank
    @Size(min = 8,max = 15)
    private String password;


}
