package es.dwes.dentista;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class ClinicDao {
    public List<Patient> findPatients() throws SQLException {
        String sql = "SELECT p.id_paciente, p.nombre, p.apellidos, p.dni, p.fecha_nacimiento, "
                + "p.telefono, p.email, p.direccion, p.alergias, "
                + "(SELECT COUNT(*) FROM cita c WHERE c.id_paciente = p.id_paciente) AS num_citas "
                + "FROM paciente p ORDER BY p.apellidos, p.nombre";
        List<Patient> patients = new ArrayList<>();
        try (Connection connection = Database.getConnection();
             Statement statement = connection.createStatement();
             ResultSet results = statement.executeQuery(sql)) {
            while (results.next()) {
                patients.add(new Patient(results.getInt("id_paciente"), results.getString("nombre"),
                        results.getString("apellidos"), results.getString("dni"),
                        results.getString("fecha_nacimiento"), results.getString("telefono"),
                        results.getString("email"), results.getString("direccion"),
                        results.getString("alergias"), results.getInt("num_citas")));
            }
        }
        return patients;
    }

    public List<Patient> findPatientsForSelect() throws SQLException {
        List<Patient> patients = new ArrayList<>();
        try (Connection connection = Database.getConnection();
             Statement statement = connection.createStatement();
             ResultSet results = statement.executeQuery(
                     "SELECT id_paciente, nombre, apellidos FROM paciente ORDER BY apellidos, nombre")) {
            while (results.next()) {
                patients.add(new Patient(results.getInt("id_paciente"), results.getString("nombre"),
                        results.getString("apellidos"), null, null, null, null, null, null, 0));
            }
        }
        return patients;
    }

    public List<Dentist> findDentists() throws SQLException {
        List<Dentist> dentists = new ArrayList<>();
        try (Connection connection = Database.getConnection();
             Statement statement = connection.createStatement();
             ResultSet results = statement.executeQuery(
                     "SELECT id_odontologo, nombre, especialidad FROM odontologos ORDER BY nombre")) {
            while (results.next()) {
                dentists.add(new Dentist(results.getInt("id_odontologo"), results.getString("nombre"),
                        results.getString("especialidad")));
            }
        }
        return dentists;
    }

    public List<Appointment> findAppointments(boolean all) throws SQLException {
        String sql = "SELECT c.id_cita, c.fecha, c.hora, c.motivo, p.nombre, p.apellidos, o.nombre AS odontologo "
                + "FROM cita c JOIN paciente p ON p.id_paciente = c.id_paciente "
                + "JOIN odontologos o ON o.id_odontologo = c.id_odontologo "
                + (all ? "" : "WHERE c.fecha >= CURDATE() ") + "ORDER BY c.fecha, c.hora";
        List<Appointment> appointments = new ArrayList<>();
        try (Connection connection = Database.getConnection();
             Statement statement = connection.createStatement();
             ResultSet results = statement.executeQuery(sql)) {
            while (results.next()) {
                String time = results.getString("hora");
                appointments.add(new Appointment(results.getInt("id_cita"), results.getString("fecha"),
                        time == null || time.length() <= 5 ? time : time.substring(0, 5),
                        results.getString("apellidos") + ", " + results.getString("nombre"),
                        results.getString("motivo"), results.getString("odontologo")));
            }
        }
        return appointments;
    }

    public void addPatient(String name, String surname, String dni, String birthDate, String phone,
                           String email, String address, String allergies) throws SQLException {
        String sql = "INSERT INTO paciente (nombre, apellidos, dni, fecha_nacimiento, telefono, email, direccion, alergias) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = Database.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, surname);
            statement.setString(3, dni);
            if (birthDate == null || birthDate.isBlank()) {
                statement.setNull(4, Types.DATE);
            } else {
                statement.setDate(4, java.sql.Date.valueOf(birthDate));
            }
            statement.setString(5, phone);
            statement.setString(6, email);
            statement.setString(7, address);
            statement.setString(8, allergies);
            statement.executeUpdate();
        }
    }

    public void deletePatient(int id) throws SQLException {
        delete("DELETE FROM paciente WHERE id_paciente = ?", id);
    }

    public void addAppointment(String date, String time, int patientId, int dentistId, String reason) throws SQLException {
        String sql = "INSERT INTO cita (fecha, hora, id_paciente, id_odontologo, motivo) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = Database.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDate(1, java.sql.Date.valueOf(date));
            statement.setTime(2, java.sql.Time.valueOf(time.length() == 5 ? time + ":00" : time));
            statement.setInt(3, patientId);
            statement.setInt(4, dentistId);
            statement.setString(5, reason);
            statement.executeUpdate();
        }
    }

    public void deleteAppointment(int id) throws SQLException {
        delete("DELETE FROM cita WHERE id_cita = ?", id);
    }

    private void delete(String sql, int id) throws SQLException {
        try (Connection connection = Database.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }
}