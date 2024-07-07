package com.alura.literalura.repository;

import com.alura.literalura.model.Autor;
import com.alura.literalura.model.Lenguaje;
import com.alura.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro,Long> {

    @Query("SELECT DISTINCT a FROM Libro l JOIN l.autores a WHERE (a.fechaNacimiento <= :anno AND (a.fechaFallecimiento >= :anno OR a.fechaFallecimiento IS NULL))")
    List<Autor> findAutoresVivosEnAnno(int anno);

    boolean existsByTitulo(String titulo);
}
