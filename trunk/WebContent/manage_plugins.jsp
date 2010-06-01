<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@page import="util.Plugin"  %>
<%@page import="java.util.ArrayList"%>
 <%
 Plugin plugin = (Plugin) session.getAttribute("plugin") ;       
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
             <h2>Modifica plugin</h2>
            <form action="DispatcherServlet" method="POST" name="plugin">

            <input type="hidden" name="action" value="edit">
            <input type="hidden" name="plugin_id" value="<%= plugin.get_id() %>">

            name: <input type="text" name="plugin_name" value="<%= plugin.get_name() %>" /><br />
            configurationfile: <input type="text" name="plugin_configurationfile" value="<%= plugin.get_configurationfile() %>" /><br/>
            type: <select name="plugin_type" >
                    <option  <% if (plugin.get_type().compareTo("link")==0){ %> 
                    								'selected'
                    								<% }%>
                    								>link</option>
                    <option <% if (plugin.get_type().compareTo("single")==0){ %> 
                    		'selected'
                    		<%} %>
                    		>single</option>
                  </select>
                <br />
                note: <input type="text" name="plugin_note" value="<%= plugin.get_note() %>" /><br />

            <input type="submit" name="SubmitUserPluginDo" />
        </form>
        <p><a href="index.jsp">Torna indietro</a></p>
    </body>
</html>
