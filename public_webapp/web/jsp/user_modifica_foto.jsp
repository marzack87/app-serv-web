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
        <title>Annuncio modificato</title>
        <link rel="stylesheet" type="text/css" href="/public_webapp/style-sheets/style.css">
    </head>
    <body>
        <%@ include file="/WEB-INF/jspf/navigation_bar_logged.jspf" %>
        <br>
        <br>
        <div class="centered text_very_big text_green">
            Informazioni dell'annuncio modificate con successo!
        </div>
        <br>
        <br>
        <hr>
        <br>
        <div class="centered" id="message">
            Vuoi aggiungere nuove foto all'annuncio o eliminare quelle già presenti?
        </div>
        <br>
        <div class="text_very_small centered">
            (N.B. è possibile caricare solo immagini di dimensione inferiore o uguale a 10 MB)
        </div>
        <br>
        <div class="centered">
            <form id="foto_form" class="nuovo_annuncio" action="/public_webapp/GestioneFotoServlet" method="POST" enctype="multipart/form-data">
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
                <hr>
                <br>
                <%
                    String images = "";
                    if (request.getAttribute("images") != null) {
                        images = (String) request.getAttribute("images");
                    }
                    
                    if (!images.equals("")){
                        String[] photos = images.split("#");
                    
                        for (String photo : photos){
                %>
                <div id="<%=photo%>">
                    <img class="preview inLine" src ="/public_webapp/multimedia/photos/<%=photo%>">
                    <a class="button bg_red text_small inLine vertically_centered" href="#" onclick="return remove_photo_saved('<%=photo%>');">rimuovi</a>
                </div>
                <%      }
                    } else {%>
                    <div class="text_small centered">
                        Nessuna foto presente nel database
                    </div>
                <%  }%>
                <br>
                <hr>
            <br>
            <a class="text_big button inLine" href="#" onclick="return save_photos();">Applica le modifiche alle foto</a>
            <a class="text_big button inLine bg_red" href="/public_webapp/ElencoAnnunciServlet">Annulla le modifiche alle foto</a>
        </div>
        <br>
        <br>
        <script type="text/javascript">
        
            var photos = 0;
            
            function add_photo(){
                photos++;

                var new_div = document.createElement('div');
                new_div.id = "foto_" + photos;

                var new_input = document.createElement('input');
                new_input.type = "file";
                new_input.name = "foto";
                new_input.accept = "image/*";

                var remove_input = document.createElement('span');
                remove_input.innerHTML = '<a class="button bg_red text_small" href="#" onclick="return remove_photo(' + photos + ');">rimuovi</a>';

                new_div.appendChild(document.createElement('br'));
                new_div.appendChild(new_input);
                new_div.appendChild(remove_input);
                document.getElementById("foto_input").appendChild(new_div);

                return false;
            }

            function remove_photo(number){
                document.getElementById('foto_' + number).remove();
                return false;
            }

            function remove_photo_saved(name){
                var new_input = document.createElement('input');
                new_input.type = "hidden";
                new_input.name = "foto_da_cancellare";
                new_input.value = name;
                document.getElementById("foto_form").appendChild(new_input);

                var element = document.getElementById(name);
                element.parentNode.removeChild(element);
                return false;
            }

            function save_photos(){

                document.getElementById('foto_form').submit();
                return false;

            }
        </script>
    </body>
</html>