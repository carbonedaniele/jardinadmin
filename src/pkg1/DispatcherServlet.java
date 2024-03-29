package pkg1;

import java.io.IOException;
//import java.io.PrintWriter;
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
       
    /**ripro
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
			if (requestSanitizedGetParameter ( request, "refreshIndex")!=null) {
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
		//PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		HttpSession session = request.getSession();
		doDispatch(request, response, session);
	}

	protected void doDispatch(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException {
		try {
			//printParameterEnumeration (request, response, session);
			if (requestSanitizedGetParameter ( request, "ConnectSubmit")!=null) {
				doHandleCommitSubmit (request,response, session);
			} else if (requestSanitizedGetParameter ( request, "SubmitResultsetCreation")!=null) {
				doSubmitResultsetCreation (request,response, session);
			} else if (requestSanitizedGetParameter ( request, "SubmitResultsetCreationAll")!=null) {
				doSubmitResultsetCreationAll (request,response, session);
			} else if (requestSanitizedGetParameter ( request, "SubmitResultsetModify")!=null) {
				doSubmitResultsetModify (request,response, session);
			} else if (requestSanitizedGetParameter ( request, "SubmitPermissionsModifyDo")!=null) {
				doSubmitPermissionsModify (request,response, session);
			} else if (requestSanitizedGetParameter ( request, "SubmitResultsetModifyChangeGroup")!=null) {
				doSubmitResultsetModifyChangeGroup(request, response, session) ;
			} else if (requestSanitizedGetParameter ( request, "SubmitResultsetDelete")!=null) {
				doSubmitResultsetDelete (request,response, session);
			} else if (requestSanitizedGetParameter ( request, "SubmitResultsetDeleteAll")!=null) {
				doSubmitResultsetDeleteAll (request,response, session);
			} else if (requestSanitizedGetParameter ( request, "SubmitUserCreation")!=null) {
				doSubmitUserCreation (request,response, session);
			} else if (requestSanitizedGetParameter ( request, "SubmitUserModify")!=null) {
				doSubmitUserModify (request,response, session);
			} else if (requestSanitizedGetParameter ( request, "SubmitUserModifyDo")!=null) {
				doSubmitUserModifyDo (request,response, session);
			} else if (requestSanitizedGetParameter ( request, "SubmitUserDelete")!=null) {
				doSubmitUserDelete (request,response, session);
			} else if (requestSanitizedGetParameter ( request, "SubmitGroupCreation")!=null) {
				doSubmitGroupCreation (request,response, session);
			} else if (requestSanitizedGetParameter ( request, "SubmitGroupModify")!=null) {
				doSubmitGroupModify (request,response, session);
			} else if (requestSanitizedGetParameter ( request, "SubmitGroupAllPrivileges")!=null) {
				doSubmitGroupAllPrivileges (request,response, session);
			} else if (requestSanitizedGetParameter ( request, "SubmitGroupModifyDo")!=null) {
				doSubmitGroupModifyDo (request,response, session);
			} else if (requestSanitizedGetParameter ( request, "SubmitGroupDelete")!=null) {
				doSubmitGroupDelete (request,response, session);		
			} else if (requestSanitizedGetParameter ( request, "SubmitGroupingCreation")!=null) {
				doSubmitGroupingCreation (request,response, session);
			} else if (requestSanitizedGetParameter ( request, "SubmitGroupingModify")!=null) {
				doSubmitGroupingModify (request,response, session);
			} else if (requestSanitizedGetParameter ( request, "SubmitGroupingModifyDo")!=null) {
				doSubmitGroupingModifyDo (request,response, session);
			} else if (requestSanitizedGetParameter ( request, "SubmitGroupingDelete")!=null) {
				doSubmitGroupingDelete (request,response, session);
			} else if (requestSanitizedGetParameter ( request, "SubmitPluginCreation")!=null) {
				doSubmitPluginCreation (request,response, session);
			} else if (requestSanitizedGetParameter ( request, "SubmitPluginModify")!=null) {
				doSubmitPluginModify (request,response, session);
			} else if (requestSanitizedGetParameter ( request, "SubmitPluginModifyDo")!=null) {
				doSubmitPluginModifyDo (request,response, session);
			} else if (requestSanitizedGetParameter ( request, "SubmitPluginDelete")!=null) {
				doSubmitPluginDelete (request,response, session);
			} else if (requestSanitizedGetParameter ( request, "SubmitNotifyCreation")!=null) {
				doSubmitNotifyCreation (request,response, session);
			} else if (requestSanitizedGetParameter ( request, "SubmitNotifyDelete")!=null) {
				doSubmitNotifyDelete (request,response, session);
			} else if (requestSanitizedGetParameter ( request, "GroupAndResultSetChoiceForm")!=null) {
				doSubmitResultsetModifyChangeGroup (request,response, session);
			}
		} catch (Exception e) {
			e.printStackTrace();				   
			session.setAttribute("printedMsg", "Errore: " +e.getMessage());
			response.sendRedirect("msgPage.jsp");
		}
	}
	
	private void doSubmitResultsetDeleteAll(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws ServletException, CustomException, IOException {
		dbUtils.delete_All_resultset();
		session.setAttribute("printedMsg", "Cancellati Tutti i ResultSet " );
		session.setAttribute("titleMsg", "Risultato: ");
		response.sendRedirect("msgPage.jsp");
	}

	private void doSubmitGroupAllPrivileges(HttpServletRequest request,
			HttpServletResponse response, HttpSession session)throws ServletException, IOException, CustomException, NumberFormatException {
		 String group_idStr = requestSanitizedGetParameter ( request, "modifyGroupSelect");
		 int group_id = Integer.parseInt(group_idStr);
		 dbUtils.insert_All_management_permissions(group_id);
		 refreshIndex(request, response, session);
	}

	private void doSubmitResultsetCreationAll(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws ServletException,  CustomException, IOException{
		dbUtils.insertAllResultSets();
		session.setAttribute("printedMsg", "Creati Tutti i ResultSet " );
		session.setAttribute("titleMsg", "Risultato: ");
		response.sendRedirect("msgPage.jsp");
	}

	private void doSubmitNotifyCreation (HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException, CustomException, NumberFormatException {
		 String id_resultset = requestSanitizedGetParameter ( request, "resultset_id");
		 String notify_name = requestSanitizedGetParameter ( request, "notify_name");
		 String address_statement = requestSanitizedGetParameter ( request, "address_statement");
		 String data_statement = requestSanitizedGetParameter ( request, "data_statement");
		 String xslt = requestSanitizedGetParameter ( request, "xslt");
		 String bmdid = requestSanitizedGetParameter ( request, "bmdid");
		 Notify notify = new Notify(Integer.parseInt(id_resultset), notify_name, address_statement, data_statement, xslt, 0, bmdid);           
	     dbUtils.insert_notify(notify);
		 session.setAttribute("printedMsg", "Notifica " + notify_name + " inserito con successo");
		 session.setAttribute("titleMsg", "Risultato: ");
		 response.sendRedirect("msgPage.jsp");
	}	
	
	private void doSubmitNotifyDelete (HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException,  CustomException, NumberFormatException{
		 String notify_id = requestSanitizedGetParameter ( request, "deleteNotifySelect");	
		 dbUtils.delete_notify(Integer.parseInt(notify_id));	
		 session.setAttribute("printedMsg", "Notifica cancellata con successo");
		 response.sendRedirect("msgPage.jsp");
	}	

	

/////////////////////////////////////	
	
	
	private void doSubmitPluginCreation (HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException, CustomException, NumberFormatException {
		 //String plugin_id = requestSanitizedGetParameter ( request, "plugin_id");
		 String plugin_name = requestSanitizedGetParameter ( request, "plugin_name");
		 String plugin_type = requestSanitizedGetParameter ( request, "plugin_type");
		 String plugin_configurationfile = requestSanitizedGetParameter ( request, "plugin_configurationfile");
		 String plugin_note = requestSanitizedGetParameter ( request, "plugin_note");
		 dbUtils.insert_plugin(plugin_name, plugin_configurationfile, plugin_type, plugin_note);
		 session.setAttribute("printedMsg", "Plugin " + plugin_name + " inserito con successo");
		 session.setAttribute("titleMsg", "Risultato: ");
		 response.sendRedirect("msgPage.jsp");
	}	

	private void doSubmitPluginModify (HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException, CustomException, NumberFormatException{
		 String plugin_id = requestSanitizedGetParameter ( request, "modifyPluginSelect");
		 Plugin plugin = dbUtils.get_plugin( Integer.parseInt(plugin_id));
		 session.setAttribute("plugin", plugin);
		 response.sendRedirect("manage_plugins.jsp");
	}		
	
	private void doSubmitPluginModifyDo (HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException,  CustomException, NumberFormatException{
		 Plugin plugin = (Plugin) session.getAttribute("plugin");
		 int plugin_id = plugin.get_id();
		 String plugin_name = requestSanitizedGetParameter ( request, "plugin_name");
		 String plugin_type = requestSanitizedGetParameter ( request, "plugin_type");
		 String plugin_configurationfile = requestSanitizedGetParameter ( request, "plugin_configurationfile");
		 String plugin_note = requestSanitizedGetParameter ( request, "plugin_note");
		 dbUtils.modify_plugin(plugin_id, plugin_name, plugin_configurationfile, plugin_type, plugin_note);
		 session.removeAttribute("plugin");
		 this.updateSessionData(request, response, session);
		 session.setAttribute("printedMsg", "Plugin " + plugin_name + " modificato con successo");
		 response.sendRedirect("msgPage.jsp");
	}	
	
	private void doSubmitPluginDelete (HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException,  CustomException, NumberFormatException{
		 String plugin_id = requestSanitizedGetParameter ( request, "deletePluginSelect");		
		 Plugin plugin = dbUtils.get_plugin( Integer.parseInt(plugin_id));
		 dbUtils.delete_plugin(Integer.parseInt(plugin_id));	
		 session.setAttribute("printedMsg", "Plugin " + plugin.get_name() + " cancellato con successo");
		 response.sendRedirect("msgPage.jsp");
	}	

	
////////////////////////////////////

	private void doSubmitUserCreation (HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException, CustomException, NumberFormatException {
		 String user_username = requestSanitizedGetParameter ( request, "user_username");
		 String user_password = requestSanitizedGetParameter ( request, "user_password");
		 String user_name = requestSanitizedGetParameter ( request, "user_name");
		 String user_surname = requestSanitizedGetParameter ( request, "user_surname");
		 String user_email = requestSanitizedGetParameter ( request, "user_email");
		 String user_office = requestSanitizedGetParameter ( request, "user_office");
		 String user_telephone = requestSanitizedGetParameter ( request, "user_telephone");
		 String user_status = requestSanitizedGetParameter ( request, "user_status");
		 String user_id_group = requestSanitizedGetParameter ( request, "user_id_group");
		 dbUtils.insert_user(user_username, user_password, user_name, user_surname,
				  user_email, user_office, user_telephone, Integer.parseInt(user_status), Integer.parseInt(user_id_group)); 
		 session.setAttribute("printedMsg", "User " + user_name + " inserito con successo");
		 session.setAttribute("titleMsg", "Risultato: ");
		 response.sendRedirect("msgPage.jsp");
	}	

	private void doSubmitUserModify (HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException, CustomException, NumberFormatException{
		 String user_id = requestSanitizedGetParameter ( request, "modifyUserSelect");
		 User user = dbUtils.get_user( Integer.parseInt(user_id));
		 session.setAttribute("user", user);
		 response.sendRedirect("manage_users.jsp");
	}		
	
	

	
	private void doSubmitUserModifyDo (HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException,  CustomException, NumberFormatException{
		 User user = (User) session.getAttribute("user");
		 int user_id = user.get_id();
		 String user_username = requestSanitizedGetParameter ( request, "user_username");
		 String user_password = requestSanitizedGetParameter ( request, "user_password");
		 String user_name = requestSanitizedGetParameter ( request, "user_name");
		 String user_surname = requestSanitizedGetParameter ( request, "user_surname");
		 String user_email = requestSanitizedGetParameter ( request, "user_email");
		 String user_office = requestSanitizedGetParameter ( request, "user_office");
		 String user_telephone = requestSanitizedGetParameter ( request, "user_telephone");
		 String user_status = requestSanitizedGetParameter ( request, "user_status");
		 String user_id_group = requestSanitizedGetParameter ( request, "user_id_group");
		 dbUtils.modify_user(user_id, user_username, user_name, user_surname,
				  user_email, user_office, user_telephone, 
				  Integer.parseInt(user_status), Integer.parseInt(user_id_group), 
				  user_password);
		 session.removeAttribute("user");
		 this.updateSessionData(request, response, session);
		 session.setAttribute("printedMsg", "User " + user_name + " modificato con successo");
		 response.sendRedirect("msgPage.jsp");
	}	
	
	private void doSubmitUserDelete (HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException,  CustomException, NumberFormatException{
		 String user_id = requestSanitizedGetParameter ( request, "deleteUserSelect");		
		 User user = dbUtils.get_user( Integer.parseInt(user_id));
		 dbUtils.delete_user(Integer.parseInt(user_id));	
		 session.setAttribute("printedMsg", "User " + user.get_name() + " cancellato con successo");
		 response.sendRedirect("msgPage.jsp");
	}	

	
	private void doSubmitGroupCreation (HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException, CustomException, NumberFormatException {
		 String group_name = requestSanitizedGetParameter ( request, "group_name");
		 String group_status = requestSanitizedGetParameter ( request, "group_status");
		 dbUtils.insert_group(group_name, Integer.parseInt(group_status));
		 session.setAttribute("printedMsg", "Gruppo " + group_name + " inserito con successo");
		 session.setAttribute("titleMsg", "Risultato: ");
		 response.sendRedirect("msgPage.jsp");
	}	

	private void doSubmitGroupModify (HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException, CustomException, NumberFormatException{
		 String group_id = requestSanitizedGetParameter ( request, "modifyGroupSelect");
		 Group group = dbUtils.get_group( Integer.parseInt(group_id));
		 session.setAttribute("group", group);
		 response.sendRedirect("manage_groups.jsp");
	}		
	
	private void doSubmitGroupModifyDo (HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException,  CustomException, NumberFormatException{
		 Group group = (Group) session.getAttribute("group");
		 int group_id = group.get_id();
		 String group_name = requestSanitizedGetParameter ( request, "group_name");
		 int group_status = Integer.parseInt(requestSanitizedGetParameter ( request, "group_status"));
		 int group_old_status = group.get_status();
		 dbUtils.modify_group(group_id, group_name, group_status, group_old_status);
		 session.removeAttribute("group");
		 this.updateSessionData(request, response, session);
		 session.setAttribute("printedMsg", "Gruppo " + group_name + " modificato con successo");
		 response.sendRedirect("msgPage.jsp");
	}	
	
	private void doSubmitGroupDelete (HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException,  CustomException, NumberFormatException{
		 String group_id = requestSanitizedGetParameter ( request, "deleteGroupSelect");		
		 Group group = dbUtils.get_group( Integer.parseInt(group_id));
		 dbUtils.delete_groups(Integer.parseInt(group_id));	
		 session.setAttribute("printedMsg", "Gruppo " + group.get_name() + " cancellato con successo");
		 response.sendRedirect("msgPage.jsp");
	}	

	
	private void doSubmitGroupingCreation (HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException, CustomException, NumberFormatException {
		 String grouping_name = requestSanitizedGetParameter ( request, "grouping_name");
		 String grouping_alias = requestSanitizedGetParameter ( request, "grouping_alias");
		 dbUtils.insert_grouping(grouping_name, grouping_alias);

		 session.setAttribute("printedMsg", "Raggruppamento " + grouping_alias + " inserito con successo");
		 session.setAttribute("titleMsg", "Risultato: ");
		 response.sendRedirect("msgPage.jsp");
	}	

	private void doSubmitGroupingModify (HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException, CustomException, NumberFormatException{
		 String grouping_id = requestSanitizedGetParameter ( request, "modifyGroupingSelect");
		 Grouping grouping = dbUtils.get_grouping( Integer.parseInt(grouping_id));
		 session.setAttribute("grouping", grouping);
		 response.sendRedirect("manage_groupings.jsp");
	}		
	
	private void doSubmitGroupingModifyDo (HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException,  CustomException, NumberFormatException{
		 String grouping_name = requestSanitizedGetParameter ( request, "grouping_name");
		 String grouping_alias = requestSanitizedGetParameter ( request, "grouping_alias");
		 Grouping grouping = (Grouping) session.getAttribute("grouping");
		 int grouping_id = grouping.get_id();
		 dbUtils.modify_grouping(grouping_id, grouping_name, grouping_alias);
		 session.removeAttribute("grouping");
		 this.updateSessionData(request, response, session);
		 session.setAttribute("printedMsg", "Raggruppamento " + grouping_name + " modificato con successo");
		 response.sendRedirect("msgPage.jsp");
	}	
	
	private void doSubmitGroupingDelete (HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException,  CustomException, NumberFormatException{
		 String grouping_id = requestSanitizedGetParameter ( request, "deleteGroupingSelect");		
		 Grouping grouping = dbUtils.get_grouping( Integer.parseInt(grouping_id));
		 dbUtils.delete_grouping(Integer.parseInt(grouping_id));	
		 session.setAttribute("printedMsg", "Raggruppamento " + grouping.get_alias() + " cancellato con successo");
		 response.sendRedirect("msgPage.jsp");
	}	

/////////////////////////////
	
	private void doSubmitResultsetCreation (HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException, CustomException, NumberFormatException {
		 String resultset_name = requestSanitizedGetParameter ( request, "resultset_name");
		 String resultset_alias = requestSanitizedGetParameter ( request, "resultset_alias");
		 String resultset_statement = requestSanitizedGetParameter ( request, "resultset_statement");
		 dbUtils.insertResultSet( resultset_name,  resultset_alias,  resultset_statement);
		 session.setAttribute("printedMsg", "Resultset " + resultset_alias + " inserito con successo");
		 session.setAttribute("titleMsg", "Risultato: ");
		 response.sendRedirect("msgPage.jsp");
	}	
	
	private void doSubmitResultsetModify (HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException, CustomException, NumberFormatException{
		 int selectedGroupId = 1;
		 doSubmitResultsetModifyChangeGroupCommon(request, response, session, selectedGroupId);
	}		

	private void doSubmitResultsetModifyChangeGroupCommon (HttpServletRequest request, HttpServletResponse response, HttpSession session, int selectedGroupId) throws ServletException, IOException, CustomException, NumberFormatException{
		 String selectedResultsetIdStr = requestSanitizedGetParameter ( request, "modifyResultsetSelect");
		 int selectedResultsetId = Integer.parseInt(selectedResultsetIdStr);
		 Resultset selectedResultset = dbUtils.get_resultset(selectedResultsetId);
		 session.setAttribute("selectedResultset", selectedResultset);
		 
		 Group selectedGroup = dbUtils.get_group( selectedGroupId);
		 session.setAttribute("selectedGroup", selectedGroup);
		 
		 ArrayList <ResourceWithGroupPermissions> selectedResources = dbUtils.get_fields_with_permissions_from_resultsetid(selectedResultset.get_id(), selectedGroupId);
		 session.setAttribute("selectedResources", selectedResources);
		 ArrayList <Pluginassociation> pluginAssociations = dbUtils.get_pluginassociation(selectedResultsetId, selectedGroupId);
		 session.setAttribute("pluginAssociations", pluginAssociations);		  		 
		 Toolbar selectedToolbar = dbUtils.get_toolbar_from_ids(selectedResultsetId, selectedGroupId);	 
		 session.setAttribute("selectedToolbar", selectedToolbar);		  		 			
		 response.sendRedirect("manage_resources.jsp");	
	}
	
	private void doSubmitResultsetModifyChangeGroup (HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException, CustomException, NumberFormatException{
		String selectedGroupIdStr = requestSanitizedGetParameter ( request, "modifyPermissionsGroupSelect");
		int selectedGroupId = Integer.parseInt(selectedGroupIdStr);
		doSubmitResultsetModifyChangeGroupCommon(request, response, session, selectedGroupId);
	}		
	
	
	private void doSubmitResultsetDelete (HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException,  CustomException, NumberFormatException{
		 String resultset_id = requestSanitizedGetParameter ( request, "deleteResultsetSelect");		
		 Resultset resultset = dbUtils.get_resultset( Integer.parseInt(resultset_id));
		 dbUtils.delete_resultset_complete_by_id(Integer.parseInt(resultset_id));	
		 session.setAttribute("printedMsg", "ResultSet " + resultset.get_alias() + " cancellato con successo");
		 response.sendRedirect("msgPage.jsp");
	}	

	private void refreshIndex(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException,  CustomException, NumberFormatException {
		updateSessionData(request, response, session);
		response.sendRedirect("index.jsp");
	}
	
	private int getSinglePermission (HttpServletRequest request, String paramName) throws ServletException, IOException,  CustomException, NumberFormatException {
		String paramValue = (String)requestSanitizedGetParameter ( request, paramName);
		if ( (paramValue!=null) && (paramValue.compareTo("")!=0) ) {
			return 1;
		}		
		return 0;
	}
	
//	private void printParameterEnumeration (HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException,  CustomException, NumberFormatException {
//		System.out.println("******************************");
//		Enumeration paramEnumeration = request.getParameterNames();
//		 while (paramEnumeration.hasMoreElements()) {
//			String param_name = (String) paramEnumeration.nextElement();			
//			String paramValue = (String) requestSanitizedGetParameter ( request, param_name);
//			System.out.print(param_name + " --> " + paramValue);
//			if ( param_name.matches( "c_(\\d*)_(\\w+)" ) ){
//				System.out.println(" MATCH OK");
//			} else {
//				System.out.println("");
//			}
//		}
//			System.out.println("-----------------------------");
//
//		 Enumeration attribEnumeration = request.getAttributeNames();
//		 while (attribEnumeration.hasMoreElements()) {
//				String attrib_name = (String) attribEnumeration.nextElement();			
//				String attribValue = (String) request.getAttribute(attrib_name);
//				System.out.print(attrib_name + " --> " + attribValue);
//				if ( attrib_name.matches( "c_(\\d*)_(\\w+)" ) ){
//					System.out.println(" MATCH OK");
//				} else {
//					System.out.println("");
//				}
//			}
//			System.out.println("******************************");
//	}
	
	private void doSubmitPermissionsModify (HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException,  CustomException, NumberFormatException {
		Group selectedGroup = (Group) session.getAttribute("selectedGroup");
		Resultset selectedResultset = (Resultset) session.getAttribute("selectedResultset");
		int selectedResultsetId = selectedResultset.get_id();
		int selectedGroupId = selectedGroup.get_id();
		boolean noPermissions = true;
		 
		 ArrayList <ResourceWithGroupPermissions> selectedResources = (ArrayList <ResourceWithGroupPermissions>) session.getAttribute("selectedResources");
		 for (ResourceWithGroupPermissions resource : selectedResources) {
			 //String resource_name = resource.get_name();
			 String resource_alias = resource.get_alias();
			 int resource_id = resource.get_id();
			 //String resource_type = resource.get_type();
			 //String resource_def = resource.get_def();
			 int resource_header = resource.get_header();
			 int resource_search = resource.get_search();
			 int resource_grouping = resource.get_grouping();		
			 int readperm = 0;
			 int deleteperm = 0;
			 int modifyperm = 0;
			 int insertperm = 0;
			 if (resource.getResourceGroupPermissions() != null){
				 readperm = resource.getResourceGroupPermissions().getRead();
				 deleteperm = resource.getResourceGroupPermissions().getDelete();
				 modifyperm = resource.getResourceGroupPermissions().getModify();
				 insertperm = resource.getResourceGroupPermissions().getInsert();
				}

			 String resource_aliasNew = (String)requestSanitizedGetParameter ( request, "c_" + resource_id + "_a");
//			 int resource_idNew = Integer.parseInt( (String)requestSanitizedGetParameter ( request, ""));
//			 String resource_typeNew = (String)requestSanitizedGetParameter ( request, "");
//			 String resource_defNew = (String)requestSanitizedGetParameter ( request, "");
			 int resource_headerNew = getSinglePermission(request, "c_" + resource_id + "_h");
			 int resource_searchNew = getSinglePermission(request,"c_" + resource_id + "_s");
			 int resource_groupingNew = Integer.parseInt((String)requestSanitizedGetParameter ( request, "c_" + resource_id + "_g"));		

			 int readpermNew = getSinglePermission(request, "c_" + resource_id + "_r");
			 int deletepermNew = getSinglePermission(request, "c_" + resource_id + "_w");
			 int modifypermNew = getSinglePermission(request, "c_" + resource_id + "_m");
			 int insertpermNew = getSinglePermission(request, "c_" + resource_id + "_i");
			 if ( 
					 (readpermNew != 0 ) ||
					 (deletepermNew != 0 ) ||
					 (modifypermNew != 0 ) ||
					 (insertpermNew != 0 ) 
					 ){
				noPermissions = false;
			}
			 if (resource_alias.compareTo(resource_aliasNew)!= 0) {				
				 dbUtils.modifyResourceAlias(resource_id, resource_aliasNew);
			 }
			 if (	 (readperm!=readpermNew) ||
					 (deleteperm!=deletepermNew) ||
					 (modifyperm!=modifypermNew) ||
					 (insertperm!=insertpermNew) ){						 
				 dbUtils.delete_management_permission_by_id_resource_id_and_goup_id(0, resource_id, selectedGroupId);
				 dbUtils.insert_management_permissions(selectedGroupId, resource_id, readpermNew, deletepermNew, modifypermNew, insertpermNew);
			 } 
			 
			if ( (resource_header!=resource_headerNew) ||
				 (resource_search!=resource_searchNew) ||
				 (resource_grouping!=resource_groupingNew) ){
				 dbUtils.modify_field(resource_id, resource_headerNew, resource_searchNew, resource_groupingNew);
			 } 
			if( noPermissions ){
				dbUtils.delete_management_permission_by_id_resource_id_and_goup_id(0,resource_id,selectedGroupId);
			}
		 }
		String tools = (requestSanitizedGetParameter ( request, "tools")).toUpperCase().trim();
		 Toolbar selectedToolbar = (Toolbar) session.getAttribute("selectedToolbar");			 
		 if ( tools.compareTo(selectedToolbar.get_tools()) != 0 ){
			 Toolbar toolbar = new Toolbar(selectedResultsetId, selectedGroupId, tools);
			 dbUtils.insert_tool(toolbar);
		 }	 
		 ArrayList <Plugin> plugins = (ArrayList <Plugin>) session.getAttribute("plugins");
		 ArrayList <Pluginassociation> pluginAssociations = (ArrayList <Pluginassociation>) session.getAttribute("pluginAssociations");	 
         for (Plugin plugin : plugins ) {
			 int id_plugin = plugin.get_id();
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
			if  ((requestSanitizedGetParameter ( request, "plugin_" + id_plugin + "_ass")!= null) && (currPluginAssociation == 0)){
				dbUtils.insert_pluginassociation(id_plugin, selectedResultset.get_id(), selectedGroup.get_id());
            } else if  ((requestSanitizedGetParameter ( request, "plugin_" + id_plugin + "_ass")== null) && (currPluginAssociation == 1)) {
            	dbUtils.delete_pluginassociation(id_plugin, selectedResultset.get_id(), selectedGroup.get_id());
            }
         }					
		 this.updateSessionData(request, response, session);
		 this.updateSelectedSessionData(request, response, session);
		 response.sendRedirect("manage_resources.jsp");
	}

	
	
	private void updateSelectedSessionData (HttpServletRequest request, HttpServletResponse response, HttpSession session)throws ServletException, IOException, CustomException {
		Group selectedGroup = (Group) session.getAttribute("selectedGroup");
		Resultset selectedResultset = (Resultset) session.getAttribute("selectedResultset");
		 int selectedGroupId = selectedGroup.get_id();
		 int selectedResultsetId = selectedResultset.get_id();
	 ArrayList <ResourceWithGroupPermissions> selectedResources = dbUtils.get_fields_with_permissions_from_resultsetid(selectedResultset.get_id(), selectedGroupId);
	 session.setAttribute("selectedResources", selectedResources);
	 ArrayList <Pluginassociation> pluginAssociations = dbUtils.get_pluginassociation(selectedResultsetId, selectedGroupId);
	 session.setAttribute("pluginAssociations", pluginAssociations);		  		 
	 Toolbar selectedToolbar = dbUtils.get_toolbar_from_ids(selectedResultsetId, selectedGroupId);	 
	 session.setAttribute("selectedToolbar", selectedToolbar);		  		 
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
		 String appo = (String) requestSanitizedGetParameter ( request, fieldName);
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
 
	private String requestSanitizedGetParameter (HttpServletRequest request, String paramName) throws CustomException {
		String s1 = request.getParameter     (paramName);
//		if (s1 != null) {
//			String s2 = dbUtils.addSlashes(s1);
//			return s2;
//		} else {
//			return null;		
//		}
		return s1;
	}
	        
	        
}
