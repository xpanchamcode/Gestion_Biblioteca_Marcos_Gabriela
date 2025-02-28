package BibliotecaMarcosGabriela;

import java.sql.SQLException;
import java.time.LocalDate;

public class DTOPrestamo {
    //Atributos
    private Integer id;
    //Atributo contador para aumentar el id
    //Se establece como ultimo id añadido el mayor que quedó guardado en la base de datos
    private static Integer contadorId=null;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Integer libroId;
    private Integer usuarioId;

    //Constructor
    public DTOPrestamo(LocalDate fechaInicio, LocalDate fechaFin, Integer libroId, Integer usuarioId) throws SQLException {
        if (contadorId == null) {
            DTOPrestamo ultimoPrestamo = DAOPrestamo.readUltimoPrestamo();
            if (ultimoPrestamo != null) {
                contadorId = ultimoPrestamo.getId();
            } else {
                contadorId = 0;
            }
        }
        this.id=++contadorId;
        this.fechaInicio=fechaInicio;
        this.fechaFin=fechaFin;
        this.libroId= libroId;
        this.usuarioId=usuarioId;
    }

    public DTOPrestamo(Integer id, LocalDate fechaInicio, LocalDate fechaFin, Integer libroId, Integer usuarioId) {
        this.id=id;
        contadorId = id;
        this.fechaInicio=fechaInicio;
        this.fechaFin=fechaFin;
        this.libroId= libroId;
        this.usuarioId=usuarioId;
    }

    //Getters y setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public static Integer getContadorId() {
        return contadorId;
    }

    public static void setContadorId(Integer contadorId) {
        DTOPrestamo.contadorId = contadorId;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Integer getLibroId() {
        return libroId;
    }

    public void setLibroId(Integer libroId) {
        this.libroId = libroId;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }
    //ToSTring

    @Override
    public String toString() {
        return String.format(
                "DTOPrestamo (id=%s, fechaInicio=%s, fechaFin=%s, libroId=%s, usuarioId=%s)", this.id, this.fechaInicio, this.fechaFin, this.libroId, this.usuarioId);
    }
}
