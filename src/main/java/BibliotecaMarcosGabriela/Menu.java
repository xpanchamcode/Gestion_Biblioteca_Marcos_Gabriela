package BibliotecaMarcosGabriela;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
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
                    2. Leer todos los libros
                    3. Leer un libro por ID
                    4. Actualizar los datos de un libro
                    5. Borrar un libro
                    6. Volver atrás
                    """);
            opcion = t.nextInt(); t.nextLine();
            switch (opcion){
                case 1 -> {
                    //Pido los datos del libro por teclado y lo inserto en la BD y la lista
                    System.out.println("Introduce los datos del libro:");
                    System.out.println("Título: ");
                    String titulo = t.nextLine();
                    System.out.println("Isbn: ");
                    String isbn = t.nextLine();
//                    if (GestionAlumnos.alumnoExists(ID)) //Si ya existe un alumno con ese ID, no dejará crearlo
//                        System.out.println("Ya existe un alumno con ese ID. Usa un update, no un insert.");
                    DAOLibro.insertLibro(new DTOLibro(titulo,isbn));
                }
                case 2 -> {
                    List<DTOLibro> listaLibros = DAOLibro.readAllLibros();
                        if(!listaLibros.isEmpty()) {
                        for (DTOLibro libroN : listaLibros) {
                            System.out.println(libroN);
                        }
                    }
                    else System.out.println("No existen libros en la BD.");
                }
                case 3 -> {
                    System.out.println("ID del libro: ");
                    Integer ID = t.nextInt(); t.nextLine();
                    if (GestionLibros.libroExists(ID)) //Si se ha encontrado un libro con ese ID
                        //Leo por pantalla el objeto  recibido por la consulta con su to String por defecto
                        System.out.println(DAOLibro.readLibroId(ID));
                    else
                        System.out.println("No existe un libro con ese ID.");
                }
                case 4 -> {
                    System.out.println("ID del libro a modificar:");
                    Integer ID = t.nextInt(); t.nextLine();
                    if (GestionLibros.libroExists(ID)){ //Si se ha encontrado un libro con ese ID
                        DTOLibro libroAupdatear = GestionLibros.getLibroIfExists(ID); //Creo un alias para el libro
                        //pido sus nuevos datos, los setteo en el objeto existente y envío el objeto actualizado al método

                        System.out.println("Introduce los nuevos datos del libro:");
                        System.out.println("Título: ");
                        libroAupdatear.setTitulo(t.nextLine());
                        System.out.println("Isbn: ");
                        libroAupdatear.setIsbn(t.nextLine());

                        DAOLibro.updateLibro(libroAupdatear); //Lo modifico en la BD
                    }
                    else
                        System.out.println("No existe un alumno con ese ID");
                }
                case 5 -> {
                    System.out.println("ID del libro a eliminar: ");
                    Integer ID = t.nextInt(); t.nextLine();
                    if (GestionLibros.libroExists(ID)) //Si se ha encontrado un libro con ese ID,
                        DAOLibro.deleteLibro(GestionLibros.getLibroIfExists(ID));
                    else
                        System.out.println("No existe un libro con ese ID");
                }
                case 6 -> System.out.println("Volviendo atrás.");
                default -> System.out.println("Opción errónea, inténtalo de nuevo");
            }
        }while(opcion!=6);
    }

    public void menuAutor() throws SQLException {
        int opcion = 0;
        do {
            System.out.println("""
                    Elige una opción:
                    1. Insertar autor
                    2. Leer todos los autores
                    3. Leer un autor por ID
                    4. Actualizar los datos de un autor
                    5. Borrar un autor
                    6. Volver atrás
                    """);
            opcion = t.nextInt(); t.nextLine();
            switch (opcion){
                case 1 -> {
                    //Pido los datos del autor por teclado y lo inserto en la BD y la lista (a la vez en el método)
                    System.out.println("Introduce el nombre del autor:");
                    String Nombre = t.nextLine();
                    DAOAutor.insertAutor(new DTOAutor(Nombre));
                }
                case 2 -> {
                    List<DTOAutor> listaAutores = DAOAutor.readAllAutores();
                    if(!listaAutores.isEmpty()) {
                        for (DTOAutor autor : listaAutores) {
                            System.out.println(autor);
                        }
                    }
                    else System.out.println("No existen autores en la BD.");
                }
                case 3 -> {
                    System.out.println("ID del autor a leer: ");
                    Integer ID = t.nextInt(); t.nextLine();
                    //Mando el ID introducido al método con la consulta
                    if (GestionAutores.autorExists(ID))
                        System.out.println(DAOAutor.readAutorId(ID));
                    else
                        System.out.println("No existe ningún autor con ese ID.");
                }
                case 4 -> {
                    System.out.println("ID del autor a modificar:");
                    Integer ID = t.nextInt(); t.nextLine();
                    if (GestionAutores.autorExists(ID)) {
                        DTOAutor autorAupdatear = GestionAutores.getAutorIfExists(ID);
                        System.out.println("Introduce los nuevos datos del autor:");
                        System.out.println("Nombre: ");
                        autorAupdatear.setNombre(t.nextLine());
                        DAOAutor.updateAutor(autorAupdatear);
                    }
                    else
                        System.out.println("No existe ningún autor con ese ID");
                }
                case 5 -> {
                    System.out.println("ID del autor a eliminar: ");
                    Integer ID = t.nextInt(); t.nextLine();
                    if (GestionAutores.autorExists(ID))
                        DAOAutor.deleteAutor(GestionAutores.getAutorIfExists(ID));
                    else
                        System.out.println("No existe ningún autor con ese ID");
                }
                case 6 -> System.out.println("Volviendo atrás.");
                default -> System.out.println("Opción errónea, inténtalo de nuevo");
            }
        }while(opcion!=6);

    }

    public void menuUsuario() throws SQLException {
        int opcion = 0;
        do {
            System.out.println("""
                    Elige una opción:
                    1. Insertar usuario
                    2. Leer todos los usuario
                    3. Leer un usuario por ID
                    4. Actualizar los datos de un usuario
                    5. Borrar un usuario
                    6. Volver atrás
                    """);
            opcion = t.nextInt(); t.nextLine();
            switch (opcion){
                case 1 -> {
                    System.out.println("Introduce el nombre del usuario:");
                    String Nombre = t.nextLine();
                    DAOUsuario.insertUsuario(new DTOUsuario(Nombre));
                }
                case 2 -> {
                    List<DTOUsuario> listaUsuarios = DAOUsuario.readAllUsuarios();
                    if(!listaUsuarios.isEmpty()) {
                        for (DTOUsuario usuario : listaUsuarios) {
                            System.out.println(usuario);
                        }
                    }
                    else System.out.println("No existen usuarios en la BD.");
                }
                case 3 -> {
                    System.out.println("ID del usuario a leer: ");
                    Integer ID = t.nextInt(); t.nextLine();
                    if (GestionUsuarios.usuarioExists(ID))
                        System.out.println(DAOUsuario.readUsuarioId(ID));
                    else
                        System.out.println("No existe ningún usuario con ese ID.");
                }
                case 4 -> {
                    System.out.println("ID del usuario a modificar:");
                    Integer ID = t.nextInt(); t.nextLine();
                    if (GestionUsuarios.usuarioExists(ID)) {
                        DTOUsuario usuarioAupdatear = GestionUsuarios.getUsuarioIfExists(ID);
                        System.out.println("Introduce los nuevos datos del usuario:");
                        System.out.println("Nombre: ");
                        usuarioAupdatear.setNombre(t.nextLine());
                        DAOUsuario.updateUsuario(usuarioAupdatear);
                    }
                    else
                        System.out.println("No existe ningún usuario con ese ID");
                }
                case 5 -> {
                    System.out.println("ID del usuario a eliminar: ");
                    Integer ID = t.nextInt(); t.nextLine();
                    if (GestionUsuarios.usuarioExists(ID))
                        DAOUsuario.deleteUsuario(GestionUsuarios.getUsuarioIfExists(ID));
                    else
                        System.out.println("No existe ningún usuario con ese ID");
                }
                case 6 -> System.out.println("Volviendo atrás.");
                default -> System.out.println("Opción errónea, inténtalo de nuevo");
            }
        }while(opcion!=6);
    }

    public void menuPrestamos() throws SQLException {
        int opcion = 0;
        do {
            System.out.println("""
                    Elige una opción:
                    1. Registrar un préstamo
                    2. Leer todos los préstamos
                    3. Leer un prestamo por ID????
                    4. Leer asignaturas de un alumno
                    5. Leer alumnos de una asignatura
                    6. Volver al menú anterior
                    """);
            opcion = t.nextInt(); t.nextLine();
            switch (opcion){
                case 1 -> {
                    System.out.println("Introduce el id del libro: ");
                    Integer idLibro = t.nextInt(); t.nextLine();
                    if (GestionLibros.libroExists(idLibro)) { //Si el libro existe
                        System.out.println("Introduce el id del usuario: ");
                        Integer idUsuario = t.nextInt(); t.nextLine();
                        if(GestionUsuarios.usuarioExists(idUsuario)) {
                            if (!GestionPrestamos.prestamoExists(idLibro, idUsuario)) { //Si esa matrícula no existe ya
                                System.out.println("Introduce la fecha de inicio (YYYY-MM-DD): ");
                                String fechaInicioS = t.nextLine();
                                LocalDate fechaInicio = LocalDate.parse(fechaInicioS);
                                System.out.println("Introduce la fecha de fin (YYYY-MM-DD): ");
                                String fechaFinS = t.nextLine();
                                LocalDate fechaFin = LocalDate.parse(fechaFinS);
                                DTOPrestamo nuevoPrestamo = new DTOPrestamo(fechaInicio, fechaFin, idLibro, idUsuario);
                                DAOPrestamo.insertPrestamo(nuevoPrestamo);
                            } else
                                System.out.println("El alumno ya está matriculado en esa asginatura.");
                        }
                        else
                            System.out.println("No existe un usuario con ese ID");
                    }
                    else
                        System.out.println("No existe un libro con ese ID");
                }
                //Leer todos los préstamos
                case 2 -> {
                    List<DTOPrestamo> listaPrestamos = DAOPrestamo.readAllPrestamos();
                    if(!listaPrestamos.isEmpty()) {
                        for (DTOPrestamo prestamo : listaPrestamos) {
                            System.out.println(prestamo);
                        }
                    }
                    else System.out.println("No existen préstamos en la BD.");
                }
                //Leer un préstamo por ID
                case 3 -> {
                    System.out.println("ID del préstamo a leer: ");
                    Integer ID = t.nextInt(); t.nextLine();
                    if (GestionPrestamos.getPrestamoIfExists(ID))
                        System.out.println(DAOUsuario.readUsuarioId(ID));
                    else
                        System.out.println("No existe ningún usuario con ese ID.");
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
