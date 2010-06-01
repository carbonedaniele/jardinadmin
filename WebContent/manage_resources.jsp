<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@page import="java.util.ArrayList"%>
<%@page import="util.*"%>

<%
Group selectedGroup = (Group) session.getAttribute("selectedGroup");
Resultset selectedResultset = (Resultset) session.getAttribute("selectedResultset");
ArrayList <ResourceWithGroupPermissions> selectedResources = (ArrayList <ResourceWithGroupPermissions>) session.getAttribute("selectedResources");
Toolbar selectedToolbar = (Toolbar) session.getAttribute("selectedToolbar");
ArrayList <Plugin> selectedPlugins = (ArrayList <Plugin>) session.getAttribute("selectedPlugins");
ArrayList <Group> groups = (ArrayList <Group>) session.getAttribute("groups");
ArrayList <Resultset> resultsets = (ArrayList <Resultset>) session.getAttribute("resultsets");
ArrayList <User> users = (ArrayList <User>) session.getAttribute("users") ;                    	                    
ArrayList <Plugin> plugins = (ArrayList <Plugin>) session.getAttribute("plugins");
ArrayList <Grouping> groupings = (ArrayList <Grouping>) session.getAttribute("groupings");
ArrayList <Notify> notifies = (ArrayList <Notify>) session.getAttribute("notifies");
ArrayList <Pluginassociation> pluginAssociations = (ArrayList <Pluginassociation>) session.getAttribute("pluginAssociations");
	
	
	


//for (Notify notify : notifies)	
//			 String array_grouping[arr_gr("id")] = arr_gr("alias");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>
        <link rel="stylesheet" type="text/css" href="style.css" />
        <script type="text/javascript">
            function checkTutti() {
                with (document.Users) {
                    for (var i=0; i < elements.length; i++) {
                        if (elements[i].type == 'checkbox' && elements[i].disabled == false)
                            elements[i].checked = true;
                    }
                }
            }

            function uncheckTutti() {
                with (document.Users) {
                    for (var i=0; i < elements.length; i++) {
                        if (elements[i].type == 'checkbox')
                            elements[i].checked = false;
                    }
                }
            }
1
            function setReadOnly() {
                with (document.Users) {
                    for (var i=0; i < elements.length; i++) {
                        if (elements[i].type == 'checkbox') {
                            var check_type = elements[i].name.split("_");
                            if (check_type[2] == 'r') {
                                elements[i].checked = true;
                            }
                        }
                    }
                }
            }

            function setDeleteOnly() {
                with (document.Users) {
                    for (var i=0; i < elements.length; i++) {
                        if (elements[i].type == 'checkbox') {
                            var check_type = elements[i].name.split("_");
                            if (check_type[2] == 'w') {
                                elements[i].checked = true;
                            }
                        }
                    }
                }
            }

            function setModifyOnly() {
                with (document.Users) {
                    for (var i=0; i < elements.length; i++) {
                        if (elements[i].type == 'checkbox') {
                            var check_type = elements[i].name.split("_");
                            if (check_type[2] == 'm') {
                                elements[i].checked = true;
                            }
                        }
                    }
                }
            }

            function setInsertOnly() {
                with (document.Users) {
                    for (var i=0; i < elements.length; i++) {
                        if (elements[i].type == 'checkbox') {
                            var check_type = elements[i].name.split("_");
                            if (check_type[2] == 'i') {
                                elements[i].checked = true;
                            }
                        }
                    }
                }
            }

            function setHeaderOnly() {
                with (document.Users) {
                    for (var i=0; i < elements.length; i++) {
                        if (elements[i].type == 'checkbox') {
                            var check_type = elements[i].name.split("_");
                            if (check_type[2] == 'h') {
                                elements[i].checked = true;
                            }
                        }
                    }
                }
            }

            function setSearchOnly() {
                with (document.Users) {
                    for (var i=0; i < elements.length; i++) {
                        if (elements[i].type == 'checkbox') {
                            var check_type = elements[i].name.split("_");
                            if (check_type[2] == 's') {
                                elements[i].checked = true;
                            }
                        }
                    }
                }
            }
        </script>
    </head>
    <body>
        <a href="index.jsp">torna all'inizio</a>
        <%

