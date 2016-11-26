/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sk.mlp.file;

import sk.mlp.database.DatabaseServices;
import sk.mlp.logger.FileLogger;
import sk.mlp.parser.GPXParser;
import sk.mlp.parser.TLVLoader;
import sk.mlp.parser.utilities.MultimediaSearcher;
import sk.mlp.pdf.PDFTrackGenerator;
import sk.mlp.util.Constants;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.FileUtils;

/**
 * Trieda WriteNewTrack je Servlet, ktorý slúži na výber nakreslených 
 * traťových bodov z mapy a ich naśledný zapis do Temp súboru pre 
 * neskoršie spracovávanie. Tento servlet je volaný po medzi 2. a 3. 
 * krokom v procese vytvorenia novej trasy pomocou zakreslenia 
 * trasy do mapy.
 * @author Matej Pazdič
 */
public class WriteNewTrack extends HttpServlet {
    
    String system = System.getProperty("os.name");
    String pathToFile = null;
    String pathToMultimedia = null;
    

    /**
     * Metóda processRequest je obslužná metóda, ktorá sa volá po vyvolaní daného servletu na strane používateľa. 
     * Pričom sa servlet vykonáva na strane servera.
     * @param request - objekt požiadavky, ktorý sa prenáša zo strany klienta na stranu servera
     * @param response - objekt odozvy servera, ktorý sa prenáša zo strany servera na stranu klienta
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        String trackName = session.getAttribute("trackName").toString();
        String trackDescr = session.getAttribute("trackDescr").toString();
        String trackActivity = session.getAttribute("trackActivity").toString();
        String access = session.getAttribute("access").toString();

        session.setAttribute("trackNameExist", "False");
//        if (system.startsWith("Windows")) {
//        
//            FileReader namereader = new FileReader("C:\\path.pth");
//            BufferedReader in = new BufferedReader(namereader);
//                    
//            String pathToUpl = in.readLine();
//            
//            String tempPath = pathToUpl + session.getAttribute("username") + "\\Temp" + "\\";
//            //String tempPath = "E:\\SCHOOL\\TUKE\\DIPLOMOVKA\\PRAKTICKA CAST\\GITHUB\\GPSWebApp\\web\\Logged\\uploaded_from_server\\" + session.getAttribute("username") + "\\Temp" + "\\";
//            File tempFile = new File(tempPath);
//            if (tempFile.exists()) {
//                System.out.println("Mam temp a vymazujem!");
//                FileUtils.deleteDirectory(tempFile);
//                //tempFile.delete();
//                FileLogger.getInstance().createNewLog("Warning: Found old temp folder which belongs to " + session.getAttribute("username") + " !!! Successfuly delete the old temp.");
//            }
//        } else {
//            String tempPath = "/usr/local/tomcat/webapps/ROOT/Logged/uploaded_from_server/" + session.getAttribute("username") + "/Temp" + "/";
//            File tempFile = new File(tempPath);
//            if (tempFile.exists()) {
//                System.out.println("Mam temp a vymazujem!");
//                FileUtils.deleteDirectory(tempFile);
//                FileLogger.getInstance().createNewLog("Warning: Found old temp folder which belongs to " + session.getAttribute("username") + " !!! Successfuly delete the old temp.");
//            }
//        }
        
        if (system.startsWith("Windows")) {
            
           pathToFile = Constants.USER_DATA_STORAGE + session.getAttribute("username") + "\\Temp" + "\\";
           pathToMultimedia = Constants.USER_DATA_STORAGE + session.getAttribute("username") + "\\Temp" + "\\Multimedia\\";
           //pathToFile = "E:\\SCHOOL\\TUKE\\DIPLOMOVKA\\PRAKTICKA CAST\\GITHUB\\GPSWebApp\\web\\Logged\\uploaded_from_server\\" + session.getAttribute("username") + "\\Temp" + "\\";
           //pathToMultimedia = "E:\\SCHOOL\\TUKE\\DIPLOMOVKA\\PRAKTICKA CAST\\GITHUB\\GPSWebApp\\web\\Logged\\uploaded_from_server\\" + session.getAttribute("username") + "\\Temp" + "\\Multimedia\\";
        } else {
            pathToFile = Constants.USER_DATA_STORAGE + session.getAttribute("username") + "/Temp" + "/";
            pathToMultimedia = Constants.USER_DATA_STORAGE + session.getAttribute("username") + "/Temp" + "/Multimedia/";
        }
        
        File tempF = new File(pathToFile + "Temp.txt");
        if(tempF.exists()){
            FileUtils.forceDelete(tempF);
            FileLogger.getInstance().createNewLog("Warning: Found tamp file Temp.txt in WriteNewTrack which belongs to user " + session.getAttribute("username") + " while writing new track " + trackName + " !!!");
        }
        
        File multF = new File(pathToMultimedia);
        MultimediaSearcher searcher = new MultimediaSearcher();
        searcher.setSearchFolder(pathToMultimedia);
        String[] files = searcher.startSearchWithoutTrack();
        if(files.length >= 1){
            session.setAttribute("isMultimedia", "True");
        }
        
        new File(pathToFile).mkdirs();
        File file = new File(pathToFile, "Temp.txt"); // Write to destination file. Pouyivaj filename!
        boolean create = file.createNewFile();
        //System.out.println("Vytvoril: " + create);
        
        if(file.exists()){
            String latLngStr = request.getParameter("textBox");
            
            FileWriter writer = new FileWriter(file, true);
            BufferedWriter buf = new BufferedWriter(writer);
            
            writer.append(latLngStr);
            
            buf.close();
            writer.close();
        }
        if(session.getAttribute("isMultimedia") != null){
            request.getRequestDispatcher("SynchronizeDrawTrack.jsp").forward(request, response);
        }else{
            try {
                
                String pathToTemp;
                String pathToTempFile;
                String pathToMultimediaFiles;
                
                String filename = trackName + ".gpx";
                if (system.startsWith("Windows")) {
                    
                    pathToFile = Constants.USER_DATA_STORAGE + session.getAttribute("username") + "\\" + trackName + "\\";
                    pathToTemp = Constants.USER_DATA_STORAGE + session.getAttribute("username") + "\\" + "Temp" + "\\";
                    pathToTempFile = pathToFile + "Temp.txt";
                    //pathToFile = "E:\\SCHOOL\\TUKE\\DIPLOMOVKA\\PRAKTICKA CAST\\GITHUB\\GPSWebApp\\web\\Logged\\uploaded_from_server\\" + session.getAttribute("username") + "\\" + trackName + "\\";
                    //pathToTemp = "E:\\SCHOOL\\TUKE\\DIPLOMOVKA\\PRAKTICKA CAST\\GITHUB\\GPSWebApp\\web\\Logged\\uploaded_from_server\\" + session.getAttribute("username") + "\\" + "Temp" + "\\";
                    //pathToTempFile = pathToFile + "Temp.txt";
                    pathToMultimediaFiles = pathToFile + "Multimedia" + "\\";
//                    File fTemp = new File(pathToMultimediaFiles);
//                    if(!fTemp.exists()){
//                        fTemp.mkdirs();
//                    }
                } else {
                    pathToFile = Constants.USER_DATA_STORAGE + session.getAttribute("username") + "/" + trackName + "/";
                    pathToTemp = Constants.USER_DATA_STORAGE + session.getAttribute("username") + "/" + "Temp" + "/";
                    pathToTempFile = pathToFile + "Temp.txt";
                    pathToMultimediaFiles = pathToFile + "Multimedia" + "/";
//                    File fTemp = new File(pathToMultimediaFiles);
//                    if(!fTemp.exists()){
//                        fTemp.mkdirs();
//                    }
                }
                
                File oldFile = new File(pathToTemp);
                File newFile = new File(pathToFile);
                oldFile.renameTo(newFile);
                
                GPXParser parser = new GPXParser(pathToFile, filename, session.getAttribute("username").toString(), trackName);
                parser.readFromTrackPoints(pathToTempFile, trackName, trackDescr);
                FileLogger.getInstance().createNewLog("For user " + session.getAttribute("username") + "was successfuly created GPXParser in STEP 3 for track " + trackName + " .");
                parser.searchForMultimediaFilesWithoutCorrection(pathToMultimediaFiles);
                
                FileLogger.getInstance().createNewLog("For user " + session.getAttribute("username") + "was successfuly founded multimedia files in STEP 3 for track " + trackName + " .");
                parser.parseFromTrackPoints(trackActivity, trackDescr);
                FileLogger.getInstance().createNewLog("For user " + session.getAttribute("username") + "was successfuly parsed GPX file in STEP 3 for track " + trackName + " .");
                parser.createGPXFile(trackDescr);
                FileLogger.getInstance().createNewLog("For user " + session.getAttribute("username") + "was successfuly generated GPX file in STEP 3 for track " + trackName + " .");
                
                DatabaseServices databaseServices = new DatabaseServices();
                
                databaseServices.createNewTrack(trackName, trackDescr, trackActivity, pathToFile, (int)databaseServices.findUserByEmail(session.getAttribute("username").toString()).getIdent(),
                        (Date)parser.getStartAndEndDate().get(0), (Date)parser.getStartAndEndDate().get(1), access, parser.getStartAddress(), parser.getEndAddress(), parser.getTrackLengthKm(), parser.getTrackMinElevation(), parser.getTrackMaxElevation(), parser.getTrackHeightDiff(), parser.getTrackDuration(), "Drawed");
                
                TLVLoader loader = new TLVLoader();
                loader.readTLVFile(pathToFile, trackName);
                PDFTrackGenerator generator = new PDFTrackGenerator(loader, pathToFile, trackName);
                generator.generateTrackPDFA4(2, null, 640, 640, 1, parser.getStartAndEndDate().get(0).toString(), parser.getStartAndEndDate().get(1).toString(), trackActivity, session.getAttribute("username").toString());

                FileLogger.getInstance().createNewLog("For user " + session.getAttribute("username") + "was successfuly created new track in STEP 3 for track " + trackName + " .");
                
                if(loader.getTrackPoints().get(0).getInternetElevation() == 0 && loader.getTrackPoints().get(loader.getTrackPoints().size() - 1).getInternetElevation() == 0){
                    session.setAttribute("Limit", "True");
                }
                
            } catch (Exception ex) {
                System.out.println("Error: Unable to create .tlv file!");
            FileLogger.getInstance().createNewLog("ERROR: Unable to create user's " + request.getSession().getAttribute("username") + " track " + trackName + " in STEP 3 !!!");
            //vloyit oznacenie chyby parsera!!!
            ex.printStackTrace();
            }
            request.getRequestDispatcher("ShowTracks.jsp").forward(request, response);
        }
        
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
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
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
        return "Short description";
    }// </editor-fold>

}
