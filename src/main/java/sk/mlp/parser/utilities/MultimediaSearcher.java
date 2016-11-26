/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.mlp.parser.utilities;

import sk.mlp.file.FileImpl;
import sk.mlp.file.TrackPointImpl;
import sk.mlp.file.video.VideoCreationDateResolver;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.Sanselan;
import org.apache.sanselan.common.IImageMetadata;
import org.apache.sanselan.formats.jpeg.JpegImageMetadata;
import org.apache.sanselan.formats.tiff.TiffField;
import org.apache.sanselan.formats.tiff.TiffImageMetadata;
import org.apache.sanselan.formats.tiff.constants.TiffConstants;
import org.apache.tools.ant.DirectoryScanner;

/**
 * Trieda určená na vyhľadávanie relevantných multimediálnych súborov
 * @author Matej Pazdič
 */
public class MultimediaSearcher {

    private String searchFilePath;
    private String searchFolder;
    private ArrayList<TrackPointImpl> track;
    private final String OS = System.getProperty("os.name");

    /**
     * Základný konštruktor triedy MultimediaSearcher
     */
    public MultimediaSearcher() {
        searchFilePath = null;
        searchFolder = null;
        track = null;
    }

    /**
     * Preťažený konštruktor triedy MultimediaSearcher
     * @param searchFilePath - cesta ku súboru so zánamami GPS
     * @param searchFolder - koreň adresárovej štruktúry od ktorého sa vyhľadávajú multimediálne súbory
     */
    public MultimediaSearcher(String searchFilePath, String searchFolder) {
        this.searchFilePath = searchFilePath;
        this.searchFolder = searchFolder;
        track = null;
    }

    /**
     * Preťažený konštruktor triedy MultimediaSearcher
     * @param searchFilePath - cesta ku súboru so záznamami GPS
     * @param searchFolder - koreň adresárovej štruktúry od ktorého sa vyhľadávajú multimediálne súbory
     * @param track - štruktúra v ktorej sú uložené jednotlivé načítané GPS body
     */
    public MultimediaSearcher(String searchFilePath, String searchFolder, ArrayList<TrackPointImpl> track) {
        this.searchFilePath = searchFilePath;
        this.searchFolder = searchFolder;
        this.track = track;
    }

    /**
     * @return Vracia cestu ku súboru so záznamami GPS
     */
    public String getSearchFilePath() {
        return searchFilePath;
    }

    /**
     * @param searchFilePath - cesta ku súboru so záznamami GPS
     */
    public void setSearchFilePath(String searchFilePath) {
        this.searchFilePath = searchFilePath;
    }

    /**
     * @return Vracia koreňový adresár vyhľadavácej štruktúry
     */
    public String getSearchFolder() {
        return searchFolder;
    }

    /**
     * @param searchFolder - koreň adresárovej štruktúry od ktorého sa vyhľadávajú multimediálne súbory
     */
    public void setSearchFolder(String searchFolder) {
        this.searchFolder = searchFolder;
    }
    
