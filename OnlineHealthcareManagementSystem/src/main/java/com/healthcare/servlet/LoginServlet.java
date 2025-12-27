package com.healthcare.servlet;

import java.io.IOException;

import com.healthcare.dao.UserDAO;
import com.healthcare.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UserDAO userDAO = new UserDAO();
        User user = userDAO.validateUser(email, password);
        System.out.println(
                "User Found: " + (user != null ? user.getEmail() : "NULL")
                + " | role=" + (user != null ? user.getRole() : "NULL")
                + " | status=" + (user != null ? user.getStatus() : "NULL")
        );


        if (user != null) {
        	

            // =============================
            // BLOCK LOGIN FOR INACTIVE USERS
            // =============================
        	if (!"ADMIN".equalsIgnoreCase(user.getRole()) &&
        		    "INACTIVE".equalsIgnoreCase(user.getStatus())) {

        		    response.sendRedirect("login.jsp?deactivated=true");
        		    return;
        		}


            HttpSession session = request.getSession(true);

            session.setAttribute("userId", user.getUserId());
            session.setAttribute("role", user.getRole());
            session.setAttribute("name", user.getName());
            session.setAttribute("email", user.getEmail());
            session.setAttribute("availability_status", user.getAvailabilityStatus());

            if ("ADMIN".equals(user.getRole())) {
                response.sendRedirect("admin.jsp");
            } else if ("DOCTOR".equals(user.getRole())) {
                response.sendRedirect("doctor.jsp");
            } else if ("PATIENT".equals(user.getRole())) {
                response.sendRedirect("patient.jsp");
            } else {
                response.sendRedirect("login.jsp");
            }

        } else {
            response.sendRedirect("login.jsp?error=true");
        }


    }
}
