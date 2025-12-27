package com.healthcare.servlet;

import java.io.IOException;

import com.healthcare.dao.AppointmentDAO;
import com.healthcare.thread.EmailNotificationThread;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.sql.Date;
import java.sql.Time;

@WebServlet("/bookAppointment")
public class AppointmentBookingServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            int patientId = (int) session.getAttribute("userId");

            int doctorId = Integer.parseInt(request.getParameter("doctorId"));
            Date appointmentDate = Date.valueOf(request.getParameter("date"));
            Time appointmentTime = Time.valueOf(request.getParameter("time") + ":00");

            AppointmentDAO appointmentDAO = new AppointmentDAO();

            // Hourly conflict check
            boolean conflict = appointmentDAO.isDoctorBookedInHour(
                    doctorId, appointmentDate, appointmentTime
            );

            if (conflict) {
                session.setAttribute(
                    "errorMsg",
                    "Doctor is already booked for this time. Please choose another slot."
                );
                response.sendRedirect("book-appointment.jsp");
                return;
            }


            boolean booked = appointmentDAO.bookAppointment(
                    patientId, doctorId, appointmentDate, appointmentTime
            );

            if (booked) {

                // =========================
                // START BACKGROUND THREAD
                // =========================
            
                String patientEmail = (session != null)
                        ? (String) session.getAttribute("email")
                        : "patient@example.com";

                EmailNotificationThread emailThread =
                        new EmailNotificationThread(
                                patientEmail,
                                "Your appointment has been booked successfully."
                        );

                emailThread.start(); //  Thread starts here

                session.setAttribute(
                        "successMsg",
                        "Appointment booked successfully!"
                    );
                session.setAttribute("emailSent", "true");


                response.sendRedirect("book-appointment.jsp");
                    return;
            }
            else {
                session.setAttribute(
                    "errorMsg",
                    "Booking failed. Please try again."
                );
                response.sendRedirect("book-appointment.jsp");
                return;
            }


        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Server Error: " + e.getMessage());
        }
    }
}
