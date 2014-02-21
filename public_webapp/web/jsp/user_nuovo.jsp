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
        <div class="centered text_very_big">
            Nuovo Annuncio
        </div>
        <div class="centered text_small" id="message">
            tutti i campi sono obbligatori
        </div>
        <br>
        <div class="centered">
            <form id="annuncio" class="nuovo_annuncio" action="/public_webapp/NuovoAnnuncioServlet" method="POST">
                <input type="hidden" name="user_name" value="<%=user %>">
            <label>Indirizzo: <input class="larger" type="text" name="indirizzo"></label>
            <span class='space'></span>
            <label>Civico: <input type="text" class="smaller" name="civico"></label>
            <br>
            <label>Città: <input type="text" name="citta"></label>
            <hr>
            INFORMAZIONI ALLOGGIO
            <br>
            <br>
            <div class="input_group" id="tipo_alloggio">
                Tipologia:
                <label><input type="radio" name="tipo_alloggio" value="0"> Appartamento</label>
                <label><input type="radio" name="tipo_alloggio" value="1"> Villetta</label>
                <label><input type="radio" name="tipo_alloggio" value="2"> Casa Indipendente</label>
            </div>
            <br>
            <div class="input_group" id="tipo_cucina">
                Cucina:
                <label><input type="radio" name="tipo_cucina" value="0"> Abitabile</label>
                <label><input type="radio" name="tipo_cucina" value="1"> Cucinotto</label>
            </div>
            <label>N° Bagni: <input type="number" class="smaller" min="0" name="bagni"></label>
            <span class='space'></span>
            <label>N° Camere da letto: <input type="number" class="smaller" min="0" name="camere_da_letto"></label>
            <span class='space'></span>
            <label>N° Piano: <input type="number" class="smaller" min="-1" name="n_piano" onchange="check_floor()"></label>
            <span class='space'></span>
            <label style="display: inline"><input type="checkbox" name="ascensore" value="1" disabled="true"> Ascensore</label>
            <br>
            <label style="display: inline"><input type="checkbox" name="garage" value="1"> Garage</label>
            <span class='space'></span>
            <label style="display: inline"><input type="checkbox" name="terrazzo" value="1"> Terrazzo</label>
            <hr>
            <label>Posti letto totali: <input type="number" class="smaller" min="0" name="posti_totali"></label>
            <span class='space'></span>
            <label>Posti Liberi: <input type="number" class="smaller" min="0" name="posti_liberi"></label>
            <span class='space'></span>
            <label>Prezzo per posto letto: <input type="number" class="smaller" min="0" name="prezzo_posto"> €</label>
            <hr>
            <div class="input_group" id="spese_incluse">
                Spese Incluse:
                <label><input type="checkbox" name="spese_incluse" value="0"> Spese Acqua</label>
                <label><input type="checkbox" name="spese_incluse" value="1"> Spese Gas</label>
                <label><input type="checkbox" name="spese_incluse" value="2"> Spese Luce</label>
                <label><input type="checkbox" name="spese_incluse" value="3"> Spese Condominiali</label>
                <label><input type="checkbox" name="spese_incluse" value="4" onchange="return no_spese();"> NESSUNA</label>
            </div>
        </form>
        </div>
        <br>
        <div class="centered">
            <a class="text_very_big button" href="#" onclick="check_form()">INSERISCI ANNUNCIO</a>
        </div>
        <br>
    </body>
    
    <script type="text/javascript">
        function check_floor(){
            var floor = document.getElementsByName("n_piano")[0];
            if (floor.value != '' & floor.value != 0){
                document.getElementsByName("ascensore")[0].disabled = false;
            } else {
                document.getElementsByName("ascensore")[0].disabled = true;
                document.getElementsByName("ascensore")[0].checked = false;
            }
        }
        
        function check_form(){
            var ok = true;
            ok = ok & check_input_field('indirizzo');
            ok = ok & check_input_field('citta');
            ok = ok & check_group('tipo_alloggio');
            ok = ok & check_group('tipo_cucina');
            ok = ok & check_input_field('bagni');
            ok = ok & check_input_field('camere_da_letto');
            ok = ok & check_input_field('n_piano');
            ok = ok & check_input_field('posti_totali');
            ok = ok & check_input_field('posti_liberi');
            ok = ok & check_input_field('prezzo_posto');
            ok = ok & check_group('spese_incluse');
            
            if (ok){
                submit_form();
            } else {
                var msg = document.getElementById("message");
                msg.innerHTML = "è necessario inserire anche i campi evidenziati in rosso";
                msg.style["color"] = "red" ;
            }
            
        }
        
        function check_input_field(name){
            var element = document.getElementsByName(name)[0];
            if (element.value != ''){
                element.style["border"] = "1px solid silver" ;
                return true;
            } else {
                element.style["border"] = "2px solid red" ;
                return false;
            }
        }
        
        function check_group(name){
            var elements = document.getElementsByName(name);
            var group_ok = false;
            for (i = 0; i < elements.length; i++){
                if (elements[i].checked == true){
                    group_ok = true;
                }
            }
            if (group_ok){
                document.getElementById(name).style["color"] = "black" ;
                return true;
            } else {
                document.getElementById(name).style["color"] = "red" ;
                return false;
            }
        }
        
        function submit_form(){
            document.getElementById('annuncio').submit();
        }
        
        function no_spese(){
            var spese = document.getElementsByName('spese_incluse');
            
            if (spese[4].checked == true){
                
                spese[0].checked = false;
                spese[1].checked = false;
                spese[2].checked = false;
                spese[3].checked = false;
                
                spese[0].disabled = true;
                spese[1].disabled = true;
                spese[2].disabled = true;
                spese[3].disabled = true;
                
            } else {
                
                spese[0].disabled = false;
                spese[1].disabled = false;
                spese[2].disabled = false;
                spese[3].disabled = false;
                
            }
            
            return false;
            
        }
    </script>
    
</html>
