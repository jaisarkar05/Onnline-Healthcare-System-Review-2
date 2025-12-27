package com.healthcare.servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.healthcare.dao.AppointmentDAO;

@WebServlet("/cancelAppointment")
public class CancelAppointmentServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // ===============================
        // Session + role check
        // ===============================
        HttpSession session = request.getSession(false);
        String role = (session != null) ? (String) session.getAttribute("role") : null;

        if (role == null || !role.equals("PATIENT")) {
            response.sendRedirect("login.jsp");
            return;
        }

        // ===============================
        // Read appointment ID
        // ===============================
        int appointmentId = Integer.parseInt(request.getParameter("appointmentId"));

        // ===============================
        // Cancel appointment
        // ===============================
        AppointmentDAO appointmentDAO = new AppointmentDAO();
        boolean cancelled = appointmentDAO.cancelAppointment(appointmentId);

        // ===============================
        // AFTER CANCEL SUCCESS
        // ===============================
        if (cancelled) {
            // success message (optional)
            session.setAttribute("successMsg", "Appointment cancelled successfully.");

            //  THIS IS STEP 1 (EMAIL ANIMATION TRIGGER)
            session.setAttribute("emailSent", "cancel");
        }

        // ===============================
        // Redirect back
        // ===============================
        response.sendRedirect("patient-appointments.jsp");
    }
}
