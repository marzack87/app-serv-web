<%-- 
    Document   : register
    Created on : 13-feb-2014, 16.54.24
    Author     : marco
--%>

<%@page import="utility.User"%>
<%@page import="utility.GestioneUtente"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="/public_webapp/style-sheets/style.css">
        <title>Modifica Informazioni Utente</title>
    </head>
    <body>
        <%@ include file="/WEB-INF/jspf/navigation_bar_logged.jspf" %>
        
        <%
            String path = session.getServletContext().getRealPath("/WEB-INF/xml/");
            path = path+"/users.xml";
            User user_info = GestioneUtente.getUserInfo(path, (String) session.getAttribute("user"));
        %>
        <div class="register_form">
            <form action="/public_webapp/GestioneUtentiServlet" method="post" id="user_info_form">
                <input type="hidden" name="q" value="edit_user">
                <input type="hidden" name="username" value="<%= user_info.user_name %>">
                Nome 
                <br>
                <input type="text" name="name" value="<%= user_info.name %>">
                <br>
                Cognome 
                <br>
                <input type="text" name="surname" value="<%= user_info.surname %>">
                Telefono 
                <br>
                <input type="text" name="phone" value="<%= user_info.phone %>">
                USERNAME 
                <br>
                <input type="text" name="user_name" value="<%= user_info.user_name %>" disabled>
                <br>
                PASSWORD 
                <br>
                <input type="password" name="password" value="<%= user_info.password %>">
                <br>
                <br>
                <a class="button" href="#" onclick="check_form()">MODIFICA</a>
            </form>
        </div>
                
        <script type="text/javascript">
            function check_form(){
                var ok = true;
                ok = ok & check_input_field('name');
                ok = ok & check_input_field('surname');
                ok = ok & check_input_field('phone');
                ok = ok & check_input_field('user_name');
                ok = ok & check_input_field('password');
                if (ok){
                    submit_form();
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

            function submit_form(){
                document.getElementById('user_info_form').submit();
            }
        </script>
                
    </body>
</html>
