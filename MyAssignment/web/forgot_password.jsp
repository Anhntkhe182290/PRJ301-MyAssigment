<%-- 
    Document   : forgot_password
    Created on : Mar 19, 2025, 1:28:43 AM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form action="forgot-password" method="post">
            <label>Email:</label>
            <input type="email" name="email" required />
            <button type="submit">Submit</button>
        </form>

    </body>
</html>
