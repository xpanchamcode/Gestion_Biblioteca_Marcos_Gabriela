package BibliotecaMarcosGabriela;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOAutor {
    public static Connection conexion = JDBC.getConexion();

    //Querys
    private static String READALLAUTORES= "SELECT * FROM Autor";
    private static String READAUTOR= "SELECT * FROM Autor WHERE id=?";
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
    public static List<DTOAutor> readAll() throws SQLException{
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
        DTOAutor autorDev;
        try(PreparedStatement pst = conexion.prepareStatement(READAUTOR)){
            pst.setInt(1,id);
            try(ResultSet rs = pst.executeQuery()){
                rs.next();
                autorDev=getAutor(rs);
            }
        }
        return autorDev;
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
    //SE INSERTARÍA EL ID????
    public static  DTOAutor insertarAutor(DTOAutor autor) throws SQLException{
        try(PreparedStatement pst= conexion.prepareStatement(INSERTAUTOR)){
            pst.setInt(1, autor.getId());
            pst.setString(2, autor.getNombre());
            pst.executeUpdate();
            GestionAutores.getListaAutores().add(autor);
        }
    }
}
