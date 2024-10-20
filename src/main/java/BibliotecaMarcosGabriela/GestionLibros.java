package BibliotecaMarcosGabriela;

import java.sql.SQLException;
import java.util.List;

public class GestionLibros {
    private static List<DTOLibro> listaLibros;

    public GestionLibros() throws SQLException {
        listaLibros = DAOUsuario.readAllLibros();
    }

    public static void mostrarLibros(){
        for (DTOLibro libro : listaLibros) {
            System.out.println(libro);
        }
    }

    //Método que comprueba si en la lista de objetos existe un objeto creado con ese id
    public static boolean libroExists(Integer id){
        boolean existe = false;
        //Busco en la lista cuál es el objeto con ese ID:
        for (DTOLibro libro : listaLibros) {
            if (libro.getId().equals(id)) {
                existe = true;
            }
        }
        return existe;
    }

    //  Método que devuelve un objeto existente en la base de datos según su id (si no existe devuelve null)
    public static DTOLibro getLibroIfExists(Integer id) {
        boolean existe = false;
        DTOLibro libroDevuelto = null;
        //Busco en la lista cuál es el objeto con ese ID:
        for (DTOLibro libro : listaLibros) {
            if (libro.getId().equals(id)) {
                libroDevuelto = libro;
                existe = true;
            }
        }
        if (existe)
            return libroDevuelto;
        else
            return null;
    }

    public static List<DTOLibro> getListaLibros() {
        return listaLibros;
    }

    public static void setListaLibros(List<DTOLibro> listaLibros) {
        GestionLibros.listaLibros = listaLibros;
    }
}
