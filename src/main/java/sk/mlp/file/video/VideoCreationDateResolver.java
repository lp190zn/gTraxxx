/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sk.mlp.file.video;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import sk.mlp.parser.utilities.TimezoneLoader;

/**
 * Trieda VideoCreationDateResolver slúži na získavanie dátumu a času 
 * vytvorenia multimediálnych video súborov z priložených meta-údajov. 
 * Pre svoj beh potrebuje v systéme nainštalovaný nástroj s názvom 
 * "ffprobe", ktorý je dostupný na www adrese www.ffmpeg.org.
 * @author Matej Pazdič
 */
public class VideoCreationDateResolver {

    /**
     * Konštruktor triedy VIdeoCreationDateResolver.
     */
    public VideoCreationDateResolver(){
        
    }
    
    /**
     * Metóda resolveCreationDate slúži na samotné získanie dátumu a času vytvorenia daného video multimediálneho súboru.
     * @param videoFilePath - cesta k danému video súboru
     * @param lat - GPS zemepisná šírka daného súboru
     * @param lon - GPS zemepisná dĺžka daného súboru
     * @return Návratová hodnota je dátum a čas vytvorenia daného video multimediálneho súboru.
     */
    public Date resolveCreationDate(String videoFilePath, double lat, double lon){  
         try {

            ProcessBuilder builder = new ProcessBuilder("ffprobe", videoFilePath);
 
            builder.redirectErrorStream(true);
  
            Process process = builder.start();

            InputStream is = process.getInputStream();
  
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            
            String line = null;
            while ((line = reader.readLine()) != null) {
                if(line.trim().startsWith("creation")){
                    String stringDate = line.trim().substring(line.trim().indexOf(":") + 2);
 
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = format.parse(stringDate);  
                    
                    if (date.getYear() < 100 && (!System.getProperty("os.name").startsWith("Windows"))){

                        date.setYear(date.getYear()+ 66);
                    }
                             
                    TimezoneLoader timezoneLoader = new TimezoneLoader();
                    Date newDate = timezoneLoader.correctTimeZone(date, lat, lon);

                    return newDate;
                }
            }
        } catch (Exception ex) {
            return new Date();
        }
         return new Date();
    }
}
