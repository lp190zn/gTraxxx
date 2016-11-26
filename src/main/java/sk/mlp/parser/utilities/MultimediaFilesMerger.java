/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sk.mlp.parser.utilities;

import sk.mlp.file.FileImpl;
import sk.mlp.file.TrackPointImpl;
import sk.mlp.parser.GPXParser;

import java.util.ArrayList;
import java.util.Date;

/**
 * Trieda MultimediaFilesMerger slúži na priradenie multimediálnych 
 * súborov k jednotlivým bodom trasy. Je to pomocná trieda pre potreby 
 * manuálnej synchronizácie trasy s multimediálnymi súbormi.
 * @author Matej Pazdič
 */
public class MultimediaFilesMerger {
    
    private GPXParser parser = null;
    private boolean isFiles[];
    private ArrayList<TrackPointImpl> track = null;
    private ArrayList<FileImpl> multimediaFiles = null;
    
    /**
     * Konštruktor triedy MultimediaFilesMerger.
     * @param parser - objekt triedy GPXParser v ktorom sa nachádzajú větky potrebné informácie o načítanej trase
     */
    public MultimediaFilesMerger(GPXParser parser){
        this.parser = parser;
        this.track = parser.getTrack();
        this.multimediaFiles = parser.getFiles();
    }
    
    /**
     * Metóda locateMultimediaFilesWithTrack slúži na samostatné priradenie multimediálnych súborov k trase, a taktiež na vytvorenie poľa isFiles, ktoré hovorí o tom v ktorom bode sa budú prezentovať nejaké multimediálne súbory.
     */
    public void locateMultimediaFilesWithTrack(){
        isFiles = new boolean[getTrackPoints().size()];
                    for(int i = 0 ; i < getTrackPoints().size() ; i++){
                        isFiles[i] = false;
                    }
                    for (int i = 0; i < getMultimediaFiles().size(); i++){
                        //System.out.println("File number " + i);
                        //System.out.println("Path: " + multimediaFiles.get(i).getPath());
                        //System.out.println("Date: " + multimediaFiles.get(i).getDate());
                        //System.out.println("GPS: " + multimediaFiles.get(i).getLatitude() + " "+ multimediaFiles.get(i).getLongitude() + " " + multimediaFiles.get(i).getElevation());
                        //System.out.println();
                        Date fileDate = getMultimediaFiles().get(i).getDate();
                        for(int j = 1; j < getTrackPoints().size(); j++){
                            Date prevTrackPointDate = getTrackPoints().get(j-1).getTime();
                            prevTrackPointDate.setSeconds(getTrackPoints().get(j-1).getTime().getSeconds()-1);
                            Date nextTrackPointDate = getTrackPoints().get(j).getTime();
                            nextTrackPointDate.setSeconds(getTrackPoints().get(j).getTime().getSeconds()+1);
                            if (getMultimediaFiles().get(i).getLongitude() != null && getMultimediaFiles().get(i).getLatitude() != null) {
                                if ((fileDate.after(prevTrackPointDate) && fileDate.before(nextTrackPointDate)) || (fileDate.equals(prevTrackPointDate) || (fileDate.equals(nextTrackPointDate)))) {
                                    double deltaLat1 = Math.abs(Double.parseDouble(getMultimediaFiles().get(i).getLatitude()) - getTrackPoints().get(j - 1).getLatitude());
                                    double deltaLon1 = Math.abs(Double.parseDouble(getMultimediaFiles().get(i).getLongitude()) - getTrackPoints().get(j - 1).getLongitude());
                                    double deltaLat2 = Math.abs(Double.parseDouble(getMultimediaFiles().get(i).getLatitude()) - getTrackPoints().get(j).getLatitude());
                                    double deltaLon2 = Math.abs(Double.parseDouble(getMultimediaFiles().get(i).getLongitude()) - getTrackPoints().get(j).getLongitude());
                                    
                                    if ((deltaLat1 <= 0.0007 && deltaLon1 <= 0.0007) || (deltaLat2 <= 0.0007 && deltaLon2 <= 0.0007)) {
                                        //System.out.println(i + ". Obrazok ma dobru GPS, k bodu " + (j - 1) + "!!!");
                                         getMultimediaFiles().get(i).setTrackPointIndex(j - 1);
                                         isFiles[j - 1] = true;
                                         break;
                                    }
                                }
                            } else {
                                // nechat toto tu prosim // || (fileDate.equals(prevTrackPointDate)) || (fileDate.equals(nextTrackPointDate))
                                if ((fileDate.after(prevTrackPointDate) && fileDate.before(nextTrackPointDate))) {
                                    //System.out.println(i + ". " + (j - 1));
                                    getMultimediaFiles().get(i).setTrackPointIndex(j - 1);
                                    isFiles[j - 1] = true;
                                    //System.out.println("Som v ife!!!");
                                    break;
                                }else{
                                    getMultimediaFiles().get(i).setTrackPointIndex(-1);
                                    //System.out.println("Som v else!!!");
                                    //break;
                                }
                            }
                             Date prevTrackPointDate1 = getTrackPoints().get(j - 1).getTime();
                            prevTrackPointDate1.setSeconds(getTrackPoints().get(j - 1).getTime().getSeconds() + 1);
                            Date nextTrackPointDate1 = getTrackPoints().get(j).getTime();
                            nextTrackPointDate1.setSeconds(getTrackPoints().get(j).getTime().getSeconds() - 1);
                        }
                    }
    }

    /**
     * @return Návratová hodnota je pole pravdivostných hodnôt, ktoré udáva na ktorom bode sa budú prezentovať multimediálne súbory.
     */
    public boolean[] getIsFiles() {
        return isFiles;
    }

    /**
     * @return Návratová hodnota je zoznam traťových bodov zapísaných pomocou údajovej šttruktúry "TrackPointImpl".
     */
    public ArrayList<TrackPointImpl> getTrackPoints() {
        return track;
    }

    /**
     * @return  Návratová hodnota je zoznam priradených multimediálnych súborov zapísaných pomocou údajovej šttruktúry "FilesImpl".
     */
    public ArrayList<FileImpl> getMultimediaFiles() {
        return multimediaFiles;
    }
    
}
