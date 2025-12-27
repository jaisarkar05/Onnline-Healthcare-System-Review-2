package com.healthcare.servlet;

import java.io.IOException;

import com.healthcare.dao.UserDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/updateUserStatus")
public class UpdateUserStatusServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Session + role check
        HttpSession session = request.getSession(false);
        Integer loggedInAdminId = (Integer) session.getAttribute("userId");
        int targetUserId = Integer.parseInt(request.getParameter("userId"));

        if (loggedInAdminId != null && loggedInAdminId == targetUserId) {
            response.sendRedirect("admin-users.jsp");
            return;
        }

        String role = (session != null) ? (String) session.getAttribute("role") : null;

        if (role == null || !role.equals("ADMIN")) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Read data
        int userId = Integer.parseInt(request.getParameter("userId"));
        String status = request.getParameter("status");

        UserDAO userDAO = new UserDAO();
        userDAO.updateUserStatus(userId, status);

        // Redirect back to users page
        response.sendRedirect("admin-users.jsp");
    }
}
