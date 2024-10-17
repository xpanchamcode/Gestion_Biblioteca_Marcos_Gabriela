package BibliotecaMarcosGabriela;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DDL {
    private static Connection conexion = JDBC.getConexion(); //Obtengo la conexión mediante la clase JDBC
    private static String nombreBD = ""; //Establezco el nombre de la BD

    //Método para usar la BD:
    public static void useBD() throws SQLException {
        try(Statement st = conexion.createStatement()){
            st.execute("USE "+nombreBD);
        }
    }

}
