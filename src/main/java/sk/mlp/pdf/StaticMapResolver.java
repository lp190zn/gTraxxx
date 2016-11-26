/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.mlp.pdf;

import sk.mlp.logger.FileLogger;
import sk.mlp.parser.GPXParser;
import sk.mlp.parser.TLVLoader;

/**
 * Trieda StaticMapResolver je určená na vytvorenie URL linky na načítanie mapy a trasy na mape ako obrázok.
 * @author Ľubomír Petrus
 */
public class StaticMapResolver {

    private TLVLoader loader = null;
    private final String startURL = "http://maps.googleapis.com/maps/api/staticmap?center=";
    private final String endURL = "&sensor=false";

    /**
     * Konštruktor triedy StaticMApResolver.
     * @param loader - objekt triedy TLVLoader z ktorého sa získavajú dôležité detaily trasy
     */
    public StaticMapResolver(TLVLoader loader) {
        this.loader = loader;
    }
    
    /**
     * Metóda getStaticMapTrackURL získava link na mapu a trasu bez traťových bodov, kde sú uložené obrázky.
     * @param lineWeight - hrúbka čiary trasy na mape
     * @param color - farba trasovej čiary
     * @param width - šírka mapy
     * @param height - výška čiary
     * @param scale - škálovacia konštanta (n x rozmery mapy)
     * @return Vracia URL adresu podľa ktorej sa dá získať obrázok mapy s trasou.
     */
    public String getStaticMapTrackURL(int lineWeight, String color, int width, int height, int scale){
        if (getLoader() != null) {
            if (getLoader().getTrackPoints().size() < 24) {
                StringBuilder builder = new StringBuilder();
                builder.append(startURL);

                for (int i = 0; i < getLoader().getTrackPoints().size(); i++) {
                    builder.append(getLoader().getTrackPoints().get(i).getLatitude());
                    builder.append(",");
                    builder.append(getLoader().getTrackPoints().get(i).getLongitude());
                    if (i != (getLoader().getTrackPoints().size() - 1)) {
                        builder.append("|");
                    }
                }

                builder.append("&path=color:0xff0000ff|weight:");
                builder.append(lineWeight);
                builder.append("|");
                for (int i = 0; i < getLoader().getTrackPoints().size(); i++) {
                    builder.append(getLoader().getTrackPoints().get(i).getLatitude());
                    builder.append(",");
                    builder.append(getLoader().getTrackPoints().get(i).getLongitude());
                    if (i != (getLoader().getTrackPoints().size() - 1)) {
                        builder.append("|");
                    }
                }

                builder.append("&size=");
                builder.append(height);
                builder.append("x");
                builder.append(width);

                builder.append("&scale=");
                builder.append(scale);

                builder.append("&maptype=roadmap");
                builder.append(endURL);

                return builder.toString();
            }else{
                int addition = getLoader().getTrackPoints().size() / 25;
                
                StringBuilder builder = new StringBuilder();
                builder.append(startURL);

                for (int i = 0; i < getLoader().getTrackPoints().size(); i = i + addition) {
                    builder.append(getLoader().getTrackPoints().get(i).getLatitude());
                    builder.append(",");
                    builder.append(getLoader().getTrackPoints().get(i).getLongitude());
                    builder.append("|");
                }
                builder.deleteCharAt(builder.lastIndexOf("|"));

                builder.append("&path=color:0xff0000ff|weight:");
                builder.append(lineWeight);
                builder.append("|");
                for (int i = 0; i < getLoader().getTrackPoints().size(); i = i + addition) {
                    builder.append(getLoader().getTrackPoints().get(i).getLatitude());
                    builder.append(",");
                    builder.append(getLoader().getTrackPoints().get(i).getLongitude());
                    builder.append("|");
                }
                builder.deleteCharAt(builder.lastIndexOf("|"));

                builder.append("&size=");
                builder.append(height);
                builder.append("x");
                builder.append(width);

                builder.append("&scale=");
                builder.append(scale);

                builder.append("&maptype=roadmap");
                builder.append(endURL);

                return builder.toString();
            }
        }else{
            FileLogger.getInstance().createNewLog("ERROR: Cannot RESOLVE STATIC MAP for track !!!");
            return null;
        }
    }
    
