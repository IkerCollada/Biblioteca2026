/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package cm69ikereducastur.com.biblioteca2025;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author 1dawd23
 */
public class Biblioteca2025 {
    private static ArrayList <Libro> libros = new ArrayList();
    private static ArrayList <Usuario> usuarios = new ArrayList();
    private static ArrayList <Prestamo> prestamos = new ArrayList();
    private static Scanner sc = new Scanner (System.in);
    private static ArrayList <Prestamo> prestamosHist = new ArrayList(); //ArrayList secundario de los préstamos
    
    public static void main(String[] args) {
        cargaDatosPrueba();
        //menúGeneral();
        //listadosConStreams();
        ordenacionesConStreams();
        
    }
    public static void cargaDatosPrueba() {
        libros.add(new Libro("1-11", "El Hobbit", "JRR Tolkien", "Aventuras", 3));
        libros.add(new Libro("1-22", "El Silmarillon", "JRR Tolkien", "Aventuras", 3));
        libros.add(new Libro("1-33", "El Medico", "N. Gordon", "Aventuras", 4));
        libros.add(new Libro("1-44", "Chaman", "N. Gordon", "Aventuras", 3));
        libros.add(new Libro("1-55", "Momo", "M. Ende", "Aventuras", 2));
        libros.add(new Libro("1-66", "Paraiso inhabitado", "A.M.Matute", "Aventuras", 2));
        libros.add(new Libro("1-77", "Olvidado Rey Gudu", "A.M.Matute", "Aventuras", 0));
        libros.add(new Libro("1-88", "El ultimo barco", "D.Villar", "Novela Negra", 3));
        libros.add(new Libro("1-99", "Ojos de agua", "D.Villar", "Novela Negra", 0));

        usuarios.add(new Usuario("11", "Ana", "ana@email.com", "621111111"));
        usuarios.add(new Usuario("22", "David", "david@email.com", "622222222"));
        usuarios.add(new Usuario("33", "Bea", "bea@email.com", "623333333"));
        usuarios.add(new Usuario("44", "Lucas", "lucas@email.com", "624444444"));
        usuarios.add(new Usuario("55", "Carlota", "carlota@email.com", "625555555"));
        usuarios.add(new Usuario("66", "Juan", "juan@email.com", "626666666"));

        LocalDate hoy = LocalDate.now(); //OBTENEMOS LA FECHA DE HOY CON EL MÉTODO now()

        //PRESTAMOS "NORMALES" REALIZADOS HOY Y QUE SE HAN DE DEVOLVER EN 15 DÍAS
        prestamos.add(new Prestamo(libros.get(0), usuarios.get(0), hoy, hoy.plusDays(15)));
        prestamos.add(new Prestamo(libros.get(1), usuarios.get(0), hoy, hoy.plusDays(15)));
        prestamos.add(new Prestamo(libros.get(5), usuarios.get(0), hoy, hoy.plusDays(15)));
        prestamos.add(new Prestamo(libros.get(0), usuarios.get(4), hoy, hoy.plusDays(15)));
        prestamos.add(new Prestamo(libros.get(0), usuarios.get(1), hoy, hoy.plusDays(15)));
        //PRESTAMOS QUE YA TENIAN QUE HABER SIDO DEVUELTOS PORQUE SU FECHA DE DEVOLUCIÓN ES ANTERIOR A HOY
        prestamos.add(new Prestamo(libros.get(5), usuarios.get(1), hoy.minusDays(17), hoy.minusDays(2)));
        prestamos.add(new Prestamo(libros.get(1), usuarios.get(4), hoy.minusDays(18), hoy.minusDays(3)));
        prestamos.add(new Prestamo(libros.get(2), usuarios.get(4), hoy.minusDays(20), hoy.minusDays(5)));
        prestamos.add(new Prestamo(libros.get(3), usuarios.get(4), hoy.minusDays(20), hoy.minusDays(5)));

        //PRESTAMOS HISTORICOS QUE YA HAN SIDO DEVUELTOS Y POR TANTO ESTÁN EN LA COLECCION prestamosHist
        prestamosHist.add(new Prestamo(libros.get(0), usuarios.get(0), hoy.minusDays(30), hoy.minusDays(15)));
        prestamosHist.add(new Prestamo(libros.get(2), usuarios.get(0), hoy.minusDays(30), hoy.minusDays(15)));
        prestamosHist.add(new Prestamo(libros.get(7), usuarios.get(4), hoy.minusDays(30), hoy.minusDays(15)));
        prestamosHist.add(new Prestamo(libros.get(5), usuarios.get(4), hoy.minusDays(20), hoy.minusDays(15)));
        prestamosHist.add(new Prestamo(libros.get(1), usuarios.get(1), hoy.minusDays(20), hoy.minusDays(5)));
        prestamosHist.add(new Prestamo(libros.get(7), usuarios.get(2), hoy.minusDays(10), hoy));
        prestamosHist.add(new Prestamo(libros.get(6), usuarios.get(3), hoy.minusDays(10), hoy));
    }
    
