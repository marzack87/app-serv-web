<%-- 
    Document   : login
    Created on : 13-feb-2014, 16.08.53
    Author     : Piero
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="/public_webapp/style/style.css">
        <title>Login Page</title>
    </head>
        <body>
            <%@ include file="/navigation_bar.jspf" %>
            <form action="LoginServlet" method="post">
                Username: <input type="text" name="user">
                <br>
                Password: <input type="password" name="pwd">
                <br>
                <input type="submit" value="Login">
            </form>
        </body>
</html>