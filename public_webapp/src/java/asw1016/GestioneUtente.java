/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package asw1016;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
 * @author marco
 */
public class GestioneUtente {
    
    private GestioneUtente(){
        
    }
    
    public static User getUserInfo (String path, String usr_name)
    {
        ArrayList<User> usr_list = userInfoList(path,usr_name);
        
        User usr = null;
        
        if (usr_list.size() > 0)
        {
            usr = usr_list.get(0);
            
            return usr;
        }
        
        return usr;
        
    }
    
    public static ArrayList<User> getAllUser (String path)
    {
        ArrayList<User> usr_list = userInfoList(path,"");
        
        return usr_list;
    }
    
    public static int editUser (String path, String usr, String pwd, String name, String surname, String phone, String admin) throws Exception
    {
        int result = editUserInfoWithUsrName(path, usr, pwd, name, surname, phone, admin);
        //0 - successo;
        //1 - utente non trovato con quel username
        //2 - errore
        return result;
    }
    
    public static int deleteUser (String path, String usr) throws ParserConfigurationException, SAXException, IOException, TransformerException
    {
        int result = deleteUserWithUserName(path, usr);
        //0 - successo;
        //1 - utente non trovato con quel username
        //2 - errore
        return result;
    }
    
    public static int loginUser(String path, String username, String pwd) throws Exception
    {
        int result = check_user(path,username,pwd);
        
        return result;
    }
    
    public static boolean registerUser(String pathToWrite, String name,
            String surname, String phone, String username, String pwd) throws Exception
    {
        boolean result = check_add_user(pathToWrite, name, surname, phone, username, pwd);
        
        return result;
    }
    
    private static boolean check_add_user(String pathToWrite, String name,
            String surname, String phone, String username, String pwd) throws Exception {
        
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(pathToWrite);
        
        boolean register_user = true;
        
        NodeList nList = document.getElementsByTagName("user");
        
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
        Element root_user = document.createElement("user");
        root_user.setAttribute("user_name", username);
        root.appendChild(root_user);

        Element username_child = document.createElement("username");
        root_user.appendChild(username_child);

        Text text = document.createTextNode(username);
        username_child.appendChild(text);

        Element password_child = document.createElement("password");
        root_user.appendChild(password_child);

        Text text1 = document.createTextNode(pwd);
        password_child.appendChild(text1);
        
        Element name_element = document.createElement("name");
        root_user.appendChild(name_element);

        Text text2 = document.createTextNode(name);
        name_element.appendChild(text2);
        
        Element surname_element = document.createElement("surname");
        root_user.appendChild(surname_element);

        Text text3 = document.createTextNode(surname);
        surname_element.appendChild(text3);
        
        Element phone_element = document.createElement("phone");
        root_user.appendChild(phone_element);

        Text text4 = document.createTextNode(phone);
        phone_element.appendChild(text4);
        
        Element admin_element = document.createElement("admin");
        root_user.appendChild(admin_element);

        Text text5 = document.createTextNode("0");
        admin_element.appendChild(text5);
        
        DOMSource source = new DOMSource(document);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        StreamResult result = new StreamResult(pathToWrite);
        transformer.transform(source, result);
      
        return register_user;
    }
    
    private static int check_user(String pathToWrite, String username, String pwd) throws Exception {
            
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(pathToWrite);
        
        NodeList nList = document.getElementsByTagName("user");
        
        for (int temp = 0; temp < nList.getLength(); temp++)
        {
            Node nNode = nList.item(temp);
            
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                if ((eElement.getAttribute("user_name")).equals(username))
                {
                    if (eElement.getElementsByTagName("password").item(0).getTextContent().equals(pwd))
                    {
                        if (eElement.getElementsByTagName("admin").item(0).getTextContent().equals("1"))
                        {
                            return 2;
                        }
                        return 1;
                    }
                }
            }
        }
        