    public static int stockLibro(String isbn) throws LibroNoExiste, LibroNoDisponible {
        int pos = buscaLibros(isbn);
        if (pos == -1) {
            throw new LibroNoExiste("No existe en esta biblioteca la referencia: " + isbn);
        } else if (libros.get(pos).getEjemplares() == 0){
            throw new LibroNoDisponible("No hay más unidades del libro: " + libros.get(pos));
        }
        return pos;
    }
    
    //<editor-fold defaultstate="collapsed" desc="Ejercicios Examen">
    public static void ejercicio1(){
        System.out.print("ISBN libro para consultar prestamos: ");
        String isbn = sc.next();
        
        int posLibro = buscaLibros(isbn);
        
        if (posLibro == -1) {
            System.out.println("El libro no se ha encontrado.");
        } else {
            System.out.println("Prestamos activos del libro: " + libros.get(posLibro).getTitulo());
            
            int totalPrest = 0;
            for (Prestamo p : prestamos) {
                if (p.getLibroPrest().getIsbn().equalsIgnoreCase(isbn)) {
                    System.out.println(p);
                    totalPrest++;
                }
            }
            System.out.println(libros.get(posLibro).getTitulo() + 
                    " tiene " + totalPrest + " prestamos actualmente.");
        }
    }
    
    public static void ejercicio2(){
        System.out.println("Libros NUNCA prestados:");
        
        for (Libro l : libros) {
            boolean prestado = false;
            for (Prestamo p : prestamos) {
                if (p.getLibroPrest().equals(l)) {
                    prestado = true;
                    break;
                }
            }
            
            for (Prestamo ph : prestamosHist) {
                if (ph.getLibroPrest().equals(l)) {
                    prestado = true;
                    break;
                }
            }
            
            if (prestado == false) {
                System.out.println("El libro " + l.getTitulo() + " no tiene prestamos");
            }
        }
    }
    
    public static void ejercicio3(){
        System.out.println("Usuarios que tienen al menos un prestamo ACTIVO:");
        
        for (Usuario u : usuarios) {
            boolean prestado = false;
            for (Prestamo p : prestamos) {
                if (p.getUsuarioPrest().equals(u)) {
                    prestado = true;
                    break;
                }
            }
            if (prestado == true) {
                System.out.println("El usuario " + u.getNombre() + " tiene al menos un préstamo activo");
            }
        }
    }
    
    public static void ejercicio4(){
        LocalDate hoy = LocalDate.now();
        System.out.println("Usuarios que tienen PRESTAMOS FUERA DE PLAZO");
        int posUsuario = 0;
        for (Usuario u : usuarios) {
            for (Prestamo p : prestamos) {
                
            }
        }
    }
    
    public static void ejercicio5(){
        LocalDate mes;
        System.out.println("PRESTAMOS realizados en el mes de NOVIEMBRE:");
        for (Prestamo p : prestamos) {
            
        }
    }
//</editor-fold>
    
