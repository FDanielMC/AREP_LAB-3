/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.escuelaing.edu.co.areplab3.service;

import java.io.IOException;
import java.net.URI;

/**
 * Interfaz funcional para poder realizar la función lambda
 * @author Daniel Fernando Moreno Cerón
 */
@FunctionalInterface
public interface MovieService {
    
    public String handle(String pathService) throws IOException;
    
}
