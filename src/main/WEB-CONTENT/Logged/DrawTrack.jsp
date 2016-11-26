<%-- 
    Document   : DrawTrack
    Created on : 29.1.2014, 13:38:37
    Author     : eLeMeNt
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="Windows-1250">
        <title>Draw your track</title>

        <link rel="stylesheet" href="https://netdna.bootstrapcdn.com/font-awesome/4.0.0/css/font-awesome.css">

        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">

        <link href="HTMLStyle/HomePageStyle/css/bootstrap.min.css" rel="stylesheet">


        <script type="text/javascript" src="HTMLStyle/HomePageStyle/js/jquery.min.js"></script>
        <script type="text/javascript" src="HTMLStyle/HomePageStyle/js/bootstrap.min.js"></script>
        

        <style>
            //html,body {height: 100%}            
            
            #map_canvas {
                
                display: block;
                width: 100%;
            }

        </style>

        <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBH31FxBV_cLA7hdbY2dBTUsJjAaDEE0MI&sensor=true"></script>
        <script>

             $( document ) .ready(function() {                                       // povodne 710 Resize dorobeny
                 var height = ((screen.height.toString())/100)*65;
                 var heightSring = Math.round(height) + "px";
                 document.getElementById('map_canvas').style.height=heightSring;
             });
             
             
             
            var isPolyCr = false;
            var polylineOK = null;
            var list = new Array();

            function initialize() {
                var mapOptions = {
                    zoom: 17,
                    center: new google.maps.LatLng(48.730861,21.244630),
                    mapTypeId: google.maps.MapTypeId.HYBRID
                    };
                    
                var map = new google.maps.Map(document.getElementById('map_canvas'),mapOptions);
                
                polylineOK = new google.maps.Polyline({
                         path: list,
                         strokeColor: '#FF0000',
                         geodesic: true,
                         strokeOpacity: 1.0,
                         strokeWeight: 2,
                         editable: true
                         });
                polylineOK.setMap(map);

                google.maps.event.addListener(map, 'click', function(e) {

                
                placeMarker(e.latLng, map);
                });
            }

           function placeMarker(position, map) {

                list = polylineOK.getPath();
                map.panTo(position);
                list.push(position);
 
                
                if (list.length > 1) {
                    document.getElementById("nextStep").disabled = false;
                }
            }
            
            function submitTrack() {
                    document.getElementById("textBox").value=list.getArray();
                    document.forms["index"].submit();
                }

       google.maps.event.addDomListener(window, 'load', initialize);
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
                    
                    <div class="tabbable" id="tabs-883724">
                        <ul class="nav nav-tabs">
                            <li class="active">
                                <a href="#panel-234896" data-toggle="tab">Draw track</a>
                            </li>
                        </ul>
                        <div class="tab-content">
                            <div class="tab-pane active" id="panel-234896">

                                        					                                            
                    <h3> Draw your track on map </h3>
                    <br>

                    <div id="map_canvas"></div>
                    <form name="index" action="WriteNewTrack" method="post">
                        <input type="hidden" id="textBox" name="textBox"><br>

                        <!--<input type="Submit" />-->
                    </form>
                    <p style="line-height: 20px; text-align: center;"> <button id="nextStep" class="btn btn-default btn-success" onClick="submitTrack();" disabled > <% if (session.getAttribute("isMultimedia") != null) { out.print("Final step"); } else {out.print("Finish");} %></button></p>
                    </div>
                  </div>
               </div>
            </div>
         </div>
       </div>
    </body>
</html>
