<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.healthcare.dao.UserDAO" %>
<%@ page import="com.healthcare.model.User" %>

<%
    String role = (String) session.getAttribute("role");
    if (role == null || !"ADMIN".equals(role)) {
        response.sendRedirect("login.jsp");
        return;
    }

    Integer loggedInAdminId = (Integer) session.getAttribute("userId");

    UserDAO userDAO = new UserDAO();
    List<User> users = userDAO.getAllUsers();
%>

<!DOCTYPE html>
<html>
<head>
    <title>Admin – All Users</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<div class="dashboard-container">

    <h1>Admin Dashboard</h1>
    <p class="subtitle">All Users</p>

    <div class="two-column-layout">

        <!-- ================= ACTIVE USERS ================= -->
        <div class="column">
            <h3>🟢 Active Users</h3>

            <div class="card table-card">
                <table class="styled-table">
                    <thead>
                        <tr>
                            <th>User ID</th>
                            <th>Name</th>
                            <th>Email</th>
                            <th>Role</th>
                            <th>Status</th>
                            <th>Action</th>
                        </tr>
                    </thead>

                    <tbody>
                    <% for (User u : users) {
                        if ("ACTIVE".equals(u.getStatus())) {
                    %>
                        <tr>
                            <td><%= u.getUserId() %></td>
                            <td><%= u.getName() %></td>
                            <td><%= u.getEmail() %></td>

                            <td>
                                <span class="role-badge <%= u.getRole().toLowerCase() %>">
                                    <%= u.getRole() %>
                                </span>
                            </td>

                            <td>
                                <span class="status-badge active">ACTIVE</span>
                            </td>

                            <td>

                                <%--  No admin can be deactivated (including self) --%>
                                <% if ("ADMIN".equals(u.getRole())) { %>

                                    ---

                                <% } else if (u.getUserId() == loggedInAdminId) { %>

                                    ---

                                <% } else { %>

                                    <form action="updateUserStatus" method="post">
                                        <input type="hidden" name="userId" value="<%= u.getUserId() %>">
                                        <input type="hidden" name="status" value="INACTIVE">
                                        <button class="btn-danger">Deactivate</button>
                                    </form>

                                <% } %>

                            </td>
                        </tr>
                    <% } } %>
                    </tbody>
                </table>
            </div>
        </div>

        <!-- ================= INACTIVE USERS ================= -->
        <div class="column">
            <h3>🔴 Inactive Users</h3>

            <div class="card table-card">
                <table class="styled-table">
                    <thead>
                        <tr>
                            <th>User ID</th>
                            <th>Name</th>
                            <th>Email</th>
                            <th>Role</th>
                            <th>Status</th>
                            <th>Action</th>
                        </tr>
                    </thead>

                    <tbody>
                    <% for (User u : users) {
                        if ("INACTIVE".equals(u.getStatus())) {
                    %>
                        <tr>
                            <td><%= u.getUserId() %></td>
                            <td><%= u.getName() %></td>
                            <td><%= u.getEmail() %></td>

                            <td>
                                <span class="role-badge <%= u.getRole().toLowerCase() %>">
                                    <%= u.getRole() %>
                                </span>
                            </td>

                            <td>
                                <span class="status-badge inactive">INACTIVE</span>
                            </td>

                            <td>

                                <%--  Admin cannot be re-activated here either --%>
                                <% if ("ADMIN".equals(u.getRole())) { %>

                                    ---

                                <% } else { %>

                                    <form action="updateUserStatus" method="post">
                                        <input type="hidden" name="userId" value="<%= u.getUserId() %>">
                                        <input type="hidden" name="status" value="ACTIVE">
                                        <button class="btn-success">Activate</button>
                                    </form>

                                <% } %>

                            </td>
                        </tr>
                    <% } } %>
                    </tbody>
                </table>
            </div>
        </div>

    </div>

    <div class="action-buttons">
        <a href="admin.jsp" class="btn btn-back">⬅ Back to Dashboard</a>
        <a href="logout" class="btn btn-logout">Logout</a>
    </div>

</div>

</body>
</html>
