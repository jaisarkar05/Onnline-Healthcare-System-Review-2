<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String role = (String) session.getAttribute("role");

    // If not logged in OR not patient → redirect to login
    if (role == null || !role.equals("PATIENT")) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Patient Dashboard</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<div class="dashboard-container">
<h1>Patient Dashboard</h1>
<p>Welcome Patient</p>

<div class="dashboard-grid">

<h3>Actions:</h3>

<!-- Book Appointment Link -->
<a href="book-appointment.jsp" class="dashboard-card">
        🕐 Book Appointment
    </a>


<a href="patient-appointments.jsp" class="dashboard-card">
        📅 View My Appointments
    </a>


<br><br>

<!-- Logout Link -->
<div class="card logout">
<a href="logout">Logout</a>
</div>
</div>
</div>
</body>
</html>
