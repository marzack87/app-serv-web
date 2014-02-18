<%-- 
    Document   : cerca
    Created on : 13-feb-2014, 16.27.26
    Author     : marco
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
        <h1>Cerca</h1>
        
        <APPLET codebase="build/web/WEB-INF" code="lib/SearchApplet.class" 
                archive="SearchApplet.jar"
                width=650 height=500>
            Applet failed to run. No Java plug-in was found. 
        </APPLET>
        
        <script> 
            var param = new Array();
            function getReq(Array array[]){
                
                var xmlhttp = new XMLHttpRequest();
                //aggiungi i parametri
                var String req = ""+"reqServlet?";
                xmlhttp.open("GET",req, true);
                
                xmlhttp.send();
                console.log("richiesta fatta!");
            }
        </script>
    </body>
</html>
