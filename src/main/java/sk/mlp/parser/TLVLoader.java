/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sk.mlp.parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import sk.mlp.file.FileImpl;
import sk.mlp.file.TrackPointImpl;

/**
 * Trieda TLVLoader slúži na kompletné načítanie trasy a správu 
 * priradených multimediálnych súborov danej trasy zo výtupného 
 * tracklog súboru vo formáte TLV.
 * @author Matej Pazdič
 */
public class TLVLoader {
    
    private ArrayList<TrackPointImpl> track = new ArrayList<TrackPointImpl>();
    private ArrayList<FileImpl> multimediaFiles = new ArrayList<FileImpl>();
    private boolean isFiles[];
    private String trackType;
    private String elevationsType;
    private String trackDescr;
    private String startAddress;
    private String endAddress;
    private String length;
    private String minElevation;
    private String maxElevation;
    private String heightDiff;
    private String duration;
  
    /**
     *Konštruktor triedy TLVLoader. Všetky parametre sa nastavujú príslušnými settermi.
     */
    public TLVLoader(){
    }
    
    /**
     * Metóda readTLVFile sĺuži na kompletné načítanie výstupného tracklog súboru vo formáte TLV do údajových štruktúr a taktiež na načítanie vštekých multimediálnych súborov.
     * @param path - cesta k adresárovej štruktúre danej trasy
     * @param file - názov danej trasy
     */
    public void readTLVFile(String path, String file){
            track.clear();
            multimediaFiles.clear();

            try {
                File f = new File(path + file + ".tlv");
                DocumentBuilderFactory DBF = DocumentBuilderFactory.newInstance();
                DocumentBuilder DocB = DBF.newDocumentBuilder();
                org.w3c.dom.Document doc = DocB.parse(f);
                doc.getDocumentElement().normalize();
                NodeList list = doc.getElementsByTagName("TLV");
                Node gpxFileeee = list.item(0);
                Element elem = (Element) gpxFileeee;

                NodeList systemNodeList = elem.getElementsByTagName("SYSTEM");
                String system = systemNodeList.item(0).getTextContent();

                NodeList coordinatesNodeList = elem.getElementsByTagName("COORDINATES");
                Node coordinatesNode = coordinatesNodeList.item(0);
                Element coordinatesElement =(Element) coordinatesNode;

                NodeList trackTypeList = coordinatesElement.getElementsByTagName("Track_Type");
                Node trackTypeNode = trackTypeList.item(0);
                trackType = trackTypeNode.getTextContent();
                
                NodeList trackDescriptionList = coordinatesElement.getElementsByTagName("Track_Description");
                Node trackDescriptionNode = trackDescriptionList.item(0);
                trackDescr = trackDescriptionNode.getTextContent();
                
                
                NodeList trackStartAddressList = coordinatesElement.getElementsByTagName("Track_Start_Address");
                Node trackStartAddressNode = trackStartAddressList.item(0);
                startAddress = trackStartAddressNode.getTextContent();
                
                NodeList trackEndAddressList = coordinatesElement.getElementsByTagName("Track_End_Address");
                Node trackEndAddressNode = trackEndAddressList.item(0);
                endAddress = trackEndAddressNode.getTextContent();
                
                NodeList trackLengthList = coordinatesElement.getElementsByTagName("Track_Length_Km");
                Node trackLengthNode = trackLengthList.item(0);
                length = trackLengthNode.getTextContent();
                
                NodeList trackMinElevationList = coordinatesElement.getElementsByTagName("Track_Min_Elevation");
                Node trackMinElevationNode = trackMinElevationList.item(0);
                minElevation = trackMinElevationNode.getTextContent();
                
                NodeList trackMaxElevationList = coordinatesElement.getElementsByTagName("Track_Max_Elevation");
                Node trackMaxElevationNode = trackMaxElevationList.item(0);
                maxElevation = trackMaxElevationNode.getTextContent();
                
                NodeList trackHeightDiffList = coordinatesElement.getElementsByTagName("Track_Height_Difference");
                Node trackHeightDiffNode = trackHeightDiffList.item(0);
                heightDiff = trackHeightDiffNode.getTextContent();
                
                NodeList trackDurationList = coordinatesElement.getElementsByTagName("Track_Duration");
                Node trackDurationNode = trackDurationList.item(0);
                duration = trackDurationNode.getTextContent();

                NodeList elevationsTypeList = coordinatesElement.getElementsByTagName("Elevations_type");
                Node elevationsTypeNode = elevationsTypeList.item(0);
                elevationsType = elevationsTypeNode.getTextContent();

                NodeList trackPointNodeList = coordinatesElement.getElementsByTagName("TrackPoint");
                for(int i = 0; i < trackPointNodeList.getLength(); i++){
                    Node trackPointNode = trackPointNodeList.item(i);
                    Element trackPointElement = (Element) trackPointNode;
                    NodeList latitude = trackPointElement.getElementsByTagName("Latitude");
                    NodeList longitude = trackPointElement.getElementsByTagName("Longitude");
                    NodeList deviceElevation = trackPointElement.getElementsByTagName("Device_Elevation");
                    NodeList internetElevation = null;
                    if(elevationsType.equals("INTERNET")){
                        internetElevation = trackPointElement.getElementsByTagName("Internet_Elevation");
                    }
                    NodeList time = trackPointElement.getElementsByTagName("Time");
                    NodeList speed = trackPointElement.getElementsByTagName("Speed");

                    TrackPointImpl tempTP = new TrackPointImpl();
                    tempTP.setLatitude(Double.parseDouble(latitude.item(0).getTextContent()));
                    tempTP.setLongitude(Double.parseDouble(longitude.item(0).getTextContent()));
                    double tempD = Double.parseDouble(deviceElevation.item(0).getTextContent());
                    int tempInt = (int) tempD;
                    tempTP.setDeviceElevation(tempInt);
                    tempTP.setSpeed(speed.item(0).getTextContent());

                    if(elevationsType.equals("INTERNET")){
                        double tempIE = Double.parseDouble(internetElevation.item(0).getTextContent());
                        int tempIntIE = (int) tempIE;
                        tempTP.setInternetElevation(tempIntIE);
                    }

                    if(!time.item(0).getTextContent().equalsIgnoreCase("null")){
                        tempTP.setTime(new Date(Long.parseLong(time.item(0).getTextContent())));
                    }
                    else{
                        tempTP.setTime(new Date(Long.parseLong("0")));
                    }
                    track.add(tempTP);
                }
                
                //gpxFile = new File(gpxNode.item(0).getTextContent());
                //System.out.println(gpxFile.getAbsolutePath());

                NodeList filesNodeList = elem.getElementsByTagName("FILES");
                Node filesNode = filesNodeList.item(0);
                Element filesElement = (Element) filesNode;
                NodeList fileEntityNode = filesElement.getElementsByTagName("File_entity");
                for(int i = 0; i < fileEntityNode.getLength(); i++){
                    FileImpl tempFile = new FileImpl();
                    Node fileNode = fileEntityNode.item(i);
                    Element fileElement = (Element) fileNode;
                    NodeList pathNode = fileElement.getElementsByTagName("path");
                    tempFile.setPath(pathNode.item(0).getTextContent());
                   
                    NodeList dateNode = fileElement.getElementsByTagName("creation_date");
                    Date tempDate = new Date(Long.parseLong(dateNode.item(0).getTextContent()));
                    tempFile.setDate(tempDate);
                    NodeList fileLatitudeNode = fileElement.getElementsByTagName("gps_latitude");
                    if(!fileLatitudeNode.item(0).getTextContent().equals("null")){
                        tempFile.setLatitude(fileLatitudeNode.item(0).getTextContent());
                    }
                    NodeList fileLongitudeNode = fileElement.getElementsByTagName("gps_longitude");
                    if(!fileLongitudeNode.item(0).getTextContent().equals("null")){
                        tempFile.setLongitude(fileLongitudeNode.item(0).getTextContent());
                    }
                    NodeList fileElevationNode = fileElement.getElementsByTagName("gps_elevation");
                    if(!fileElevationNode.item(0).getTextContent().equals("null")){
                        tempFile.setElevation(fileElevationNode.item(0).getTextContent());
                    }
                    multimediaFiles.add(tempFile);
                }

                    isFiles = new boolean[track.size()];
                    for(int i = 0 ; i < track.size() ; i++){
                        isFiles[i] = false;
                    }
                    
                    
                    String firstPointDate = track.get(0).getTime().toString();
                    
                    for (int i = 0; i < multimediaFiles.size(); i++){
                        
                        
                        
                        //System.out.println("File number " + i);
                        //System.out.println("Path: " + multimediaFiles.get(i).getPath());
                        //System.out.println("Date: " + multimediaFiles.get(i).getDate());
                        //System.out.println("GPS: " + multimediaFiles.get(i).getLatitude() + " "+ multimediaFiles.get(i).getLongitude() + " " + multimediaFiles.get(i).getElevation());
                        //System.out.println();
                        
                        Date fileDate = multimediaFiles.get(i).getDate();
                        
                        //System.out.println(fileDate.toString() + "  " +  firstPointDate.toString());
                        
                        if (fileDate.toString().equalsIgnoreCase(firstPointDate)) {
                            multimediaFiles.get(i).setTrackPointIndex(0);
                                    isFiles[0] = true;
                                             
                        } else { 
                   
                        for(int j = 1; j < track.size(); j++){

                            Date prevTrackPointDate = track.get(j-1).getTime();
                            prevTrackPointDate.setSeconds(track.get(j-1).getTime().getSeconds()-1);
                            Date nextTrackPointDate = track.get(j).getTime();
                            nextTrackPointDate.setSeconds(track.get(j).getTime().getSeconds()+1);
                            //Bo nefungovalo priradzovanie.
//                            if (multimediaFiles.get(i).getLongitude() != null && multimediaFiles.get(i).getLatitude() != null) {
//                                if ((fileDate.after(prevTrackPointDate) && fileDate.before(nextTrackPointDate)) || (fileDate.equals(prevTrackPointDate) || (fileDate.equals(nextTrackPointDate)))) {
//                                    double deltaLat1 = Math.abs(Double.parseDouble(multimediaFiles.get(i).getLatitude()) - track.get(j - 1).getLatitude());
//                                    double deltaLon1 = Math.abs(Double.parseDouble(multimediaFiles.get(i).getLongitude()) - track.get(j - 1).getLongitude());
//                                    double deltaLat2 = Math.abs(Double.parseDouble(multimediaFiles.get(i).getLatitude()) - track.get(j).getLatitude());
//                                    double deltaLon2 = Math.abs(Double.parseDouble(multimediaFiles.get(i).getLongitude()) - track.get(j).getLongitude());
//                                    
//                                    //if ((deltaLat1 <= 0.0007 && deltaLon1 <= 0.0007) || (deltaLat2 <= 0.0007 && deltaLon2 <= 0.0007)) {
//                                        //System.out.println(i + ". Obrazok ma dobru GPS, k bodu " + (j - 1) + "!!!");
//                                         multimediaFiles.get(i).setTrackPointIndex(j);
//                                         isFiles[j] = true;
//                                    Date prevTrackPointDate1 = track.get(j - 1).getTime();
//                                    prevTrackPointDate1.setSeconds(track.get(j - 1).getTime().getSeconds() + 2);
//                                    Date nextTrackPointDate1 = track.get(j).getTime();
//                                    nextTrackPointDate1.setSeconds(track.get(j).getTime().getSeconds() - 2);
//                                    
//                                         
//                                         break;
//                                    //}
//                                }
//                            } else { 
                                
                                // nechat toto tu prosim // || (fileDate.equals(prevTrackPointDate)) || (fileDate.equals(nextTrackPointDate))
                                if ((fileDate.after(prevTrackPointDate) && fileDate.before(nextTrackPointDate))) {
                                    //System.out.println(i + ". " + (j - 1));
                                    multimediaFiles.get(i).setTrackPointIndex(j);
                                    isFiles[j] = true;
                                    
                                    Date prevTrackPointDate1 = track.get(j - 1).getTime();
                                    prevTrackPointDate1.setSeconds(track.get(j - 1).getTime().getSeconds() + 1);
                                    Date nextTrackPointDate1 = track.get(j).getTime();
                                    nextTrackPointDate1.setSeconds(track.get(j).getTime().getSeconds() - 1);
                                    
                                    break;
                                }else{
                                    multimediaFiles.get(i).setTrackPointIndex(track.size() - 1);
                                    //isFiles[track.size() - 1] = true;
                                    //break;
                                }
                            //}
                                Date prevTrackPointDate1 = track.get(j - 1).getTime();
                                prevTrackPointDate1.setSeconds(track.get(j - 1).getTime().getSeconds() + 1);
                                Date nextTrackPointDate1 = track.get(j).getTime();
                                nextTrackPointDate1.setSeconds(track.get(j).getTime().getSeconds() - 1);
                        }
                        }
                    }
            } catch (ParserConfigurationException ex) {
            } catch (SAXException ex) {
            } catch (IOException ex) {
            }
    }
     /**
     * @return Návratová hodnota je zoznam traťových bodov  danej trasy zapísaný v údajovej štruktúre "TrackPointImpl".
     */
    public ArrayList<TrackPointImpl> getTrackPoints() {
        return track;
    }

