/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package asw1016;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Piero
 */
public class GestioneAnnunci {
    
    private GestioneAnnunci(){
        
    }
    
    public static ArrayList<Apartment> listAnnunci (String path, final String user_name, final boolean admin)
    {
        ArrayList<Apartment> list = cercaAnnunciPerUtente(path,user_name,admin);
        
        return list;
    }
    
    public static int eliminaAnnuncio (String pathToWrite, String apartment_id) throws ParserConfigurationException, SAXException, IOException, TransformerException
    {
        int result = deleteAnnuncio(pathToWrite,apartment_id);
        
        return result;
    }
    
    public static int eliminaAnnunciUtente (String path, String user_name) throws ParserConfigurationException, SAXException, IOException, TransformerException{
        
        ArrayList<Apartment> annunci_utente = cercaAnnunciPerUtente(path, user_name, false);
        
        int result = 0;
        
        for (Apartment ap : annunci_utente) {
            result = deleteAnnuncio(path, ap.getId());
            if (result == 2) return 2;
        }
        
        return 0;
    }
    
    public static int modificaAnnuncio (String pathToWrite, String apartment_id, String user,String indirizzo,String civico,String citta,
            String tipo_alloggio,String tipo_cucina,String bagni,
            String camere_da_letto,String n_piano,String ascensore,String garage,
            String terrazzo,String posti_totali,String posti_liberi,String prezzo_posto,
            String acqua,String gas,String luce,String condominiali, String nessune_spese) throws Exception
    {
        int result = editAnnuncio(pathToWrite, apartment_id, user, indirizzo, civico,
                citta, tipo_alloggio, tipo_cucina, bagni, camere_da_letto, n_piano, 
                ascensore, garage, terrazzo, posti_totali, posti_liberi, 
                prezzo_posto, acqua, gas, luce, condominiali, nessune_spese);
        
        return result;
        
    }
    
    public static String aggiungiAnnuncio (String pathToWrite, String user, String indirizzo,String civico,String citta,
            String tipo_alloggio,String tipo_cucina,String bagni,
            String camere_da_letto,String n_piano,String ascensore,String garage,
            String terrazzo,String posti_totali,String posti_liberi,String prezzo_posto,
            String acqua,String gas,String luce,String condominiali, String nessune_spese) throws Exception
    {
        String id_annuncio = add_apartment(pathToWrite, user, indirizzo, civico, citta, tipo_alloggio,
                tipo_cucina, bagni, camere_da_letto, n_piano, ascensore, garage, terrazzo, posti_totali, 
                posti_liberi, prezzo_posto, acqua, gas, luce, condominiali, nessune_spese);
        return id_annuncio;
    }
    
    public static int gestioneImmagini (ArrayList <String> images,ArrayList <String> images_to_delete, String pathToWrite, String apartment_id) throws ParserConfigurationException, SAXException, IOException, TransformerException
    {
        int result = handleImages(images, images_to_delete, pathToWrite, apartment_id);
        
        return result;
    }
    
    public static ArrayList<Apartment> cercaAppartamentoPerID (String path, final String apartment_id)
    {
        ArrayList<Apartment> list = searchApartmentForID(path, apartment_id);
        
        return list;
    }
    