    /**
     * Metóda určená na samotné vyhľadávanie multimediálnych súborov, bez kontroly či patria k trase.
     * @return Vracia zoznam všetkyćh nájdených podporovaných multimediálnych súborov.
     */
    public String[] startSearchWithoutTrack(){
        System.out.println("Som v startSearchWithoutTrack");
        String os = System.getProperty("os.name");

        DirectoryScanner scanner = new DirectoryScanner();
        scanner.setFollowSymlinks(false);

        if (OS.startsWith("Windows")) {
            String str1 = "**" + System.getProperty("file.separator") + "*.jpg";
            String str2 = "**" + System.getProperty("file.separator") + "*.jpeg";
            String str3 = "**\\*.avi";
            String str4 = "**\\*.mov";
            String str5 = "**\\*.mp4";
            String str6 = "**\\*.3gp";
            String str7 = "**\\*.mp3";
            String str8 = "**\\*.wav";
            String str9 = "**\\*.amr";
            String str10 = "**\\*.txt";
            String str11 = "**\\*.pdf";
            scanner.setIncludes(new String[]{str1, str2, str3, str4, str5, str6, str7, str8, str9, str10, str11});
            File f = new File(this.getSearchFolder());
            scanner.setBasedir(f);
            scanner.setCaseSensitive(false);
            scanner.scan();
            String[] tempFiles = scanner.getIncludedFiles();

            return tempFiles;
        } else {
            String str1 = "**/*.jpg";
            String str2 = "**/*.jpeg";
            String str3 = "**/*.avi";
            String str4 = "**/*.mov";
            String str5 = "**/*.mp4";
            String str6 = "**/*.3gp";
            String str7 = "**/*.mp3";
            String str8 = "**/*.wav";
            String str9 = "**/*.amr";
            String str10 = "**/*.txt";
            String str11 = "**/*.pdf";
            scanner.setIncludes(new String[]{str1, str2, str3, str4, str5, str6, str7, str8, str9, str10, str11});
            scanner.setBasedir(this.searchFolder);
            scanner.setCaseSensitive(false);
            scanner.scan();
            String[] tempFiles = scanner.getIncludedFiles();

            return tempFiles;
        }
    }

