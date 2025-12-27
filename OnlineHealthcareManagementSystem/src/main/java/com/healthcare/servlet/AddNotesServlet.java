package com.healthcare.servlet;

import java.io.IOException;

import com.healthcare.dao.AppointmentDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/addNotes")
public class AddNotesServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // =========================
        // SESSION + ROLE CHECK
        // =========================
        HttpSession session = request.getSession(false);
        String role = (session != null) ? (String) session.getAttribute("role") : null;

        if (role == null || !role.equals("DOCTOR")) {
            response.sendRedirect("login.jsp");
            return;
        }

        // =========================
        // READ FORM DATA
        // =========================
        int appointmentId = Integer.parseInt(request.getParameter("appointmentId"));
        String doctorNotes = request.getParameter("doctorNotes");
        String prescription = request.getParameter("prescription");
        System.out.println("Doctor Notes = " + doctorNotes);
        System.out.println("Prescription = " + prescription);


        // =========================
        // TRANSACTIONAL DAO CALL
        // =========================
        AppointmentDAO appointmentDAO = new AppointmentDAO();

        boolean success = appointmentDAO.saveDoctorNotesWithTransaction(
                appointmentId,
                doctorNotes,
                prescription
        );

        if (!success) {
            response.getWriter().println("Error saving notes");
            return;
        }

        response.sendRedirect("doctor-appointments.jsp");


    }
}
