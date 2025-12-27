package com.healthcare.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import com.healthcare.model.DoctorAvailability;
import com.healthcare.util.DBConnection;

public class DoctorAvailabilityDAO {

    //  Add availability (Doctor)
    public boolean addAvailability(DoctorAvailability availability) {

        String sql = "INSERT INTO doctor_availability (doctor_id, day_of_week, start_time, end_time, active) " +
                     "VALUES (?, ?, ?, ?, TRUE)";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, availability.getDoctorId());
            ps.setString(2, availability.getDayOfWeek());
            ps.setTime(3, availability.getStartTime());
            ps.setTime(4, availability.getEndTime());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    //  Get availability of a doctor
    public List<DoctorAvailability> getAvailabilityByDoctor(int doctorId) {

        List<DoctorAvailability> list = new ArrayList<>();

        String sql = "SELECT * FROM doctor_availability WHERE doctor_id = ?";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, doctorId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                DoctorAvailability da = new DoctorAvailability();
                da.setAvailabilityId(rs.getInt("availability_id"));
                da.setDoctorId(rs.getInt("doctor_id"));
                da.setDayOfWeek(rs.getString("day_of_week"));
                da.setStartTime(rs.getTime("start_time"));
                da.setEndTime(rs.getTime("end_time"));
                da.setActive(rs.getBoolean("active"));
                list.add(da);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    //  Enable / Disable availability
    public boolean updateAvailabilityStatus(int availabilityId, boolean active) {

        String sql = "UPDATE doctor_availability SET active = ? WHERE availability_id = ?";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setBoolean(1, active);
            ps.setInt(2, availabilityId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // ️ Check if doctor is available (USED DURING APPOINTMENT BOOKING)
    public boolean isDoctorAvailable(int doctorId, String dayOfWeek, Time appointmentTime) {

        String sql =
            "SELECT COUNT(*) FROM doctor_availability " +
            "WHERE doctor_id = ? AND day_of_week = ? AND active = TRUE " +
            "AND ? BETWEEN start_time AND end_time";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, doctorId);
            ps.setString(2, dayOfWeek);
            ps.setTime(3, appointmentTime);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    public List<Integer> getAvailableDoctorIds() {

        List<Integer> doctorIds = new ArrayList<>();

        String sql = "SELECT DISTINCT doctor_id FROM doctor_availability WHERE available = 1";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                doctorIds.add(rs.getInt("doctor_id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return doctorIds;
    }

}
