<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.util.Random"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
Message :      
<%
String titleMsg = (String) session.getAttribute("titleMsg");
String printedMsg = (String) session.getAttribute("printedMsg");
String returnLink = (String) session.getAttribute("returnLink");
%>             

<br>
<%if (titleMsg!= null){ %>
	<h1><%= titleMsg %></h1>
<% } else {%>
	<h1><%= "Esito: " %></h1>
<%}%>


<br>
<%= printedMsg %>
<br>
<br>
<%
String correctedReturnLink = "DispatcherServlet?refreshIndex=1";
if (returnLink!= null){
	correctedReturnLink = returnLink;
 } %>

<a href="<%=correctedReturnLink %>" >return</a>  
</body>
</html>