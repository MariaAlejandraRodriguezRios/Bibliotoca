/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectoestructuras.biblioteca_proyecto;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class Usuario {
    private final int id;
    private final String nombre;
    private final Stack<Libro> historial = new Stack<>();
    private final Queue<Libro> prestadosActivos = new LinkedList<>();

    public Usuario(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() { return id; }

    public String getNombre() { return nombre; }

    public Stack<Libro> getHistorial() { return historial; }

    public Queue<Libro> getPrestadosActivos() { return prestadosActivos; }

    public void agregarLibroHistorial(Libro l) {
        historial.push(l);
        prestadosActivos.offer(l);  // También se agrega a la cola de préstamos activos
    }

    public Libro ultimoLibro() {
        return historial.isEmpty() ? null : historial.peek();
    }

    public Libro deshacerPrestamo() {
        return historial.isEmpty() ? null : historial.pop();
    }

    public Libro siguienteParaDevolver() {
        return prestadosActivos.peek();
    }

    public Libro removerLibroParaDevolver() {
        return prestadosActivos.poll();
    }

    public List<Libro> librosPrestadosActuales() {
        List<Libro> actuales = new ArrayList<>();
        for (Libro l : prestadosActivos) {
            if (!l.estaDisponible()) {
                actuales.add(l);
            }
        }
        return actuales;
    }
}



