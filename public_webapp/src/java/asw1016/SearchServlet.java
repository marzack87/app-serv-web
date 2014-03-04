/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package asw1016;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Piero
 */
public class SearchServlet extends HttpServlet {
    
    /**
	 * Get a String-object from the applet and send it back.
     * @param request
     * @param response
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
	 */
        @Override
	public void doPost(
		HttpServletRequest request,
		HttpServletResponse response)
		throws ServletException, IOException {
		try {
                    InputStream is = request.getInputStream();
                    HttpSession session = request.getSession();
                    response.setContentType("text/xml;charset=UTF-8");
                    OutputStream os = response.getOutputStream();
                    ManageXML mngXML = new ManageXML();
                    Document data = mngXML.parse(is);
                    is.close();
                    
                    String path = request.getSession().getServletContext().getRealPath("/WEB-INF/xml/");
                    path = path+"/home_db.xml";
                    
                    Document answer= operations(data,session,mngXML,path);
                    mngXML.transform(os, answer);
                    os.close();
			/*InputStream in = request.getInputStream();
			ObjectInputStream inputFromApplet = new ObjectInputStream(in);
                        
                        
			String[] parameter = (String[]) inputFromApplet.readObject();
                        
                        ArrayList<Map> apartments = GestioneAnnunci.cercaAppartamento(path,parameter);
			// echo it to the applet
			OutputStream outstr = response.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(outstr);
			oos.writeObject(apartments);
			oos.flush();
			oos.close();*/

		} catch (Exception e) {
			e.printStackTrace();
                        System.out.println(e);
		}
	}
        
        private Document operations(Document data, HttpSession session, ManageXML mngXML, String path) throws ParserConfigurationException {
    
            //name of operation is message root
            Element root = data.getDocumentElement();
            String operation = root.getTagName();
            Document answer= null;
            
            if (operation.equals("search"))
            {
                answer = GestioneAnnunci.cercaAppartamento(path,handleResponse(data));
            }
            return answer;
        }
        
        private String[] handleResponse (Document doc)
        {
            NodeList apartments = doc.getElementsByTagName("search");
            
            String parameter[] = new String[4];
            
            for (int i = 0; i < apartments.getLength(); i++)
            {
                Node node = apartments.item(i);
                NodeList results = node.getChildNodes();
                
                Map <String, String> map = new HashMap<String,String>();
                
                for (int k = 0; k < results.getLength(); k++)
                {
                    if ("indirizzo".equals(results.item(k).getNodeName()))
                    {
                        parameter[0] = results.item(k).getTextContent();
                        
                    } else if ("prezzo".equals(results.item(k).getNodeName()))
                    {
                        parameter[1] = results.item(k).getTextContent();
                    } else if ("posti_liberi".equals(results.item(k).getNodeName()))
                    {
                        parameter[2] = results.item(k).getTextContent();
                    } else if ("tipologia".equals(results.item(k).getNodeName()))
                    {
                        parameter[3] = results.item(k).getTextContent();
                    }
                }
            }
            
            return parameter;
        }
}
