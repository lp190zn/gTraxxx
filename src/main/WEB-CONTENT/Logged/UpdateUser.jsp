<%-- 
    Document   : Register
    Created on : 10.10.2013, 18:04:18
    Author     : Falinko
--%>



<%@page import="sk.mlp.database.DatabaseServices"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    session.removeAttribute("trackFilename");
    session.removeAttribute("trackName");
    session.removeAttribute("trackDescr");
    session.removeAttribute("trackActivity");
    session.removeAttribute("trackNameExist");
    
    request.setCharacterEncoding("Windows-1250");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register Page</title>
    </head>
    <body>
        <%
            String email = request.getParameter("Login");
            String firstName = request.getParameter("FirstName");
            String lastName = request.getParameter("LastName");
            int age;
            String activity = request.getParameter("Activity");
            
            if (request.getParameter("Age").toString().equalsIgnoreCase("")) {
                age = -1;
            } else {   
            age = Integer.parseInt(request.getParameter("Age"));
            }
            
            String currpass = request.getParameter("currPass");
            String pass = request.getParameter("Pass");
            String reenteredPass = request.getParameter("RetypePass");
            
            
            DatabaseServices databaseServices = new DatabaseServices();
            boolean isGood = databaseServices.isCorrectLogin(request.getParameter("Login"), request.getParameter("currPass"));
            
            if (isGood) {

                if(!pass.equals("")){
 
                    if (pass.equals(reenteredPass)) {
                    
                        databaseServices.updateUserData(email, email, firstName, lastName, activity, currpass, pass, age);
                        response.sendRedirect("HomePage.jsp");
                    } else {
                        session.setAttribute("passNotMatch", "True");
                        response.sendRedirect("EditAccount.jsp");
                    }
                } else{
                    session.setAttribute("emptyPass", "True");
                    response.sendRedirect("EditAccount.jsp");
                    }
            } else {
                session.setAttribute("wrongPass", "True");
                response.sendRedirect("EditAccount.jsp");
            }     
        %>
    </body>
</html>
