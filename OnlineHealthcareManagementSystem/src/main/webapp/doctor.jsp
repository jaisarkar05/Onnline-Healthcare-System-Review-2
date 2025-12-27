<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>

<%
    Integer userId = (Integer) session.getAttribute("userId");
    String role = (String) session.getAttribute("role");
    String name = (String) session.getAttribute("name");
    String availabilityStatus = (String) session.getAttribute("availability_status");

    if (userId == null || !"DOCTOR".equals(role)) {
        response.sendRedirect("login.jsp");
        return;
    }

    if (availabilityStatus == null) {
        availabilityStatus = "AVAILABLE"; // default safety
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Doctor Dashboard</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<div class="dashboard-container">
<h1>Doctor Dashboard</h1>

<h3>Welcome Dr. <%= name %></h3>

<div class="card">
<p>
    Current Availability:
    <b style="color:<%= "AVAILABLE".equals(availabilityStatus) ? "green" : "red" %>">
        <%= availabilityStatus %>
    </b>
</p>

<hr>

<!-- Toggle Availability -->
<div class="availability-actions">

<% if ("AVAILABLE".equals(availabilityStatus)) { %>

    <form action="toggleAvailability" method="post">
        <input type="hidden" name="availability" value="UNAVAILABLE">
        <button type="submit" class="btn-unavailable">
         <span class="icon-black">✖</span> Mark Unavailable
        </button>
    </form>

<% } else { %>

    <form action="toggleAvailability" method="post">
        <input type="hidden" name="availability" value="AVAILABLE">
        <button type="submit" class="btn-available">
            ✅ Mark Available
        </button>
    </form>

<% } %>

</div>

</div>
<hr>

<!-- Navigation -->
<div class="dashboard-grid">

    <a href="doctor-appointments.jsp" class="dashboard-card">
        📋 View Appointments
    </a>

</div>

<br><br>
<div class="card logout">
<a href="logout">Logout</a>
</div>
</div>

</body>
</html>
