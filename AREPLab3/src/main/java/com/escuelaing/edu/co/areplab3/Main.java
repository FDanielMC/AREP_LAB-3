package com.escuelaing.edu.co.areplab3;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase principal para iniciar el servidor
 *
 * @author Daniel Fernando Moreno Cerón
 */
public class Main {

    public static void main(String[] args) {
        // Set the static file location
        DanielSpark.staticFileLocation("src/resources/public");

        // Define the services
        DanielSpark.get("/hola", (message) -> {
            return "/hola.html";
        });

        try {
            // Start the server
            MovieClient.getInstance().startServer();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
