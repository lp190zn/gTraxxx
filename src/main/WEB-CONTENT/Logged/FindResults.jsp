<%@page import="java.util.Locale"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="sk.mlp.database.DatabaseServices"%>
<%@page import="sk.mlp.ui.model.User"%>
<%@page import="sk.mlp.ui.model.Track"%>
<%@page import="sk.mlp.parser.TLVLoader"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
	session.removeAttribute("trackFilename");
	session.removeAttribute("trackName");
	session.removeAttribute("trackDescr");
	session.removeAttribute("trackActivity");
	session.removeAttribute("access");
	session.removeAttribute("trackNameExist");
	session.removeAttribute("isMultimedia");

	request.setCharacterEncoding("Windows-1250");
	String findStr = request.getParameter("finderText");
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="Windows-1250">
<title>Founded tracks</title>

<link rel="stylesheet"
	href="https://netdna.bootstrapcdn.com/font-awesome/4.0.0/css/font-awesome.css">

<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<link href="HTMLStyle/HomePageStyle/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet" href="HTMLStyle/theme.bootstrap.css">

<script type="text/javascript"
	src="HTMLStyle/HomePageStyle/js/jquery.min.js"></script>
<script type="text/javascript"
	src="HTMLStyle/HomePageStyle/js/bootstrap.min.js"></script>

<script type="text/javascript" src="HTMLStyle/jquery.tablesorter.js"></script>
<script type="text/javascript"
	src="HTMLStyle/jquery.tablesorter.widgets.js"></script>

<script>
	$(function() {

		$.extend($.tablesorter.themes.bootstrap, {
			// these classes are added to the table. To see other table classes available,
			// look here: http://twitter.github.com/bootstrap/base-css.html#tables

			icons : '', // add "icon-white" to make them white; this icon class is added to the <i> in the header
			sortNone : 'bootstrap-icon-unsorted',
			sortAsc : 'icon-chevron-up glyphicon glyphicon-chevron-up', // includes classes for Bootstrap v2 & v3
			sortDesc : 'icon-chevron-down glyphicon glyphicon-chevron-down', // includes classes for Bootstrap v2 & v3

			table : 'table table-bordered',
			footerRow : '',
			footerCells : '',

			active : '', // applied when column is sorted
			hover : '', // use custom css here - bootstrap class may not override it
			filterRow : '', // filter row class
			even : '', // odd row zebra striping
			odd : '' // even row zebra striping
		});

		$("#myTable").tablesorter({

			theme : "bootstrap",
			headerTemplate : '{content} {icon}', // new in v2.7. Needed to add the bootstrap icon!
			widgets : [ "uitheme" ],

			headers : {
				// assign the secound column (we start counting zero) 

				3 : {
					sorter : "shortDate",
					dateFormat : "ddmmyyyy"
				},
				5 : {
					sorter : "shortDate",
					dateFormat : "ddmmyyyy"
				},
				6 : {
					// disable it by setting the property sorter to false 
					sorter : false
				},

			}
		});
	});
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
									name="finderText" value="<%out.print(findStr);%>">
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

				<div class="carousel slide" id="carousel-646485"
					data-ride="carousel">
					<ol class="carousel-indicators">
						<li class="active" data-slide-to="0"
							data-target="#carousel-646485"></li>
						<li data-slide-to="1" data-target="#carousel-646485"></li>
						<li data-slide-to="2" data-target="#carousel-646485"></li>
					</ol>
					<div class="carousel-inner">
						<div class="item active">
							<img alt="" src="HTMLStyle/HomePageStyle/car1.jpg">
							<div class="carousel-caption">
								<h4>Release your soul...</h4>
								<p>...conquer the highest mountain in the world...</p>
							</div>
						</div>
						<div class="item">
							<img alt="" src="HTMLStyle/HomePageStyle/car2.jpg">
							<div class="carousel-caption">
								<h4>Discover the beauty of world...</h4>
								<p>...take your bike and go...</p>
							</div>
						</div>
						<div class="item">
							<img alt="" src="HTMLStyle/HomePageStyle/car3.jpg">
							<div class="carousel-caption">
								<h4>Take off to the sky...</h4>
								<p>...get your paraglide and fly with the wind...</p>
							</div>
						</div>
					</div>
					<a class="left carousel-control" href="#carousel-646485"
						data-slide="prev"><span
						class="glyphicon glyphicon-chevron-left"></span></a> <a
						class="right carousel-control" href="#carousel-646485"
						data-slide="next"><span
						class="glyphicon glyphicon-chevron-right"></span></a>
				</div>

				<h3>
					Search results for:
					<%
					out.print(findStr);
				%>
				</h3>

				<br>

				<%
					DatabaseServices databaseServices = new DatabaseServices();
					List<Track> results = databaseServices.findPattern(findStr);
					if (results.size() == 0) {
						out.print("<div class=\"alert alert-danger\">Sorry, you don't have any uploaded tracks...</div>");
					} else {
						out.println(
								"<table id=\"myTable\" class= \"tablesorter \"><thead><tr><th>Track name</th><th>Owner</th><th>Activity</th><th>Uploaded</th><th>Length</th><th>Start</th><th>&nbsp;</th></tr></thead><tbody>");

						for (Track track : results) {
							DateFormat df1 = new SimpleDateFormat("dd.MM.yyyy HH:mm");
							String finalDate = df1.format(track.getUpdated());
							String finalStartDate = df1.format(track.getStartdate());

							out.println("<tr><td style=\"word-wrap: break-word;padding-top:12px;\">" + track.getName()
									+ "</td><td style=\"word-wrap: break-word;padding-top:12px;\">"
									+ track.getUser().getEmail()
									+ "</td><td style=\"word-wrap: break-word;padding-top:12px;\">"
									+ track.getActivity().substring(4)
									+ "</td><td style=\"word-wrap: break-word;padding-top:12px;\">" + finalDate
									+ "</td><td style=\"word-wrap: break-word;padding-top:12px;\">" + track.getLengthKm()
									+ "</td>");

							if (track.getType().equalsIgnoreCase("Parsed")) {
								out.print("<td style=\"word-wrap: break-word;padding-top:12px;\">" + finalStartDate + "</td>");
							} else {
								out.print("<td style=\"word-wrap: break-word;padding-top:12px;\"> NONE </td>");
							}

							out.print("<td class=\"text-center\"><a href=ShowTrack.jsp?trkID=" + track.getIdent()
									+ " class=\"btn btn-success btn-sm \">Show</a></td>");
							out.print("</tr>");
						}
						out.println("</tbody></table>");
					}
				%>
				<br>
			</div>
		</div>
	</div>
</body>
</html>


