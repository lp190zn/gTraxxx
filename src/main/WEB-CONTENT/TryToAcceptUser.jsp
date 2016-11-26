<%-- 
    Document   : TryToAcceptUser
    Created on : 27.2.2014, 12:27:43
    Author     : matej_000
--%>

<%@page import="sk.mlp.database.DatabaseServices"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
            String userToken = request.getParameter("token");
            String email = request.getParameter("email");
            
            DatabaseServices databaseServices = new DatabaseServices();
            boolean accept = databaseServices.acceptUser(email, userToken);
            
            if(accept){
                response.sendRedirect("./login");
            }else{
                response.sendRedirect("./login");
            }
        %>
    </body>
</html>
