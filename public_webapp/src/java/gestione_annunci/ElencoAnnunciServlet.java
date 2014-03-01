/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gestione_annunci;

import java.io.*;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import utility.Apartment;
import utility.GestioneAnnunci;

/**
 *
 * @author Piero
 */
public class ElencoAnnunciServlet extends HttpServlet {
    
    	public void doGet(
		HttpServletRequest request,
		HttpServletResponse response)
		throws ServletException, IOException {
		try {
			response.setContentType("text/html");
       
                        String path = request.getSession().getServletContext().getRealPath("/WEB-INF/xml/");
                        path = path+"/home_db.xml";
                        
                        HttpSession session = request.getSession();
                        
                        boolean admin = false;
                        
                        if (session.getAttribute("admin").equals("1")) {
                            admin = true;
                        }
                        
                        GestioneAnnunci gest = new GestioneAnnunci();
                        
                        ArrayList<Apartment> apartments = gest.listAnnunci(path,(String) session.getAttribute("user"), admin);
                        
                        if (admin) request.setAttribute("admin", "1");
                        request.setAttribute("apartments_list", apartments);
                        request.setAttribute("from", "user");
                        RequestDispatcher rd_forward = getServletContext().getRequestDispatcher("/jsp/user_elenco_annunci.jsp");
                        rd_forward.forward(request, response);
                        
		} catch (Exception e) {
			request.setAttribute("msg", e.getMessage());
                        RequestDispatcher rd_forward = getServletContext().getRequestDispatcher("/jsp/error.jsp");
                        rd_forward.forward(request, response);
		}
	}
}
