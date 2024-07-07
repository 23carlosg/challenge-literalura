package com.alura.literalura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String nombre;
    private Integer fechaNacimiento;
    private Integer fechaFallecimiento;
    @ManyToOne
    @JoinColumn(name = "libro_id")
    private Libro libro;

    public Autor() {
    }

    public Autor(DatosAutor datosAutor) {
        this.nombre = datosAutor.nombre();
        try {
            this.fechaNacimiento = Integer.valueOf(datosAutor.fechaNacimiento());
        } catch (NumberFormatException | NullPointerException e) {
            this.fechaNacimiento = null;
        }
        try {
            this.fechaFallecimiento = Integer.valueOf(datosAutor.fechaFallecimiento());
        } catch (NumberFormatException | NullPointerException e) {
            this.fechaFallecimiento = null;
        }
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer  getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Integer  fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Integer  getFechaFallecimiento() {
        return fechaFallecimiento;
    }

    public void setFechaFallecimiento(Integer  fechaFallecimiento) {
        this.fechaFallecimiento = fechaFallecimiento;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    @Override
    public String toString() {
        return "nombre='" + nombre + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", fechaFallecimiento=" + fechaFallecimiento;
    }
}
