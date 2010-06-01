<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@page import="util.Group" %>
 <%
  Group group = (Group) session.getAttribute("group") ;       
 %>                  	             	                    

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>
        <link rel="stylesheet" type="text/css" href="style.css" />
    </head>
    <body>
        <a href="index.jsp">torna all' inizio</a>
        <%
            %>
            <h2>Modifica Gruppo</h2>
            <form action="DispatcherServlet" method="POST" name="group">

            <input type="hidden" name="action" value="edit">
            <input type="hidden" name="group_id" value="<%= group.get_id() %>">
            <input type="hidden" name="group_old_status" value="<%= group.get_status() %>">

            name: <input type="text" name="group_name" value="<%= group.get_name() %>" /><br />
            status: <select name="group_status">
                    <option<% if(group.get_status()==0){ %> selected="selected" <% } %>>0</option>
                    <option<% if(group.get_status()==1){ %> selected="selected" <% } %>>1</option>
                </select><br />
            <input type="submit" name="SubmitGroupModifyDo" />
        </form>
        <p><a href="index.jsp">Torna indietro</a></p>
    </body>
</html>
