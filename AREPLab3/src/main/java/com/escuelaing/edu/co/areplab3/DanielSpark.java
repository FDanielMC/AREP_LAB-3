/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.escuelaing.edu.co.areplab3;

import com.escuelaing.edu.co.areplab3.service.MovieService;
import java.util.HashMap;
import java.util.Map;

/**
 * Clase encargada para manejar las rutas de los servicios
 * @author moren
 */
public class DanielSpark {

    private static DanielSpark instance;
    private final Map<String, MovieService> getServices = new HashMap<>();
    private final Map<String, MovieService> postServices = new HashMap<>();
    
    private DanielSpark(){}
    
    /**
     * Método encargado para obtener una única instancia de la clase.
     * @return 
     */
    public static DanielSpark getInstance(){
        if(instance == null){
            instance = new DanielSpark();
        }
        return instance;
    }
    
    /**
     * Método encargado de setear la ubicación de los recursos
     * @param path 
     */
    public static void staticFileLocation(String path){
        MovieClient.getInstance().setStaticFileLocation(path);
    }
    
    /**
     * Método encargado de PONER los recursos pedidos por petición GET
     * @param pathService
     * @param handler 
     */
    public static void get(String pathService, MovieService handler){
        getInstance().getServices.put(pathService, handler);
    }
    
    /**
     * Método encargado de PONER los recursos pedidos por petición GET
     * @param pathService
     * @param handler 
     */
    public static void post(String pathService, MovieService handler){
        getInstance().postServices.put(pathService, handler);
    }
    
    /**
     * Método encargado de OBTENER los recursos pedidos por petición GET
     * @param httpMethod
     * @param pathService
     * @return 
     */
    public static MovieService findHandler(String httpMethod, String pathService){
        if("GET".equalsIgnoreCase(httpMethod)){
            return getInstance().getServices.get(pathService);
        }else if("POST".equalsIgnoreCase(httpMethod)){
            return getInstance().postServices.get(pathService);
        }else return null;
    }
}
