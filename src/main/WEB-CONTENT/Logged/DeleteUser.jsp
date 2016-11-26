<%-- 
    Document   : DeleteUser
    Created on : 27.2.2014, 10:12:37
    Author     : matej_000
--%>

<%@page import="java.io.BufferedReader"%>
<%@page import="java.io.FileReader"%>
<%@page import="sk.mlp.logger.FileLogger"%>
<%@page import="sk.mlp.database.DatabaseServices"%>
<%@page import="org.apache.commons.io.FileUtils"%>
<%@page import="java.io.File"%>
<%@page import="sk.mlp.file.FileImpl"%>
<%@page import="sk.mlp.parser.TLVLoader"%>
<%@page import="sk.mlp.file.video.YouTubeAgent"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="sk.mlp.ui.model.Track"%>
<%@page import="sk.mlp.util.Constants"%>
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
		ArrayList<Integer> trackIDs = new ArrayList<Integer>();
		String user = session.getAttribute("username").toString();
		YouTubeAgent agent = new YouTubeAgent("skuska.api3@gmail.com", "skuskaapi3");
		String system = System.getProperty("os.name");
		if (user != null) {
			DatabaseServices databaseServices = new DatabaseServices();
			List<Track> tracks = databaseServices.getUserTracks(user);

			for (Track track : tracks) {

				String path = track.getTrackFile();
				if (system.startsWith("Windows")) {
					path = path.replaceAll("/", "\\\\");
				}
				out.println(path);

				TLVLoader loader = new TLVLoader();
				loader.readTLVFile(path, track.getTrackName());
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
				databaseServices.deleteTrack(track.getTrackId());
			}
			boolean isErased = databaseServices.deleteUser(databaseServices.findUserByEmail(user).getUserId());
			String rootPath = null;
			if (isErased) {

				rootPath = Constants.USER_DATA_STORAGE + user + "\\";

				FileUtils.deleteDirectory(new File(rootPath));
				session.removeAttribute("username");
				session.removeAttribute("Pass");
				session.invalidate();
				FileLogger.getInstance().createNewLog("User " + user + "was successfuly erased from server.");

				response.sendRedirect("../LoginPage.jsp");
			} else {
				session.removeAttribute("username");
				session.removeAttribute("Pass");
				session.invalidate();
				FileLogger.getInstance().createNewLog("ERROR: Cannot erase user " + user + "from server!!!");

				response.sendRedirect("../LoginPage.jsp");
			}
		}
	%>
</body>
</html>
