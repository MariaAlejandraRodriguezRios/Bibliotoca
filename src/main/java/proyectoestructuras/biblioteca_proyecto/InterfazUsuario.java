/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectoestructuras.biblioteca_proyecto;

import javax.swing.*;
import java.awt.*;
import java.util.Stack;

public class InterfazUsuario extends JFrame {
    private final GestorBiblioteca gestor;
    private final Usuario usuario;

    public InterfazUsuario(GestorBiblioteca gestor, Usuario usuario) {
        this.gestor = gestor;
        this.usuario = usuario;

        setTitle("Usuario: " + usuario.getNombre());
        setSize(400, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(6, 1, 10, 10));

        JButton btnVerLibros = new JButton("📚 Ver todos los libros");
        JButton btnPrestar = new JButton("🔄 Pedir prestado un libro");
        JButton btnDevolver = new JButton("📥 Devolver libro");
        JButton btnHistorial = new JButton("🕓 Ver historial");
        JButton btnSugerencias = new JButton("🤝 Sugerencias por autor");
        JButton btnSalir = new JButton("❌ Cerrar sesión");

        panel.add(btnVerLibros);
        panel.add(btnPrestar);
        panel.add(btnDevolver);
        panel.add(btnHistorial);
        panel.add(btnSugerencias);
        panel.add(btnSalir);

        add(panel);

        // Acciones
        btnVerLibros.addActionListener(e -> mostrarLibros());
        btnPrestar.addActionListener(e -> prestarLibro());
        btnDevolver.addActionListener(e -> devolverLibro());
        btnHistorial.addActionListener(e -> mostrarHistorial());
        btnSugerencias.addActionListener(e -> mostrarSugerencias());
        btnSalir.addActionListener(e -> {
            new PantallaInicio(gestor);
            dispose();
        });

        setVisible(true);
    }

    private void mostrarLibros() {
        StringBuilder sb = new StringBuilder();
        for (Libro l : gestor.getTodosLosLibros()) {
            sb.append(l).append("\n");
        }
        mostrarMensaje("Libros disponibles", sb.toString());
    }

    private void prestarLibro() {
        String isbn = JOptionPane.showInputDialog(this, "Ingrese ISBN del libro:");
        if (isbn == null || isbn.isEmpty()) return;
        boolean ok = gestor.prestarLibro(isbn, usuario.getId());
        if (ok) {
            mostrarMensaje("Préstamo exitoso", "Libro prestado correctamente.");
        } else {
            mostrarMensaje("No disponible", "Libro no disponible. Se agregó a la cola de espera.");
        }
    }

    private void devolverLibro() {
        Libro siguiente = usuario.removerLibroParaDevolver();  // saca el primero prestado (FIFO)
        if (siguiente == null) {
            mostrarMensaje("Sin libros", "No hay libros para devolver.");
            return;
        }

        boolean ok = gestor.devolverLibro(siguiente.getIsbn());
        if (ok) {
            mostrarMensaje("Devolución", "Libro devuelto: " + siguiente.getTitulo());
        } else {
            mostrarMensaje("Error", "No se pudo devolver el libro.");
        }
    }

    private void mostrarHistorial() {
        StringBuilder sb = new StringBuilder("Historial de libros:\n\n");
        Stack<Libro> historial = usuario.getHistorial();

        for (int i = historial.size() - 1; i >= 0; i--) {
            sb.append(historial.get(i)).append("\n");
        }

        mostrarMensaje("Historial de libros", sb.toString());
    }

    private void mostrarSugerencias() {
        Libro ultimo = usuario.ultimoLibro();
        if (ultimo == null) {
            mostrarMensaje("Sin libros", "Aún no has leído ningún libro.");
            return;
        }
        java.util.List<Libro> sugerencias = gestor.grafo.sugerenciasPorAutor(ultimo.getAutor());
        if (sugerencias.isEmpty()) {
            mostrarMensaje("Sugerencias", "No hay sugerencias por este autor.");
        } else {
            StringBuilder sb = new StringBuilder();
            for (Libro l : sugerencias) sb.append(l).append("\n");
            mostrarMensaje("Sugerencias basadas en autor", sb.toString());
        }
    }

    private void mostrarMensaje(String titulo, String mensaje) {
        JTextArea textArea = new JTextArea(mensaje);
        textArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(textArea);
        scroll.setPreferredSize(new Dimension(400, 300));
        JOptionPane.showMessageDialog(this, scroll, titulo, JOptionPane.INFORMATION_MESSAGE);
    }
}

