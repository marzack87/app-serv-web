/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package asw1016;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

/**
 *
 * @author marco
 */
@WebServlet(name = "NuovoAnnuncioServlet", urlPatterns = {"/NuovoAnnuncioServlet"})
public class NuovoAnnuncioServlet extends HttpServlet {
    
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
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/jsp/user_nuovo.jsp");
        PrintWriter out= response.getWriter();
        
        String path = request.getSession().getServletContext().getRealPath("/WEB-INF/xml/");
        path = path+"/home_db.xml";
        
        if (checkDatabase(path))
        {
            try 
            {
                String user = request.getParameter("user_name");
                String indirizzo = request.getParameter("indirizzo");
                String civico = request.getParameter("civico");
                String citta = request.getParameter("citta");
                String tipo_alloggio = request.getParameter("tipo_alloggio");
                String tipo_cucina = request.getParameter("tipo_cucina");
                String bagni = request.getParameter("bagni");
                String camere_da_letto = request.getParameter("camere_da_letto");
                String n_piano = request.getParameter("n_piano");
                String ascensore = "0";
                if (request.getParameter("ascensore") != null)
                {
                    ascensore = "1";
                }
                String garage = "0";
                if (request.getParameter("garage") != null)
                {
                    garage = "1";
                }
                String terrazzo = "0";
                if (request.getParameter("terrazzo") != null)
                {
                    terrazzo = "1";
                }
                String posti_totali = request.getParameter("posti_totali");
                String posti_liberi = request.getParameter("posti_liberi");
                String prezzo_posto = request.getParameter("prezzo_posto");
                String[] spese_incluse = request.getParameterValues("spese_incluse");
                String acqua = "0";
                String gas = "0";
                String luce = "0";
                String condominiali = "0";
                String nessune_spese = "0";
                
                for (int i = 0; i < spese_incluse.length; i++) {
                    switch (Integer.parseInt(spese_incluse[i])) {
                        case 0:
                            acqua = "1";
                            break;
                        case 1:
                            gas = "1";
                            break;
                        case 2:
                            luce = "1";
                            break;
                        case 3:
                            condominiali = "1";
                            break;
                        case 4:
                            nessune_spese = "1";
                            break;
                    }
                }
                
                String id_apartment = GestioneAnnunci.aggiungiAnnuncio(path, user, indirizzo, civico, citta, tipo_alloggio,
                        tipo_cucina, bagni, camere_da_letto, n_piano, ascensore, garage, 
                        terrazzo, posti_totali, posti_liberi, prezzo_posto, acqua, gas, luce, condominiali,nessune_spese);
                
                if (id_apartment != null)
                {
                    //Annuncio inserito correttamente
                    request.setAttribute("id_apartment", id_apartment);
                    RequestDispatcher rd_forward = getServletContext().getRequestDispatcher("/jsp/user_nuovo_foto.jsp");
                    rd_forward.forward(request, response);
                    
                } else {
                    out.println("<div align=center><font color=red >Errore inserimento annuncio.</font></div>");
                    rd.include(request, response);
                }
                  
            } catch (Exception ex) {
                out.println("<div align=center><font color=red >Errore nella creazionde dell'annuncio.</font></div>");
                rd.include(request, response);
            }
            
        } else {
            out.println("<div align=center><font color=red >Errore nella lettura del database.</font></div>");
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
                Element root_users = doc.createElement("Apartments");
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
}
