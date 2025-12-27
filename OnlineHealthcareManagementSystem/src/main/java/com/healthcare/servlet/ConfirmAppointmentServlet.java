package com.healthcare.servlet;

import java.io.IOException;

import com.healthcare.dao.AppointmentDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/confirmAppointment")
public class ConfirmAppointmentServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Session + role check
        HttpSession session = request.getSession(false);
        String role = (session != null) ? (String) session.getAttribute("role") : null;

        if (role == null || !role.equals("DOCTOR")) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Read appointment ID
        int appointmentId = Integer.parseInt(request.getParameter("appointmentId"));

        AppointmentDAO appointmentDAO = new AppointmentDAO();
        appointmentDAO.updateAppointmentStatus(appointmentId, "CONFIRMED");

        // Redirect back to doctor appointments page
        response.sendRedirect("doctor-appointments.jsp");
    }
}
