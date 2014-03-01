/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utility;

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
    
    public ArrayList<Apartment> listAnnunci (String path, final String user_name, final boolean admin)
    {
        ArrayList<Apartment> list = cercaAnnunciPerUtente(path,user_name,admin);
        
        return list;
    }
    
    public int eliminaAnnuncio (String pathToWrite, String apartment_id) throws ParserConfigurationException, SAXException, IOException, TransformerException
    {
        int result = deleteAnnuncio(pathToWrite,apartment_id);
        
        return result;
    }
    
    public int modificaAnnuncio (String pathToWrite, String apartment_id, String user,String indirizzo,String civico,String citta,
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
    
    public String aggiungiAnnuncio (String pathToWrite, String user,String indirizzo,String civico,String citta,
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
    
    public int gestioneImmagini (ArrayList <String> images,ArrayList <String> images_to_delete, String pathToWrite, String apartment_id) throws ParserConfigurationException, SAXException, IOException, TransformerException
    {
        int result = handleImages(images, images_to_delete, pathToWrite, apartment_id);
        
        return result;
    }
    
    public ArrayList<Apartment> cercaAppartamentoPerID (String path, final String apartment_id)
    {
        ArrayList<Apartment> list = searchApartmentForID(path, apartment_id);
        
        return list;
    }
    
    public ArrayList<Map> cercaAppartamento (String path,final String [] parameters)
    {
        ArrayList<Apartment> list = searchApartment(path, parameters);
        
        ArrayList<Map> array_map = new ArrayList<Map>();
        
        for (int i = 0; i<list.size();i++)
        {
            Map <String, String> map = new HashMap<String,String>();
            
            if (list.get(i).img_url.size() > 0)
            {
                map.put("img",list.get(i).img_url.get(0));
            } else {
                map.put("img","");
            }
            
            String tipologia = "Locale";
            if (list.get(i).tipologia == "0"){
                tipologia = "Appartamento";
            }else if(list.get(i).tipologia == "1"){
                tipologia = "Villetta";
            }else if(list.get(i).tipologia == "2"){
                tipologia = "Casa Indipendente";
            }
            
            String descrizione = "<html>" + tipologia+ " in " + list.get(i).address + 
                                                      " n° " + list.get(i).civico + " a " + list.get(i).citta + " di propietà di "
                                                      + list.get(i).user_owner + ". <br> Posti Liberi: " + list.get(i).posti_liberi 
                                                      + " <br> Prezzo per persona: " + list.get(i).prezzo + " €";
            map.put("description",descrizione);
            map.put("id_apartment",list.get(i).id_apartment);
            
            array_map.add(map);
        }
        
        return array_map;
    }
    
    private ArrayList<Apartment> searchApartment (String path,final String [] parameters)
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
                       String str = parameters[3];
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
    
    private ArrayList<Apartment> searchApartmentForID (String path, final String apartment_id)
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
                       if (!aprt.id_apartment.equals(apartment_id))
                       {
                           jump_element = true;
                       }
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
              System.out.println("erroe: "+e);
            }
            
              return list;
        }
    
    private int handleImages(ArrayList <String> images,ArrayList <String> images_to_delete, String pathToWrite, String apartment_id) throws ParserConfigurationException, SAXException, IOException, TransformerConfigurationException, TransformerException 
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
                    if (images_to_delete.size() > 0)
                    {   
                        boolean delete_done = false;
                        
                        for (int n = 0; n < list.getLength(); n++)
                        {
                            if ("Images".equals(list.item(n).getNodeName()))
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
                        if ("Images".equals(list.item(n).getNodeName()))
                        {
                             exist_images = true;
                             for (int j = 0; j < images.size(); j++)
                             {
                                Element img = document.createElement("Image");
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
                        Element imges_node = document.createElement("Images");
                        node.appendChild(imges_node);

                        for (int j = 0; j < images.size(); j++)
                        {
                            Element img = document.createElement("Image");
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
    
    private int deleteAnnuncio(String pathToWrite, String apartment_id) throws ParserConfigurationException, SAXException,
            IOException, TransformerConfigurationException, TransformerException 
    {
        try
        {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(pathToWrite);
            
            Element root = document.getDocumentElement();
            
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
    
    private ArrayList<Apartment> cercaAnnunciPerUtente (String path, final String user_name, final boolean admin)
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
                        if (!admin) {
                            if (!attributes.getValue("user_name").equals(user_name))
                            {
                                jump_element = true;
                            }
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
              System.out.println("erroe: "+e);
            }
            
              return list;
        }
}
