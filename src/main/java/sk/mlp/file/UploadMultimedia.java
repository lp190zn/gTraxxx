package sk.mlp.file;


import sk.mlp.file.image.ImageResizer;
import sk.mlp.logger.FileLogger;
import sk.mlp.util.Constants;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.Normalizer;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * Trieda UploadMultimedia je Servlet, ktorý obsluhuje ukladanie a 
 * úpravu odovzdávaných multimediálnych súborov pri obidvoch typoch 
 * vytvorenia novej trasy. Tento servlet je volaný pri odovzdávaní 
 * multimediálnych súboroch v kroku 3 pri vytváraní novej trasy pomocou 
 * vstupného tracklogu ako aj pri vytváraní novej trasy pomocou 
 * zakreslenia do mapy.
 * @author Matej Pazdič
 */
public class UploadMultimedia extends HttpServlet {
    

    private String system = System.getProperty("os.name");
    private String path;

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
                //trackName = items.get(0).getString();
                //System.out.println(items.get(0).getString());
            } else {
                    //System.out.println(item.getName());
                try {
                    HttpSession session = request.getSession();
                    session.setAttribute("isMultimedia", "True");
                    String trackName =  session.getAttribute("trackName").toString();
                    if(system.startsWith("Windows")){
                    
                        path = Constants.USER_DATA_STORAGE + session.getAttribute("username") + "\\" + "Temp" + "\\" + "Multimedia" + "\\";
                        //path = "E:\\SCHOOL\\TUKE\\DIPLOMOVKA\\PRAKTICKA CAST\\GITHUB\\GPSWebApp\\web\\Logged\\uploaded_from_server\\" + session.getAttribute("username") + "\\" + "Temp" + "\\" + "Multimedia" + "\\";
                    }else{
                        path = Constants.USER_DATA_STORAGE + session.getAttribute("username") + "/" + "Temp" + "/" + "Multimedia" + "/";
                    }
                    new File(path).mkdirs();
                    String tmpFileName = Normalizer.normalize(item.getName(), Normalizer.Form.NFD);
                    String fileNameTMP = tmpFileName.replaceAll("[^\\x00-\\x7F]", "");
                    String fileName = fileNameTMP.replaceAll("[,|;]", "");
                    System.out.println("SUBORIK: "  + fileName);
                    item.write(new File(path, fileName));
                    FileLogger.getInstance().createNewLog("Successfuly uploaded multimedia file " + fileName + " in user's " + session.getAttribute("username") + " track " + trackName + " .");
                    if(item.getName().toLowerCase().endsWith(".jpg") || item.getName().toLowerCase().endsWith(".jpeg")){
                        ImageResizer resizer = new ImageResizer(1024, 720);
                        //System.out.println("Resized> " + path + item.getName());
                        resizer.resizeImageWithTempThubnails(path + fileName);
                    }
                } catch (Exception ex) {
                    System.out.println("Error: Cannot save multimedia files!");
                    FileLogger.getInstance().createNewLog("ERROR: Cannot upload multimedia files for user " + request.getSession().getAttribute("username") + " !!!");
                }
            }
        }
         //Show result page.
         //request.getRequestDispatcher("HomePage.jsp").forward(request, response);
    }
}

