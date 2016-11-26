/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.mlp.parser.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.*;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import sk.mlp.file.TrackPointImpl;


/**
 * Trieda určená na načítavanie nadmorských výšok z mapového servera.
 * @author Matej Pazdič
 */
public class ElevationLoader extends Thread {

    private final String baseUrlString = "https://maps.googleapis.com/maps/api/elevation/xml?locations=";
    //private final String endUrlString = "&sensor=false&key=AIzaSyAaOYSaYYTKOpbuBXpPiWEvi8KcdxqgJec";
    //private final String endUrlString = "&sensor=false&key=AIzaSyAJEy1EPr4qN9sY6ElEcQGo32BAMDaavnc";
    private final String endUrlString = "&sensor=false&key=AIzaSyCS9e5QiCD4PyIDgawyTnVCe2EXRttn7sM";

    /**
     * Konťruktor triedy ElevationLoader.
     */
    public ElevationLoader() {
    }

    /**
     * Metóda reclaimElevation slúži na samotné načítanie nadmorských výšok z mapového servera google.com. Pričom sa vytvárajú potupne požiadavky na server s 50 traťovými bodmi, kôli zrýchleniu celkového procesu načítavania.
     * @param lats - zoznam traťových bodov zapísaných v údajovej štruktúre "TrackPointImpl"
     * @return Návratová hodnota je zoznam reťazcov znakov, ktoré predstavujú jednotlivé nadmorské výšky.
     */
    public ArrayList<String> reclaimElevation(ArrayList<TrackPointImpl> lats) {
        ArrayList<String> internetElevations = new ArrayList<String>();
        
        System.out.println("Loadujem vysky");
        
            int nenacitaneBody = lats.size();
            int nacitaneBody = 0;
            for(int i = 0; i < lats.size(); i=i+50){
                try {
                    StringBuilder builder = new StringBuilder();
                    builder.append(baseUrlString);
                    builder.append(lats.get(i).getLatitude());
                    builder.append(",");
                    builder.append(lats.get(i).getLongitude());
                    if (nenacitaneBody > 50) {
                        for (int j = i + 1; j < (i + 50); j++) {
                            builder.append("|");
                            builder.append(lats.get(j).getLatitude());
                            builder.append(",");
                            builder.append(lats.get(j).getLongitude());
                        }
                    } else {
                        for (int l = i + 1; l < (i + nenacitaneBody); l++) {
                            builder.append("|");
                            builder.append(lats.get(l).getLatitude());
                            builder.append(",");
                            builder.append(lats.get(l).getLongitude());
                        }
                    }
                    builder.append(endUrlString);
                    URL queryUrl = new URL(builder.toString());
                    
                    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                    DocumentBuilder db = dbf.newDocumentBuilder();
                    Document doc = db.parse(queryUrl.openStream());
                    
                    for(int f = 0; f < doc.getElementsByTagName("elevation").getLength(); f++){
                        String stringElev = doc.getElementsByTagName("elevation").item(f).getTextContent();
                        int ddd =(int)Double.parseDouble(stringElev);
                        internetElevations.add(String.valueOf(ddd));
                    }
                    nenacitaneBody = nenacitaneBody - 50;
                    nacitaneBody = nacitaneBody + 50;
                    
                } catch (Exception ex) {
                    System.out.println("ERROR: Cannot read elevations from Google server!");
                } 
            }
            return internetElevations;
    }
}
