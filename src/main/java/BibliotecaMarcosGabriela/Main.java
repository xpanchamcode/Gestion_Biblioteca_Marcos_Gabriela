package BibliotecaMarcosGabriela;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            DDL.useBD(); //Uso la BD para conectarla a mi programa

//          HACE FALTA INSTANCIAR ESTAS CLASES PARA INICIALIZAR LOS ARRAYLIST EN LOS CONSTRUCTORES
            GestionLibros gestionLibros = new GestionLibros();
            GestionAutores gestionAutores = new GestionAutores();
            GestionUsuarios gestionUsuarios = new GestionUsuarios();
            GestionPrestamos gestionPrestamos = new GestionPrestamos();
            GestionLibroAutor gestionLibroAutor = new GestionLibroAutor();

            //Instanciamos el menú, que lo abrirá directamente.
            Menu menu = new Menu();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
