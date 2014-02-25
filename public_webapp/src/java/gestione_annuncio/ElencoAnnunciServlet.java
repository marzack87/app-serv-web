/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gestione_annuncio;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

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
                        
                        ArrayList<Apartment> apartments = searchXMLForUser(path,request.getParameter("user"));
                        
                        if (apartments.size() > 0)
                        {
                            //
                        } else {
                            
                        }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
        
    public ArrayList<Apartment> searchXMLForUser (String path, final String user_name)
        {
            final ArrayList<Apartment> list = new ArrayList<Apartment>();
            
            try 
            {
                SAXParserFactory factory = SAXParserFactory.newInstance();
                SAXParser saxParser = factory.newSAXParser();

                DefaultHandler handler = new DefaultHandler() {
                    
                //0 - indirizzo
                //1 - prezzo
                //2 - posti liberi
                //3 - tipologia
                    
                boolean ap_id = false;
                boolean ap_address = false;
                boolean ap_civico = false;
                boolean ap_citta = false;
                boolean ap_posti_liberi = false;
                boolean ap_prezzo_posto = false;
                boolean ap_tipo_alloggio = false;
                boolean ap_tipo_cucina = false;
                boolean ap_bagni = false;
                boolean ap_cam_letto = false;
                boolean ap_piano = false;
                boolean ap_ascensore = false;
                boolean ap_garage = false;
                boolean ap_terrazzo = false;
                boolean ap_posti_tot = false;
                boolean ap_spese_acqua = false;
                boolean ap_spese_gas = false;
                boolean ap_spese_luce = false;
                boolean ap_spese_cond = false;
                boolean ap_nessuna_spesa = false;
                boolean ap_image = false;
                
                boolean jump_element = false;
                
                Apartment aprt = null;
                
                public void startElement(String uri, String localName,String qName, 
                        Attributes attributes) throws SAXException {

                    if (qName.equals("Apartment"))
                    {
                        aprt = new Apartment();
                        aprt.user_owner = attributes.getValue("user_name");
                        aprt.img_url = new ArrayList<String> ();
                        jump_element = false;
                        if (!attributes.getValue("user_name").equals(user_name))
                        {
                            jump_element = true;
                        }
                        
                        
                    } else if (qName.equals("ID") && !jump_element)
                    {
                        ap_id = true;
                    } else if (qName.equals("Indirizzo") && !jump_element)
                    {
                        ap_address = true;
                    } else if (qName.equals("Prezzo_Posto") && !jump_element)
                    {
                        ap_prezzo_posto = true;
                    } else if (qName.equals("Posti_Liberi") && !jump_element)
                    {
                        ap_posti_liberi = true;
                    } else if (qName.equals("Civico") && !jump_element)
                    {
                        ap_civico = true;
                    } else if (qName.equals("Citta") && !jump_element)
                    {
                        ap_citta = true;
                    } else if (qName.equals("Tipo_Alloggio") && !jump_element)
                    {
                        ap_tipo_alloggio = true;
                    } else if (qName.equals("Tipo_Cucina") && !jump_element)
                    {
                        ap_tipo_cucina = true;
                    } else if (qName.equals("Bagni") && !jump_element)
                    {
                        ap_bagni = true;
                    } else if (qName.equals("Camere_Letto") && !jump_element)
                    {
                        ap_cam_letto = true;
                    } else if (qName.equals("N_Piano") && !jump_element)
                    {
                        ap_piano = true;
                    } else if (qName.equals("Ascensore") && !jump_element)
                    {
                        ap_ascensore = true;
                    } else if (qName.equals("Garage") && !jump_element)
                    {
                        ap_garage = true;
                    } else if (qName.equals("Terrazzo") && !jump_element)
                    {
                        ap_terrazzo = true;
                    } else if (qName.equals("Posti_Totali") && !jump_element)
                    {
                        ap_posti_tot = true;
                    } else if (qName.equals("Spese_Acqua") && !jump_element)
                    {
                        ap_spese_acqua = true;
                    } else if (qName.equals("Spese_Gas") && !jump_element)
                    {
                        ap_spese_gas = true;
                    } else if (qName.equals("Spese_Luce") && !jump_element)
                    {
                        ap_spese_luce = true;
                    } else if (qName.equals("Spese_Condominiali") && !jump_element)
                    {
                        ap_spese_cond = true;
                    }
                    else if (qName.equals("Nessune_Spese") && !jump_element)
                    {
                        ap_nessuna_spesa = true;
                    } else if (qName.equals("Image") && !jump_element)
                    {
                        ap_image = true;
                    }
                }

               public void characters(char ch[], int start, int length) throws SAXException {
                   
                   if (ap_id) {
                       aprt.id_apartment = new String(ch, start, length);
                       ap_id = false;
                   }
                   if (ap_address){
                       aprt.address = new String(ch, start, length);
                       ap_address = false;
                   }
                   if (ap_prezzo_posto){
                       aprt.prezzo = new String(ch, start, length);
                       ap_prezzo_posto = false;
                   }
                   if (ap_posti_liberi){
                       aprt.posti_liberi = new String(ch, start, length);
                       ap_posti_liberi = false;
                   }
                   if (ap_tipo_alloggio){
                       aprt.tipologia = new String(ch, start, length);
                       ap_tipo_alloggio = false;
                       
                   }
                   if (ap_civico){
                       aprt.civico = new String(ch, start, length);
                       ap_civico = false;
                   }
                   if (ap_citta){
                       aprt.citta = new String(ch, start, length);
                       ap_citta = false;
                   }
                   if (ap_tipo_cucina){
                       aprt.tipo_cucina = new String(ch, start, length);
                       ap_tipo_cucina = false;
                   }
                   if (ap_bagni){
                       aprt.bagni = new String(ch, start, length);
                       ap_bagni = false;
                   }
                   if (ap_cam_letto){
                       aprt.camere_letto = new String(ch, start, length);
                       ap_cam_letto = false;
                   }
                   if (ap_piano){
                       aprt.n_piano = new String(ch, start, length);
                       ap_piano = false;
                   }
                   if (ap_ascensore){
                       aprt.ascensore = new String(ch, start, length);
                       ap_ascensore = false;
                   }
                   if (ap_garage){
                       aprt.garage = new String(ch, start, length);
                       ap_garage = false;
                   }
                   if (ap_terrazzo){
                       aprt.terrazzo = new String(ch, start, length);
                       ap_terrazzo = false;
                   }
                   if (ap_posti_tot){
                       aprt.posti_totali = new String(ch, start, length);
                       ap_posti_tot = false;
                   }
                   if (ap_spese_acqua){
                       aprt.spese_acqua = new String(ch, start, length);
                       ap_spese_acqua = false;
                   }
                   if (ap_spese_gas){
                       aprt.spese_gas = new String(ch, start, length);
                       ap_spese_gas = false;
                   }
                   if (ap_spese_luce){
                       aprt.spese_luce = new String(ch, start, length);
                       ap_spese_luce = false;
                   }
                   if (ap_spese_cond){
                       aprt.spese_cond = new String(ch, start, length);
                       ap_spese_cond = false;
                   }
                   if (ap_nessuna_spesa){
                       aprt.ness_spesa = new String(ch, start, length);
                       ap_nessuna_spesa = false;
                   }
                   if (ap_image){
                       aprt.img_url.add(new String(ch, start, length));
                   }
                }
                                
                public void endElement(String uri, String localName,
                        String qName) throws SAXException {

                    //Finito elemento Apartment, se è diverso da nil lo metto in lista
                    if (qName.equals("Images"))
                    {
                        ap_image = false;
                    }
                    if (qName.equals("Apartment"))
                    {
                       if (!jump_element)
                       {
                           //controllo questo perchè se cè anche un solo elemento che non mi va bene metto = null e ho bisogno di un'altro
                           //flag per dire se continuare a leggere fino al prossimo elemento oppure no
                           if (aprt != null)
                           {
                               list.add(aprt);
                               aprt = null;
                           }
                       }
                    }
                }
 
            };

              saxParser.parse(path, handler);

            } catch (Exception e) {
              e.printStackTrace();
              System.out.println("erroe: "+e);
            }
            
              return list;
        }
}
