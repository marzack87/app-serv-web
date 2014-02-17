/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package login;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;  
/**
 *
 * @author marco
 */
public class LoginServlet extends HttpServlet {
    
    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html");
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.jsp");
        PrintWriter out= response.getWriter();
        
        if ((request.getParameter("user") != null && !request.getParameter("user").isEmpty()) 
                && (request.getParameter("pwd") != null && !request.getParameter("pwd").isEmpty()))
        {
            String user = request.getParameter("user");
            String pwd = request.getParameter("pwd");
            
            String path = request.getSession().getServletContext().getRealPath("/");
            
            File f = new File(path);
            if(f.exists() && !f.isDirectory())
            {
                //Esiste il database utenti, controllo se è presente l'utente
            } else {
                //Non esiste il database utenti, quindi l'utente andrà rimandanto alla registrazione
                out.println("<div align=center><font color=red >Non ci sono utenti con queste credenziali,<br> premi registrati per creare un account<br> o ricontrolla i tuoi dati.</font></div>");
                rd.include(request, response);  
            }
                
        } else {
            
            out.println("<div align=center><font color=red >Username or password is wrong.</font></div>");
            rd.include(request, response);
        }
    }
}
