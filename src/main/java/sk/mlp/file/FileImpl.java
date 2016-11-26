/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sk.mlp.file;

import java.util.Date;

/**
 * Trieda FileImpl je dátová štruktúra multimediálneho súboru, ktorá 
 * obsahuje prídavné informácie o súbore a používa sa na vytvaranie zoznamu 
 * relevantných multimediálnych súborov, ktoré patria k trase.
 * @author Matej Pazdič
 */
public class FileImpl {
    private String path;
    private Date date;
    private String latitude;
    private String longitude;
    private String elevation;
    private int trackPointIndex;

    /**
     *  Základný konštruktor triedy FileImpl. Atribúty sa dajú zmeniť príslušnými settermi.
     */
    public FileImpl() {
        this.path = null;
        this.date = null;
        this.latitude = null;
        this.longitude = null;
        this.elevation = null;
        this.trackPointIndex = -1;
    }

    /**
     * Preťažený konštruktor triedy FileImpl. Ostané atribúty sa dajú zmeniť príslušnými settermi.
     * @param path - cesta k danému multimediálnemu súboru
     * @param date - dátum a čas vytvorenia daného multimediálneho súboru
     */
    public FileImpl(String path, Date date) {
        this.path = path;
        this.date = date;
        this.latitude = null;
        this.longitude = null;
        this.elevation = null;
        this.trackPointIndex = -1;
    }

    /**
     * Preťažený konštruktor triedy FileImpl. Ostané atribúty sa dajú zmeniť príslušnými settermi.
     * @param path - cesta k danému multimediálnemu súboru
     * @param date - dátum a čas vytvorenia daného multimediálneho súboru
     * @param latitude - GPS zemepisná šírka vytvorenia daného multimediálneho súboru
     * @param longitude - GPS zemepisná dĺžka vytvorenia daného multimediálneho súboru
     */
    public FileImpl(String path, Date date, String latitude, String longitude) {
        this.path = path;
        this.date = date;
        this.latitude = latitude;
        this.longitude = longitude;
        this.elevation = null;
        this.trackPointIndex = -1;
    }

    /**
     * Preťažený konštruktor triedy FileImpl. Ostané atribúty sa dajú zmeniť príslušnými settermi.
     * @param path - cesta k danému multimediálnemu súboru
     * @param date - dátum a čas vytvorenia daného multimediálneho súboru
     * @param latitude - GPS zemepisná šírka vytvorenia daného multimediálneho súboru
     * @param longitude - GPS zemepisná dĺžka vytvorenia daného multimediálneho súboru
     * @param elevation - GPS nadmorská výška vytvorenia daného multimediálneho súboru
     */
    public FileImpl(String path, Date date, String latitude, String longitude, String elevation) {
        this.path = path;
        this.date = date;
        this.latitude = latitude;
        this.longitude = longitude;
        this.elevation = elevation;
        this.trackPointIndex = -1;
    }

    /**
     * @return Návratová hodnota je cesta k danému multimedialnému súboru
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path - Cesta k multimediálnemu súboru
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * @return Návratová hodnota je dátum a čas vytvorenia daného multimediálneho súboru.
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date - dátum a čas vytvorenia daného multimediálneho súboru
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @return Navratová hodnota je reťazec znakov predstavujúci GPS zemepisnú šírku vytvorenia daného multimediálneho súboru.
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * @param latitude - zemepisná šírka vytvorenia daného multiemdiálneho súboru
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**
     * @return Návratová hodnota je reťazec znakov predstavujúci GPS zemepisnú dĺžku vytvorenia daného multimediálneho súboru.
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * @param longitude - GPS zemepisná dĺžka vytvorenia multimediálneho súboru
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    /**
     * @return Návratová hodnota je reťazec znakov predstavujúci nadmorskú výšku (v metroch) vytvorenia daného multimediálneho súboru.
     */
    public String getElevation() {
        return elevation;
    }

    /**
     * @param elevation - nadmorská výška vytvorenia multimediálneho súboru v metroch.
     */
    public void setElevation(String elevation) {
        this.elevation = elevation;
    }

    /**
     * @return Návratová hodnota prestavuje poradové číslo bodu trasy, ku ktorému je daný multimediálny súbor priradený.
     */
    public int getTrackPointIndex() {
        return trackPointIndex;
    }

    /**
     * @param trackPointIndex - poradové číslo bodu trasy, ku ktorému bude daný súboru priradený
     */
    public void setTrackPointIndex(int trackPointIndex) {
        this.trackPointIndex = trackPointIndex;
    }
}