    public static void cargaDatos(){
        
        libros.add(new Libro("1-11","El Hobbit","JRR Tolkien","Aventuras",3)); 
        libros.add(new Libro("1-22","El Silmarillon","JRR Tolkien","Aventuras",3)); 
        libros.add(new Libro("1-33","El Medico","N. Gordon","Aventuras",4)); 
        libros.add(new Libro("1-44","Chaman","N. Gordon","Aventuras",3)); 
        libros.add(new Libro("1-55","Momo","M. Ende","Aventuras",2)); 
        libros.add(new Libro("1-66","Paraiso inhabitado","A.M.Matute","Aventuras",2)); 
        libros.add(new Libro("1-77","Olvidado Rey Gudu","A.M.Matute","Aventuras",0)); 
        libros.add(new Libro("1-88","El ultimo barco","D.Villar","Novela Negra",3)); 
        libros.add(new Libro("1-99","Ojos de agua","D.Villar","Novela Negra",0)); 

        usuarios.add(new Usuario("11","Ana","ana@email.com","621111111")); 
        usuarios.add(new Usuario("22","David","david@email.com","622222222")); 
        usuarios.add(new Usuario("33","Bea","bea@email.com","623333333")); 
        usuarios.add(new Usuario("44","Lucas","lucas@email.com","624444444")); 
        usuarios.add(new Usuario("55","Carlota","carlota@email.com","625555555")); 
        usuarios.add(new Usuario("66","Juan","juan@email.com","626666666"));
        
        LocalDate hoy= LocalDate.now();
        prestamos.add(new Prestamo(libros.get(0),usuarios.get(0), hoy.minusDays(20),hoy.minusDays(5)));
        prestamos.add(new Prestamo(libros.get(0),usuarios.get(0), hoy,hoy.plusDays(15)));
        prestamos.add(new Prestamo(libros.get(5),usuarios.get(0), hoy,hoy.plusDays(15)));
        prestamos.add(new Prestamo(libros.get(5),usuarios.get(0), hoy.minusDays(20),hoy.minusDays(5)));
        prestamos.add(new Prestamo(libros.get(1),usuarios.get(4), hoy.minusDays(20),hoy.minusDays(5)));
        prestamos.add(new Prestamo(libros.get(2),usuarios.get(4), hoy.minusDays(20),hoy.minusDays(5)));
        prestamos.add(new Prestamo(libros.get(3),usuarios.get(4), hoy.minusDays(20),hoy.minusDays(5)));
        prestamos.add(new Prestamo(libros.get(6),usuarios.get(4), hoy,hoy.plusDays(2)));
        prestamos.add(new Prestamo(libros.get(6),usuarios.get(1), hoy,hoy.plusDays(5)));
        
        //PRESTAMOS QUE YA TENIAN QUE HABER SIDO DEVUELTOS PORQUE SU FECHA DE DEVOLUCIÓN ES ANTERIOR A HOY
        prestamos.add(new Prestamo(libros.get(5),usuarios.get(1), hoy.minusDays(17),hoy.minusDays(2)));
        prestamos.add(new Prestamo(libros.get(1),usuarios.get(4), hoy.minusDays(18),hoy.minusDays(3)));
        prestamos.add(new Prestamo(libros.get(2),usuarios.get(4), hoy.minusDays(20),hoy.minusDays(5)));
        prestamos.add(new Prestamo(libros.get(3),usuarios.get(4), hoy.minusDays(20),hoy.minusDays(5)));
        
        //PRESTAMOS HISTORICOS QUE YA HAN SIDO DEVUELTOS Y POR TANTO ESTÁN EN LA COLECCION prestamosHist
	prestamosHist.add(new Prestamo(libros.get(0),usuarios.get(0), hoy.minusDays(20),hoy.minusDays(5)));
        prestamosHist.add(new Prestamo(libros.get(2),usuarios.get(0), hoy.minusDays(20),hoy.minusDays(5)));
        prestamosHist.add(new Prestamo(libros.get(7),usuarios.get(4), hoy.minusDays(20),hoy.minusDays(5)));
        prestamosHist.add(new Prestamo(libros.get(5),usuarios.get(4), hoy.minusDays(20),hoy.minusDays(5)));
        prestamosHist.add(new Prestamo(libros.get(1),usuarios.get(1), hoy.minusDays(20),hoy.minusDays(5)));
        prestamosHist.add(new Prestamo(libros.get(7),usuarios.get(2), hoy.minusDays(15),hoy));
        prestamosHist.add(new Prestamo(libros.get(6),usuarios.get(3), hoy.minusDays(15),hoy));
    }
   
