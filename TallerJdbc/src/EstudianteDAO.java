import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstudianteDAO {
    private Connection conn;

    public EstudianteDAO(Connection conn) {
        this.conn = conn;
    }

    public void insertar(Estudiante e) throws SQLException {
        String sql = "INSERT INTO estudiante (nombre, apellido, correo, edad, estadoCivil) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, e.getNombre());
        ps.setString(2, e.getApellido());
        ps.setString(3, e.getCorreo());
        ps.setInt(4, e.getEdad());
        ps.setString(5, e.getEstadoCivil());
        ps.executeUpdate();
        
    }

    public boolean actualizar(Estudiante e) throws SQLException {
        String sql = "UPDATE estudiante SET nombre=?, apellido=?, edad=?, estadoCivil=? WHERE correo=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, e.getNombre());
        ps.setString(2, e.getApellido());
        ps.setInt(3, e.getEdad());
        ps.setString(4, e.getEstadoCivil());
        ps.setString(5, e.getCorreo());
        int rows = ps.executeUpdate();
        return rows > 0;
    }

    public boolean eliminar(String correo) throws SQLException {
        String sql = "DELETE FROM estudiante WHERE correo=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, correo);
        int rows = ps.executeUpdate();
        return rows > 0;
    }

    public List<Estudiante> consultarTodos() throws SQLException {
        List<Estudiante> lista = new ArrayList<>();
        String sql = "SELECT * FROM estudiante";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            Estudiante e = new Estudiante(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("correo"),
                    rs.getInt("edad"),
                    rs.getString("estadoCivil")
            );
            lista.add(e);
        }
        return lista;
    }

    public Estudiante consultarPorCorreo(String correo) throws SQLException {
        String sql = "SELECT * FROM estudiante WHERE correo=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, correo);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return new Estudiante(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("correo"),
                    rs.getInt("edad"),
                    rs.getString("estadoCivil")
            );
        }
        return null;
    }
}
