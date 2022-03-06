package pro.gsilva.catalogo.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Data
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "Nome não pode ser vazio")
    @Size(max = 30, message = "Nome não pode conter mais que 30 caracteres")
    @Column(length = 30)
    private String nome;
}
