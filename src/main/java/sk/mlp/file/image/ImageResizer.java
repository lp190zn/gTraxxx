/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sk.mlp.file.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Mode;

import sk.mlp.file.FileImpl;

/**
 * Trieda ImageResizer je určerná na vytváranie zmenšenín (Thumbnail) z 
 * obrázkových multimediálnych súborov, ktoré sú odovzdávané do 
 * systému pri vytváraní trás. Táto metóda využíva pri vytváraní obrázkových
 * ukážok balíćek služieb imgscalr-lib-4.2.jar.
 * @author Matej Pazdič
 */
public class ImageResizer {
    
    private ArrayList<FileImpl> files;
    private int width = -1;
    private int height = -1;
    
    /**
     * Konštruktor (Kompletný) triedy ImageResizer.
     * @param files - zoznam ciest k obrázkovým multimediálym súborom
     * @param width - cieľová šírka ukážky
     * @param height - cieľová výška ukážky
     */
    public ImageResizer(ArrayList<FileImpl> files, int width, int height){
        this.files = files;
        this.width = width;
        this.height = height;
    }
    
    /**
     * Preťažený konštruktor triedy ImageResizer.
     * @param width - cieľová šírka ukážky
     * @param height - cieľová výška ukážky
     */
    public ImageResizer(int width, int height){
        this.width = width;
        this.height = height;
    }
    
    /**
     * Metóda resizeImagesWithTempThubnails je určená na samotné vytváranie ukážok z obrázkový multimedialnych súborov, ktoré sú vložené do zonamu files.
     * @throws ThumbnailException Výnimka sa vyhodí pri problémoch s vytvorením ukážky
     */
    public void resizeImagesWithTempThubnails() throws ThumbnailException{
        try {
            for(int i = 0; i < files.size(); i++){
                String originalPath = files.get(i).getPath();
                String extension = originalPath.substring(originalPath.lastIndexOf("."), originalPath.length());
                String newPath = originalPath.substring(0,originalPath.lastIndexOf(".")) + "_THUMB" + extension;
                
                BufferedImage img = ImageIO.read(new File(originalPath));
                BufferedImage scaledImg = Scalr.resize(img, Mode.AUTOMATIC, width, height);
                File destFile = new File(newPath);
                ImageIO.write(scaledImg, "jpg", destFile);
                //System.out.println("Done resizing image: " + newPath + " " + newPath);
            }
            
        } catch (Exception ex) {
            throw new ThumbnailException();
        }
    }
    
    /**
     * Metóda resizeImmaeWithTempThubnails je určeny na vytorenie obrázkovej ukáźky k danému obrazkoveho multimediálnemu súboru.
     * @param pathToImage - cesta k súboru, z ktorého sa má vytvoriť obrázková ukážka
     * @throws ThumbnailException Výnimka sa vyhodí pri problémoch s vytvorením ukážky
     */
    public void resizeImageWithTempThubnails(String pathToImage) throws ThumbnailException{
        try {
                String originalPath = pathToImage;
                String extension = originalPath.substring(originalPath.lastIndexOf("."), originalPath.length());
                String newPath = originalPath.substring(0,originalPath.lastIndexOf(".")) + "_THUMB" + extension;
                
                BufferedImage img = ImageIO.read(new File(originalPath));
                BufferedImage scaledImg = Scalr.resize(img, Mode.AUTOMATIC, width, height);
                File destFile = new File(newPath);
                ImageIO.write(scaledImg, "jpg", destFile);
                //System.out.println("Done resizing image: " + newPath + " " + newPath);
            
        } catch (Exception ex) {
            throw new ThumbnailException();
        }
    }

    /**
     * @return Navrátová hodnota je zoznam ciest k multimediálnym súborom, reprezentovaných reťazcami znakov.
     */
    public ArrayList<FileImpl> getFiles() {
        return files;
    }

    /**
     * @return Navrátová hodnota predstavuje požadovanú šírku vytváraných ukážok.
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return Navrátová hodnota predstavuje požadovanú výšku vytváraných ukážok.
     */
    public int getHeight() {
        return height;
    }
}
