/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sk.mlp.parser.utilities;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;

import sk.mlp.file.TrackPointImpl;

/**
 * Trieda TimezoneLoader slúži na vykonávanie tzv. automatickej 
 * časovej korekcie dátumu a času jednotlivých traťových bodov. 
 * Samotná korekcia sa načítava z mapového servera google.com.
 * @author Matej Pazdič
 */
public class TimezoneLoader {
    
    ArrayList<TrackPointImpl> track;
    String baseURL = "https://maps.googleapis.com/maps/api/timezone/xml?location=";
    String endURL = "&sensor=false&key=AIzaSyAaOYSaYYTKOpbuBXpPiWEvi8KcdxqgJec";
    
    /**
     * Konštruktor triedy TimezoneLoader.
     * @param track - trasa zapísaná prostredníctvom tridy "TimezoneLoader"
     */
    public TimezoneLoader(ArrayList<TrackPointImpl> track){
        this.track = track;
    }
    
    /**
     * Preťažený (prázdny) konštruktor triedy TimezoneLoader.
     */
    public TimezoneLoader(){
        
    }
    
    /**
     * Metóda correctTimeZone slúži na samotnú automatickú časovú korekciu trasy.
     * @return Návratová hodnota je zoznam traťových bodov zapísaných pomocou údajovej štruktúry "TrackPointImpl"
     */
    public ArrayList<TrackPointImpl> correctTimeZone(){
        try {
            String tempStr = String.valueOf(track.get(0).getTime().getTime()).substring(0, String.valueOf(track.get(0).getTime().getTime()).length() - 3);

            String UrlString = baseURL + track.get(0).getLatitude() + "," + track.get(0).getLongitude() + "&timestamp=" + tempStr + endURL;

            URL url = new URL(UrlString);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(url.openStream());

            String dstOffsetStr = doc.getElementsByTagName("dst_offset").item(0).getTextContent();
            String gmtOffsetStr = doc.getElementsByTagName("raw_offset").item(0).getTextContent();

            double dstOffset = Double.parseDouble(dstOffsetStr) / 60 / 60;
            double gmtOffset = Double.parseDouble(gmtOffsetStr) / 60 / 60;

            int offset = (int) (dstOffset + gmtOffset);

            for (int i = 0; i < track.size(); i++) {
                //System.out.println("OLD> " + track.get(i).getTime());
                track.get(i).getTime().setHours(track.get(i).getTime().getHours() + offset);
                //System.out.println("new> " + track.get(i).getTime());
            }

        } catch (Exception ex) {
            Logger.getLogger(TimezoneLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return track;
    }
    
    /**
     * Metóda correctTimeZone slúži na časovú korekciu jeného traťového bodu.
     * @param date - dátum a čas vytvorenia daného traťového bodu
     * @param lat - GPS zemepisná šírka
     * @param lon - GPS zemepisná dĺžka
     * @return Návratová hodnota je dátum a čas po vykonaní časovej korekcie.
     */
    public Date correctTimeZone(Date date, double lat, double lon){
        try {
       
            String tempStr = String.valueOf(date.getTime()).substring(0, String.valueOf(date.getTime()).length() - 3);
            String UrlString = baseURL + lat + "," + lon + "&timestamp=" + tempStr + endURL;
            
            URL url = new URL(UrlString);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(url.openStream());

            String dstOffsetStr = doc.getElementsByTagName("dst_offset").item(0).getTextContent();
            String gmtOffsetStr = doc.getElementsByTagName("raw_offset").item(0).getTextContent();

            double dstOffset = Double.parseDouble(dstOffsetStr) / 60 / 60;
            double gmtOffset = Double.parseDouble(gmtOffsetStr) / 60 / 60;

            int offset = (int) (dstOffset + gmtOffset);
            
            Date newDate = date;
            newDate.setHours(date.getHours() + offset);
            
            return newDate;

        } catch (Exception ex) {
            Logger.getLogger(TimezoneLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
