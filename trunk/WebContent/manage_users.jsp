<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@page import="util.User"  %>
<%@page import="util.Group"  %>
<%@page import="java.util.ArrayList"%>
 <%
  User user = (User) session.getAttribute("user") ;       
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
        <%
            %>
            <h2>Modifica Utente</h2>
            <form action="DispatcherServlet" method="POST" name="User">

            <input type="hidden" name="action" value="edit">
            <input type="hidden" name="user_id" value="<%= user.get_id() %>">

            username: <input type="text" name="user_username" value="<%= user.get_username()  %>" /><br />
            password: <input type="text" name="user_password" /> (nota: se il campo viene lasciato vuoto, verrà mantenuta la password esistente)<br />
            name: <input type="text" name="user_name" value="<%= user.get_name() %>" /><br />
            surname: <input type="text" name="user_surname" value="<%= user.get_surname() %>" /><br />
            email: <input type="text" name="user_email" value="<%= user.get_email() %>" /><br />
            office: <input type="text" name="user_office" value="<%= user.get_office() %>" /><br />
            telephone: <input type="text" name="user_telephone" value="<%= user.get_telephone() %>" /><br />
            status: <select name="user_status">
                    <option<% if(user.get_status()==0){ %> selected="selected" <% } %>>0</option>
                    <option<% if(user.get_status()==1){ %> selected="selected" <% } %>>1</option>
                 </select><br />
            group: <select name="user_id_group">
                <% 
                ArrayList <Group> groups = (ArrayList <Group>) session.getAttribute("groups");
								for (Group group : groups ) {
									 int id_gr = group.get_id();
									 String name_gr = group.get_name();
									 int status_gr = group.get_status();
                    if(status_gr==1) { %>
                    <option value="<%= id_gr %>"
                        <% 
                        if(user.get_id_group() == id_gr){
                        	%> 
                        	selected="selected"
                        	<%}
                    %>><%= name_gr %></option><%
                    }
                } %>
            </select><br />
            <input type="submit" name="SubmitUserModifyDo" />
        </form>
        <p><a href="index.jsp">Torna indietro</a></p>
    </body>
</html>
