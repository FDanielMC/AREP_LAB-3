# LABORATORIO #3:  MICROFRAMEWORKS WEB

En este taller usted debe explorar la arquitectura del microframework WEB denominado sparkweb (https://sparkjava.com/). Este micro framework permite construir aplicaciones web de manera simple usando funciones lambda.

Para este ejercicio usted deb construir un  servidor web para soportar una funcionalidad similar a la de Spark. Su aplicación debe permitir por lo menos el registro de servicios get y post usando funciones lambda. Implemente igualmente funciones que le permitan configurar el directorio de los archivos estáticos, y otra que permita cambiar el tipo de la respuesta a "application/json". Para esto solo debe usar el API básico de Java. No utilice frameworks como Spark o Spring.

### PREREQUISITOS

* [Java (desde la 15 para delante)](https://www.oracle.com/co/java/technologies/downloads/) 
* [Maven](https://maven.apache.org/download.cgi) 
* [Git](https://git-scm.com/downloads) 

### REQUISITOS

1. Contar con IDE para la ejecución del proyecto o línea de comandos.
2. Contar con los prerequisitos.
3. Al tenerlos, ejecutar el siguiente comando en la maquina

```bash
git clone https://github.com/FDanielMC/AREP_LAB-3.git
```

### JAVADOC
Usando el siguiente comando se genera la documentación del proyecto y para acceder a ella se encuentra en la carpeta targe/site en el archivo index.html: 
```
mvn site
```
En caso que no aparezcan el JAVADOC luego de haber ejecutado ese comando se puede hacer mediante NETBEANS:

![image](https://github.com/FDanielMC/AREP_LAB-2/assets/123689924/c8aee78f-38c4-4a63-ad28-016ee38f8598)
![image](https://github.com/FDanielMC/AREP_LAB-2/assets/123689924/839db2ca-8927-4eb3-b217-057808a54ed0)

### DESCRIPCION DEL PROYECTO

El programa consta por 4 clases java. La clase Main es la que pondra en funcionamiento todo el programa, la clase MovieClient pondra en ejecución el servidor web que retorna las páginas necesarias para poder realizar la búsqueda de las películas. Por medio de la clase OMDBProvider se realizan las peticiones correspondientes a la API externa y una clase Cache para almacenar las peticiones que ya se han realizado, así usando un buen uso de los recursos.Para la parte del FrontEnd se añadieron archivos con extensión .html, .css y .js. Luego de haber ejecutado el programa y buscarlo en el browser se verá de la siguiente manera:

![image](https://github.com/FDanielMC/AREP_LAB-2/assets/123689924/83e7815c-a615-4ba5-913b-d797425fabdf)

## INICIANDO EL PROYECTO

```
1. En un IDE de desarrollo o en la línea de comandos se ejecuta la clase Main.java, se recomienda hacerlo desde la línea de comandos. 
2. En un browser, ingresar a la URL http://localhost:35000
3. En la barra de busqueda, insertar el nombre de la pelicula a buscar
```

Si desea hacerlo usando la linea de comandos, use los siguientes comandos (se recomienda hacerlo de esta manera):
```
mvn clean compile
mvn exec:java
```

## EJECUTAR PRUEBAS

Para ejecutar las pruebas ingrese el siguiente comando en la línea de comandos:
```
mvn test
```
### Añadidos del proyecto

Se agregó una nueva clase encargada de manejar las rutas de los servicios que pidan, ya sea por petición GET o POST. A su vez se modifico la clase MovieClient para que mediante la nueva opción de los registros lambda se pueda acceder a los servicios según la ruta del servicio. Además, soporta consultas de la URI. De manera que se muestra en la página.

### Casos de Prueba

  ## Caso de Prueba: Buscar los recursos
  * ingresar a la URL http://localhost:35000/action/(recurso) 
      - Entre los recursos están /action/hola y /action/mensaje:
    
![image](https://github.com/FDanielMC/AREP_LAB-3/assets/123689924/6c9423dc-462e-4fa4-82d7-a1a16a7a6b91)

![image](https://github.com/FDanielMC/AREP_LAB-3/assets/123689924/553a2842-6709-425f-bb97-2b7f5fe83c33)

  * En caso que no esté el recurso o no se busque ningún recurso saldrá la página de error 404:


![image](https://github.com/FDanielMC/AREP_LAB-3/assets/123689924/5d3c45d0-0b91-4224-b69f-1517fffa42e7)

![image](https://github.com/FDanielMC/AREP_LAB-3/assets/123689924/8f0825fe-113e-4f59-bf11-d69044f5ebe7)

  ## Caso de Prueba: Uso de parámetros en los recursos
  * A su vez se puede ingresar parámetros en los recursos para poder interactuar con la página de cada recurso:

![image](https://github.com/FDanielMC/AREP_LAB-3/assets/123689924/c5b2fb2c-0f3d-40ba-83bd-e6f7f20c1804)

![image](https://github.com/FDanielMC/AREP_LAB-3/assets/123689924/394f3523-1787-40c8-a720-8fa0a61e2ad7)

![image](https://github.com/FDanielMC/AREP_LAB-3/assets/123689924/cea8296b-43cc-4769-8182-f00fd668abb7)

![image](https://github.com/FDanielMC/AREP_LAB-3/assets/123689924/43ed16ec-1ad2-4d81-bb89-5c823549b8ef)

  ## Caso de Prueba: Uso de POST
  * Mediante Postman se hará la prueba, así ver que la aplicación también recibe peticios HTTP del tipo POST:
      - Por ahora el único recurso que recibe peticiones del tipo POST es /action/update y en caso de que el recurso no esté saldrá la página de error. 

![image](https://github.com/FDanielMC/AREP_LAB-3/assets/123689924/0e58b30c-2e27-45e9-a726-163606f98e2b)

![image](https://github.com/FDanielMC/AREP_LAB-3/assets/123689924/ce08fae5-a180-41fa-9e8b-b250894c7210)

## DESARROLLADO CON

* [Java version 15 (Netbeans JDK 15)](https://www.oracle.com/co/java/technologies/downloads/)
* [Maven](https://maven.apache.org/download.cgi)
* [Git](https://git-scm.com/downloads)
* [omdbapi](https://www.omdbapi.com)

## Autor

* **Daniel Fernando Moreno Cerón** - [FDanielMC](https://github.com/FDanielMC)

### Licencia

This project is licensed under the MIT License - see the LICENSE.md file for details

### Agradecimientos

Escuela Colombiana de Ingeniería

