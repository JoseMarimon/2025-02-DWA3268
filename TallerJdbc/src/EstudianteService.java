
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class EstudianteService {
    private EstudianteDAO estudianteDAO;

    public EstudianteService(Connection conn) {
        this.estudianteDAO = new EstudianteDAO(conn);
    }

    // Insertar estudiante
    public void insertarEstudiante(Estudiante e) {
        try {
            estudianteDAO.insertar(e);
            System.out.println("✅ Estudiante insertado correctamente");
        } catch (SQLException ex) {
            System.out.println("❌ Error al insertar estudiante: " + ex.getMessage());
        }
    }

    // Actualizar estudiante
    public void actualizarEstudiante(Estudiante e) {
        try {
            boolean actualizado = estudianteDAO.actualizar(e);
            if (actualizado) {
                System.out.println("✅ Estudiante actualizado correctamente");
            } else {
                System.out.println("⚠️ No se encontró estudiante con el correo: " + e.getCorreo());
            }
        } catch (SQLException ex) {
            System.out.println("❌ Error al actualizar estudiante: " + ex.getMessage());
        }
    }

    // Eliminar estudiante
    public void eliminarEstudiante(String correo) {
        try {
            boolean eliminado = estudianteDAO.eliminar(correo);
            if (eliminado) {
                System.out.println("✅ Estudiante eliminado correctamente");
            } else {
                System.out.println("⚠️ No se encontró estudiante con el correo: " + correo);
            }
        } catch (SQLException ex) {
            System.out.println("❌ Error al eliminar estudiante: " + ex.getMessage());
        }
    }

    // Consultar todos los estudiantes
    public void consultarTodos() {
        try {
            List<Estudiante> lista = estudianteDAO.consultarTodos();
            if (lista.isEmpty()) {
                System.out.println("⚠️ No hay estudiantes registrados.");
            } else {
                for (Estudiante e : lista) {
                    System.out.println(e);
                }
            }
        } catch (SQLException ex) {
            System.out.println("❌ Error al consultar estudiantes: " + ex.getMessage());
        }
    }

    // Consultar estudiante por correo
    public void consultarPorCorreo(String correo) {
        try {
            Estudiante e = estudianteDAO.consultarPorCorreo(correo);
            if (e != null) {
                System.out.println(e);
            } else {
                System.out.println("⚠️ No se encontró estudiante con el correo: " + correo);
            }
        } catch (SQLException ex) {
            System.out.println("❌ Error al consultar estudiante: " + ex.getMessage());
        }
    }
}
