<%-- 
    Document   : annuncio
    Created on : 13-feb-2014, 16.27.26
    Author     : andrea
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" type="text/css" href="/public_webapp/style-sheets/style.css">
    </head>
    <body>
        <%@ include file="/WEB-INF/jspf/navigation_bar.jspf" %>
        <div class="centered">
            <h1>Annuncio</h1>
           <!-- <applet code="gestione_annuncio.SearchApplet" archive="/public_webapp/applet/SearchApplet.jar" width="600" height="700"/>
           -->
           <p> Qui ci va l'applet view </p>
           <p> L'id dell'annuncio è: <%= request.getParameter("id_apartment") %> </p>
        </div>
        
        
       
        
    </body>
</html>

