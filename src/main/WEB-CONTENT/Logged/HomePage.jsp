<%@page import="sk.mlp.database.DatabaseServices"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.ArrayList"%>
<%@page import="sk.mlp.ui.model.Track"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
//    session.removeAttribute("trackFilename");
//    session.removeAttribute("trackName");
//    session.removeAttribute("trackDescr");
//    session.removeAttribute("trackActivity");
//    session.removeAttribute("access");
//    session.removeAttribute("trackNameExist");
//    session.removeAttribute("isMultimedia");
//    session.removeAttribute("userEmail");
%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Home page</title>

        <link rel="stylesheet" href="https://netdna.bootstrapcdn.com/font-awesome/4.0.0/css/font-awesome.css">

        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">

        <!--<link href="HTMLStyle/theme.bootstrap.css" rel="stylesheet">-->

        <link href="HTMLStyle/HomePageStyle/css/bootstrap.min.css" rel="stylesheet">

        <script type="text/javascript" src="HTMLStyle/HomePageStyle/js/jquery.min.js"></script>
        <script type="text/javascript" src="HTMLStyle/HomePageStyle/js/bootstrap.min.js"></script>

      
    </head>

    <body>
        
         <div class="container">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <nav class="navbar navbar-default" role="navigation">
                        <div class="navbar-header">
                            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"> <span class="sr-only">Toggle navigation</span><span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span></button> <a class="navbar-brand" href="HomePage.jsp"><i class="fa fa-globe"></i>&nbsp;  GPSWebApp</a>
                        </div>

                        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                            <ul class="nav navbar-nav">
                                <li class="active">
                                    <a href="HomePage.jsp">Home</a>
                                </li>
                                <li>
                                    <a href="ShowTracks.jsp">My Tracks</a>
                                </li>
                                <li class="dropdown">
                                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">Create track<strong class="caret"></strong></a>
                                    <ul class="dropdown-menu">
                                        <li>
                                            <a href="UploadTrack1.jsp">Upload track</a>
                                        </li>

                                        <li class="divider">
                                        </li>
                                        <li>
                                            <a href="WriteTrack1.jsp">Write new track</a>
                                        </li>                                      
                                    </ul>
                                </li>
                            </ul>
                            <form action="FindResults.jsp" method="POST" class="navbar-form navbar-left" role="search" accept-charset="Windows-1250">
                                <div class="form-group">
                                    <input type="text" class="form-control home-search" name="finderText">
                                </div> <button type="submit" class="btn btn-default">Find</button>
                            </form>
                            <ul class="nav navbar-nav navbar-right">
                                <li>
                                    <a href="About.jsp">About</a>
                                </li>
                                <li class="dropdown">
                                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-user"></i>  Account<strong class="caret"></strong></a>
                                    <ul class="dropdown-menu">
                                        <li>
                                            <a href="ShowUserInfo.jsp">View account</a>
                                        </li>
                                        <li>
                                            <a href="EditAccount.jsp">Edit account</a>
                                        </li>
                                        <li>
                                            <a href="DeleteUser.jsp">Delete account</a>
                                        </li>
                                        <li class="divider">
                                        </li>
                                        <li>
                                            <a href="../Logout.jsp">Logout</a>
                                        </li>
                                    </ul>
                                </li>
                            </ul>
                        </div>

                    </nav>
                    <div class="carousel slide" id="carousel-646485" data-ride="carousel">
                        <ol class="carousel-indicators">
                            <li class="active" data-slide-to="0" data-target="#carousel-646485">
                            </li>
                            <li data-slide-to="1" data-target="#carousel-646485">
                            </li>
                            <li data-slide-to="2" data-target="#carousel-646485">
                            </li>
                        </ol>
                        <div class="carousel-inner">
                            <div class="item active">
                                <img alt="" src="HTMLStyle/HomePageStyle/car1.jpg">
                                <div class="carousel-caption">
                                    <h4>
                                        Release your soul...
                                    </h4>
                                    <p>
                                        ...conquer the highest mountain in the world...
                                    </p>
                                </div>
                            </div>
                            <div class="item">
                                <img alt="" src="HTMLStyle/HomePageStyle/car2.jpg">
                                <div class="carousel-caption">
                                    <h4>
                                        Discover the beauty of world...
                                    </h4>
                                    <p>
                                        ...take your bike and go...
                                    </p>
                                </div>
                            </div>
                            <div class="item">
                                <img alt="" src="HTMLStyle/HomePageStyle/car3.jpg">
                                <div class="carousel-caption">
                                    <h4>
                                        Take off to the sky...
                                    </h4>
                                    <p>
                                        ...get your paraglide and fly with the wind...
                                    </p>
                                </div>
                            </div>
                        </div> <a class="left carousel-control" href="#carousel-646485" data-slide="prev"><span class="glyphicon glyphicon-chevron-left"></span></a> <a class="right carousel-control" href="#carousel-646485" data-slide="next"><span class="glyphicon glyphicon-chevron-right"></span></a>
                    </div>
                    <div class="tabbable" id="tabs-883724">
                        <ul class="nav nav-tabs">
                            <li class="active">
                                <a href="#panel-234896" data-toggle="tab">Overview</a>
                            </li>
                            <li>
                                <a href="#panel-42569" data-toggle="tab">All added tracks</a>
                            </li>
                            <li>
                                <a href="#panel-42556" data-toggle="tab">Services for our users</a>
                            </li>
                        </ul>
                        <div class="tab-content">
                            <div class="tab-pane active" id="panel-234896">
                                
                                <h1>
				Welcome in GPS Web App!!!
                                </h1>
                                
                                <div class="row">
				<div class="col-md-4">
					<div class="thumbnail">
						<img alt="300x200" src="HTMLStyle/HomePageStyle/uplnewtrack.jpg" />
						<div class="caption">
							<h3>
								<i class="fa fa-file-o"></i>  Upload your track...
							</h3>
							<p>
								Upload track from your new trip or travel. You can add a lot multimedia file formats.
							</p>
							<p>
								<a class="btn btn-primary" href="UploadTrack1.jsp">Upload new track</a> 
							</p>
						</div>
					</div>
				</div>
				<div class="col-md-4">
					<div class="thumbnail">
						<img alt="300x200" src="HTMLStyle/HomePageStyle/drawnewtrack.jpg" />
						<div class="caption">
							<h3>
								<i class="fa fa-pencil"></i>  Draw your track...
							</h3>
							<p>
								Draw track of your unrecorded trip on our map and at next you can also add multimedia files.
							</p>
							<p>
								<a class="btn btn-primary" href="WriteTrack1.jsp">Write new track</a> 
							</p>
						</div>
					</div>
				</div>
				<div class="col-md-4">
					<div class="thumbnail">
						<img alt="300x200" src="HTMLStyle/HomePageStyle/explyoutracks.jpg" />
						<div class="caption">
							<h3>
								<i class="fa fa-list"></i>  Explore your tracks...
							</h3>
							<p>
								Explore your previously added trips and venues with relevant multimedia files.
							</p>
							<p>
								<a class="btn btn-primary" href="ShowTracks.jsp">Explore your tracks</a>
							</p>
						</div>
					</div>
				</div>
			</div>

                            </div>
                            <div class="tab-pane" id="panel-42569">

                                <h1>
				All added tracks...
                                </h1>
                                
                                <br>
                                <%
                                   
                                    DatabaseServices databaseServices = new DatabaseServices();
                                	List<Track> results = databaseServices.findNewNTracks(50);

                                    if (results.size() == 0) {
                                            out.print("<div class=\"alert alert-danger\">Sorry, system does not contains any tracks at this time...</div>");
                                        } else {
                                            out.println("<table id=\"myTable\" class= \"table \"><thead><tr><th>#</th><th>Uploaded</th><th>Track owner</th><th>Track name</th><th>Track access</th><th></th></tr></thead><tbody>");
											int i = 0;
                                            for (Track track : results) {  
                                            	i++;
                                                String modifiedDate = track.getTrackDateUpdated().toGMTString();

                                                out.println("<tr><td style=\"word-wrap: break-word;padding-top:12px;padding-bottom:12px;\">" + (i) + "</td><td style=\"word-wrap: break-word;padding-top:12px;\">" + modifiedDate + "</td><td style=\"word-wrap: break-word;padding-top:12px;\">" + track.getUser().getUserEmail() + "</td><td style=\"word-wrap: break-word;padding-top:12px;\">" + track.getTrackName() + "</td><td style=\"word-wrap: break-word;padding-top:12px;\">" + track.getTrackAccess() + "</td>"); 
                                                if(track.getTrackAccess().equalsIgnoreCase("Public") || track.getUser().getUserEmail().equalsIgnoreCase(session.getAttribute("username").toString())){
                                                    out.print("<td class=\"text-center\"><a href=ShowTrack.jsp?trkID=" + track.getTrackId() +  " class=\"btn btn-success btn-sm \">Show</a>");
                                                }
                                                out.print("<td class=\"text-center\"></td></tr>");

                                            }
                                            out.println("</tbody></table>");
                                        }
                                %>
                            </div>    
                            <div class="tab-pane" id="panel-42556">

                                <h1>
				Our web service provide...
                                </h1>
                                
                                <br>
                                
                                
                                <ul>
                                    <li>
                                        up to 10Gb space for user files
                                    </li>
                                    <li>
                                        adding tracklog from GPS device
                                    </li>
                                    <li>
                                        adding pictures (.jpeg, .jpg)
                                    </li>
                                    <li>
                                        adding multiple video formats
                                        <ul>
                                            <li>
                                            .mov
                                            </li>
                                            <li>
                                            .3gp / .3gpp
                                            </li>
                                            <li>
                                            .mp4
                                            </li>
                                            <li>
                                            .avi
                                            </li>
                                            <li>
                                            .mpg
                                            </li>
                                        </ul>
                                    </li>
                                    <li>
                                        drawing tracks on high quality map (Google Maps)
                                    </li>
                                    <li>
                                        drawing advanced altitude graph
                                    </li>
                                    <li>
                                        presentating entire trip with relevant multimedia files
                                    </li>
                                    <li>
                                        possiblity draw own track on map (unrecorded trip)
                                    </li>
                                    <li>
                                        user-friendly page interface
                                    </li>
                                    <li>
                                        inteligent searching on website
                                    </li>
                                </ul>

                                <br>

                            </div>
                        </div>
                    </div>

                    <p>
                        This website is developed as a practical Part of <strong>Ľubomír Petrus</strong> and <strong>Matej Pazdič</strong> thesis. Take note of the copyrights owns <em> Petrus </em> , <em> Pazdič </em> and at last but not least<em> Technical University of Košice, Faculty of Electrical Engineering and Informatics.</em> Thanks our close people for support.<b> Optimalized for <a href="http://chrome.google.com">Google Chrome</a> and <a href="http://www.opera.com">Opera</a> with <a href="http://get.adobe.com/flashplayer/">Adobe Flash plugin</a></b>
                    </p>
                </div>
            </div>
        </div>
        
    </body>
</html>


