package BibliotecaMarcosGabriela;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOUsuario {
    public static Connection conexion = JDBC.getConexion();

    //Querys
    private static String READALLUSUARIOS = "SELECT * FROM Usuario";
    private static String READUSUARIO = "SELECT * FROM Usuario WHERE id=?";
    private static String READULTIMOUSUARIO ="SELECT * FROM Usuario ORDER BY idUsuario DESC LIMIT 1";
    private static String INSERTUSUARIO = "INSERT INTO Usuario (nombre) VALUES (?) WHERE id=?";
    private static String UPDATEUSUARIO ="UPDATE Usuario SET nombre=? WHERE id=?";
    private static String DELETEUSUARIO = "DELETE FROM Usuario WHERE id=?";

    //Métodos
    //Para instanciar un usuario con los datos
    public static DTOUsuario getUsuario(ResultSet rs) throws SQLException{
        Integer id = rs.getInt("id");
        String nombre =rs.getString("nombre");
        DTOUsuario usuario = new DTOUsuario(id, nombre);
        return usuario;
    }
    //Para instanciar una lista de usuarios
    public static List<DTOUsuario> readAllUsuarios() throws SQLException{
        List<DTOUsuario> listaUsuarios = new ArrayList<>();
        try(Statement st = conexion.createStatement();
            ResultSet rs= st.executeQuery(READALLUSUARIOS)){
            while (rs.next()){
                DTOUsuario usuario = getUsuario(rs);
                listaUsuarios.add(usuario);
            }
        }
        return listaUsuarios;
    }
    //Para leer el id de usuario
    public static DTOUsuario readUsuarioId(Integer id) throws SQLException{
        DTOUsuario usuarioDev;
        try(PreparedStatement pst = conexion.prepareStatement(READUSUARIO)){
            pst.setInt(1,id);
            try(ResultSet rs = pst.executeQuery()){
                rs.next();
                usuarioDev=getUsuario(rs);
            }
        }
        return usuarioDev;
    }
    //Para leer un usuario
    public static DTOUsuario readUsuario (DTOUsuario usuario) throws SQLException{
        DTOUsuario usuarioDev;
        try (PreparedStatement pst = conexion.prepareStatement(READUSUARIO)){
            pst.setInt(1,usuario.getId());
            try (ResultSet rs = pst.executeQuery()){
                rs.next();
                usuarioDev=getUsuario(rs);
            }
        }
        return usuarioDev;
    }

    //Para insertar un usuario
    public static void insertUsuario(DTOUsuario usuario) throws SQLException{
        try(PreparedStatement pst= conexion.prepareStatement(INSERTUSUARIO)){
            pst.setString(1,usuario.getNombre());
            pst.setInt(2, usuario.getId());
            pst.executeUpdate();
        }
    }

    //Para hacer update de un usuario
    public static void updateUsuario (DTOUsuario usuario) throws SQLException{
        try(PreparedStatement pst = conexion.prepareStatement(UPDATEUSUARIO)){
            pst.setString(1,usuario.getNombre());
            pst.setInt(2, usuario.getId());
            pst.executeUpdate();
        }
    }

    //Para borrar un usuario
    public static void deleteUsuario (DTOUsuario usuario) throws SQLException{
        Integer idUsuario= usuario.getId();
        try (PreparedStatement pst = conexion.prepareStatement(DELETEUSUARIO)){
            pst.setInt(1, usuario.getId());
            pst.executeUpdate();
        }
    }

    //Para obtener el último usuario insertado en la base de datos
    public static DTOUsuario readUltimoUsuario() throws SQLException {
        DTOUsuario usuario = null; // Variable para almacenar el último libro
        try (Statement st = conexion.createStatement();
             ResultSet rs = st.executeQuery(READULTIMOUSUARIO)) {
            if (rs.next()) {
                usuario = getUsuario(rs);
            }
        }
        return usuario;
    }

}