    /**
     * Metóda getStaticMapTrackURLWithMultimedia získava link na mapu a trasu s vyznacením traťových bodov, kde sú uložené obrázky.
     * @param lineWeight - hrúbka čiary trasy na mape
     * @param color - farba trasovej čiary
     * @param width - šírka mapy
     * @param height - výška čiary
     * @param scale - škálovacia konštanta (n x rozmery mapy)
     * @return Vracia URL adresu podľa ktorej sa dá získať obrázok mapy s trasou.
     */
    public String getStaticMapTrackURLWithMultimedia(int lineWeight, String color, int width, int height, int scale){
        if (getLoader() != null) {
            if (getLoader().getTrackPoints().size() < 35) { // 35 je optimum odskusane!!!!
                StringBuilder builder = new StringBuilder();
                builder.append(startURL);

                for (int i = 0; i < getLoader().getTrackPoints().size(); i++) {
                    builder.append(getLoader().getTrackPoints().get(i).getLatitude());
                    builder.append(",");
                    builder.append(getLoader().getTrackPoints().get(i).getLongitude());
                    if (i != (getLoader().getTrackPoints().size() - 1)) {
                        builder.append("|");
                    }
                }

                builder.append("&markers=");
                for (int i = 0; i < getLoader().getTrackPoints().size(); i++) {
                    boolean isFiles[] = getLoader().getIsFiles();
                    if (isFiles[i] == true) {
                        builder.append(getLoader().getTrackPoints().get(i).getLatitude());
                        builder.append(",");
                        builder.append(getLoader().getTrackPoints().get(i).getLongitude());
                        if (i != (getLoader().getTrackPoints().size() - 1)) {
                            builder.append("|");
                        }
                    }
                }
                

                builder.append("&path=color:0xff0000ff|weight:");
                builder.append(lineWeight);
                builder.append("|");
                for (int i = 0; i < getLoader().getTrackPoints().size(); i++) {
                    builder.append(getLoader().getTrackPoints().get(i).getLatitude());
                    builder.append(",");
                    builder.append(getLoader().getTrackPoints().get(i).getLongitude());
                    if (i != (getLoader().getTrackPoints().size() - 1)) {
                        builder.append("|");
                    }
                }

                builder.append("&size=");
                builder.append(height);
                builder.append("x");
                builder.append(width);

                builder.append("&scale=");
                builder.append(scale);

                builder.append("&maptype=roadmap");
                builder.append(endURL);

                return builder.toString();
            }else{
                int addition = getLoader().getTrackPoints().size() / 35;
                addition++;
                
                StringBuilder builder = new StringBuilder();
                builder.append(startURL);

                for (int i = 0; i < getLoader().getTrackPoints().size(); i = i + addition) {
                    builder.append(getLoader().getTrackPoints().get(i).getLatitude());
                    builder.append(",");
                    builder.append(getLoader().getTrackPoints().get(i).getLongitude());
                        builder.append("|");
                }
                builder.deleteCharAt(builder.lastIndexOf("|"));

                builder.append("&markers=");
                for (int i = 0; i < getLoader().getTrackPoints().size(); i = i + addition) {
                    boolean isFiles[] = getLoader().getIsFiles();
                    
                    int addition2;
                    if((i + addition) <= getLoader().getTrackPoints().size()){
                        addition2 = addition;
                    }else{
                        addition2 = getLoader().getTrackPoints().size() - i;
                    }
                    
                    for(int j = i; j < i + addition2; j++){
                        if(isFiles[j] == true){
                            builder.append(getLoader().getTrackPoints().get(i).getLatitude());
                            builder.append(",");
                            builder.append(getLoader().getTrackPoints().get(i).getLongitude());
                            builder.append("|");
                            break;
                        }
                    }
                }
                if (builder.charAt(builder.length() - 1) == '|') {
                    builder.deleteCharAt(builder.lastIndexOf("|"));
                }

                builder.append("&path=color:0xff0000ff|weight:");
                builder.append(lineWeight);
                builder.append("|");
                for (int i = 0; i < getLoader().getTrackPoints().size(); i = i + addition) {
                    builder.append(getLoader().getTrackPoints().get(i).getLatitude());
                    builder.append(",");
                    builder.append(getLoader().getTrackPoints().get(i).getLongitude());
                    builder.append("|");
                }
                builder.deleteCharAt(builder.lastIndexOf("|"));

                builder.append("&size=");
                builder.append(height);
                builder.append("x");
                builder.append(width);

                builder.append("&scale=");
                builder.append(scale);

                builder.append("&maptype=roadmap");
                builder.append(endURL);
                
                if(builder.length() > 2047){
                    String newStr = getStaticMapTrackURL(lineWeight, color, width, height, scale);
                    System.out.println("MAM BODDOV " + newStr.length());
                    return newStr;
                }

                return builder.toString();
            }
            
        }else{
            FileLogger.getInstance().createNewLog("ERROR: Cannot RESOLVE STATIC MAP for track !!!");
            return null;
        }
    }

    /**
     * @return Vracia objekt triedy TLVLoader, z ktorého sa získavajú dôležité detaily danej trasy.
     */
    public TLVLoader getLoader() {
        return loader;
    }

    /**
     * @param loader - objekt triedy TLVLoader z ktorého sa získavajú dôležité detaily trasy
     */
    public void setLoader(TLVLoader loader) {
        this.loader = loader;
    }

}
