/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package asw1016;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import org.w3c.dom.*;
import javax.xml.parsers.*;
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
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/jsp/login.jsp");
        PrintWriter out= response.getWriter();
        
        if ((request.getParameter("user") != null && !request.getParameter("user").isEmpty()) 
                && (request.getParameter("pwd") != null && !request.getParameter("pwd").isEmpty()))
        {
            String user = request.getParameter("user");
            String pwd = request.getParameter("pwd");
            
            String path = request.getSession().getServletContext().getRealPath("/WEB-INF/xml/");
            path = path+"/users.xml";
            
            File f = new File(path);
            if(f.exists() && !f.isDirectory())
            {
                try {
                    //Esiste il database utenti, controllo se è presente l'utente
                    int user_type = GestioneUtente.loginUser(path, user, pwd);
                    if ( user_type == 1)
                    {
                        //UTENTE NORMALE
                        HttpSession session = request.getSession();
                        session.setAttribute("user", user);
                        session.setAttribute("admin", "0");
                        //setting session to expiry in 30 mins
                        session.setMaxInactiveInterval(30*60);
                        Cookie userName = new Cookie("user", user);
                        userName.setMaxAge(30*60);
                        response.addCookie(userName);
                        response.sendRedirect("/public_webapp/ElencoAnnunciServlet");
                        
                    } else if (user_type == 2) 
                    {
                        // ADMIN
                        //UTENTE NORMALE
                        HttpSession session = request.getSession();
                        session.setAttribute("user", user);
                        session.setAttribute("admin", "1");
                        //setting session to expiry in 30 mins
                        session.setMaxInactiveInterval(30*60);
                        Cookie userName = new Cookie("user", user);
                        userName.setMaxAge(30*60);
                        response.addCookie(userName);
                        response.sendRedirect("/public_webapp/ElencoAnnunciServlet");
                        
                    }else 
                    {
                        out.println("<div align=center><font color=red >Non ci sono utenti con queste credenziali,<br> premi REGISTRATI per creare un account<br> o ricontrolla i tuoi dati.</font></div>");
                        rd.include(request, response);
                    }
                } catch (Exception ex) {
                    request.setAttribute("msg", "Errore nella lettura del Database");
                    RequestDispatcher rd_forward = getServletContext().getRequestDispatcher("/jsp/error.jsp");
                    rd_forward.forward(request, response);
                }
            } else {
                //Non esiste il database utenti, quindi l'utente andrà rimandanto alla registrazione
                out.println("<div align=center><font color=red >Non ci sono utenti con queste credenziali,<br> premi REGISTRATI per creare un account<br> o ricontrolla i tuoi dati.</font></div>");
                rd.include(request, response);  
            }
                
        } else {
            
            out.println("<div align=center><font color=red >L'username o la password sono sbagliate.</font></div>");
            rd.include(request, response);
        }
    }
}
