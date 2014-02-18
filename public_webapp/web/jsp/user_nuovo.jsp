<%-- 
    Document   : user_nuovo
    Created on : 17-feb-2014, 12.26.25
    Author     : marco
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Nuovo Annuncio</title>
        <link rel="stylesheet" type="text/css" href="/public_webapp/style-sheets/style.css">
    </head>
    <body>
        <%@ include file="/WEB-INF/jspf/navigation_bar_logged.jspf" %>
        <div align="center" class="text_very_big">
            Nuovo Annuncio
        </div>
        <br>
        <form class="nuovo_annuncio" method="POST">
            <label>Indirizzo: <input class="larger" type="text" name="indirizzo"></label>
            <label>Città: <input type="text" name="indirizzo"></label>
            <hr>
            INFORMAZIONI ALLOGGIO
            <div class="input_group">
                Tipologia:
                <label><input type="radio" name="tipo_alloggio" value="Appartamento"> Appartamento</label>
                <label><input type="radio" name="tipo_alloggio" value="Villetta"> Villetta</label>
                <label><input type="radio" name="tipo_alloggio" value="Casa Indipendente"> Casa Indipendente</label>
            </div>
            <br>
            <div class="input_group">
                Cucina:
                <label><input type="radio" name="tipo_alloggio" value="Appartamento"> Appartamento</label>
                <label><input type="radio" name="tipo_alloggio" value="Villetta"> Villetta</label>
                <label><input type="radio" name="tipo_alloggio" value="Casa Indipendente"> Casa Indipendente</label>
            </div>
        </form>
    </body>
</html>
