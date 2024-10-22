package BibliotecaMarcosGabriela;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOLibro {
    public static Connection conexion = JDBC.getConexion();

    //Querys
    private static String READALLLIBROS = "SELECT * FROM Libro";
    private static String READLIBRO = "SELECT * FROM Libro WHERE id=?";
    private static String INSERTLIBRO = "INSERT INTO Libro (titulo,isbn) VALUES (?,?)";
    //SE PUEDE HACER UPDATE DEL ID????
    private static String UPDATElIBRO ="UPDATE Libro SET id=?, titulo=?, isbn=?";
    private static String DELETELIBRO = "DELETE FROM Autor WHERE id=?";

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
            ResultSet rs= st.executeQuery(READALLLIBROS)){
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
        try(PreparedStatement pst = conexion.prepareStatement(READLIBRO)){
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
        try (PreparedStatement pst = conexion.prepareStatement(READLIBRO)){
            pst.setInt(1,autor.getId());
            try (ResultSet rs = pst.executeQuery()){
                rs.next();
                autorDev=getAutor(rs);
            }
        }
        return autorDev;
    }
