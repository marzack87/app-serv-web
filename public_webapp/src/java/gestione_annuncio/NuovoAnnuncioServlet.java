/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gestione_annuncio;

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
                String id_apartment = add_apartment(path, user, indirizzo, civico, citta, tipo_alloggio,
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
    
    private String add_apartment(String pathToWrite, String user,String indirizzo,String civico,String citta,
            String tipo_alloggio,String tipo_cucina,String bagni,
            String camere_da_letto,String n_piano,String ascensore,String garage,
            String terrazzo,String posti_totali,String posti_liberi,String prezzo_posto,
            String acqua,String gas,String luce,String condominiali, String nessune_spese) throws Exception {
        
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(pathToWrite);
        
        Element root = document.getDocumentElement();
        
        //This method creates an element node
        Element root_user = document.createElement("Apartment");
        root_user.setAttribute("user_name", user);
        root.appendChild(root_user);
        
        String id_apartment = String.valueOf(UUID.randomUUID());
        Element id_apartment_el = document.createElement("ID");
        root_user.appendChild(id_apartment_el);

        Text text_id = document.createTextNode(id_apartment);
        id_apartment_el.appendChild(text_id);
        
        Element indirizzo_el = document.createElement("Indirizzo");
        root_user.appendChild(indirizzo_el);

        Text text = document.createTextNode(indirizzo);
        indirizzo_el.appendChild(text);

        Element civico_el = document.createElement("Civico");
        root_user.appendChild(civico_el);

        Text text1 = document.createTextNode(civico);
        civico_el.appendChild(text1);
        
        Element citta_el = document.createElement("Citta");
        root_user.appendChild(citta_el);

        Text text2 = document.createTextNode(citta);
        citta_el.appendChild(text2);
        
        Element tipo_alloggio_el = document.createElement("Tipo_Alloggio");
        root_user.appendChild(tipo_alloggio_el);

        Text text3 = document.createTextNode(tipo_alloggio);
        tipo_alloggio_el.appendChild(text3);
        
        Element tipo_cucina_el = document.createElement("Tipo_Cucina");
        root_user.appendChild(tipo_cucina_el);

        Text text4 = document.createTextNode(tipo_cucina);
        tipo_cucina_el.appendChild(text4);
        
        Element bagni_el = document.createElement("Bagni");
        root_user.appendChild(bagni_el);

        Text text5 = document.createTextNode(bagni);
        bagni_el.appendChild(text5);
        
        Element camere_lett_el = document.createElement("Camere_Letto");
        root_user.appendChild(camere_lett_el);

        Text text6 = document.createTextNode(camere_da_letto);
        camere_lett_el.appendChild(text6);
        
        Element n_piano_el = document.createElement("N_Piano");
        root_user.appendChild(n_piano_el);

        Text text7 = document.createTextNode(n_piano);
        n_piano_el.appendChild(text7);
        
        Element ascensore_el = document.createElement("Ascensore");
        root_user.appendChild(ascensore_el);

        Text text8 = document.createTextNode(ascensore);
        ascensore_el.appendChild(text8);
        
        Element garage_el = document.createElement("Garage");
        root_user.appendChild(garage_el);

        Text text9 = document.createTextNode(garage);
        garage_el.appendChild(text9);
        
        Element terrazzo_el = document.createElement("Terrazzo");
        root_user.appendChild(terrazzo_el);

        Text text10 = document.createTextNode(terrazzo);
        terrazzo_el.appendChild(text10);
        
        Element posti_totali_el = document.createElement("Posti_Totali");
        root_user.appendChild(posti_totali_el);

        Text text11 = document.createTextNode(posti_totali);
        posti_totali_el.appendChild(text11);
        
        Element posti_liberi_el = document.createElement("Posti_Liberi");
        root_user.appendChild(posti_liberi_el);

        Text text12 = document.createTextNode(posti_liberi);
        posti_liberi_el.appendChild(text12);
        
        Element prezzo_posto_el = document.createElement("Prezzo_Posto");
        root_user.appendChild(prezzo_posto_el);

        Text text13 = document.createTextNode(prezzo_posto);
        prezzo_posto_el.appendChild(text13);
        
        Element acqua_el = document.createElement("Spese_Acqua");
        root_user.appendChild(acqua_el);

        Text text14 = document.createTextNode(acqua);
        acqua_el.appendChild(text14);
        
        Element gas_el = document.createElement("Spese_Gas");
        root_user.appendChild(gas_el);

        Text text15 = document.createTextNode(gas);
        gas_el.appendChild(text15);
        
        Element luce_el = document.createElement("Spese_Luce");
        root_user.appendChild(luce_el);

        Text text16 = document.createTextNode(luce);
        luce_el.appendChild(text16);
        
        Element spese_cond_el = document.createElement("Spese_Condominiali");
        root_user.appendChild(spese_cond_el);

        Text text17 = document.createTextNode(condominiali);
        spese_cond_el.appendChild(text17);
        
        Element nessune_spese_el = document.createElement("Nessune_Spese");
        root_user.appendChild(nessune_spese_el);

        Text text18 = document.createTextNode(nessune_spese);
        nessune_spese_el.appendChild(text18);
        
        DOMSource source = new DOMSource(document);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        StreamResult result = new StreamResult(pathToWrite);
        transformer.transform(source, result);
        
        return id_apartment;
    }
}
