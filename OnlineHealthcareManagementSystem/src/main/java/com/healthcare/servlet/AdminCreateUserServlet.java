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

@WebServlet("/adminCreateUser")
public class AdminCreateUserServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        String adminRole = (session != null) ? (String) session.getAttribute("role") : null;

        // SECURITY CHECK
        if (!"ADMIN".equals(adminRole)) {
            response.sendRedirect("login.jsp");
            return;
        }

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String role = request.getParameter("role"); // ADMIN or DOCTOR

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);
        user.setStatus("ACTIVE");

        UserDAO dao = new UserDAO();
        dao.addUser(user);

        response.sendRedirect("admin-users.jsp");
    }
}
