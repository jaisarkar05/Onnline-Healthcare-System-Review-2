package com.healthcare.dao;

import java.sql.Date;
import java.sql.Time;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.healthcare.model.Appointment;
import com.healthcare.util.DBConnection;

public class AppointmentDAO {

    // ===============================
    // ADD APPOINTMENT (Already used)
    // ===============================
    public boolean addAppointment(Appointment appointment) {

        String sql = "INSERT INTO appointments "
                   + "(patient_id, doctor_id, appointment_date, appointment_time, status) "
                   + "VALUES (?, ?, ?, ?, ?)";

        try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)
        ) {

            ps.setInt(1, appointment.getPatientId());
            ps.setInt(2, appointment.getDoctorId());
            ps.setDate(3, appointment.getAppointmentDate());
            ps.setTime(4, appointment.getAppointmentTime());
            ps.setString(5, appointment.getStatus());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    public List<Appointment> getAppointmentsByPatientId(int patientId) {

        List<Appointment> list = new ArrayList<>();

        String sql = "SELECT appointment_id, doctor_id, appointment_date, appointment_time, " +
                     "status, doctor_notes, prescription " +
                     "FROM appointments WHERE patient_id = ?";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, patientId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Appointment a = new Appointment();

                a.setAppointmentId(rs.getInt("appointment_id"));
                a.setDoctorId(rs.getInt("doctor_id"));
                a.setAppointmentDate(rs.getDate("appointment_date"));
                a.setAppointmentTime(rs.getTime("appointment_time"));
                a.setStatus(rs.getString("status"));
                a.setDoctorNotes(rs.getString("doctor_notes"));
                a.setPrescription(rs.getString("prescription"));

                list.add(a);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    

    public List<Appointment> getAppointmentsByDoctorId(int doctorId) {

        List<Appointment> list = new ArrayList<>();

        String sql = "SELECT appointment_id, patient_id, appointment_date, appointment_time, " +
                     "status, doctor_notes, prescription " +
                     "FROM appointments WHERE doctor_id = ?";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, doctorId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Appointment a = new Appointment();

                a.setAppointmentId(rs.getInt("appointment_id"));
                a.setPatientId(rs.getInt("patient_id"));
                a.setAppointmentDate(rs.getDate("appointment_date"));
                a.setAppointmentTime(rs.getTime("appointment_time"));
                a.setStatus(rs.getString("status"));
                a.setDoctorNotes(rs.getString("doctor_notes"));
                a.setPrescription(rs.getString("prescription"));

                list.add(a);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

 
 // Update appointment status (Doctor confirms appointment)
    public boolean updateAppointmentStatus(int appointmentId, String status) {

        String sql = "UPDATE appointments SET status = ? WHERE appointment_id = ?";

        try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)
        ) {

            ps.setString(1, status);
            ps.setInt(2, appointmentId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
 // Fetch all appointments (Admin)
    public List<Appointment> getAllAppointments() {

        List<Appointment> appointments = new ArrayList<>();

        String sql = "SELECT * FROM appointments";

        try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {
                Appointment appointment = new Appointment();

                appointment.setAppointmentId(rs.getInt("appointment_id"));
                appointment.setPatientId(rs.getInt("patient_id"));
                appointment.setDoctorId(rs.getInt("doctor_id"));
                appointment.setAppointmentDate(rs.getDate("appointment_date"));
                appointment.setAppointmentTime(rs.getTime("appointment_time"));
                appointment.setStatus(rs.getString("status"));

                appointments.add(appointment);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return appointments;
    }
 // Cancel appointment (Patient)
    public boolean cancelAppointment(int appointmentId) {

        String sql = "UPDATE appointments SET status = ? WHERE appointment_id = ?";

        try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)
        ) {

            ps.setString(1, "CANCELLED");
            ps.setInt(2, appointmentId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
 // Check hourly appointment conflict (max 6 per hour)
    public boolean isDoctorFullyBookedForHour(int doctorId, Date appointmentDate, Time appointmentTime) {

        String sql = """
            SELECT COUNT(*) 
            FROM appointments
            WHERE doctor_id = ?
            AND appointment_date = ?
            AND HOUR(appointment_time) = HOUR(?)
            AND status != 'CANCELLED'
        """;

        try (
            Connection connection = DBConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)
        ) {

            ps.setInt(1, doctorId);
            ps.setDate(2, appointmentDate);
            ps.setTime(3, appointmentTime);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) >= 6;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
 // Save doctor notes & prescription
    public boolean saveDoctorNotesWithTransaction(int appointmentId,
            String doctorNotes,
            String prescription) {

Connection con = null;

try {
con = DBConnection.getConnection();
con.setAutoCommit(false); //  START TRANSACTION

// Update appointments table
String updateAppointmentSQL =
"UPDATE appointments SET doctor_notes=?, prescription=?, status='COMPLETED' " +
"WHERE appointment_id=?";

try (PreparedStatement ps1 = con.prepareStatement(updateAppointmentSQL)) {
ps1.setString(1, doctorNotes);
ps1.setString(2, prescription);
ps1.setInt(3, appointmentId);
ps1.executeUpdate();
}

//  Insert into medical_records (FIXED COLUMN NAMES)
String insertMedicalSQL =
"INSERT INTO medical_records (appointment_id, doctor_notes, prescription) " +
"VALUES (?, ?, ?)";

try (PreparedStatement ps2 = con.prepareStatement(insertMedicalSQL)) {
ps2.setInt(1, appointmentId);
ps2.setString(2, doctorNotes);
ps2.setString(3, prescription);
ps2.executeUpdate();
}

con.commit(); //  COMMIT
return true;

} catch (Exception e) {
try {
if (con != null) con.rollback(); //  ROLLBACK
} catch (Exception ex) {
ex.printStackTrace();
}
e.printStackTrace();
return false;

} finally {
try {
if (con != null) {
con.setAutoCommit(true);
con.close();
}
} catch (Exception e) {
e.printStackTrace();
}
}
}


    public boolean isDoctorBookedInHour(int doctorId, Date date, Time time) {

        String sql = """
            SELECT COUNT(*) 
            FROM appointments
            WHERE doctor_id = ?
              AND appointment_date = ?
              AND HOUR(appointment_time) = HOUR(?)
              AND status IN ('BOOKED', 'CONFIRMED')
        """;

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, doctorId);
            ps.setDate(2, date);
            ps.setTime(3, time);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) >= 6; // max 6 patients per hour
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    public boolean bookAppointment(
            int patientId,
            int doctorId,
            Date appointmentDate,
            Time appointmentTime
    ) {

        String sql = """
            INSERT INTO appointments
            (patient_id, doctor_id, appointment_date, appointment_time, status)
            VALUES (?, ?, ?, ?, 'BOOKED')
        """;

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, patientId);
            ps.setInt(2, doctorId);
            ps.setDate(3, appointmentDate);
            ps.setTime(4, appointmentTime);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    public boolean completeAppointmentWithRecord(
            int appointmentId,
            String doctorNotes,
            String prescription
    ) {

        Connection con = null;

        try {
            con = DBConnection.getConnection();

            // START TRANSACTION
            con.setAutoCommit(false);

            // Step 1: Update appointment status
            String updateAppointment =
                "UPDATE appointments SET status = 'COMPLETED' WHERE appointment_id = ?";

            try (PreparedStatement ps1 = con.prepareStatement(updateAppointment)) {
                ps1.setInt(1, appointmentId);
                ps1.executeUpdate();
            }

            // Step 2: Insert medical record
            String insertRecord =
                "INSERT INTO medical_records (appointment_id, doctor_notes, prescription) VALUES (?, ?, ?)";

            try (PreparedStatement ps2 = con.prepareStatement(insertRecord)) {
                ps2.setInt(1, appointmentId);
                ps2.setString(2, doctorNotes);
                ps2.setString(3, prescription);
                ps2.executeUpdate();
            }

            // COMMIT if both succeed
            con.commit();
            return true;

        } catch (Exception e) {

            // ROLLBACK if any step fails
            try {
                if (con != null) con.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            e.printStackTrace();
            return false;

        } finally {

            try {
                if (con != null) con.setAutoCommit(true);
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public boolean saveNotesWithTransaction(
            int appointmentId,
            String doctorNotes,
            String prescription
    ) {

        Connection con = null;

        try {
            // Get DB connection
            con = DBConnection.getConnection();

            // START TRANSACTION
            con.setAutoCommit(false);

            // =========================
            // STEP 1: UPDATE APPOINTMENT
            // =========================
            String updateAppointment =
                    "UPDATE appointments SET doctor_notes = ?, prescription = ?, status = 'COMPLETED' " +
                    "WHERE appointment_id = ?";

            try (PreparedStatement ps1 = con.prepareStatement(updateAppointment)) {
                ps1.setString(1, doctorNotes);
                ps1.setString(2, prescription);
                ps1.setInt(3, appointmentId);
                ps1.executeUpdate();
            }

            // =========================
            // STEP 2: INSERT MEDICAL RECORD
            // =========================
            String insertRecord =
                    "INSERT INTO medical_records (appointment_id, doctor_notes, prescription) " +
                    "VALUES (?, ?, ?)";

            try (PreparedStatement ps2 = con.prepareStatement(insertRecord)) {
                ps2.setInt(1, appointmentId);
                ps2.setString(2, doctorNotes);
                ps2.setString(3, prescription);
                ps2.executeUpdate();
            }

            //  COMMIT (SUCCESS)
            con.commit();
            return true;

        } catch (Exception e) {

            //️⃣ ROLLBACK (FAILURE)
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            e.printStackTrace();
            return false;

        } finally {

            //  RESET AUTOCOMMIT + CLOSE
            try {
                if (con != null) {
                    con.setAutoCommit(true);
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public List<Appointment> getAppointmentsWithMedicalRecordsByPatient(int patientId) {

        List<Appointment> list = new ArrayList<>();

        String sql =
            "SELECT a.*, m.doctor_notes, m.prescription " +
            "FROM appointments a " +
            "LEFT JOIN medical_records m " +
            "ON a.appointment_id = m.appointment_id " +
            "WHERE a.patient_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, patientId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Appointment a = new Appointment();
                a.setAppointmentId(rs.getInt("appointment_id"));
                a.setDoctorId(rs.getInt("doctor_id"));
                a.setPatientId(rs.getInt("patient_id"));
                a.setAppointmentDate(rs.getDate("appointment_date"));
                a.setAppointmentTime(rs.getTime("appointment_time"));
                a.setStatus(rs.getString("status"));
                a.setDoctorNotes(rs.getString("doctor_notes"));
                a.setPrescription(rs.getString("prescription"));
                list.add(a);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }











}
