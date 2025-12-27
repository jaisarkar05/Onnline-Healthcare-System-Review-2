package com.healthcare.servlet;

import java.io.IOException;

import com.healthcare.dao.UserDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/toggleAvailability")
public class ToggleAvailabilityServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        Integer doctorId = (Integer) session.getAttribute("userId");
        String role = (String) session.getAttribute("role");

        if (doctorId == null || !"DOCTOR".equals(role)) {
            response.sendRedirect("login.jsp");
            return;
        }

        String availability = request.getParameter("availability");

        UserDAO userDAO = new UserDAO();
        boolean updated = userDAO.updateDoctorAvailability(doctorId, availability);

        // UPDATE SESSION AFTER DB UPDATE
        if (updated) {
            session.setAttribute("availability_status", availability);
        }

        response.sendRedirect("doctor.jsp");
    }
}
