package pkg1;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//import org.apache.catalina.Session;

import util.*;
/**
 * Servlet implementation class DispatcherServlet
 */
public class DispatcherServlet extends HttpServlet {
	Db_utils dbUtils = new Db_utils();
	String mysql_host;
	String mysql_user;
	String mysql_database;
	String mysql_password;
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DispatcherServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    public void init (){
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		try {
			if (request.getParameter("refreshIndex")!=null) {
				this.updateSessionData(request, response, session);
			}
			response.sendRedirect("index.jsp");
		} catch (Exception e) {
			session.setAttribute("printedMsg", "Errore: " +e.getMessage());
			response.sendRedirect("msgPage.jsp");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		HttpSession session = request.getSession();
		doDispatch(request, response, session);
	}

	protected void doDispatch(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException {
		try {
			if (request.getParameter("ConnectSubmit")!=null) {
				doHandleCommitSubmit (request,response, session);
			} else if (request.getParameter("SubmitResultsetCreation")!=null) {
				doSubmitResultsetCreation (request,response, session);
			} else if (request.getParameter("SubmitResultsetModify")!=null) {
				doSubmitResultsetModify (request,response, session);
			} else if (request.getParameter("SubmitResultsetModifyDo")!=null) {
				doSubmitResultsetModifyDo (request,response, session);
			} else if (request.getParameter("SubmitModifyPermissionResultsetOrGroup")!=null) {
				doSubmitResultsetModifyChangeGroup(request, response, session) ;
			} else if (request.getParameter("SubmitResultsetDelete")!=null) {
				doSubmitResultsetDelete (request,response, session);
			} else if (request.getParameter("SubmitUserCreation")!=null) {
				doSubmitUserCreation (request,response, session);
			} else if (request.getParameter("SubmitUserModify")!=null) {
				doSubmitUserModify (request,response, session);
			} else if (request.getParameter("SubmitUserModifyDo")!=null) {
				doSubmitUserModifyDo (request,response, session);
			} else if (request.getParameter("SubmitUserDelete")!=null) {
				doSubmitUserDelete (request,response, session);
			} else if (request.getParameter("SubmitGroupCreation")!=null) {
				doSubmitGroupCreation (request,response, session);
			} else if (request.getParameter("SubmitGroupModify")!=null) {
				doSubmitGroupModify (request,response, session);
			} else if (request.getParameter("SubmitGroupModifyDo")!=null) {
				doSubmitGroupModifyDo (request,response, session);
			} else if (request.getParameter("SubmitGroupDelete")!=null) {
				doSubmitGroupDelete (request,response, session);		
			} else if (request.getParameter("SubmitGroupingCreation")!=null) {
				doSubmitGroupingCreation (request,response, session);
			} else if (request.getParameter("SubmitGroupingModify")!=null) {
				doSubmitGroupingModify (request,response, session);
			} else if (request.getParameter("SubmitGroupingModifyDo")!=null) {
				doSubmitGroupingModifyDo (request,response, session);
			} else if (request.getParameter("SubmitGroupingDelete")!=null) {
				doSubmitGroupingDelete (request,response, session);
			} else if (request.getParameter("SubmitPluginCreation")!=null) {
				doSubmitPluginCreation (request,response, session);
			} else if (request.getParameter("SubmitPluginModify")!=null) {
				doSubmitPluginModify (request,response, session);
			} else if (request.getParameter("SubmitPluginDelete")!=null) {
				doSubmitPluginDelete (request,response, session);
			} else if (request.getParameter("SubmitNotifyCreation")!=null) {
				doSubmitNotifyCreation (request,response, session);
			} else if (request.getParameter("SubmitNotifyDelete")!=null) {
				doSubmitNotifyDelete (request,response, session);
			}
		} catch (Exception e) {
			e.printStackTrace();				   
			session.setAttribute("printedMsg", "Errore: " +e.getMessage());
			response.sendRedirect("msgPage.jsp");
		}
	}

	
	private void doSubmitNotifyCreation (HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException, CustomException, NumberFormatException {
		 String id_resultset = request.getParameter("resultset_id");
		 String notify_name = request.getParameter("notify_name");
		 String address_statement = request.getParameter("address_statement");
		 String data_statement = request.getParameter("data_statement");
		 String xslt = request.getParameter("xslt");
		 String bmdid = request.getParameter("bmdid");
		 Notify notify = new Notify(Integer.parseInt(id_resultset), notify_name, address_statement, data_statement, xslt, 0, bmdid);           
	     dbUtils.insert_notify(notify);
		 session.setAttribute("printedMsg", "Notifica " + notify_name + " inserito con successo");
		 session.setAttribute("titleMsg", "Risultato: ");
		 response.sendRedirect("msgPage.jsp");
	}	
	
	private void doSubmitNotifyDelete (HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException,  CustomException, NumberFormatException{
		 String notify_id = request.getParameter("deleteNotifySelect");	
		 dbUtils.delete_notify(Integer.parseInt(notify_id));	
		 session.setAttribute("printedMsg", "Notifica cancellata con successo");
		 response.sendRedirect("msgPage.jsp");
	}	

	

/////////////////////////////////////	
	
	
	private void doSubmitPluginCreation (HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException, CustomException, NumberFormatException {
		 String plugin_id = request.getParameter("plugin_id");
		 String plugin_name = request.getParameter("plugin_name");
		 String plugin_type = request.getParameter("plugin_type");
		 String plugin_configurationfile = request.getParameter("plugin_configurationfile");
		 String plugin_note = request.getParameter("plugin_note");
		 dbUtils.insert_plugin(plugin_name, plugin_configurationfile, plugin_type, plugin_note);
		 session.setAttribute("printedMsg", "Plugin " + plugin_name + " inserito con successo");
		 session.setAttribute("titleMsg", "Risultato: ");
		 response.sendRedirect("msgPage.jsp");
	}	

	private void doSubmitPluginModify (HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException, CustomException, NumberFormatException{
		 String plugin_id = request.getParameter("modifyPluginSelect");
		 Plugin plugin = dbUtils.get_plugin( Integer.parseInt(plugin_id));
		 session.setAttribute("plugin", plugin);
		 response.sendRedirect("manage_plugins.jsp");
	}		
	
	private void doSubmitPluginModifyDo (HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException,  CustomException, NumberFormatException{
		 Plugin plugin = (Plugin) session.getAttribute("plugin");
		 int plugin_id = plugin.get_id();
		 String plugin_name = request.getParameter("plugin_name");
		 String plugin_type = request.getParameter("plugin_type");
		 String plugin_configurationfile = request.getParameter("plugin_configurationfile");
		 String plugin_note = request.getParameter("plugin_note");
		 dbUtils.edit_plugin(plugin_id, plugin_name, plugin_configurationfile, plugin_type, plugin_note);
		 session.removeAttribute("plugin");
		 this.updateSessionData(request, response, session);
		 session.setAttribute("printedMsg", "Plugin " + plugin_name + " modificato con successo");
		 response.sendRedirect("msgPage.jsp");
	}	
	
	private void doSubmitPluginDelete (HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException,  CustomException, NumberFormatException{
		 String plugin_id = request.getParameter("deletePluginSelect");		
		 Plugin plugin = dbUtils.get_plugin( Integer.parseInt(plugin_id));
		 dbUtils.delete_plugin(Integer.parseInt(plugin_id));	
		 session.setAttribute("printedMsg", "Plugin " + plugin.get_name() + " cancellato con successo");
		 response.sendRedirect("msgPage.jsp");
	}	


	
	
	
	
	
	
	
	
////////////////////////////////////

	private void doSubmitUserCreation (HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException, CustomException, NumberFormatException {
		 String user_username = request.getParameter("user_username");
		 String user_password = request.getParameter("user_password");
		 String user_name = request.getParameter("user_name");
		 String user_surname = request.getParameter("user_surname");
		 String user_email = request.getParameter("user_email");
		 String user_office = request.getParameter("user_office");
		 String user_telephone = request.getParameter("user_telephone");
		 String user_status = request.getParameter("user_status");
		 String user_id_group = request.getParameter("user_id_group");
		 dbUtils.insert_user(user_username, user_password, user_name, user_surname,
				  user_email, user_office, user_telephone, Integer.parseInt(user_status), Integer.parseInt(user_id_group)); 
		 session.setAttribute("printedMsg", "User " + user_name + " inserito con successo");
		 session.setAttribute("titleMsg", "Risultato: ");
		 response.sendRedirect("msgPage.jsp");
	}	

	private void doSubmitUserModify (HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException, CustomException, NumberFormatException{
		 String user_id = request.getParameter("modifyUserSelect");
		 User user = dbUtils.get_user( Integer.parseInt(user_id));
		 session.setAttribute("user", user);
		 response.sendRedirect("manage_users.jsp");
	}		
	
	

	
	private void doSubmitUserModifyDo (HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException,  CustomException, NumberFormatException{
		 User user = (User) session.getAttribute("user");
		 int user_id = user.get_id();
		 String user_username = request.getParameter("user_username");
		 String user_password = request.getParameter("user_password");
		 String user_name = request.getParameter("user_name");
		 String user_surname = request.getParameter("user_surname");
		 String user_email = request.getParameter("user_email");
		 String user_office = request.getParameter("user_office");
		 String user_telephone = request.getParameter("user_telephone");
		 String user_status = request.getParameter("user_status");
		 String user_id_group = request.getParameter("user_id_group");
		 dbUtils.edit_user(user_id, user_username, user_name, user_surname,
				  user_email, user_office, user_telephone, 
				  Integer.parseInt(user_status), Integer.parseInt(user_id_group), 
				  user_password);
		 session.removeAttribute("user");
		 this.updateSessionData(request, response, session);
		 session.setAttribute("printedMsg", "User " + user_name + " modificato con successo");
		 response.sendRedirect("msgPage.jsp");
	}	
	
	private void doSubmitUserDelete (HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException,  CustomException, NumberFormatException{
		 String user_id = request.getParameter("deleteUserSelect");		
		 User user = dbUtils.get_user( Integer.parseInt(user_id));
		 dbUtils.delete_user(Integer.parseInt(user_id));	
		 session.setAttribute("printedMsg", "User " + user.get_name() + " cancellato con successo");
		 response.sendRedirect("msgPage.jsp");
	}	

	
	private void doSubmitGroupCreation (HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException, CustomException, NumberFormatException {
		 String group_name = request.getParameter("group_name");
		 String group_status = request.getParameter("group_status");
		 dbUtils.insert_group(group_name, Integer.parseInt(group_status));
		 session.setAttribute("printedMsg", "Gruppo " + group_name + " inserito con successo");
		 session.setAttribute("titleMsg", "Risultato: ");
		 response.sendRedirect("msgPage.jsp");
	}	

	private void doSubmitGroupModify (HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException, CustomException, NumberFormatException{
		 String group_id = request.getParameter("modifyGroupSelect");
		 Group group = dbUtils.get_group( Integer.parseInt(group_id));
		 session.setAttribute("group", group);
		 response.sendRedirect("manage_groups.jsp");
	}		
	
	private void doSubmitGroupModifyDo (HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException,  CustomException, NumberFormatException{
		 Group group = (Group) session.getAttribute("group");
		 int group_id = group.get_id();
		 String group_name = request.getParameter("group_name");
		 int group_status = Integer.parseInt(request.getParameter("group_status"));
		 int group_old_status = group.get_status();
		 dbUtils.edit_group(group_id, group_name, group_status, group_old_status);
		 session.removeAttribute("group");
		 this.updateSessionData(request, response, session);
		 session.setAttribute("printedMsg", "Gruppo " + group_name + " modificato con successo");
		 response.sendRedirect("msgPage.jsp");
	}	
	
	private void doSubmitGroupDelete (HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException,  CustomException, NumberFormatException{
		 String group_id = request.getParameter("deleteGroupSelect");		
		 Group group = dbUtils.get_group( Integer.parseInt(group_id));
		 dbUtils.delete_groups(Integer.parseInt(group_id));	
		 session.setAttribute("printedMsg", "Gruppo " + group.get_name() + " cancellato con successo");
		 response.sendRedirect("msgPage.jsp");
	}	

	
	private void doSubmitGroupingCreation (HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException, CustomException, NumberFormatException {
		 String grouping_name = request.getParameter("grouping_name");
		 String grouping_alias = request.getParameter("grouping_alias");
		 dbUtils.insert_grouping(grouping_name, grouping_alias);

		 session.setAttribute("printedMsg", "Raggruppamento " + grouping_alias + " inserito con successo");
		 session.setAttribute("titleMsg", "Risultato: ");
		 response.sendRedirect("msgPage.jsp");
	}	

	private void doSubmitGroupingModify (HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException, CustomException, NumberFormatException{
		 String grouping_id = request.getParameter("modifyGroupingSelect");
		 Grouping grouping = dbUtils.get_grouping( Integer.parseInt(grouping_id));
		 session.setAttribute("grouping", grouping);
		 response.sendRedirect("manage_groupings.jsp");
	}		
	
	private void doSubmitGroupingModifyDo (HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException,  CustomException, NumberFormatException{
		 String grouping_name = request.getParameter("grouping_name");
		 String grouping_alias = request.getParameter("grouping_alias");
		 Grouping grouping = (Grouping) session.getAttribute("grouping");
		 int grouping_id = grouping.get_id();
		 dbUtils.edit_grouping(grouping_id, grouping_name, grouping_alias);
		 session.removeAttribute("grouping");
		 this.updateSessionData(request, response, session);
		 session.setAttribute("printedMsg", "Raggruppamento " + grouping_name + " modificato con successo");
		 response.sendRedirect("msgPage.jsp");
	}	
	
	private void doSubmitGroupingDelete (HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException,  CustomException, NumberFormatException{
		 String grouping_id = request.getParameter("deleteGroupingSelect");		
		 Grouping grouping = dbUtils.get_grouping( Integer.parseInt(grouping_id));
		 dbUtils.delete_grouping(Integer.parseInt(grouping_id));	
		 session.setAttribute("printedMsg", "Raggruppamento " + grouping.get_alias() + " cancellato con successo");
		 response.sendRedirect("msgPage.jsp");
	}	

/////////////////////////////
	
	private void doSubmitResultsetCreation (HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException, CustomException, NumberFormatException {
		 String resultset_name = request.getParameter("resultset_name");
		 String resultset_alias = request.getParameter("resultset_alias");
		 String resultset_statement = request.getParameter("resultset_statement");
	
		 
		 dbUtils.createResultSet( resultset_name,  resultset_alias,  resultset_statement);

		 session.setAttribute("printedMsg", "Resultset " + resultset_alias + " inserito con successo");
		 session.setAttribute("titleMsg", "Risultato: ");
		 response.sendRedirect("msgPage.jsp");
	}	


	
	
	private void doSubmitResultsetModify (HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException, CustomException, NumberFormatException{
		 String resultset_id = request.getParameter("modifyResultsetSelect");
		 Resultset resultset = dbUtils.get_resultset( Integer.parseInt(resultset_id));
		 session.setAttribute("resultset", resultset);
		 response.sendRedirect("manage_resources.jsp");
	}		

	private void doSubmitResultsetModifyChangeGroup (HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException, CustomException, NumberFormatException{
		 String selectedResultsetIdStr = request.getParameter("modifyPermissionsResultsetSelect");
		 int selectedResultsetId = Integer.parseInt(selectedResultsetIdStr);
		 Resultset selectedResultset = dbUtils.get_resultset(selectedResultsetId);
		 session.setAttribute("selectedResultset", selectedResultset);
		 
		 String selectedGroupIdStr = request.getParameter("modifyPermissionsGroupSelect");
		 int selectedGroupId = Integer.parseInt(selectedGroupIdStr);
		 Group selectedGroup = dbUtils.get_group( selectedGroupId);
		 session.setAttribute("selectedGroup", selectedGroup);

		 
		 ArrayList <ResourceWithGroupPermissions> selectedResources = dbUtils.get_fields_with_permissions_from_resultsetid(selectedResultset.get_id());
		 session.setAttribute("selectedResources", selectedResources);

		 ArrayList <Pluginassociation> pluginAssociations = dbUtils.get_pluginassociation(selectedResultsetId, selectedGroupId);
		 session.setAttribute("pluginAssociations", pluginAssociations);
		  		 
		 response.sendRedirect("manage_ressources.jsp");
	}		
	
	private void doSubmitResultsetModifyDo (HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException,  CustomException, NumberFormatException{
		 String resultset_name = request.getParameter("resultset_name");
		 String resultset_alias = request.getParameter("resultset_alias");
		 Resultset resultset = (Resultset) session.getAttribute("resultset");
		 int resultset_id = resultset.get_id();
// 
		 /*
		  * 
		  *  DA RISCRIVERE 
		  *    
		  */
		 
		 
	//	 dbUtils.edit_resultset(resultset_id, resultset_name, resultset_alias);
		 session.removeAttribute("resultset");
		 this.updateSessionData(request, response, session);
		 session.setAttribute("printedMsg", "Raggruppamento " + resultset_name + " modificato con successo");
		 response.sendRedirect("msgPage.jsp");
	}	
	
	private void doSubmitResultsetDelete (HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException,  CustomException, NumberFormatException{
		 String resultset_id = request.getParameter("deleteResultsetSelect");		
		 Resultset resultset = dbUtils.get_resultset( Integer.parseInt(resultset_id));
		 dbUtils.remove_resultset_complete_by_id(Integer.parseInt(resultset_id));	
		 session.setAttribute("printedMsg", "ResultSet " + resultset.get_alias() + " cancellato con successo");
		 response.sendRedirect("msgPage.jsp");
	}	

	
	
	
	private void managePermissions (HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException,  CustomException, NumberFormatException {
		 String group_id = request.getParameter("group_id");
		 String tools = (request.getParameter("tools")).toUpperCase();
		 String resultset_id = request.getParameter("resultset_id");
// 		 String resource_list_UPDATE = array();
//		
//		    for(_REQUEST AS key => value) {
//		        if (preg_match('/^c_(\d*)_(\w+)/', key, pin)) {
//							 String {'res_'.pin[1]}("id") = pin[1];
//							 String {'res_'.pin[1]}[pin[2]] = value;
//							 String resource_list_UPDATE[pin[1]] = {'res_'.pin[1]};
//		        }
//		    }
//		
//		    /*
//		    * per controllare se tutti i permessi sono impostati a 0,
//		    * caso in cui devo cancellare tutte le righe in __system_management,
//		    * imposto un flag e creo un array dove inserire gli id delle righe
//		    * appena create per poterle (eventualmente) cancellare alla fine
//		    */
//		
//		String permissions = 0;
//		String array_insert = array();
//		
//		    %> <pre>Inserting permissions... 
//		    <%
//		    for (resource_list_UPDATE as resource) {
//							 String id = resource("id");
//							 String r = 0 + resource("r");
//							 String w = 0 + resource("w");
//							 String m = 0 + resource("m");
//							 String i = 0 + resource("i");
//							 String alias = resource("a");
//							 String header = resource("h");
//							 String search = resource("s");
//							 String grouping=resource("g");
//		        if(search=="") search = 0;
//		        if(header=="") header = 0;
//		//        echo "<pre>Inserting id (alias) permissions [rwmi] ... ";
//		        // cancello eventuali permessi inseriti precedentemente
//		String connection = db_get_connection();
//		String query_del = "delete from T_MANAGEMENT where id_resource = id and id_group = group_id";
//		String risu_del = mysql_query(query_del);
//		        // modifico l'alias della risorsa
//		String query_upd = "update T_RESOURCE set alias = 'alias' where id = id";
//		String risu_upd = mysql_query(query_upd);
//		        mysql_close(connection);
//		        // inserisco i permessi
//		String array_insert[] = insert_management_permissions(group_id, id, r, w, m, i);
//		        // inserisco le proprietà della risorsa in _field
//		        if(header!="" || search!="" || grouping!="") edit_field(id, header, search, grouping);
//		//
//		        if(r==1||w==1||m==1||i==1) permissions = 1;
//		    }
//		    %> <b>done</b></pre><%
//		    //       	echo insert_management_toolbar(group_id, resultset_id, tools);
//		    // INSERIRE CODICE SALVATAGGIO TOOLBAR
//		String toolbar = new toolbar(group_id, resultset_id, tools);
//		    insert_tool(toolbar);
//		//print_r(_POST);
//		    // CODICE SALVATAGGIO ASSOCIAZIONI PLUGIN
//		String plugins = get_plugins();
//		    for (plugins as plugin) {
//		        if ( request.getParameter("plugin_'.plugin.get_id().'_ass") == 'on') {
//		            insert_pluginassociation(plugin.get_id(), resultset_id, group_id);
//		        } else {
//		            delete_pluginassociation(plugin.get_id(), resultset_id, group_id);
//		        }
//		    }
//		    // se i permessi sono tutti 0 e i tools sono vuoti, cancello le righe da system_management
//		    if(permissions==0 && trim(tools)=="") {
//		        for(array_insert as id_insert) {
//					String connection = db_get_connection();
//					String query_del = "delete from T_MANAGEMENT where id = id_insert";
//					String risu_del = mysql_query(query_del);
//		        }
//		        %> 
//		        <pre><b>Permessi e tools vuoti, elimino i record relativi ai permessi</b></pre> 
//		        <% %>
//		    }

	}

	
	
	
	
	
	
	private void updateSessionData (HttpServletRequest request, HttpServletResponse response, HttpSession session)throws ServletException, IOException, CustomException {
		   ArrayList <Resultset> resultsets = dbUtils.get_resultsets();
		   session.setAttribute("resultsets", resultsets);
		   ArrayList <User> users = dbUtils.get_users();
		   session.setAttribute("users", users);
		   ArrayList <Group> groups = dbUtils.get_groups();
		   session.setAttribute("groups", groups);
		   ArrayList <Plugin> plugins = dbUtils.get_plugins(0);
		   session.setAttribute("plugins", plugins);
		   ArrayList <Grouping> groupings = dbUtils.get_groupings(0);
		   session.setAttribute("groupings", groupings);
		   ArrayList <Notify> notifies = dbUtils.get_notify();
		   session.setAttribute("notifies", notifies);	
	}	
	
	private void doHandleCommitSubmit (HttpServletRequest request, HttpServletResponse response, HttpSession session)throws ServletException, IOException {
			try {
			  open_db_get_connection(request);	
			  updateSessionData (request, response, session);   
				   response.sendRedirect("index.jsp");
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				session.setAttribute("printedMsg", "Errore DB: " + e.getMessage());
				response.sendRedirect("msgPage.jsp");
			}

	}

	
	private String getAndEmptyCheckTextHTmlField (HttpServletRequest request, String fieldName) throws CustomException{
		 String appo = (String) request.getParameter (fieldName);
		 if  (appo == null) 			throw new CustomException(fieldName + " nullo!!");
		 if  (appo.compareTo("") == 0) 	throw new CustomException(fieldName + " vuoto !!");
		 return appo;
	}
	
	private void open_db_get_connection(HttpServletRequest request) throws CustomException {
		 //mysql_driver = "com.mysql.jdbc.Driver";

		String mysql_host = getAndEmptyCheckTextHTmlField (request, "mysql_host");
		 String mysql_database = getAndEmptyCheckTextHTmlField (request, "mysql_database");
		 String mysql_user = getAndEmptyCheckTextHTmlField (request, "mysql_user");
		 String mysql_password = getAndEmptyCheckTextHTmlField (request, "mysql_password");
				 
		 this.mysql_host = mysql_host;
		 this.mysql_database = mysql_database;
		 this.mysql_user = mysql_user;
		 this.mysql_password = mysql_password;
		 
		 HttpSession session = request.getSession();
		 session.setAttribute("mysql_host", mysql_host);
	     session.setAttribute("mysql_database", mysql_database);
	     session.setAttribute("mysql_user", mysql_user);
	     session.setAttribute("mysql_password", mysql_password);
	     dbUtils.createConnection (mysql_host, mysql_database, mysql_user, mysql_password );
		 session.setAttribute("db_conn", "1");
	}
 

	        
	        
}
