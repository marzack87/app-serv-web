/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package search;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
public class AnnuncioServlet extends HttpServlet {
    
    public void doGet(
		HttpServletRequest request,
		HttpServletResponse response)
		throws ServletException, IOException {
		try {
			response.setContentType("text/html");
       
                        String path = request.getSession().getServletContext().getRealPath("/WEB-INF/xml/");
                        path = path+"/home_db.xml";
                        
                        GestioneAnnunci gest = new GestioneAnnunci();
                        
                        ArrayList<Apartment> apartments = gest.cercaAppartamentoPerID(path,request.getParameter("id_apartment"));
                        
                        if (request.getParameter("edit") != null){
                            request.setAttribute("apartments_list", apartments);
                            RequestDispatcher rd_forward = getServletContext().getRequestDispatcher("/jsp/user_modifica.jsp");
                            rd_forward.forward(request, response);
                        } else {
                            request.setAttribute("apartments_list", apartments);
                            request.setAttribute("from", "search");
                            RequestDispatcher rd_forward = getServletContext().getRequestDispatcher("/jsp/user_elenco_annunci.jsp");
                            rd_forward.forward(request, response);   
                        }
                        
		} catch (Exception e) {
			request.setAttribute("msg", e.getMessage());
                        RequestDispatcher rd_forward = getServletContext().getRequestDispatcher("/jsp/error.jsp");
                        rd_forward.forward(request, response);

		}
	}
}
