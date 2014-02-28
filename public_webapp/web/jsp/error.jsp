<%-- 
    Document   : error
    Created on : 26-feb-2014, 17.19.55
    Author     : marco
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="/public_webapp/style-sheets/style.css">
        <title>Errore</title>
    </head>
        <body>
            <%
            String msg = "";
            if (request.getAttribute("msg") != null) {
                msg = (String) request.getAttribute("msg");
            }

            if(session.getAttribute("user") == null){%>
                <%@ include file="/WEB-INF/jspf/navigation_bar.jspf" %>
            <%} else {%>
                <%@ include file="/WEB-INF/jspf/navigation_bar_logged.jspf" %>
            <%}%>
            
            <img class="error" src="/public_webapp/multimedia/error.png">
            
            <div class="error_msg centered">
                <div class="text_very_big"> ERRORE: </div>
                <br>
                <br>
                <br>
                <%= msg%>
                <br>
                <br>
                <br>
                <a class="text_normal button bg_grey" href="/public_webapp/index.jsp">torna alla Home</a>
            </div>
        </body>
</html>
