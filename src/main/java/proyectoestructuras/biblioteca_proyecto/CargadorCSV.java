/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectoestructuras.biblioteca_proyecto;

import java.io.*;

public class CargadorCSV {
    public static void cargarLibrosCSV(String ruta, GestorBiblioteca gestor) {
    try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
        br.readLine();
        String linea;
        while ((linea = br.readLine()) != null) {
            String[] p = linea.split(",");
            Libro l = new Libro(p[1], p[2], p[0], p[3]);
            int veces = Integer.parseInt(p[4].trim());
            l.setVecesPrestado(veces);
            gestor.agregarLibro(l);
        }
    } catch (IOException e) {}
    }


    public static void cargarUsuariosCSV(String ruta, GestorBiblioteca gestor) {
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            br.readLine();
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] p = linea.split(",");
                gestor.registrarUsuario(new Usuario(Integer.parseInt(p[0]), p[1]));
            }
        } catch (IOException e) {}
    }
    

}