    public static Document cercaAppartamento (String path,final String [] parameters) throws ParserConfigurationException
    {
        ArrayList<Apartment> list = searchApartment(path, parameters);
        
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = dbf.newDocumentBuilder();
        Document doc = builder.newDocument();
        
        Element search = doc.createElement("search_result");
        doc.appendChild(search);
            
        for (int i = 0; i<list.size();i++)
        {
            Map <String, String> map = new HashMap<String,String>();
            
            Apartment ap = list.get(i);
            
            Element apt_el = doc.createElement("result");
            search.appendChild(apt_el);
            
            Element img_el = doc.createElement("img");
            apt_el.appendChild(img_el);
            
            ArrayList<String> images = ap.getImg_url();
            if (images.size() > 0)
            {
                Text text = doc.createTextNode(images.get(0));
                img_el.appendChild(text);
            } else {
                Text text = doc.createTextNode("");
                img_el.appendChild(text);
            }

            String tipologia = "Locale";
            if (ap.getTipologia().equals("0")){
                tipologia = "Appartamento";
            }else if(ap.getTipologia().equals("1")){
                tipologia = "Villetta";
            }else if(ap.getTipologia().equals("2")){
                tipologia = "Casa Indipendente";
            }
            
            String descrizione = tipologia + " in " + ap.getIndirizzo() + 
                                    " n° " + ap.getCivico() + " a " + ap.getCitta() + " di propietà di "
                                    + ap.getProprietario() + ". ## Posti Liberi: " + ap.getPostiLiberi()
                                    + " ## Prezzo per persona: " + ap.getPrezzo() + " €";
            
            Element descrp_el = doc.createElement("description");
            apt_el.appendChild(descrp_el);

            Text text = doc.createTextNode(descrizione);
            descrp_el.appendChild(text);
            
            Element id_ap_el = doc.createElement("id_apartment");
            apt_el.appendChild(id_ap_el);

            Text text2 = doc.createTextNode(ap.getId());
            id_ap_el.appendChild(text2);
        }
        
        return doc;
    }
    
    private static ArrayList<Apartment> searchApartment (String path,final String [] parameters)
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
                
                boolean jump_element = false;
                
                Apartment aprt = null;
                
                public void startElement(String uri, String localName,String qName, 
                        Attributes attributes) throws SAXException {

                    if (qName.equals("apartment"))
                    {
                        aprt = new Apartment();
                        aprt.setProprietario(attributes.getValue("user_name"));
                        jump_element = false;
                        
                    } else if (qName.equals("id") && !jump_element)
                    {
                        ap_id = true;
                    } else if (qName.equals("indirizzo") && !jump_element)
                    {
                        ap_address = true;
                    } else if (qName.equals("prezzo_posto") && !jump_element)
                    {
                        ap_prezzo_posto = true;
                    } else if (qName.equals("posti_liberi") && !jump_element)
                    {
                        ap_posti_liberi = true;
                    } else if (qName.equals("civico") && !jump_element)
                    {
                        ap_civico = true;
                    } else if (qName.equals("citta") && !jump_element)
                    {
                        ap_citta = true;
                    } else if (qName.equals("tipo_alloggio") && !jump_element)
                    {
                        ap_tipo_alloggio = true;
                    } else if (qName.equals("image") && !jump_element)
                    {
                        ap_image = true;
                    } 
                }

               public void characters(char ch[], int start, int length) throws SAXException {
                   
                   if (ap_id) {
                       aprt.setId(new String(ch, start, length));
                       ap_id = false;
                   }
                   if (ap_address){
                       aprt.setIndirizzo(new String(ch, start, length));
                       ap_address = false;
                       
                       if (!parameters[0].equals("") && !aprt.getIndirizzo().toLowerCase().contains(parameters[0].toLowerCase()))
                       {
                           jump_element = true;
                       }
                   }
                   if (ap_prezzo_posto){
                       int prezzo_apt = Integer.parseInt(new String(ch, start, length));
                       int prezzo_search = (!parameters[1].equals("")) ? Integer.parseInt(parameters[1]) : 0;
                       aprt.setPrezzo(new String(ch, start, length));
                       ap_prezzo_posto = false;
                       if(!parameters[1].equals("") && prezzo_apt > prezzo_search)
                       {
                           jump_element = true;
                       }
                   }
                   if (ap_posti_liberi){
                       int posti_apt = Integer.parseInt(new String(ch, start, length));
                       int posti_search = (!parameters[2].equals("")) ? Integer.parseInt(parameters[2]) : 0;
                       aprt.setPostiLiberi(new String(ch, start, length));
                       ap_posti_liberi = false;
                       if (!parameters[2].equals("") && posti_apt < posti_search)
                       {
                           jump_element = true;
                       }
                   }
                   if (ap_tipo_alloggio){
                       aprt.setTipologia(new String(ch, start, length));
                       ap_tipo_alloggio = false;
                       String str = parameters[3];
                       if (!parameters[3].equals("") && !parameters[3].equals(aprt.getTipologia()))
                       {
                           jump_element = true;
                       }
                       
                   }
                   if (ap_civico){
                       aprt.setCivico(new String(ch, start, length));
                       ap_civico = false;
                   }
                   if (ap_citta){
                       aprt.setCitta(new String(ch, start, length));
                       ap_citta = false;
                   }
                   if (ap_image){
                       aprt.addImg_url(new String(ch, start, length));
                       ap_image = false;
                   }
                }
                                
