/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gestione_utenti;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Piero
 */
public class GestioneUtentiServlet extends HttpServlet {
    
            	public void doGet(
		HttpServletRequest request,
		HttpServletResponse response)
		throws ServletException, IOException {
		try {
			response.setContentType("text/html");
       
                        String path = request.getSession().getServletContext().getRealPath("/WEB-INF/xml/");
                        path = path+"/users.xml";
                        
                        //getUserInfo ha bisogno del path qui sopra e del nome utente e ritorna un oggetto User
                        //getAllUser ha bisogno del path qui sopra e basta e ritorna un array list di utenti
                        //editUser ha bisogno del path qui sopra e di tutti i dati utente e ritorna un int guarda nel metodo in user per sapere i valori
                        //deleteUser ha bisogno del path qui sopra e dell'username e ritorna un int come sopra guarda nel metodo per sapere i valori
                        
                        //Questi qui decidi tu come scriverli e cosa ti serve
                        //request.setAttribute("users_list", users);
                        //request.setAttribute("from", "user");
                        //RequestDispatcher rd_forward = getServletContext().getRequestDispatcher("/jsp/user_elenco_annunci.jsp");
                        //rd_forward.forward(request, response);
                        
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
