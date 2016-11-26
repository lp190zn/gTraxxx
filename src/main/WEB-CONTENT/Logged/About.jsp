<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    session.removeAttribute("trackFilename");
    session.removeAttribute("trackName");
    session.removeAttribute("trackDescr");
    session.removeAttribute("trackActivity");
    session.removeAttribute("access");
    session.removeAttribute("trackNameExist");
    session.removeAttribute("isMultimedia");
  
    String isUser = session.getAttribute("Admin").toString();
    
    String logLink;
    String clearLogLink;
    String system = System.getProperty("os.name");
    
    if (system.startsWith("Windows")) {
        logLink = "http://localhost:8080/GPSWebApp/Logged/uploaded_from_server/GPSWebAppLog.log";
        clearLogLink = "http://localhost:8080/GPSWebApp/Logged/ClearFileLog";
    } else {
        logLink = "http://gps.kpi.fei.tuke.sk/Logged/uploaded_from_server/GPSWebAppLog.log";
        clearLogLink = "http://gps.kpi.fei.tuke.sk/Logged/ClearFileLog";
    }
%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>About</title>

        <link rel="stylesheet" href="https://netdna.bootstrapcdn.com/font-awesome/4.0.0/css/font-awesome.css">

        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">

        <link href="HTMLStyle/HomePageStyle/css/bootstrap.min.css" rel="stylesheet">

        <script type="text/javascript" src="HTMLStyle/HomePageStyle/js/jquery.min.js"></script>
        <script type="text/javascript" src="HTMLStyle/HomePageStyle/js/bootstrap.min.js"></script>
        
        <script>  
        
        var isUser = "<% out.print(isUser.toString());%>";
        
        if (isUser.toString() === "True") {
            $( document ).ready(function() {document.getElementById('admin').style.display="inline";});
        }


        </script>
        
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
                                <li class="active">
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
                    <div class="tabbable" id="tabs-883724">
                        <ul class="nav nav-tabs">
                            <li class="active">
                                <a href="#panel-234896" data-toggle="tab">About GPSWebApp</a>
                            </li>
                        </ul>
                        

                                <h3>
                                    <b>GPSWebApp v. 1.00</b>
                                </h3>

                                 <div class="row clearfix">
                                        <div class="col-md-4 column">
                                            <div class="text-center">
                                                <br>
                                                <img id="kpi" name="img" src="HTMLStyle/kpi.png" alt="..." class="img-rounded" style="height:120px;width:120px">
                                                
                                            </div>
                                        </div>
                                        
                                        
                                        <div class="col-md-4 column">
                                            <div class="text-center">
                                                <br>
                                                <img id="fei" name="img" src="HTMLStyle/fei.png" alt="..." class="img-rounded" style="height:120px;width:120px">
                                                
                                            
                                        </div>
                                        </div>
                                        <div class="col-md-4 column">
                                        <div class="text-center">
                                                <br>
                                                <img id="tuke" name="img" src="HTMLStyle/tuke.png" alt="..." class="img-rounded" style="height:120px;width:120px">
                                                
                                            
                                        </div>
                                        </div>
                                     </div>
                        
                                 <br>
           
                                    <h4>This website is developed as a practical part of Ľubomír Petrus and Matej Pazdič thesis. 
                                        Take note of the copyrights owns Petrus, Pazdič and at last but not least Technical University of Košice, 
                                        Faculty of Electrical Engineering and Informatics.</h4>
                                    
                                                                   
                                    <div class="row clearfix">
                                        <div class="col-md-6 column">
                                                <dl>
                                                    <dt>Web design, front-end functionality, map services and showing, drawing and presentating tracks with related multimedia files: </dt>
                                                        <dd><h5> <b>Thesis:</b> <br>Services for Publication a Presentation GPS Records with Multimedia Information </h5></dd>
                                                    
                                                    <dt>Author:</dt>
                                                    <dd><h5>Ľubomír Petrus</h5></dd>
                                        
                                                    <dt>Supervisor:</dt>
                                                    <dd><h5>doc. Ing. Zdeněk Havlice, CSc.</h5></dd>
                                                </dl>
                                        </div>
                                        <div class="col-md-6 column">
                                            <dl>
                                                    <dt>Back-end design, processing input files, generating output files, designing and managment of database: </dt>
                                                        <dd><h5> <b>Thesis:</b> <br>Services for Processing and Storage GPS Records with Multimedia Information </h5></dd>
                                                    <dt>Author:</dt>
                                                    <dd><h5>Matej Pazdič</h5></dd>
                                               
                                                    <dt>Supervisor:</dt>
                                                    <dd><h5>doc. Ing. Zdeněk Havlice, CSc.</h5></dd>
                                                </dl>
                                        </div>
                                    
                                      
                                   
                                
                            </div>
                        </div>
                                   
                                    <div class="tabbable" id="admin" style="display:none">
                                        <ul class="nav nav-tabs">
                                            <li class="active">
                                            <a href="#panel-234666" data-toggle="tab">Admin</a>
                                        </li>
                                         </ul>
                                    <div class="tab-content">
                                        <div class="tab-pane active" id="panel-234666">

                                    <div class="row clearfix">
                                        <div class="col-md-6 column">
                                            <h3>
                                            <b>Whats new in v. 1.00 »</b>
                                        </h3>
                                                <ul>
                                                         <li>Restore your forgotten password</li>
                                                         <li>Generate publication in PDF fromat from your track</li>
                                                         <li>Added dynamic presentation mode</li>
                                                         <li>Added centered dynamic presentation mode</li>
                                                         <li>Generate .gpx tracklog file from your track</li>
                                                         <li>Added list of all tracks in application</li>
                                                         <li>Added new hint popups for buttons</li>
                                                         <li>Added track filtering in My Tracks page</li>
                                                         <li>Fixed recognizing wrong characters in filenames</li>
                                                         <li>Optimalized algorithm for track drawing</li>                                            
                                                         
                                                </ul>
                                    </div>

                                    <div class="col-md-6 column">
                                        
                                        <h3>
                                            <b>Admin Section</b>
                                            </h3>    
                                            
                                                <p>
                                                    <a href="https://github.com/matejpazdic/GPSWebApp">GPSWebApp project on GitHub »</a>
                                                </p>
                                    
                                                <p>
                                                    <a href="<% out.print(logLink);%>">GPSWebApp Log »</a>
                                                </p>
                                                
                                                <p>
                                                    <a href="<% out.print(clearLogLink);%>">Clear GPSWebApp Log »</a>
                                                </p>
                                        
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