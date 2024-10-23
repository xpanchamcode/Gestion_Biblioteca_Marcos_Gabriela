package BibliotecaMarcosGabriela;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class DAOPrestamo {
    public static Connection conexion = JDBC.getConexion();

    //Querys
    private static String READALLPRESTAMOS = "SELECT * FROM Prestamo";
    private static String READALLUSUARIOSLIBRO = "SELECT idUsuario FROM Prestamo WHERE idLibro=?";
    private static String READALLLIBROSUSUARIO = "SELECT idLibro FROM Prestamo WHERE idUsuario=?";
    private static String READPRESTAMO = "SELECT * FROM Prestamo WHERE id=?";
    private static String READULTIMOPRESTAMO ="SELECT * FROM Prestamo ORDER BY idPrestamo DESC LIMIT 1";
    private static String INSERTPRESTAMO = "INSERT INTO Prestamo (fechaInicio, fechaFin, usuarioId, libroId) VALUES (?,?,?,?)";
    private static String UPDATEPRESTAMO ="UPDATE Prestamo SET idLibro=?, idUsuario=?, fechaInicio=?, fechaFin=? WHERE id=?";
    private static String DELETEPRESTAMO = "DELETE FROM Prestamo WHERE id=?";

    //Métodos
    public static DTOPrestamo getPrestamo(ResultSet rs) throws SQLException {
        Integer idPrestamo = rs.getInt("id");
        LocalDate fechaInicio = rs.getDate("fechaInicio").toLocalDate();
        LocalDate fechaFin = rs.getDate("fechaFin").toLocalDate();
        Integer usuarioId = rs.getInt("usuarioId");
        Integer libroId = rs.getInt("libroId");
        return new DTOPrestamo(idPrestamo, fechaInicio, fechaFin, usuarioId, libroId);
    }


        //Para leer todos los préstamos
        public static List<DTOPrestamo> readAllPrestamos() throws SQLException {
            List<DTOPrestamo> listaPrestamos = new ArrayList<>();
            try (Statement st = conexion.createStatement();
                 ResultSet rs = st.executeQuery(READALLPRESTAMOS)) {
                while (rs.next()) {
                    DTOPrestamo prestamo = getPrestamo(rs);
                    listaPrestamos.add(prestamo);
                }
            }
            return listaPrestamos;
        }

        //Para leer un préstamo específico
        public static DTOPrestamo readPrestamo(Integer idPrestamo) throws SQLException {
            DTOPrestamo prestamo = null;
            try (PreparedStatement pst = conexion.prepareStatement(READPRESTAMO)) {
                pst.setInt(1, idPrestamo);
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        prestamo = getPrestamo(rs);
                    }
                }
            }
            return prestamo;
        }

        //Para leer el último préstamo
        public static DTOPrestamo readUltimoPrestamo() throws SQLException {
            DTOPrestamo prestamo = null;
            try (Statement st = conexion.createStatement();
                 ResultSet rs = st.executeQuery(READULTIMOPRESTAMO)) {
                if (rs.next()) {
                    prestamo = getPrestamo(rs);
                }
            }
            return prestamo;
        }

        //Para insertar un nuevo préstamo
        public static void insertPrestamo(DTOPrestamo prestamo) throws SQLException {
            try (PreparedStatement pst = conexion.prepareStatement(INSERTPRESTAMO)) {
                // Convertir LocalDate a java.sql.Date para la inserción
                pst.setDate(1, java.sql.Date.valueOf(prestamo.getFechaInicio()));
                pst.setDate(2, java.sql.Date.valueOf(prestamo.getFechaFin()));
                pst.setInt(3, prestamo.getUsuarioId());
                pst.setInt(4, prestamo.getLibroId());
                pst.executeUpdate();
            }
        }


    //Para actualizar un préstamo existente
    public static void updatePrestamo(DTOPrestamo prestamo) throws SQLException {
        try (PreparedStatement pst = conexion.prepareStatement(UPDATEPRESTAMO)) {
            // Convertir LocalDate a java.sql.Date para la actualización
            pst.setInt(1, prestamo.getLibroId());
            pst.setInt(2, prestamo.getUsuarioId());
            pst.setDate(3, java.sql.Date.valueOf(prestamo.getFechaInicio()));
            pst.setDate(4, java.sql.Date.valueOf(prestamo.getFechaFin()));
            pst.setInt(5, prestamo.getId());
            pst.executeUpdate();
        }
    }


    //Para eliminar un préstamo
        public static void deletePrestamo(Integer idPrestamo) throws SQLException {
            try (PreparedStatement pst = conexion.prepareStatement(DELETEPRESTAMO)) {
                pst.setInt(1, idPrestamo);
                pst.executeUpdate();
            }
        }

        //Para leer todos los usuarios que han cogido un libro en concreto
        public static List<DTOUsuario> readAllUsuariosLibro(Integer libroId) throws SQLException {
            List<DTOUsuario> usuarios = new ArrayList<>();
            try (PreparedStatement pst = conexion.prepareStatement(READALLUSUARIOSLIBRO)) {
                pst.setInt(1, libroId);
                try (ResultSet rs = pst.executeQuery()) {
                    while (rs.next()) { //Mientras que haya líneas, recoge en la lista los objetos usuario ya creados con los ids de la BD
                        usuarios.add(GestionUsuarios.getUsuarioIfExists(rs.getInt("idUsuario")));
                    }
                }
            }
            return usuarios;
        }

        //Para leer todos los libros que ha cogido un usuario en concreto
        public static List<DTOLibro> readAllLibrosUsuario(Integer usuarioId) throws SQLException {
            List<DTOLibro> libros = new ArrayList<>();
            try (PreparedStatement pst = conexion.prepareStatement(READALLLIBROSUSUARIO)) {
                pst.setInt(1, usuarioId);
                try (ResultSet rs = pst.executeQuery()) {
                    while (rs.next()) {
                        libros.add(GestionLibros.getLibroIfExists(rs.getInt("libroId")));
                    }
                }
            }
            return libros;
        }
    }