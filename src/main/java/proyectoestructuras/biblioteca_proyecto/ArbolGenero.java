/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectoestructuras.biblioteca_proyecto;

import java.util.*;

class NodoGenero {
    String genero;
    TreeSet<Libro> libros;
    NodoGenero izq, der;

    public NodoGenero(String genero) {
        this.genero = genero;
        this.libros = new TreeSet<>((l1, l2) -> {
            int cmp = Integer.compare(l2.getVecesPrestado(), l1.getVecesPrestado());
            return cmp != 0 ? cmp : l1.getIsbn().compareTo(l2.getIsbn());
        });
    }

    public void agregarLibro(Libro l) {
        libros.remove(l);
        libros.add(l);
    }

    public void mostrar() {
        System.out.println("Género: " + genero);
        for (Libro l : libros) System.out.println(" - " + l + " | Veces prestado: " + l.getVecesPrestado());
    }
}

public class ArbolGenero {
    private NodoGenero raiz;

    public void insertarLibro(Libro libro) {
        raiz = insertarRec(raiz, libro);
    }

    private NodoGenero insertarRec(NodoGenero nodo, Libro l) {
        if (nodo == null) {
            nodo = new NodoGenero(l.getCategoria());
            nodo.agregarLibro(l);
            return nodo;
        }
        int cmp = l.getCategoria().compareTo(nodo.genero);
        if (cmp < 0) nodo.izq = insertarRec(nodo.izq, l);
        else if (cmp > 0) nodo.der = insertarRec(nodo.der, l);
        else nodo.agregarLibro(l);
        return nodo;
    }

    public void mostrarTodo() {
        mostrarRec(raiz);
    }

    private void mostrarRec(NodoGenero nodo) {
        if (nodo == null) return;
        mostrarRec(nodo.izq);
        nodo.mostrar();
        mostrarRec(nodo.der);
    }
}