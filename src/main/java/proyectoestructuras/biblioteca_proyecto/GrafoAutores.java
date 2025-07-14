/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectoestructuras.biblioteca_proyecto;

import java.util.*;
public class GrafoAutores {
    private final Map<String, Set<String>> relaciones = new HashMap<>();
    private final Map<String, List<Libro>> librosPorAutor = new HashMap<>();

    public void agregarLibro(Libro libro) {
        String autor = libro.getAutor();

       
        librosPorAutor.putIfAbsent(autor, new ArrayList<>());
        librosPorAutor.get(autor).add(libro);

        
        relaciones.putIfAbsent(autor, new HashSet<>());

        
        relaciones.get(autor).add(autor);

        
        for (Libro existente : librosPorAutor.get(autor)) {
            if (!existente.equals(libro)) {
                relaciones.get(autor).add(autor); 
            }
        }

       
        for (String otroAutor : librosPorAutor.keySet()) {
            if (!otroAutor.equals(autor)) {
                for (Libro otroLibro : librosPorAutor.get(otroAutor)) {
                    if (otroLibro.getCategoria().equalsIgnoreCase(libro.getCategoria())) {
                        relacionarAutores(autor, otroAutor);
                        break; 
                    }
                }
            }
        }
    }

    public void relacionarAutores(String a1, String a2) {
        if (!a1.equals(a2)) {
            relaciones.putIfAbsent(a1, new HashSet<>());
            relaciones.putIfAbsent(a2, new HashSet<>());
            relaciones.get(a1).add(a2);
            relaciones.get(a2).add(a1);
        }
    }

    public List<Libro> sugerenciasPorAutor(String autor) {
        List<Libro> sugerencias = new ArrayList<>();
        Set<String> relacionados = relaciones.getOrDefault(autor, new HashSet<>());

        for (String rel : relacionados) {
            List<Libro> lista = librosPorAutor.getOrDefault(rel, new ArrayList<>());
            for (Libro l : lista) {
                if (l.estaDisponible()) {
                    sugerencias.add(l);
                }
            }
        }

        return sugerencias;
    }

   
    public void mostrarRelaciones() {
        for (String autor : relaciones.keySet()) {
            System.out.println("Autor: " + autor + " relacionado con: " + relaciones.get(autor));
        }
    }
}
