package models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ejemplar", schema = "examen")
public class Ejemplar implements Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @Basic
    @Column(name = "estado", nullable = false, length = 16)
    private String estado; /* excelente, bueno, regular, malo */

    @Basic
    @Column(name = "edicion", nullable = false, length = 16)
    private Integer edicion;

    @Basic
    @Column(name = "libro_id", nullable = false, length = 11)
    private Integer libro_id;

    public Ejemplar() {
    }

    public Ejemplar(String estado, Integer edicion) {
        this.estado = estado;
        this.edicion = edicion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getEdicion() {
        return edicion;
    }

    public void setEdicion(Integer edicion) {
        this.edicion = edicion;
    }

    public Integer getLibro_id() {
        return libro_id;
    }

    public void setLibro_id(Integer libro_id) {
        this.libro_id = libro_id;
    }

    @Override
    public String toString() {
        return "Ejemplar{" + "id=" + id + ", estado=" + estado + ", edicion=" + edicion + ", Libro=" + libro_id + '}';
    }
    
    
}
