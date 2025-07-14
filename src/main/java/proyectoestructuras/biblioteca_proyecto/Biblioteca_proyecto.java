/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package proyectoestructuras.biblioteca_proyecto;

/**
 *
 * @author Aleja
 */
public class Biblioteca_proyecto {
    public static void main(String[] args) { 
        GestorBiblioteca gestor = new GestorBiblioteca();

        // Cargar desde CSV
        CargadorCSV.cargarLibrosCSV("libros.csv", gestor);
        CargadorCSV.cargarUsuariosCSV("usuarios.csv", gestor);

        javax.swing.SwingUtilities.invokeLater(() -> new PantallaInicio(gestor));
    }
}