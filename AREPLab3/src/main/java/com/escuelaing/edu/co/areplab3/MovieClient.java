package com.escuelaing.edu.co.areplab3;

import com.escuelaing.edu.co.areplab3.service.MovieService;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.parser.PdfTextExtractor;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Servidor Web para el buscador
 *
 * @author Daniel Fernando Moreno Cerón
 */
public class MovieClient {

    public static final String TEXT_PLAIN = "text/plain";
    public static final String TEXT_HTML = "text/html";
    public static final String TEXT_CSS = "text/css";
    public static final String APPLICATION_JAVASCRIPT = "application/javascript";
    public static final String IMAGE_PNG = "image/png";
    public static final String IMAGE_JPG = "image/jpg";
    public static final String IMAGE_JPEG = "image/jpeg";
    public static String URIStaticFileBase;
    private static MovieClient instance;
    private static boolean running = true;
    private static final OMDBProvider omdbProvider = new OMDBProvider();

    /**
     * Constructor de la Clase MovieClient
     */
    public MovieClient() {
    }

    /**
     * Método para iniciar el servidor.
     *
     * @throws IOException si ocurre un error de entrada/salida
     */
    public static void startServer() throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(35000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }

        while (running) {
            Socket clientSocket = null;
            try {
                System.out.println("Listo para recibir ...");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String inputLine, outputLine = "";
            boolean readingFirst = true;
            String petition = "", httpMethod = "";

            while ((inputLine = in.readLine()) != null) {
                if (readingFirst) {
                    petition = inputLine.split(" ")[1];
                    httpMethod = (inputLine.contains("GET") ? "GET"
                            : (httpMethod.contains("POST") ? "POST" : ""));
                    readingFirst = false;
                }
                if (!in.ready()) {
                    break;
                }
            }

            try {
                URI uri = new URI(petition);
                String path = uri.getPath();
                String query = uri.getQuery();
                query = (query != null ? query.split("=")[1] : "");
                if (path.contains("/action")) {
                    try {
                        String servicePath = callService(path, httpMethod);
                        outputLine = PageBody(servicePath, clientSocket.getOutputStream()).replace("{query}", query);
                    } catch (NullPointerException e) {
                        outputLine = "HTTP/1.1 404 NOT FOUND\r\n"
                                + "Content-Type: " + "text/html" + "\r\n"
                                + "\r\n" + getBodyFile("/Error.html", clientSocket.getOutputStream());
                        Logger.getLogger(MovieClient.class.getName()).log(Level.SEVERE, null, e);
                    }
                } else {
                    outputLine = (path.contains("/movies")) ? moviePage(query, clientSocket.getOutputStream()) : PageBody(path, clientSocket.getOutputStream());
                }

            } catch (URISyntaxException ex) {
                outputLine = "HTTP/1.1 404 NOT FOUND\r\n"
                                + "Content-Type: " + "text/html" + "\r\n"
                                + "\r\n" + getBodyFile("/Error.html", clientSocket.getOutputStream());
                Logger.getLogger(MovieClient.class.getName()).log(Level.SEVERE, null, ex);
            }

            System.out.println(outputLine);
            out.println(outputLine);
            out.close();
            in.close();
            clientSocket.close();
        }

        serverSocket.close();
    }

    private static String callService(String pathService, String httpMethod) throws IOException {
        String calledServiceURI = pathService.substring(7);
        MovieService handlerService = DanielSpark.findHandler(httpMethod, "/" + calledServiceURI.split("/")[1]);
        System.out.println(handlerService);
        String responseBody = handlerService.handle(pathService);
        return responseBody;
    }

