package BibliotecaMarcosGabriela;

public class DTOLibro {
    //Atributos
    private Integer id;
    //Atributo contador para aumentar el id
    //Se establece como ultimo id añadido el mayor que quedó guardado en la base de datos
    private static Integer contadorId=DAOLibro.readUltimoLibro;
    private String titulo;
    private String isbn;

    //Constructor
    public DTOLibro(String titulo, String isbn) {
        this.id=++contadorId;
        this.titulo = titulo;
        this.isbn=isbn;
    }

    public DTOLibro(Integer id, String titulo, String isbn) {
        this.id = id;
        contadorId=id;
        this.titulo = titulo;
        this.isbn=isbn;
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
        DTOLibro.contadorId = contadorId;
    }

    public String getNombre() {
        return titulo;
    }

    public void setNombre(String nombre) {
        this.titulo = nombre;
    }
    //To String

    @Override
    public String toString() {
        return String.format(
                "DTOLibro (id=%s, titulo=%s, isbn=%s)", this.id, this.titulo, this.isbn);
    }
}
