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
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;


/**
 *
 * @author marco
 */
@MultipartConfig(location="/tmp",
fileSizeThreshold=32,
maxFileSize=1024*1024*10,
maxRequestSize=1024*1024*10)
public class GestioneFotoServlet extends HttpServlet {

    private boolean isMultipart;
    
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
       
        String content_type = request.getContentType();
        
        if (request.getContentType() != null && 
            request.getContentType().toLowerCase().indexOf("multipart/form-data") > -1 ) {
                isMultipart = true;
        } else {
                isMultipart = false;
        } 
        response.setContentType("text/html");
        if( !isMultipart ){
            request.setAttribute("msg", "Errore nel caricamento delle foto");
            RequestDispatcher rd_forward = getServletContext().getRequestDispatcher("/jsp/error.jsp");
            rd_forward.forward(request, response);
        }
        
        String path = request.getSession().getServletContext().getRealPath("/multimedia/photos");
        
        try{ 
            Iterator i = request.getParts().iterator();

            int index = 0;
            
            String id_annuncio = request.getParameter("id_annuncio");
            String[] foto_da_cancellare = request.getParameterValues("foto_da_cancellare");
            String now = "";
            
            ArrayList <String> images = new ArrayList<String>();
            
            ArrayList <String> images_to_delete;
            if (foto_da_cancellare != null){
                 images_to_delete= new ArrayList<String>(Arrays.asList(foto_da_cancellare));
            } else {
                 images_to_delete = new ArrayList<String>();
            }
            
            while ( i.hasNext () ) 
            {
               Part p = (Part) i.next();
               
               String cont_type = p.getContentType();
               if (cont_type != null && cont_type.contains("image")) {
                   
                   java.util.Date date= new java.util.Date();
                   now = new Timestamp(date.getTime()).toString();
                   now = now.replace(" ", "_");
                   
                   String fileExt = cont_type.replaceAll("image/", "");
                   
                   String fileName =  id_annuncio + "_" + index + "_" + now + "." + fileExt;
                   
                   File file = new File(path + "/" + fileName);
                   
                   if (!file.exists()) {
                        file.createNewFile();
                   }
                   
                   InputStream filecontent = null;
                   OutputStream outputStream = new FileOutputStream(file);
                   
                   filecontent = p.getInputStream();
                   
                   int read = 0;
                   byte[] bytes = new byte[1024];

                   while ((read = filecontent.read(bytes)) != -1) {
                        outputStream.write(bytes, 0, read);
                   }
                   
                   outputStream.close();
                   
                   images.add(fileName);
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
            
            
         } catch(Exception ex) {
            request.setAttribute("msg", ex.getMessage());
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
