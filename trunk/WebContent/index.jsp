<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="util.*"%>
<%

boolean db_conn = false; 
// se ho un db a cui connettermi, includo i vari files e imposto un flag
String mysql_host = "";
String mysql_database =  "";
String mysql_user = "";
String mysql_password  = "";

mysql_host = (String) session.getAttribute("mysql_host"); 
mysql_database = (String) session.getAttribute("mysql_database"); 
mysql_user = (String) session.getAttribute("mysql_user"); 
mysql_password = (String) session.getAttribute("mysql_password"); 

if (  session.getAttribute("db_conn") != null) {
		db_conn =  true;
}

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>
        <link rel="stylesheet" type="text/css" href="style.css" />
        <script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>
        <script type="text/javascript">
							 $(document).ready(function() {
							 $("div.section h2").next("form").slideToggle("slow");
							 $("div.section h2").toggleClass("not_visible");
						
							 $("div.section h2").click(function () {
							 $(this).next("form").slideToggle();
							 $(this).toggleClass("not_visible");
                });

            });

            function canc_user() {
                // conferma la richiesta di cancellazione di un utente
                conf = window.confirm('Confermi la cancellazione di questo utente? L\'operazione non può essere annullata');
                if(conf) return true;
            }

            function canc_group() {
                // conferma la richiesta di cancellazione di un gruppo 
                conf = window.confirm('Confermi la cancellazione di questo gruppo? L\'operazione non può essere annullata e tutti gli utenti del gruppo non avranno più accesso al sistema');
                if(conf) return true;
            }

            function canc_grouping() {
                // conferma la richiesta di cancellazione di un reggruppamento
                conf = window.confirm('Confermi la cancellazione di questo raggruppamento? L\'operazione non può essere annullata');
                if(conf) return true;
            }

            function canc_plugin() {
                // conferma la richiesta di cancellazione di un reggruppamento
                conf = window.confirm('Confermi la cancellazione di questo plugin? L\'operazione non può essere annullata');
                if(conf) return true;
            }
        </script>
    </head>
    <body>
        <form action="DispatcherServlet" method="post">
            Host: <input type="text" name="mysql_host" value="<%= mysql_host %>">
            DB user: <input type="text" name="mysql_user" value="<%= mysql_user %>">
            Password: <input type="password" name="mysql_password" value="<%= mysql_password %>">
            Database: <input type="text" name="mysql_database" value="<%= mysql_database %>">
            <input type="submit" name="ConnectSubmit">
        </form>
        <%
        if(!db_conn ){
            // non è stato selezionato un db quindi non stampo il resto della pagina
            %>
            </body></html>
            <%
            return;
        }
        %>
        <div class="section">
            <h1>Gestione Resultset</h1>

            <h2>Creazione Resultset (con impostazione permessi ad un gruppo)</h2>
            <form action="DispatcherServlet" method="POST">
                <input type="hidden" name="action" value="new" />
                name: <input type="text" name="resultset_name" /><br>
                alias: <input type="text" name="resultset_alias" /><br>
                statement: <input type="text" name="resultset_statement" />
                </p>
                <input type="submit" name="SubmitResultsetCreation" />
                <input type="submit" name="SubmitResultsetCreationAll"  value="Crea Tutti" />
            </form>

           <h2>Amministrazione Resultset/Gruppo</h2>
            <form action="DispatcherServlet" method="POST">
                <input type="hidden" name="action" value="old" />
                <select name="modifyResultsetSelect">
                    <% 
                    @SuppressWarnings("unchecked")
                    ArrayList <Resultset> resultsets = (ArrayList <Resultset>) session.getAttribute("resultsets");
                    for (Resultset resultset : resultsets ) {
											 int id = resultset.get_id();
											 String name = resultset.get_alias();
                        %>
                    <option value="<%= id %>">
                            <%= name %>
                    </option>
                    <% } %>
                </select>
                <input type="submit" name="SubmitResultsetModify" />
            </form>

            <h2>Eliminazione Resultset</h2>
            <form action="DispatcherServlet" method="POST">
                <input type="hidden" name="action" value="delete" />
                <select name="deleteResultsetSelect">
                    <% for (Resultset resultset : resultsets) {
										 int id = resultset.get_id();
										 String name = resultset.get_alias();
                        %>
                    <option value="<%= id %>">
                            <%= name %>
                    </option>
                    <% } %>
                </select>
                <input type="submit" name="SubmitResultsetDelete" />
            </form>
        </div>

        <div class="section">
            <h1>Gestione Utenti</h1>

            <h2>Creazione Utente</h2>
            <form action="DispatcherServlet" method="POST">
                <input type="hidden" name="action" value="new" />
                username: <input type="text" name="user_username" /><br />
                password: <input type="text" name="user_password" /><br />
                name: <input type="text" name="user_name" /><br />
                surname: <input type="text" name="user_surname" /><br />
                email: <input type="text" name="user_email" /><br />
                office: <input type="text" name="user_office" /><br />
                telephone: <input type="text" name="user_telephone" /><br />
                status: <select name="user_status">
                        <option>1</option>
                        <option>0</option>
                    </select><br />
                group: <select name="user_id_group">
                    <% 
                    ArrayList <Group> groups = (ArrayList <Group>) session.getAttribute("groups");
										for (Group group : groups ) {
													 int id = group.get_id();
													 String name = group.get_name();
													 int status = group.get_status();
                        if(status==1) { %>
                        <option value="<%= id %>"><%= name %></option><%
                        }
                    } %>
                </select>
                <input type="submit" name="SubmitUserCreation" />
            </form>

            <h2>Modifica Utente</h2>
            <form action="DispatcherServlet" method="POST">
                <input type="hidden" name="action" value="user_edit" />
                <select name=modifyUserSelect>
                    <% 
                   	ArrayList <User> users = (ArrayList <User>) session.getAttribute("users") ;                    	                    
									for (User user : users) {
											 int id = user.get_id();
											 String username = user.get_username();
											 int status = user.get_status();
											 String group_name = user.get_group_name();
	                        %>
                  <option value="<%= id %>"><%= username %></option>
                  <% }
								%>
                </select>
                <input type="submit" name="SubmitUserModify" />
            </form>

            <h2>Eliminazione Utente</h2>
            <form name="delete_user" action="DispatcherServlet" method="POST" onsubmit="return canc_user();">
                <input type="hidden" name="action" value="user_delete" />
                <select name="deleteUserSelect">
                    <% for (User user : users ) {
												 int id = user.get_id();
												 String username = user.get_username();
												 int status = user.get_status();
												 String group_name = user.get_group_name();
                        %>
                    <option value="<%= id %>"><%= username %></option>
                    <% } %>
                </select>
                <input type="submit" name="SubmitUserDelete" />
            </form>
        </div>

        <div class="section">

            <h1>Gestione Gruppi</h1>

            <h2>Creazione Gruppo</h2>
            <form action="DispatcherServlet" method="POST">
                <input type="hidden" name="action" value="new" />
                name: <input type="text" name="group_name" /><br />
                status: <select name="group_status">
                        <option>1</option>
                        <option>0</option>
                    </select><br />
                </p>
                <input type="submit" name="SubmitGroupCreation" />
            </form>

            <h2>Modifica Gruppo</h2>
            <form action="DispatcherServlet" method="POST">
                <input type="hidden" name="action" value="group_edit" />
                <select name="modifyGroupSelect">
                    <% 
                    for (Group group : groups ) {
											 int id = group.get_id();
											 String name = group.get_name();
											 int status = group.get_status();
                        %>
                    <option value="<%= id %>"><%= name %></option>
                    <% } %>
                </select>
                <input type="submit" name="SubmitGroupModify" />
                <input type="submit" name="SubmitGroupAllPrivileges"  value="assegna tutti i privilegi" />                
            </form>

            <h2>Eliminazione Gruppo</h2>
            <form name="delete_group" action="DispatcherServlet" method="POST" onsubmit="return canc_group();">
                <input type="hidden" name="action" value="group_delete" />
                <select name="deleteGroupSelect">
                    <% for (Group group : groups ) {
                    	int id = group.get_id();
												 String name = group.get_name();
												 int status = group.get_status();
                        %>
                    <option value="<%= id %>"><%= name %></option>
                    <% } %>
                </select>
                <input type="submit" name="SubmitGroupDelete" />
            </form>
        </div>

        <div class="section">
            <h1>Gestione Plugin</h1>

            <h2>Creazione Plugin</h2>
            <form action="DispatcherServlet" method="POST">
                <input type="hidden" name="action" value="new" />
                name: <input type="text" name="plugin_name" /><br />
                configurationfile: <input type="text" name="plugin_configurationfile" /><br/>
                type: <select name="plugin_type" >
                    <option>link</option>
                    <option>single</option>
                </select>
                <br />
                note: <input type="text" name="plugin_note" /><br />
                </p>
                <input type="submit" name="SubmitPluginCreation" />
            </form>

            <h2>Modifica Plugin</h2>
            <form action="DispatcherServlet" method="POST">
                <input type="hidden" name="action" value="plugin_edit" />
                <select name="plugin_id">
                    <% 
                    ArrayList <Plugin> plugins = (ArrayList <Plugin>) session.getAttribute("plugins");
                    for (Plugin plugin : plugins) {
											 int id = plugin.get_id();
											 String name = plugin.get_name();
//                        configurationfile = plugin.get_configurationfile();
//                        type = plugin.get_type();
//                        note = plugin.get_note();
                        %>
                    <option value="<%= id %>"><%= "name" %></option>
                    <% } %>
                </select>
                <input type="submit" name="SubmitPluginModify" />
            </form>

            <h2>Eliminazione Plugin</h2>
            <form name="delete_plugin" action="DispatcherServlet" method="POST" onsubmit="return canc_plugin();">
                <input type="hidden" name="action" value="plugin_delete" />
                <select name="plugin_id">
                    <% for (Plugin plugin : plugins) {
												 int id = plugin.get_id();
												 String name = plugin.get_name();
//                        configurationfile = plugin.get_configurationfile();
//                        type = plugin.get_type();
//                        note = plugin.get_note();
                        %>
                    <option value="<%= id %>"><%= name %></option>
                    <% } %>
                </select>
                <input type="submit" name="SubmitPluginDelete" />
            </form>
        </div>

        <div class="section">
            <h1>Gestione Raggruppamenti</h1>

            <h2>Creazione Raggruppamento</h2>
            <form action="DispatcherServlet" method="POST">
                <input type="hidden" name="action" value="new" />
                name: <input type="text" name="grouping_name" /><br />
                alias: <input type="text" name="grouping_alias" /><br />
                </p>
                <input type="submit" name="SubmitGroupingCreation" />
            </form>

            <h2>Modifica Raggruppamento</h2>
            <form action="DispatcherServlet" method="POST">
                <input type="hidden" name="action" value="grouping_edit" />
                <select name="modifyGroupingSelect">
                    <% 
                    ArrayList <Grouping> groupings = (ArrayList <Grouping>) session.getAttribute("groupings");
                    for (Grouping grouping: groupings) {
											 int id = grouping.get_id();
											 String name = grouping.get_name();
											 String alias = grouping.get_alias();
                        %>
                    <option value="<%= id %>"><%= alias %></option>
                    <% } %>
                </select>
                <input type="submit" name="SubmitGroupingModify" />
            </form>

            <h2>Eliminazione Raggruppamento</h2>
            <form name="delete_grouping" action="DispatcherServlet" method="POST" onsubmit="return canc_grouping();">
                <input type="hidden" name="action" value="grouping_delete" />
                <select name="deleteGroupingSelect">
                    <% for (Grouping grouping: groupings) {
											 int id = grouping.get_id();
											 String name = grouping.get_name();
											 String alias = grouping.get_alias();
                        %>
                    <option value="<%= id %>"><%= alias %></option>
                    <% } %>
                </select>
                <input type="submit" name="SubmitGroupingDelete" />
            </form>
        </div>


		<div class="section">

		<h1>Gestione Notifiche</h1>
		
		<h2>Creazione notifica</h2>
            <form action="DispatcherServlet" method="POST">
                <input type="hidden" name="action" value="new" />
                resultset: <select name="resultset_id">
                    <% for (Resultset resultset : resultsets) {
											 int id = resultset.get_id();
											 String name = resultset.get_alias();
                        %>
                    <option value="<%= id %>">
                            <%= name %>
                    </option>
                    <% } %>
                </select><br>
                baseModelData id: <input type="text" name="bmdid" /><br>
                name: <input type="text" name="notify_name" /><br>
                address statement: <input type="text" name="address_statement" /><br>
                data statement: <input type="text" name="data_statement" /><br>
                xslt: <input type="text" name="xslt" />
				</p>
                <input type="submit" name="SubmitNotifyCreation" />
            </form>
			<%  
				ArrayList <Notify> notifies = (ArrayList <Notify>) session.getAttribute("notifies");
					//resultsets = get_notify()
					%>
            <h2>Eliminazione notifica</h2>
            <form action="DispatcherServlet" method="POST">
                <input type="hidden" name="action" value="delete" />
                <select name="id">
                    <% for (Notify notify : notifies) {
												 int id = notify.get_id_notify();
												 String name = notify.get_notify_name();
                        %>
                    <option value="<%= id %>">
                            <%= name %>
                    </option>
                    <% } %>
                </select>
                <input type="submit" name="SubmitNotifyDelete" />
            </form>
		</div>
    </body>
</html>
