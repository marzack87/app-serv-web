/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gestione_annuncio;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
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
import org.xml.sax.SAXException;

/**
 *
 * @author Piero
 */
public class EliminaAnnuncioServlet extends HttpServlet {
    
    public void doGet(
		HttpServletRequest request,
		HttpServletResponse response)
		throws ServletException, IOException {
		try {
			response.setContentType("text/html");
       
                        String path = request.getSession().getServletContext().getRealPath("/WEB-INF/xml/");
                        path = path+"/home_db.xml";
                        
                        int annunciodeleted = deleteAnnuncio(path,request.getParameter("id_apartment"));
                        
                        if (annunciodeleted == 0)
                        {
                            //Eliminato corretamente
                        } else if (annunciodeleted == 1) {
                            //Nessun annucio con quell'ID trovato
                        } else if (annunciodeleted == 2) {
                            //Errore nell'eliminazione
                        }
		} catch (Exception e) {
			e.printStackTrace();
                        System.out.println("erroe: "+e);

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


}