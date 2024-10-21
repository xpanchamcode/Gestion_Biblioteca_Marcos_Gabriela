package BibliotecaMarcosGabriela;

import java.sql.SQLException;
import java.util.List;

public class GestionUsuarios {
    private static List<DTOUsuario> listaUsuarios;

    //En el constructor por defecto inicia la lista de objetos y la obtiene haciendo un readAll a la BD
    public GestionUsuarios() throws SQLException {
        listaUsuarios = DAOUsuario.readAllUsuarios();
    }

    //Muestra todos los objetos almacenados en la lista de memoria:
    public static void mostrarUsuarios(){
        for (DTOUsuario usuario : listaUsuarios) {
            System.out.println(usuario);
        }
    }

    //Método que comprueba si en la lista de objetos existe un objeto creado con ese id
    public static boolean usuarioExists(Integer id){
        boolean existe = false;
        //Busco en la lista cuál es el objeto con ese ID:
        for (DTOUsuario usuario : listaUsuarios) {
            if (usuario.getId().equals(id)) {
                existe = true;
            }
        }
        return existe;
    }

    //  Método que devuelve un objeto existente en la base de datos según su id (si no existe devuelve null)
    public static DTOUsuario getUsuarioIfExists(Integer id) {
        boolean existe = false;
        DTOUsuario usuarioDevuelto = null;
        //Busco en la lista cuál es el objeto con ese ID:
        for (DTOUsuario usuario : listaUsuarios) {
            if (usuario.getId().equals(id)) {
                usuarioDevuelto = usuario;
                existe = true;
            }
        }
        if (existe)
            return usuarioDevuelto;
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
