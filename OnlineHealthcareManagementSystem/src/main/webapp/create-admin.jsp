<%@ page contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <title>Create Admin Account</title>
    <link rel="stylesheet" href="css/style.css">
</head>

<body>

<div class="dashboard-container">

    <h1>Create Admin Account</h1>
    <%
    String success = request.getParameter("success");
%>

<% if ("true".equals(success)) { %>
<div class="alert success">
    Admin account created successfully!
</div>
<% } %>
    

    <div class="card">

        <form action="adminCreateUser" method="post">


            <input type="hidden" name="role" value="ADMIN">
            <label>Name:</label>
            <input type="text" name="name" class="form-input" required>

            <label>Email:</label>
            <input type="email" name="email" class="form-input" required>

            <label>Password:</label>
            <input type="password" name="password" class="form-input" required>

            <button type="submit" class="btn-primary">
                Create Admin
            </button>

        </form>
    </div>

    <div class="action-buttons">
        <a href="admin.jsp" class="btn btn-back">⬅ Back</a>
    </div>

</div>
<script>
setTimeout(() => {
    document.querySelectorAll(".alert")
            .forEach(a => a.style.opacity = "0");
}, 4000);
</script>

</body>
</html>
