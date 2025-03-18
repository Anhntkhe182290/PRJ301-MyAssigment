<%-- 
    Document   : reset_password
    Created on : Mar 19, 2025, 2:26:22 AM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Đặt lại mật khẩu</title>
    </head>
    <body>
        <h2>Đặt lại mật khẩu</h2>

    <c:if test="${not empty errorMessage}">
        <p style="color: red;">${errorMessage}</p>
    </c:if>

    <c:if test="${not empty successMessage}">
        <p style="color: green;">${successMessage}</p>
    </c:if>

    <form action="reset-password" method="post">
        <input type="hidden" name="token" value="${token}" />
        <label>Mật khẩu mới:</label>
        <input type="password" name="password" required />
        <br>
        <button type="submit">Đặt lại mật khẩu</button>
    </form>
</body>
</html>
