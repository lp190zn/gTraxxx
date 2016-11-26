/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sk.mlp.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;

import sk.mlp.logger.FileLogger;
import sk.mlp.util.Constants;

/**
 * Trieda SaveTrackInfo je Servlet, ktorý 
 * slúži na uchovanie zadaných detailov trasy v procese 
 * vytvorenia novej trasy pomocou vstupného tracklog 
 * súboru medzi ktorkmi 2 a 3.
 * @author Matej Pazdič
 */
public class SaveTrackInfo extends HttpServlet {
    
    private String pathToFile;
    private String trackName;
    private String trackDescr;
    private String trackActivity;
    private String access;
    private String system = System.getProperty("os.name");
    

    /**
     * Metóda processRequest je obslužná metóda, ktorá sa volá po vyvolaní daného servletu na strane používateľa. 
     * Pričom sa servlet vykonáva na strane servera.
     * @param request - objekt požiadavky, ktorý sa prenáša zo strany klienta na stranu servera
     * @param response - objekt odozvy servera, ktorý sa prenáša zo strany servera na stranu klienta
     * @throws ServletException
     * @throws IOException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
                // Process regular form fields here the same way as request.getParameter().
                // You can get parameter name by item.getFieldName();
                // You can get parameter value by item.getString();
                //System.out.println(items.size());
                trackName = request.getParameter("trkName").toString();
                
                if(request.getParameter("descr") != null){
                    trackDescr = request.getParameter("descr").toString();
                }else{
                    trackDescr = "";
                }
                
                trackActivity = request.getParameter("Activity").toString();
                access = request.getParameter("Access").toString();
               
                String filename;
                HttpSession session = request.getSession();
                
                FileLogger.getInstance().createNewLog("User " + session.getAttribute("username") + "is in servlet SaveTrackInfo, and name of track is " + session.getAttribute("trackName"));
                
                session.setAttribute("trackNameExist", "False");
                filename = session.getAttribute("trackFilename").toString();
                String foldername =filename.substring(0, filename.lastIndexOf(".gpx"));
                //session.removeAttribute("trackFilename");
                session.setAttribute("trackName", trackName);
                session.setAttribute("trackDescr", trackDescr);
                session.setAttribute("trackActivity", trackActivity);
                session.setAttribute("access", access);


                if (system.startsWith("Windows")) {
                    
                   
                   String oldPathToFile = Constants.USER_DATA_STORAGE + session.getAttribute("username") + "\\" + "Temp" + "\\";
                   pathToFile = Constants.USER_DATA_STORAGE + session.getAttribute("username") + "\\" + trackName + "\\";
                    
                    File oldFile = new File(oldPathToFile);
                    File newFile = new File(pathToFile);
                    
                    if(oldFile.exists() && newFile.exists()){
                        System.out.println("Mam Rovnaku trasu!!!");
                        FileLogger.getInstance().createNewLog("Warning: User " + session.getAttribute("username") + " attempted to create duplicate of track with track name " + foldername + " !!!");
                        session.setAttribute("trackNameExist", "True");
                        request.getRequestDispatcher("UploadTrack2.jsp").forward(request, response);
                        //response.sendRedirect("UploadTrack2.jsp");
                        return;
                    }else{
                        String pathToMultimedia = oldPathToFile + "Multimedia\\";
                        File f = new File(pathToMultimedia);
                        if(f.exists()){
                            FileUtils.forceDelete(f);
                        }
                    }


                } else {
                    String oldPathToFile = Constants.USER_DATA_STORAGE + session.getAttribute("username") + "/" + "Temp" + "/";
                    String pathToFile = Constants.USER_DATA_STORAGE + session.getAttribute("username") + "/" + trackName + "/";
                    File oldFile = new File(oldPathToFile);
                    File newFile = new File(pathToFile);
                    
                    if(oldFile.exists() && newFile.exists()){
                        System.out.println("Mam Rovnaku trasu!!!");
                        FileLogger.getInstance().createNewLog("Warning: User " + session.getAttribute("username") + " attempted to create duplicate of track with track name " + foldername + " !!!");
                        session.setAttribute("trackNameExist", "True");
                        request.getRequestDispatcher("UploadTrack2.jsp").forward(request, response);
                        //response.sendRedirect("UploadTrack2.jsp");
                        return;
                    }else{
                        String pathToMultimedia = oldPathToFile + "Multimedia/";
                        File f = new File(pathToMultimedia);
                        if (f.exists()) {
                            //f.delete();
                            FileUtils.forceDelete(f);
                        }
                    }
                }

                //System.out.println(items.get(0).getString());
        // Show result page.
        request.getRequestDispatcher("UploadTrack3.jsp").forward(request, response);
        return;
    }
        
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Metóda doGet je obslužná metóda, ktorá sa volá po vyvolaní daného servletu na strane používateľa. 
     * Pričom sa servlet vykonáva na strane servera.
     * @param request - objekt požiadavky, ktorý sa prenáša zo strany klienta na stranu servera
     * @param response - objekt odozvy servera, ktorý sa prenáša zo strany servera na stranu klienta
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Metóda doPost je obslužná metóda, ktorá sa volá po vyvolaní daného servletu na strane používateľa. 
     * Pričom sa servlet vykonáva na strane servera.
     * @param request - objekt požiadavky, ktorý sa prenáša zo strany klienta na stranu servera
     * @param response - objekt odozvy servera, ktorý sa prenáša zo strany servera na stranu klienta
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Vracia krátky popis čo vykonáva tento servlet.
     * @return Návratová hodnota je reťazec znakov s popisom daného servletu.
     */
    @Override
    public String getServletInfo() {
        return "Trieda SaveTrackInfo je Servlet, ktorý slúži na uchovanie zadaných detailov trasy v procese vytvorenia novej trasy pomocou vstupného tracklog súboru medzi ktorkmi 2 a 3.";
    }// </editor-fold>


    }

