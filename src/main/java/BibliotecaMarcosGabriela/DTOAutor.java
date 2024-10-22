package BibliotecaMarcosGabriela;

import java.sql.SQLException;

public class DTOAutor {
    //Atributos
    private Integer id;
    //Atributo contador para aumentar el id
    //Se establece como ultimo id añadido el mayor que quedó guardado en la base de datos
    private static Integer contadorId=null;
    private String nombre;

    //Constructor
    public DTOAutor(String nombre) throws SQLException {
        if (contadorId == null) {
            DTOAutor ultimoAutor = DAOAutor.readUltimoAutor();
            if (ultimoAutor != null) {
                contadorId = ultimoAutor.getId(); // Obtiene el último ID de la base de datos
            } else {
                contadorId = 0; // Si no hay registros en la base de datos, empieza en 0
            }
        }
        this.id=++contadorId;
        this.nombre = nombre;
    }

    public DTOAutor(Integer id, String nombre) {
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
        DTOAutor.contadorId = contadorId;
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
                "DTOAutor (id=%s, nombre=%s)", this.id, this.nombre);
    }
}
