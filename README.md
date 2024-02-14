# LABORATORIO #2:  MICROFRAMEWORKS WEB

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