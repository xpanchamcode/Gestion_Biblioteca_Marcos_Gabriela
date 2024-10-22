package BibliotecaMarcosGabriela;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DAOLibro_Autor {
    public static Connection conexion = JDBC.getConexion();
    private static String READALL = "SELECT * FROM Libro_Autor";
    private static String READALLLIBROSAUTOR = "SELECT idLibro FROM Libro_Autor WHERE idAutor = ?";
    private static String READALLAUTORESLIBRO = "SELECT idAutor FROM Libro_Autor WHERE idLibro = ?";
    private static String AÑADIRLIBROAUTOR = "INSERT INTO Libro_Autor VALUES(?, ?)";
    private static String DELETELIBRO = "DELETE FROM Libro_Autor WHERE idLibro = ?";
    private static String DELETEAUTOR = "DELETE FROM Libro_Autor WHERE idAutor = ?";
    private static String UPDATEAUTORLIBRO = "UPDATE Libro_Autor SET idAutor = ? WHERE idLibro = ? AND idAutor = ?";

    public static DTOLibro_Autor getLibroAutor(ResultSet rs) throws SQLException {
        //Guardamos como variables en nuestro programa cada uno de los datos traidos desde las consultas
        //Que nos devuelven un resultSet con las filas de la tabla de la BD
        Integer idLibro = rs.getInt("idLibro");
        Integer idAutor = rs.getInt("idAutor");
        return new DTOLibro_Autor(idLibro, idAutor);
    }

    public static List<DTOLibro_Autor> readAll() throws SQLException {
        List<DTOLibro_Autor> listaLibroAutor = new ArrayList<>();

        try (Statement st = conexion.createStatement();
             ResultSet rs = st.executeQuery(READALL)) {
            while (rs.next()) {
                DTOLibro_Autor libroAutor = getLibroAutor(rs);
                //En cada iteración instancia un objeto libroAutor con los datos del resultSet de la tabla

                listaLibroAutor.add(libroAutor); //Guarda en una lista los libroAutor instanciados
            }
        }

        return listaLibroAutor; //Devolvemos una lista de las libroAutor leidos de la BD
    }

    public static List<DTOLibro> readAllLibrosAutor(DTOAutor autor) throws SQLException {
        List<DTOLibro> listaLibros = new ArrayList<>();

        try (PreparedStatement pst = conexion.prepareStatement(READALLLIBROSAUTOR)) {
            pst.setInt(1, autor.getId()); //La consulta ahora nos devolverá solo los idLibro cuyo
            //idAutor sea igual al del autor del que queremos leer

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    DTOLibro libro = GestionLibros.getLibroIfExists(rs.getInt("idAlumno"));
                    //En cada iteración devuelve el objeto libro ya existente en el programa en forma de alias mediante el id de la tabla

                    listaLibros.add(libro); //Guarda en una lista los libros
                }
            }

            return listaLibros; //Devolvemos una lista de los libros que pertenecen a ese autor
        }
    }

    public static List<DTOLibro> readAllLibrosAutor(Integer id) throws SQLException {
        List<DTOLibro> listaLibros = new ArrayList<>();

        try (PreparedStatement pst = conexion.prepareStatement(READALLLIBROSAUTOR)) {
            pst.setInt(1, id); //La consulta ahora nos devolverá solo los idLibro cuyo
            //idAutor sea igual al del id que queremos leer

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    DTOLibro libro = GestionLibros.getLibroIfExists(rs.getInt("idAlumno"));
                    //En cada iteración devuelve el objeto libro ya existente en el programa en forma de alias mediante el id de la tabla

                    listaLibros.add(libro); //Guarda en una lista los libros
                }
            }

            return listaLibros; //Devolvemos una lista de los libros que pertenecen a ese autor
        }
    }

    public static List<DTOAutor> readAllAutoresLibro(DTOLibro libro) throws SQLException {
        List<DTOAutor> listaAutores = new ArrayList<>();

        try (PreparedStatement pst = conexion.prepareStatement(READALLAUTORESLIBRO)) {
            pst.setInt(1, libro.getId()); //La consulta ahora nos devolverá solo los idAutor cuyo
            //idLibro sea igual al del alumno del que queremos leer

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    DTOAutor autor = GestionAutores.getAutorIfExists(rs.getInt("idAutor"));
                    //En cada iteración devuelve el objeto autor ya existente en el programa en forma de alias según el id en la BD

                    listaAutores.add(autor); //Guarda en una lista los autores
                }
            }

            return listaAutores; //Devolvemos una lista de los autores que han escrito ese libro
        }
    }

    public static List<DTOAutor> readAllAutoresLibro(Integer id) throws SQLException {
        List<DTOAutor> listaAutores = new ArrayList<>();

        try (PreparedStatement pst = conexion.prepareStatement(READALLAUTORESLIBRO)) {
            pst.setInt(1, id); //La consulta ahora nos devolverá solo los idAutor cuyo
            //idLibro sea igual al del id que queremos leer

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    DTOAutor autor = GestionAutores.getAutorIfExists(rs.getInt("idAutor"));
                    //En cada iteración devuelve el objeto autor ya existente en el programa en forma de alias según el id en la BD

                    listaAutores.add(autor); //Guarda en una lista los autores
                }
            }

            return listaAutores; //Devolvemos una lista de los autores que han escrito ese libro
        }
    }

    public static void insertLibroAutor(Integer idLibro, Integer idAutor) throws SQLException {
        DTOLibro_Autor libroAutor = new DTOLibro_Autor(idLibro, idAutor);

        try (PreparedStatement pst = conexion.prepareStatement(AÑADIRLIBROAUTOR)) {
            //Convertimos las ? del preparedStatement en los ids del libroAutor a añadir.
            pst.setInt(1, libroAutor.getIdLibro());
            pst.setInt(2, libroAutor.getIdAutor());
            //Ejecutamos el update
            pst.executeUpdate();

//            GestionLibroAutor.getListaLibroAutor().add(libroAutor); //Lo añadimos a la lista de libroAutor
        }
    }

    public static void insertMatricula(DTOLibro libro, DTOAutor autor) throws SQLException {
        DTOLibro_Autor libroAutor = new DTOLibro_Autor(libro.getId(), autor.getId());

        try (PreparedStatement pst = conexion.prepareStatement(AÑADIRLIBROAUTOR)) {
            //Convertimos las ? del preparedStatement en los ids del libroAutor a añadir traidos desde los objetos
            pst.setInt(1, libroAutor.getIdLibro());
            pst.setInt(2, libroAutor.getIdAutor());
            //Ejecutamos el update
            pst.executeUpdate();

//            GestionLibroAutor.getListaLibroAutor().add(libroAutor); //Lo añado a la lista de libroAutor
        }
    }

    public static void updateAutorLibro(DTOLibro_Autor libroAutor, DTOAutor autor) throws SQLException{
        try(PreparedStatement pst = conexion.prepareStatement(UPDATEAUTORLIBRO)){
            pst.setInt(1, autor.getId());
            pst.setInt(2, libroAutor.getIdLibro());
            pst.setInt(3, libroAutor.getIdAutor());
            pst.executeUpdate();
        }

        //Updatea el objeto libroAutor en el menu y le cambia el idAutor al nuevo
        GestionLibroAutor.getLibroAutorIfExists(libroAutor.getIdLibro(), libroAutor.getIdAutor()).setIdAutor(autor.getId());
    }

    //Borra los libroAutor según el id de un autor
    public static void deleteLibrosAutor(Integer id) throws SQLException {
        try (PreparedStatement pst = conexion.prepareStatement(DELETELIBRO, Statement.RETURN_GENERATED_KEYS)) {
            //Convertimos el ? del preparedStatement en el ID del autor a borrar sus libros
            pst.setInt(1, id);
            pst.executeUpdate();//Ejecutamos


            //BORRAR TAMBIÉN EN LA LISTA LIBRO AUTOR
            if (!GestionLibros.getListaLibros().isEmpty()) { //Si existen libros
                for (DTOLibro libro : GestionLibros.getListaLibros()) { //Recorro el array de objetos de libro
                    if (GestionLibroAutor.libroAutorExists(libro.getId(), id)) //Compruebo si ese libro ha sido escrito por el autor a borrar
                        //Borro la relación libroAutor de la lista obteniendo el objeto libroAutor con el id del libro y del autor en cuestión:
                        GestionLibroAutor.getListaLibroAutor().remove(GestionLibroAutor.getLibroAutorIfExists(libro.getId(), id));
                }
            }
        }
    }

    //Borra los libroAutor según el id de una libro
    public static void deleteAutoresLibro(Integer id) throws SQLException {
        try (PreparedStatement pst = conexion.prepareStatement(DELETEAUTOR, Statement.RETURN_GENERATED_KEYS)) {
            //Convertimos el ? del preparedStatement en el ID del libro a borrar sus autores
            pst.setInt(1, id);
            pst.executeUpdate();//Ejecutamos


            //BORRAR TAMBIÉN EN LA LISTA LIBRO AUTOR
            if (!GestionAutores.getListaAutores().isEmpty()) { //Si existen autores
                for (DTOAutor autor : GestionAutores.getListaAutores()) { //Recorro el array de objetos de autor
                    if (GestionLibroAutor.libroAutorExists(autor.getId(), id)) //Compruebo si ese autor ha escrito ese libro a borrar
                        // Borro la relación libroAutor de la lista obteniendo el objeto libroAutor con el id del autor y del libro en cuestión:
                        GestionLibroAutor.getListaLibroAutor().remove(GestionLibroAutor.getLibroAutorIfExists(autor.getId(), id));
                }
            }
        }
    }
}