    public static void uno(){
        System.out.print("DNI usuario para consultar prestamos: ");
        String dni = sc.next();
        int posUsuario = buscaUsuarios(dni);
        if (posUsuario == -1) {
            System.out.println("Usuario no encontrado.");
        } else {
            
            System.out.println("Prestamos activos de: " + usuarios.get(posUsuario).getNombre());
            for (Prestamo p : prestamos) {
                if (p.getUsuarioPrest().getDni().equalsIgnoreCase(dni)) {
                    System.out.println(p);
                }
            }
                    
                    
            System.out.println("Prestamos históricos de: " + usuarios.get(posUsuario).getNombre());

            for (Prestamo p : prestamosHist) {
                if (p.getUsuarioPrest().getDni().equalsIgnoreCase(dni)) {
                    System.out.println(p);
                }
            }
        }
}
    
    //<editor-fold defaultstate="collapsed" desc="Listados">
    

    public static void mostrarLibros(){
        for (Libro l : libros) {
            System.out.println("\n" + l.getIsbn() + " - " + l.getTitulo() + " - " + l.getAutor() + " - " + l.getGenero() + " - " + l.getEjemplares());
        }
    }
    
    public static void mostrarUsuarios(){
        for (Usuario u : usuarios) {
            System.out.println("\n" + u.getDni() + " - " + u.getNombre() + " - " + u.getEmail() + " - " + u.getTelefono());
        }
    }
    
    public static void mostrarPrestamos(){
        for (Prestamo p : prestamos) {
            System.out.println("\n" + p.getLibroPrest() + " - " + p.getUsuarioPrest() + " - " + p.getFechaPrest() + " - " + p.getFechaDev());
        }
    }
    
