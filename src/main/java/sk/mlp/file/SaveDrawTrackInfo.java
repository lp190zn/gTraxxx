package sk.mlp.file;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;

import sk.mlp.logger.FileLogger;
import sk.mlp.util.Constants;

/**
 * Trieda SaveDrawTrackInfo je Servlet, ktorý obsluhuje 
 * uloženie zadaných detailov trasy pri procese vytvorenia 
 * novej trasy pomocou zakreslenia na mape medzi krokmi číslo 1 a 2.
 * @author Matej Pazdič
 */
public class SaveDrawTrackInfo extends HttpServlet {
    
    private String pathToFile;
    private String newPathToFile;
    private String trackName;
    private String trackDescr;
    private String trackActivity;
    private String access;
    private String system = System.getProperty("os.name");

    /**
     * Metóda doPost je obslužná metóda, ktorá sa volá po vyvolaní daného servletu na strane používateľa. 
     * Pričom sa servlet vykonáva na strane servera.
     * @param request - objekt požiadavky, ktorý sa prenáša zo strany klienta na stranu servera
     * @param response - objekt odozvy servera, ktorý sa prenáša zo strany servera na stranu klienta
     * @throws ServletException
     * @throws IOException
     */
    @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
    List<FileItem> items = null;
        try {
            items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
        } catch (FileUploadException e) {
            throw new ServletException("Cannot parse multipart request.", e);
        }
        for (FileItem item : items) {
            if (item.isFormField()) {
                // Process regular form fields here the same way as request.getParameter().
                // You can get parameter name by item.getFieldName();
                // You can get parameter value by item.getString();
                //System.out.println(items.size());
                trackName = items.get(0).getString();

                trackDescr = items.get(1).getString();
                trackActivity = items.get(2).getString();
                access = items.get(3).getString();
               
                HttpSession session = request.getSession();
                session.setAttribute("trackNameExist", "False");
                //filename = session.getAttribute("trackFilename").toString();
                //String foldername =filename.substring(0, filename.lastIndexOf(".gpx"));
                //session.removeAttribute("trackFilename");
                session.setAttribute("trackName", trackName);
                session.setAttribute("trackDescr", trackDescr);
                session.setAttribute("trackActivity", trackActivity);
                session.setAttribute("access", access);

                if (system.startsWith("Windows")) {
                    
                    pathToFile = Constants.USER_DATA_STORAGE + session.getAttribute("username") + "\\" + "Temp" + "\\";
                    newPathToFile = Constants.USER_DATA_STORAGE + session.getAttribute("username") + "\\" + trackName + "\\";
                    String pathToMultimedia = pathToFile + "Multimedia\\";

                    //pathToFile = "E:\\SCHOOL\\TUKE\\DIPLOMOVKA\\PRAKTICKA CAST\\GITHUB\\GPSWebApp\\web\\Logged\\uploaded_from_server\\" + session.getAttribute("username") + "\\" + "Temp" + "\\";
                    //newPathToFile = "E:\\SCHOOL\\TUKE\\DIPLOMOVKA\\PRAKTICKA CAST\\GITHUB\\GPSWebApp\\web\\Logged\\uploaded_from_server\\" + session.getAttribute("username") + "\\" + trackName + "\\";
                    //String pathToMultimedia = pathToFile + "Multimedia\\";
                    
                    File newFile = new File(pathToFile);
                    
                    if (session.getAttribute("isMultimedia") != null) {
                        if (new File(pathToFile + "Temp.txt").exists()) {
                            System.out.println("Warning: Mam tempFile a vymazujem!!!");
                            FileLogger.getInstance().createNewLog("Warning: Found old temp file in SaveDrawTrackInfo which belongs to " + session.getAttribute("username") + " !!! Successfuly delete the old temp.");
                            File tempFile = new File(pathToFile + "Temp.txt");
                            //tempFile.delete();
                            FileUtils.forceDelete(tempFile);
                        }
                    } else if (newFile.exists()) {
                        System.out.println("Warning: Mam temp a vymazujem!!!");
                        FileLogger.getInstance().createNewLog("Warning: Found old temp folder in SaveDrawTrackInfo which belongs to " + session.getAttribute("username") + " !!! Successfuly delete the old temp.");
                        //newFile.delete();
                        FileUtils.forceDelete(newFile);
                    }

                    if (new File(newPathToFile).exists()) {
                        System.out.println("Mam Rovnaku trasu!!!");
                        FileLogger.getInstance().createNewLog("Warning: User " + session.getAttribute("username") + " attempted to create duplicate of track with track name " + trackName + " !!!");
                        session.setAttribute("trackNameExist", "True");
                        request.getRequestDispatcher("WriteTrack1.jsp").forward(request, response);
                        //response.sendRedirect("UploadTrack2.jsp");
                        return;
                    } else {
                        session.removeAttribute("trackNameExist");
//                        oldFile.renameTo(newFile);
//
//                        String old = pathToFile + "Temp.gpx"; // Pou+y=ivaj filename premennu!
//                        String newS = pathToFile + trackName + ".gpx";
//                        File oldF = new File(old);
//                        File newF = new File(newS);
//                        oldF.renameTo(newF);
                    }

                } else {
                    pathToFile = Constants.USER_DATA_STORAGE + session.getAttribute("username") + "/" + "Temp" + "/";
                    newPathToFile = Constants.USER_DATA_STORAGE + session.getAttribute("username") + "/" + trackName + "/";
                    String pathToMultimedia = pathToFile + "Multimedia/";
                    File newFile = new File(pathToFile);
                    
                    if(session.getAttribute("isMultimedia") != null){
                        if(new File(pathToFile + "Temp.txt").exists()){
                            System.out.println("Warning: Mam tempFile a vymazujem!!!");
                            FileLogger.getInstance().createNewLog("Warning: Found old temp file in SaveDrawTrackInfo which belongs to " + session.getAttribute("username") + " !!! Successfuly delete the old temp.");
                            File tempFile = new File(pathToFile + "Temp.txt");
                            //tempFile.delete();
                            FileUtils.forceDelete(tempFile);
                        } else if(newFile.exists()){
                            System.out.println("Warning: Mam temp a vymazujem!!!");
                            FileLogger.getInstance().createNewLog("Warning: Found old temp folder in SaveDrawTrackInfo which belongs to " + session.getAttribute("username") + " !!! Successfuly delete the old temp.");
                            //newFile.delete();
                            FileUtils.forceDelete(newFile);
                        }
                    }

                    if (new File(newPathToFile).exists()) {
                        System.out.println("Mam Rovnaku trasu!!!");
                        FileLogger.getInstance().createNewLog("Warning: User " + session.getAttribute("username") + " attempted to create duplicate of track with track name " + trackName + " !!!");
                        session.setAttribute("trackNameExist", "True");
                        request.getRequestDispatcher("WriteTrack1.jsp").forward(request, response);
                        //response.sendRedirect("UploadTrack2.jsp");
                        return;
                    } else {
                        session.removeAttribute("trackNameExist");
//
//                        oldFile.renameTo(newFile);
//
//                        String old = pathToFile + "Temp.gpx"; // Pou+y=ivaj filename premennu!
//                        String newS = pathToFile + trackName + ".gpx";
//                        File oldF = new File(old);
//                        File newF = new File(newS);
//                        oldF.renameTo(newF);
//                        //
                    }
                }
                
            } else {
            }
        }
        // Show result page.
        request.getRequestDispatcher("UploadMultimediaDrawTrack.jsp").forward(request, response);
        return;
    }
}

