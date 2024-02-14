/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.escuelaing.edu.co.areplab2;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Fachada para realizar solicitudes a la API REST para obtener toda la información de las películas
 * 
 * @author Daniel Fernando Moreno Cerón
 */
public class OMDBProvider {

    private static final String USER_AGENT = "Mozilla/5.0";
    private static final String GET_URL = "https://www.omdbapi.com/?apikey=2d61ae7e&t=";
    private final Cache cache;
    
     /**
     * Constructor de la clase OMDBProvider.
     */
    public OMDBProvider() {
        this.cache = Cache.getInstance();
    }
    
     /**
     * Busca el nombre de la película en una API 
     * 
     * @param name nombre de la película a buscar
     * @throws IOException Si no encuentra la película
     * @return un JsonObject Retorna el JSON de la información de la película
     */ 
    public JsonObject searchMovie(String name) throws IOException {
        if(cache.movieInCache(name)){
            return cache.getMovie(name);
        }
        URL obj = new URL(GET_URL+name);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) { 
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            cache.addMovieToCache(name, JsonParser.parseString(response.toString()).getAsJsonObject());

            System.out.println(response.toString());
        } else {
            System.out.println("no se  realizó la petición");
        }
        return Cache.getInstance().getMovie(name);
    }
    
     /**
     * Se hace la conexión con la URL con el la APIKey y el nombre de la
     * película para luego verificar si se pudo realizar la conexión mediante
     * el código de estado.
     * @param apiUrl Url a buscar
     * @throws IOException Si no encuentra la URL
     * @return retorna la información de la película en formato String 
     */ 
    private String executeGetRequest(String apiUrl) throws IOException {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = connection.getResponseCode();
        System.out.println("GET Response Code: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();
            return response.toString();
        } else {
            System.out.println("Failed to make the request");
            return "";
        }
    }
    
}