    /**
     * Metóda určená na vyhľadávanie relevantných multimediálnych súborov.
     * @return Vracia zoznam relevantných multimediálnych súborov.
     */
    public ArrayList<FileImpl> startSearch() {
        System.out.println("Som v startSearch");
        ArrayList<FileImpl> files = new ArrayList<FileImpl>();
        String os = System.getProperty("os.name");

        files.clear();
        DirectoryScanner scanner = new DirectoryScanner();
        scanner.setFollowSymlinks(false);

        if (OS.startsWith("Windows")) {
            String str1 = "**" + System.getProperty("file.separator") + "*.jpg";
            String str2 = "**" + System.getProperty("file.separator") + "*.jpeg";
            String str3 = "**\\*.avi";
            String str4 = "**\\*.mov";
            String str5 = "**\\*.mp4";
            String str6 = "**\\*.3gp";
            String str7 = "**\\*.mp3";
            String str8 = "**\\*.wav";
            String str9 = "**\\*.amr";
            String str10 = "**\\*.txt";
            String str11 = "**\\*.pdf";
            scanner.setIncludes(new String[]{str1, str2, str3, str4, str5, str6, str7, str8, str9, str10, str11});
            File f = new File(this.getSearchFolder());
            scanner.setBasedir(f);
            scanner.setCaseSensitive(false);
            scanner.scan();
            String[] tempFiles = scanner.getIncludedFiles();

            System.out.println(tempFiles.length);
            Date first = track.get(0).getTime();
            first.setSeconds(first.getSeconds() - 1);
            Date last = track.get(track.size() - 1).getTime();
            last.setSeconds(last.getSeconds() + 1);
            for (int i = 0; i < tempFiles.length; i++) {
                FileImpl fileimpl = new FileImpl();
                String temp = scanner.getBasedir() + "\\" + tempFiles[i];
                if (track != null) {
                    File file = new File(temp);
                    if (temp.toLowerCase().endsWith(".jpg") || temp.toLowerCase().endsWith(".jpeg")) {
                        IImageMetadata metadata = null;
                        try {
                            metadata = Sanselan.getMetadata(file);
                        } catch (ImageReadException e) {
                            System.out.println("ERROR: Cannot read EXIF metadata with Sanselan!!!");
                        } catch (IOException e) {
                            System.out.println("ERROR: Cannot read EXIF metadata with Sanselan!!!");
                        }
                        if (metadata instanceof JpegImageMetadata) {
                            JpegImageMetadata jpegMetadata = (JpegImageMetadata) metadata;
                            TiffField createDateField = jpegMetadata.findEXIFValue(TiffConstants.EXIF_TAG_CREATE_DATE);

                            if (createDateField == null) {
                                fileimpl.setDate(new Date(file.lastModified()));
                            } else {
                                try {
                                    String createDateStr = createDateField.getValueDescription();
                                    createDateStr = createDateStr.substring(createDateStr.indexOf("'") + 1, createDateStr.lastIndexOf("'"));
                                    DateFormat dateForm = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
                                    Date date = (Date) dateForm.parse(createDateStr);
                                    fileimpl.setDate(date);
                                } catch (ParseException ex) {
                                    System.out.println("ERROR: Cannot parse creation date from picture!!!");
                                }
                            }
                            TiffImageMetadata exifMetadata = jpegMetadata.getExif();
                            if (exifMetadata != null) {
                                try {
                                    TiffImageMetadata.GPSInfo gpsInfo = exifMetadata.getGPS();
                                    if (gpsInfo != null) {
                                        fileimpl.setLatitude(String.valueOf(gpsInfo.getLatitudeAsDegreesNorth()));
                                        fileimpl.setLongitude(String.valueOf(gpsInfo.getLongitudeAsDegreesEast()));
                                    }
                                } catch (ImageReadException ex) {
                                    System.out.println("ERROR: Cannot read GPS metadata from jpeg EXIF!!! Using no coordinates!!!");
                                }
                            }
                        } else {
                            fileimpl.setDate(new Date(file.lastModified()));
                        }
                    } else if (temp.toLowerCase().endsWith(".avi") || temp.toLowerCase().endsWith(".mov") || temp.toLowerCase().endsWith(".mp4") || temp.toLowerCase().endsWith(".3gp")) {
                        VideoCreationDateResolver resolver = new VideoCreationDateResolver();
                        Date videoCreationDate = resolver.resolveCreationDate(temp, track.get(0).getLatitude(), track.get(0).getLongitude());
                        if(videoCreationDate != null){
                            fileimpl.setDate(videoCreationDate);
                        }else{
                            fileimpl.setDate(new Date());
                        }
                    } else {
                        fileimpl.setDate(new Date(file.lastModified()));
                    }
                    fileimpl.setPath(temp);
                    if (fileimpl.getDate().after(first) && fileimpl.getDate().before(last)) {
                        if (temp.substring(0, 4).lastIndexOf("\\") != temp.substring(0, 4).indexOf("\\")) {
                            temp = scanner.getBasedir() + tempFiles[i];
                            fileimpl.setPath(temp);
                        }
                            files.add(fileimpl);
                    }
                }
            }
        } else {
            String str1 = "**/*.jpg";
            String str2 = "**/*.jpeg";
            String str3 = "**/*.avi";
            String str4 = "**/*.mov";
            String str5 = "**/*.mp4";
            String str6 = "**/*.3gp";
            String str7 = "**/*.mp3";
            String str8 = "**/*.wav";
            String str9 = "**/*.amr";
            String str10 = "**/*.txt";
            String str11 = "**/*.pdf";
            scanner.setIncludes(new String[]{str1, str2, str3, str4, str5, str6, str7, str8, str9, str10, str11});
            scanner.setBasedir(this.searchFolder);
            scanner.setCaseSensitive(false);
            scanner.scan();
            String[] tempFiles = scanner.getIncludedFiles();

            for (int i = 0; i < tempFiles.length; i++) {
                FileImpl fileimpl = new FileImpl();
                String temp = null;
                //if (!this.searchFolder.endsWith("/")) {
                    temp = scanner.getBasedir() + "/" + tempFiles[i];
               // } else {
                //    temp = scanner.getBasedir() + tempFiles[i];
                //}
                
                System.out.println("Searching file: " + temp + " " + tempFiles.length);
                
                Date first = new Date(track.get(0).getTime().getTime());
                first.setSeconds(first.getSeconds() - 1);
                Date last = new Date(track.get(track.size() - 1).getTime().getTime());
                last.setSeconds(last.getSeconds() + 1);
                if (track != null) {
                    File file = new File(temp);
                    if (temp.toLowerCase().endsWith(".jpg") || temp.toLowerCase().endsWith(".jpeg")) {
                        IImageMetadata metadata = null;
                        System.out.println("SUBOR: " + file.getName());
                        try {
                            metadata = Sanselan.getMetadata(file);
                        } catch (ImageReadException e) {
                            System.out.println("ERROR: Cannot read EXIF metadata with Sanselan!!!");
                        } catch (IOException e) {
                            System.out.println("ERROR: Cannot read EXIF metadata with Sanselan!!!");
                        }
                        if (metadata instanceof JpegImageMetadata) {
                            JpegImageMetadata jpegMetadata = (JpegImageMetadata) metadata;
                            TiffField createDateField = jpegMetadata.findEXIFValue(TiffConstants.EXIF_TAG_CREATE_DATE);

                            if (createDateField == null) {
                                fileimpl.setDate(new Date(file.lastModified()));
                            } else {
                                try {
                                    String createDateStr = createDateField.getValueDescription();
                                    createDateStr = createDateStr.substring(createDateStr.indexOf("'") + 1, createDateStr.lastIndexOf("'"));
                                    DateFormat dateForm = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
                                    Date date = (Date) dateForm.parse(createDateStr);
                                    fileimpl.setDate(date);
                                } catch (ParseException ex) {
                                    System.out.println("ERROR: Cannot parse creation date from picture!!!");
                                }
                            }
                            TiffImageMetadata exifMetadata = jpegMetadata.getExif();
                            if (exifMetadata != null) {
                                try {
                                    TiffImageMetadata.GPSInfo gpsInfo = exifMetadata.getGPS();
                                    if (gpsInfo != null) {
                                        fileimpl.setLatitude(String.valueOf(gpsInfo.getLatitudeAsDegreesNorth()));
                                        fileimpl.setLongitude(String.valueOf(gpsInfo.getLongitudeAsDegreesEast()));
                                    }
                                } catch (ImageReadException ex) {
                                    System.out.println("ERROR: Cannot read GPS metadata from jpeg EXIF!!! Using no coordinates!!!");
                                }
                            }
                        } else {
                            fileimpl.setDate(new Date(file.lastModified()));
                            System.out.println("Pouzivam creation date.");
                        }
                    } else if (temp.toLowerCase().endsWith(".avi") || temp.toLowerCase().endsWith(".mov") || temp.toLowerCase().endsWith(".mp4") || temp.toLowerCase().endsWith(".3gp")) {
                        VideoCreationDateResolver resolver = new VideoCreationDateResolver();
                        Date videoCreationDate = resolver.resolveCreationDate(temp, track.get(0).getLatitude(), track.get(0).getLongitude());
                        if (videoCreationDate != null) {
                            fileimpl.setDate(videoCreationDate);
                        } 
                    }else {
                            fileimpl.setDate(new Date(file.lastModified()));
                            System.out.println("Pouzivam creation date.");
                        }
                        fileimpl.setPath(temp);
                    if (fileimpl.getDate().after(first) && fileimpl.getDate().before(last)) {
                        if (temp.substring(0, 4).lastIndexOf("/") != temp.substring(0, 4).indexOf("/")) {
                            temp = scanner.getBasedir() + tempFiles[i];
                            fileimpl.setPath(temp);
                        }
                            files.add(fileimpl);
                    }
                }
            }
        }
        
        ArrayList<FileImpl> goodFiles = new ArrayList<FileImpl>();
        for (int i = 0; i < files.size(); i++) {
            Date fileDate = files.get(i).getDate();
            for (int j = 1; j < track.size(); j++) {
                Date prevTrackPointDate = track.get(j - 1).getTime();
                prevTrackPointDate.setSeconds(track.get(j - 1).getTime().getSeconds() - 1);
                Date nextTrackPointDate = track.get(j).getTime();
                nextTrackPointDate.setSeconds(track.get(j).getTime().getSeconds() + 1);
                if (files.get(i).getLongitude() != null && files.get(i).getLatitude() != null) {
                    if ((fileDate.after(prevTrackPointDate) && fileDate.before(nextTrackPointDate)) || (fileDate.equals(prevTrackPointDate) || (fileDate.equals(nextTrackPointDate)))) {
                        double deltaLat1 = Math.abs(Double.parseDouble(files.get(i).getLatitude()) - track.get(j - 1).getLatitude());
                        double deltaLon1 = Math.abs(Double.parseDouble(files.get(i).getLongitude()) - track.get(j - 1).getLongitude());
                        double deltaLat2 = Math.abs(Double.parseDouble(files.get(i).getLatitude()) - track.get(j).getLatitude());
                        double deltaLon2 = Math.abs(Double.parseDouble(files.get(i).getLongitude()) - track.get(j).getLongitude());

                        if ((deltaLat1 <= 0.0007 && deltaLon1 <= 0.0007) || (deltaLat2 <= 0.0007 && deltaLon2 <= 0.0007)) {
                            goodFiles.add(files.get(i));
                            break;
                        }
                    }
                } else {
                    if ((fileDate.after(prevTrackPointDate) && fileDate.before(nextTrackPointDate))) {
                        goodFiles.add(files.get(i));
                        break;
                    }
                }
            }
        }
        System.out.println("kolko je multimedii" + goodFiles.size());
        return goodFiles;
    }
    
