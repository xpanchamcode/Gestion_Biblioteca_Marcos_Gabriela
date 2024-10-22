package BibliotecaMarcosGabriela;

import java.sql.SQLException;

public class DTOUsuario {
    //Atributos
    private Integer id;
    //Atributo contador para aumentar el id
    //Se establece como ultimo id añadido el mayor que quedó guardado en la base de datos
    private static Integer contadorId=null;
    private String nombre;

    //Constructor
    public DTOUsuario(String nombre) throws SQLException {
        if (contadorId == null) {
            DTOUsuario ultimoUsuario = DAOUsuario.readUltimoUsuario();
            if (ultimoUsuario != null) {
                contadorId = ultimoUsuario.getId();
            } else {
                contadorId = 0;
            }
        }
        this.id=++contadorId;
        this.nombre = nombre;
    }

    public DTOUsuario(Integer id, String nombre) {
        this.id = id;
        contadorId=id;
        this.nombre = nombre;
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
        DTOUsuario.contadorId = contadorId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    //ToString

    @Override
    public String toString() {
        return String.format(
                "DTOUsuario (id=%s, nombre=%s)", this.id, this.nombre);
    }
}
