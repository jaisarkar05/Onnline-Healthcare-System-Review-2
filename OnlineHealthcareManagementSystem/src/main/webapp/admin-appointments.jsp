<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.healthcare.dao.AppointmentDAO" %>
<%@ page import="com.healthcare.model.Appointment" %>

<%
    String role = (String) session.getAttribute("role");

    // Allow only ADMIN
    if (role == null || !role.equals("ADMIN")) {
        response.sendRedirect("login.jsp");
        return;
    }

    AppointmentDAO appointmentDAO = new AppointmentDAO();
    List<Appointment> appointments = appointmentDAO.getAllAppointments();
%>

<!DOCTYPE html>
<html>
<head>
    <title>All Appointments - Admin</title>
    <link rel="stylesheet" href="css/style.css">
    
</head>
<body>

<h1>Admin Dashboard – All Appointments</h1>

<% if (appointments.isEmpty()) { %>
    <p>No appointments found.</p>
<% } else { %>

<table border="1" cellpadding="8">
    <tr>
        <th>Appointment ID</th>
        <th>Patient ID</th>
        <th>Doctor ID</th>
        <th>Date</th>
        <th>Time</th>
        <th>Status</th>
    </tr>

    <% for (Appointment a : appointments) { %>
        <tr>
            <td><%= a.getAppointmentId() %></td>
            <td><%= a.getPatientId() %></td>
            <td><%= a.getDoctorId() %></td>
            <td><%= a.getAppointmentDate() %></td>
            <td><%= a.getAppointmentTime() %></td>
            <td><span class="status <%= a.getStatus() %>">
        <%= a.getStatus() %>
    </span>
    </td>
        </tr>
    <% } %>

</table>

<% } %>

<br><br>
<div class="action-buttons">
    <a href="admin.jsp" class="btn btn-back">⬅ Back to Dashboard</a>
    <a href="logout" class="btn btn-logout">Logout</a>
</div>


</body>
</html>
