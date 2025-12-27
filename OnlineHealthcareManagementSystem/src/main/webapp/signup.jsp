<%@ page contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <title>Create Patient Account</title>
    <link rel="stylesheet" href="css/style.css">
</head>

<body>

<div class="dashboard-container">

    <h1>Create Patient Account</h1>
    <%
    String success = request.getParameter("success");
%>

<% if ("true".equals(success)) { %>
<div class="alert success">
    Account created successfully! Redirecting to login…
</div>
<% } %>
    

    <div class="card">
        <form action="signup" method="post">

            <label>Name:</label>
            <input type="text" name="name" class="form-input" required>

            <label>Email:</label>
            <input type="email" name="email" class="form-input" required>

            <label>Password:</label>
            <input type="password" name="password" class="form-input" required>

            <button type="submit" class="btn-primary">
                Create Account
            </button>
        </form>
    </div>

    <p style="margin-top:15px;">
        Already have an account?
        <a href="login.jsp">Login here</a>
    </p>

</div>
<script>
setTimeout(() => {
    document.querySelectorAll(".alert")
            .forEach(a => a.style.opacity = "0");
}, 4000);
</script>

</body>
</html>
