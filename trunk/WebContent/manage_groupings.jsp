<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@page import="util.Grouping" %>

 <%
  Grouping grouping = (Grouping) session.getAttribute("grouping") ;       
 %>         
 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>
        <link rel="stylesheet" type="text/css" href="style.css" />
    </head>
    <body>
        <a href="index.jsp">torna all'inizio</a>

             <h2>Modifica raggruppamento</h2>
            <form action="DispatcherServlet" method="POST" name="group">

            <input type="hidden" name="action" value="edit">

            name: <input type="text" name="grouping_name" value="<%= grouping.get_name() %>" /><br />
            alias: <input type="text" name="grouping_alias" value="<%= grouping.get_alias() %>" /><br />

            <input type="submit" name="SubmitGroupingModifyDo" />
        </form>
        <p><a href="index.jsp">Torna indietro</a></p>
    </body>
</html>
