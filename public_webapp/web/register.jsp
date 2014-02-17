<%-- 
    Document   : register
    Created on : 13-feb-2014, 16.54.24
    Author     : marco
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="/public_webapp/style/style.css">
        <title>Register Page</title>
    </head>
    <body>
        <%@ include file="/navigation_bar.jspf" %>
        <div class ="register_form">
            <form action="RegisterServlet" method="post">
                Nome 
                <br>
                <input type="text" name="name">
                <br>
                Cognome 
                <br>
                <input type="password" name="surname">
                Telefono 
                <br>
                <input type="text" name="phone">
                USERNAME 
                <br>
                <input type="text" name="user">
                <br>
                PASSWORD 
                <br>
                <input type="password" name="pwd">
                <br>
                <input class="button" type="submit" value="REGISTRATI">
            </form>
        </div>
    </body>
</html>
