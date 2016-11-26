package sk.mlp.file;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.util.FileUtils;

import sk.mlp.database.DatabaseServices;
import sk.mlp.file.video.YouTubeAgent;
import sk.mlp.logger.FileLogger;
import sk.mlp.parser.GPXParser;
import sk.mlp.parser.TLVLoader;
import sk.mlp.pdf.PDFTrackGenerator;
import sk.mlp.util.Constants;

/**
 * Trieda SubmitTrack je Servlet, ktorý vykonáva 
 * vyparsovanie a záverečné vytvorenie danej trasy v systéme, pri 
 * porocese vytvorenia novej trasy pomocou vstupného tracklog súboru. 
 * Tento servlet je volaný po kroku číslo 4 pri zadaní multimediálnych 
 * súborov, alebo po kroku číslo 3 ak neboli pridané žiadné 
 * multimediálne súbory.
 * @author Matej Pazdič
 */
public class SubmitTrack extends HttpServlet {
    
    private String pathToFile;
    private String oldPathToFile;
    private String pathToMultimediaFiles;
    private String trackName;
    private String trackDescr;
    private String access;
    private String trackActivity;
    private String system = System.getProperty("os.name");

    /**
     * Metóda processRequest je obslužná metóda, ktorá sa volá po vyvolaní daného servletu na strane používateľa. 
     * Pričom sa servlet vykonáva na strane servera.
     * @param request - objekt požiadavky, ktorý sa prenáša zo strany klienta na stranu servera
     * @param response - objekt odozvy servera, ktorý sa prenáša zo strany servera na stranu klienta
     * @throws ServletException
     * @throws IOException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            
            YouTubeAgent uploader = new YouTubeAgent("skuska.api.3", "skuskaapi3");
            
            String arrayString = request.getParameter("textBox");
            String[] list = arrayString.split(",");
            ArrayList<String> filePaths = new ArrayList<String>();
            ArrayList<Integer> filePoints = new ArrayList<Integer>();
            for(int i = 0; i < list.length; i++){
                if(list[0].length() < 5){
                    break;
                }
                //System.out.println("Som dalej");
                
                String[] temp = list[i].split(";");
                //String ext = temp[0].substring(temp[0].lastIndexOf("."));
                filePaths.add(temp[0]);
                //System.out.println("File: " + temp[0].substring(temp[0].lastIndexOf("/"), temp[0].lastIndexOf("_THUMB")) + ext);
                
//                System.out.println("Cesta: " + temp[0]);
//                System.out.println("Point: " + temp[1]);
                filePoints.add(Integer.parseInt(temp[1]));
            }
            
            
            
            HttpSession session = request.getSession();
            trackName = session.getAttribute("trackName").toString();
            trackDescr = session.getAttribute("trackDescr").toString();
            trackActivity = session.getAttribute("trackActivity").toString();
            access = session.getAttribute("access").toString();
            
            FileLogger.getInstance().createNewLog("User " + session.getAttribute("username") + "is in servlet submitTrack, and name of track is " + session.getAttribute("trackName"));

            String filename = trackName + ".gpx";
            if (system.startsWith("Windows")) {
                
                pathToFile = Constants.USER_DATA_STORAGE + session.getAttribute("username") + "\\" + trackName + "\\";
                oldPathToFile = Constants.USER_DATA_STORAGE + session.getAttribute("username") + "\\" + "Temp" + "\\";
                //pathToFile = "E:\\SCHOOL\\TUKE\\DIPLOMOVKA\\PRAKTICKA CAST\\GITHUB\\GPSWebApp\\web\\Logged\\uploaded_from_server\\" + session.getAttribute("username") + "\\" + trackName + "\\";
                //oldPathToFile = "E:\\SCHOOL\\TUKE\\DIPLOMOVKA\\PRAKTICKA CAST\\GITHUB\\GPSWebApp\\web\\Logged\\uploaded_from_server\\" + session.getAttribute("username") + "\\" + "Temp" + "\\";
                
                pathToMultimediaFiles = pathToFile + "\\" + "Multimedia" + "\\";
                
                File oldFile = new File(oldPathToFile);
                File newFile = new File(pathToFile);
                oldFile.renameTo(newFile);

                String old = pathToFile + "Temp.gpx"; // Pou+y=ivaj filename premennu!
                String newS = pathToFile + trackName + ".gpx";
                File oldF = new File(old);
                File newF = new File(newS);
                oldF.renameTo(newF);
            } else {
                pathToFile = Constants.USER_DATA_STORAGE + session.getAttribute("username") + "/" + trackName + "/";
                oldPathToFile = Constants.USER_DATA_STORAGE + session.getAttribute("username") + "/" + "Temp" + "/";
                pathToMultimediaFiles = pathToFile + "Multimedia" + "/";

                File oldFile = new File(oldPathToFile);
                File newFile = new File(pathToFile);
                oldFile.renameTo(newFile);

                String old = pathToFile + "Temp.gpx"; // Pou+y=ivaj filename premennu!
                String newS = pathToFile + trackName + ".gpx";
                File oldF = new File(old);
                File newF = new File(newS);
                oldF.renameTo(newF);
            }

            GPXParser parser = new GPXParser(pathToFile, filename, session.getAttribute("username").toString(), trackName);
            parser.readGpx();
            FileLogger.getInstance().createNewLog("For user " + session.getAttribute("username") + "was successfuly created GPXParser in STEP 3 for track " + trackName + " .");
            parser.searchForMultimediaFiles(pathToMultimediaFiles);
            
            System.out.println("Mam Multi: " + parser.getFiles().size() + " " + filePoints.size());
            
            int index = parser.getFiles().size();
            for(int i = 0; i < index; i++){
                if(filePoints.isEmpty()) {
                    break;
                }
                if(filePoints.get(i) != -1){
                    if(filePaths.get(i).startsWith("YTB")){
                        parser.getFiles().get(i).setPath(filePaths.get(i));
                    }
                    parser.getFiles().get(i).setDate(parser.getTrack().get(filePoints.get(i)).getTime());
                }else{
                    if(filePaths.get(i).startsWith("YTB")){
                        uploader.deleteVideo(filePaths.get(i).substring(4));
                        System.out.println("Vymazujem " + filePaths.get(i).substring(4));
                    }
                    parser.getFiles().remove(i);
                    filePaths.remove(i);
                    filePoints.remove(i);
                    System.out.println("Mam Multim: " + parser.getFiles().size() + " " + filePoints.size());
                    index--;
                    i--;
                }
            }
            
            FileLogger.getInstance().createNewLog("For user " + session.getAttribute("username") + "was successfuly founded multimedia files in STEP 3 for track " + trackName + " .");
            parser.parseGpx(trackActivity, trackDescr);
            FileLogger.getInstance().createNewLog("For user " + session.getAttribute("username") + "was successfuly parsed GPX file in STEP 3 for track " + trackName + " .");

            DatabaseServices databaseServices = new DatabaseServices();
           
            databaseServices.createNewTrack(trackName, trackDescr, trackActivity, pathToFile,(int)databaseServices.findUserByEmail(session.getAttribute("username").toString()).getIdent(), 
                                                    (Date)parser.getStartAndEndDate().get(0), (Date)parser.getStartAndEndDate().get(1), access, parser.getStartAddress(), parser.getEndAddress(), parser.getTrackLengthKm(), parser.getTrackMinElevation(), parser.getTrackMaxElevation(), parser.getTrackHeightDiff(), parser.getTrackDuration(), "Parsed");
            
            TLVLoader loader = new TLVLoader();
            loader.readTLVFile(pathToFile, trackName);
            PDFTrackGenerator generator = new PDFTrackGenerator(loader, pathToFile, trackName);
            generator.generateTrackPDFA4(2, null, 640, 640, 1, parser.getStartAndEndDate().get(0).toString(), parser.getStartAndEndDate().get(1).toString(), trackActivity, session.getAttribute("username").toString());
            
            //ZACIATOK Vymazanie videi z disku
            String str1 = "**" + System.getProperty("file.separator") + "*.avi";
            String str2 = "**" + System.getProperty("file.separator") + "*.mov";
            String str3 = "**" + System.getProperty("file.separator") + "*.mp4";
            String str4 = "**" + System.getProperty("file.separator") + "*.3gp";
            DirectoryScanner scanner = new DirectoryScanner();
            scanner.setFollowSymlinks(false);
            scanner.setIncludes(new String[]{str1, str2, str3, str4});
            File f = new File(pathToMultimediaFiles);
            scanner.setBasedir(f);
            scanner.setCaseSensitive(false);
            scanner.scan();
            String[] tempFiles = scanner.getIncludedFiles();
            System.out.println("SKUSKA: " + scanner.getBasedir() + " >>> " + tempFiles.length);
            for (int i = 0; i < tempFiles.length; i++) {
                System.out.println("SKUSKA: " + pathToMultimediaFiles + tempFiles[i]);
                    FileUtils.delete(new File(pathToMultimediaFiles + tempFiles[i]));
                    FileLogger.getInstance().createNewLog("Successfuly deleted local video file " + tempFiles[i] + " which was uploaded!!!");
            }
            //KONIEC Vymazanie videi z disku
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
        // Show result page.
        request.getRequestDispatcher("ShowTracks.jsp").forward(request, response);
    }

// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Metóda doGet je obslužná metóda, ktorá sa volá po vyvolaní daného servletu na strane používateľa. 
     * Pričom sa servlet vykonáva na strane servera.
     * @param request - objekt požiadavky, ktorý sa prenáša zo strany klienta na stranu servera
     * @param response - objekt odozvy servera, ktorý sa prenáša zo strany servera na stranu klienta
     * @throws ServletException
     * @throws IOException
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
        return "Short description";
    }// </editor-fold>

}