            return 0;
        }
    
    private static ArrayList<User> userInfoList (String path, final String usr)
    {
        final ArrayList<User> usr_list = new ArrayList<User> ();
            
            try 
            {
                SAXParserFactory factory = SAXParserFactory.newInstance();
                SAXParser saxParser = factory.newSAXParser();

                DefaultHandler handler = new DefaultHandler() {
                    
                boolean us_pwd = false;
                boolean us_name = false;
                boolean us_surname = false;
                boolean us_phone = false;
                boolean us_admin = false;
                
                boolean jump_element = false;
                
                User usr_n = null;
                
                public void startElement(String uri, String localName,String qName, 
                        Attributes attributes) throws SAXException {

                    if (qName.equals("user"))
                    {
                        usr_n = new User();
                        usr_n.setUser_name(attributes.getValue("user_name"));
                        jump_element = false;
                        if (!usr.equals(""))
                        {
                            if (!attributes.getValue("user_name").equals(usr))
                            {
                                jump_element = true;
                            }
                        }
                        
                    } else if (qName.equals("Password") && !jump_element)
                    {
                        us_pwd = true;
                    } else if (qName.equals("name") && !jump_element)
                    {
                        us_name = true;
                    } else if (qName.equals("surname") && !jump_element)
                    {
                        us_surname = true;
                    } else if (qName.equals("phone") && !jump_element)
                    {
                        us_phone = true;
                    } else if (qName.equals("admin") && !jump_element)
                    {
                        us_admin = true;
                    }
                }

               public void characters(char ch[], int start, int length) throws SAXException {
                   
                   if (us_pwd) {
                       usr_n.setPassword(new String(ch, start, length));
                       us_pwd = false;
                   }
                   if (us_name){
                       usr_n.setName(new String(ch, start, length));
                       us_name = false;
                   }
                   if (us_surname){
                       usr_n.setSurname(new String(ch, start, length));
                       us_surname = false;
                   }
                   if (us_phone){
                       usr_n.setPhone(new String(ch, start, length));
                       us_phone = false;
                   }
                   if (us_admin){
                       usr_n.setAdmin(new String(ch, start, length));
                       us_admin = false;
                       
                   }
                }
                                
                public void endElement(String uri, String localName,
                        String qName) throws SAXException {

                    //Finito elemento Apartment, se è diverso da nil lo metto in lista
                    if (qName.equals("user"))
                    {
                       if (!jump_element)
                       {
                           //controllo questo perchè se cè anche un solo elemento che non mi va bene metto = null e ho bisogno di un'altro
                           //flag per dire se continuare a leggere fino al prossimo elemento oppure no
                           if (usr_n != null)
                           {
                               usr_list.add(usr_n);
                               usr_n = null;
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
            
              return usr_list;
        }
    
    private static int editUserInfoWithUsrName (String pathToWrite, String usr,String pwd, String name,String surname,String phone,String admin) throws Exception
    {
        try
        {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(pathToWrite);

            //Prendo tutti gli apartment:
            NodeList users = document.getElementsByTagName("user");

            for (int i = 0; i < users.getLength(); i++)
            {
                Node node = users.item(i);
                NodeList list = node.getChildNodes();
                boolean find = false;
                for (int k = 0; k < list.getLength(); k++)
                {
                    if ("username".equals(list.item(k).getNodeName()))
                    {
                        if (list.item(k).getTextContent().equals(usr))
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
                        if (list.item(n).getNodeName().equals("Password"))
                        {
                            list.item(n).setTextContent(pwd);
                        } else if (list.item(n).getNodeName().equals("name"))
                        {
                            list.item(n).setTextContent(name);
                        } else if (list.item(n).getNodeName().equals("surname"))
                        {
                            list.item(n).setTextContent(surname);
                        } else if (list.item(n).getNodeName().equals("phone"))
                        {
                            list.item(n).setTextContent(phone);
                        } else if (list.item(n).getNodeName().equals("admin"))
                        {
                            list.item(n).setTextContent(admin);
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
    
    private static int deleteUserWithUserName (String pathToWrite, String usr) throws ParserConfigurationException, SAXException,
            IOException, TransformerConfigurationException, TransformerException 
    {
        try
        {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(pathToWrite);
            
            Element root = document.getDocumentElement();
            
            //Prendo tutti gli apartment:
            NodeList users = document.getElementsByTagName("user");

            for (int i = 0; i < users.getLength(); i++)
            {
                Node node = users.item(i);
                NodeList list = node.getChildNodes();
                boolean find = false;
                for (int k = 0; k < list.getLength(); k++)
                {
                    if ("username".equals(list.item(k).getNodeName()))
                    {
                        if (list.item(k).getTextContent().equals(usr))
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
                    
                    String annunci_path = pathToWrite.replaceAll("/users.xml", "");
                    annunci_path += "/home_db.xml";
                    
                    GestioneAnnunci.eliminaAnnunciUtente(annunci_path, usr);
                    
                    return 0;
                }
            }
            
            return 1;
        
        } catch (Exception ex)
        {
            return 2;
        }
        
    }
}
