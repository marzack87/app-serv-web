<%-- 
    Document   : index
    Created on : 12-feb-2014, 17.53.44
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
        <div class="centered text_very_big">
            Benvenuto!
        </div>
        <p class="italic">
            Sei un giovane teenager che è appena scappato di casa perchè i tuoi genitori sono
            cattivi e si rifiutano di comprarti l'iPhone 5S e si ostinano a dirti cosa devi fare?    
        </p>
        <p>
            Questo è il sito giusto per te! Qui potrai trovare tantissimi annunci di posti immaginari
            dove ti puoi rifugiare per cercare di farti una vita tutta tua, senza vecchiacci che ti
            rompono!
        </p>
        <p class="italic">
            Ti sei appropriato indebitamente di troppi immobili e non sai cosa fartene?
        </p>
        <p>
            Benissimo! Mettili in affitto così potrai guadagnare un sacco di soldi rimanendo comodamente
            spaparanzato nel divano a non fare niente! Inserisci gli annunci nel nostro super database e
            verrai contattato da tantissime persone disperate che non hanno una casa!
        </p>
        <br>
        <div class='centered'>
            <a href="/public_webapp/jsp/login.jsp" class="button inLine fixed_size">
                <span class="text_very_big">CERCA</span>
                <br> un annuncio
            </a>
            <a href="/public_webapp/jsp/login.jsp" class="button inLine fixed_size">
                <span class="text_very_big">ACCEDI</span>
                <br> e inserisci/modifica i tuoi annunci
            </a>
        </div>
        
    </body>
</html>
