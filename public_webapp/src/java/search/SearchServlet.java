/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package search;

import gestione_annunci.Apartment;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import static java.lang.Thread.sleep;
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
public class SearchServlet extends HttpServlet {
    
    /**
	 * Get a String-object from the applet and send it back.
	 */
	public void doPost(
		HttpServletRequest request,
		HttpServletResponse response)
		throws ServletException, IOException {
		try {
			response.setContentType("application/x-java-serialized-object");

			// read a String-object from applet
			// instead of a String-object, you can transmit any object, which
			// is known to the servlet and to the applet
			InputStream in = request.getInputStream();
			ObjectInputStream inputFromApplet = new ObjectInputStream(in);
                        
                        String path = request.getSession().getServletContext().getRealPath("/WEB-INF/xml/");
                        path = path+"/home_db.xml";
			String[] parameter = (String[]) inputFromApplet.readObject();
                        
                        ArrayList<Apartment> apartments = searchXML(path,parameter);
			// echo it to the applet
			OutputStream outstr = response.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(outstr);
			oos.writeObject(apartments);
			oos.flush();
			oos.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
        
        public ArrayList<Apartment> searchXML (String path,final String [] parameters)
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
                boolean ap_image = false;
                
                /*
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
                */
                
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
                       
                       if (!parameters[0].equals("") && !aprt.address.toLowerCase().contains(parameters[0].toLowerCase()))
                       {
                           jump_element = true;
                       }
                   }
                   if (ap_prezzo_posto){
                       int prezzo_apt = Integer.parseInt(new String(ch, start, length));
                       int prezzo_search = (!parameters[1].equals("")) ? Integer.parseInt(parameters[1]) : 0;
                       aprt.prezzo = new String(ch, start, length);
                       ap_prezzo_posto = false;
                       if(!parameters[1].equals("") && prezzo_apt > prezzo_search)
                       {
                           jump_element = true;
                       }
                   }
                   if (ap_posti_liberi){
                       int posti_apt = Integer.parseInt(new String(ch, start, length));
                       int posti_search = (!parameters[2].equals("")) ? Integer.parseInt(parameters[2]) : 0;
                       aprt.posti_liberi = new String(ch, start, length);
                       ap_posti_liberi = false;
                       if (!parameters[2].equals("") && posti_apt < posti_search)
                       {
                           jump_element = true;
                       }
                   }
                   if (ap_tipo_alloggio){
                       aprt.tipologia = new String(ch, start, length);
                       ap_tipo_alloggio = false;
                       if (!parameters[3].equals("") && !parameters[3].equals(aprt.tipologia))
                       {
                           jump_element = true;
                       }
                       
                   }
                   if (ap_civico){
                       aprt.civico = new String(ch, start, length);
                       ap_civico = false;
                   }
                   if (ap_citta){
                       aprt.citta = new String(ch, start, length);
                       ap_citta = false;
                   }
                   if (ap_image){
                       aprt.img_url.add(new String(ch, start, length));
                       ap_image = false;
                   }
                }
                                
                public void endElement(String uri, String localName,
                        String qName) throws SAXException {

                    //Finito elemento Apartment, se è diverso da nil lo metto in lista
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
            }
            
              return list;
        }

}
