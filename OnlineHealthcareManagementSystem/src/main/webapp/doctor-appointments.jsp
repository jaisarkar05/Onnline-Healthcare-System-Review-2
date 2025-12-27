<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.healthcare.dao.AppointmentDAO" %>
<%@ page import="com.healthcare.model.Appointment" %>

<%
    String role = (String) session.getAttribute("role");
    Integer doctorId = (Integer) session.getAttribute("userId");

    if (role == null || !"DOCTOR".equals(role)) {
        response.sendRedirect("login.jsp");
        return;
    }

    AppointmentDAO appointmentDAO = new AppointmentDAO();
    List<Appointment> appointments =
            appointmentDAO.getAppointmentsByDoctorId(doctorId);
%>

<!DOCTYPE html>
<html>
<head>
    <title>Doctor Dashboard - Appointments</title>
    <link rel="stylesheet" href="css/style.css">
    
</head>
<body>

<h2>Doctor Dashboard - Appointments</h2>

<table border="1" cellpadding="8">
<tr>
    <th>ID</th>
    <th>Patient ID</th>
    <th>Date</th>
    <th>Time</th>
    <th>Status</th>
    <th>Actions / Notes</th>
</tr>

<% for (Appointment a : appointments) { %>
<tr>
    <td><%= a.getAppointmentId() %></td>
    <td><%= a.getPatientId() %></td>
    <td><%= a.getAppointmentDate() %></td>
    <td><%= a.getAppointmentTime() %></td>
    <td><span class="status <%= a.getStatus() %>">
        <%= a.getStatus() %>
    </span>
    </td>

    <td>
    <%-- STEP 1: BOOKED → CONFIRM --%>
    <% if ("BOOKED".equals(a.getStatus())) { %>
        <form action="confirmAppointment" method="post">
            <input type="hidden" name="appointmentId"
                   value="<%= a.getAppointmentId() %>">
            <button type="submit">Confirm</button>
        </form>

    <%-- STEP 2: CONFIRMED → WRITE NOTES ONCE --%>
    <% } else if ("CONFIRMED".equals(a.getStatus())
           && (a.getDoctorNotes() == null || a.getDoctorNotes().isBlank())) { %>

        <form action="addNotes" method="post">
            <input type="hidden" name="appointmentId"
                   value="<%= a.getAppointmentId() %>">

            <textarea name="doctorNotes"
                      placeholder="Doctor Notes"
                      required></textarea><br><br>

            <textarea name="prescription"
                      placeholder="Prescription"
                      required></textarea><br><br>

            <button type="submit">Save</button>
        </form>

    <%-- STEP 3: COMPLETED → READ ONLY --%>
    <% } else if ("COMPLETED".equals(a.getStatus())) { %>

        <b>Doctor Notes:</b><br>
        <%= a.getDoctorNotes() %><br><br>

        <b>Prescription:</b><br>
        <%= a.getPrescription() %>

    <% } else { %>
        ---
    <% } %>
    </td>
</tr>
<% } %>
</table>

<br>
<div class="action-buttons">
    <a href="doctor.jsp" class="btn btn-back">⬅ Back to Dashboard</a>
    <a href="logout" class="btn btn-logout">Logout</a>
</div>


</body>
</html>
