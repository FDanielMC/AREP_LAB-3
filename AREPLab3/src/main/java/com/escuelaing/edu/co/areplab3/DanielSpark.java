/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.escuelaing.edu.co.areplab3;

import com.escuelaing.edu.co.areplab3.service.MovieService;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author moren
 */
public class DanielSpark {

    private static DanielSpark instance;
    private final Map<String, MovieService> getServices = new HashMap<>();
    private final Map<String, MovieService> postServices = new HashMap<>();
    
    private DanielSpark(){}
    
    public static DanielSpark getInstance(){
        if(instance == null){
            instance = new DanielSpark();
        }
        return instance;
    }
    
    public static void staticFileLocation(String path){
        MovieClient.getInstance().setStaticFileLocation(path);
    }
    
    public static void get(String pathService, MovieService handler){
        getInstance().getServices.put(pathService, handler);
    }
    
    public static void post(String pathService, MovieService handler){
        getInstance().postServices.put(pathService, handler);
    }
    
    public static MovieService findHandler(String httpMethod, String pathService){
        if("GET".equalsIgnoreCase(httpMethod)){
            return getInstance().getServices.get(pathService);
        }else if("POST".equalsIgnoreCase(httpMethod)){
            return getInstance().postServices.get(pathService);
        }else return null;
    }
}
