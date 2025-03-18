<%-- 
    Document   : login
    Created on : Mar 19, 2025, 1:24:33 AM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Đăng nhập</title>
    </head>
    <body>
        <h2>Đăng nhập</h2>

    <c:if test="${not empty errorMessage}">
        <p style="color: red;">${errorMessage}</p>
    </c:if>

    <form action="login" method="post">
        <label>Username:</label>
        <input type="text" name="username" required />

        <label>Password:</label>
        <input type="password" name="password" required />

        <label>Captcha:</label>
        <img src="captcha" />
        <input type="text" name="captcha" required />

        <button type="submit">Login</button>
    </form>
    <a href="forgot_password.jsp">Forgot Password?</a>

</body>
</html>
