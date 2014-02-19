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
        <div align="center" class="text_small">
            i campi contrassegnati con l'asterisco [*] sono obbligatori
        </div>
        <br>
        <form class="nuovo_annuncio" method="POST">
            <label>Indirizzo: <input class="larger" type="text" name="indirizzo"></label>
            <label>Città: <input type="text" name="indirizzo"></label>
            <hr>
            INFORMAZIONI ALLOGGIO
            <br>
            <br>
            <div class="input_group">
                Tipologia:
                <label><input type="radio" name="tipo_alloggio" value="Appartamento"> Appartamento</label>
                <label><input type="radio" name="tipo_alloggio" value="Villetta"> Villetta</label>
                <label><input type="radio" name="tipo_alloggio" value="Casa Indipendente"> Casa Indipendente</label>
            </div>
            <div class="input_group">
                Cucina:
                <label><input type="radio" name="tipo_cucina" value="Abitabile"> Abitabile</label>
                <label><input type="radio" name="tipo_cucina" value="Cucinotto"> Cucinotto</label>
            </div>
            <br>
            <label>N° Bagni: <input type="number" min="0" name="bagni"></label>
            <span class='space'></span>
            <label>N° Camere da letto: <input type="number" min="0" name="camere_da_letto"></label>
            <br>
            <label>N° Piano: <input type="number" min="0" name="n_piano"></label>
            <div class="input_group">
                Ascensore:
                <label><input type="radio" name="ascensore" value="si"> Sì</label>
                <label><input type="radio" name="ascensore" value="no"> No</label>
            </div>
            <hr>
            <label>Posti letto totali: <input type="number" min="0" name="posti_totali"></label>
            <span class='space'></span>
            <label>Posti Liberi: <input type="number" min="0" name="posti_liberi"></label>
            <br>
            <label>Prezzo per posto letto: <input type="number" min="0" name="camere_da_letto"></label>
            <hr>
            <div class="input_group">
                Spese Incluse:
                <label><input type="checkbox" name="spese_incluse" value="acqua"> Spese Acqua</label>
                <label><input type="checkbox" name="spese_incluse" value="gas"> Spese Gas</label>
                <label><input type="checkbox" name="spese_incluse" value="luce"> Spese Luce</label>
                <label><input type="checkbox" name="spese_incluse" value="condominiali"> Spese Condominiali</label>
            </div>
        </form>
    </body>
</html>
