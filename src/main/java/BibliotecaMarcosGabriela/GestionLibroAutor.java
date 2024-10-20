package BibliotecaMarcosGabriela;

import java.sql.SQLException;
import java.util.List;

public class GestionLibroAutor {
    private Integer idLibro;
    private Integer idAutor;
    public static List<DTOLibro_Autor> listaLibroAutor;

    public GestionLibroAutor() throws SQLException {
        listaLibroAutor = DAOLibro_Autor.readAllLibroAutor();
    }

    public static void mostrar() {
        for (DTOLibro_Autor matricula : listaLibroAutor) {
            System.out.println(matricula);
        }
    }

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