//	 String resultset_id = request.getParameter("resultset_id");
        //	 String resource_list = get_fields_from_resultsetid(resultset_id);
//	 String resource_list[] = get_resource_from_id(resultset_id);
//	 String toolbar = get_toolbar_from_ids(resultset_id, group_id);

        //groups = get_groups(resultset_id);
	// String groups = get_groups();
	// String resultsets = get_resultsets();
        //        if (count(groups) <= 0) {
        //            echo "Non esiste nessun gruppo che non abbia il resultset già in management. ".
        //                "<a href='index.jsp'>Torna indietro</a>";
        //        }
        //        else {
//        var_dump(group_id);
        

        %>

        <form action="DispactcherServlet" method="POST" name="Group">
            <input type="hidden" name="action" value="old">
            <table>
                <tr>
                    <td>Seleziona resultset:</td>
                    <td><select name="modifyPermissionsResultsetSelect" onchange="this.form.submit()">
                <% for (Resultset resultset : resultsets ) {
										 int id = resultset.get_id();
										 String name = resultset.get_alias();
                        %>
                    <option value="<%= id %>" <% 
                    if (selectedResultset.get_id() == id) {
                    	%> selected <% 
                    } %>>
                            <%= name %>
                    </option>
                    <% } %>
            </select></td>
            <td rowspan="2"><input type="submit" value="aggiorna selezione"></td>
            
                </tr>
                <tr>
                    <td>Seleziona gruppo:</td>
                    <td><select name="modifyPermissionsGroupSelect" onchange="this.form.submit()">
                <% for (Group group : groups  ) {
										 int id = group.get_id();
										 String name = group.get_name();
                    %>
                <option value="<%= id %>" <% 
                if (selectedGroup.get_id()==id){ 
                	%> selected <%
                }%>>
                        <%= name %>
                </option>
                <% } %>
            </select></td>
                </tr>           
        </table>
        </form>
        <form action="DispatcherServlet" method="POST" name="Users">
            <input type="submit" value="Submit" />
            <input type=button onclick="checkTutti()" value="Seleziona tutti" />
            <input type=button onclick="uncheckTutti()" value="Deseleziona tutti" />
            <input type=button onclick="setReadOnly()" value="sola lettura" />
            <input type=button onclick="setModifyOnly()" value="sola modifica" />
            <input type=button onclick="setDeleteOnly()" value="sola cancellazione" />
            <input type=button onclick="setInsertOnly()" value="solo inserimento" />
            <input type=button onclick="setHeaderOnly()" value="solo header" />
            <input type=button onclick="setSearchOnly()" value="sola ricerca" />
                   <table>
                <tr>
                    <td colspan="11">Gruppo: <b><%= selectedGroup.get_name() %></b></td>
                </tr>
                <tr class="title">
                    <td>name</td>
                    <td>alias</td>
                    <td>type</td>
                    <td>defval</td>
                    <td>read</td>
                    <td>delete</td>
                    <td>modify</td>
                    <td>insert</td>
                    <td>header</td>
                    <td>ricerca</td>
                    <td>raggruppamento</td>
                </tr>

                <%
                for (ResourceWithGroupPermissions resource : selectedResources) {
									 String resource_name = resource.get_name();
									 String resource_alias = resource.get_alias();
									 int resource_id = resource.get_id();
									 String resource_type = resource.get_type();
									 String resource_def = resource.get_def();
									 int resource_header = resource.get_header();
									 int resource_search = resource.get_search();
									 int resource_grouping = resource.get_grouping();
								
										 int readperm = resource.getResourceGroupPermissions().getRead();
										 int deleteperm = resource.getResourceGroupPermissions().getDelete();
										 int modifyperm = resource.getResourceGroupPermissions().getModify();
										 int insertperm = resource.getResourceGroupPermissions().getInsert();
                    %>
                <tr>
                    <td><%= resource_name %></td>
                    <td><input type="text" name="c_<%= resource_id %>_a" value="<%= resource_alias %>"></td>
                    <td><%= resource_type %></td>
                    <td><%= resource_def %></td>
                    <td><input type="checkbox" name="c_<%= resource_id %>_r" value="1" <% if(readperm==1){ %> checked="checked"<%} %> /></td>
                    <td><input type="checkbox" name="c_<%= resource_id %>_w" value="1" <% if(deleteperm==1){ %> checked="checked"<%} %> <% if(resource_type=="View") { %> disabled<%} %> /></td>
                    <td><input type="checkbox" name="c_<%= resource_id %>_m" value="1" <% if(modifyperm==1){ %> checked="checked"<%} %> <% if(resource_type=="View") { %> disabled<%} %> /></td>
                    <td><input type="checkbox" name="c_<%= resource_id %>_i" value="1" <% if(insertperm==1){ %> checked="checked"<%} %> <% if(resource_type=="View") { %> disabled<%} %> /></td>
                    <td><% if(resource_id != selectedResultset.get_id()) { %><input type="checkbox" name="c_<%= resource_id %>_h" value="1" <% if(resource_header==1){ %> checked="checked"<%} %> /> <% } %></td>
                    <td><% if(resource_id != selectedResultset.get_id()) { %><input type="checkbox" name="c_<%= resource_id %>_s" value="1" <% if(resource_search==1){ %> checked="checked"<%} %> /> <% } %></td>
                    
                    <td><%
                    if(resource_id != selectedResultset.get_id()) {
                    	%> <select name="c_{resource_id}_g">
                    	<%
                        for ( Grouping grouping : groupings ) {
                            %> <option value=<%= grouping.get_id() %>                            
                            <%
                            if(resource_grouping == grouping.get_id()){ %>
                             " selected"<%
                            }
                            %>
                             ><%= grouping.get_name() %></option>
                            <%
                        }
                        %> </select><%
                    }
                    %></td>
                </tr>
                <%
                }
                %>
                <tr>
                    <td colspan="11">&nbsp;</td>
                </tr>
                

            </table>

             <table>
            <tr>
                    <td colspan="4">Tools (ALL MODIFY EXPORT IMPORT PREFERENCE ANALISYS)
                    </td>
                    <td colspan="4"><input type="text" name="tools" size="50" value="<%= selectedToolbar.get_tools() %>" /></td>
                </tr>
             </table>
            <table>
                <tr>
                    <td>Plugins</td>
                    <td>
                        <table>
                                <tr class="title">
                                    <td>id</td>
                                    <td>name</td>
                                    <td>configurationfile</td>
                                    <td>type</td>
                                    <td>note</td>
                                    <td>abilita per questo gruppo</td>
                                </tr>
                                <%

                                for (Plugin plugin : plugins ) {
																		 int id_plugin = plugin.get_id();
																		 String name = plugin.get_name();
																		 String configurationfile = plugin.get_configurationfile();
																		 String type = plugin.get_type();
																		 String note = plugin.get_note();
																		 int currPluginAssociation = 0;
																			 for (Pluginassociation pluginassociation : pluginAssociations ){
																				 if ( 
																						 	(pluginassociation.get_idgroup() == selectedGroup.get_id() ) 
																					 	&& 
																					 		(pluginassociation.get_idresultset() == selectedResultset.get_id() ) 
																					 	)																					 
																					 currPluginAssociation = 1;
																				 break;																					
																		 }
																 %>
                                <tr>
                                    <td><%= id_plugin %></td>
                                    <td><%= name %></td>
                                    <td><%= configurationfile %></td>
                                    <td><%= type %></td>
                                    <td><%= note %></td>
                                    <td><input type="checkbox" name="plugin_<%= id_plugin %>_ass" <% 
                                    if(currPluginAssociation == 1){
                                    	%>checked="checked"<% 
                                    	}%> /></td>
                                </tr>
                                <%
                                }
                                %>
                        </table>

                    </td>
                </tr>
        </table>
            <input type="submit" value="Submit" />
            <input type=button onclick="checkTutti()" value="Seleziona tutti" />
            <input type=button onclick="uncheckTutti()" value="Deseleziona tutti" />
            <input type=button onclick="setReadOnly()" value="sola lettura" />
            <input type=button onclick="setModifyOnly()" value="sola modifica" />
            <input type=button onclick="setDeleteOnly()" value="sola cancellazione" />
            <input type=button onclick="setInsertOnly()" value="solo inserimento" />
            <input type=button onclick="setHeaderOnly()" value="solo header" />
            <input type=button onclick="setSearchOnly()" value="sola ricerca" />
        </form>
    </body>
</html>
