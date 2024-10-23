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
                    5. Menú libro-autor
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
                    DTOLibro libro = new DTOLibro(titulo,isbn);
                    DAOLibro.insertLibro(libro); //Añadimos a la BD
                    GestionLibros.getListaLibros().add(libro); //Añadimos a la lista
                }
                case 2 -> {
                    List<DTOLibro> listaLibros = DAOLibro.readAllLibros();
                        if(!listaLibros.isEmpty()) {
                        for (DTOLibro libro : listaLibros) {
                            System.out.println(libro);
                        }
                    }
                    else System.out.println("No existen libros en la BD.");
                }
                case 3 -> {
                    System.out.println("ID del libro: ");
                    Integer ID = t.nextInt(); t.nextLine();
                    if (GestionLibros.libroExists(ID)) //Si se ha encontrado un libro con ese ID
                        //Leo por pantalla el objeto recibido por la consulta con su to String por defecto
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
                    if (GestionLibros.libroExists(ID)) { //Si se ha encontrado un libro con ese ID,
                        DTOLibro libroAborrar = GestionLibros.getLibroIfExists(ID);

//                        //Borrado de las listas de préstamo donde coincida ese libro:
//                        if (!GestionPrestamos.getListaPrestamos().isEmpty()) { //Si existen prestamos
//                            for (DTOPrestamo prestamo : GestionPrestamos.getListaPrestamos()) { //Recorro el array de objetos de prestamos
//                                if (prestamo.getLibroId().equals(ID)) { //Compruebo si ese libro ha sido pedido en el préstamo
//                                    GestionPrestamos.getListaPrestamos().remove(prestamo);
//                                }
//                            } //En la BD se borrará con el DELETE ON CASCADE
//                        }

                        //BORRAR TAMBIÉN EN LA LISTA LIBRO AUTOR
                        if (!GestionAutores.getListaAutores().isEmpty()) { //Si existen autores
                            for (DTOAutor autor : GestionAutores.getListaAutores()) { //Recorro el array de objetos de autor
                                if (GestionLibroAutor.libroAutorExists(autor.getId(), libroAborrar.getId())) //Compruebo si ese autor ha escrito ese libro a borrar
                                    // Borro la relación libroAutor de la lista obteniendo el objeto libroAutor con el id del autor y del libro en cuestión:
                                    GestionLibroAutor.getListaLibroAutor().remove(GestionLibroAutor.getLibroAutorIfExists(autor.getId(), libroAborrar.getId()));
                                    //En la BD ya está borrado con el delete on cascade
                            }
                        }

                        GestionLibros.getListaLibros().remove(libroAborrar); //Borrado de la lista
                        DAOLibro.deleteLibro(libroAborrar); //Borrado de la BD
                    }
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
                    DTOAutor autor = new DTOAutor(Nombre);
                    DAOAutor.insertAutor(autor);//Añadido a la BD
                    GestionAutores.getListaAutores().add(autor);//Añadido a la lista
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
                    if (GestionAutores.autorExists(ID)) {
                        DTOAutor autorAborrar = GestionAutores.getAutorIfExists(ID);

                        //BORRAR TAMBIÉN EN LA LISTA LIBRO AUTOR
                        if (!GestionLibros.getListaLibros().isEmpty()) { //Si existen libros
                            for (DTOLibro libro : GestionLibros.getListaLibros()) { //Recorro el array de objetos de libro
                                if (GestionLibroAutor.libroAutorExists(libro.getId(), autorAborrar.getId())) //Compruebo si ese libro ha sido escrito por el autor a borrar
                                    //Borro la relación libroAutor de la lista obteniendo el objeto libroAutor con el id del libro y del autor en cuestión:
                                    GestionLibroAutor.getListaLibroAutor().remove(GestionLibroAutor.getLibroAutorIfExists(libro.getId(), autorAborrar.getId()));
                                    //En la BD está ya borrado con el delete on cascade.
                            }
                        }


                        GestionAutores.getListaAutores().remove(autorAborrar); //Borrado de la lista
                        DAOAutor.deleteAutor(autorAborrar); //Borrado de la BD
                    }
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
                    DTOUsuario usuario = new DTOUsuario(Nombre);
                    DAOUsuario.insertUsuario(usuario);//Añadido a la BD
                    GestionUsuarios.getListaUsuarios().add(usuario);//Añadido a la lista
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
                    if (GestionUsuarios.usuarioExists(ID)) {
                        DTOUsuario usuarioABorrar = GestionUsuarios.getUsuarioIfExists(ID);

                        //Borrado de las listas de préstamo donde coincida ese usuario:
                        if (!GestionPrestamos.getListaPrestamos().isEmpty()) { //Si existen prestamos
                            for (DTOPrestamo prestamo : GestionPrestamos.getListaPrestamos()) { //Recorro el array de objetos de prestamos
                                if (prestamo.getUsuarioId().equals(usuarioABorrar.getId())) { //Compruebo si el prestamo ha sido pedido por ese usuario
                                    GestionPrestamos.getListaPrestamos().remove(prestamo);
                                }
                            } //En la BD se borrarán los préstamos con el DELETE ON CASCADE
                        }

                        DAOUsuario.deleteUsuario(usuarioABorrar);//Borrado de la BD del usuario
                    }
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
                    3. Leer un prestamo por ID
                    4. Modificar préstamo
                    5. Leer préstamos de un libro
                    6. Leer préstamos de un usuario
                    7. Borrar préstamo
                    8. Volver al menú anterior
                    """);
            opcion = t.nextInt(); t.nextLine();
            switch (opcion) {
                case 1 -> {
                    System.out.println("Introduce el id del libro: ");
                    Integer idLibro = t.nextInt(); t.nextLine();
                    if (GestionLibros.libroExists(idLibro)) { //Si el libro existe
                        System.out.println("Introduce el id del usuario: ");
                        Integer idUsuario = t.nextInt(); t.nextLine();
                        if (GestionUsuarios.usuarioExists(idUsuario)) {
                            System.out.println("Introduce la fecha de inicio (YYYY-MM-DD): ");
                            String fechaInicioS = t.nextLine();
                            LocalDate fechaInicio = LocalDate.parse(fechaInicioS);
                            System.out.println("Introduce la fecha de fin (YYYY-MM-DD): ");
                            String fechaFinS = t.nextLine();
                            LocalDate fechaFin = LocalDate.parse(fechaFinS);
                            DTOPrestamo nuevoPrestamo = new DTOPrestamo(fechaInicio, fechaFin, idLibro, idUsuario);
                            DAOPrestamo.insertPrestamo(nuevoPrestamo); //Añadido a la BD
                            GestionPrestamos.getListaPrestamos().add(nuevoPrestamo); //Añadido a la lista
                        } else
                            System.out.println("No existe un usuario con ese ID");
                    } else
                        System.out.println("No existe un libro con ese ID");
                }
                //Leer todos los préstamos
                case 2 -> {
                    List<DTOPrestamo> listaPrestamos = DAOPrestamo.readAllPrestamos();
                    if (!listaPrestamos.isEmpty()) {
                        for (DTOPrestamo prestamo : listaPrestamos) {
                            System.out.println(prestamo);
                        }
                    } else System.out.println("No existen préstamos en la BD.");
                }
                //Leer un préstamo por ID
                case 3 -> {
                    System.out.println("ID del préstamo a leer: ");
                    Integer ID = t.nextInt();
                    t.nextLine();
                    if (GestionPrestamos.prestamoExists(ID))
                        System.out.println(DAOPrestamo.readPrestamo(ID));
                    else
                        System.out.println("No existe ningún préstamo con ese ID.");
                }
                case 4 -> {
                    System.out.println("Escribe el id del préstamo: ");
                    Integer ID = t.nextInt(); t.nextLine();
                    if (GestionPrestamos.prestamoExists(ID)) {
                        DTOPrestamo prestamoAupdatear = GestionPrestamos.getPrestamoIfExists(ID);
                        System.out.println("Introduce el id del libro (puede ser el mismo): ");
                        Integer idLibro = t.nextInt(); t.nextLine();
                        if (GestionLibros.libroExists(idLibro)) { //Si el libro existe
                            System.out.println("Introduce el id del usuario (puede ser el mismo): ");
                            Integer idUsuario = t.nextInt(); t.nextLine();
                            if (GestionUsuarios.usuarioExists(idUsuario)) { //Si el usuario existe
                                System.out.println("Introduce la fecha de inicio (YYYY-MM-DD): ");
                                String fechaInicioS = t.nextLine();
                                LocalDate fechaInicio = LocalDate.parse(fechaInicioS);
                                System.out.println("Introduce la fecha de fin (YYYY-MM-DD): ");
                                String fechaFinS = t.nextLine();
                                LocalDate fechaFin = LocalDate.parse(fechaFinS);
                                prestamoAupdatear.setLibroId(idLibro);
                                prestamoAupdatear.setUsuarioId(idUsuario);
                                prestamoAupdatear.setFechaInicio(fechaInicio);
                                prestamoAupdatear.setFechaFin(fechaFin);
                                DAOPrestamo.updatePrestamo(prestamoAupdatear);
                            } else
                                System.out.println("No existe un usuario con ese ID");
                        } else
                            System.out.println("No existe un libro con ese ID");
                    } else
                        System.out.println("No existe un préstamo con ese ID");
                }
                case 5 -> {
                    System.out.println("Escribe el id del libro: ");
                    Integer idLibro = t.nextInt();
                    t.nextLine();
                    if (GestionLibros.libroExists(idLibro)) {
                        List<DTOUsuario> listaUsuarioLibro = DAOPrestamo.readAllUsuariosLibro(idLibro);
                        if (!listaUsuarioLibro.isEmpty()) {
                            for (DTOUsuario usuario : listaUsuarioLibro) {
                                System.out.println(usuario);
                            }
                        } else System.out.println("Ese libro no ha sido pedido en ningún préstamo.");
                    } else
                        System.out.println("No existe un libro con ese ID");
                }
                case 6 -> {
                    System.out.println("Escribe el id del usuario: ");
                    Integer idUsuario = t.nextInt();
                    t.nextLine();
                    if (GestionUsuarios.usuarioExists(idUsuario)) {
                        List<DTOLibro> listaLibrosUsuario = DAOPrestamo.readAllLibrosUsuario(idUsuario);
                        if (!listaLibrosUsuario.isEmpty()) {
                            for (DTOLibro libro : listaLibrosUsuario) {
                                System.out.println(libro);
                            }
                        } else System.out.println("Ese usuario no ha pedido ningún préstamo.");
                    } else
                        System.out.println("No existe un usuario con ese ID");
                }
                case 7 -> {
                    System.out.println("ID del préstamo a eliminar: ");
                    Integer ID = t.nextInt(); t.nextLine();
                    if (GestionPrestamos.prestamoExists(ID)) {
                        DTOPrestamo prestamoAborrar = GestionPrestamos.getPrestamoIfExists(ID);
                        //Borrado de las listas de préstamo:
                        GestionPrestamos.getListaPrestamos().remove(prestamoAborrar);
                        DAOPrestamo.deletePrestamo(prestamoAborrar.getId());//Borrado de la BD
                    }
                    else
                        System.out.println("No existe ningún préstamo con ese ID");
                }
                case 8 -> System.out.println("Volviendo atrás.");
                default -> System.out.println("Opción errónea, inténtalo de nuevo");
            }
        } while (opcion != 8);
    }
    public void menuLibroAutor() throws SQLException {
        int opcion = 0;
        do {
            System.out.println("""
                    Elige una opción:
                    1. Registrar el autor de un libro
                    2. Leer todos las relaciones libro-autor
                    3. Leer autores de un libro
                    4. Leer libros de un autor
                    5. Modificar el autor de un libro
                    6. Borrar los libros de un autor
                    7. Borrar los autores de un libro
                    8. Borrar relación entre un libro y autor
                    9. Volver al menú anterior
                    """);
            opcion = t.nextInt(); t.nextLine();
            switch (opcion){
                case 1 -> {
                    System.out.println("Escribe el id del libro: ");
                    Integer idLibro = t.nextInt(); t.nextLine();
                    if (GestionLibros.libroExists(idLibro)) {
                        System.out.println("Escribe el id del autor: ");
                        Integer idAutor = t.nextInt(); t.nextLine();
                        if (GestionAutores.autorExists(idAutor)){
                            DTOLibro_Autor libroAutor = new DTOLibro_Autor(idLibro, idAutor);
                            DAOLibro_Autor.insertLibroAutor(libroAutor.getIdLibro(), libroAutor.getIdAutor()); //Añadido a la BD
                            GestionLibroAutor.getListaLibroAutor().add(libroAutor); //Añadido a la lista
                        }
                        else System.out.println("No existe un autor con ese ID.");
                    } else
                        System.out.println("No existe un libro con ese ID");
                }
                case 2 -> {
                    List<DTOLibro_Autor> listaRelaciones = DAOLibro_Autor.readAll();
                    if(!listaRelaciones.isEmpty()) {
                        for (DTOLibro_Autor libroAutor : listaRelaciones) {
                            System.out.println(libroAutor);
                        }
                    }
                    else System.out.println("No existen relaciones entre libros y autores.");
                }
                case 3 ->{
                    System.out.println("Escribe el id del libro: ");
                    Integer idLibro = t.nextInt(); t.nextLine();
                    if (GestionLibros.libroExists(idLibro)) {
                        List<DTOAutor> listaAutoresLibro = DAOLibro_Autor.readAllAutoresLibro(idLibro);
                        if(!listaAutoresLibro.isEmpty()) {
                            for (DTOAutor autor : listaAutoresLibro) {
                                System.out.println(autor);
                            }
                        }
                        else System.out.println("Ese libro no tiene autores registrados.");
                    }
                    else
                        System.out.println("No existe un libro con ese ID.");
                }
                case 4 -> {
                    System.out.println("Escribe el id del autor: ");
                    Integer idAutor = t.nextInt(); t.nextLine();
                    if (GestionAutores.autorExists(idAutor)) {
                        List<DTOLibro> listaLibrosAutor = DAOLibro_Autor.readAllLibrosAutor(idAutor);
                        if(!listaLibrosAutor.isEmpty()) {
                            for (DTOLibro libro : listaLibrosAutor) {
                                System.out.println(libro);
                            }
                        }
                        else System.out.println("Ese autor no tiene libros registrados.");
                    }
                    else
                        System.out.println("No existe un autor con ese ID.");
                }
                case 5 -> {
                    System.out.println("Escribe el id del libro: ");
                    Integer idLibro = t.nextInt(); t.nextLine();
                    if (GestionLibros.libroExists(idLibro)) {
                        System.out.println("Escribe el anterior autor de ese libro a cambiar: ");
                        Integer idAutorAntiguo = t.nextInt();t.nextLine();
                        if (GestionAutores.autorExists(idAutorAntiguo)) {
                            if(GestionLibroAutor.libroAutorExists(idLibro, idAutorAntiguo)){
                                //Obtengo la relación entre libroAutor
                                DTOLibro_Autor libroAutorAupdatear = GestionLibroAutor.getLibroAutorIfExists(idLibro, idAutorAntiguo);
                                System.out.println("Escribe el nuevo autor de ese libro: ");
                                Integer idAutorNuevo = t.nextInt();t.nextLine();
                                if (GestionAutores.autorExists(idAutorNuevo)) {
                                    //Obtengo el alias de ese nuevoAutor
                                    DTOAutor autorNuevo = GestionAutores.getAutorIfExists(idAutorNuevo);
                                    //Paso al método la relación ya existente y el nuevo autor (en la BD)
                                    DAOLibro_Autor.updateAutorLibro(libroAutorAupdatear, autorNuevo);
                                    //Updatea el objeto libroAutor y le cambia el idAutor al nuevo (en la lista)
                                    libroAutorAupdatear.setIdAutor(idAutorNuevo);
                                } else
                                    System.out.println("No existe un autor con ese ID.");
                            } else
                                System.out.println("No existe esa relación entre ese libro y autor.");
                        } else
                            System.out.println("No existe un autor con ese ID.");
                    } else
                        System.out.println("No existe un libro con ese ID.");
                }
                case 6 -> {
                    System.out.println("Id del autor del que eliminar sus libros: ");
                    Integer ID = t.nextInt(); t.nextLine();
                    if (GestionAutores.autorExists(ID)) {
                        DTOAutor autorAborrar = GestionAutores.getAutorIfExists(ID);

                        //BORRADO EN LA LISTA LIBRO AUTOR:
                        if (!GestionLibros.getListaLibros().isEmpty()) { //Si existen libros
                            for (DTOLibro libro : GestionLibros.getListaLibros()) { //Recorro el array de objetos de libro
                                if (GestionLibroAutor.libroAutorExists(libro.getId(), autorAborrar.getId())) //Compruebo si ese libro ha sido escrito por el autor a borrar
                                    //Borro la relación libroAutor de la lista obteniendo el objeto libroAutor con el id del libro y del autor en cuestión:
                                    GestionLibroAutor.getListaLibroAutor().remove(GestionLibroAutor.getLibroAutorIfExists(libro.getId(), autorAborrar.getId()));
                            } //En la BD ya está borrado con el delete on cascade
                        }

                        DAOLibro_Autor.deleteLibrosAutor(autorAborrar.getId()); //Borramos los libros del autor de la BD
                    }
                    else
                        System.out.println("No existe ningún autor con ese ID");
                }
                case 7 -> {
                    System.out.println("Id del libro del que eliminar sus autores: ");
                    Integer ID = t.nextInt(); t.nextLine();
                    if (GestionLibros.libroExists(ID)) { //Si se ha encontrado un libro con ese ID,
                        DTOLibro libroAborrar = GestionLibros.getLibroIfExists(ID);

                        //BORRADO EN LA LISTA LIBRO AUTOR:
                        if (!GestionAutores.getListaAutores().isEmpty()) { //Si existen autores
                            for (DTOAutor autor : GestionAutores.getListaAutores()) { //Recorro el array de objetos de autor
                                if (GestionLibroAutor.libroAutorExists(autor.getId(), libroAborrar.getId())) //Compruebo si ese autor ha escrito ese libro a borrar
                                    // Borro la relación libroAutor de la lista obteniendo el objeto libroAutor con el id del autor y del libro en cuestión:
                                    GestionLibroAutor.getListaLibroAutor().remove(GestionLibroAutor.getLibroAutorIfExists(autor.getId(), libroAborrar.getId()));
                            } //En la BD ya está borrado con el delete on cascade
                        }

                        DAOLibro_Autor.deleteAutoresLibro(libroAborrar.getId()); //Borramos los autores del libro de la BD
                    }
                    else
                        System.out.println("No existe un libro con ese ID");
                }
                case 8 -> {
                    System.out.println("Escribe el id del libro: ");
                    Integer idLibro = t.nextInt(); t.nextLine();
                    if (GestionLibros.libroExists(idLibro)) {
                        System.out.println("Escribe el id del autor: ");
                        Integer idAutor = t.nextInt(); t.nextLine();
                        if (GestionAutores.autorExists(idAutor)){
                            if(GestionLibroAutor.libroAutorExists(idLibro, idAutor)){
                                DTOLibro_Autor libroAutorAborrar = GestionLibroAutor.getLibroAutorIfExists(idLibro, idAutor);
                                //Borrado de la lista:
                                GestionLibroAutor.getListaLibroAutor().remove(libroAutorAborrar);
                                //Borrado de la BD:
                                DAOLibro_Autor.deleteRelacion(libroAutorAborrar.getIdLibro(), libroAutorAborrar.getIdAutor());
                            } else
                                System.out.println("No existe una relación entre ese libro y autor.");
                        } else
                            System.out.println("No existe un autor con ese ID.");
                    } else
                        System.out.println("No existe un libro con ese ID");
                }
                case 9 -> System.out.println("Volviendo atrás.");
                default -> System.out.println("Opción errónea, inténtalo de nuevo");
            }
        }while(opcion!=9);
    }
}
