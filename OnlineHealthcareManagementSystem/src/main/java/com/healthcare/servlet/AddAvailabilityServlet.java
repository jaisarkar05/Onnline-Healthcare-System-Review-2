package com.healthcare.servlet;

import java.io.IOException;
import java.sql.Time;

import com.healthcare.dao.DoctorAvailabilityDAO;
import com.healthcare.model.DoctorAvailability;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/addAvailability")
public class AddAvailabilityServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        String role = (String) session.getAttribute("role");
        Integer doctorIdObj = (Integer) session.getAttribute("userId");

        if (role == null || !role.equals("DOCTOR") || doctorIdObj == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int doctorId = doctorIdObj;

        String dayOfWeek = request.getParameter("dayOfWeek");
        String start = request.getParameter("startTime");
        String end = request.getParameter("endTime");

        // BASIC VALIDATION
        if (dayOfWeek == null || start == null || end == null) {
            response.sendRedirect("doctor-availability.jsp");
            return;
        }

        DoctorAvailability availability = new DoctorAvailability();
        availability.setDoctorId(doctorId);
        availability.setDayOfWeek(dayOfWeek);
        availability.setStartTime(Time.valueOf(start + ":00"));
        availability.setEndTime(Time.valueOf(end + ":00"));

        DoctorAvailabilityDAO dao = new DoctorAvailabilityDAO();
        dao.addAvailability(availability);

        response.sendRedirect("doctor-availability.jsp");
    }
}
