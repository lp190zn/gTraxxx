<%-- 
    Document   : Logout
    Created on : 9.10.2013, 20:14:21
    Author     : matej_000
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>
    </head>
    <body>
        <%
            session.removeAttribute("username");
            session.removeAttribute("Pass");
            session.invalidate();
            
            response.sendRedirect("LoginPage.jsp");
            %>
    </body>
</html>
