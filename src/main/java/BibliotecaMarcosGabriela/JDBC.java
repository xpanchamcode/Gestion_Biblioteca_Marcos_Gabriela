package BibliotecaMarcosGabriela;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBC {
    //Se establecen los parámetros de acceso a la BD
    private static String USUARIO = "root";
    private static String OS;
    private static String PASSWD;
    private static String URL = "";

    private static Connection conexion = null;

    //Constructor de la clase JDBC:
    public JDBC() {
        try{
            Class.forName("org.mariadb.jdbc.Driver");
            setOS();
            setPasswd();
            conexion =  DriverManager.getConnection(URL, USUARIO, PASSWD); //Conexión a la BD
        } catch (ClassNotFoundException | SQLException ex){
            throw new RuntimeException("Error en la conexión " + ex.getMessage());
        }
    }

    //Al hacer getConexión comprobará si se ha creado o no, si no se había creado invoca el constructor JDBC
    public static Connection getConexion() {
        if (conexion == null) {
            new JDBC();
        }
        return conexion;
    }

    //Obtenemos el SO de la máquina que ejecuta el programa
    private void setOS(){
        OS = System.getProperty("os.name");
    }

    //La contraseña dependerá del SO (en los de sobremesa linux es "a", en los Windows suele ser "")
    private void setPasswd(){
        if(OS.contains("Windows")){
            PASSWD = "";
        } else if (OS.contains("Linux")| OS.contains("Unix")) {
            PASSWD = "a";
        }
    }
}
