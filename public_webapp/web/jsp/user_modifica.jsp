<%-- 
    Document   : user_nuovo
    Created on : 17-feb-2014, 12.26.25
    Author     : marco
--%>

<%@page import="utility.Apartment"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Modifica Informazioni Annuncio</title>
        <link rel="stylesheet" type="text/css" href="/public_webapp/style-sheets/style.css">
    </head>
    <body>
        <%@ include file="/WEB-INF/jspf/navigation_bar_logged.jspf" %>
        
        <% 
            
            ArrayList<Apartment> apartments_list = new ArrayList<Apartment>();
            if (request.getAttribute("apartments_list") != null) {
                apartments_list = (ArrayList<Apartment>) request.getAttribute("apartments_list");
            }
            
            Apartment ap = apartments_list.get(0);
            
            String images = "";
            boolean first = true;
            for (String image : ap.img_url)
            {
                if (image.equals("\n")) continue;
                if (first){
                    first = false;
                } else {
                    images += "#";
                }
                images += image;
            }
        %>
        
        <div class="centered text_very_big">
            Modifica Informazioni Annuncio
        </div>
        <div class="centered text_small" id="message">
            qui è possibile modificare le informazioni riguardo l'alloggio, cliccando sul pulsante si potrà accedere alla modifica delle foto dell'annuncio
        </div>
        <br>
        <div class="centered">
            <form id="annuncio" class="nuovo_annuncio" action="/public_webapp/ModificaAnnuncioServlet" method="POST">
                <input type="hidden" name="id_apartment" value="<%=ap.id_apartment %>">
                <input type="hidden" name="images" value="<%=images %>">
            <label>Indirizzo: <input class="larger" type="text" name="indirizzo" value="<%= ap.address %>"></label>
            <span class='space'></span>
            <label>Civico: <input type="text" class="smaller" name="civico" value="<%= ap.civico %>"></label>
            <br>
            <label>Città: <input type="text" name="citta" value="<%= ap.citta %>"></label>
            <hr>
            INFORMAZIONI ALLOGGIO
            <br>
            <br>
            <div class="input_group" id="tipo_alloggio">
            
                <%
                
                String app = (ap.tipologia.equals("0")) ? "checked" : "";
                String vil = (ap.tipologia.equals("1")) ? "checked" : "";
                String ind = (ap.tipologia.equals("2")) ? "checked" : "";
                
                %>
            
                Tipologia:
                <label><input type="radio" name="tipo_alloggio" value="0" <%= app %>> Appartamento</label>
                <label><input type="radio" name="tipo_alloggio" value="1" <%= vil %>> Villetta</label>
                <label><input type="radio" name="tipo_alloggio" value="2" <%= ind %>> Casa Indipendente</label>
            </div>
            <br>
            <div class="input_group" id="tipo_cucina">
                
                <%
                
                String abitabile = (ap.tipo_cucina.equals("0")) ? "checked" : "";
                String cucinotto = (ap.tipo_cucina.equals("1")) ? "checked" : "";
                
                %>
                
                Cucina:
                <label><input type="radio" name="tipo_cucina" value="0" <%= abitabile%>> Abitabile</label>
                <label><input type="radio" name="tipo_cucina" value="1" <%= cucinotto%>> Cucinotto</label>
            </div>
            <label>N° Bagni: <input type="number" class="smaller" min="0" name="bagni" value="<%= ap.bagni%>"></label>
            <span class='space'></span>
            <label>N° Camere da letto: <input type="number" class="smaller" min="0" name="camere_da_letto" value="<%= ap.camere_letto%>"></label>
            <span class='space'></span>
            <label>N° Piano: <input type="number" class="smaller" min="-1" name="n_piano" onchange="check_floor()" value="<%= ap.n_piano%>"></label>
            <span class='space'></span>
            <label style="display: inline"><input type="checkbox" name="ascensore" value="1" disabled="true" <%= (ap.ascensore.equals("1")) ? "checked" : "" %>> Ascensore</label>
            <br>
            <label style="display: inline"><input type="checkbox" name="garage" value="1" <%= (ap.garage.equals("1")) ? "checked" : "" %>> Garage</label>
            <span class='space'></span>
            <label style="display: inline"><input type="checkbox" name="terrazzo" value="1" <%= (ap.terrazzo.equals("1")) ? "checked" : "" %>> Terrazzo</label>
            <hr>
            <label>Posti letto totali: <input type="number" class="smaller" min="0" name="posti_totali" value="<%= ap.posti_totali %>"></label>
            <span class='space'></span>
            <label>Posti Liberi: <input type="number" class="smaller" min="0" name="posti_liberi" value="<%= ap.posti_liberi %>"></label>
            <span class='space'></span>
            <label>Prezzo per posto letto: <input type="number" class="smaller" min="0" name="prezzo_posto" value="<%= ap.prezzo %>"> € al mese</label>
            <hr>
            <div class="input_group" id="spese_incluse">
                Spese Incluse:
                <label><input type="checkbox" name="spese_incluse" value="0" <%= (ap.spese_acqua.equals("1")) ? "checked" : "" %>> Spese Acqua</label>
                <label><input type="checkbox" name="spese_incluse" value="1" <%= (ap.spese_gas.equals("1")) ? "checked" : "" %>> Spese Gas</label>
                <label><input type="checkbox" name="spese_incluse" value="2" <%= (ap.spese_luce.equals("1")) ? "checked" : "" %>> Spese Luce</label>
                <label><input type="checkbox" name="spese_incluse" value="3" <%= (ap.spese_cond.equals("1")) ? "checked" : "" %>> Spese Condominiali</label>
                <label><input type="checkbox" name="spese_incluse" value="4" onchange="return no_spese();" <%= (ap.ness_spesa.equals("1")) ? "checked" : "" %>> NESSUNA</label>
            </div>
        </form>
        </div>
        <br>
        <div class="centered">
            <a href="#" onclick="check_form()" class="button inLine">
                <span class="text_very_big">MODIFICA INFORMAZIONI</span>
                <br>e vai alla modifica delle foto
            </a>
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
