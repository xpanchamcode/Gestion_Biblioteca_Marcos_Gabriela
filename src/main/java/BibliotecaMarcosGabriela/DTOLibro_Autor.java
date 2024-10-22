package BibliotecaMarcosGabriela;

public class DTOLibro_Autor {
    //Atributos
    private Integer idLibro;
    private Integer idAutor;
    //Atributo contador para aumentar el id

    //Constructor
    public DTOLibro_Autor(Integer idLibro, Integer idAutor) {
        this.idLibro = idLibro;
        this.idAutor = idAutor;
    }

    //Getters y setters

    public Integer getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(Integer idLibro) {
        this.idLibro = idLibro;
    }

    public Integer getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(Integer idAutor) {
        this.idAutor = idAutor;
    }

    //To String
    @Override
    public String toString() {
        return String.format(
                "DTOLibro_Autor (idLibro=%s, idAutor=%s)", this.idLibro, this.idAutor);
    }
}
