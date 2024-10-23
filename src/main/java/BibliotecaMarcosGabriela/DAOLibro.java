package BibliotecaMarcosGabriela;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOLibro {
    public static Connection conexion = JDBC.getConexion();

    //Querys
    private static String READALLLIBROS = "SELECT * FROM Libro";
    private static String READLIBRO= "SELECT * FROM Libro WHERE id=?";
    private static String READULTIMOLIBRO ="SELECT * FROM Libro ORDER BY idLibro DESC LIMIT 1";
    private static String INSERTLIBRO= "INSERT INTO Libro (titulo, isbn) VALUES (?,?)";
    private static String UPDATELIBRO ="UPDATE Libro SET titulo=?, isbn=? WHERE id=?";
    private static String DELETELIBRO = "DELETE FROM Libro WHERE id=?";

    //Métodos
    //Para instanciar un libro con los datos
    public static DTOLibro getLibro(ResultSet rs) throws SQLException{
        Integer id = rs.getInt("id");
        String titulo =rs.getString("titulo");
        String isbn= rs.getString("isbn");
        DTOLibro libro = new DTOLibro(id, titulo, isbn);
        return libro;
    }
    //Para instanciar una lista de libro
    public static List<DTOLibro> readAllLibros() throws SQLException{
        List<DTOLibro> listaLibros = new ArrayList<>();
        try(Statement st = conexion.createStatement();
            ResultSet rs= st.executeQuery(READALLLIBROS)){
            while (rs.next()){
                DTOLibro libro = getLibro(rs);
                listaLibros.add(libro);
            }
        }
        return listaLibros;
    }
    //Para leer el id de libro
    public static DTOLibro readLibroId(Integer id) throws SQLException{
        DTOLibro libroDev;
        try(PreparedStatement pst = conexion.prepareStatement(READLIBRO)){
            pst.setInt(1,id);
            try(ResultSet rs = pst.executeQuery()){
                rs.next();
                libroDev=getLibro(rs);
            }
        }
        return libroDev;
    }
    //Para leer un libro
    public static DTOLibro readLibro (DTOLibro libro) throws SQLException{
        DTOLibro libroDev;
        try (PreparedStatement pst = conexion.prepareStatement(READLIBRO)){
            pst.setInt(1,libro.getId());
            try (ResultSet rs = pst.executeQuery()){
                rs.next();
                libroDev=getLibro(rs);
            }
        }
        return libroDev;
    }

    //Para insertar un libro
    public static void insertLibro(DTOLibro libro) throws SQLException{
        try(PreparedStatement pst= conexion.prepareStatement(INSERTLIBRO)){
            pst.setString(1, libro.getTitulo());
            pst.setString(2, libro.getIsbn());
            pst.executeUpdate();
        }
    }

    //Para hacer update de un libro
    public static void updateLibro (DTOLibro libro) throws SQLException{
        try(PreparedStatement pst = conexion.prepareStatement(UPDATELIBRO)){
            pst.setString(1, libro.getTitulo());
            pst.setString(2, libro.getIsbn());
            pst.setInt(3, libro.getId());
            pst.executeUpdate();
        }
    }

    //Para borrar un libro
    public static void deleteLibro (DTOLibro libro) throws SQLException{
        Integer idLibro= libro.getId();
        try (PreparedStatement pst = conexion.prepareStatement(DELETELIBRO)){
            pst.setInt(1, libro.getId());
            pst.executeUpdate();
        }
    }

    //Para obtener el último libro insertado en la base de datos
    public static DTOLibro readUltimoLibro() throws SQLException {
        DTOLibro libro = null; // Variable para almacenar el último libro
        try (Statement st = conexion.createStatement();
             ResultSet rs = st.executeQuery(READULTIMOLIBRO)) {
            if (rs.next()) {
                libro = getLibro(rs);
            }
        }
        return libro;
    }

}
