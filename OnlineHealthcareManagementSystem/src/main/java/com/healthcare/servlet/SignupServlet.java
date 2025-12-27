package com.healthcare.servlet;

import com.healthcare.dao.UserDAO;
import com.healthcare.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/signup")
public class SignupServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // BASIC VALIDATION
        if (name == null || email == null || password == null ||
                name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            response.sendRedirect("signup.jsp");
            return;
        }

        UserDAO userDAO = new UserDAO();

        // CHECK IF EMAIL EXISTS
        if (userDAO.emailExists(email)) {
            response.setContentType("text/html");
            response.getWriter().println("<h3>Email already registered.</h3>");
            response.getWriter().println("<a href='signup.html'>Go Back</a>");
            return;
        }

        // CREATE PATIENT USER ONLY
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole("PATIENT");
        user.setStatus("ACTIVE");

        boolean created = userDAO.addUser(user);

        if (created) {
        	response.sendRedirect("login.jsp?signup=success");

        } else {
            response.getWriter().println("Signup failed. Try again.");
        }
    }
}