    /**
     * @return Návratová hodnota je zoznam priradených multimediálnych súborov zapísaných v štruktúre "FileImpl".
     */
    public ArrayList<FileImpl> getMultimediaFiles() {
        return multimediaFiles;
    }

    /**
     * @return Návratová hodnota je pole pravdivostných hodnôt v ktorom je pre každý traťový bod zapísaná informácia či sa na danom trasovom bode nachádza aspoň jeden multimedálny súbor.
     */
    public boolean[] getIsFiles() {
        return isFiles;
    }

    /**
     * @return Návratová hodnota je typ trasy.
     */
    public String getTrackType() {
        return trackType;
    }

    /**
     * @return Návratová hodnota je buď "INTERNET" ak boli nadmorské výšky načítavané z internetu, alebo "DEVICE" ak boli nadmorské výšky použité z GPS zariadenia.
     */
    public String getElevationsType() {
        return elevationsType;
    }
    
    /**
     * @return Návratová hodnota je popis trasy.
     */
    public String getTrackDescription() {
        return trackDescr;
    }

    /**
     * @return Návratová hodnota je adresa prvého bodu trasy, zapísaná ako reťazec znakov.
     */
    public String getStartAddress() {
        return startAddress;
    }

    /**
     * @return Návratová hodnota je adresa posledného bodu trasy, zapísaná ako reťazec znakov.
     */
    public String getEndAddress() {
        return endAddress;
    }

    /**
     * @return Návratová hodnota je dĺžka danej trasy.
     */
    public String getLength() {
        return length;
    }

    /**
     * @return Návratová hodnota predsatvuje minimálnu nadmorskú výšku danej trasy.
     */
    public String getMinElevation() {
        return minElevation;
    }

    /**
     * @return Návratová hodnota predsatvuje maximálnu nadmorskú výšku danej trasy.
     */
    public String getMaxElevation() {
        return maxElevation;
    }

    /**
     * @return the Návratová hodnota predsatvuje výškové prevýšenie danej trasy.
     */
    public String getHeightDiff() {
        return heightDiff;
    }

    /**
     * @return Návratová hodnota predsatvuje trvanie danej trasy.
     */
    public String getDuration() {
        return duration;
    }
}
