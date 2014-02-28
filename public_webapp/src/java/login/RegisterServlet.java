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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;  

/**
 *
 * @author Piero
 */
public class RegisterServlet extends HttpServlet {

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
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/jsp/register.jsp");
        PrintWriter out= response.getWriter();
        
        if ((request.getParameter("user") != null && !request.getParameter("user").isEmpty()) 
                && (request.getParameter("pwd") != null && !request.getParameter("pwd").isEmpty()) 
                && (request.getParameter("name") != null && !request.getParameter("name").isEmpty()) 
                && (request.getParameter("surname") != null && !request.getParameter("surname").isEmpty())
                && (request.getParameter("phone") != null && !request.getParameter("phone").isEmpty()))
        {
            String user = request.getParameter("user");
            String pwd = request.getParameter("pwd");
            String name = request.getParameter("name");
            String surname = request.getParameter("surname");
            String phone = request.getParameter("phone");
            
            String path = request.getSession().getServletContext().getRealPath("/WEB-INF/xml/");
            path = path+"/users.xml";
            
            if (checkDatabase(path))
            {
                try {
                    //Controllo se l'utente esiste, se non esiste lo creo
                    if (check_add_user(path, name, surname, phone, user, pwd))
                    {
                        out.println("<div align=center><font color=green >Utente creato correttamente.</font></div>");
                        rd.include(request, response);
                    } else {
                        out.println("<div align=center><font color=red >Username già esistente.</font></div>");
                        rd.include(request, response);
                    }
                    
                } catch (Exception ex) {
                    request.setAttribute("msg", "Errore nella creazione dell'utente");
                    RequestDispatcher rd_forward = getServletContext().getRequestDispatcher("/jsp/error.jsp");
                    rd_forward.forward(request, response);
                }
            } else {
                request.setAttribute("msg", "Errore nella lettura del Database");
                RequestDispatcher rd_forward = getServletContext().getRequestDispatcher("/jsp/error.jsp");
                rd_forward.forward(request, response);
            }
        } else {
            
            out.println("<div align=center><font color=red >Tutti i campi sono obbligatori.</font></div>");
            rd.include(request, response);
        }
    }
    
    private boolean checkDatabase (String path)
    {
        File f = new File(path);
        if(f.exists() && !f.isDirectory())
        {
            //Esiste il database utenti, controllo se è presente l'utente
            return true;
        } else {
            //Non esiste il database quindi è la prima registrazione e va creato
            try
            {
                DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
                Document doc = documentBuilder.newDocument();
                Element root_users = doc.createElement("Users");
                doc.appendChild(root_users);

                DOMSource source = new DOMSource(doc);

                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                StreamResult result = new StreamResult(path);
                transformer.transform(source, result);
                
                return true;
            }
            catch(Exception e)
            {
               System.out.println(e);
               return false;
            }
        }
    }
    
    private boolean check_add_user(String pathToWrite, String name,
            String surname, String phone, String username, String pwd) throws Exception {
        
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(pathToWrite);
        
        boolean register_user = true;
        
        NodeList nList = document.getElementsByTagName("User");
        
        for (int temp = 0; temp < nList.getLength(); temp++)
        {
            Node nNode = nList.item(temp);
            
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                if ((eElement.getAttribute("user_name")).equals(username))
                {
                    register_user = false;
                    break;
                }
            }
        }
        
        if (!register_user)
        {
            return false;
        }
        
        Element root = document.getDocumentElement();
        
        //This method creates an element node
        Element root_user = document.createElement("User");
        root_user.setAttribute("user_name", username);
        root.appendChild(root_user);

        Element username_child = document.createElement("Username");
        root_user.appendChild(username_child);

        Text text = document.createTextNode(username);
        username_child.appendChild(text);

        Element password_child = document.createElement("Password");
        root_user.appendChild(password_child);

        Text text1 = document.createTextNode(pwd);
        password_child.appendChild(text1);
        
        Element name_element = document.createElement("Name");
        root_user.appendChild(name_element);

        Text text2 = document.createTextNode(name);
        name_element.appendChild(text2);
        
        Element surname_element = document.createElement("Surname");
        root_user.appendChild(surname_element);

        Text text3 = document.createTextNode(surname);
        surname_element.appendChild(text3);
        
        Element phone_element = document.createElement("Phone");
        root_user.appendChild(phone_element);

        Text text4 = document.createTextNode(phone);
        phone_element.appendChild(text4);
        
        DOMSource source = new DOMSource(document);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        StreamResult result = new StreamResult(pathToWrite);
        transformer.transform(source, result);
      
        return register_user;
    }

}