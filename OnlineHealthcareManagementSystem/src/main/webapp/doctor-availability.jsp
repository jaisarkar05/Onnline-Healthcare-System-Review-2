<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.Time" %>
<%@ page import="com.healthcare.dao.DoctorAvailabilityDAO" %>
<%@ page import="com.healthcare.model.DoctorAvailability" %>

<%
    String role = (String) session.getAttribute("role");
    Integer doctorIdObj = (Integer) session.getAttribute("userId");

    if (role == null || !role.equals("DOCTOR") || doctorIdObj == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    int doctorId = doctorIdObj;

    DoctorAvailabilityDAO dao = new DoctorAvailabilityDAO();
    List<DoctorAvailability> availabilityList = dao.getAvailabilityByDoctor(doctorId);
%>

<!DOCTYPE html>
<html>
<head>
    <title>Doctor Availability</title>
</head>
<body>

<h2>Manage Availability</h2>

<!-- ADD AVAILABILITY FORM -->
<form action="addAvailability" method="post">
    <label>Day:</label><br>
    <select name="dayOfWeek" required>
        <option value="MONDAY">Monday</option>
        <option value="TUESDAY">Tuesday</option>
        <option value="WEDNESDAY">Wednesday</option>
        <option value="THURSDAY">Thursday</option>
        <option value="FRIDAY">Friday</option>
        <option value="SATURDAY">Saturday</option>
        <option value="SUNDAY">Sunday</option>
    </select><br><br>

    <label>Start Time:</label><br>
    <input type="time" name="startTime" required><br><br>

    <label>End Time:</label><br>
    <input type="time" name="endTime" required><br><br>

    <button type="submit">Add Availability</button>
</form>

<hr>

<h3>Your Availability</h3>

<table border="1" cellpadding="8">
    <tr>
        <th>Day</th>
        <th>Start</th>
        <th>End</th>
        <th>Status</th>
        <th>Action</th>
    </tr>

<% for (DoctorAvailability da : availabilityList) { %>
    <tr>
        <td><%= da.getDayOfWeek() %></td>
        <td><%= da.getStartTime() %></td>
        <td><%= da.getEndTime() %></td>
        <td><%= da.isActive() ? "ACTIVE" : "INACTIVE" %></td>
        <td>
            <form action="toggleAvailability" method="post">
                <input type="hidden" name="availabilityId" value="<%= da.getAvailabilityId() %>">
                <input type="hidden" name="active" value="<%= da.isActive() ? "false" : "true" %>">
                <button type="submit">
                    <%= da.isActive() ? "Disable" : "Enable" %>
                </button>
            </form>
        </td>
    </tr>
<% } %>

</table>

<br>
<a href="doctor.jsp">Back to Dashboard</a> |
<a href="logout">Logout</a>

</body>
</html>
