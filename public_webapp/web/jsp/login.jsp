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
        <link rel="stylesheet" type="text/css" href="/public_webapp/style-sheets/style.css">
        <title>Login Page</title>
    </head>
        <body>
            <%@ include file="/WEB-INF/jspf/navigation_bar.jspf" %>
            <div class ="login_form">
                <form action="/public_webapp/LoginServlet" method="post">
                USERNAME 
                <br>
                <input type="text" name="user">
                <br>
                PASSWORD 
                <br>
                <input type="password" name="pwd">
                <br>
                <input class="button" type="submit" value="ACCEDI">
            </form>
                <br>
                non hai un account? <a href="/public_webapp/jsp/register.jsp">REGISTRATI</a>
            </div>
        </body>
</html>