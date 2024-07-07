package com.alura.literalura.model;

import com.alura.literalura.repository.LibroRepository;
import com.alura.literalura.service.ConsumoAPI;
import com.alura.literalura.service.ConvierteDatos;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {

    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books";
    private ConvierteDatos conversor = new ConvierteDatos();
    private LibroRepository repositorio;
    private List<Libro> libros;
    private List<Autor> autores;

    public Principal(LibroRepository repositorio) {
        this.repositorio = repositorio;
    }

    public void muestraElMenu() {
        int opcion = -1;
        while (opcion != 0) {
            var menu = """
                    Elija la opcion a traves de su numero:
                    1- Buscar libro por titulo
                    2- Listar libros registrados
                    3- Listar autores registrados
                    4- Listar autores vivos en un determinado año
                    5- Listar libros por idioma     
                    0- Salir
                    """;
            System.out.println(menu);

            try {
                opcion = Integer.parseInt(teclado.nextLine());

            switch (opcion) {
                case 1:
                    guardarPrimerLibro();
                    break;
                case 2:
                    listarLibrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivosEnAnio();
                    break;
                case 5:
                    listarLibrosPorLenguaje();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un número.");
                opcion = -1;
            }
        }

    }

    private DatosLibro getDatosLibro() {
        System.out.println("Ingrese el nombre del libro que desea buscar");
        var nombreLibro = teclado.nextLine();
        String url = URL_BASE + "?search=" + nombreLibro.replace(" ", "+");
        System.out.println("URL: " + url);

        var json = consumoApi.obtenerDatos(url);
        System.out.println("Verificar: " + json);

        DatosAPI respuestaAPI = conversor.obtenerDatos(json, DatosAPI.class);

        if (respuestaAPI.resultados() != null && !respuestaAPI.resultados().isEmpty()) {
            DatosLibro primerLibro = respuestaAPI.resultados().get(0);
            return primerLibro;
        } else {
            System.out.println("No se encontraron libros para el título ingresado.");
            return null;
        }
    }

    private void guardarPrimerLibro() {
        DatosLibro datosLibro = getDatosLibro();

        if (datosLibro != null) {

            if (repositorio.existsByTitulo(datosLibro.titulo())) {
                System.out.println("El libro con título '" + datosLibro.titulo() + "' ya está registrado.");
                return;
            }

            Libro libro = new Libro(datosLibro);

            List<Autor> autores = datosLibro.autores().stream()
                    .map(datosAutor -> {
                        Autor autor = new Autor(datosAutor);
                        libro.addAutor(autor);
                        return autor;
                    }).collect(Collectors.toList());

            autores.forEach(libro::addAutor);

            repositorio.save(libro);
            System.out.println("----- LIBRO -----\n");
            System.out.println("Título: " + libro.getTitulo() + "\n");
            System.out.println("Autor: " + libro + "\n");
            System.out.println("Idioma: " + libro.getLenguaje() + "\n");
            System.out.println("Número de descargas: " + libro.getNumeroDeDescargas() + "\n");
            System.out.println("-----------------\n");
        }
    }

    private void listarLibrosRegistrados() {
        libros = repositorio.findAll();
        libros.forEach(libro -> System.out.println(libro.toString()));
    }

    private void listarAutoresRegistrados() {
        List<Libro> libros = repositorio.findAll();

        libros.forEach(libro -> {
            List<Autor> autores = libro.getAutores();
            autores.forEach(autor -> System.out.println("Autor: " + autor.toString()));
        });
    }

    public void listarAutoresVivosEnAnio() {
        System.out.println("Ingrese el año para listar autores vivos:");
        int anno = teclado.nextInt();
        teclado.nextLine();

        List<Autor> autores = repositorio.findAutoresVivosEnAnno(anno);

        if (autores.isEmpty()) {
            System.out.println("No se encontraron autores vivos en el año " + anno);
        } else {
            System.out.println("Autores vivos en el año " + anno + ":");
            autores.forEach(autor -> System.out.println(autor.toString()));
        }
    }

    public void listarLibrosPorLenguaje() {
        Scanner teclado = new Scanner(System.in);
        System.out.println("Ingrese el idioma para listar los libros:");
        String lenguaje = teclado.nextLine().trim();

        List<Libro> libros = repositorio.findAll();

        List<Libro> librosPorIdioma = libros.stream()
                .filter(libro -> libro.getLenguaje().toString().equalsIgnoreCase(lenguaje))
                .collect(Collectors.toList());

        if (librosPorIdioma.isEmpty()) {
            System.out.println("No se encontraron libros en el idioma " + lenguaje);
        } else {
            System.out.println("Libros en el idioma " + lenguaje + ":");
            librosPorIdioma.forEach(libro -> System.out.println(libro.toString()));
        }
    }
}
