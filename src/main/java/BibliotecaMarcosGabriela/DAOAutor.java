package BibliotecaMarcosGabriela;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOAutor {
    public static Connection conexion = JDBC.getConexion();

    //Querys
    private static String READALLAUTORES= "SELECT * FROM Autor";
    private static String READAUTOR= "SELECT * FROM Autor WHERE id=?";
    private static String READULTIMOAUTOR ="SELECT * FROM Autor ORDER BY idAutor DESC LIMIT 1";
    private static String INSERTAUTOR= "INSERT INTO Autor (nombre) VALUES (?)";
    private static String UPDATEAUTOR ="UPDATE Autor SET nombre=? WHERE id=?";
    private static String DELETEAUTOR = "DELETE FROM Autor WHERE id=?";

    //Métodos
    //Para instanciar un autor con los datos
    public static DTOAutor getAutor(ResultSet rs) throws SQLException{
        Integer id = rs.getInt("id");
        String nombre =rs.getString("nombre");
        DTOAutor autor = new DTOAutor(id, nombre);
        return autor;
    }
    //Para instanciar una lista de autores
    public static List<DTOAutor> readAllAutores() throws SQLException{
        List<DTOAutor> listaAutores = new ArrayList<>();
        try(Statement st = conexion.createStatement();
        ResultSet rs= st.executeQuery(READALLAUTORES)){
            while (rs.next()){
                DTOAutor autor = getAutor(rs);
                listaAutores.add(autor);
            }
        }
        return listaAutores;
    }
    //Para leer el id de autor
    public static DTOAutor readAutorId(Integer id) throws SQLException{
        DTOAutor autor;
        try(PreparedStatement pst = conexion.prepareStatement(READAUTOR)){
            pst.setInt(1,id);
            try(ResultSet rs = pst.executeQuery()){
                rs.next();
                autor=getAutor(rs);
            }
        }
        return autor;
    }
    //Para leer un autor
    public static DTOAutor readAutor (DTOAutor autor) throws SQLException{
        DTOAutor autorDev;
        try (PreparedStatement pst = conexion.prepareStatement(READAUTOR)){
            pst.setInt(1,autor.getId());
            try (ResultSet rs = pst.executeQuery()){
                rs.next();
                autorDev=getAutor(rs);
            }
        }
        return autorDev;
    }

    //Para insertar un autor
    public static void insertAutor(DTOAutor autor) throws SQLException{
        try(PreparedStatement pst= conexion.prepareStatement(INSERTAUTOR)){
            pst.setString(1, autor.getNombre());
            pst.executeUpdate();
        }
    }

    //Para hacer update de un autor
    public static void updateAutor (DTOAutor autor) throws SQLException{
        try(PreparedStatement pst = conexion.prepareStatement(UPDATEAUTOR)){
            pst.setString(1, autor.getNombre());
            pst.setInt(2, autor.getId());
            pst.executeUpdate();
        }
    }

    //Para borrar un autor
    public static void deleteAutor (DTOAutor autor) throws SQLException{
        Integer idAutor= autor.getId();
        try (PreparedStatement pst = conexion.prepareStatement(DELETEAUTOR)){
            pst.setInt(1, autor.getId());
            pst.setInt(2, autor.getId());
            pst.executeUpdate();
        }
    }

    //Para leer el último autor introducido
    public static DTOAutor readUltimoAutor() throws SQLException {
        DTOAutor autor = null;
        try (Statement st = conexion.createStatement();
             ResultSet rs = st.executeQuery(READULTIMOAUTOR)) {
            if (rs.next()) {
                autor = getAutor(rs);
            }
        }
        return autor;
    }
}
