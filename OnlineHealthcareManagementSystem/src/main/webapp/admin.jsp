<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String role = (String) session.getAttribute("role");

    // If not logged in OR not admin → go to login page
    if (role == null || !role.equals("ADMIN")) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="css/style.css">
    
</head>
<body>

<div class="dashboard-container">
<h1>Admin Dashboard</h1>
<p>Welcome Admin</p>

<br><br>
<div class="dashboard-grid">

    <a href="admin-appointments.jsp" class="dashboard-card">
        📅 View All Appointments
    </a>

    <a href="admin-users.jsp" class="dashboard-card">
        👥 View All Users
    </a>

    <a href="create-doctor.jsp" class="dashboard-card">
        🩺 Create Doctor Account
    </a>

    <a href="create-admin.jsp" class="dashboard-card">
        🛡 Create Admin Account
    </a>

</div>

<br><br>

<div class="card logout">
<a href="logout">Logout</a>
</div>
</div>
</body>
</html>
