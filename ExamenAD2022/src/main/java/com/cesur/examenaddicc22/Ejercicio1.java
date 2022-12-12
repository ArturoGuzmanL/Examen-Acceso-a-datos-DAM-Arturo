package com.cesur.examenaddicc22;

import java.io.*;

class Ejercicio1 {

    /**
     * Enunciado:
     *
     * Completar el método estadísticasDeArchivo de manera que lea el archivo
     * de texto que se le pasa como parámetro, lo analice y muestre por consola 
     * el número de caracteres, palabras y líneas total.
     * 
     * Modificar solo el código del método.
     * 
     */
    
    static void solucion() {

        estadísticasDeArchivo("pom.xml");
    }

    private static void estadísticasDeArchivo(String archivo) {

        try (BufferedReader bfr = new BufferedReader(new FileReader(archivo))) {

            StringBuilder textoLeido = new StringBuilder();
            int numeroLineas = 0;
            int numeroCaracteres = 0;
            int numeroPalabras = 0;

            while (bfr.ready()) {
                String linea = bfr.readLine();
                textoLeido.append(linea).append("\n");
                numeroLineas++;
                numeroCaracteres += linea.length();
                numeroPalabras += linea.split(" ").length;
            }

            System.out.println("Estadisticas de "+archivo+":");
            System.out.println("Numero de lineas: "+numeroLineas);
            System.out.println("Numero de caracteres: "+numeroCaracteres);
            System.out.println("Numero de palabras: "+numeroPalabras);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
}