    public static void librosNoDevueltos(){
        
        
        
        
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="MENÚ">
    public static void menúGeneral(){
        int opciones;
        do {            
            System.out.println("\n\t\t\tMENÚ GENERAL");
            System.out.println("\t\t\t1 - GESTIONAR LIBROS");
            System.out.println("\t\t\t2 - GESTIONAR USUARIOS");
            System.out.println("\t\t\t3 - GESTIONAR PRÉSTAMOS");
            System.out.println("\t\t\t4 - VER LISTADOS");
            System.out.println("\t\t\t9  - SALIR");
            opciones = sc.nextInt();
            switch (opciones) {
            case 1:
                menúLibros();
                break;
            case 2:
                menúUsuarios();
            break;
            case 3:
                menúPrestamos();
                break;
            case 4:
                menúListados();
                break;
            }
        } while (opciones !=9);
    }
    
    public static void menúLibros(){
        int opciones;
        do {            
            System.out.println("\n\t\t\t\tMENÚ LIBROS");
            System.out.println("\t\t\t1 - AÑADIR LIBRO");
            System.out.println("\t\t\t2 - LISTADO LIBROS");
            System.out.println("\t\t\t3 - MODIFICAR LIBRO");
            System.out.println("\t\t\t4 - ELIMINAR LIBRO");
            System.out.println("\t\t\t9  - SALIR");
            opciones = sc.nextInt();
            switch (opciones) {
            case 1:
                añadirLibros();
                break;
            case 2:
               mostrarLibros();
            break;
            case 3:
                modificarLibro();
                break;
            case 4:
                eliminarLibro();
                break;
            }
        } while (opciones !=9);
    }
    
    public static void menúUsuarios(){
        int opciones;
        do {            
            System.out.println("\n\t\t\t\tMENÚ USUARIOS");
            System.out.println("\t\t\t1 - AÑADIR USUARIOS");
            System.out.println("\t\t\t2 - LISTADO USUARIOS");
            System.out.println("\t\t\t3 - MODIFICAR USUARIOS");
            System.out.println("\t\t\t4 - ELIMINAR USUARIO");
            System.out.println("\t\t\t9  - SALIR");
            opciones = sc.nextInt();
            switch (opciones) {
            case 1:
                añadirUsuarios();
                break;
            case 2:
                mostrarUsuarios();
            break;
            case 3:
                modificarUsuario();
                break;
            case 4:
                eliminarUsuario();
                break;
            }
        } while (opciones !=9);
    }
    
    public static void menúPrestamos(){
        int opciones;
        do {            
            System.out.println("\n\t\t\t\tMENÚ PRÉSTAMOS");
            System.out.println("\t\t\t1 - PRESTAMO");
            System.out.println("\t\t\t2 - DEVOLUCIÓN");
            System.out.println("\t\t\t2 - LISTADO PRÉSTAMOS");
            System.out.println("\t\t\t9 - SALIR");
            opciones = sc.nextInt();
            switch (opciones) {
            case 1:
                NuevoPrestamo();
                break;
            case 2:
                devolucion();
                break;
            case 3:
                mostrarPrestamos();
                break;
            }
        } while (opciones !=9);
    }
    
    public static void menúListados(){
        int opciones;
        do {            
            System.out.println("\n\t\t\t\tMENÚ LISTADOS");
            System.out.println("\t\t\t1 - ");
            System.out.println("\t\t\t2 - ");
            System.out.println("\t\t\t3 - ");
            System.out.println("\t\t\t4 - ");
            System.out.println("\t\t\t5 - ");
            System.out.println("\t\t\t9 - SALIR");
            opciones = sc.nextInt();
            switch (opciones) {
            case 1:
                System.out.println("");
                break;
            case 2:
                System.out.println("");
                break;
            case 3:
                System.out.println("");
                break;
            case 4:
                System.out.println("");
                break;
            case 5:
                System.out.println("");
                break;
            }
        } while (opciones !=9);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Añadir">
    
    public static void añadirLibros(){
        System.out.print("ISBN: ");
        String isbn = sc.next();
        sc.nextLine();
        System.out.print("\nTítulo: ");
        String titulo = sc.nextLine(); 
        System.out.print("\nAutor: ");
        String autor = sc.nextLine();
        System.out.print("\nGénero: ");
        String genero = sc.nextLine();
        System.out.print("\nNº de ejemplares: ");
        int ejemplares = sc.nextInt();
        libros.add(new Libro(isbn, titulo, autor, genero, ejemplares));
    }
    
    public static void añadirUsuarios(){
        System.out.print("DNI: ");
        String dni = sc.nextLine();
        sc.nextLine();
        System.out.print("Nombre: ");
        String nombre = sc.nextLine(); 
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Teléfono: ");
        String telefono = sc.nextLine();
        usuarios.add(new Usuario(dni, nombre, email, telefono));
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Modificar">
    public static void modificarLibro(){
        System.out.print("Introduce el ISBN del libro que desea modificar: ");
        sc.nextLine();
        String isbn = sc.nextLine();
        int p = buscaLibros(isbn);
        if (p == -1) {
            System.out.println("EL LIBRO BUSCADO NO EXISTE.");
        } else {
        System.out.print("ISBN: ");
        isbn = sc.nextLine();
        System.out.print("Título: ");
        String titulo = sc.nextLine(); 
        System.out.print("Autor: ");
        String autor = sc.nextLine();
        System.out.print("Género: ");
        String genero = sc.nextLine();
        System.out.print("Nº de ejemplares: ");
        int ejemplares = sc.nextInt();
        libros.get(p).setIsbn(isbn);
        libros.get(p).setTitulo(titulo);
        libros.get(p).setAutor(autor);
        libros.get(p).setGenero(genero);
        libros.get(p).setEjemplares(ejemplares);
        }
    }
    
    public static void modificarUsuario(){
        System.out.print("Introduce el nombre de dni que desea modificar: ");
        sc.nextLine();
        String dni = sc.nextLine();
        int p = buscaUsuarios(dni);
        if (p == -1) {
            System.out.println("EL LIBRO BUSCADO NO EXISTE.");
        } else {
        System.out.print("DNI: ");
        dni = sc.nextLine();
        System.out.print("Nombre: ");
        String nombre = sc.nextLine(); 
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Teléfono: ");
        String telefono = sc.nextLine();
        usuarios.get(p).setDni(dni);
        usuarios.get(p).setNombre(nombre);
        usuarios.get(p).setEmail(email);
        usuarios.get(p).setTelefono(telefono);
        }
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Eliminar">
    public static void eliminarLibro(){
        System.out.print("Introduce el ISBN del libro para eliminarlo: ");
        sc.nextLine();
        String isbn = sc.nextLine();
        int p = buscaLibros(isbn);
        if (p == -1) {
            System.out.println("El libro no existe.");
        } else{
            libros.remove(p);
            System.out.println("Libro eliminado.");
        }
    }
    
    public static void eliminarUsuario(){
        System.out.print("Introduce el ISBN del libro para eliminarlo: ");
        sc.nextLine();
        String dni = sc.nextLine();
        int p = buscaUsuarios(dni);
        if (p == -1) {
            System.out.println("El usuario no existe.");
        } else{
            libros.remove(p);
            System.out.println("Usuario eliminado.");
        }
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Busqueda">
    public static int buscaLibros(String isbn){
        int pos = -1;
        int p = 0;
        for (Libro l : libros) {
            if (l.getIsbn().equalsIgnoreCase(isbn)) {
                pos = p;
                break;
            }
            p++;
        }
        return p;
    }
    
    public static int buscaUsuarios(String dni){
        int pos = -1;
        int p = 0;
        for (Usuario u : usuarios) {
            if (u.getDni().equalsIgnoreCase(dni)) {
                pos = p;
                break;
            }
            p++;
        }
        return p;
    }
    
    public static int buscaPrestamos(String isbn, String dni){
        int pos = -1;
        int po = 0;
        for (Prestamo p : prestamos) {
            if (p.getLibroPrest().equals(isbn) && p.getUsuarioPrest().equals(dni)) {
                pos = po;
                break;
            }
            po++;
        }
        return po;
    }
    //</editor-fold>
   
    //<editor-fold defaultstate="collapsed" desc="Préstamos y devoluciones">
    public static void NuevoPrestamo(){
        String dni,isbn;
        int posUsuario, posLibro;
        System.out.print("Teclea DNI usuario:");
        dni=sc.next();
        posUsuario=buscaUsuarios(dni);
        
        if (posUsuario == -1){
            System.out.println("Ese usuario no existe");
        }else{
            System.out.print("Teclea ISBN libro:");
            isbn=sc.next();
            
            try {
                posLibro = stockLibro(isbn); //LLAMO AL MÉTODO stockLibro, PUEDEN SALIR 2 EXCEPCIONES
                LocalDate hoy= LocalDate.now();
                prestamos.add(new Prestamo(libros.get(posLibro), usuarios.get(posUsuario), hoy,hoy.plusDays(15)));
                libros.get(posLibro).setEjemplares(libros.get(posLibro).getEjemplares()-1);
            } catch (LibroNoExiste | LibroNoDisponible ex) { //para hacer "|" es con altgr + 1.
                System.out.println(ex.getMessage());
            }
        }
    }
    
    public static void prorroga(){
        System.out.print("DNI usuario: ");
            sc.nextLine();
            String dni = sc.next();
            System.out.print("ISBN libro: ");
            String isbn = sc.nextLine();
            
        int posPrestamo = buscaPrestamos(dni, isbn);
        
        if (posPrestamo == -1) {
            System.out.println("No encontramos el préstamo"); 
        } else {
            System.out.println("Préstamo encontrado");
            prestamos.get(posPrestamo).setFechaDev(prestamos.get(posPrestamo).getFechaDev().plusDays(15));
            System.out.println("Préstamo ampliado a 15 días más.");
        }
    }
    
    public static void devolucion(){
            System.out.print("DNI usuario: ");
            sc.nextLine();
            String dni = sc.next();
            System.out.print("ISBN libro: ");
            String isbn = sc.nextLine();
            
            int posPrestamo = buscaPrestamos(isbn, dni);
            
            if (posPrestamo == -1) {
                System.out.println("Prestamo no encontrado.");
        } else {
                System.out.println("Prestamo encontrado");
                prestamos.get(posPrestamo).setFechaDev(LocalDate.now());
                libros.get(buscaLibros(isbn)).setEjemplares(libros.get(buscaLibros(isbn)).getEjemplares()+1);
                prestamosHist.add(prestamos.get(posPrestamo));
                prestamos.remove(posPrestamo);
        }
}
//</editor-fold>

    //PROGRAMACIÓN FUNCIONAL
    //Tipos de listados
    public static void listadosConStreams(){
        //1. Listado general.
        System.out.println("\n\nLibros listados desde un stream");
        libros.stream().forEach(l->System.out.println(l)); //traducido sería: para cada libro, imprime.
        //Eso de arriba, es lo mismo que lo de abajo.
        /*for (Libro l : libros) {
            System.out.println(l); 
        }*/
           System.out.println("\n\nUsuarios listados desde un stream");
        usuarios.stream()
                .forEach(u->System.out.println(u)); //se puede "dividir" la línea de código.
        
        
        //Listados selectivos (filter) con STREAMS.
            //Así sería como filtrariamos un libro de género aventuras de normal:
            System.out.println("\n\n\nLibros filtrados sin uso de un stream:");
        for (Libro l : libros) {
            if (l.getGenero().equalsIgnoreCase("aventuras")) {
                System.out.println(l);
            }
        }
        //Ahora vamos a hacerlo en programación funcional (filter):
        System.out.println("\n\nLibros filtrados por el género: aventuras.");
        libros.stream().filter(l->l.getGenero().equalsIgnoreCase("aventuras"))
                .forEach(l->System.out.println(l));
        /*Puedes cambiar la cadena de carácteres "aventuras" por otras. No tiene 
        por qué ser sobre el género, también puedes poner "JRR Tolkien" para filtrar por
        autor. EJEMPLO:*/
        System.out.println("\n\nLibros filtrados por el autor: JRR Tolkien.");
        libros.stream().filter(l->l.getAutor().equalsIgnoreCase("jrr Tolkien"))
                .forEach(l->System.out.println(l));
        /*O también puedes combinarlo. EJEMPLO:*/
        System.out.println("\n\nLibros filtrados por el género: aventuras, y por el autor:"
                + "JRR Tolkien.");
        libros.stream().filter(l-> l.getGenero().equalsIgnoreCase("aventuras")
                && l.getAutor().equalsIgnoreCase("jrr Tolkien"))
                .forEach(l->System.out.println(l));
        
        //LIBROS FUERA DE PLAZO CON STREAMS
        System.out.println("\n\nListado con stream de prestamos fuera de plazo:");
        prestamos.stream().filter(p->p.getFechaDev().isBefore(LocalDate.now()))
                .forEach(p->System.out.println(p));
        
        //PRESTAMOS ACTIVOS Y NO ACTIVOS CON STREAMS
        System.out.print("\n\n\n\nListado con stream de prestamos activos y "
                + "no activos de un usuario (teclear NOMBRE):");
        String nombre = sc.next();
        System.out.println("\nBuscando los prestamos activos de: " + nombre);
        
        prestamos.stream().filter(p->p.getUsuarioPrest().getNombre().equalsIgnoreCase(nombre))
                .forEach(p->System.out.println(p));
        System.out.println("\nBuscando los prestamos NO activos de: " + nombre);
        prestamosHist.stream().filter(pHist->pHist.getUsuarioPrest().getNombre().equalsIgnoreCase(nombre))
                .forEach(p->System.out.println(p));
        
        System.out.println("\n\n\nListado con stream de libros activos del género: aventuras.");
        
        prestamos.stream().filter(p->p.getLibroPrest().getGenero().equalsIgnoreCase("Aventuras"))
                .forEach(p->System.out.println(p));
        
    }
    
    public static void ordenacionesConStreams(){
        System.out.println("Listado de libros alfabéticamente por ");
        libros.stream().sorted().forEach(l->System.out.println(l)); 
//el sorted funciona solo si le dices a la clase Libro que es comparable y cómo (CompareTo).
    }
}