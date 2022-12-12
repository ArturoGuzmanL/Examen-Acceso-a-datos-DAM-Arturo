package dao;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import models.Ejemplar;
import models.Libro;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

/**
 *
 * @author FranciscoRomeroGuill
 */
public class BibliotecaDAO {
    
    private static SessionFactory sessionFactory;
    
    static{
        try{

            sessionFactory = new Configuration().configure().buildSessionFactory();

        }catch(Exception ex){
            System.out.println("Error iniciando Hibernate");
            System.out.println(ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    public void saveLibro( Libro e, ArrayList<Ejemplar> ejemplares){

        /* Guarda un libro con todos sus ejemplares en la base de datos */

        try (var session = sessionFactory.openSession()) {

            var trans = session.beginTransaction();

            session.persist(e);
            trans.commit();

            trans = session.beginTransaction();
            Query query = session.createQuery("from Libro where titulo = :titulo")
                    .setParameter("titulo", e.getTitulo());
            trans.commit();

            //Dado que en este ejercicio hay varios libros creados que se llaman igual y tienen el mismo autor
            //Se guardará en una lista ya que habrá varias coincidencias iguales
            ArrayList<Libro> libro = new ArrayList<Libro>(query.list());

            for (Ejemplar ejemplar : ejemplares) {
                trans = session.beginTransaction();
                ejemplar.setLibro_id(Math.toIntExact(libro.get(1).getId()));
                session.persist(ejemplar);
                trans.commit();
            }



        }catch (HibernateException ex) {
            System.out.println("Error al guardar el libro");
            System.out.println(ex);
        }
    }
  
    public HashSet<Libro> findByEstado(String estado){
        
        HashSet<Libro> salida = new HashSet<Libro>();

        try (var session = sessionFactory.openSession()) {
            var trans = session.beginTransaction();
            Query query = session.createQuery("FROM Ejemplar WHERE estado= :estado")
                    .setParameter("estado",estado);
            trans.commit();

            ArrayList<Ejemplar> ejemplares = new ArrayList<Ejemplar>(query.list());

            Set<Integer> idLibros = new HashSet<Integer>();

            for (Ejemplar ejemplar : ejemplares) {

                if (ejemplar.getEstado().equals(estado)) {
                    idLibros.add(ejemplar.getLibro_id());
                }
            }

            for (Integer id : idLibros) {
                long idl = id;
                trans = session.beginTransaction();
                query = session.createQuery("from Libro where id=:id")
                        .setParameter("id",idl);
                trans.commit();

                salida.add((Libro) query.getSingleResult());
            }
        }

        return salida;
        
    }
    
    public void printInfo(){
        
        /* 
          Muestra por consola todos los libros de la biblioteca y el número
          de ejemplares disponibles de cada uno.
          
          Debe imprimirlo de esta manera:
        
          Biblioteca
          ----------
          Como aprender java en 24h. (3)
          Como ser buena persona (1)
          Aprobando exámenes fácilmente (5)
          ...
        
        */

        try (var session = sessionFactory.openSession()) {

            var trans = session.beginTransaction();

            var libros = session.createQuery("from Libro").list();
            ArrayList<Libro> listaLibros = new ArrayList<Libro>(libros);

            trans.commit();

            System.out.println("Biblioteca");
            System.out.println("----------");
            for (Libro libro : listaLibros) {
                trans = session.beginTransaction();
                Query query = session.createQuery("FROM Ejemplar WHERE libro_id=:id_libro");
                query.setParameter("id_libro", Math.toIntExact(libro.getId()));
                trans.commit();

                ArrayList<Ejemplar> listaEjemplares = new ArrayList<Ejemplar>(query.list());
                System.out.println(libro.getTitulo()+" ("+listaEjemplares.size()+")");
            }

        }catch (HibernateException ex) {
            System.out.println("Error al guardar el libro");
            System.out.println(ex);
        }

        
    }
    
}
