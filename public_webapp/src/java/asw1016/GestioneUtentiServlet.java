/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package asw1016;

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
                        
                        String query = request.getParameter("q");
                        
                        if (query.equals("user_info")){
                            
                            String user_name = (String) request.getSession().getAttribute("user");
                            User user = GestioneUtente.getUserInfo(path, user_name);
                            
                            request.setAttribute("utente", user);
                            RequestDispatcher rd_forward = getServletContext().getRequestDispatcher("/jsp/user_info.jsp");
                            rd_forward.forward(request, response);
                        
                        } else if (query.equals("users_list")){
                            
                            String user_name = (String) request.getSession().getAttribute("user");
                            
                            if (GestioneUtente.getUserInfo(path, user_name).isAdmin()){
                            
                                ArrayList<User> users = GestioneUtente.getAllUser(path);
                            
                                request.setAttribute("lista_utenti", users);
                                RequestDispatcher rd_forward = getServletContext().getRequestDispatcher("/jsp/admin_utenti.jsp");
                                rd_forward.forward(request, response);
                            
                            } else {
                            
                                request.setAttribute("msg", "non hai i diritti necessari per accedere alla lista di tutti gli utenti");
                                RequestDispatcher rd_forward = getServletContext().getRequestDispatcher("/jsp/error.jsp");
                                rd_forward.forward(request, response);
                            
                            }
                        } else if (query.equals("delete_user")) {
                            
                            String user_to_delete = request.getParameter("user");
                            if (user_to_delete != null) {
                                int result = GestioneUtente.deleteUser(path, user_to_delete);
                                
                                if (result == 0){
                                    response.sendRedirect("/public_webapp/GestioneUtentiServlet?q=users_list");
                                } else if (result == 1) {
                                    request.setAttribute("msg", "L'username " + user_to_delete + " non corrisponde a nessun utente");
                                    RequestDispatcher rd_forward = getServletContext().getRequestDispatcher("/jsp/error.jsp");
                                    rd_forward.forward(request, response);
                                } else {
                                    request.setAttribute("msg", "Si è verificato un problema durante l'eliminazione dell'utente");
                                    RequestDispatcher rd_forward = getServletContext().getRequestDispatcher("/jsp/error.jsp");
                                    rd_forward.forward(request, response);
                                }
                                
                            } else {
                                response.sendRedirect("/public_webapp/index.jsp");
                            }
                            
                        } else {
                                response.sendRedirect("/public_webapp/index.jsp");
                        }
                        
		} catch (Exception e) {
			request.setAttribute("msg", e.getMessage());
                        RequestDispatcher rd_forward = getServletContext().getRequestDispatcher("/jsp/error.jsp");
                        rd_forward.forward(request, response);
		}
                
	}
                
                public void doPost(
		HttpServletRequest request,
		HttpServletResponse response)
		throws ServletException, IOException {
		try {
			response.setContentType("text/html");
       
                        String path = request.getSession().getServletContext().getRealPath("/WEB-INF/xml/");
                        path = path+"/users.xml";
                        
                        String query = request.getParameter("q");
                        
                        if (query.equals("edit_user")) {
                            
                            String user_to_edit = request.getParameter("username");
                            if (user_to_edit != null) {
                                
                                String name = request.getParameter("name");
                                String surname = request.getParameter("surname");
                                String phone = request.getParameter("phone");
                                String password = request.getParameter("password");
                                
                                int result = GestioneUtente.editUser(path, user_to_edit, password, name, surname, phone, "0");
                                
                                if (result == 0){
                                    response.sendRedirect("/public_webapp/GestioneUtentiServlet?q=user_info");
                                } else if (result == 1) {
                                    request.setAttribute("msg", "L'username " + user_to_edit + " non corrisponde a nessun utente");
                                    RequestDispatcher rd_forward = getServletContext().getRequestDispatcher("/jsp/error.jsp");
                                    rd_forward.forward(request, response);
                                } else {
                                    request.setAttribute("msg", "Si è verificato un problema durante l'eliminazione dell'utente");
                                    RequestDispatcher rd_forward = getServletContext().getRequestDispatcher("/jsp/error.jsp");
                                    rd_forward.forward(request, response);
                                }
                                
                            } else {
                                response.sendRedirect("/public_webapp/index.jsp");
                            }
                            
                        } else {
                                response.sendRedirect("/public_webapp/index.jsp");
                        }
                        
		} catch (Exception e) {
			request.setAttribute("msg", e.getMessage());
                        RequestDispatcher rd_forward = getServletContext().getRequestDispatcher("/jsp/error.jsp");
                        rd_forward.forward(request, response);
		}
        }

}
