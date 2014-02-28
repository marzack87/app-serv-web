/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gestione_annuncio;

import java.io.*;
import java.sql.Timestamp;
import java.util.*;
import javax.servlet.RequestDispatcher;

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

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.output.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

/**
 *
 * @author marco
 */
public class AggiuntaFotoServlet extends HttpServlet {

    private boolean isMultipart;
    private String filePath;
    private int maxFileSize = 10 * 1024 * 1024;
    private int maxMemSize = 4 * 1024;
    private File file ;
    
    
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
        
        isMultipart = ServletFileUpload.isMultipartContent(request);
        response.setContentType("text/html");
        if( !isMultipart ){
            request.setAttribute("msg", "Errore nel caricamento delle foto");
            RequestDispatcher rd_forward = getServletContext().getRequestDispatcher("/jsp/error.jsp");
            rd_forward.forward(request, response);
        }
        
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // maximum size that will be stored in memory
        factory.setSizeThreshold(maxMemSize);
        // Location to save data that is larger than maxMemSize.

        String path = request.getSession().getServletContext().getRealPath("/multimedia/photos");

        factory.setRepository(new File(path));

        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);
        // maximum file size to be uploaded.
        upload.setSizeMax( maxFileSize );

        try{ 
            // Parse the request to get file items.
            List fileItems = upload.parseRequest(request);

            // Process the uploaded file items
            Iterator i = fileItems.iterator();

            filePath = path;
            int index = 0;
            String id_annuncio = "";
            String now = "";
            
            ArrayList <String> images = new ArrayList<String>();
            
            while ( i.hasNext () ) 
            {
               FileItem fi = (FileItem)i.next();
               if ( !fi.isFormField () )	
               {
                  index++; 
                  
                  java.util.Date date= new java.util.Date();
                  now = new Timestamp(date.getTime()).toString();
                  now = now.replace(" ", "_"); 
                 // Get the uploaded file parameters
                  String fieldName = fi.getFieldName();
                  String fileExt = FilenameUtils.getExtension(fi.getName());
                  String fileName =  id_annuncio + "_" + index + "_" + now + "." + fileExt;
                  String contentType = fi.getContentType();
                  boolean isInMemory = fi.isInMemory();
                  long sizeInBytes = fi.getSize();
                  // Write the file
                  if( fileName.lastIndexOf("\\") >= 0 ){
                     file = new File( filePath + "/" +
                     fileName.substring( fileName.lastIndexOf("\\"))) ;
                  }else{
                     file = new File( filePath + "/" +
                     fileName.substring(fileName.lastIndexOf("\\")+1)) ;
                  }
                  fi.write( file ) ;
                  
                  images.add(fileName);
                  
               } else {
                   if (fi.getFieldName().equals("id_annuncio")){
                      id_annuncio = fi.getString();
                   }
               }
            }
            
            //Se è arrivato qui vuol dire che è riuscito a caricare correttamente le foto
            //Quindi ora le aggiungo nel rispettivo annuncio
            String pathApartment_db = request.getSession().getServletContext().getRealPath("/WEB-INF/xml/");
            pathApartment_db = pathApartment_db+"/home_db.xml";
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/jsp/user_nuovo_foto.jsp");
            PrintWriter out_response = response.getWriter();
            
            if (checkDatabase(pathApartment_db))
            {
                int added = addImages(images, pathApartment_db, id_annuncio);
                if (added == 0)
                {
                    request.setAttribute("msg", "Foto inserite correttamente!");
                    RequestDispatcher rd_forward = getServletContext().getRequestDispatcher("/jsp/user_messaggio.jsp");
                    rd_forward.forward(request, response);
                    
                } else if (added == 1)
                {
                    request.setAttribute("msg", "nessun annuncio trovato con l'ID indicato");
                    RequestDispatcher rd_forward = getServletContext().getRequestDispatcher("/jsp/error.jsp");
                    rd_forward.forward(request, response);
                } else if (added == 2)
                {
                    request.setAttribute("msg", "c'è stato un problema nella lettura del DataBase");
                    RequestDispatcher rd_forward = getServletContext().getRequestDispatcher("/jsp/error.jsp");
                    rd_forward.forward(request, response);
                }
            } else {
                request.setAttribute("msg", "c'è stato un problema nella lettura del DataBase");
                RequestDispatcher rd_forward = getServletContext().getRequestDispatcher("/jsp/error.jsp");
                rd_forward.forward(request, response);
            }
            
            
         }catch(Exception ex) {
            request.setAttribute("msg", ex.getMessage());
            RequestDispatcher rd_forward = getServletContext().getRequestDispatcher("/jsp/error.jsp");
            rd_forward.forward(request, response);
         }
        
    }
    
    private int addImages(ArrayList <String> images, String pathToWrite, String apartment_id) throws ParserConfigurationException, SAXException, IOException, TransformerConfigurationException, TransformerException 
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
                    Element imges_node = document.createElement("Images");
                    node.appendChild(imges_node);

                    for (int j = 0; j < images.size(); j++)
                    {
                        Element img = document.createElement("Image");
                        imges_node.appendChild(img);
                        Text text_img = document.createTextNode(images.get(j));
                        img.appendChild(text_img);
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