    /**
     * Retorna un HTML con la película que se quiera buscar.
     *
     * @param name Nombre de la película
     * @return una estructura HTML con información de la película y encabezados
     */
    private static String moviePage(String name, OutputStream op) {
        try {
            JsonObject resp = omdbProvider.searchMovie(name);
            JsonElement poster, title, released, genre, director, actors,
                    language, plot;
            poster = resp.get("Poster");
            title = resp.get("Title");
            released = resp.get("Released");
            genre = resp.get("Genre");
            director = resp.get("Director");
            actors = resp.get("Actors");
            language = resp.get("Language");
            plot = resp.get("Plot");
            String bodyFile = getBodyFile("/MoviePage.html", op).replace("{Poster}", poster.getAsString())
                    .replace("{Title}", title.getAsString()).replace("{Released}", released.getAsString())
                    .replace("{Genre}", genre.getAsString()).replace("{Director}", director.getAsString())
                    .replace("{Actors}", actors.getAsString()).replace("{Language", language.getAsString())
                    .replace("{Plot", plot.getAsString());

            return "HTTP/1.1 200 OK\r\n"
                    + "Content-Type: text/html\r\n"
                    + "\r\n"
                    + bodyFile;
        } catch (Exception e) {
            System.err.println("Error al procesar la película:");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Método que devuelve el Buscador de las películas, en caso que se le pida
     * algún archivo tipo .css, .js, .jpg o png retorna el archivo pedido del
     * servidor
     *
     * @param fileName
     * @param op
     * @return cuerpo del buscador o archivo solicitado
     */
    private static String PageBody(String fileName, OutputStream op) {
        String formatFile = getFormatFile(fileName);
        String headerPage = getOKHeader(formatFile);
        String bodyFile = getBodyFile(fileName, op);
        String page = "";
        if (getFormatFile(fileName).startsWith("image")) {
            try {
                op.write(getOKHeader(fileName).getBytes());
                op.write(getImage(fileName));
            } catch (IOException ex) {
                Logger.getLogger(MovieClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            // Si es otro tipo de archivo, retornamos el encabezado HTTP y el contenido del archivo
            page = headerPage + bodyFile;
        }
        return page;
    }

    /**
     * Método que retorna el Content-type, según el archivo. Así se puede
     * generar correctamente el encabezado HTTP
     *
     * @param fileName
     * @return
     */
    public static String getFormatFile(String fileName) {
        String formatFile = (fileName.endsWith("/") || fileName.endsWith(".html")
                ? TEXT_HTML : (fileName.endsWith(".css")) ? TEXT_CSS
                : (fileName.endsWith(".js")) ? APPLICATION_JAVASCRIPT
                : (fileName.endsWith(".jpg")) ? IMAGE_JPG
                : (fileName.endsWith(".jpeg")) ? IMAGE_JPEG
                : (fileName.endsWith(".png")) ? IMAGE_PNG
                : TEXT_PLAIN);
        return formatFile;
    }

    /**
     * Método que retorna el archivo en formato de texto plano.
     *
     * @param fileName
     * @return
     */
    private static String getBodyFile(String fileName, OutputStream op) {
        Path file = (fileName.equals("/")) ? Paths.get(URIStaticFileBase + "/Browser.html")
                : Paths.get(URIStaticFileBase + fileName);
        StringBuilder outputLine = new StringBuilder();
        Charset charset = Charset.forName("UTF-8");
        try (BufferedReader reader = Files.newBufferedReader(file, charset)) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                outputLine.append(line).append("\n");
            }

        } catch (Exception e) {
            System.err.format("IOException: " + e.getMessage(), e);
        }

        return outputLine.toString();
    }

    /**
     * Método que retorna la imágen en un arreglo de enteros, de manera que la
     * longitud de este arreglo es el tamaño de la imágen
     *
     * @param pathFile
     * @return
     */
    public static byte[] getImage(String pathFile) {
        Path file = Paths.get("src/resources/public" + pathFile);
        byte[] imageData = null;
        try {
            imageData = Files.readAllBytes(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageData;
    }

    public static MovieClient getInstance() {
        if (instance == null) {
            instance = new MovieClient();
        }
        return instance;
    }

    public static String getOKHeader(String formatFile) {
        String header = "HTTP/1.1 200 OK\r\n" + "Content-Type:" + formatFile
                + "\r\n" + "\r\n";
        return header;
    }

    public static boolean getRunning() {
        return running;
    }

    public static void setStaticFileLocation(String path) {
        URIStaticFileBase = path;
    }
}
