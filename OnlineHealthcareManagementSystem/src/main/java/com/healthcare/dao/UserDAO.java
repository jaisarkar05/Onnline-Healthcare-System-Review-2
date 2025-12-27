package com.healthcare.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.healthcare.model.User;
import com.healthcare.util.DBConnection;

public class UserDAO {

    // =========================
    // LOGIN + SESSION USER FETCH
    // =========================
	public User validateUser(String email, String password) {

	    User user = null;

	    String sql = """
	        SELECT user_id, name, email, role, status, availability_status
	        FROM users
	        WHERE email = ? AND password = ?
	    """;

	    try (
	        Connection con = DBConnection.getConnection();
	        PreparedStatement ps = con.prepareStatement(sql)
	    ) {

	        ps.setString(1, email);
	        ps.setString(2, password);

	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            user = new User();
	            user.setUserId(rs.getInt("user_id"));
	            user.setName(rs.getString("name"));
	            user.setEmail(rs.getString("email"));
	            user.setRole(rs.getString("role"));
	            user.setStatus(rs.getString("status"));
	            user.setAvailabilityStatus(rs.getString("availability_status"));
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return user;
	}



    // =========================
    // CHECK EMAIL EXISTS (SIGNUP)
    // =========================
    public boolean emailExists(String email) {

        String sql = "SELECT user_id FROM users WHERE email = ?";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // =========================
    // ADD USER (SIGNUP / ADMIN)
    // =========================
    public boolean addUser(User user) {

        String sql = "INSERT INTO users (name, email, password, role, status) " +
                     "VALUES (?, ?, ?, ?, ?)";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getRole());
            ps.setString(5, user.getStatus());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // =========================
    // ACTIVATE / DEACTIVATE USER (ADMIN)
    // =========================
    public boolean updateUserStatus(int userId, String status) {

        String sql = "UPDATE users SET status = ? WHERE user_id = ?";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, status);
            ps.setInt(2, userId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // =========================
    // GET ALL USERS (ADMIN)
    // =========================
    public List<User> getAllUsers() {

        List<User> users = new ArrayList<>();

        String sql = "SELECT user_id, name, email, role, status FROM users";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setRole(rs.getString("role"));
                user.setStatus(rs.getString("status"));
                users.add(user);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return users;
    }
 // Get all active doctors
    public List<User> getActiveDoctors() {

        List<User> doctors = new ArrayList<>();

        String sql = "SELECT user_id, name FROM users WHERE role='DOCTOR' AND status='ACTIVE'";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                User doctor = new User();
                doctor.setUserId(rs.getInt("user_id"));
                doctor.setName(rs.getString("name"));
                doctors.add(doctor);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return doctors;
    }
    public List<User> getAvailableDoctors() {

        List<User> doctors = new ArrayList<>();

        String sql = """
            SELECT user_id, name
            FROM users
            WHERE role = 'DOCTOR'
              AND status = 'ACTIVE'
              AND availability_status = 'AVAILABLE'
        """;

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                User doctor = new User();
                doctor.setUserId(rs.getInt("user_id"));
                doctor.setName(rs.getString("name"));
                doctors.add(doctor);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return doctors;
    }

    public List<User> getDoctorsByIds(List<Integer> doctorIds) {

        List<User> doctors = new ArrayList<>();

        if (doctorIds == null || doctorIds.isEmpty()) {
            return doctors;
        }

        StringBuilder sql = new StringBuilder(
            "SELECT user_id, name FROM users WHERE role='DOCTOR' AND user_id IN ("
        );

        for (int i = 0; i < doctorIds.size(); i++) {
            sql.append("?");
            if (i < doctorIds.size() - 1) sql.append(",");
        }
        sql.append(")");

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql.toString())
        ) {
            for (int i = 0; i < doctorIds.size(); i++) {
                ps.setInt(i + 1, doctorIds.get(i));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User doctor = new User();
                doctor.setUserId(rs.getInt("user_id"));
                doctor.setName(rs.getString("name"));
                doctors.add(doctor);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return doctors;
    }
 // Toggle doctor availability
    public boolean updateDoctorAvailability(int doctorId, String availability) {

        String sql = "UPDATE users SET availability_status = ? WHERE user_id = ? AND role = 'DOCTOR'";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setString(1, availability);
            ps.setInt(2, doctorId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }




}