                public void endElement(String uri, String localName,
                        String qName) throws SAXException {

                    //Finito elemento Apartment, se è diverso da nil lo metto in lista
                    if (qName.equals("apartment"))
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
    
    private static ArrayList<Apartment> searchApartmentForID (String path, final String apartment_id)
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

                    if (qName.equals("apartment"))
                    {
                        aprt = new Apartment();
                        aprt.setProprietario(attributes.getValue("user_name"));
                        jump_element = false;
                        
                    } else if (qName.equals("id") && !jump_element)
                    {
                        ap_id = true;
                    } else if (qName.equals("indirizzo") && !jump_element)
                    {
                        ap_address = true;
                    } else if (qName.equals("prezzo_posto") && !jump_element)
                    {
                        ap_prezzo_posto = true;
                    } else if (qName.equals("posti_liberi") && !jump_element)
                    {
                        ap_posti_liberi = true;
                    } else if (qName.equals("civico") && !jump_element)
                    {
                        ap_civico = true;
                    } else if (qName.equals("citta") && !jump_element)
                    {
                        ap_citta = true;
                    } else if (qName.equals("tipo_alloggio") && !jump_element)
                    {
                        ap_tipo_alloggio = true;
                    } else if (qName.equals("tipo_cucina") && !jump_element)
                    {
                        ap_tipo_cucina = true;
                    } else if (qName.equals("bagni") && !jump_element)
                    {
                        ap_bagni = true;
                    } else if (qName.equals("camere_letto") && !jump_element)
                    {
                        ap_cam_letto = true;
                    } else if (qName.equals("n_piano") && !jump_element)
                    {
                        ap_piano = true;
                    } else if (qName.equals("ascensore") && !jump_element)
                    {
                        ap_ascensore = true;
                    } else if (qName.equals("garage") && !jump_element)
                    {
                        ap_garage = true;
                    } else if (qName.equals("terrazzo") && !jump_element)
                    {
                        ap_terrazzo = true;
                    } else if (qName.equals("posti_totali") && !jump_element)
                    {
                        ap_posti_tot = true;
                    } else if (qName.equals("spese_acqua") && !jump_element)
                    {
                        ap_spese_acqua = true;
                    } else if (qName.equals("spese_gas") && !jump_element)
                    {
                        ap_spese_gas = true;
                    } else if (qName.equals("spese_luce") && !jump_element)
                    {
                        ap_spese_luce = true;
                    } else if (qName.equals("spese_condominiali") && !jump_element)
                    {
                        ap_spese_cond = true;
                    }
                    else if (qName.equals("nessune_spese") && !jump_element)
                    {
                        ap_nessuna_spesa = true;
                    } else if (qName.equals("image") && !jump_element)
                    {
                        ap_image = true;
                    }
                }

