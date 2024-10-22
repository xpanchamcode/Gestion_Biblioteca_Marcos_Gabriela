package BibliotecaMarcosGabriela;

import java.sql.SQLException;
import java.util.List;

public class GestionLibroAutor {
    private Integer idLibro;
    private Integer idAutor;
    public static List<DTOLibro_Autor> listaLibroAutor;

    //En el constructor por defecto inicia la lista de objetos y la obtiene haciendo un readAll a la BD
    public GestionLibroAutor() throws SQLException {
        listaLibroAutor = DAOLibro_Autor.readAll();
    }

    //Muestra todos los objetos almacenados en la lista de memoria:
    public static void mostrar() {
        for (DTOLibro_Autor libroAutor : listaLibroAutor) {
            System.out.println(libroAutor);
        }
    }

    //Método que devuelve si existe un libro específico escrito por un autor específico:
    public static boolean libroAutorExists(Integer idLibro, Integer idAutor) {
        boolean existe = false;
        //Busco en la lista si existe un objeto con esos IDs:
        for (DTOLibro_Autor libroAutor : listaLibroAutor) {
            if (libroAutor.getIdLibro().equals(idLibro) && libroAutor.getIdAutor().equals(idAutor)) {
                existe = true;
            }
        }
        return existe;
    }

    //Método que devuelve el objeto libroAutor si existe un libro específico escrito por un autor específico:
    public static DTOLibro_Autor getLibroAutorIfExists(Integer idLibro, Integer idAutor) {
        boolean existe = false;
        DTOLibro_Autor libroAutorDevuelto = null;
        //Busco en la lista cuál es el objeto con esos IDs:
        for (DTOLibro_Autor libroAutor : listaLibroAutor) {
            if (libroAutor.getIdLibro().equals(idLibro) && libroAutor.getIdAutor().equals(idAutor)) {
                libroAutorDevuelto = libroAutor;
                existe = true;
            }
        }
        if (existe)
            return libroAutorDevuelto;
        else
            return null;
    }

    public static List<DTOLibro_Autor> getListaLibroAutor() {
        return listaLibroAutor;
    }

    public static void setListaLibroAutor(List<DTOLibro_Autor> listaLibroAutor) {
        GestionLibroAutor.listaLibroAutor = listaLibroAutor;
    }
}
