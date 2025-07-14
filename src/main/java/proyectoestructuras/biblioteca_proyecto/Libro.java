/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectoestructuras.biblioteca_proyecto;

public class Libro {
    private String titulo, autor, isbn, categoria;
    private boolean disponible = true;
    private int vecesPrestado = 0;

    public Libro(String titulo, String autor, String isbn, String categoria) {
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.categoria = categoria;
    }

    public String getIsbn() { return isbn; }
    public String getTitulo() { return titulo; }
    public String getAutor() { return autor; }
    public String getCategoria() { return categoria; }
    public boolean estaDisponible() { return disponible; }
    public void prestar() { disponible = false; vecesPrestado++; }
    public void devolver() { disponible = true; }
    public int getVecesPrestado() { return vecesPrestado; }
    public void setVecesPrestado(int veces) { this.vecesPrestado = veces; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Libro)) return false;
        Libro l = (Libro) o;
        return isbn.equals(l.isbn);
    }

    @Override
    public int hashCode() {
        return isbn.hashCode();
    }

    @Override
    public String toString() {
        return titulo + " por " + autor + " [" + (disponible ? "Disponible" : "Prestado") + "]";
    }
}