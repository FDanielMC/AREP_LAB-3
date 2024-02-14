/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.escuelaing.edu.co.areplab3.service;

import java.io.IOException;
import java.net.URI;

/**
 *
 * @author moren
 */
@FunctionalInterface
public interface MovieService {
    
    public String handle(URI requestURI) throws IOException;
    
}
