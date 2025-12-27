<%@ page contentType="text/html; charset=UTF-8" %>

<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="css/style.css">
</head>

<body>

<div class="container">

    <%-- 🔔 SHOW DEACTIVATED MESSAGE --%>
    <%
        String deac = request.getParameter("deactivated");
        String error = request.getParameter("error");
    %>

    <% if ("true".equals(deac)) { %>
        <div class="alert error">
            Your account is deactivated. Please contact the admin.
        </div>
    <% } %>

    <% if ("true".equals(error)) { %>
        <div class="alert error">
            Invalid email or password.
        </div>
    <% } %>

    <h2>Login Form</h2>

    <form action="login" method="post">
        <label>Email:</label><br>
        <input type="text" name="email" class="form-input"><br><br>

        <label>Password:</label><br>
        <input type="password" name="password" class="form-input"><br><br>

        <input type="submit" value="Login">
    </form>
    

</div>
<%
    String signup = request.getParameter("signup");
%>

<% if ("success".equals(signup)) { %>
    <div class="alert success">
        Account created successfully! Redirecting to login...
    </div>

    <script>
        setTimeout(() => {
            window.location.href = "login.jsp";
        }, 3000);
    </script>
<% } %>

<script>
setTimeout(() => {
    document.querySelectorAll(".alert").forEach(a => a.style.opacity="0");
}, 4000);
</script>

</body>
</html>
