package pro.gsilva.catalogo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pro.gsilva.catalogo.model.Categoria;

import java.util.Optional;

public interface CategoriaService {
    Page<Categoria> findAll(Pageable pageable);

    void save(Categoria categoria);

    Optional<Categoria> findById(Long id);

    void deleteById(Long id);
}
