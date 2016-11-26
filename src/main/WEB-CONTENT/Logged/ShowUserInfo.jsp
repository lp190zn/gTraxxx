<%@page import="java.util.ArrayList"%>
<%@page import="sk.mlp.database.DatabaseServices"%>
<%@page import="sk.mlp.ui.model.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    session.removeAttribute("trackFilename");
    session.removeAttribute("trackName");
    session.removeAttribute("trackDescr");
    session.removeAttribute("trackActivity");
    session.removeAttribute("access");
    session.removeAttribute("trackNameExist");
    session.removeAttribute("isMultimedia");
    
    DatabaseServices databaseServices = new DatabaseServices();
    User user = databaseServices.findUserByEmail(session.getAttribute("username").toString());

    String usrage, usractivity = null;

     if (user.getUserAge()==-1) {
         usrage = "";
     } else {usrage = user.getUserAge().toString();}
     
 
     
     if (user.getUserActivity() == null) {
         usractivity = "";
     } else {usractivity = user.getUserActivity();}
  
%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="Windows-1250">
        <title>Account info</title>

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
                                <li class="dropdown active">
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
                                <a href="#panel-234896" data-toggle="tab">Account data</a>
                            </li>
                        </ul>
                        <div class="tab-content">
                            <div class="tab-pane active" id="panel-234896">

                                <h3>
                                    View account
                                </h3>
                                <br>

                                <div class="container">
                                    <div class="row">
                                        <div>
                                    
                                    <form class="form-horizontal" role="form">
                                        <div class="form-group">
                                          <label class="col-md-6 control-label col-sm-pad" style="color: red;" >Email</label>
                                          <div class="col-md-1 col-sm-pad">
                                              <p class="form-control-static"><% out.print(user.getUserEmail());%></p>
                                          </div>
                                        </div>
                                        <div class="form-group">
                                          <label class="col-md-6 control-label col-sm-pad">First name</label>
                                          <div class="col-md-1 col-sm-pad">
                                            <p class="form-control-static"><% out.print(user.getUserFirstName());%></p>
                                          </div>
                                        </div>
                                        <div class="form-group">
                                          <label class="col-md-6 control-label">Last name</label>
                                          <div>
                                            <p class="col-md-1 form-control-static"><% out.print(user.getUserLastName());%></p>
                                          </div>
                                        </div>
                                        <div class="form-group">
                                          <label class="col-md-6 control-label">Age</label>
                                          <div>
                                            <p class="col-md-1 form-control-static"><% out.print(usrage);%></p>
                                          </div>
                                        </div>
                                        <div class="form-group">
                                          <label class="col-md-6 control-label">Favourite activity</label>
                                          <div>
                                            <p class="col-md-1 form-control-static"><% out.print(usractivity);%></p>
                                          </div>
                                        </div>
                                        <div class="form-group">
                                          <label class="col-md-6 control-label">Account type</label>
                                          <div>
                                            <p class="col-md-1 form-control-static"><% out.print(user.getUserStatus());%></p>
                                          </div>
                                        </div>
                                        </form>
                                                <br>
                                             <p style="line-height: 20px; text-align: center;"> <a class="btn btn-default btn-success" href="EditAccount.jsp">Edit account</a> </p>
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


