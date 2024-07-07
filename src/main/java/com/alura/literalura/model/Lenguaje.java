package com.alura.literalura.model;

public enum Lenguaje {
    ESPAÑOL("es", "Español"),
    INGLES("en", "Inglés"),
    FRANCES("fr", "Francés"),
    PORTUGUES("pt", "Portugués");

    private String lenguajeGutendex;
    private String lenguajeEspanol;

    Lenguaje (String lenguajeGutendex, String lenguajeEspanol) {
        this.lenguajeGutendex = lenguajeGutendex;
        this.lenguajeEspanol = lenguajeEspanol;
    }

    public static Lenguaje fromString(String text) {
        for (Lenguaje lenguaje : Lenguaje.values()) {
            if (lenguaje.lenguajeGutendex.equalsIgnoreCase(text)) {
                return lenguaje;
            }
        }
        throw new IllegalArgumentException("Ningun lenguaje encontrado: " + text);
    }

    public static Lenguaje fromEspanol(String text) {
        for (Lenguaje lenguaje : Lenguaje.values()) {
            if (lenguaje.lenguajeEspanol.equalsIgnoreCase(text)) {
                return lenguaje;
            }
        }
        throw new IllegalArgumentException("Ningun lenguaje encontrado: " + text);
    }
}
