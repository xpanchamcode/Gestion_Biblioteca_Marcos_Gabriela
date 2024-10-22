package BibliotecaMarcosGabriela;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOPrestamo {
    public static Connection conexion = JDBC.getConexion();

    //Querys
    private static String READALLPRESTAMOS = "SELECT * FROM Prestamo";
    private static String READALLUSUARIOSLIBRO = "SELECT idUsuario FROM Prestamo WHERE idLibro=?";
    private static String READALLLIBROSUSUARIO = "SELECT idLibro FROM Prestamo WHERE idUsuario=?";
    private static String READPRESTAMO = "SELECT * FROM Prestamo WHERE id=?";
    private static String READULTIMOPRESTAMO ="SELECT * FROM Prestamo ORDER BY idPrestamo DESC LIMIT 1";
    private static String INSERTPRESTAMO = "INSERT INTO Prestamo (fechaInicio, fechaFin, usuarioId, libroId) VALUES (?,?,?,?) WHERE id=?";
    private static String UPDATEPRESTAMO ="UPDATE Prestamo SET fechaInicio=?, fechaFin=? WHERE id=?";
    private static String DELETEPRESTAMO = "DELETE FROM Prestamo WHERE id=?";

    //Métodos

}