    /**
     * Metóda určená na vyhľadávanie relevantných multimediálnych súborov, pričom sa na koniec zoznamu pridajú súbory, ktoré nepatria k trase aby mohli byť neskôr priradené manuálne.
     * @return Vracia zoznam relevantných multimediálnych súborov.
     */
    public ArrayList<FileImpl> startSearchWithBadFiles() {
        System.out.println("Som v startSearchWithBadFiles");

        ArrayList<FileImpl> files = new ArrayList<FileImpl>();
        ArrayList<FileImpl> badFiles = new ArrayList<FileImpl>();
        String os = System.getProperty("os.name");

        files.clear();
        badFiles.clear();
        DirectoryScanner scanner = new DirectoryScanner();
        scanner.setFollowSymlinks(false);

        if (OS.startsWith("Windows")) {
            String str1 = "**" + System.getProperty("file.separator") + "*.jpg";
            String str2 = "**" + System.getProperty("file.separator") + "*.jpeg";
            String str3 = "**\\*.avi";
            String str4 = "**\\*.mov";
            String str5 = "**\\*.mp4";
            String str6 = "**\\*.3gp";
            String str7 = "**\\*.mp3";
            String str8 = "**\\*.wav";
            String str9 = "**\\*.amr";
            String str10 = "**\\*.txt";
            String str11 = "**\\*.pdf";
            
            String strEx1 = "**" + System.getProperty("file.separator") + "*THUMB.jpg";
            String strEx2 = "**" + System.getProperty("file.separator") + "*THUMB.jpeg";
            
            scanner.setIncludes(new String[]{str1, str2, str3, str4, str5, str6, str7, str8, str9, str10, str11});
            scanner.setExcludes(new String[]{strEx1, strEx2});
            File f = new File(this.getSearchFolder());
            scanner.setBasedir(f);
            scanner.setCaseSensitive(false);
            scanner.scan();
            String[] tempFiles = scanner.getIncludedFiles();

            System.out.println(tempFiles.length);
            Date first = track.get(0).getTime();
            first.setSeconds(first.getSeconds() - 1);
            Date last = track.get(track.size() - 1).getTime();
            last.setSeconds(last.getSeconds() + 1);
            for (int i = 0; i < tempFiles.length; i++) {
                FileImpl fileimpl = new FileImpl();
                String temp = scanner.getBasedir() + "\\" + tempFiles[i];
                if (track != null) {
                    File file = new File(temp);
                    if (temp.toLowerCase().endsWith(".jpg") || temp.toLowerCase().endsWith(".jpeg")) {
                        IImageMetadata metadata = null;
                        try {
                            metadata = Sanselan.getMetadata(file);
                        } catch (ImageReadException e) {
                            System.out.println("ERROR: Cannot read EXIF metadata with Sanselan!!!");
                        } catch (IOException e) {
                            System.out.println("ERROR: Cannot read EXIF metadata with Sanselan!!!");
                        }
                        if (metadata instanceof JpegImageMetadata) {
                            JpegImageMetadata jpegMetadata = (JpegImageMetadata) metadata;
                            TiffField createDateField = jpegMetadata.findEXIFValue(TiffConstants.EXIF_TAG_CREATE_DATE);

                            if (createDateField == null) {
                                fileimpl.setDate(new Date(file.lastModified()));
                            } else {
                                try {
                                    String createDateStr = createDateField.getValueDescription();
                                    createDateStr = createDateStr.substring(createDateStr.indexOf("'") + 1, createDateStr.lastIndexOf("'"));
                                    DateFormat dateForm = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
                                    Date date = (Date) dateForm.parse(createDateStr);
                                    fileimpl.setDate(date);
                                } catch (ParseException ex) {
                                    System.out.println("ERROR: Cannot parse creation date from picture!!!");
                                }
                            }
                            TiffImageMetadata exifMetadata = jpegMetadata.getExif();
                            if (exifMetadata != null) {
                                try {
                                    TiffImageMetadata.GPSInfo gpsInfo = exifMetadata.getGPS();
                                    if (gpsInfo != null) {
                                        fileimpl.setLatitude(String.valueOf(gpsInfo.getLatitudeAsDegreesNorth()));
                                        fileimpl.setLongitude(String.valueOf(gpsInfo.getLongitudeAsDegreesEast()));
                                    }
                                } catch (ImageReadException ex) {
                                    System.out.println("ERROR: Cannot read GPS metadata from jpeg EXIF!!! Using no coordinates!!!");
                                }
                            }
                        } else {
                            fileimpl.setDate(new Date(file.lastModified()));
                        }
                    } else if (temp.toLowerCase().endsWith(".avi") || temp.toLowerCase().endsWith(".mov") || temp.toLowerCase().endsWith(".mp4") || temp.toLowerCase().endsWith(".3gp")) {
                        VideoCreationDateResolver resolver = new VideoCreationDateResolver();
                        Date videoCreationDate = resolver.resolveCreationDate(temp, track.get(0).getLatitude(), track.get(0).getLongitude());
                        if(videoCreationDate != null){
                            fileimpl.setDate(videoCreationDate);
                        }else{
                            fileimpl.setDate(new Date());
                        }
                    } else {
                        fileimpl.setDate(new Date(file.lastModified()));
                    }
                    fileimpl.setPath(temp);
                    if (fileimpl.getDate().after(first) && fileimpl.getDate().before(last)) {
                        if (temp.substring(0, 4).lastIndexOf("\\") != temp.substring(0, 4).indexOf("\\")) {
                            temp = scanner.getBasedir() + tempFiles[i];
                            fileimpl.setPath(temp);
                        }
                            files.add(fileimpl);
                    }else{
                        badFiles.add(fileimpl);
                    }
                }
            }
        } else {
            String str1 = "**/*.jpg";
            String str2 = "**/*.jpeg";
            String str3 = "**/*.avi";
            String str4 = "**/*.mov";
            String str5 = "**/*.mp4";
            String str6 = "**/*.3gp";
            String str7 = "**/*.mp3";
            String str8 = "**/*.wav";
            String str9 = "**/*.amr";
            String str10 = "**/*.txt";
            String str11 = "**/*.pdf";
            
            String strEx1 = "**" + System.getProperty("file.separator") + "*THUMB.jpg";
            String strEx2 = "**" + System.getProperty("file.separator") + "*THUMB.jpeg";
            
            scanner.setIncludes(new String[]{str1, str2, str3, str4, str5, str6, str7, str8, str9, str10, str11});
            scanner.setExcludes(new String[]{strEx1, strEx2});
            scanner.setBasedir(this.searchFolder);
            scanner.setCaseSensitive(false);
            scanner.scan();
            String[] tempFiles = scanner.getIncludedFiles();

            for (int i = 0; i < tempFiles.length; i++) {
                FileImpl fileimpl = new FileImpl();
                String temp = null;
                //if (!this.searchFolder.endsWith("/")) {
                    temp = scanner.getBasedir() + "/" + tempFiles[i];
               // } else {
                //    temp = scanner.getBasedir() + tempFiles[i];
                //}
                
                System.out.println("Searching file: " + temp + " " + tempFiles.length);
                
                Date first = new Date(track.get(0).getTime().getTime());
                System.out.println("FIRST DATE: " + first);
                first.setSeconds(first.getSeconds() - 1);
                System.out.println("FIRST DATE: " + first);
                Date last = new Date(track.get(track.size() - 1).getTime().getTime());
                System.out.println("LAST DATE: " + first);
                last.setSeconds(last.getSeconds() + 1);
                System.out.println("LAST DATE: " + first);
                if (track != null) {
                    File file = new File(temp);
                    if (temp.toLowerCase().endsWith(".jpg") || temp.toLowerCase().endsWith(".jpeg")) {
                        IImageMetadata metadata = null;
                        System.out.println("SUBOR: " + file.getName());
                        try {
                            metadata = Sanselan.getMetadata(file);
                        } catch (ImageReadException e) {
                            System.out.println("ERROR: Cannot read EXIF metadata with Sanselan!!!");
                        } catch (IOException e) {
                            System.out.println("ERROR: Cannot read EXIF metadata with Sanselan!!!");
                        }
                        if (metadata instanceof JpegImageMetadata) {
                            JpegImageMetadata jpegMetadata = (JpegImageMetadata) metadata;
                            TiffField createDateField = jpegMetadata.findEXIFValue(TiffConstants.EXIF_TAG_CREATE_DATE);

                            if (createDateField == null) {
                                fileimpl.setDate(new Date(file.lastModified()));
                            } else {
                                try {
                                    String createDateStr = createDateField.getValueDescription();
                                    createDateStr = createDateStr.substring(createDateStr.indexOf("'") + 1, createDateStr.lastIndexOf("'"));
                                    DateFormat dateForm = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
                                    Date date = (Date) dateForm.parse(createDateStr);
                                    fileimpl.setDate(date);
                                } catch (ParseException ex) {
                                    System.out.println("ERROR: Cannot parse creation date from picture!!!");
                                }
                            }
                            TiffImageMetadata exifMetadata = jpegMetadata.getExif();
                            if (exifMetadata != null) {
                                try {
                                    TiffImageMetadata.GPSInfo gpsInfo = exifMetadata.getGPS();
                                    if (gpsInfo != null) {
                                        fileimpl.setLatitude(String.valueOf(gpsInfo.getLatitudeAsDegreesNorth()));
                                        fileimpl.setLongitude(String.valueOf(gpsInfo.getLongitudeAsDegreesEast()));
                                    }
                                } catch (ImageReadException ex) {
                                    System.out.println("ERROR: Cannot read GPS metadata from jpeg EXIF!!! Using no coordinates!!!");
                                }
                            }
                        } else {
                            fileimpl.setDate(new Date(file.lastModified()));
                            System.out.println("Pouzivam creation date.");
                        }
                    } else if (temp.toLowerCase().endsWith(".avi") || temp.toLowerCase().endsWith(".mov") || temp.toLowerCase().endsWith(".mp4") || temp.toLowerCase().endsWith(".3gp")) {
                        VideoCreationDateResolver resolver = new VideoCreationDateResolver();
                        Date videoCreationDate = resolver.resolveCreationDate(temp, track.get(0).getLatitude(), track.get(0).getLongitude());
                        if (videoCreationDate != null) {
                            fileimpl.setDate(videoCreationDate);
                        } 
                    }else {
                            fileimpl.setDate(new Date(file.lastModified()));
                            System.out.println("Pouzivam creation date.");
                        }
                        fileimpl.setPath(temp);
                    if (fileimpl.getDate().after(first) && fileimpl.getDate().before(last)) {
                        if (temp.substring(0, 4).lastIndexOf("/") != temp.substring(0, 4).indexOf("/")) {
                            temp = scanner.getBasedir() + tempFiles[i];
                            fileimpl.setPath(temp);
                        }
                            files.add(fileimpl);
                    }else{
                        badFiles.add(fileimpl);
                    }
                }
            }
        }
        
        ArrayList<FileImpl> goodFiles = new ArrayList<FileImpl>();
        for (int i = 0; i < files.size(); i++) {
            Date fileDate = files.get(i).getDate();
            for (int j = 1; j < track.size(); j++) {
                Date prevTrackPointDate = track.get(j - 1).getTime();
                prevTrackPointDate.setSeconds(track.get(j - 1).getTime().getSeconds() - 1);
                Date nextTrackPointDate = track.get(j).getTime();
                nextTrackPointDate.setSeconds(track.get(j).getTime().getSeconds() + 1);
                if (files.get(i).getLongitude() != null && files.get(i).getLatitude() != null) {
                    if ((fileDate.after(prevTrackPointDate) && fileDate.before(nextTrackPointDate)) || (fileDate.equals(prevTrackPointDate) || (fileDate.equals(nextTrackPointDate)))) {
                        double deltaLat1 = Math.abs(Double.parseDouble(files.get(i).getLatitude()) - track.get(j - 1).getLatitude());
                        double deltaLon1 = Math.abs(Double.parseDouble(files.get(i).getLongitude()) - track.get(j - 1).getLongitude());
                        double deltaLat2 = Math.abs(Double.parseDouble(files.get(i).getLatitude()) - track.get(j).getLatitude());
                        double deltaLon2 = Math.abs(Double.parseDouble(files.get(i).getLongitude()) - track.get(j).getLongitude());

                        if ((deltaLat1 <= 0.0007 && deltaLon1 <= 0.0007) || (deltaLat2 <= 0.0007 && deltaLon2 <= 0.0007)) {
                            goodFiles.add(files.get(i));
                            //System.out.println("GOOD> " + files.get(i).getPath());
                            break;
                         }else{
                             if(!badFiles.contains(files.get(i))){
                                 badFiles.add(files.get(i));
                             //System.out.println("BAD> " + files.get(i).getPath());
                             }   
                        }
                    }
                } else {
                    if ((fileDate.after(prevTrackPointDate) && fileDate.before(nextTrackPointDate))) {
                        goodFiles.add(files.get(i));
                        break;
                    }else{
                        //badFiles.add(files.get(i));
                    }
                }
                Date prevTrackPointDate1 = track.get(j - 1).getTime();
                prevTrackPointDate1.setSeconds(track.get(j - 1).getTime().getSeconds() + 1);
                Date nextTrackPointDate1 = track.get(j).getTime();
                nextTrackPointDate1.setSeconds(track.get(j).getTime().getSeconds() - 1);
            }
        }
        System.out.println("kolko je multimedii" + goodFiles.size() + " " + badFiles.size());
        goodFiles.addAll(badFiles);
        System.out.println("kolko je multimedii dokopy" + goodFiles.size());
        Date first1 = null;
        Date last1 = null;
        if(OS.startsWith("Windows")){
        Date first = track.get(0).getTime();
        first.setSeconds(first.getSeconds() + 1);
        first1 = first;
        Date last = track.get(track.size() - 1).getTime();
        last.setSeconds(last.getSeconds() - 1);
        last1 = last;
        }
        System.out.println("FIRST DATE " + first1 + ". LAST DATE " + last1);
        return goodFiles;
    }

    /**
     * @param track - Zoznam jednotlivých trackpointov danej trasy zapísaných pomocou údajovej štruktúry "TrackPointImpl".
     */
    public void setTrackPoints(ArrayList<TrackPointImpl> track) {
        this.track = track;
    }
}
