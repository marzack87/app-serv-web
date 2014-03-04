<%-- 
    Document   : user_home
    Created on : 17-feb-2014, 11.49.19
    Author     : marco
--%>

<%@page import="asw1016.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="/public_webapp/style-sheets/style.css">
        <title>Informazioni Utente</title>
    </head>
    <body>
        <%@ include file="/WEB-INF/jspf/navigation_bar_logged.jspf" %>
        <div class="centered text_very_big">
            Informazioni Utente
        </div>
        <hr>
        <div class ="user_info">
            <%
                User user_info = (User) request.getAttribute("utente");
            %>
            NOME:<br>
            <div class="text_very_big"><%= user_info.getName() %></div>
            <br>COGNOME:<br>
            <div class="text_very_big"><%= user_info.getSurname() %></div>
            <br>USERNAME:<br>
            <div class="text_very_big"><%= user_info.getUser_name() %></div>
            <br>TELEFONO:<br>
            <div class="text_very_big"><%= user_info.getPhone() %></div>
        </div>
        <br>
        <div class="centered">
            <a class="text_very_big button" href="/public_webapp/jsp/user_modifica_info.jsp">MODIFICA</a>
        </div>
        <br><br>
    </body>
</html>

