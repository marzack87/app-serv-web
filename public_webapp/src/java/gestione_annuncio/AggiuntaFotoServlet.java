/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gestione_annuncio;

import java.io.*;
import java.sql.Timestamp;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.output.*;

/**
 *
 * @author marco
 */
public class AggiuntaFotoServlet extends HttpServlet {

    private boolean isMultipart;
    private String filePath;
    private int maxFileSize = 1024 * 1024;
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
        java.io.PrintWriter out = response.getWriter( );
        if( !isMultipart ){
           out.println("<html>");
           out.println("<head>");
           out.println("<title>Servlet upload</title>");  
           out.println("</head>");
           out.println("<body>");
           out.println("<p>No file uploaded</p>"); 
           out.println("</body>");
           out.println("</html>");
           return;
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

            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet upload</title>");  
            out.println("</head>");
            out.println("<body>");
            
            filePath = path;
            int index = 0;
            String id_annuncio = "";
            String now = "";
            
            while ( i.hasNext () ) 
            {
               FileItem fi = (FileItem)i.next();
               if ( !fi.isFormField () )	
               {
                  index++; 
                  
                  java.util.Date date= new java.util.Date();
                  now = new Timestamp(date.getTime()).toString();
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
                  out.println("Uploaded Filename: " + fileName + "<br>");
               } else {
                   if (fi.getFieldName().equals("id_annuncio")){
                      id_annuncio = fi.getString();
                  }
               }
            }
            out.println("</body>");
            out.println("</html>");
         }catch(Exception ex) {
             out.println("<html>");
             out.println("<head>");
             out.println("<title>Servlet upload</title>");  
             out.println("</head>");
             out.println("<body>");
             out.println("<p>" + ex + "</p>"); 
             out.println("</body>");
             out.println("</html>");
         }
        
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
