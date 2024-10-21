package BibliotecaMarcosGabriela;

import java.sql.SQLException;
import java.util.List;

public class GestionPrestamos {
    private static List<DTOPrestamo> listaPrestamos;

    //En el constructor por defecto inicia la lista de objetos y la obtiene haciendo un readAll a la BD
    public GestionPrestamos() throws SQLException {
        listaPrestamos = DAOPrestamo.readAllPrestamos();
    }

    //Muestra todos los objetos almacenados en la lista de memoria:
    public static void mostrarPrestamos(){
        for (DTOPrestamo prestamo : listaPrestamos) {
            System.out.println(prestamo);
        }
    }

    //Método que comprueba si en la lista de objetos existe un objeto creado con ese id
    public static boolean prestamoExists(Integer id){
        boolean existe = false;
        //Busco en la lista cuál es el objeto con ese ID:
        for (DTOPrestamo prestamo : listaPrestamos) {
            if (prestamo.getId().equals(id)) {
                existe = true;
            }
        }
        return existe;
    }

    //  Método que devuelve un objeto existente en la base de datos según su id (si no existe devuelve null)
    public static DTOPrestamo getPrestamoIfExists(Integer id) {
        boolean existe = false;
        DTOPrestamo prestamoDevuelto = null;
        //Busco en la lista cuál es el objeto con ese ID:
        for (DTOPrestamo prestamo : listaPrestamos) {
            if (prestamo.getId().equals(id)) {
                prestamoDevuelto = prestamo;
                existe = true;
            }
        }
        if (existe)
            return prestamoDevuelto;
        else
            return null;
    }

    public static List<DTOPrestamo> getListaPrestamos() {
        return listaPrestamos;
    }

    public static void setListaPrestamos(List<DTOPrestamo> listaPrestamos) {
        GestionPrestamos.listaPrestamos = listaPrestamos;
    }
}
