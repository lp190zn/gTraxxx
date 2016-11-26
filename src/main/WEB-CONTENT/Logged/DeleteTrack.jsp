<%-- 
    Document   : skuska
    Created on : 1.11.2013, 19:36:17
    Author     : matej_000
--%>

<%@page import="sk.mlp.logger.FileLogger"%>
<%@page import="sk.mlp.ui.model.User"%>
<%@page import="sk.mlp.database.DatabaseServices"%>
<%@page import="sk.mlp.file.video.YouTubeAgent"%>
<%@page import="sk.mlp.file.FileImpl"%>
<%@page import="java.util.ArrayList"%>
<%@page import="sk.mlp.parser.TLVLoader"%>
<%@page import="sk.mlp.ui.model.Track"%>
<%@page import="java.io.File"%>
<%@page import="org.apache.commons.io.FileUtils"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    session.removeAttribute("trackFilename");
    session.removeAttribute("trackName");
    session.removeAttribute("trackDescr");
    session.removeAttribute("trackActivity");
    session.removeAttribute("access");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
            YouTubeAgent agent = new YouTubeAgent("skuska.api3@gmail.com", "skuskaapi3");
            String system = System.getProperty("os.name");
            int trkID = Integer.parseInt(request.getParameter("trkID"));
            
            DatabaseServices databaseServices = new DatabaseServices();
            int userID = (int)databaseServices.findUserByEmail(session.getAttribute("username").toString()).getIdent();
            Track track = databaseServices.findTrackById(trkID);
            int realTrkID = (int)track.getUser().getIdent();
            
            if(realTrkID == userID){
                String path = track.getFile();
                    if (system.startsWith("Windows")) {
                        path = path.replaceAll("/", "\\\\");
                    }
                    out.println(path);

                    TLVLoader loader = new TLVLoader();
                    loader.readTLVFile(path, track.getFile());
                    ArrayList<FileImpl> files = loader.getMultimediaFiles();
                    System.out.println("Musim deletnut: " + files.size());

                    for (int i = 0; i < files.size(); i++) {
                        String filePath = files.get(i).getPath();
                        System.out.println("Som tu konecne!");
                        if (filePath.startsWith("YTB ")) {
                            String videoEntryID = filePath.substring(filePath.indexOf("YTB ") + 4);
                            System.out.println("DELETE: " + filePath + " ??? " + videoEntryID);
                            agent.deleteVideo(videoEntryID);
                        }
                    }

                    FileUtils.deleteDirectory(new File(path));


                    databaseServices.deleteTrack(trkID);
                    response.sendRedirect("ShowTracks.jsp");
            }else{
                FileLogger.getInstance().createNewLog("Warning: User " + session.getAttribute("username") + " tried to delete userID " + realTrkID + " track!!!");
                response.sendRedirect("ShowTracks.jsp");
            }
            
        %>
            
    </body>
</html>
