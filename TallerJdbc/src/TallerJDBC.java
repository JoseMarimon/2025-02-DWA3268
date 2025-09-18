import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class TallerJDBC {
    private static final String URL = "jdbc:mysql://localhost:3306/parcial";
    private static final String USER = "root";  
    private static final String PASSWORD = "jose";  

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Scanner sc = new Scanner(System.in)) {

            EstudianteService service = new EstudianteService(conn);
            int opcion = 0;

            do {
                try {
                    mostrarMenu();
                    opcion = sc.nextInt();
                    sc.nextLine(); // limpiar buffer

                    switch (opcion) {
                        case 1 -> insertarEstudiante(sc, service);
                        case 2 -> actualizarEstudiante(sc, service);
                        case 3 -> eliminarEstudiante(sc, service);
                        case 4 -> service.consultarTodos();
                        case 5 -> consultarPorCorreo(sc, service);
                        case 6 -> System.out.println("👋 Saliendo...");
                        default -> System.out.println("❌ Opción no válida.");
                    }
                } catch (InputMismatchException ime) {
                    System.out.println("⚠️ Debes ingresar un número. Intenta de nuevo.");
                    sc.nextLine(); // limpiar entrada inválida
                }
            } while (opcion != 6);

        } catch (SQLException e) {
            System.out.println("❌ Error de conexión a la base de datos: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("❌ Error inesperado: " + e.getMessage());
        }
    }

    private static void insertarEstudiante(Scanner sc, EstudianteService service) {
        try {
            System.out.println("➡️ Insertar Estudiante (escribe 'cancelar' para volver al menú)");

            String nombre = pedirDato(sc, "Nombre");
            if (nombre == null) return;

            String apellido = pedirDato(sc, "Apellido");
            if (apellido == null) return;

            String correo = pedirDato(sc, "Correo");
            if (correo == null) return;

            int edad = pedirNumero(sc, "Edad");
            if (edad == -1) return;

            String estadoCivil = pedirDato(sc, "Estado Civil").toUpperCase();
            if (estadoCivil == null) return;

            Estudiante e = new Estudiante(nombre, apellido, correo, edad, estadoCivil);
            service.insertarEstudiante(e);
        } catch (Exception ex) {
            System.out.println("❌ Error al insertar estudiante: " + ex.getMessage());
        }
    }

    private static void actualizarEstudiante(Scanner sc, EstudianteService service) {
        try {
            System.out.println("➡️ Actualizar Estudiante (escribe 'cancelar' para volver al menú)");

            String correo = pedirDato(sc, "Correo del estudiante a actualizar");
            if (correo == null) return;

            String nombre = pedirDato(sc, "Nuevo Nombre");
            if (nombre == null) return;

            String apellido = pedirDato(sc, "Nuevo Apellido");
            if (apellido == null) return;

            int edad = pedirNumero(sc, "Nueva Edad");
            if (edad == -1) return;

            String estadoCivil = pedirDato(sc, "Nuevo Estado Civil").toUpperCase();
            if (estadoCivil == null) return;

            Estudiante e = new Estudiante(nombre, apellido, correo, edad, estadoCivil);
            service.actualizarEstudiante(e);
        } catch (Exception ex) {
            System.out.println("❌ Error al actualizar estudiante: " + ex.getMessage());
        }
    }

    private static void eliminarEstudiante(Scanner sc, EstudianteService service) {
        try {
            System.out.println("➡️ Eliminar Estudiante (escribe 'cancelar' para volver al menú)");
            String correo = pedirDato(sc, "Correo del estudiante a eliminar");
            if (correo == null) return;
            service.eliminarEstudiante(correo);
        } catch (Exception ex) {
            System.out.println("❌ Error al eliminar estudiante: " + ex.getMessage());
        }
    }

    private static void consultarPorCorreo(Scanner sc, EstudianteService service) {
        try {
            System.out.println("➡️ Consultar Estudiante por Correo (escribe 'cancelar' para volver al menú)");
            String correo = pedirDato(sc, "Correo");
            if (correo == null) return;
            service.consultarPorCorreo(correo);
        } catch (Exception ex) {
            System.out.println("❌ Error al consultar estudiante: " + ex.getMessage());
        }
    }

    private static String pedirDato(Scanner sc, String campo) {
        while (true) {
            System.out.print(campo + ": ");
            String valor = sc.nextLine().trim();
            if (valor.equalsIgnoreCase("cancelar")) {
                System.out.println("↩️ Operación cancelada, volviendo al menú...");
                return null;
            }
            if (valor.isEmpty()) {
                System.out.println("⚠️ El campo " + campo + " no puede estar vacío.");
            } else {
                return valor;
            }
        }
    }

    private static int pedirNumero(Scanner sc, String campo) {
        while (true) {
            System.out.print(campo + ": ");
            String valor = sc.nextLine().trim();
            if (valor.equalsIgnoreCase("cancelar")) {
                System.out.println("↩️ Operación cancelada, volviendo al menú...");
                return -1;
            }
            try {
                return Integer.parseInt(valor);
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Debes ingresar un número válido.");
            }
        }
    }

    private static void mostrarMenu() {
        System.out.println("\n===== MENU PRINCIPAL =====");
        System.out.println("1. Insertar Estudiante");
        System.out.println("2. Actualizar Estudiante");
        System.out.println("3. Eliminar Estudiante");
        System.out.println("4. Consultar todos los Estudiantes");
        System.out.println("5. Consultar Estudiante por email");
        System.out.println("6. Salir");
        System.out.print("Seleccione una opción: ");
    }
}
