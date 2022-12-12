package com.cesur.examenaddicc22;

import dao.UsuarioDAO;
import java.util.ArrayList;
import models.Usuario;

class Ejercicio2 {
    
    /**
     * Enunciado:
     * 
     * Queremos gestionar una pequeña biblioteca compartida usando una base
     * de datos MySQL gestionada por un programa en Java (se adjunta el script
     * de creación de la base de datos). Completa los métodos que aparecen en
     * la clase UsuarioDAO para gestionar el listado de usuarios de la miama,
     * usando exclusivamente JDBC.
     * 
     * Exclusivamente modificar la clase UsuarioDAO
     */
    
    static void solucion() {
        
        var dao = new UsuarioDAO();
        dao.connect();
        
        Usuario u = new Usuario();
        u.setNombre("Francisco");
        u.setApellidos("Romero");
        u.setDni("42352343F");
        
        dao.save(u);
        
        System.out.println("\nEl usuario introducido es: ");
        System.out.println( u );
        System.out.println();
        
        ArrayList<Usuario> total = dao.list();
        System.out.println("\nEl total de usuarios es: "+total.size());
        total.forEach( (us) -> System.out.println(us) );
        System.out.println();
        
        System.out.println("\nEl usuario con DNI 42352343F es: ");
        System.out.println( dao.getByDNI("42352343F") );
        System.out.println();
        
        System.out.println("\nEl usuario con DNI 00000000F es: ");
        System.out.println( dao.getByDNI("00000000F") );
        System.out.println();
                
        dao.close();
        
    }
    
}
