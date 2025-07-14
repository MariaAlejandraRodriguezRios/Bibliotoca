/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectoestructuras.biblioteca_proyecto;

import javax.swing.*;
import java.awt.*;

public class PantallaInicio extends JFrame {
    public PantallaInicio(GestorBiblioteca gestor) {
        setTitle("Inicio - Biblioteca");
        setSize(300, 150);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));
        JButton btnUsuario = new JButton("Entrar como Usuario");
        JButton btnAdmin = new JButton("Entrar como Administrador");

        btnUsuario.addActionListener(e -> {
    dispose();
    String idStr = JOptionPane.showInputDialog(null, "Ingrese su ID de usuario:");
    try {
        int id = Integer.parseInt(idStr);
        Usuario u = gestor.getUsuario(id);
        if (u != null) {
            new InterfazUsuario(gestor, u);
        } else {
            JOptionPane.showMessageDialog(null, "Usuario no encontrado.");
            new PantallaInicio(gestor); // volver al inicio
        }
    } catch (HeadlessException | NumberFormatException ex) {
        JOptionPane.showMessageDialog(null, "ID inválido.");
        new PantallaInicio(gestor); // volver al inicio
    }
});

        btnAdmin.addActionListener(e -> {
    String usuario = JOptionPane.showInputDialog(null, "Ingrese usuario administrador:");
    JPasswordField pf = new JPasswordField();
    int okCxl = JOptionPane.showConfirmDialog(null, pf, "Ingrese contraseña:", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    if (okCxl == JOptionPane.OK_OPTION) {
        String password = new String(pf.getPassword());
        if ("admin".equals(usuario) && "1234".equals(password)) {
            dispose();
            new InterfazAdmin(gestor);
        } else {
            JOptionPane.showMessageDialog(null, "Credenciales incorrectas.");
        }
    }
});


        panel.add(btnUsuario);
        panel.add(btnAdmin);
        add(panel);
        setVisible(true);
    }
}
