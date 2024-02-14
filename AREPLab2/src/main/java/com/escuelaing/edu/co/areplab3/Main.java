package com.escuelaing.edu.co.areplab3;

import java.io.IOException;

/**
 * Clase principal para iniciar el servidor
 * 
 * @author Daniel Fernando Moreno Cerón
 */
public class Main {
    public static void main(String[] args) {
        try {
            MovieClient.startServer();
        } catch (IOException e) {
            System.err.println("No se pudo conectar al servidor.");
            System.exit(1);
        }
    }
}
