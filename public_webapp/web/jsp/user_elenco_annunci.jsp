<%-- 
    Document   : user_modifica
    Created on : 17-feb-2014, 12.26.48
    Author     : marco
--%>

<%@page import="gestione_annuncio.Apartment"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Elenco Annunci</title>
        <link rel="stylesheet" type="text/css" href="/public_webapp/style-sheets/style.css">
    </head>
    <body>
        <%@ include file="/WEB-INF/jspf/navigation_bar_logged.jspf" %>
        
        <% 
            
            ArrayList<Apartment> apartments_list = new ArrayList<Apartment>();
            if (request.getAttribute("apartments_list") != null) {
                apartments_list = (ArrayList<Apartment>) request.getAttribute("apartments_list");
            }
            
            if (apartments_list.size() == 0){
                
            %>
            <p>Non sono presenti annunci per questo utente.</p>
            <%
                
            } else {
            
                for(int i = 0; i < apartments_list.size(); i++){ 
        %>
        
        <div class="centered text_big">
            <%= apartments_list.get(i).address %>
        </div>
        
        
        <hr>
        
        <%      }
            }
        %>
    </body>
</html>
