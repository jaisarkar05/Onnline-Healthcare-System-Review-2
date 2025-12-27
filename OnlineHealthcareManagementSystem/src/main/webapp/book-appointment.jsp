<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.LocalTime" %>
<%@ page import="com.healthcare.dao.UserDAO" %>
<%@ page import="com.healthcare.model.User" %>

<%
    Integer userId = (Integer) session.getAttribute("userId");
    String role = (String) session.getAttribute("role");

    if (userId == null || !"PATIENT".equals(role)) {
        response.sendRedirect("login.jsp");
        return;
    }

    UserDAO userDAO = new UserDAO();
    List<User> doctors = userDAO.getAvailableDoctors();

    LocalDate today = LocalDate.now();
    LocalTime now = LocalTime.now().withSecond(0).withNano(0);
%>

<!DOCTYPE html>
<html>
<head>
    <title>Book Appointment</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<div class="dashboard-container">

<h1>Book Appointment</h1>

<%
    String errorMsg = (String) session.getAttribute("errorMsg");
    String successMsg = (String) session.getAttribute("successMsg");
    String emailSent = (String) session.getAttribute("emailSent");

    if (errorMsg != null) {
%>
    <div class="alert error">
        <%= errorMsg %>
    </div>
<%
        session.removeAttribute("errorMsg");
    }

    if (successMsg != null) {
%>
    <div class="alert success">
        <%= successMsg %>
    </div>
<%
        session.removeAttribute("successMsg");
    }

    if (emailSent != null) {
%>
    <div id="emailToast" class="email-toast">
        📧 Confirmation email sent successfully
    </div>
<%
        session.removeAttribute("emailSent");
    }
%>

<div class="card">
<form action="bookAppointment" method="post" onsubmit="disableBtn(this)">

    <label>Doctor:</label>
    <select name="doctorId" required>
        <option value="">-- Select Doctor --</option>
        <% for (User d : doctors) { %>
            <option value="<%= d.getUserId() %>">
                <%= d.getName() %>
            </option>
        <% } %>
    </select>

    <label>Appointment Date:</label>
    <input type="date" name="date" required min="<%= today %>">

    <label>Appointment Time:</label>
    <input type="time" name="time" required min="<%= now %>">

    <button type="submit" class="btn-primary" id="bookBtn">
        Book Appointment
    </button>

</form>
</div>

<div class="action-buttons">
    <a href="patient.jsp" class="btn btn-back">⬅ Back to Dashboard</a>
    <a href="logout" class="btn btn-logout">Logout</a>
</div>

</div>

<script>
function disableBtn(form) {
    const btn = document.getElementById("bookBtn");
    btn.disabled = true;
    btn.innerText = "Booking...";
}

setTimeout(() => {
    const toast = document.getElementById("emailToast");
    if (toast) toast.style.opacity = "0";
}, 2500);
</script>

</body>
</html>
