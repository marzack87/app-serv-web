/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package login;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;  
/**
 *
 * @author marco
 */
public class LoginServlet extends HttpServlet {
    
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
        
        String path = request.getSession().getServletContext().getRealPath("/");

        String user = request.getParameter("user");
        String pwd = request.getParameter("pwd");
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.jsp");
        PrintWriter out= response.getWriter();
        out.println("<div align=center><font color=red >Username or password is wrong.</font></div>");
        rd.include(request, response);
        
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
	try
	{
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = builderFactory.newDocumentBuilder();
            //creating a new instance of a DOM to build a DOM tree.
            
            Document doc = docBuilder.newDocument();
            new LoginServlet().createXmlTree(doc,path);
            pw.println("<b>Xml File Created Successfully</b>");
	}
	catch(Exception e)
	{
            System.out.println(e);
	}
        
    }
    
    private void createXmlTree(Document doc, String pathToWrite) throws Exception {
        
        //This method creates an element node
        Element root = doc.createElement("Company");
        //adding a node after the last child node of the specified node.
        doc.appendChild(root);

        Element child = doc.createElement("Location");
        root.appendChild(child);

        Element child1 = doc.createElement("Companyname");
        child.appendChild(child1);

        Text text = doc.createTextNode("Roseindia .Net");
        child1.appendChild(text);

        Comment comment = doc.createComment("Employee in roseindia");
        child.appendChild(comment);

        Element element = doc.createElement("Employee");
        child.appendChild(element);

        Text text1 = doc.createTextNode("Vineett Bansal");
        element.appendChild(text1);

        Element chilE = doc.createElement("Id");
        chilE.setAttribute("name", "Vineet");
        root.appendChild(chilE);

        Text text12 = doc.createTextNode("status");
        chilE.appendChild(text12);
       
        //TransformerFactory instance is used to create Transformer objects. 
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer();
       
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        // create string from xml tree
        StringWriter sw = new StringWriter();
        StreamResult result = new StreamResult(sw);
        DOMSource source = new DOMSource(doc);
        transformer.transform(source, result);
        String xmlString = sw.toString();
        
        File file = new File(pathToWrite+"newxml.xml");
        BufferedWriter bw = new BufferedWriter
                      (new OutputStreamWriter(new FileOutputStream(file)));
        bw.write(xmlString);
        bw.flush();
        bw.close();
      
    }
}
