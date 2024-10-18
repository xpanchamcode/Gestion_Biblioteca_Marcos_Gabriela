package BibliotecaMarcosGabriela;

public class DTOAutor {
    //Atributos
    private Integer id;
    //Atributo contador para aumentar el id
    //Se establece como ultimo id añadido el mayor que quedó guardado en la base de datos
    private static Integer contadorId=DAOAutor.readUltimoAutor;
    private String nombre;

    //Constructor
    public DTOAutor(String nombre) {
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
