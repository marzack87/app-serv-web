/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package login;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Piero
 */
@WebServlet(name = "RegisterServlet", urlPatterns = {"/RegisterServlet"})
public class RegisterServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet RegisterServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RegisterServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

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
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

/*
try
                {
                    DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder docBuilder = builderFactory.newDocumentBuilder();
                    //creating a new instance of a DOM to build a DOM tree.
                    Document doc = docBuilder.newDocument();
                    new LoginServlet().createXmlTree(doc,path);
                    out.println("<b>Xml File Created Successfully</b>");
                    rd.include(request, response);
                }
                catch(Exception e)
                {
                    System.out.println(e);
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
        //Scrivo il file
        File file = new File(pathToWrite+"newxml.xml");
        BufferedWriter bw = new BufferedWriter
                      (new OutputStreamWriter(new FileOutputStream(file)));
        bw.write(xmlString);
        bw.flush();
        bw.close();
      
    }
*/