               public void characters(char ch[], int start, int length) throws SAXException {
                   
                   if (ap_id) {
                       aprt.setId(new String(ch, start, length));
                       if (!aprt.getId().equals(apartment_id))
                       {
                           jump_element = true;
                       }
                       ap_id = false;
                   }
                   if (ap_address){
                       aprt.setIndirizzo(new String(ch, start, length));
                       ap_address = false;
                   }
                   if (ap_prezzo_posto){
                       aprt.setPrezzo(new String(ch, start, length));
                       ap_prezzo_posto = false;
                   }
                   if (ap_posti_liberi){
                       aprt.setPostiLiberi(new String(ch, start, length));
                       ap_posti_liberi = false;
                   }
                   if (ap_tipo_alloggio){
                       aprt.setTipologia(new String(ch, start, length));
                       ap_tipo_alloggio = false;
                       
                   }
                   if (ap_civico){
                       aprt.setCivico(new String(ch, start, length));
                       ap_civico = false;
                   }
                   if (ap_citta){
                       aprt.setCitta(new String(ch, start, length));
                       ap_citta = false;
                   }
                   if (ap_tipo_cucina){
                       aprt.setTipo_cucina(new String(ch, start, length));
                       ap_tipo_cucina = false;
                   }
                   if (ap_bagni){
                       aprt.setBagni(new String(ch, start, length));
                       ap_bagni = false;
                   }
                   if (ap_cam_letto){
                       aprt.setCamere_letto(new String(ch, start, length));
                       ap_cam_letto = false;
                   }
                   if (ap_piano){
                       aprt.setPiano(new String(ch, start, length));
                       ap_piano = false;
                   }
                   if (ap_ascensore){
                       aprt.setAscensore(new String(ch, start, length));
                       ap_ascensore = false;
                   }
                   if (ap_garage){
                       aprt.setGarage(new String(ch, start, length));
                       ap_garage = false;
                   }
                   if (ap_terrazzo){
                       aprt.setTerrazzo(new String(ch, start, length));
                       ap_terrazzo = false;
                   }
                   if (ap_posti_tot){
                       aprt.setPostiTotali(new String(ch, start, length));
                       ap_posti_tot = false;
                   }
                   if (ap_spese_acqua){
                       aprt.setSpeseAcqua(new String(ch, start, length));
                       ap_spese_acqua = false;
                   }
                   if (ap_spese_gas){
                       aprt.setSpeseGas(new String(ch, start, length));
                       ap_spese_gas = false;
                   }
                   if (ap_spese_luce){
                       aprt.setSpeseLuce(new String(ch, start, length));
                       ap_spese_luce = false;
                   }
                   if (ap_spese_cond){
                       aprt.setSpeseCond(new String(ch, start, length));
                       ap_spese_cond = false;
                   }
                   if (ap_nessuna_spesa){
                       aprt.setNoSpese(new String(ch, start, length));
                       ap_nessuna_spesa = false;
                   }
                   if (ap_image){
                       aprt.addImg_url(new String(ch, start, length));
                       ap_image = false;
                   }
                }
                                
