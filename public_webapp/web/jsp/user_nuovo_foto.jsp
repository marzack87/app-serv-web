<%-- 
    Document   : user_addphoto
    Created on : 20-feb-2014, 15.28.24
    Author     : marco
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Annuncio inserito</title>
        <link rel="stylesheet" type="text/css" href="/public_webapp/style-sheets/style.css">
    </head>
    <body>
        <%@ include file="/WEB-INF/jspf/navigation_bar_logged.jspf" %>
        <br>
        <br>
        <div class="centered text_very_big text_green">
            Annuncio inserito correttamente!
        </div>
        <br>
        <br>
        <hr>
        <br>
        <div class="centered" id="message">
            Vuoi aggiungere qualche immagine all'annuncio?
        </div>
        <br>
        <div class="centered">
            <form id="foto_form" class="nuovo_annuncio" action="/public_webapp/AggiuntaFotoServlet" method="POST" enctype="multipart/form-data>
                <br>
                <%
                    String id_annuncio = "";
                    if (request.getAttribute("id_apartment") != null) {
                        id_annuncio = (String) request.getAttribute("id_apartment");
                    }
                %>
                <input type="hidden" name="id_annuncio" value="<%= id_annuncio %>"/>
                <div class="centered">
                    <a class="text_big button bg_grey" href="#" onclick="return add_photo();">+ aggiungi foto</a>
                </div>
                <div id='foto_input' class="centered">
                    
                </div>
                <br>
            </form>
            <br>
            <a class="text_big button inLine" href="#" onclick="return save_photos();">Salva le foto selezionate</a>
            <a class="text_big button inLine bg_red" href="/public_webapp/jsp/user_home.jsp">Continua senza salvare</a>
        </div>
        <br>
        <br>
    </body>
    
    <script type="text/javascript">
        
        var photos = 0;
        
        function add_photo(){
            photos++;
            
            var photo_input = '<div id="foto_' + photos + '"><input type="file" name="foto[]" />';
            photo_input += '<a class="button bg_red text_small" href="#" onclick="return remove_photo(' + photos + ');">rimuovi</a>';
            photo_input += '<br></div>';
            
            if (photos === 1) {
                photo_input = '<br>' + photo_input;
            }
            
            document.getElementById("foto_input").innerHTML += photo_input;
            
            return false;
        }
        
        function remove_photo(number){
            document.getElementById('foto_' + number).remove();
            return false;
        }
        
        function save_photos(){
            document.getElementById('foto_form').submit();
            return false;
        }
    </script>
    
</html>