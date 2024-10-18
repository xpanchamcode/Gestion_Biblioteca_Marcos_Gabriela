package BibliotecaMarcosGabriela;

import java.sql.SQLException;
import java.util.List;

public class GestionAutores {
    private static List<DTOAutor> listaAutores;

    public GestionAutores() throws SQLException {
        listaAutores = DTOAutor.readAllAutores();
    }

    public static void mostrarAutores(){
        for (DTOAutor autor : listaAutores) {
            System.out.println(autor);
        }
    }

    //Método que comprueba si en la lista de objetos existe un objeto creado con ese id
    public static boolean autorExists(Integer id){
        boolean existe = false;
        //Busco en la lista cuál es el objeto con ese ID:
        for (DTOAutor autor : listaAutores) {
            if (autor.getId().equals(id)) {
                existe = true;
            }
        }
        return existe;
    }

    //  Método que devuelve un objeto existente en la base de datos según su id (si no existe devuelve null)
    public static DTOAutor getUsuarioIfExists(Integer id) {
        boolean existe = false;
        DTOAutor autorDevuelto = null;
        //Busco en la lista cuál es el objeto con ese ID:
        for (DTOAutor autor : listaAutores) {
            if (autor.getId().equals(id)) {
                autorDevuelto = autor;
                existe = true;
            }
        }
        if (existe)
            return autorDevuelto;
        else
            return null;
    }

    public static List<DTOUsuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public static void setListaUsuarios(List<DTOUsuario> listaUsuarios) {
        GestionUsuarios.listaUsuarios = listaUsuarios;
    }
}
