/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package asw1016;

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
            ArrayList <String> images_to_delete = new ArrayList<String>();
            
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
                   } else {
                       images_to_delete.add(fi.getString());
                   }
               }
            }
            
            //Se è arrivato qui vuol dire che è riuscito a caricare correttamente le foto
            //Quindi ora le aggiungo nel rispettivo annuncio
            String pathApartment_db = request.getSession().getServletContext().getRealPath("/WEB-INF/xml/");
            pathApartment_db = pathApartment_db+"/home_db.xml";
            
            if (checkDatabase(pathApartment_db))
            {
                int added = GestioneAnnunci.gestioneImmagini(images,images_to_delete, pathApartment_db, id_annuncio);
                if (added == 0)
                {
                    request.setAttribute("msg", "Operazione eseguita correttamente!");
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
            request.setAttribute("msg", ex);
            RequestDispatcher rd_forward = getServletContext().getRequestDispatcher("/jsp/error.jsp");
            rd_forward.forward(request, response);
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
