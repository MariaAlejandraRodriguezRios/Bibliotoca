
package proyectoestructuras.biblioteca_proyecto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class InterfazGrafica extends JFrame {

    public static void iniciar(GestorBiblioteca gestor) {
    SwingUtilities.invokeLater(() -> new InterfazGrafica(gestor));
}

    final GestorBiblioteca gestor;

    public InterfazGrafica(GestorBiblioteca gestor) {
        this.gestor = gestor;

        setTitle("Sistema de Biblioteca");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centra la ventana

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 10, 10));

        JButton btnVerLibros = new JButton(" Ver todos los libros");
        JButton btnPrestar = new JButton(" Prestar libro");
        JButton btnHistorial = new JButton(" Ver historial de usuario");
        JButton btnPopularidad = new JButton("Libros por popularidad");
        JButton btnSalir = new JButton(" Salir");

        panel.add(btnVerLibros);
        panel.add(btnPrestar);
        panel.add(btnHistorial);
        panel.add(btnPopularidad);
        panel.add(btnSalir);

        add(panel);

        // Acciones
        btnVerLibros.addActionListener(e -> mostrarLibros());
        btnPrestar.addActionListener(e -> prestarLibro());
        btnHistorial.addActionListener(e -> mostrarHistorial());
        btnPopularidad.addActionListener(e -> mostrarPopularidad());
        btnSalir.addActionListener(e -> System.exit(0));

        setVisible(true);
    }

    private void mostrarLibros() {
        StringBuilder sb = new StringBuilder("Lista de libros:\n\n");
        for (Libro l : gestor.getTodosLosLibros()) {
            sb.append(l).append("\n");
        }
        mostrarMensaje("Libros disponibles", sb.toString());
    }

    private void prestarLibro() {
        String isbn = JOptionPane.showInputDialog(this, "Ingrese ISBN:");
        String idStr = JOptionPane.showInputDialog(this, "Ingrese ID de usuario:");
        try {
            int id = Integer.parseInt(idStr);
            boolean exito = gestor.prestarLibro(isbn, id);
            if (exito) {
                mostrarMensaje("Préstamo exitoso", "Libro prestado correctamente.");
            } else {
                mostrarMensaje("No disponible", "El libro no está disponible. Te añadimos a la cola.");
            }
        } catch (Exception e) {
            mostrarMensaje("Error", "ID inválido.");
        }
    }

    private void mostrarHistorial() {
        String idStr = JOptionPane.showInputDialog(this, "Ingrese ID de usuario:");
        try {
            int id = Integer.parseInt(idStr);
            Usuario u = gestor.getUsuario(id);
            if (u == null) {
                mostrarMensaje("Error", "Usuario no encontrado.");
                return;
            }
            StringBuilder sb = new StringBuilder("Historial de " + u.getNombre() + ":\n\n");
            for (Libro l : u.getHistorial()) sb.append(l).append("\n");
            mostrarMensaje("Historial", sb.toString());
        } catch (Exception e) {
            mostrarMensaje("Error", "ID inválido.");
        }
    }

    private void mostrarPopularidad() {
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        // Captura la salida estándar
        PrintStream originalOut = System.out;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));
        gestor.mostrarArbolGenero();
        System.setOut(originalOut);
        textArea.setText(baos.toString());

        JScrollPane scroll = new JScrollPane(textArea);
        scroll.setPreferredSize(new Dimension(400, 300));
        JOptionPane.showMessageDialog(this, scroll, "Popularidad por género", JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarMensaje(String titulo, String mensaje) {
        JTextArea textArea = new JTextArea(mensaje);
        textArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(textArea);
        scroll.setPreferredSize(new Dimension(400, 300));
        JOptionPane.showMessageDialog(this, scroll, titulo, JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        GestorBiblioteca gestor = new GestorBiblioteca();
        CargadorCSV.cargarLibrosCSV("libros.csv", gestor);
        CargadorCSV.cargarUsuariosCSV("usuarios.csv", gestor);
        new InterfazGrafica(gestor);
    }
}

