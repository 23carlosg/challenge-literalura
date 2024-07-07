package com.alura.literalura.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true)
    private String titulo;
    @OneToMany(mappedBy = "libro", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Autor> autores = new ArrayList<>();
    @Enumerated(EnumType.STRING)
    private Lenguaje lenguaje;
    private Integer numeroDeDescargas;

    public Libro() {
    }

    public Libro(DatosLibro datosLibro) {
        this.titulo = datosLibro.titulo();
        if (datosLibro.lenguajes() != null && !datosLibro.lenguajes().isEmpty()) {
            this.lenguaje = Lenguaje.fromString(datosLibro.lenguajes().get(0));
        } else {
            this.lenguaje = null;
        }
        this.numeroDeDescargas = Optional.of(Integer.valueOf(datosLibro.numeroDeDescargas())).orElse(0);
    }

    public void addAutor(Autor autor) {
        autores.add(autor);
        autor.setLibro(this);
    }
    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autores) {
        this.autores = autores;
    }

    public Lenguaje getLenguaje() {
        return lenguaje;
    }

    public void setLenguaje(Lenguaje lenguaje) {
        this.lenguaje = lenguaje;
    }

    public Integer getNumeroDeDescargas() {
        return numeroDeDescargas;
    }

    public void setNumeroDeDescargas(Integer numeroDeDescargas) {
        this.numeroDeDescargas = numeroDeDescargas;
    }

    @Override
    public String toString() {
        String salida = "Libro: titulo=" + titulo + '\n' +
                ", autores=" + autores + '\n' +
                ", lenguaje=" + lenguaje + '\n' +
                ", numeroDeDescargas=" + numeroDeDescargas + '\n';

        return salida;
    }
}
