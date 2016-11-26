<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    session.removeAttribute("trackFilename");
    session.removeAttribute("trackName");
    session.removeAttribute("trackDescr");
    session.removeAttribute("trackActivity");
    session.removeAttribute("access");
    session.removeAttribute("trackNameExist");
%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="Windows-1250">
        <title>Upload track</title>

        <link rel="stylesheet" href="https://netdna.bootstrapcdn.com/font-awesome/4.0.0/css/font-awesome.css">

        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">

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
                                <li>
                                    <a href="HomePage.jsp">Home</a>
                                </li>
                                <li>
                                    <a href="ShowTracks.jsp">My Tracks</a>
                                </li>
                                <li class="dropdown active">
                                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">Upload track<strong class="caret"></strong></a>
                                    <ul class="dropdown-menu">
                                        <li>
                                            <a href="UploadFile.jsp">Upload track only</a>
                                        </li>
                                        <li>
                                            <a href="UploadTrack1.jsp">Upload track with multimedia files</a>
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
                                    <a href="#">About</a>
                                </li>
                                <li class="dropdown">
                                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-user"></i>  Account<strong class="caret"></strong></a>
                                    <ul class="dropdown-menu">
                                        <li>
                                            <a href="#">View account</a>
                                        </li>
                                        <li>
                                            <a href="#">Edit account</a>
                                        </li>
                                        <li>
                                            <a href="#">Delete account</a>
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
                    <div class="tabbable" id="tabs-883724">
                        <ul class="nav nav-tabs">
                            <li class="active">
                                <a href="#panel-234896" data-toggle="tab">Track upload</a>
                            </li>
                        </ul>
                        <div class="tab-content">
                            <div class="tab-pane active" id="panel-234896">

                                <h3>
                                    Upload your track
                                </h3>
                                <br>

                                <div class="container">
                                    <div class="row clearfix">
                                        <div class="col-md-4 column"></div>
                                        <div class="col-md-4 column">
                                            <form action="Upload" method="post" enctype="multipart/form-data">
                                                <div class="form-group">
                                                    <label for="TrackName">Track name</label><input type="text" name="name" required="required" class="form-control" id="exampleInputEmail1" />
                                                    
                                                </div>
                                                <div class="form-group">
                                                    <label for="TrackDesc">Track description</label><textarea class="form-control" name="descr" rows="3" id="exampleInputEmail1"></textarea>
                                                    
                                                </div>
                                                <div class="form-group">
                                                    <label for="TrackActivity">Activity</label> 
                                                    <select name="Activity" class="form-control">
                                                    <option value="Hiking">Hiking</option>
                                                    <option value="Cycling">Cycling</option>
                                                    <option value="Paragliding">Paragliding</option>
                                                    <option value="Road tripping">Road tripping</option>
                                                    <option value="Skiing">Skiing</option>
                                                    <option value="Canoeing">Canoeing</option>
                                                    <option value="Sailing">Sailing</option>
                                                    <option value="Flying">Flying</option>
                                                    </select>                                                    
                                                </div>
                                                
                                                <div class="form-group">
                                                    <label for="InputFileGps">Input track file</label><input type="file" name="file" accept=".gpx, .GPX" required="required" id="exampleInputFile" />
                                                    <br>
                                                    <p class="help-block"> Take note, in this time is only .gpx file supported!!!</p>
                                                    <br>
                                                    </div> <p style="line-height: 20px; text-align: center;"> <button type="submit" class="btn btn-default btn-success ">Submit</button></p>
                                            </form>
                                        </div>
                                        <div class="col-md-4 column"></div>
                                    </div>
                                </div>
                            </div>
                           

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>


