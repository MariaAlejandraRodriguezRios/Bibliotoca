/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectoestructuras.biblioteca_proyecto;

import java.util.*;

public class GestorBiblioteca {
    private Map<String, Libro> libros = new HashMap<>();
    private Map<Integer, Usuario> usuarios = new HashMap<>();
    private List<Libro> listaLibros = new LinkedList<>();
    private Map<String, Queue<Usuario>> colasEspera = new HashMap<>();
    private ArbolGenero arbol = new ArbolGenero();
    GrafoAutores grafo = new GrafoAutores();

    public void agregarLibro(Libro l) {
        libros.put(l.getIsbn(), l);
        listaLibros.add(l);
        colasEspera.put(l.getIsbn(), new LinkedList<>());
        arbol.insertarLibro(l);
        grafo.agregarLibro(l);
    }

    public void registrarUsuario(Usuario u) {
        usuarios.put(u.getId(), u);
    }

    public boolean prestarLibro(String isbn, int id) {
        Libro l = libros.get(isbn);
        Usuario u = usuarios.get(id);
        if (l == null || u == null) return false;
        if (l.estaDisponible()) {
            l.prestar();
            u.agregarLibroHistorial(l);
            return true;
        } else {
            colasEspera.get(isbn).add(u);
            return false;
        }
    }

    public boolean devolverLibro(String isbn) {
        Libro l = libros.get(isbn);
        if (l == null) return false;
        Queue<Usuario> cola = colasEspera.get(isbn);
        if (cola != null && !cola.isEmpty()) {
            Usuario siguiente = cola.poll();
            l.prestar();
            siguiente.agregarLibroHistorial(l);
        } else {
            l.devolver();
        }
        return true;
    }

    public void mostrarTodosLosLibros() {
        for (Libro l : listaLibros) System.out.println(l);
    }

    public void mostrarColasDeEspera() {
        for (String isbn : colasEspera.keySet()) {
            Queue<Usuario> cola = colasEspera.get(isbn);
            if (!cola.isEmpty()) {
                System.out.println("Cola para " + isbn + ":");
                for (Usuario u : cola) System.out.println(" - " + u.getNombre());
            }
        }
    }

    public void mostrarHistorialUsuario(int id) {
    Usuario u = usuarios.get(id);
    if (u == null) return;

    Stack<Libro> historial = u.getHistorial();
    for (int i = historial.size() - 1; i >= 0; i--) {
        System.out.println(" - " + historial.get(i));
    }
}
    

    public void mostrarSugerenciasAutor(int id) {
        Usuario u = usuarios.get(id);
        if (u == null) return;
        Libro ult = u.ultimoLibro();
        if (ult == null) return;
        List<Libro> sugerencias = grafo.sugerenciasPorAutor(ult.getAutor());
        for (Libro l : sugerencias) System.out.println(" - " + l);
    }

    public Usuario getUsuario(int id) { return usuarios.get(id); }
    public List<Libro> getTodosLosLibros() { return listaLibros; }
    public void mostrarArbolGenero() { arbol.mostrarTodo(); }

    
}

