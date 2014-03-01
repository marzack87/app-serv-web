<%-- 
    Document   : register
    Created on : 13-feb-2014, 16.54.24
    Author     : marco
--%>

<%@page import="utility.User"%>
<%@page import="utility.GestioneUtente"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="/public_webapp/style-sheets/style.css">
        <title>Register Page</title>
    </head>
    <body>
        <%@ include file="/WEB-INF/jspf/navigation_bar_logged.jspf" %>
        
        <%
            String path = session.getServletContext().getRealPath("/WEB-INF/xml/");
            path = path+"/users.xml";
            User user_info = GestioneUtente.getUserInfo(path, (String) session.getAttribute("user"));
        %>
        
        <div class ="register_form">
            <form action="/public_webapp/GestioneUtentiServlet" method="post">
                <input type="hidden" name="q" value="edit_user">
                <input type="hidden" name="username" value="<%= user_info.user_name %>">
                Nome 
                <br>
                <input type="text" name="name" value="<%= user_info.name %>">
                <br>
                Cognome 
                <br>
                <input type="text" name="surname" value="<%= user_info.surname %>">
                Telefono 
                <br>
                <input type="text" name="phone" value="<%= user_info.phone %>">
                USERNAME 
                <br>
                <input type="text" name="user_name" value="<%= user_info.user_name %>" disabled>
                <br>
                PASSWORD 
                <br>
                <input type="password" name="password" value="<%= user_info.password %>">
                <br>
                <input class="button" type="submit" value="MODIFICA">
            </form>
        </div>
    </body>
</html>
