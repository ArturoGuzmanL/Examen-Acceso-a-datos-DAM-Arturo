package dao;

import models.Usuario;

import java.sql.*;
import java.util.ArrayList;

public class UsuarioDAO {

    private Connection connection;

    /* Completar consultas */
    static final String INSERT_QUERY ="INSERT INTO usuario (nombre, apellidos, dni) VALUES (?, ?, ?)";
    static final String LIST_QUERY="SELECT * FROM usuario";
    static final String GET_BY_DNI="SELECT * FROM usuario WHERE dni=?";

    public void connect(){

        try {

            /* completar código de conexión */

        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/examen", "root", "");

        }catch(Exception ex){
            System.out.println("Error al conectar a la base de datos");
            System.out.println("ex");
        }
    }

    public void close(){
        try {
            connection.close();
        } catch (Exception ex) {
            System.out.println("Error al cerrar la base de datos");
        }
    }

    public void save(Usuario user){

        /**
         * Completar código para guardar un usuario 
         * Este método no debe generar el id del usuario, ya que la base
         * de datos es la que lo genera.
         * Una vez obtenido el id del usuario tras la consulta sql,
         * hay que modificarlo en el objeto que se pasa como parámetro 
         * de forma que pase a tener el id correcto.
         */

//Se obtienen los datos del objeto usuario

        String nombre = user.getNombre();
        String apellidos = user.getApellidos();
        String dni = user.getDni();

        try {
//Se crea el statement con la query y el parametro Generated Keys para poder obtener la key autogenerada correspondiente

            PreparedStatement ps = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, nombre);
            ps.setString(2, apellidos);
            ps.setString(3, dni);

            ps.executeUpdate();

//Se obtiene la key
            try (ResultSet keys = ps.getGeneratedKeys()) {
                keys.next();
                user.setId(keys.getLong(1));
            }

            System.out.println("Usuario guardado correctamente");

        } catch (Exception ex) {
            System.out.println("Error al guardar el usuario");
        }

    }

    public ArrayList<Usuario> list(){

        var salida = new ArrayList<Usuario>(0);

        try {

//Se crea el statement con la query y se ejecuta

            PreparedStatement ps = connection.prepareStatement(LIST_QUERY);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
//Se recorren los resultados y se van creando los usuarios con los datos obtenidos

                Usuario usuario = new Usuario();

                usuario.setId(rs.getLong("id"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setApellidos(rs.getString("apellidos"));
                usuario.setDni(rs.getString("dni"));

                salida.add(usuario);

            }

        } catch (Exception ex) {
            System.out.println("Error al listar los usuarios");
        }

        return salida;
    }

    public Usuario getByDNI(String dni){

        Usuario salida = new Usuario();

        try {

//Se crea el statement con la query y se ejecuta

            PreparedStatement ps = connection.prepareStatement(GET_BY_DNI);

            ps.setString(1, dni);

            ResultSet rs = ps.executeQuery();

            rs.next();
//Se guardan los datos obtenidos en el usuario

            salida.setId(rs.getLong("id"));
            salida.setNombre(rs.getString("nombre"));
            salida.setApellidos(rs.getString("apellidos"));
            salida.setDni(rs.getString("dni"));

        } catch (Exception ex) {
            System.out.println("Error al obtener el usuario");
        }


        return salida;
    }
}