                public void endElement(String uri, String localName,
                        String qName) throws SAXException {

                    //Finito elemento Apartment, se è diverso da nil lo metto in lista
                    if (qName.equals("apartment"))
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
    
    private static int handleImages(ArrayList <String> images,ArrayList <String> images_to_delete, String pathToWrite, String apartment_id) throws ParserConfigurationException, SAXException, IOException, TransformerConfigurationException, TransformerException 
    {
        try
        {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(pathToWrite);

            //Prendo tutti gli apartment:
            NodeList apartments = document.getElementsByTagName("apartment");

            for (int i = 0; i < apartments.getLength(); i++)
            {
                Node node = apartments.item(i);
                NodeList list = node.getChildNodes();
                boolean find = false;
                for (int k = 0; k < list.getLength(); k++)
                {
                    if ("id".equals(list.item(k).getNodeName()))
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
                    if (images_to_delete.size() > 0)
                    {   
                        boolean delete_done = false;
                        
                        for (int n = 0; n < list.getLength(); n++)
                        {
                            if ("images".equals(list.item(n).getNodeName()))
                            {
                                delete_done = true;
                                NodeList all_images = list.item(n).getChildNodes();
                                for (int m = 0; m < all_images.getLength(); m++)
                                {
                                    if (images_to_delete.contains(all_images.item(m).getTextContent()))
                                    {
                                        list.item(n).removeChild(all_images.item(m));
                                    }
                                }
                            }
                            if (delete_done == true)
                            {
                                break;
                            }
                        }
                    }
                    
                    boolean exist_images = false;
                    
                    for (int n = 0; n < list.getLength(); n++)
                    {
                        if ("images".equals(list.item(n).getNodeName()))
                        {
                             exist_images = true;
                             for (int j = 0; j < images.size(); j++)
                             {
                                Element img = document.createElement("image");
                                list.item(n).appendChild(img);
                                Text text_img = document.createTextNode(images.get(j));
                                img.appendChild(text_img);
                             }
                        }
                        
                        if(exist_images == true)
                        {
                            break;
                        }
                    }
                    
                    if (exist_images == false)
                    {
                        Element imges_node = document.createElement("images");
                        node.appendChild(imges_node);

                        for (int j = 0; j < images.size(); j++)
                        {
                            Element img = document.createElement("image");
                            imges_node.appendChild(img);
                            Text text_img = document.createTextNode(images.get(j));
                            img.appendChild(text_img);
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
    
    private static String add_apartment(String pathToWrite, String user,String indirizzo,String civico,String citta,
            String tipo_alloggio,String tipo_cucina,String bagni,
            String camere_da_letto,String n_piano,String ascensore,String garage,
            String terrazzo,String posti_totali,String posti_liberi,String prezzo_posto,
            String acqua,String gas,String luce,String condominiali, String nessune_spese) throws Exception {
        
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(pathToWrite);
        
        Element root = document.getDocumentElement();
        
        //This method creates an element node
        Element root_user = document.createElement("apartment");
        root_user.setAttribute("user_name", user);
        root.appendChild(root_user);
        
        String id_apartment = String.valueOf(UUID.randomUUID());
        Element id_apartment_el = document.createElement("id");
        root_user.appendChild(id_apartment_el);

        Text text_id = document.createTextNode(id_apartment);
        id_apartment_el.appendChild(text_id);
        
        Element indirizzo_el = document.createElement("indirizzo");
        root_user.appendChild(indirizzo_el);

        Text text = document.createTextNode(indirizzo);
        indirizzo_el.appendChild(text);

        Element civico_el = document.createElement("civico");
        root_user.appendChild(civico_el);

        Text text1 = document.createTextNode(civico);
        civico_el.appendChild(text1);
        
        Element citta_el = document.createElement("citta");
        root_user.appendChild(citta_el);

        Text text2 = document.createTextNode(citta);
        citta_el.appendChild(text2);
        
        Element tipo_alloggio_el = document.createElement("tipo_alloggio");
        root_user.appendChild(tipo_alloggio_el);

        Text text3 = document.createTextNode(tipo_alloggio);
        tipo_alloggio_el.appendChild(text3);
        
        Element tipo_cucina_el = document.createElement("tipo_cucina");
        root_user.appendChild(tipo_cucina_el);

        Text text4 = document.createTextNode(tipo_cucina);
        tipo_cucina_el.appendChild(text4);
        
        Element bagni_el = document.createElement("bagni");
        root_user.appendChild(bagni_el);

        Text text5 = document.createTextNode(bagni);
        bagni_el.appendChild(text5);
        
        Element camere_lett_el = document.createElement("camere_letto");
        root_user.appendChild(camere_lett_el);

        Text text6 = document.createTextNode(camere_da_letto);
        camere_lett_el.appendChild(text6);
        
        Element n_piano_el = document.createElement("n_piano");
        root_user.appendChild(n_piano_el);

        Text text7 = document.createTextNode(n_piano);
        n_piano_el.appendChild(text7);
        
        Element ascensore_el = document.createElement("ascensore");
        root_user.appendChild(ascensore_el);

        Text text8 = document.createTextNode(ascensore);
        ascensore_el.appendChild(text8);
        
        Element garage_el = document.createElement("garage");
        root_user.appendChild(garage_el);

        Text text9 = document.createTextNode(garage);
        garage_el.appendChild(text9);
        
        Element terrazzo_el = document.createElement("terrazzo");
        root_user.appendChild(terrazzo_el);

        Text text10 = document.createTextNode(terrazzo);
        terrazzo_el.appendChild(text10);
        
        Element posti_totali_el = document.createElement("posti_totali");
        root_user.appendChild(posti_totali_el);

        Text text11 = document.createTextNode(posti_totali);
        posti_totali_el.appendChild(text11);
        
        Element posti_liberi_el = document.createElement("posti_liberi");
        root_user.appendChild(posti_liberi_el);

        Text text12 = document.createTextNode(posti_liberi);
        posti_liberi_el.appendChild(text12);
        
        Element prezzo_posto_el = document.createElement("prezzo_posto");
        root_user.appendChild(prezzo_posto_el);

        Text text13 = document.createTextNode(prezzo_posto);
        prezzo_posto_el.appendChild(text13);
        
        Element acqua_el = document.createElement("spese_acqua");
        root_user.appendChild(acqua_el);

        Text text14 = document.createTextNode(acqua);
        acqua_el.appendChild(text14);
        
        Element gas_el = document.createElement("spese_gas");
        root_user.appendChild(gas_el);

        Text text15 = document.createTextNode(gas);
        gas_el.appendChild(text15);
        
        Element luce_el = document.createElement("spese_luce");
        root_user.appendChild(luce_el);

        Text text16 = document.createTextNode(luce);
        luce_el.appendChild(text16);
        
        Element spese_cond_el = document.createElement("spese_condominiali");
        root_user.appendChild(spese_cond_el);

        Text text17 = document.createTextNode(condominiali);
        spese_cond_el.appendChild(text17);
        
        Element nessune_spese_el = document.createElement("nessune_spese");
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
    
    private static int editAnnuncio (String pathToWrite, String apartment_id, String user,String indirizzo,String civico,String citta,
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
            NodeList apartments = document.getElementsByTagName("apartment");

            for (int i = 0; i < apartments.getLength(); i++)
            {
                Node node = apartments.item(i);
                NodeList list = node.getChildNodes();
                boolean find = false;
                for (int k = 0; k < list.getLength(); k++)
                {
                    if ("id".equals(list.item(k).getNodeName()))
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
                        if (list.item(n).getNodeName().equals("indirizzo"))
                        {
                            list.item(n).setTextContent(indirizzo);
                        } else if (list.item(n).getNodeName().equals("prezzo_posto"))
                        {
                            list.item(n).setTextContent(prezzo_posto);
                        } else if (list.item(n).getNodeName().equals("posti_liberi"))
                        {
                            list.item(n).setTextContent(posti_liberi);
                        } else if (list.item(n).getNodeName().equals("civico"))
                        {
                            list.item(n).setTextContent(civico);
                        } else if (list.item(n).getNodeName().equals("citta"))
                        {
                            list.item(n).setTextContent(citta);
                        } else if (list.item(n).getNodeName().equals("tipo_alloggio"))
                        {
                            list.item(n).setTextContent(tipo_alloggio);
                        } else if (list.item(n).getNodeName().equals("tipo_cucina"))
                        {
                            list.item(n).setTextContent(tipo_cucina);
                        } else if (list.item(n).getNodeName().equals("bagni"))
                        {
                            list.item(n).setTextContent(bagni);
                        } else if (list.item(n).getNodeName().equals("camere_letto"))
                        {
                            list.item(n).setTextContent(camere_da_letto);
                        } else if (list.item(n).getNodeName().equals("n_piano"))
                        {
                            list.item(n).setTextContent(n_piano);
                        } else if (list.item(n).getNodeName().equals("ascensore"))
                        {
                            list.item(n).setTextContent(ascensore);
                        } else if (list.item(n).getNodeName().equals("garage"))
                        {
                            list.item(n).setTextContent(garage);
                        } else if (list.item(n).getNodeName().equals("terrazzo"))
                        {
                            list.item(n).setTextContent(terrazzo);
                        } else if (list.item(n).getNodeName().equals("posti_totali"))
                        {
                            list.item(n).setTextContent(posti_totali);
                        } else if (list.item(n).getNodeName().equals("spese_acqua"))
                        {
                            list.item(n).setTextContent(acqua);
                        } else if (list.item(n).getNodeName().equals("spese_gas"))
                        {
                            list.item(n).setTextContent(gas);
                        } else if (list.item(n).getNodeName().equals("spese_luce"))
                        {
                            list.item(n).setTextContent(luce);
                        } else if (list.item(n).getNodeName().equals("spese_condominiali"))
                        {
                            list.item(n).setTextContent(condominiali);
                        }
                        else if (list.item(n).getNodeName().equals("nessune_spese"))
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
    
    private static int deleteAnnuncio(String pathToWrite, String apartment_id) throws ParserConfigurationException, SAXException,
            IOException, TransformerConfigurationException, TransformerException 
    {
        try
        {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(pathToWrite);
            
            Element root = document.getDocumentElement();
            
            //Prendo tutti gli apartment:
            NodeList apartments = document.getElementsByTagName("apartment");

            for (int i = 0; i < apartments.getLength(); i++)
            {
                Node node = apartments.item(i);
                NodeList list = node.getChildNodes();
                boolean find = false;
                for (int k = 0; k < list.getLength(); k++)
                {
                    if ("id".equals(list.item(k).getNodeName()))
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
                    
                    root.removeChild(node);
                    
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
    
    private static ArrayList<Apartment> cercaAnnunciPerUtente (String path, final String user_name, final boolean admin)
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

                    if (qName.equals("apartment"))
                    {
                        aprt = new Apartment();
                        aprt.setProprietario(attributes.getValue("user_name"));
                        jump_element = false;
                        if (!admin) {
                            if (!attributes.getValue("user_name").equals(user_name))
                            {
                                jump_element = true;
                            }
                        }
                        
                        
                    } else if (qName.equals("id") && !jump_element)
                    {
                        ap_id = true;
                    } else if (qName.equals("indirizzo") && !jump_element)
                    {
                        ap_address = true;
                    } else if (qName.equals("prezzo_posto") && !jump_element)
                    {
                        ap_prezzo_posto = true;
                    } else if (qName.equals("posti_liberi") && !jump_element)
                    {
                        ap_posti_liberi = true;
                    } else if (qName.equals("civico") && !jump_element)
                    {
                        ap_civico = true;
                    } else if (qName.equals("citta") && !jump_element)
                    {
                        ap_citta = true;
                    } else if (qName.equals("tipo_alloggio") && !jump_element)
                    {
                        ap_tipo_alloggio = true;
                    } else if (qName.equals("tipo_cucina") && !jump_element)
                    {
                        ap_tipo_cucina = true;
                    } else if (qName.equals("bagni") && !jump_element)
                    {
                        ap_bagni = true;
                    } else if (qName.equals("camere_letto") && !jump_element)
                    {
                        ap_cam_letto = true;
                    } else if (qName.equals("n_piano") && !jump_element)
                    {
                        ap_piano = true;
                    } else if (qName.equals("ascensore") && !jump_element)
                    {
                        ap_ascensore = true;
                    } else if (qName.equals("garage") && !jump_element)
                    {
                        ap_garage = true;
                    } else if (qName.equals("terrazzo") && !jump_element)
                    {
                        ap_terrazzo = true;
                    } else if (qName.equals("posti_totali") && !jump_element)
                    {
                        ap_posti_tot = true;
                    } else if (qName.equals("spese_acqua") && !jump_element)
                    {
                        ap_spese_acqua = true;
                    } else if (qName.equals("spese_gas") && !jump_element)
                    {
                        ap_spese_gas = true;
                    } else if (qName.equals("spese_luce") && !jump_element)
                    {
                        ap_spese_luce = true;
                    } else if (qName.equals("spese_condominiali") && !jump_element)
                    {
                        ap_spese_cond = true;
                    }
                    else if (qName.equals("nessune_spese") && !jump_element)
                    {
                        ap_nessuna_spesa = true;
                    } else if (qName.equals("image") && !jump_element)
                    {
                        ap_image = true;
                    }
                }

               public void characters(char ch[], int start, int length) throws SAXException {
                   
                   if (ap_id) {
                       aprt.setId(new String(ch, start, length));
                       ap_id = false;
                   }
                   if (ap_address){
                       aprt.setIndirizzo(new String(ch, start, length));
                       ap_address = false;
                   }
                   if (ap_prezzo_posto){
                       aprt.setPrezzo(new String(ch, start, length));
                       ap_prezzo_posto = false;
                   }
                   if (ap_posti_liberi){
                       aprt.setPostiLiberi(new String(ch, start, length));
                       ap_posti_liberi = false;
                   }
                   if (ap_tipo_alloggio){
                       aprt.setTipologia(new String(ch, start, length));
                       ap_tipo_alloggio = false;
                       
                   }
                   if (ap_civico){
                       aprt.setCivico(new String(ch, start, length));
                       ap_civico = false;
                   }
                   if (ap_citta){
                       aprt.setCitta(new String(ch, start, length));
                       ap_citta = false;
                   }
                   if (ap_tipo_cucina){
                       aprt.setTipo_cucina(new String(ch, start, length));
                       ap_tipo_cucina = false;
                   }
                   if (ap_bagni){
                       aprt.setBagni(new String(ch, start, length));
                       ap_bagni = false;
                   }
                   if (ap_cam_letto){
                       aprt.setCamere_letto(new String(ch, start, length));
                       ap_cam_letto = false;
                   }
                   if (ap_piano){
                       aprt.setPiano(new String(ch, start, length));
                       ap_piano = false;
                   }
                   if (ap_ascensore){
                       aprt.setAscensore(new String(ch, start, length));
                       ap_ascensore = false;
                   }
                   if (ap_garage){
                       aprt.setGarage(new String(ch, start, length));
                       ap_garage = false;
                   }
                   if (ap_terrazzo){
                       aprt.setTerrazzo(new String(ch, start, length));
                       ap_terrazzo = false;
                   }
                   if (ap_posti_tot){
                       aprt.setPostiTotali(new String(ch, start, length));
                       ap_posti_tot = false;
                   }
                   if (ap_spese_acqua){
                       aprt.setSpeseAcqua(new String(ch, start, length));
                       ap_spese_acqua = false;
                   }
                   if (ap_spese_gas){
                       aprt.setSpeseGas(new String(ch, start, length));
                       ap_spese_gas = false;
                   }
                   if (ap_spese_luce){
                       aprt.setSpeseLuce(new String(ch, start, length));
                       ap_spese_luce = false;
                   }
                   if (ap_spese_cond){
                       aprt.setSpeseCond(new String(ch, start, length));
                       ap_spese_cond = false;
                   }
                   if (ap_nessuna_spesa){
                       aprt.setNoSpese(new String(ch, start, length));
                       ap_nessuna_spesa = false;
                   }
                   if (ap_image){
                       aprt.addImg_url(new String(ch, start, length));
                       ap_image = false;
                   }
                }
                                
                public void endElement(String uri, String localName,
                        String qName) throws SAXException {

                    //Finito elemento Apartment, se è diverso da nil lo metto in lista
                    if (qName.equals("apartment"))
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
