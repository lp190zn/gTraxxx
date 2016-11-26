/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sk.mlp.file.video;

import sk.mlp.file.FileImpl;
import sk.mlp.logger.FileLogger;

import com.google.gdata.client.youtube.YouTubeService;
import com.google.gdata.data.media.MediaFileSource;
import com.google.gdata.data.media.mediarss.MediaCategory;
import com.google.gdata.data.media.mediarss.MediaDescription;
import com.google.gdata.data.media.mediarss.MediaKeywords;
import com.google.gdata.data.media.mediarss.MediaTitle;
import com.google.gdata.data.youtube.VideoEntry;
import com.google.gdata.data.youtube.YouTubeMediaGroup;
import com.google.gdata.data.youtube.YouTubeNamespace;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.XmlBlob;
import java.io.File;
import java.net.URL;

/**
 * Trieda YouTubeAgent slúži na odovzdávanie, mazanie a správu video 
 * multimediálnych súborov vo webovej službe YouTube 
 * na www adrese www.youtube.com. 
 * @author Matej Pazdič
 */
public class YouTubeAgent {
    
    private YouTubeService service;
    
    /**
     * Konštruktor triedyy YouTubeAgent.
     * @param userName - prihlasovacie meno do webovej služby YouTube
     * @param Password - heslo do webovej služby YouTube
     */
    public YouTubeAgent(String userName, String Password){
        try {
            service = new YouTubeService("YouTubeAgent for GPSWebApp", "AI39si4T3ipzbmmskeC0gh9sl094LAw1aJZFf3OtA82N2tyfpnAyUxBxkHoQYu1WgCyuqFSPlrWokd8I7-B4wtTimBU2dIHLHA");
            service.setUserCredentials(userName, Password);
        } catch (AuthenticationException ex) {
            System.out.println("ERROR: Cannot connect to YouTube Service!!! Bad Login or Pass!!!");
        }
    }
    
    /**
     * Metóda uploadVideo slúži na odovzdanie multimediálneho video a súboru na server YouTube. Taktiež priradzuje k videu aj  detaily o videosúbore a detaily o serveri s kadiaľ je odovzdávaný.
     * @param file - video multimediálny súbor, zapísaný v štruktúre FileImpl
     * @param user - používateľ (majiteľ), ktorý daný video multimediálnych súbor odovzdáva z potálu
     * @param name - názov vytváranej trasy ku ktorej daný súbor patrí
     * @param ID - poradove číslo multimediálneho súboru v danej trase
     * @return Navratová hodnota je ID daného odovzdaneho multimedialneho súboru, pomocou ktoreho sa ten da vyvolať na serveri YouTube
     * @throws YouTubeAgentException je vyhodená ppri problemoch s odovzdaním video multimediálneho súboru
     */
    public String uploadVideo (FileImpl file, String user, String name, String ID) throws YouTubeAgentException{
        try {
            VideoEntry newEntry = new VideoEntry();
            
           YouTubeMediaGroup mg = newEntry.getOrCreateMediaGroup();
                mg.setTitle(new MediaTitle());
                mg.getTitle().setPlainTextContent(user + "=" + name + "=" + ID);
                mg.addCategory(new MediaCategory(YouTubeNamespace.CATEGORY_SCHEME, "Autos"));
                mg.setKeywords(new MediaKeywords());
                mg.getKeywords().addKeyword("GPSWebApp");
                mg.setDescription(new MediaDescription());
                mg.getDescription().setPlainTextContent("This video has been uploaded from GPSWebApp server, and it is property of GPSWebApp server.");
                //mg.setPrivate(true);
                //mg.addCategory(new MediaCategory(YouTubeNamespace.DEVELOPER_TAG_SCHEME, "mydevtag"));
                //mg.addCategory(new MediaCategory(YouTubeNamespace.DEVELOPER_TAG_SCHEME, "anotherdevtag"));
            
            MediaFileSource ms = new MediaFileSource(new File(file.getPath()), "video/quicktime");
            newEntry.setMediaSource(ms);
            String uploadUrl = "http://uploads.gdata.youtube.com/feeds/api/users/default/uploads";
      //      
            XmlBlob xmlBlob = new XmlBlob(); 
            xmlBlob.setBlob("<yt:accessControl action='list' permission='denied'/>"); 
            newEntry.setXmlBlob(xmlBlob); 
      //      
            VideoEntry createdEntry = service.insert(new URL(uploadUrl), newEntry);
            System.out.println("Video has been uploaded to YouTube: " + createdEntry.getMediaGroup().getPlayer().getUrl());
            FileLogger.getInstance().createNewLog("Successfully uploaded video to YouTube with URL " + createdEntry.getMediaGroup().getPlayer().getUrl() + " .");
            
            
            return createdEntry.getMediaGroup().getVideoId();
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("ERROR: Cannot upload video to YouTube server!!!");
            FileLogger.getInstance().createNewLog("ERROR: Cannot upload video to YouTube with ID !!!");
            throw new YouTubeAgentException();
        }
    }
    
    /**
     * Metóda deleteVideo slúži na vymazanie odovzdaného video multimediálneho súboru zo servera YouTube.
     * @param videoID - ID daného video multimediálneho súboru odovzdaného na portály YOuTube
     * @throws YouTubeAgentException je vyhodená pri problémoch s vymazaním videa zo serveru YouTube
     */
    public void deleteVideo(String videoID) throws YouTubeAgentException{
        try {
            String videoEntryUrl = "http://gdata.youtube.com/feeds/api/users/default/uploads/" + videoID;
            VideoEntry videoEntry = service.getEntry(new URL(videoEntryUrl), VideoEntry.class);
            videoEntry.delete();
            FileLogger.getInstance().createNewLog("Successfully deleted video from youtube with ID " + videoID + " .");
        } catch (Exception ex) {
            System.out.println("ERROR: Cannot delete video from YouTube server!!!");
            FileLogger.getInstance().createNewLog("ERROR: Cannot delete video from YouTube with ID " + videoID + " !!!");
            //throw new YouTubeAgentException();
        }
    }
    
}
