
<%@page import="sk.mlp.util.Constants"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.io.File"%>
<%@page import="java.util.ArrayList"%>
<%@page import="sk.mlp.database.DatabaseServices"%>
<%@page import="sk.mlp.ui.model.User"%>
<%@page import="sk.mlp.parser.TLVLoader"%>
<%@page import="sk.mlp.ui.model.Track"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
	session.removeAttribute("trackFilename");
	session.removeAttribute("trackName");
	session.removeAttribute("trackDescr");
	session.removeAttribute("trackActivity");
	session.removeAttribute("access");
	session.removeAttribute("trackNameExist");
	session.removeAttribute("isMultimedia");
%>
<!DOCTYPE html>

<%
	TLVLoader loader = new TLVLoader();
	String system = System.getProperty("os.name");
	int trkID = Integer.parseInt(request.getParameter("trkID"));
	DatabaseServices databaseServices = new DatabaseServices();
	Track track = databaseServices.findTrackById(trkID);
	String path = track.getFile();
	String file = track.getName();

	if (system.startsWith("Windows")) {
		path = path.replaceAll("/", "\\\\");
	}
	loader.readTLVFile(path, file);
%>
<html lang="en">
<head>
<meta charset="Windows-1250">
<title>
	<%
		out.print("Track: " + file);
	%>
</title>

<link rel="stylesheet"
	href="https://netdna.bootstrapcdn.com/font-awesome/4.0.0/css/font-awesome.css">

<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<link href="HTMLStyle/HomePageStyle/css/bootstrap.min.css"
	rel="stylesheet">
<link href="HTMLStyle/SliderStyle/css/slider.css" rel="stylesheet">

<link type="text/css" rel="stylesheet"
	href="HTMLStyle/GalleryStyle/themes/classic/galleria.classic.css">

<script type="text/javascript"
	src="HTMLStyle/HomePageStyle/js/jquery.min.js"></script>
<script type="text/javascript"
	src="HTMLStyle/HomePageStyle/js/bootstrap.min.js"></script>
<script type="text/javascript" src="HTMLStyle/HomePageStyle/js/tab.js"></script>


<!--<script src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.js"></script>-->

<script src="HTMLStyle/GalleryStyle/galleria-1.3.3.min.js"></script>
<script type="text/javascript"
	src="HTMLStyle/GalleryStyle/themes/classic/galleria.classic.min.js"></script>
<script type="text/javascript"
	src="HTMLStyle/SliderStyle/js/bootstrap-slider.js"></script>
<script type="text/javascript"
	src='https://www.google.com/jsapi?autoload={"modules":[{"name":"visualization","version":"1","packages":["corechart"]}]}'></script>


<style>
#map_canvas {
	display: block;
	width: 100%;
	height: 550px;
}
</style>



<script
	src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBH31FxBV_cLA7hdbY2dBTUsJjAaDEE0MI&sensor=true"></script>
