package pro.gsilva.catalogo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class MusicaDTO {

    private Long id;

    @NotBlank
    private String autor;

    @NotBlank
    private String titulo;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", locale = "UTC-03")
    private LocalDate data;

    @NotBlank
    private String letra;

    @NotNull
    private Long categoriaId;
}
