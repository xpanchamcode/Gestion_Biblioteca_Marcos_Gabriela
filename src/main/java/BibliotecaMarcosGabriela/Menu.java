package BibliotecaMarcosGabriela;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Menu {
    public static Scanner t = new Scanner(System.in);

    public Menu() throws SQLException {
        menu();
    }

    public void menu() throws SQLException{
        int opcion = 0;
        do {
            System.out.println("""
                    Elige una opción:
                    1. Menú libro
                    2. Menú autor
                    3. Menú usuario
                    4. Menú préstamo
                    5. Menú Libro-Autor
                    6. Salir
                    """);
            opcion = t.nextInt(); t.nextLine();
            switch (opcion){
                case 1 -> menuLibro();
                case 2 -> menuAutor();
                case 3 -> menuUsuario();
                case 4 -> menuPrestamos();
                case 5 -> menuLibroAutor();
                case 6 -> System.out.println("Cerrando.");
                default -> System.out.println("Opción errónea, inténtalo de nuevo");
            }
        } while(opcion!=6);
    }

    public void menuLibro() throws SQLException {
        int opcion = 0;
        do {
            System.out.println("""
                    Elige una opción:
                    1. Insertar libro
                    2. Leer todos los alumnos
                    3. Leer un alumno según ID
                    4. Actualizar los datos de un alumno
                    5. Borrar un alumno
                    6. Menú de ordenar alumno
                    7. Volver atrás
                    """);
            opcion = t.nextInt(); t.nextLine();
            switch (opcion){
                case 1 -> {
                    //Pido los datos del alumno por teclado y lo inserto en la BD y la lista (a la vez en el método)
                    System.out.println("Introduce los datos del alumno:");
                    System.out.println("Nombre: ");
                    String Nombre = t.nextLine();
                    System.out.println("Fecha de nacimiento (yyyy-mm-dd): ");
                    Date FechaNacimiento = Date.valueOf(t.nextLine());
                    System.out.println("Teléfono: ");
                    String Telefono = t.nextLine();
                    System.out.println("Dirección: ");
                    String Direccion = t.nextLine();
//                    if (GestionAlumnos.alumnoExists(ID)) //Si ya existe un alumno con ese ID, no dejará crearlo
//                        System.out.println("Ya existe un alumno con ese ID. Usa un update, no un insert.");
                    DAOAlumno.insertarAlumno(new DTOAlumno(Nombre, FechaNacimiento, Telefono, Direccion));
                }
                case 2 -> {
                    List<DTOAlumno> listaAlumnos = DAOAlumno.readAll();
                    if(!listaAlumnos.isEmpty()) {
                        for (DTOAlumno alumnoN : listaAlumnos) {
                            System.out.println(alumnoN);
                        }
                    }
                    else System.out.println("No existen alumnos en la BD.");
                }
                case 3 -> {
                    System.out.println("ID del alumno a leer: ");
                    Integer ID = t.nextInt(); t.nextLine();
                    if (GestionAlumnos.alumnoExists(ID)) //Si se ha encontrado un alumno con ese ID
                        //Leo por pantalla el objeto DTOAlumno recibido por la consulta con su to String por defecto
                        System.out.println(DAOAlumno.readAlumnoID(ID));
                    else
                        System.out.println("No existe un alumno con ese ID.");
                }
                case 4 -> {
                    System.out.println("ID del alumno a modificar:");
                    Integer ID = t.nextInt(); t.nextLine();
                    if (GestionAlumnos.alumnoExists(ID)){ //Si se ha encontrado un alumno con ese ID
                        DTOAlumno alumnoAupdatear = GestionAlumnos.getAlumnoIfExists(ID); ///Creo un alias para el alumno
                        //pido sus nuevos datos, los setteo en el objeto existente y envío el objeto actualizado al método

                        System.out.println("Introduce los nuevos datos del alumno:");
                        System.out.println("Nombre: ");
                        alumnoAupdatear.setNombre(t.nextLine());
                        System.out.println("Fecha de nacimiento (yyyy-mm-dd): ");
                        alumnoAupdatear.setFechaNacimiento(Date.valueOf(t.nextLine()));
                        System.out.println("Teléfono: ");
                        alumnoAupdatear.setTelefono(t.nextLine());
                        System.out.println("Dirección: ");
                        alumnoAupdatear.setDireccion(t.nextLine());

                        DAOAlumno.updateAlumno(alumnoAupdatear); //Lo modifico en la BD
                    }
                    else
                        System.out.println("No existe un alumno con ese ID");
                }
                case 5 -> {
                    System.out.println("ID del alumno a eliminar: ");
                    Integer ID = t.nextInt(); t.nextLine();
                    if (GestionAlumnos.alumnoExists(ID)) //Si se ha encontrado un alumno con ese ID,
                        //mando el alumno al método.
                        //Dentro se elimina de la lista global alumno y se eliminan sus matrículas
                        DAOAlumno.deleteAlumno(GestionAlumnos.getAlumnoIfExists(ID));
                    else
                        System.out.println("No existe un alumno con ese ID");
                }
                case 6 -> menuOrdenAlumno();
                case 7 -> System.out.println("Volviendo atrás.");
                default -> System.out.println("Opción errónea, inténtalo de nuevo");
            }
        }while(opcion!=7);
    }

    public void menuAsignatura() throws SQLException {
        int opcion = 0;
        do {
            System.out.println("""
                    Elige una opción:
                    1. Insertar asignatura
                    2. Leer todas los asignaturas
                    3. Leer una asignatura según ID
                    4. Actualizar los datos de una asignatura
                    5. Borrar una asignatura
                    6. Menú de ordenar asignatura
                    7. Volver atrás
                    """);
            opcion = t.nextInt(); t.nextLine();
            switch (opcion){
                case 1 -> {
                    //Pido los datos de la asignatura por teclado y la inserto en la BD y la lista (a la vez en el método)
                    System.out.println("Introduce el nombre de la asignatura:");
                    String Nombre = t.nextLine();
                    System.out.println("Introduce sus horas semanales:");
                    Integer HorasSemanales = t.nextInt(); t.nextLine();

                    DAOAsignatura.insertAsignatura(new DTOAsignatura(Nombre, HorasSemanales));
                }
                case 2 -> {
                    List<DTOAsignatura> listaAsignaturas = DAOAsignatura.readAllAsignaturas();
                    if(!listaAsignaturas.isEmpty()) {
                        for (DTOAsignatura asignatura : listaAsignaturas) {
                            System.out.println(asignatura);
                        }
                    }
                    else System.out.println("No existen asignaturas en la BD.");
                }
                case 3 -> {
                    System.out.println("ID de la asignatura a leer: ");
                    Integer ID = t.nextInt(); t.nextLine();
                    //Mando el ID introducido al método con la consulta
                    if (GestionAsignaturas.asignaturaExists(ID)) //Si se ha encontrado una asignatura con ese ID
                        //Leo por pantalla el objeto DTOAsignatura recibido por la consulta con su to String por defecto
                        System.out.println(DAOAsignatura.readAsignaturaID(ID));
                    else
                        System.out.println("No existe una asignatura con ese ID.");
                }
                case 4 -> {
                    System.out.println("ID de la asignatura a modificar:");
                    Integer ID = t.nextInt(); t.nextLine();
                    if (GestionAsignaturas.asignaturaExists(ID)) {//Si se ha encontrado una asignatura con ese ID,
                        DTOAsignatura asignaturaAupdatear = GestionAsignaturas.getAsignaturaIfExists(ID);
                        //Creo un alias para la asignatura, pido sus nuevos datos, los setteo en el objeto
                        //existente y envío el objeto actualizado al método

                        System.out.println("Introduce los nuevos datos de la asignatura:");
                        System.out.println("Nombre: ");
                        asignaturaAupdatear.setNombre(t.nextLine());
                        System.out.println("Horas semanales: ");
                        asignaturaAupdatear.setHorasSemanales(t.nextInt());

                        DAOAsignatura.updateAsignatura(asignaturaAupdatear); //Lo modifico en la BD
                    }
                    else
                        System.out.println("No existe una asignatura con ese ID");
                }
                case 5 -> {
                    System.out.println("ID de la asignatura a eliminar: ");
                    Integer ID = t.nextInt(); t.nextLine();
                    if (GestionAsignaturas.asignaturaExists(ID)) //Si se ha encontrado una asignatura con ese ID,
                        //mando la asignatura al método.
                        //Dentro se elimina de la lista global de asignaturas y se eliminan sus matrículas
                        DAOAsignatura.deleteAsignatura(GestionAsignaturas.getAsignaturaIfExists(ID));
                    else
                        System.out.println("No existe una asignatura con ese ID");
                }
                case 6 -> menuOrdenAsignatura();
                case 7 -> System.out.println("Volviendo atrás.");
                default -> System.out.println("Opción errónea, inténtalo de nuevo");
            }
        }while(opcion!=7);

    }
    public void menuOrdenAlumno() throws SQLException {
        int opcion = 0;
        do {
            System.out.println("""
                    Elige una opción de ordenación:
                    1. ID
                    2. Nombre
                    3. Volver al menú anterior
                    """);
            opcion = t.nextInt(); t.nextLine();
            switch (opcion){
                case 1 -> {
                    GestionAlumnos.ordenarLista();
                    System.out.println("Alumnos ordenados por ID: ");
                    GestionAlumnos.mostrarAlumnos();
                }
                case 2 -> {
                    GestionAlumnos.ordenarListaNombre();
                    System.out.println("Alumnos ordenados por Nombre: ");
                    GestionAlumnos.mostrarAlumnos();
                }
                case 3 -> System.out.println("Volviendo atrás.");
                default -> System.out.println("Opción errónea, inténtalo de nuevo");
            }
        }while(opcion!=3);
    }

    public void menuOrdenAsignatura() throws SQLException {
        int opcion = 0;
        do {
            System.out.println("""
                    Elige una opción de ordenación:
                    1. ID
                    2. Nombre
                    3. Volver al menú anterior
                    """);
            opcion = t.nextInt(); t.nextLine();
            switch (opcion){
                case 1 -> {
                    GestionAsignaturas.ordenarLista();
                    System.out.println("Asignaturas ordenadas por ID: ");
                    GestionAsignaturas.mostrarAsignaturas();
                }
                case 2 -> {
                    GestionAsignaturas.ordenarListaNombre();
                    System.out.println("Asignaturas ordenadas por Nombre: ");
                    GestionAsignaturas.mostrarAsignaturas();
                }
                case 3 -> System.out.println("Volviendo atrás.");
                default -> System.out.println("Opción errónea, inténtalo de nuevo");
            }
        }while(opcion!=3);
    }

    public void menuMatricula() throws SQLException {
        int opcion = 0;
        do {
            System.out.println("""
                    Elige una opción:
                    1. Matricular un alumno
                    2. Leer todas las matrículas
                    3. Leer asignaturas de un alumno
                    4. Leer alumnos de una asignatura
                    5. Volver al menú anterior
                    """);
            opcion = t.nextInt(); t.nextLine();
            switch (opcion){
                case 1 -> {
                    System.out.println("Introduce el id del alumno");
                    Integer idAlumno = t.nextInt(); t.nextLine();
                    Integer idAsignatura;
                    if (GestionAlumnos.alumnoExists(idAlumno)) { //Si el alumno existe
                        System.out.println("Introduce el id de la asignatura: ");
                        idAsignatura = t.nextInt(); t.nextLine();
                        if(GestionAsignaturas.asignaturaExists(idAsignatura))  //Si la asignatura existe
                            if(!GestionMatriculas.matriculaExists(idAlumno, idAsignatura)) //Si esa matrícula no existe ya
                                DAOMatricula.insertarMatricula(idAlumno, idAsignatura); //Inserto una nueva matrícula con el id de alumno y asignatura
                            else
                                System.out.println("El alumno ya está matriculado en esa asginatura.");
                        else
                            System.out.println("No existe una asignatura con ese ID");
                    }
                    else
                        System.out.println("No existe un alumno con ese ID");
                }
                case 2 -> {
                    List<DTOMatricula> listaMatriculas = DAOMatricula.readAll();
                    if(!listaMatriculas.isEmpty()) {
                        for (DTOMatricula matricula : listaMatriculas) {
                            System.out.println(matricula);
                        }
                    }
                    else System.out.println("No existen matrículas en la BD.");
                }
                case 3 ->{
                    System.out.println("Escribe el id del alumno: ");
                    Integer ID = t.nextInt(); t.nextLine();
                    if (GestionAlumnos.alumnoExists(ID)){
                        List<DTOAsignatura> listaAsignaturasAlumno = DAOMatricula.readAllAsignaturasAlumno(ID);
                        if(!listaAsignaturasAlumno.isEmpty()) {
                            for (DTOAsignatura asignatura : listaAsignaturasAlumno) {
                                System.out.println(asignatura);
                            }
                        }
                        else System.out.println("Ese alumno no está cursando ninguna asignatura.");
                    }
                    else
                        System.out.println("No existe un alumno con ese ID");
                }
                case 4 -> {
                    System.out.println("Escribe el id de la asignatura: ");
                    Integer ID = t.nextInt(); t.nextLine();
                    if (GestionAsignaturas.asignaturaExists(ID)){
                        List<DTOAlumno> listaAlumnosAsig = DAOMatricula.readAllAlumnosAsignatura(ID);
                        if(!listaAlumnosAsig.isEmpty()) {
                            for (DTOAlumno alumno : listaAlumnosAsig) {
                                System.out.println(alumno);
                            }
                        }
                        else System.out.println("No hay alumnos cursando esa asignatura.");
                    }
                    else
                        System.out.println("No existe una asignatura con ese ID");
                }
                case 5 -> System.out.println("Volviendo atrás.");
                default -> System.out.println("Opción errónea, inténtalo de nuevo");
            }
        }while(opcion!=5);
    }
}