<script>

                    var map_options = {
                    mapTypeId: google.maps.MapTypeId.HYBRID
                    };
                    
                    var map;
                    var polylineOK = null;
                    var isEnd = false;
                    var isPresented = false;
                    var index = 0;
                    var a = 0;
                    var marker;
                    
                    var multimediaMarker;
                    
                    var chart;

                    var leadMarker;
                    
                    var polylineCoordinatesListFinal = [];
                    var isPolylineAlreadyCreated = false;

                    var options;
                    
                    var isAlreadyMark = false;
                    var mark;
                    
                    var photoTimeout;
                    var presentTimeout;
                    
                    var isVideoShowed = false;
                    var isPlayerReady = false;
                    var isVideoPaused = false;
                    
                    ///////////////////////////////////// OPTIONS
                    
                    var Mode = 2;
                    
                    var presentationSpeed = 35;
                    var pictureShowingTime = 5000;
                    var lastPictureStayShowed = false;
                    
                    var polyLineColor = '#FF0000';
                    var polyLinePresentationColor = '#FF0000';
                    var strokeWeight = 2;
                    var strokeOpacity = 1.0;
                    
                    var emptyGraph = false;
                    
                    var graphData = null;
                    
                    var implicitGraph = 0;
                    var isDeviceElevations = false;
                    var isElevationsOnMap = false;
                    
                    var image = 'HTMLStyle/TrackPointIcon/BluePin1.png';
                    var image1 = 'HTMLStyle/TrackPointIcon/RedPin1.png';
                    var image2 = 'HTMLStyle/TrackPointIcon/GreenPin1.png';
                    
                    var boundsForMode;
                    
                    var markersArray = [];
                    
                    
                    /////////////////////////////////////
                    
                    var tag = document.createElement('script');
                    tag.src = "https://www.youtube.com/iframe_api";
                    var firstScriptTag = document.getElementsByTagName('script')[0];
                    firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);
            
                    var player;
                            function onYouTubeIframeAPIReady() {
                                player = new YT.Player('ytplayer', {
                                  playerVars: {
                                    autohide: "1",
                                    modestbranding: "1",
                                    rel: "0",
                                    showinfo: "0",
                                    iv_load_policy: "3",
                                    //origin: "http://gps.kpi.fei.tuke.sk",
                                    videoId: "M7lc1UVf-VE",
                                    theme: "light"
                                  },
                                  height: '275',
                                  width: '100%',
                           
                                  events: {
                                    'onReady': onPlayerReady,
                                    'onStateChange': onPlayerStateChange
                                  }
                                });
                              }
                            
                            function onPlayerReady(event) {
                                //event.target.playVideo();
                                isPlayerReady = true;
                                document.getElementById("play").disabled = false;
                            }
                            
                            function onPlayerStateChange(event) {
                                    if (event.data == YT.PlayerState.ENDED) {
                                      next();                                     
                                    }
                            }
                    
            <%out.print("var creationType = \"" + track.getType() + "\";\n\n");

			out.print("var trackType = \"" + track.getActivity() + "\";\n\n");

			out.print("var polylineCoordinatesList = [\n");
			for (int i = 0; i < loader.getTrackPoints().size(); i++) {
				out.print("new google.maps.LatLng(" + loader.getTrackPoints().get(i).getLatitude() + ", "
						+ loader.getTrackPoints().get(i).getLongitude() + ")");
				if (i != loader.getTrackPoints().size() - 1) {
					out.println(",");
				}
			}
			out.print("\n];");

			out.print("var isFiles = [\n");
			for (int i = 0; i < loader.getTrackPoints().size(); i++) {
				out.print(loader.getIsFiles()[i]);
				if (i != loader.getTrackPoints().size() - 1) {
					out.println(",");
				}
			}
			out.print("\n];");

			out.print("\n\n var times = [\n");
			for (int i = 0; i < loader.getTrackPoints().size(); i++) {
				out.print("\"" + loader.getTrackPoints().get(i).getTime().toString().substring(11, 19) + "\"");
				if (i != loader.getTrackPoints().size() - 1) {
					out.println(",");
				}
			}
			out.print("\n];");

			out.print("\nvar filesPath = [\n");
			for (int i = 0; i < loader.getMultimediaFiles().size(); i++) {
				String temp = null;

				if (System.getProperty("os.name").startsWith("Windows")) {
					File f = new File(loader.getMultimediaFiles().get(i).getPath());
					temp = f.toURI().toString().substring(f.toURI().toString().lastIndexOf("/Logged/") + 8);
				} else {
					if (!loader.getMultimediaFiles().get(i).getPath().toString().contains("YTB")) {
						String temp1 = loader.getMultimediaFiles().get(i).getPath().substring(38);
						temp = temp1.replaceAll(" ", "%20");
					}
				}

				String newPath = null;

				if (!loader.getMultimediaFiles().get(i).getPath().toString().contains("YTB")) {

					String extension = temp.substring(temp.lastIndexOf("."), temp.length());
					newPath = temp.substring(0, temp.lastIndexOf(".")) + "_THUMB" + extension;

				} else {

					if (System.getProperty("os.name").startsWith("Windows")) {
						newPath = temp.substring(temp.length() - 17);
						newPath = newPath.replaceAll("%20", " ");
					} else {
						newPath = loader.getMultimediaFiles().get(i).getPath();
					}

				}
				//System.out.print("ORIGINAL: " + temp);
				//String temp1 = temp.replaceAll("\\\\", "\\\\\\\\");
				//String temp2 = temp1.replaceAll("/", "\\\\\\\\");
				//System.out.print("NEW: " + temp1);
				out.print("\"" + newPath + "\"");
				if (i != loader.getMultimediaFiles().size() - 1) {
					out.println(",");
				}
			}
			out.print("\n];");

			out.print("\nvar filesPoints = [\n");
			for (int i = 0; i < loader.getMultimediaFiles().size(); i++) {
				out.print(loader.getMultimediaFiles().get(i).getTrackPointIndex());
				if (i != loader.getMultimediaFiles().size() - 1) {
					out.println(",");
				}
			}
			out.print("\n];");

			out.print("var elevationsOnMap = [\n");
			for (int i = 0; i < loader.getTrackPoints().size(); i++) {
				out.print(loader.getTrackPoints().get(i).getInternetElevation());
				if (i != loader.getTrackPoints().size() - 1) {
					out.println(",");
				}
			}
			out.print("\n];");

			out.print("var deviceElevations = [\n");
			for (int i = 0; i < loader.getTrackPoints().size(); i++) {
				out.print(loader.getTrackPoints().get(i).getDeviceElevation());
				if (i != loader.getTrackPoints().size() - 1) {
					out.println(",");
				}
			}
			out.print("\n];");

			out.print("var speeds = [\n");
			for (int i = 0; i < loader.getTrackPoints().size(); i++) {
				out.print(loader.getTrackPoints().get(i).getSpeed());
				if (i != loader.getTrackPoints().size() - 1) {
					out.println(",");
				}
			}
			out.print("\n];");%>
	var filesPointsUnique = [];
	$.each(filesPoints, function(i, el) {
		if ($.inArray(el, filesPointsUnique) === -1)
			filesPointsUnique.push(el);
	});

	function clearOverlays() {
		for (var i = 0; i < markersArray.length; i++) {
			markersArray[i].setMap(null);
		}
		markersArray.length = 0;
	}

	//var graphData = google.visualization.arrayToDataTable(gData);
	graphData = new google.visualization.DataTable();

	if (creationType === "Drawed") {
		if (elevationsOnMap[0] == 0) {
			implicitGraph = 1;
			setGraph1();
		} else {
			implicitGraph = 2;
			isElevationsOnMap = true;
			setGraph2();
		}
	} else if (elevationsOnMap[0] == 0) {
		implicitGraph = 3;
		isDeviceElevations = true;
		setGraph3();
	} else if (trackType.substring(0, 3) === "Air") {
		implicitGraph = 4;
		isDeviceElevations = true;
		isElevationsOnMap = true;
		setGraph4();
	} else {
		implicitGraph = 5;
		isElevationsOnMap = true;
		isDeviceElevations = true;
		setGraph5();
	}

	function setGraph1() {

		graphData.addColumn('number', '');

		alert("Sorry, but we cant draw elevation chart because elevation server wasn't return values and device elevation not exist (Drawed track) - For view elevation graph you must add (draw) yout track again!!!");

		graphData.addColumn('number', 'Device elevation');

		for (i = 0; i < polylineCoordinatesList.length; i++) {

			graphData.addRow([ i, deviceElevations[i] ]);
		}

		options = {
			chartArea : {
				left : 50,
				top : 35,
				height : "70%",
				width : "100%"
			},
			hAxis : {
				gridlines : {
					count : 0
				},
				textPosition : 'none',
				title : '',
				titleTextStyle : {
					color : '#666'
				}
			},
			vAxis : {
				gridlines : {
					count : 8
				},
				textStyle : {
					fontSize : "12",
					bold : true
				}
			},
			legend : {
				position : "top",
				alignment : "center",
				textStyle : {
					bold : true,
					fontSize : "12"
				}
			},
			colors : [ 'blue' ],
			vAxes : [ /// odtialto
			{
				titleTextStyle : {
					color : '#FF0000'
				}
			}, // Left axis
			{
				title : '',
				titleTextStyle : {
					color : '#FF0000'
				}
			} // Right axis
			],
			series : [ {
				targetAxisIndex : 0
			}, ],
		};

		$(document).ready(function() {
			document.getElementById('chart_div').style.display = "none";
			var tag = document.createElement('br');
			document.getElementById('empty_div').appendChild(tag);
		});

	}

	function setGraph2() {

		graphData.addColumn('number', '');

		graphData.addColumn('number', 'Elevation on map');
		graphData.addColumn({
			type : 'string',
			role : 'tooltip'
		});

		for (i = 0; i < polylineCoordinatesList.length; i++) {

			graphData.addRow([ i, elevationsOnMap[i],
					"Elevation on the map: " + elevationsOnMap[i].toString() ]);
		}

		options = {
			chartArea : {
				left : 48,
				top : 35,
				height : "70%",
				width : "100%"
			},
			hAxis : {
				gridlines : {
					count : 0
				},
				textPosition : 'none',
				title : '',
				titleTextStyle : {
					color : '#666'
				}
			},
			vAxis : {
				gridlines : {
					count : 8
				},
				textStyle : {
					fontSize : "12",
					bold : true
				}
			},
			legend : {
				position : "top",
				alignment : "center",
				textStyle : {
					bold : true,
					fontSize : "12"
				}
			},
			colors : [ 'blue' ],
			vAxes : [ /// odtialto
			{
				titleTextStyle : {
					color : '#FF0000'
				},
				format : '# m'
			}, // Left axis
			{
				title : '',
				titleTextStyle : {
					color : '#FF0000'
				}
			} // Right axis
			],
			series : [ {
				targetAxisIndex : 0
			}, ],
		};
	}

	function setGraph3() {

		graphData.addColumn('number', '');

		graphData.addColumn('number', 'Device elevation');
		graphData.addColumn({
			type : 'string',
			role : 'tooltip'
		});

		graphData.addColumn('number', 'Speed');
		graphData.addColumn({
			type : 'string',
			role : 'tooltip'
		});

		for (i = 0; i < polylineCoordinatesList.length; i++) {

			graphData.addRow([
					i,
					deviceElevations[i],
					"Time: " + times[i] + "\nDevice elevation: "
							+ deviceElevations[i].toString(),
					speeds[i],
					"Time: " + times[i] + "\nSpeed: " + speeds[i].toString()
							+ "km/h" ]);
		}

		options = {
			chartArea : {
				left : 48,
				top : 35,
				height : "70%",
				width : "90%"
			},
			hAxis : {
				gridlines : {
					count : 0
				},
				textPosition : 'none',
				title : '',
				titleTextStyle : {
					color : '#666'
				}
			},
			vAxis : {
				gridlines : {
					count : 8
				},
				textStyle : {
					fontSize : "12",
					bold : true
				}
			},
			legend : {
				position : "top",
				alignment : "center",
				textStyle : {
					bold : true,
					fontSize : "12"
				}
			},
			colors : [ 'green', 'red' ],
			vAxes : [ /// odtialto
			{
				titleTextStyle : {
					color : '#FF0000'
				},
				format : '# m'
			}, // Left axis
			{
				title : '',
				titleTextStyle : {
					color : '#FF0000'
				},
				format : '# km/h'
			} // Right axis
			],
			series : [ {
				targetAxisIndex : 0
			}, {
				targetAxisIndex : 1
			}

			],
		};
	}

	function setGraph4() {

		graphData.addColumn('number', '');

		graphData.addColumn('number', 'Device elevation');
		graphData.addColumn({
			type : 'string',
			role : 'tooltip'
		});
		graphData.addColumn('number', 'Elevation on map');
		graphData.addColumn({
			type : 'string',
			role : 'tooltip'
		});
		graphData.addColumn('number', 'Speed');
		graphData.addColumn({
			type : 'string',
			role : 'tooltip'
		});

		for (i = 0; i < polylineCoordinatesList.length; i++) {

			graphData.addRow([
					i,
					deviceElevations[i],
					"Time: " + times[i] + "\nDevice elevation: "
							+ deviceElevations[i].toString(),
					elevationsOnMap[i],
					"Time: " + times[i] + "\nElevation on the map: "
							+ elevationsOnMap[i].toString(),
					speeds[i],
					"Time: " + times[i] + "\nSpeed: " + speeds[i].toString()
							+ "km/h" ]);

		}

		options = {
			chartArea : {
				left : 48,
				top : 35,
				height : "70%",
				width : "90%"
			},
			hAxis : {
				gridlines : {
					count : 0
				},
				textPosition : 'none',
				title : '',
				titleTextStyle : {
					color : '#666'
				}
			},
			vAxis : {
				gridlines : {
					count : 8
				},
				textStyle : {
					fontSize : "12",
					bold : true
				}
			},
			legend : {
				position : "top",
				alignment : "center",
				textStyle : {
					bold : true,
					fontSize : "12"
				}
			},
			colors : [ 'green', 'blue', 'red' ],
			vAxes : [ /// odtialto
			{
				titleTextStyle : {
					color : '#FF0000'
				},
				format : '# m'
			}, // Left axis
			{
				title : '',
				titleTextStyle : {
					color : '#FF0000'
				},
				format : '# km/h'
			} // Right axis
			],
			series : [ {
				targetAxisIndex : 0
			}, {
				targetAxisIndex : 0
			}, {
				targetAxisIndex : 1
			} ]
		};
	}

	function setGraph5() {

		graphData.addColumn('number', '');

		graphData.addColumn('number', 'Elevation on map');
		graphData.addColumn({
			type : 'string',
			role : 'tooltip'
		});

		graphData.addColumn('number', 'Speed');
		graphData.addColumn({
			type : 'string',
			role : 'tooltip'
		});

		for (i = 0; i < polylineCoordinatesList.length; i++) {

			graphData.addRow([
					i,
					elevationsOnMap[i],
					"Time: " + times[i] + "\nElevation on the map: "
							+ elevationsOnMap[i].toString(),
					speeds[i],
					"Time: " + times[i] + "\nSpeed: " + speeds[i].toString()
							+ "km/h" ]);
		}

		options = {
			chartArea : {
				left : 48,
				top : 35,
				height : "70%",
				width : "90%"
			},
			hAxis : {
				gridlines : {
					count : 0
				},
				textPosition : 'none',
				title : '',
				titleTextStyle : {
					color : '#666'
				}
			},
			vAxis : {
				gridlines : {
					count : 8
				},
				textStyle : {
					fontSize : "12",
					bold : true
				}
			},
			legend : {
				position : "top",
				alignment : "center",
				textStyle : {
					bold : true,
					fontSize : "12"
				}
			},
			colors : [ 'blue', 'red' ],
			vAxes : [ /// odtialto
			{
				titleTextStyle : {
					color : '#FF0000'
				},
				format : '# m'
			}, // Left axis
			{
				title : '',
				titleTextStyle : {
					color : '#FF0000'
				},
				format : '# km/h'
			} // Right axis
			],
			series : [ {
				targetAxisIndex : 0
			}, {
				targetAxisIndex : 1
			} ],
		};
	}

	function initialize() {

		isAlreadyMark = false;
		isPresented = false;
		var bounds = new google.maps.LatLngBounds();

		for (var i = 0; i < polylineCoordinatesList.length; i++) {
			bounds.extend(polylineCoordinatesList[i]);
		}

		map_canvas = document.getElementById('map_canvas');

		map = new google.maps.Map(map_canvas, map_options);

		polylineOK = new google.maps.Polyline({
			path : polylineCoordinatesList,
			strokeColor : polyLineColor,
			geodesic : true,
			strokeOpacity : strokeOpacity,
			strokeWeight : strokeWeight,
			editable : false
		});

		polylineOK.setMap(map);
		map.fitBounds(bounds);

		for (i = 0; i < filesPointsUnique.length; i++) {

			multimediaMarker = new google.maps.Marker({
				position : polylineCoordinatesList[filesPointsUnique[i]],
				map : map,
				icon : image1,
				title : ''
			});

			multimediaMarker.setMap(map);
			markersArray.push(multimediaMarker);
		}

		drawChart();

	}

	function draw() {

		if (a == polylineCoordinatesList.length) { /// NOVY KOD
			clearmap();
			return;
		} ///

		document.getElementById("play").disabled = true;
		document.getElementById("pause").disabled = false;
		document.getElementById("stop").disabled = false;

		isPresented = true;

		if (marker) {
			marker.setMap(null);
		}

		clearTimeout(presentTimeout); //  SKUSKAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAa

		if (isPolylineAlreadyCreated == false) {

			if (isAlreadyMark == true) {
				mark.setMap(null);
			}

			if (markersArray.length != 0) {
				clearOverlays();
			}

			polylineOK.setPath([]);
			polylineOK.setMap(null);

			a = 0;
			i = 0;

			polylineOK = new google.maps.Polyline({
				path : polylineCoordinatesListFinal,
				strokeColor : polyLinePresentationColor,
				geodesic : true,
				map : map, //zmenil som to
				strokeOpacity : strokeOpacity,
				strokeWeight : strokeWeight,
				editable : false
			});
			isEnd = false;

			leadMarker = new google.maps.Marker({
				position : polylineCoordinatesList[a],
				map : map,
				icon : image1
			});

			isPolylineAlreadyCreated = true;

			if (Mode == 1) {
				map.setZoom(16);
				//map.setOptions({zoomControl: false,scrollwheel: false});
			}

			boundsForMode = new google.maps.LatLngBounds();

		}

		function drawingMap() {

			clearTimeout(presentTimeout); //  SKUSKAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAa

			polylineCoordinatesListFinal.push(polylineCoordinatesList[a]);
			polylineOK.setPath(polylineCoordinatesListFinal);

			boundsForMode.extend(polylineCoordinatesList[a]);
			//polylineOK.setMap(map);                                   //zmenil som to

			if (Mode == 1) {
				map.panTo(polylineCoordinatesList[a]);
			} else if (Mode == 2) {
				leadMarker.setPosition(polylineCoordinatesList[a]);
			} else if (Mode == 3) {
				leadMarker.setPosition(polylineCoordinatesList[a]);
				if (!map.getBounds().contains(polylineCoordinatesList[a])) {
					if (a >= (polylineCoordinatesList.length - 3)) {
						boundsForMode.extend(polylineCoordinatesList[a + 1]); // UVIDIME CI TO TU NECHAME
						boundsForMode.extend(polylineCoordinatesList[a + 2]);
					}
					map.panToBounds(boundsForMode);
					boundsForMode = new google.maps.LatLngBounds();
				}

			} else if (Mode == 4) {
				leadMarker.setPosition(polylineCoordinatesList[a]);
				if (!map.getBounds().contains(polylineCoordinatesList[a])) {
					map.panTo(polylineCoordinatesList[a]);
				}
			}

			//graphEx = google.visualization.arrayToDataTable(graphDataFinal);

			chart.setSelection([ {
				row : a,
				column : null
			} ]);

			if (isFiles[a] == true) {
				isEnd = true;
				presentMultimedia();
			}
			presentTimeout = setTimeout(function() {
				if (isEnd != true) {
					a++
				}
				;
				if (a < polylineCoordinatesList.length) {
					if (isEnd != true)
						drawingMap();
				} else
					clearmap();
			}, presentationSpeed);
		}
		;
		drawingMap();
	}

	function presentMultimedia() {

		if (a == filesPoints[index]) {

			document.getElementById("next").disabled = false;

			if (filesPath[index].toString().indexOf("YTB") === 0) {

				marker = new google.maps.Marker({
					position : polylineCoordinatesList[a],
					map : map,
					icon : image,
					title : ''
				});

				marker.setMap(map);

				document.getElementById('ytplayer').style.display = "inline";
				document.getElementById('img').style.display = "none";

				if (isPlayerReady && !isVideoPaused) {

					player.loadVideoById(filesPath[index].toString().substr(4),
							0, "default");
					isVideoShowed = true;
				}

				else if (isPlayerReady && isVideoPaused) {

					player.playVideo();
					isVideoPaused = false;
				}

			} else {

				marker = new google.maps.Marker({
					position : polylineCoordinatesList[a],
					map : map,
					icon : image,
					title : ''
				});

				marker.setMap(map);

				document.getElementsByName("img")[0].src = filesPath[index];
				document.getElementsByName("img")[0].style.height = 'auto';

				photoTimeout = setTimeout(function() {
					next();
					return
				}, pictureShowingTime); // automaticky mod
			}
		} else {
			//alert("som v presentation length je " + filesPoints.length + " a index je " + index);
			if (filesPoints.length != index) {
				index++;
				presentMultimedia();
			} else {
				isEnd = false;
				a++;
				index = 0;
				draw();

			}
		}

	}

	function next() {

		document.getElementById("next").disabled = true;

		clearTimeout(photoTimeout);

		if (!lastPictureStayShowed) {
			document.getElementsByName("img")[0].src = "HTMLStyle/PV.PNG";
		}

		document.getElementById('ytplayer').style.display = "none";
		document.getElementById('img').style.display = "inline";

		if (isVideoShowed) {
			player.stopVideo();
			isVideoShowed = false;
			isVideoPaused = false;
			//alert("som v isVideoShowed v nexte");
		}

		if (marker) {
			marker.setMap(null);
		}

		if (filesPoints.length != index) {
			index++;
			presentMultimedia();
		} else {
			isEnd = false;
			draw();

		}

	}

	function clearmap() {

		document.getElementById("play").disabled = false;
		document.getElementById("pause").disabled = true;
		document.getElementById("stop").disabled = true;
		document.getElementById("next").disabled = true;

		clearTimeout(photoTimeout); // automaticky mod

		isEnd = true;
		polylineCoordinatesListFinal = [];
		a = 0;
		index = 0;
		isPolylineAlreadyCreated = false;

		document.getElementsByName("img")[0].src = "HTMLStyle/PV.PNG";

		document.getElementById('ytplayer').style.display = "none";
		document.getElementById('img').style.display = "inline";

		if (isVideoShowed) {
			player.stopVideo();
			isVideoShowed = false;
			isVideoPaused = false;
			//alert("som v isVideoShowed v stope");
		}

		initialize();
	}

	google.maps.event.addDomListener(window, 'load', initialize);

	function drawChart() {

		chart = new google.visualization.AreaChart(document
				.getElementById('chart_div'));

		function selectHandler() {

			if ((typeof chart.getSelection()[0] == 'undefined')) {
				chart.setSelection(null);
			} else {

				var selectedItem = chart.getSelection()[0];

				if (selectedItem.row != null && isPresented == false) {
					var value = graphData.getValue(selectedItem.row, 0);

					if (isAlreadyMark == true) {
						mark.setMap(null);
					}
					mark = new google.maps.Marker({
						position : polylineCoordinatesList[value],
						map : map,
						icon : image2,
						animation : google.maps.Animation.DROP,
						title : ''
					});

					mark.setMap(map);

					isAlreadyMark = true;

				}
			}
		}

		google.visualization.events.addListener(chart, 'select', selectHandler);
		chart.draw(graphData, options);

	}

	function pause() {

		document.getElementById("play").disabled = false;
		document.getElementById("pause").disabled = true;

		clearTimeout(photoTimeout);
		clearTimeout(presentTimeout);

		if (isVideoShowed) {
			player.pauseVideo();
			isVideoPaused = true;
			//alert("pauseVideo");
		}
	}

	function saveOptions() {

		if (document.getElementById("optionsRadios11").checked) {
			Mode = 2;
		} else if (document.getElementById("optionsRadios12").checked) {
			Mode = 1;
		} else if (document.getElementById("optionsRadios13").checked) {
			Mode = 3;
		} else {
			Mode = 4;
		}

		var tempSpeed = $('#ex1').data('slider').getValue();
		presentationSpeed = Math.abs(tempSpeed - 250);
		pictureShowingTime = $('#ex2').data('slider').getValue() * 1000;

		strokeWeight = $('#ex3').data('slider').getValue();
		strokeOpacity = $('#ex4').data('slider').getValue() / 10;

		if (document.getElementById("lstPict").checked) {
			lastPictureStayShowed = true;
		} else {
			lastPictureStayShowed = false;
		}

		var polColorselect = document.getElementById("polColor");
		polyLineColor = polColorselect.options[polColorselect.selectedIndex].value;
		var polColorPresSelect = document.getElementById("polColorPres");
		polyLinePresentationColor = polColorPresSelect.options[polColorPresSelect.selectedIndex].value;

		if (document.getElementById("lstPict").checked) {
			lastPictureStayShowed = true;
		} else {
			lastPictureStayShowed = false;
		}

		graphData = new google.visualization.DataTable();

		if (document.getElementById("optionsRadios1").checked) {

			switch (implicitGraph) {
			case 1:
				setGraph1();
				break;
			case 2:
				setGraph2();
				break;
			case 3:
				setGraph3();
				break;
			case 4:
				setGraph4();
				break;
			case 5:
				setGraph5();
				break;
			}

		} else {
			if (document.getElementById("thirdCheck").checked) {
				setGraph4();
			} else if (document.getElementById("firstCheck").checked) {
				setGraph3();
			} else if (creationType === "Drawed") {
				setGraph2();
			} else {
				setGraph5();
			}

		}

		//                     drawChart();

		$(function() {
			$('#myTab a:first').tab('show')
		});

		clearmap();

	}

	function hideChecks() {
		document.getElementById('checks').style.display = "none";
	}

	function showChecks() {
		document.getElementById('checks').style.display = "inline";
	}
