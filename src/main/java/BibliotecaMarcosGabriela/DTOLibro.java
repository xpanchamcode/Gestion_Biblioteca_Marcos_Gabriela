package BibliotecaMarcosGabriela;

import java.sql.SQLException;

public class DTOLibro {
    //Atributos
    private Integer id;
    //Atributo contador para aumentar el id
    //Se establece como ultimo id añadido el mayor que quedó guardado en la base de datos
    private static Integer contadorId=null;
    private String titulo;
    private String isbn;

    //Constructor
    public DTOLibro(String titulo, String isbn) throws SQLException {
        if (contadorId == null) {
            DTOLibro ultimoLibro = DAOLibro.readUltimoLibro();
            if (ultimoLibro != null) {
                contadorId = ultimoLibro.getId();
            } else {
                contadorId = 0;
            }
        }
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

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    //To String

    @Override
    public String toString() {
        return String.format(
                "DTOLibro (id=%s, titulo=%s, isbn=%s)", this.id, this.titulo, this.isbn);
    }
}
