/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sk.mlp.parser.utilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;

import sk.mlp.file.TrackPointImpl;

/**
 * Trieda LocationResolver slúži na načítanie adresy podľa GPS súradníc prostredníctvom mapového servera google.com.
 * @author Matej Pazdič
 */
public class LocationResolver {
    
    private ArrayList<TrackPointImpl> track;
    String baseURL = "https://maps.googleapis.com/maps/api/geocode/xml?latlng=";
    String endURL = "&sensor=false&key=AIzaSyAaOYSaYYTKOpbuBXpPiWEvi8KcdxqgJec";
    
    /**
     * Konštruktor triedy LocationResolver.
     * @param track - samotná trasa zapísaná ako zoznam pomocou údajovej štruktúry "TrackPointImpl"
     */
    public LocationResolver(ArrayList<TrackPointImpl> track){
        this.track = track;
    }
    
    /**
     * Preťažený (čistý) konštruktor triedy LocationResolver.
     */
    public LocationResolver(){
        
    }
    
    /**
     * Metóda getAddressFromCoordinates slúži na nájdenie adresy pomocou 2 GPS úradníc.
     * @param lat - GPS zemepisná šírka
     * @param lon - GPS zemepiná dĺžka
     * @return Návratová hodnota je reťazec znakov, ktorý predstavuje adresu GPS bodu.
     */
    public String getAddressFromCoordinates(double lat, double lon){
        try {
            String stringUrl = baseURL + lat + "," + lon + endURL;
            
            URL url = new URL(stringUrl);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(url.openStream());
            
            String result = doc.getElementsByTagName("formatted_address").item(0).getTextContent();
            
            return result;
        } catch (Exception ex) {
            System.out.println("ERROR: Cannot resolve address from track!!!");
            return "";
        }
    }
    
    /**
     * Metóda getAddressFromCoordinates slúži na nájdenie adresy pomocou prvého bodu trasy.
     * @param track - samotná trasa zapísaná ako zoznam pomocou údajovej štruktúry "TrackPointImpl"
     * @return Návratová hodnota je reťazec znakov, ktorý predstavuje adresu prvého GPS bodu.
     */
    public String getAddressFromTrackPoints(ArrayList<TrackPointImpl> track){
        try {
            String stringUrl = baseURL + track.get(0).getLatitude() + "," + track.get(0).getLongitude()+ endURL;
            
            URL url = new URL(stringUrl);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(url.openStream());
            
            String result = doc.getElementsByTagName("formatted_address").item(0).getTextContent();
            
            return result;
        } catch (Exception ex) {
            System.out.println("ERROR: Cannot resolve address from track!!!");
            return "";
        }
    }
    
    /**
     * Metóda getAddressFromCoordinates slúži na nájdenie adresy prvého a posledného bodu trasy.
     * @param track - samotná trasa zapísaná ako zoznam pomocou údajovej štruktúry "TrackPointImpl"
     * @return Návratová hodnota je zoznam 2 reťazecov znakov, ktorý predstavuje adresu prvého a posledného GPS bodu trasy.
     */
    public ArrayList<String> getStartEndAddressFromTrack(ArrayList<TrackPointImpl> track){
        try {
            String stringUrl1 = baseURL + track.get(0).getLatitude() + "," + track.get(0).getLongitude() + endURL;
            String stringUrl2 = baseURL + track.get(track.size()-1).getLatitude() + "," + track.get(track.size()-1).getLongitude() + endURL;

            URL url1 = new URL(stringUrl1);
            DocumentBuilderFactory dbf1 = DocumentBuilderFactory.newInstance();
            DocumentBuilder db1 = dbf1.newDocumentBuilder();
            Document doc1 = db1.parse(url1.openStream());
            
            String result1 = doc1.getElementsByTagName("formatted_address").item(0).getTextContent();
            
            URL url2 = new URL(stringUrl2);
            DocumentBuilderFactory dbf2 = DocumentBuilderFactory.newInstance();
            DocumentBuilder db2 = dbf2.newDocumentBuilder();
            Document doc2 = db2.parse(url2.openStream());
            
            String result2 = doc2.getElementsByTagName("formatted_address").item(0).getTextContent();
            
            ArrayList<String> results = new ArrayList<String>();
            results.add(result1);
            results.add(result2);

            return results;
        } catch (Exception ex) {
            System.out.println("ERROR: Cannot resolve address from track!!!");
            ArrayList<String> results = new ArrayList<String>();
            return results;
        }
}

    
}
