<%-- 
    Document   : user_annuncio_cancellato
    Created on : 27-feb-2014, 18.30.16
    Author     : marco
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="/public_webapp/style-sheets/style.css">
        <title>User Home</title>
    </head>
    <body>
        <%@ include file="/WEB-INF/jspf/navigation_bar_logged.jspf" %>
        <br>
        <br>
        <br>
        <br>
        <div class="centered text_very_big text_green">
            <%
            String msg = "";
            if (request.getAttribute("msg") != null) {
                msg = (String) request.getAttribute("msg");
            }
            
            if (msg.equals("eliminato")){
            %>
            L'annuncio è stato correttamente cancellato!
            <% } else if (msg.equals("modificato")) {%>
            L'annuncio è stato correttamente modificato!
            <% } %>
            <br>
            <br>
            <br>
            <a class="text_normal button bg_grey" href="/public_webapp/ElencoAnnunciServlet">torna all'elenco degli annunci</a>
        </div>
    </body>
</html>