</script>

</head>

<body>
	<div class="container">
		<div class="row clearfix">
			<div class="col-md-12 column">
				<nav class="navbar navbar-default" role="navigation">
					<div class="navbar-header">
						<button type="button" class="navbar-toggle" data-toggle="collapse"
							data-target="#bs-example-navbar-collapse-1">
							<span class="sr-only">Toggle navigation</span><span
								class="icon-bar"></span><span class="icon-bar"></span><span
								class="icon-bar"></span>
						</button>
						<a class="navbar-brand" href="HomePage.jsp"><i
							class="fa fa-globe"></i>&nbsp; GPSWebApp</a>
					</div>

					<div class="collapse navbar-collapse"
						id="bs-example-navbar-collapse-1">
						<ul class="nav navbar-nav">
							<li><a href="HomePage.jsp">Home</a></li>
							<li class="active"><a href="ShowTracks.jsp">My Tracks</a></li>
							<li class="dropdown"><a href="#" class="dropdown-toggle"
								data-toggle="dropdown">Create track<strong class="caret"></strong></a>
								<ul class="dropdown-menu">
									<li><a href="UploadTrack1.jsp">Upload track</a></li>

									<li class="divider"></li>
									<li><a href="WriteTrack1.jsp">Write new track</a></li>
								</ul></li>
						</ul>
						<form action="FindResults.jsp" method="POST"
							class="navbar-form navbar-left" role="search"
							accept-charset="Windows-1250">
							<div class="form-group">
								<input type="text" class="form-control home-search"
									name="finderText">
							</div>
							<button type="submit" class="btn btn-default">Find</button>
						</form>
						<ul class="nav navbar-nav navbar-right">
							<li><a href="About.jsp">About</a></li>
							<li class="dropdown"><a href="#" class="dropdown-toggle"
								data-toggle="dropdown"><i class="fa fa-user"></i> Account<strong
									class="caret"></strong></a>
								<ul class="dropdown-menu">
									<li><a href="ShowUserInfo.jsp">View account</a></li>
									<li><a href="EditAccount.jsp">Edit account</a></li>
									<li><a href="DeleteUser.jsp">Delete account</a></li>
									<li class="divider"></li>
									<li><a href="../Logout.jsp">Logout</a></li>
								</ul></li>
						</ul>
					</div>

				</nav>

				<div class="tabbable" id="tabs-747520">
					<ul class="nav nav-tabs" id="myTab">
						<li class="active"><a href="#tabs-1" data-toggle="tab">Track
								on map</a></li>
						<li><a href="#tabs-2" data-toggle="tab">Track information</a>
						</li>
						<li><a href="#tabs-3" data-toggle="tab">Track options</a></li>
						<li><a href="#tabs-4" data-toggle="tab">Track
								publications</a></li>
					</ul>
					<div class="tab-content">
						<div class="tab-pane active" id="tabs-1">

							<h3>
								<%
									out.print(file);
								%>
							</h3>
							<br>

							<div class="row">
								<div class="col-md-8">
									<div id="map_canvas"></div>
								</div>

								<div class="col-md-4" style="height: 550px">
									<div class="text-center" style="height: 275px;">
										<img name="img" src="HTMLStyle/PV.PNG" alt="..."
											class="fixed_width">

									</div>

									<div id="ytplayer" style="display: none"></div>
									<img id="img" name="img" src="HTMLStyle/VP.PNG" alt="..."
										class="fixed_width" style="height: 100%">


								</div>
							</div>


							<div id="chart_div" style="width: 100%; height: 200px;"></div>
							<div id="empty_div"></div>
							<p style="line-height: 20px; text-align: center;">
								<!--<div class="btn-group">-->
								<button id="play" title="Start presentation" type="button"
									class="btn btn-sm btn-primary" onclick="draw();">
									<span class="glyphicon glyphicon-play"></span>
								</button>
								<button id="pause" title="Pause presentation" type="button"
									class="btn btn-sm btn-primary" onclick="pause();" disabled>
									<span class="glyphicon glyphicon-pause"></span>
								</button>
								<button id="next" title="Skip multimedia file" type="button"
									class="btn btn-sm btn-primary" onclick="next();" disabled>
									<span class="glyphicon glyphicon-step-forward"></span>
								</button>
								<button id="stop" title="Stop presentation" type="button"
									class="btn btn-sm btn-primary" onclick="clearmap();" disabled>
									<span class="glyphicon glyphicon-stop"></span>
								</button>
								<!--</div>-->
							</p>

						</div>
						<div class="tab-pane" id="tabs-2">

							<h3>Info and multimedia files</h3>

							<br>

							<div class="col-md-3">

								<%
									String modifiedDate = track.getUpdated().toGMTString();
								%>

								<label for="TrackDesc"
									style="font-size: 13px; margin-bottom: 0px">Track
									description</label>
								<h6>
									<%
										out.println(loader.getTrackDescription());
									%>
								</h6>

								<label for="TrackActivity"
									style="font-size: 13px; margin-bottom: 0px">Track
									activity</label>
								<h6>
									<%
										out.println(loader.getTrackType().substring(4));
									%>
								</h6>

								<!--                                    <label for="Privacy" style="font-size:13px; margin-bottom: 0px">Privacy</label>
                                        <h6> <%out.println(track.getAccess());%> </h6>
                                        
                                        <label for="Uploaded" style="font-size:13px; margin-bottom: 0px">Uploaded</label>
                                        <h6> <%out.println(track.getCreated());%> </h6>-->

								<label for="StartPlace"
									style="font-size: 13px; margin-bottom: 0px">Start place</label>
								<h6>
									<%
										out.println(loader.getStartAddress());
									%>
								</h6>

								<label for="EndPlace"
									style="font-size: 13px; margin-bottom: 0px">End place</label>
								<h6>
									<%
										out.println(loader.getEndAddress());
									%>
								</h6>

								<label for="Track Length"
									style="font-size: 13px; margin-bottom: 0px">Track
									Length</label>
								<h6>
									<%
										out.println(track.getLengthKm());
									%>
									km
								</h6>

								<label for="MinElevation"
									style="font-size: 13px; margin-bottom: 0px">Min
									Elevation</label>
								<h6>
									<%
										out.println(track.getMinElevation());
									%>
									m
								</h6>

								<label for="MaxElevation"
									style="font-size: 13px; margin-bottom: 0px">Max
									Elevation</label>
								<h6>
									<%
										out.println(track.getMaxElevation());
									%>
									m
								</h6>

								<label for="HeightDiff"
									style="font-size: 13px; margin-bottom: 0px">Height
									Difference</label>
								<h6>
									<%
										out.println(track.getHeightDiff());
									%>
									m
								</h6>

								<%
									if (track.getType().equalsIgnoreCase("Parsed")) {
										out.print("<label for=\"StartDate\" style=\"font-size:13px; margin-bottom: 0px\">Start</label><h6>");
										out.print(track.getStartdate());
										out.print("</h6>");
										out.print("<label for=\"EndDate\" style=\"font-size:13px; margin-bottom: 0px\">End</label><h6>");
										out.print(track.getEnddate());
										out.print("</h6>");
										out.print("<label for=\"Duration\" style=\"font-size:13px; margin-bottom: 0px\">Duration</label><h6>");
										out.print(track.getDuration());
										out.print("</h6>");
									}
								%>

							</div>

							<div class="col-md-9">

								<div class="galleria">
									<%
										String temp = null;
										for (int i = 0; i < loader.getMultimediaFiles().size(); i++) {
											if (System.getProperty("os.name").startsWith("Windows")) {
												File f = new File(loader.getMultimediaFiles().get(i).getPath());
												temp = f.toURI().toString().substring(f.toURI().toString().lastIndexOf("/Logged/") + 8);
											} else {
												if (!loader.getMultimediaFiles().get(i).getPath().toString().contains("YTB")) {
													String temp1 = loader.getMultimediaFiles().get(i).getPath().substring(38);
													temp = temp1.replaceAll(" ", "%20");
												}
											}

											String newPath = null;

											if (!loader.getMultimediaFiles().get(i).getPath().toString().contains("YTB")) {

												String extension = temp.substring(temp.lastIndexOf("."), temp.length());

												newPath = temp.substring(0, temp.lastIndexOf(".")) + "_THUMB" + extension;

												out.println("<img src=\"" + newPath + "\" " + "data-title=\""
														+ temp.substring(temp.lastIndexOf("/") + 1, temp.length()) + "\">");
											}

											else {
												if (System.getProperty("os.name").startsWith("Windows")) {
													newPath = temp.substring(temp.length() - 11);
												} else {
													newPath = loader.getMultimediaFiles().get(i).getPath().substring(4);

												}
												out.println("<a href=\"http://www.youtube.com/watch?v=" + newPath
														+ "\"><span class=\"video\">Watch this on Vimeo!</span></a>");
											}

										}
									%>
								</div>
								<script>
									$(document)
											.ready(
													function() {
														Galleria
																.loadTheme('HTMLStyle/GalleryStyle/themes/classic/galleria.classic.min.js');
														Galleria
																.configure({
																	transition : 'fade',
																	imageCrop : true,
																	wait : '20 000'
																});
														Galleria
																.run(
																		'.galleria',
																		{
																			height : 640,
																			width : 'auto'
																		});
													});
								</script>

							</div>
						</div>

						<div class="tab-pane" id="tabs-3">


							<h3>Track options</h3>

							<br>

							<div class="container">
								<div class="row clearfix">
									<div class="col-md-4 column"></div>
									<div class="col-md-4 column">

										<label for="Presentation mode">Presentation mode</label> <br>

										<div id="presMode1" class="radio">
											<label> <input type="radio" name="optionsRadios1"
												id="optionsRadios11" value="static" checked> Static
												mode
											</label>
										</div>
										<div id="presMode2" class="radio">
											<label> <input type="radio" name="optionsRadios1"
												id="optionsRadios12" value="motion"> Motion mode
											</label>
										</div>
										<div id="presMode3" class="radio">
											<label> <input type="radio" name="optionsRadios1"
												id="optionsRadios13" value="dynamic"> Dynamic mode
											</label>
										</div>
										<div id="presMode4" class="radio">
											<label> <input type="radio" name="optionsRadios1"
												id="optionsRadios14" value="centered dynamic">
												Centered Dynamic mode
											</label>
										</div>


										<br> <label for="presentationSpeed">Presentation
											speed</label> <br> <input id="ex1" data-slider-id='ex1Slider'
											type="text" data-slider-min="5" data-slider-max="255"
											data-slider-step="1" data-slider-value="215"
											style="width: 360px;" /> <br> <br> <label
											for="PictureLength">Picture showing time</label> <br> <input
											id="ex2" data-slider-id='ex2Slider' type="text"
											data-slider-min="1" data-slider-max="10" data-slider-step="1"
											data-slider-value="5" style="width: 360px;" /> <br> <br>

										<div class="checkbox">
											<input id="lstPict" type="checkbox" value=""> <label>

												<b> Last picture stay showed</b>
											</label>
										</div>
										<br> <label for="polylineColorState">Polyline
											color</label> <select id="polColor" name="polColor"
											class="form-control">
											<option value="#3300FF">Blue</option>
											<option value="#FF0000" selected>Red</option>
											<option value="#FF6600">Orange</option>
											<option value="#000000">Black</option>
											<option value="#FFFFFF">White</option>
											<option value="#FFCC00">Yellow</option>
											<option value="#00E000">Green</option>
											<option value="#F10066">Pink</option>
										</select> <br> <label for="polylineColorPresent">Polyline
											color in presentation</label> <select id="polColorPres"
											name="polColorPres" class="form-control">
											<option value="#3300FF">Blue</option>
											<option value="#FF0000" selected>Red</option>
											<option value="#FF6600">Orange</option>
											<option value="#000000">Black</option>
											<option value="#FFFFFF">White</option>
											<option value="#FFCC00">Yellow</option>
											<option value="#00E000">Green</option>
											<option value="#F10066">Pink</option>
										</select> <br> <label for="strokeWeight">Stroke weight</label> <br>

										<input id="ex3" data-slider-id='ex3Slider' type="text"
											data-slider-min="1" data-slider-max="5" data-slider-step="1"
											data-slider-value="2" style="width: 360px;" /> <br> <br>
										<label for="strokeOpacity">Stroke opacity</label> <br> <input
											id="ex4" data-slider-id='ex4Slider' type="text"
											data-slider-min="0" data-slider-max="10" data-slider-step="1"
											data-slider-value="10" style="width: 360px;" /> <br>

										<script>
											$('#ex1')
													.slider(
															{
																formater : function(
																		value) {
																	return 'Current speed: '
																			+ value;
																}
															});

											$('#ex2').slider({
												formater : function(value) {
													return value + ' seconds';
												}
											});

											$('#ex3').slider({
												formater : function(value) {
													return value;
												}
											});

											$('#ex4').slider({
												formater : function(value) {
													return (value * 10) + '%';
												}
											});
										</script>
										<br> <label for="GraphOptions">Chart options</label> <br>

										<div id="graphOpt" class="radio">
											<label> <input type="radio" name="optionsRadios"
												id="optionsRadios1" value="implicit" onClick="hideChecks()"
												checked> Show default chart based on track activity
											</label>
										</div>
										<div class="radio">
											<label> <input type="radio" name="optionsRadios"
												id="optionsRadios2" value="customize" onClick="showChecks()">
												Show customized chart
											</label>
										</div>

										<div id="checks" style="display: none">
											<br>
											<div class="radio">
												<label> <input type="radio" name="optionsRadio"
													value="optionsRadio1" disabled id="firstCheck">
													Show device elevations
												</label>
											</div>
											<div class="radio">
												<label> <input type="radio" name="optionsRadio"
													value="optionsRadio2" disabled id="secondCheck">
													Show elevations on map
												</label>
											</div>
											<div class="radio">
												<label> <input type="radio" name="optionsRadio"
													value="optionsRadio3" disabled id="thirdCheck">
													Show elevations on map and device elevations
												</label>
											</div>
										</div>

										<script>
											$(document)
													.ready(
															function() {

																if (isDeviceElevations == true
																		&& isElevationsOnMap == true) {
																	document
																			.getElementById('thirdCheck').disabled = false;
																	document
																			.getElementById('firstCheck').disabled = false;
																	document
																			.getElementById('secondCheck').disabled = false;
																	document
																			.getElementById('thirdCheck').checked = true;
																} else if (isDeviceElevations == true) {
																	document
																			.getElementById('firstCheck').disabled = false;
																	document
																			.getElementById('firstCheck').checked = true;
																} else if (isElevationsOnMap == true) {
																	document
																			.getElementById('secondCheck').disabled = false;
																	document
																			.getElementById('secondCheck').checked = true;
																} else {
																	document
																			.getElementById('optionsRadios2').disabled = true;
																}

															});
										</script>


										<br>
										<p style="line-height: 20px; text-align: center;">
											<button id="saveOptions" class="btn btn-default btn-success"
												onClick="saveOptions();">Save</button>
										</p>


									</div>
									<div class="col-md-4 column"></div>

								</div>
							</div>
						</div>
						<div class="tab-pane" id="tabs-4">


							<h3>Track publication</h3>
							<br>
							<div class="col-md-4 column"></div>
							<div class="col-md-4 column text-center">
								<br>

								<%
									String user = track.getUser().getEmail();
									String url = Constants.USER_DATA_STORAGE + user + "/" + file + "/" + file;
								%>
								<p class="help-block">You can download pdf file with
									presentation of track clicking button below</p>
								<br> <a class="btn btn-primary"
									href="<%out.print(url + ".pdf");%>" download
									title="This button will launch dowload with generated publication from this track. This publication will contains a selection of your multimedia files and track with track details.">Download
									PDF file from track</a> <br> <br>
								<p class="help-block">You can download gpx file with
									tracklog clicking button below</p>
								<br> <a class="btn btn-primary"
									href="<%out.print(url + ".gpx");%>" download
									title="This button will launch download GPX tracklog file. This file can be uploaded to GPS device for navigation purpose.">Download
									GPX file from track</a>




							</div>
							<div class="col-md-4 column"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
</body>
</html>
