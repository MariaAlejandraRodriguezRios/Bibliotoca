/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectoestructuras.biblioteca_proyecto;

import javax.swing.*;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class InterfazAdmin extends JFrame {
    private final GestorBiblioteca gestor;

    public InterfazAdmin(GestorBiblioteca gestor) {
        this.gestor = gestor;

        setTitle("Panel de Administrador");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(6, 1, 10, 10));

        JButton btnAgregarLibro = new JButton("➕ Agregar libro");
        JButton btnRegistrarUsuario = new JButton("👤 Registrar usuario");
        JButton btnBuscarLibro = new JButton("🔍 Buscar libros");
        JButton btnVerArbol = new JButton("🌳 Ver popularidad por género");
        JButton btnVerColas = new JButton("📋 Ver colas de espera");
        JButton btnSalir = new JButton("❌ Cerrar sesión");

        panel.add(btnAgregarLibro);
        panel.add(btnRegistrarUsuario);
        panel.add(btnBuscarLibro);
        panel.add(btnVerArbol);
        panel.add(btnVerColas);
        panel.add(btnSalir);

        add(panel);

        // Acciones
        btnAgregarLibro.addActionListener(e -> agregarLibro());
        btnRegistrarUsuario.addActionListener(e -> registrarUsuario());
        btnBuscarLibro.addActionListener(e -> buscarLibro());
        btnVerArbol.addActionListener(e -> verArbol());
        btnVerColas.addActionListener(e -> verColas());
        btnSalir.addActionListener(e -> {
            new PantallaInicio(gestor);
            dispose();
        });

        setVisible(true);
    }

    private void agregarLibro() {
        JTextField titulo = new JTextField();
        JTextField autor = new JTextField();
        JTextField isbn = new JTextField();
        JTextField categoria = new JTextField();

        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.add(new JLabel("Título:"));
        panel.add(titulo);
        panel.add(new JLabel("Autor:"));
        panel.add(autor);
        panel.add(new JLabel("ISBN:"));
        panel.add(isbn);
        panel.add(new JLabel("Categoría:"));
        panel.add(categoria);

        int result = JOptionPane.showConfirmDialog(this, panel, "Agregar libro", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            Libro nuevo = new Libro(titulo.getText(), autor.getText(), isbn.getText(), categoria.getText());
            gestor.agregarLibro(nuevo);
            JOptionPane.showMessageDialog(this, "Libro agregado correctamente.");
        }
    }

    private void registrarUsuario() {
        JTextField idField = new JTextField();
        JTextField nombreField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(new JLabel("ID:"));
        panel.add(idField);
        panel.add(new JLabel("Nombre:"));
        panel.add(nombreField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Registrar usuario", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                int id = Integer.parseInt(idField.getText());
                String nombre = nombreField.getText();
                gestor.registrarUsuario(new Usuario(id, nombre));
                JOptionPane.showMessageDialog(this, "Usuario registrado.");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "ID inválido.");
            }
        }
    }

    private void buscarLibro() {
        String clave = JOptionPane.showInputDialog(this, "Ingrese título o autor:");
        if (clave == null || clave.isEmpty()) return;
        StringBuilder sb = new StringBuilder();
        for (Libro l : gestor.getTodosLosLibros()) {
            if (l.getTitulo().toLowerCase().contains(clave.toLowerCase()) ||
                l.getAutor().toLowerCase().contains(clave.toLowerCase())) {
                sb.append(l).append("\n");
            }
        }
        if (sb.length() == 0) sb.append("No se encontraron resultados.");
        mostrarMensaje("Resultado de búsqueda", sb.toString());
    }

    private void verArbol() {
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
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

    private void verColas() {
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        PrintStream originalOut = System.out;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));
        gestor.mostrarColasDeEspera();
        System.setOut(originalOut);
        textArea.setText(baos.toString());

        JScrollPane scroll = new JScrollPane(textArea);
        scroll.setPreferredSize(new Dimension(400, 300));
        JOptionPane.showMessageDialog(this, scroll, "Colas de espera", JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarMensaje(String titulo, String mensaje) {
        JTextArea textArea = new JTextArea(mensaje);
        textArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(textArea);
        scroll.setPreferredSize(new Dimension(400, 300));
        JOptionPane.showMessageDialog(this, scroll, titulo, JOptionPane.INFORMATION_MESSAGE);
    }
    
    
}
