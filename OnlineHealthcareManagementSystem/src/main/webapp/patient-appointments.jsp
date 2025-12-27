<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.healthcare.dao.AppointmentDAO" %>
<%@ page import="com.healthcare.model.Appointment" %>

<%
    String role = (String) session.getAttribute("role");

    if (role == null || !role.equals("PATIENT")) {
        response.sendRedirect("login.jsp");
        return;
    }

    Integer patientIdObj = (Integer) session.getAttribute("userId");
    if (patientIdObj == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    int patientId = patientIdObj;

    AppointmentDAO appointmentDAO = new AppointmentDAO();
    List<Appointment> appointments =
            appointmentDAO.getAppointmentsWithMedicalRecordsByPatient(patientId);

    //  EMAIL / CANCEL ANIMATION FLAG
    String emailSent = (String) session.getAttribute("emailSent");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Patient Appointments</title>
    <link rel="stylesheet" href="css/style.css">

    <style>
        /* Toast animation */
        .toast {
            position: fixed;
            top: 20px;
            right: 20px;
            background: #fff;
            border-left: 5px solid #e74c3c;
            padding: 15px 20px;
            border-radius: 10px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.15);
            display: flex;
            align-items: center;
            gap: 10px;
            animation: slideIn 0.5s ease;
            z-index: 9999;
        }

        .toast.success {
            border-left-color: #27ae60;
        }

        .toast .icon {
            font-size: 22px;
        }

        .toast .msg {
            font-size: 15px;
            font-weight: 500;
        }

        @keyframes slideIn {
            from { opacity: 0; transform: translateX(100%); }
            to { opacity: 1; transform: translateX(0); }
        }

        @keyframes fadeOut {
            to { opacity: 0; transform: translateX(100%); }
        }
    </style>
</head>
<body>

<h1>Patient Appointments</h1>

<!--  CANCEL / EMAIL SENT TOAST -->
<% if ("cancel".equals(emailSent)) { %>
    <div class="toast">
        <span class="icon">✉️</span>
        <span class="msg">Cancellation email sent successfully</span>
    </div>

    <script>
        setTimeout(() => {
            const toast = document.querySelector(".toast");
            toast.style.animation = "fadeOut 0.6s ease forwards";
        }, 2500);
    </script>

<%
        session.removeAttribute("emailSent");
    }
%>

<table border="1" cellpadding="8">
    <tr>
        <th>ID</th>
        <th>Doctor ID</th>
        <th>Date</th>
        <th>Time</th>
        <th>Status</th>
        <th>Doctor Notes</th>
        <th>Prescription</th>
        <th>Action</th>
    </tr>

<% for (Appointment a : appointments) { %>
    <tr>
        <td><%= a.getAppointmentId() %></td>
        <td><%= a.getDoctorId() %></td>
        <td><%= a.getAppointmentDate() %></td>
        <td><%= a.getAppointmentTime() %></td>

        <td>
            <span class="status <%= a.getStatus() %>">
                <%= a.getStatus() %>
            </span>
        </td>

        <td>
            <%= (a.getDoctorNotes() != null && !a.getDoctorNotes().trim().isEmpty())
                    ? a.getDoctorNotes()
                    : "---" %>
        </td>

        <td>
            <%= (a.getPrescription() != null && !a.getPrescription().trim().isEmpty())
                    ? a.getPrescription()
                    : "---" %>
        </td>

        <td>
            <% if ("BOOKED".equals(a.getStatus())) { %>
                <form action="cancelAppointment" method="post">
                    <input type="hidden" name="appointmentId"
                           value="<%= a.getAppointmentId() %>">
                    <input type="submit" value="Cancel">
                </form>
            <% } else { %>
                ---
            <% } %>
        </td>
    </tr>
<% } %>

</table>

<br><br>
<div class="action-buttons">
    <a href="patient.jsp" class="btn btn-back">⬅ Back to Dashboard</a>
    <a href="logout" class="btn btn-logout">Logout</a>
</div>

</body>
</html>
