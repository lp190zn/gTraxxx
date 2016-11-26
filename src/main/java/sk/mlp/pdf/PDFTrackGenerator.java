/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sk.mlp.pdf;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import sk.mlp.logger.FileLogger;
import sk.mlp.parser.TLVLoader;

import java.io.FileOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Trieda PDFTrackGenerator slúži na generovanie publikácií vytvorených 
 * trás vo formáte PDF. Na vytváranie PDF súborov je využitý balíček 
 * iText, ktorý je dostupný na adrese http://sourceforge.net/projects/itext/.
 * @author Ľubomír Petrus
 */
public class PDFTrackGenerator {
    
   
    
    private String path = null;
    private String fileName = null;
    private TLVLoader loader = null;
    
    /**
     * Konštruktor triedy PDFTrackGenerator.
     * @param loader - objekt TLVLoadera, z ktorého sa ziskávajú dôležité detaily trasy
     * @param path - cesta ku stromovej štruktúre danej trasy
     * @param fileName - názov danej trasy
     */
    public PDFTrackGenerator(TLVLoader loader, String path, String fileName){
        this.loader = loader;
        this.path = path;
        this.fileName = fileName;
        System.out.println("SOM V PDF GENERATORE!!!");
    }
    
    /**
     * Metóda generateTrackPDFA4 slúži na samotné vygenerovanie PDF dokumentu trasy na formát A4.
     * @param lineWeight - hrúbka čiary trasy na mape
     * @param color - farba čiary trasy na mape
     * @param width - šírka mapy
     * @param height - výška mapy
     * @param scale - škálovacia konštanta mapy (n x rozlíšenie mapy)
     * @param startDate - dátum a čas prvého bodu trasy
     * @param endDate - dátum a čas posledného bodu trasy
     * @param activity - aktivita trasy
     * @param user - používateľ (majiteľ) trasy
     */
    public void generateTrackPDFA4(int lineWeight, String color, int width, int height, int scale, String startDate, String endDate, String activity, String user){
        try {
            Document doc = new Document();
            PdfWriter.getInstance(doc, new FileOutputStream(path + fileName + ".pdf"));
            doc.open();

            
            Font nadpisFont = new Font(Font.FontFamily.HELVETICA, 25, Font.BOLD);

            Font detailyFont = new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL);

            Paragraph nadpisPar = new Paragraph();

            nadpisPar.setAlignment(Element.ALIGN_CENTER);
   
            Phrase nadpis = new Phrase(fileName, nadpisFont);
 
            nadpisPar.add(nadpis);
     
            nadpisPar.add("");

            
            doc.add(nadpisPar);
            doc.add(Chunk.NEWLINE);
            
            PdfPTable tabulka = new PdfPTable(2);
            tabulka.setWidthPercentage(100);
            float[] columnWidth = {6f,4f};
            tabulka.setWidths(columnWidth);

            StaticMapResolver res = new StaticMapResolver(loader);
     
            String mapUrl = res.getStaticMapTrackURLWithMultimedia(lineWeight, color, width, height, scale);

            
            Image img = Image.getInstance(new URL(mapUrl));
            //img.scalePercent(50);
            PdfPCell riadokSObr = new PdfPCell(img, true);
            riadokSObr.setBorder(Rectangle.NO_BORDER);
            riadokSObr.setPaddingBottom(10f);
            PdfPCell riadokSText = new PdfPCell(new Phrase("Description: " + loader.getTrackDescription() + "\n\n\nTrack activity: " + activity.substring(4) + "\n\n\nStart place: " + loader.getStartAddress() + "\n\n\nEnd Place: " + loader.getEndAddress() + "\n\n\nTrack length: " + loader.getLength() + " km\n\n\nMin elevation: " + loader.getMinElevation() + " m\n\n\nMax elevation: " + loader.getMaxElevation() + " m\n\n\nHeight difference: " + loader.getHeightDiff() + " m\n\n\nStart: " + startDate + "\n\n\nEnd: " + endDate + "\n\n\nDuration: " + loader.getDuration(), detailyFont));
            riadokSText.setBorder(Rectangle.NO_BORDER);
            riadokSText.setPaddingLeft(20f);
            riadokSText.setPaddingTop(5f);
            riadokSText.setPaddingBottom(10f);
            tabulka.addCell(riadokSObr);
            tabulka.addCell(riadokSText);
            
            doc.add(tabulka);
            //doc.add(new Phrase("\n", detailyFont));
            
            PdfPTable obrTabulka = new PdfPTable(3);
            obrTabulka.setWidthPercentage(100);
            
            ArrayList<String> goodFiles = new ArrayList<String>();
            for(int i = 0; i < loader.getMultimediaFiles().size();  i++){
                if(!loader.getMultimediaFiles().get(i).getPath().startsWith("YTB")){
                    String extension = loader.getMultimediaFiles().get(i).getPath().substring(loader.getMultimediaFiles().get(i).getPath().lastIndexOf("."), loader.getMultimediaFiles().get(i).getPath().length());
                    String newPath = loader.getMultimediaFiles().get(i).getPath().substring(0, loader.getMultimediaFiles().get(i).getPath().lastIndexOf(".")) + "_THUMB" + extension;
                    goodFiles.add(newPath);
                }
            }
            
            if(!goodFiles.isEmpty()){
                int freeCount = 9;
                if(goodFiles.size() <= 9){
                    for(int i = 0; i < goodFiles.size(); i++){
                        Image tempImg = Image.getInstance(goodFiles.get(i));
                        tempImg.scalePercent(10f);
                        PdfPCell tempCell = new PdfPCell(tempImg, true);
                        tempCell.setPadding(3f);
                        //tempCell.setPaddingTop(5f);
                        tempCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        tempCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        tempCell.setFixedHeight(130f);
                        tempCell.setBackgroundColor(BaseColor.BLACK);
                        tempCell.setBorderColor(BaseColor.WHITE);
                        tempCell.setBorderWidth(4f);
                        //tempCell.setBorder(Rectangle.NO_BORDER);
                        obrTabulka.addCell(tempCell);
                    }
                    for(int i = 0; i < 9 - goodFiles.size(); i++){
                        PdfPCell tempCell = new PdfPCell();
                        tempCell.setBorder(Rectangle.NO_BORDER);
                        obrTabulka.addCell(tempCell);
                    }
                }else if(goodFiles.size() <= 18){
                    for(int i = 0; i < 9; i++){
                        Image tempImg = Image.getInstance(goodFiles.get(i));
                        tempImg.scalePercent(10f);
                        PdfPCell tempCell = new PdfPCell(tempImg, true);
                        tempCell.setPadding(3f);
                        //tempCell.setPaddingTop(5f);
                        tempCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        tempCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        tempCell.setFixedHeight(130f);
                        tempCell.setBackgroundColor(BaseColor.BLACK);
                        tempCell.setBorderColor(BaseColor.WHITE);
                        tempCell.setBorderWidth(4f);
                        //tempCell.setBorder(Rectangle.NO_BORDER);
                        obrTabulka.addCell(tempCell);
                    }
                }else{
                    for(int i = 0; i < (goodFiles.size() % 9); i++){
                        goodFiles.remove(goodFiles.size() - 1 - i);
                    }
                    
                    int counting = (goodFiles.size() / 9);
                    
                    for(int i = 0; i < goodFiles.size(); i = i + counting){
                        Image tempImg = Image.getInstance(goodFiles.get(i));
                        tempImg.scalePercent(10f);
                        PdfPCell tempCell = new PdfPCell(tempImg, true);
                        tempCell.setPadding(3f);
                        //tempCell.setPaddingTop(5f);
                        tempCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        tempCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        tempCell.setFixedHeight(130f);
                        tempCell.setBackgroundColor(BaseColor.BLACK);
                        tempCell.setBorderColor(BaseColor.WHITE);
                        tempCell.setBorderWidth(4f);
                        //tempCell.setBorder(Rectangle.NO_BORDER);
                        obrTabulka.addCell(tempCell);
                        freeCount--;
                    }
                    for(int i = 0; i < freeCount; i++){
                        PdfPCell tempCell = new PdfPCell();
                        tempCell.setBorder(Rectangle.NO_BORDER);
                        obrTabulka.addCell(tempCell);
                    }
                }
            }
            
            doc.add(obrTabulka);
            
            Font lastFont = new Font(Font.FontFamily.HELVETICA, 7, Font.ITALIC);
            Phrase lastText = new Phrase("This PDF document was generated by gTrax app for user " + user, lastFont);
            doc.add(lastText);
            
            
            doc.close();
        } catch (Exception ex) {
            FileLogger.getInstance().createNewLog("ERROR: Cannot CREATE PDF for track " + fileName + " for user " + user + " !!!");
            System.out.println("pruser");
        }
        
    }
    
}
