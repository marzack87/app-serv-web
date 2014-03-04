<%-- 
    Document   : user_home
    Created on : 17-feb-2014, 11.49.19
    Author     : marco
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="asw1016.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="/public_webapp/style-sheets/style.css">
        <title>Elenco Utenti</title>
    </head>
    <body>
        <%@ include file="/WEB-INF/jspf/navigation_bar_logged.jspf" %>
        <div class="centered text_very_big">
            Elenco Utenti
        </div>
        <div class="centered text_big text_green">In quanto Amministratore del sito hai la possibilità di ELIMINARE ogni utente</div>
        <hr>
        <%
            ArrayList<User> users_list = (ArrayList<User>) request.getAttribute("lista_utenti");

            for (int i = 0; i < users_list.size(); i++) {
        %>
        <div class ="centered">
            <br>
            <div class="text_very_big"><%=users_list.get(i).getName() %> <%=users_list.get(i).getSurname() %></div>
            <div class="text_big italic"> (<%=users_list.get(i).getUser_name() %>) </div>
            <br>
            <div class="text_big"><%=users_list.get(i).getPhone() %></div>
            <br>
            <a class="bg_red button text_small" href="/public_webapp/GestioneUtentiServlet?q=delete_user&user=<%=users_list.get(i).getUser_name() %>" >elimina</a>
        </div>
        <br>
        <hr>
        <% } %>
    </body>
</html>

