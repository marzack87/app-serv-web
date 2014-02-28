/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gestione_annuncio;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
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
 * @author Piero
 */
public class ModificaAnnuncioServlet extends HttpServlet {

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
        //RequestDispatcher rd = getServletContext().getRequestDispatcher("/jsp/user_nuovo.jsp");
        //PrintWriter out= response.getWriter();
        
        String path = request.getSession().getServletContext().getRealPath("/WEB-INF/xml/");
        path = path+"/home_db.xml";
        
        if (checkDatabase(path))
        {
            try 
            {
                String apartment_id = request.getParameter("id_apartment");
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
                
                String images = request.getParameter("images");
                
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
                int annuncioedited = editAnnuncio(path, apartment_id, user, indirizzo, civico, citta, tipo_alloggio,
                        tipo_cucina, bagni, camere_da_letto, n_piano, ascensore, garage, 
                        terrazzo, posti_totali, posti_liberi, prezzo_posto, acqua, gas, luce, condominiali,nessune_spese);
                
                if (annuncioedited == 0)
                {
                    //Modificato correttamente
                    request.setAttribute("images", images);
                    request.setAttribute("id_apartment", apartment_id);
                    RequestDispatcher rd_forward = getServletContext().getRequestDispatcher("/jsp/user_modifica_foto.jsp");
                    rd_forward.forward(request, response);
                } else if (annuncioedited == 1)
                {
                    //Annuncio non trovato con quell'id
                    request.setAttribute("msg", "nessun annuncio trovato con l'ID indicato");
                    RequestDispatcher rd_forward = getServletContext().getRequestDispatcher("/jsp/error.jsp");
                    rd_forward.forward(request, response);
                } else if (annuncioedited == 2)
                {
                    //Errore nella modifica dell'annuncio
                    request.setAttribute("msg", "si è verificato un problema durante la modifica dell'annuncio");
                    RequestDispatcher rd_forward = getServletContext().getRequestDispatcher("/jsp/error.jsp");
                    rd_forward.forward(request, response);
                }
                  
            } catch (Exception ex) {
                //out.println("<div align=center><font color=red >Errore nella modifica dell'annuncio.</font></div>");
                //rd.include(request, response);
            }
            
        } else {
            //out.println("<div align=center><font color=red >Errore nella lettura del database.</font></div>");
            //rd.include(request, response);
        }
    }
    
    private int editAnnuncio (String pathToWrite, String apartment_id, String user,String indirizzo,String civico,String citta,
            String tipo_alloggio,String tipo_cucina,String bagni,
            String camere_da_letto,String n_piano,String ascensore,String garage,
            String terrazzo,String posti_totali,String posti_liberi,String prezzo_posto,
            String acqua,String gas,String luce,String condominiali, String nessune_spese) throws Exception
    {
        try
        {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(pathToWrite);

            //Prendo tutti gli apartment:
            NodeList apartments = document.getElementsByTagName("Apartment");

            for (int i = 0; i < apartments.getLength(); i++)
            {
                Node node = apartments.item(i);
                NodeList list = node.getChildNodes();
                boolean find = false;
                for (int k = 0; k < list.getLength(); k++)
                {
                    if ("ID".equals(list.item(k).getNodeName()))
                    {
                        if (list.item(k).getTextContent().equals(apartment_id))
                        {
                            find = true;
                            break;
                        } else {
                            break;
                        }
                    }
                }

                if (find)
                {
                    for (int n = 0; n < list.getLength(); n++)
                    {
                        if (list.item(n).getNodeName().equals("Indirizzo"))
                        {
                            list.item(n).setTextContent(indirizzo);
                        } else if (list.item(n).getNodeName().equals("Prezzo_Posto"))
                        {
                            list.item(n).setTextContent(prezzo_posto);
                        } else if (list.item(n).getNodeName().equals("Posti_Liberi"))
                        {
                            list.item(n).setTextContent(posti_liberi);
                        } else if (list.item(n).getNodeName().equals("Civico"))
                        {
                            list.item(n).setTextContent(civico);
                        } else if (list.item(n).getNodeName().equals("Citta"))
                        {
                            list.item(n).setTextContent(citta);
                        } else if (list.item(n).getNodeName().equals("Tipo_Alloggio"))
                        {
                            list.item(n).setTextContent(tipo_alloggio);
                        } else if (list.item(n).getNodeName().equals("Tipo_Cucina"))
                        {
                            list.item(n).setTextContent(tipo_cucina);
                        } else if (list.item(n).getNodeName().equals("Bagni"))
                        {
                            list.item(n).setTextContent(bagni);
                        } else if (list.item(n).getNodeName().equals("Camere_Letto"))
                        {
                            list.item(n).setTextContent(camere_da_letto);
                        } else if (list.item(n).getNodeName().equals("N_Piano"))
                        {
                            list.item(n).setTextContent(n_piano);
                        } else if (list.item(n).getNodeName().equals("Ascensore"))
                        {
                            list.item(n).setTextContent(ascensore);
                        } else if (list.item(n).getNodeName().equals("Garage"))
                        {
                            list.item(n).setTextContent(garage);
                        } else if (list.item(n).getNodeName().equals("Terrazzo"))
                        {
                            list.item(n).setTextContent(terrazzo);
                        } else if (list.item(n).getNodeName().equals("Posti_Totali"))
                        {
                            list.item(n).setTextContent(posti_totali);
                        } else if (list.item(n).getNodeName().equals("Spese_Acqua"))
                        {
                            list.item(n).setTextContent(acqua);
                        } else if (list.item(n).getNodeName().equals("Spese_Gas"))
                        {
                            list.item(n).setTextContent(gas);
                        } else if (list.item(n).getNodeName().equals("Spese_Luce"))
                        {
                            list.item(n).setTextContent(luce);
                        } else if (list.item(n).getNodeName().equals("Spese_Condominiali"))
                        {
                            list.item(n).setTextContent(condominiali);
                        }
                        else if (list.item(n).getNodeName().equals("Nessune_Spese"))
                        {
                            list.item(n).setTextContent(nessune_spese);
                        }
                    }
                    TransformerFactory transformerFactory = TransformerFactory.newInstance();
                    Transformer transformer = transformerFactory.newTransformer();
                    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                    DOMSource source = new DOMSource(document);
                    StreamResult result = new StreamResult(new File(pathToWrite));
                    transformer.transform(source, result);
                    
                    return 0;
                }
            }
            
            return 1;
        
        } catch (Exception ex)
        {
            return 2;
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
            return false;
        }
    }

}
