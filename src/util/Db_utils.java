package util;

//import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLData;
import java.sql.Statement;
//import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Iterator;

//include_once 'db_connection.php';
//include_once 'Db_table_names.php';
//include_once 'resource.php';
//include_once 'notify.php';
//include_once 'resultset.php';
//include_once 'group.php';
//include_once 'grouping.php';
//include_once 'user.php';
//include_once 'toolbar.php';
//include_once 'plugin.php';

public class Db_utils {
	DBManager dbManager = new DBManager();

	private Connection db_get_connection() throws CustomException {
		return dbManager.getConnection();
	}
	
	public void createConnection (String mysql_host, String mysql_database , String mysql_user, String mysql_password) throws CustomException {
		dbManager.createConnection(mysql_host, mysql_database, mysql_user, mysql_password) ;
	}
	
	
	public void insert_management_permissions (int group_id,int resource_id) throws  CustomException{
		insert_management_permissions( group_id, resource_id, 0, 0, 0, 0);
	}

//	public int insert_All_management_permissions (int group_id) throws  CustomException {
//		
//	}

	public void insert_All_management_permissions (int group_id ) throws  CustomException {
		 ArrayList <ResourceMinimal> resList = get_Allresources();
		 for (ResourceMinimal resourceMinimal : resList) {
			 insert_management_permissions (group_id, resourceMinimal.get_id(), 1,1,1,1);
		}
	}	
	
	public void insert_management_permissions (int group_id,int resource_id,int r ,int d ,int m ,int i ) throws  CustomException {
	    Connection  connection = db_get_connection();		
	    String query = "" +
	    		" INSERT into " + 
	    		Db_table_names.T_MANAGEMENT + 
	    		" " + 
	    		" (`id_group`, `id_resource`, `readperm`, `deleteperm`, `modifyperm`, `insertperm`) " + 
	    		" VALUES " +
	    		" (" + group_id + ", " + resource_id + ", " +  r + ", " +  d + ", " + m + ", " +  i + ")";
	    try {
    		PreparedStatement pstmt = connection.prepareStatement(query);
    		pstmt.executeUpdate();
    		//int insertedKey = pstmt.getGeneratedKeys().getInt(1);
	    	pstmt.close();
	    } catch (Exception e) {
		      CustomLog.log("Errore durante la insert_management_permissions a database : query --> "+ query, e);
		      throw new CustomException(
		          "Errore database");
	    }	 		    	
	}
		
		public String insert_tool(Toolbar toolbar)throws  CustomException {	
		    Connection connection = db_get_connection();		
		    String query = " INSERT " +
		    				" INTO " + 
		    				Db_table_names.T_TOOLBAR + 
		    				" SET " +
		    				"`id_resultset`='" + 
		    				toolbar.get_id_resultset() + 
		    				"', `id_group`='" + 
		                	toolbar.get_id_group() + 
		                	"',`tools`='" +
		                	toolbar.get_tools() +
		                	"' ON DUPLICATE KEY UPDATE `id_resultset`='" + 
		                	toolbar.get_id_resultset() + 
		                	"', `id_group`='" +
		                	toolbar.get_id_group() + 
		                	"', `tools`='" + 
		                	toolbar.get_tools() + "'";
		    try {
	    		PreparedStatement pstmt = connection.prepareStatement(query);
	    		int modifiedRowsNumber = pstmt.executeUpdate();
			    if (modifiedRowsNumber > 0) 
			    	return "<pre>Toolbar added!</pre>";
			    else 
				    return "<pre>Toolbar NOT added!</pre>";	
		    } catch (Exception e) {
			      CustomLog.log("Errore durante la insert_tool a database : query --> "+ query, e);
			      throw new CustomException(
			          "Errore database");
		    }	 
		}
		
		
		public String insert_pluginassociation(int id_plugin, int id_resultset, int id_group) throws  CustomException{	
		    Connection connection = db_get_connection();		
		    String query = "INSERT INTO " + 
		    Db_table_names.T_PLUGINASSOCIATION + 
		    " SET `id_plugin`='" + 
		    id_plugin + "',`id_resultset`='" + 
		    id_resultset +
		    "', `id_group`='" +
			id_group +
			"' ON DUPLICATE KEY UPDATE `id_plugin`='" +
			id_plugin + 
			"',`id_resultset`='" +
			id_resultset +
			"', `id_group`='" +
			id_group +
			"'";
		    try {
	    		PreparedStatement pstmt = connection.prepareStatement(query);
	    		int modifiedRowsNumber = pstmt.executeUpdate();
		    	pstmt.close();
			    if (modifiedRowsNumber > 0) 
			    	return "<pre>Plugin association  added!</pre>";
			    else 
				    return "<pre>Plugin association  NOT added!</pre>";
		    } catch (Exception e) {
			      CustomLog.log("Errore durante la insert_pluginassociation : query --> "+ query, e);
			      throw new CustomException(
			          "Errore database");
		    }	 
		}	
		
		public String delete_pluginassociation(int id_plugin, int id_resultset, int id_group) throws  CustomException{		
		    Connection connection = db_get_connection();		
		    String query = "" +
		    		"DELETE FROM " +
				    Db_table_names.T_PLUGINASSOCIATION +
				    " WHERE `id_plugin`='" +
				    id_plugin +
				    "' AND `id_resultset`='" + 
				    id_resultset +
				    "' AND `id_group`='" +
				    id_group +
				    "'";
		    try {
	    		PreparedStatement pstmt = connection.prepareStatement(query);
	    		int modifiedRowsNumber = pstmt.executeUpdate();
			    pstmt.close();
			    if (modifiedRowsNumber > 0) 
			    	return "<pre>Plugin association  deleted!</pre>";
			    else 
				    return "<pre>Plugin association  NOT deleted!</pre>";	
		    } catch (Exception e) {
			      CustomLog.log("Errore durante la delete_pluginassociation : query --> "+ query, e);
			      throw new CustomException(
			          "Errore database");
		    }	 

		}
		
		public String insert_management_toolbar(int group_id, int id_resulteset, String tools) 
		throws  CustomException  {
		    if ((tools.trim()).compareTo("")!=0) {
		        Connection connection = db_get_connection();
		        String query = "" +
		        		"INSERT into " + 
		        		Db_table_names.T_TOOLBAR + 
		        		" " +
		        		" (`id_resultset`, `id_group`, `tools`) " + 
		        		" VALUES" +
		        		" ("+
		        		id_resulteset +
		        		", " +
		        		group_id + 
		        		", " +
		        		"'" +
		        		tools +
		        		"'" +
		        		") ";		
			    try {
		    		PreparedStatement pstmt = connection.prepareStatement(query);
		    		int modifiedRowsNumber = pstmt.executeUpdate();
				    pstmt.close();
				    if (modifiedRowsNumber > 0) 
				    	return "<pre>Toolbar added!</pre>";
				    else 
					    return "<pre>Toolbar NOT added!</pre>";		
				 } catch (Exception e) {
				      CustomLog.log("Errore durante la insert_management_toolbar : query --> "+ query, e);
				      throw new CustomException(
				          "Errore database");
			    }	 
		    }
		    return "";
		}
		
		public void insert_management(int group_id, int resource_id)  throws  CustomException {
		    Connection connection = db_get_connection();		
		    String  query = "" +
		    		"INSERT into " + 
		    		Db_table_names.T_MANAGEMENT + 
		    		" " + 
		    		" (`id_group`, `id_resource`) " +
		    		" VALUES " +
		    		" ( " + 
		    		group_id + ", " +
		    		resource_id +
		    		")";
		    try {
	    		PreparedStatement pstmt = connection.prepareStatement(query);
	    		pstmt.close();
			 } catch (Exception e) {
			      CustomLog.log("Errore durante la insert_management : query --> "+ query, e);
			      throw new CustomException(
			          "Errore database");
		    }	 
		}
		
		public int insert_user(	String username,
									String password,
									String name,
									String surname,
									String email,
									String office,
									String telephone,
									int status, 
									int id_group)  throws  CustomException  {
		    Connection connection = db_get_connection();
		    String query = "" +
		    		"INSERT into " +
		    		Db_table_names.T_USER +
		    		" " + 
		    		" (`username`, " +
		    		" `password`, " +
		    		" `name`, " +
		    		" `surname`, " +
		    		" `email`, " +
		    		" `office`, " +
		    		" `telephone`, " +
		    		" `status`, " +
		    		" `id_group` )" +
		    		" VALUES " +
		    		" ('" + username + "', " +
		    		" PASSWORD( '" + password + "'), " +
		    		"'" + addslashes(name) + "', " +
		    		"'" + addslashes(surname) + "', " +
		    		"'" + addslashes(email) + "', " +
		    		"'" + addslashes(office) + "', " +
		    		"'" + telephone + "', " +
		    		status + ", " + 
		    		id_group +
		    		" )" ;
				    try {
			    		PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
						pstmt.executeUpdate();
					    ResultSet keys = pstmt.getGeneratedKeys();
					    keys.next();
					    int id = keys.getInt(1);
						pstmt.close();
						return id;
					 } catch (Exception e) {
					      CustomLog.log("Errore durante la insert_user : query --> "+ query, e);
					      throw new CustomException(
					          "Errore database");
				    }	 			
		}

		public void modify_user ( int user_id,
				String username,
				String name,
				String surname,
				String email,
				String office,
				String telephone,
				int status,
				int id_group) throws  CustomException  {
			 modify_user( user_id, username, name, surname, email, office, telephone, status, id_group, ""); 
		}		

			public void modify_user(int user_id,
				String username,
				String name,
				String surname,
				String email,
				String office,
				String telephone,
				int status,
				int id_group, 
				String password) throws  CustomException   {
		    Connection connection = db_get_connection();		
		        String query = "" +
		        		" UPDATE " + 
		        		Db_table_names.T_USER + 
		        		" SET " +
		        		" `username`= '" + username  + "', " ;
			    if(password!="") {
			    	query += 
			    		" `password`= PASSWORD('" + password + "'), " ;
			    }
			    	query +=
			    		" `name`= '" + name + "', " +
		        		" `surname`= '" + surname + "', " +
		        		" `email`= '" + email + "', " +
		        		" `office`= '" + office + "', " +
		        		" `telephone`='" + telephone + "', " +
		        		" `status`= " + status + ", " +
		        		" `id_group`= '" + id_group + "' " +
		        		" WHERE " +
		        		" id=" + user_id;
		    try {
	    		PreparedStatement pstmt = connection.prepareStatement(query);
	    		pstmt.executeUpdate();
		    	pstmt.close();
			 } catch (Exception e) {
			      CustomLog.log("Errore durante la insert_management : query --> "+ query, e);
			      throw new CustomException(
			          "Errore database");
		    }	 
		}
			
		public User get_user(int id_user) throws  CustomException  {
			return get_users (id_user).get(0);
		}

		public ArrayList<User> get_users() throws  CustomException  {
			return get_users (0);
		}

		public ArrayList<User> get_users(int id_user)  throws  CustomException  {
		    Connection connection = db_get_connection();
		    String WHERE_CLAUSE = " ";
		    if(id_user!=0) {
		    	WHERE_CLAUSE = "WHERE tu.`id` = " + id_user + " ";
		    }	
		    String query = "" +
		    		" SELECT " +
		    		" tu.*, " +
		    		" tg.name AS group_name " +
		    		" FROM " + 
		    		Db_table_names.T_USER + " tu " +
		    		" LEFT JOIN " +
		    		Db_table_names.T_GROUP + " tg " +
		    		" ON tu.`id_group` = tg.`id` " +
		    		 WHERE_CLAUSE +
		    		 " ORDER BY " +
		    		 " tu.status DESC, " +
		    		 " tu.id";
		    try {
	    		PreparedStatement pstmt = connection.prepareStatement(query);	    		
			    ResultSet rs = pstmt.executeQuery();
		    ArrayList<User> results = new ArrayList<User>();
		    while(rs.next()) {
		        int id = rs.getInt("id");
		        String username = rs.getString("username");
		        String password = rs.getString("password");
		        String name = rs.getString("name");
		        String  surname = rs.getString("surname");
		        String email = rs.getString("email");
		        String office = rs.getString("office");
		        String telephone = rs.getString("telephone");
		        int status = rs.getInt("status");
		        int id_group = rs.getInt("id_group");
		        String group_name = rs.getString("group_name");
		        User newUser =  new User(id, username, password, name, surname, email, office, telephone, status, id_group, group_name); 
		        results.add(newUser);
		    }
		    pstmt.close();		
		    return results;
			 } catch (Exception e) {
			      CustomLog.log("Errore durante la get_users : query --> "+ query, e);
			      throw new CustomException(
			          "Errore database");
		    }	 
		}	
		
		public ArrayList<User> get_usersOfAGroup(int id_group) throws  CustomException  {
		    Connection connection = db_get_connection();
		    String WHERE_CLAUSE = " ";
		    if(id_group!=0) {
		    	WHERE_CLAUSE = " WHERE tg.`id` = " + id_group;
		    }	
		    String query = "" +
		    		" SELECT " +
		    		" * " +
		    		" FROM " + 
		    		Db_table_names.T_USER + " tu " +
		    		" LEFT JOIN " +
		    		Db_table_names.T_GROUP + " tg " +
		    		" ON tu.`id_group` = tg.`id` " +
		    		 WHERE_CLAUSE +
		    		 " ORDER BY " +
		    		 " tu.status DESC, " +
		    		 " tu.id";
		    try {
	    		PreparedStatement pstmt = connection.prepareStatement(query);	    		
			    ResultSet rs = pstmt.executeQuery();
		    ArrayList<User> results = new ArrayList<User>();
		    while(rs.next()) {
		        int id = rs.getInt("id");
		        String username = rs.getString("username");
		        String password = rs.getString("password");
		        String name = rs.getString("name");
		        String  surname = rs.getString("surname");
		        String email = rs.getString("email");
		        String office = rs.getString("office");
		        String telephone = rs.getString("telephone");
		        int status = rs.getInt("status");
		        //int id_group = rs.getInt("id_group");
		        String group_name = rs.getString("group_name");
		        User newUser =  new User(id, username, password, name, surname, email, office, telephone, status, id_group, group_name); 
		        results.add(newUser);
		    }
		    pstmt.close();		
		    return results;
			 } catch (Exception e) {
			      CustomLog.log("Errore durante la get_usersOfAGroup : query --> "+ query, e);
			      throw new CustomException(
			          "Errore database");
		    }	 
		}	

		public void delete_user(int id_user ) throws  CustomException  {
			Connection connection = db_get_connection();	
		    if(id_user!=0) {		
		        String query = "" +
		        		"DELETE" +
		        		" from " +
		        		Db_table_names.T_USER +
		        		" where" +
		        		" `id` = " +
		        		id_user +
		        		" LIMIT 1";
			    try {
		    		PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
					pstmt.executeUpdate();
			    	pstmt.close();
				 } catch (Exception e) {
				      CustomLog.log("Errore durante la delete_user : query --> "+ query, e);
				      throw new CustomException("Errore database");
				 }
		    }	 
		}
		
		public int insert_group(String name, int status) throws  CustomException  {
		    Connection connection = db_get_connection();
		    String query = "" +
		    		" INSERT into " +
		    		Db_table_names.T_GROUP +
		    		" (`name`, `status`) " +
		    		" VALUES " +
		    		" ('" +
		    		name +
		    		"', " +
		    		status +
		    		" )";	        
				    try {
			    		PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
						pstmt.executeUpdate();
					    ResultSet keys = pstmt.getGeneratedKeys();
					    keys.next();
					    int id = keys.getInt(1);
						pstmt.close();	
						return id;
					 } catch (Exception e) {
					      CustomLog.log("Errore durante la insert_group : query --> "+ query, e);
					      throw new CustomException(
					          "Errore database");
				    }	 
		}
		
		public void modify_group(int group_id,
				String name,
				int status,
				int old_status )  throws  CustomException  {
		    Connection connection = db_get_connection();		
		    String query_usr= "";
		    String query = "" +
		    		" UPDATE " + 
		    		Db_table_names.T_GROUP +
		    		" SET " +
		    		" `name`='" +
		    		name +
		    		"', " +
		    		"`status`=" +
		    		status +
		    		" WHERE " +
		    		" id=" +
		    		group_id ;
		    try {
	    		PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
				 pstmt.executeUpdate();
				pstmt.close();
		    if( status != old_status) {
		        /* lo stato del gruppo è stato modificato, quindi attivo o disattivo
		         * tutti gli utenti che ne fanno parte */
		        query_usr = "" +
		        		" UPDATE " +
		        		Db_table_names.T_USER +
		        		" SET " +
		        		" `status`=" + status +
		        		" WHERE " +
		        		" id_group=" + group_id;
	    		PreparedStatement pstmt2 = connection.prepareStatement(query_usr);
				pstmt2.executeUpdate();
				pstmt2.close();    
		    }		
			pstmt.close();			
			 } catch (Exception e) {
			      CustomLog.log("Errore durante la edit_group : query --> "+ query + " query2 --> " + query_usr , e  );
			      throw new CustomException(
			          "Errore database");
		    }	 			
		}
		
		public ArrayList<Group> get_groups() throws  CustomException  {
			return get_groups (0);
		}

		public Group get_group(int id_group) throws  CustomException  {
			return get_groups(id_group).get(0);
		}
		
		public ArrayList<Group> get_groups(int id_group) throws  CustomException  {
		    Connection connection = db_get_connection();		
		    // la query commentata prendeva solo i dati dei gruppi che non sono hanno il resultset
		    //String query = "SELECT * FROM " + Db_table_names.T_GROUP + " WHERE `status` = 1 " +  "AND `id` NOT IN (SELECT DISTINCT `id_group` " + " FROM " + Db_table_names.T_MANAGEMENT + " WHERE `id_resource` = resultset_id)";		
		    String WHERE_CLAUSE = "";
		    if(id_group!=0) 
		    	WHERE_CLAUSE = "WHERE `id` = " + id_group;
		    String query = "" +
		    		" SELECT " +
		    		" * " +
		    		" FROM " +
		    		Db_table_names.T_GROUP + " " +
		    		 WHERE_CLAUSE +
		    		" ORDER BY" +
		    		" status DESC," +
		    		" id";		
		    /* Esegui la query */
	
		    try {
	    		PreparedStatement pstmt = connection.prepareStatement(query);	    		
			    ResultSet rs = pstmt.executeQuery();
		    ArrayList<Group> results = new ArrayList<Group>();
		    while(rs.next()) {
		        int id = rs.getInt("id");
		        String name = rs.getString("name");
		        int status = rs.getInt("status");
		        Group newGroup =  new Group(id, name, status); 
		        results.add(newGroup);
		    }
		    pstmt.close();		
		    return results;
			 } catch (Exception e) {
			      CustomLog.log("Errore durante la get_groups : query --> "+ query, e);
			      throw new CustomException(
			          "Errore database");
		    }	 
		}

	
		public void delete_groups(int id_group ) throws  CustomException  {
			ArrayList<User> userOfGroup = get_usersOfAGroup( id_group);
			if (userOfGroup.size() > 0){
				throw new CustomException ("gruppo non vuoto");
			}
			Connection connection = db_get_connection();	
		    if(id_group!=0) {		
		        String query = "" +
		        		"DELETE" +
		        		" from " +
		        		Db_table_names.T_GROUP +
		        		" where" +
		        		" `id` = " +
		        		id_group +
		        		" LIMIT 1";
			    try {
		    		PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
					pstmt.executeUpdate();
			    	pstmt.close();
				 } catch (Exception e) {
				      CustomLog.log("Errore durante la delete_groups : query --> "+ query, e);
				      throw new CustomException("Errore database");
				 }
		    }	 
		}
		
		public void insert_grouping(String name, String alias) throws  CustomException  {
		    Connection connection = db_get_connection();	
		    String query = "" +
		    		" INSERT into " +
		    		Db_table_names.T_GROUPING + 
		    		" (`name`, `alias`)" +
		    		" VALUES " +
		    		" ('" +
		    		addslashes(name) +
		    		"', '" +
		    		addslashes(alias) +
		    		"')";
		    try {
	    		PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
				pstmt.executeUpdate();
				pstmt.close();
			 } catch (Exception e) {
			      CustomLog.log("Errore durante la insert_grouping : query --> "+ query, e);
			      throw new CustomException(
			          "Errore database");
		    }	 			
		}
		
		public void modify_grouping(int grouping_id, String name, String alias ) throws  CustomException  {
		    Connection connection = db_get_connection();		
		    String query = "" +
		    		" UPDATE " +
		    		Db_table_names.T_GROUPING +
		    		" SET " +
		    		"`name`='" +
		    		name +
		    		"', " +
		    		" `alias`='" +
		    		alias +
		    		"' WHERE" +
		    		" id=" +
		    		grouping_id ;
		    try {
	    		PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
				pstmt.executeUpdate();
				pstmt.close();
			 } catch (Exception e) {
			      CustomLog.log("Errore durante la edit_grouping : query --> "+ query, e);
			      throw new CustomException(
			          "Errore database");
		    }	 			
		}
		
		public Plugin get_plugin(int id_plugin)  throws  CustomException {
			return get_plugins(id_plugin).get(0);
		}

		public ArrayList<Plugin> get_plugins()  throws  CustomException {
			return get_plugins(0);
		}
		

		public ArrayList<Plugin> get_plugins(int id_plugin)  throws  CustomException {
		    Connection connection = db_get_connection();		
		    String WHERE_CLAUSE = "";
		    if(id_plugin!=0) 
		    	WHERE_CLAUSE = " WHERE `id` = " + id_plugin;		
		    String query = "" +
		    		" SELECT " +
		    		" * " +
		    		" FROM " +
		    		Db_table_names.T_PLUGIN +
		    		WHERE_CLAUSE +
		    		" ORDER BY" +
		    		" name";
		    try {
	    		PreparedStatement pstmt = connection.prepareStatement(query);	    		
			    ResultSet rs = pstmt.executeQuery();
		    ArrayList<Plugin> results = new ArrayList<Plugin>();
		    while(rs.next()) {
		        int id = rs.getInt("id");
		        String name = rs.getString("name");
		        String configurationfile = rs.getString("configurationfile");
		        String type = rs.getString("type");
		        String note = rs.getString("note");
		        Plugin newplugin =  new Plugin(id, name, configurationfile, type, note); 
		        results.add(newplugin);
		    }
		    pstmt.close();		
		    return results;
			 } catch (Exception e) {
			      CustomLog.log("Errore durante la get_plugins : query --> "+ query, e);
			      throw new CustomException(
			          "Errore database");
		    }	 
		}
		
		public ArrayList<Pluginassociation> get_pluginassociation( int id_resultset, int id_group)  throws  CustomException {
		    Connection connection = db_get_connection();		
		    String query = "" +
		    		" SELECT " +
		    		" * " +
		    		" FROM " +
		    		Db_table_names.T_PLUGINASSOCIATION + 
		    		" WHERE "  +
		    		" `id_resultset`='" +
		    		id_resultset + 
		    		"' AND" +
		    		" `id_group`='" +
		    		id_group +
		    		"'";		
		    try {
	    		PreparedStatement pstmt = connection.prepareStatement(query);	    		
			    ResultSet rs = pstmt.executeQuery();
			    ArrayList<Pluginassociation> results = new ArrayList<Pluginassociation>();
			    while (rs.next()){
			    	int pluginId = rs.getInt("id_plugin");
			    	Pluginassociation pluginassociation = new Pluginassociation(pluginId, id_resultset, id_group);
			    	results.add(pluginassociation);
		    	}
			    pstmt.close();		
			    return results ;
			 } catch (Exception e) {
			      CustomLog.log("Errore durante la get_pluginassociation : query --> "+ query, e);
			      throw new CustomException(
			          "Errore database");
		    }	 
		}
		
		public void delete_plugin(int id_plugin) throws  CustomException  {
		    Connection connection = db_get_connection();		
		    if(id_plugin!=0) {		
		    // cancello il gruppo
		        String query = "" +
		        		" DELETE " +
		        		" from " +
		        		Db_table_names.T_PLUGIN +
		        		" where " +
		        		" `id` = " +
		        		id_plugin +
		        		" LIMIT 1";
			    try {
		    		PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
					pstmt.executeUpdate();					
					pstmt.close();
				 } catch (Exception e) {
				      CustomLog.log("Errore durante la delete_plugin : query --> "+ query, e);
				      throw new CustomException(
				          "Errore database");
			    }
		    }
		}
		
		public void modify_plugin(
				int plugin_id,
				String name,
				String  configurationfile,
				String  type,
				String  note ) throws  CustomException  {
		    Connection connection = db_get_connection();		
		    String query = "" +
		    		" UPDATE " +
		    		Db_table_names.T_PLUGIN +
		    		" SET " +
		    		" `name`='" +
		    		name +
		    		"'," +
		    		" `type`='" +
		    		type +
		    		"'," +
		    		" `configurationfile`='" +
		    		configurationfile +
		    		"'," +
		    		" `note`='" +
		    		note +
		    		"' " +
		    		" WHERE" +
		    		" id=" +
		    		plugin_id ;
		    try {
	    		PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
				pstmt.executeUpdate();
				pstmt.close();
			 } catch (Exception e) {
			      CustomLog.log("Errore durante la edit_plugin : query --> "+ query, e);
			      throw new CustomException(
			          "Errore database");
		    }	 
		}	
		
		public void insert_plugin(
				String name, 
				String configurationfile, 
				String type, 
				String note) throws  CustomException  {
		    Connection connection = db_get_connection();
		    String query = "" +
		    		" INSERT " +
		    		" into " +
		    		Db_table_names.T_PLUGIN +
		    		" (`name`, `type`, `configurationfile`, `note`)" +
		    		" VALUES " +
		    		" ('" +
		    		addslashes(name) +
		    		"', '" +
		    		type +
		    		"', '" +
		    		addslashes(configurationfile) +
		    		"', '" +
		    		addslashes(note) +
		    		"')";
		    try {
	    		PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
				pstmt.executeUpdate();
				pstmt.close();
			 } catch (Exception e) {
			      CustomLog.log("Errore durante la insert_plugin : query --> "+ query, e);
			      throw new CustomException(
			          "Errore database");
		    }	 
		}
		public Grouping get_grouping(int id_grouping) throws  CustomException  {
			return get_groupings(id_grouping).get(0);
		}
		
		public ArrayList<Grouping> get_groupings() throws  CustomException  {
			return get_groupings(0);
		}
			
		public ArrayList<Grouping> get_groupings(int id_grouping) throws  CustomException  {
		    Connection connection = db_get_connection();		
		    String WHERE_CLAUSE = "";
		    if(id_grouping!=0) 
		    	WHERE_CLAUSE = " WHERE `id` = " + id_grouping + " ";		
		    String query = "" +
		    		" SELECT " +
		    		" * " +
		    		" FROM " +
		    		Db_table_names.T_GROUPING + 
		    		WHERE_CLAUSE + 
		    		" ORDER BY " +
		    		" alias ";		
		    try {
	    		PreparedStatement pstmt = connection.prepareStatement(query);	    		
			    ResultSet rs = pstmt.executeQuery();
		    ArrayList<Grouping> results = new ArrayList<Grouping>();
		    while(rs.next()) {
		        int id = rs.getInt("id");
		        String name = rs.getString("name");
		        String alias = rs.getString("alias");
		        Grouping newGrouping =  new Grouping(id, name, alias); 
		        results.add(newGrouping);
		    }
		    pstmt.close();		
		    return results;
			 } catch (Exception e) {
			      CustomLog.log("Errore durante la get_groupings : query --> "+ query, e);
			      throw new CustomException(
			          "Errore database");
		    }	 
		}
		
		public void delete_grouping(int id_grouping) throws  CustomException  {
		     ArrayList<Resource> fieldsOfGrouping = get_fields_from_id_grouping (id_grouping);
			if (fieldsOfGrouping.size() > 0){
				throw new CustomException ("grouping non vuoto");
			}
		    Connection connection = db_get_connection();	
		    if(id_grouping!=0) {		
		    // cancello il gruppo
		        String query = "" +
		        		" DELETE " +
		        		" from " +
		        		Db_table_names.T_GROUPING +
		        		" where " +
		        		" `id` = " + id_grouping + " " +
		        		" LIMIT 1";
			    try {
		    		PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
					pstmt.executeUpdate();
			    	pstmt.close();
				 } catch (Exception e) {
				      CustomLog.log("Errore durante la delete_grouping : query --> "+ query, e);
				      throw new CustomException(
				          "Errore database");
			    }	
		    }
		}

		public ArrayList<String> getAllTablesList() throws  CustomException  {
		    Connection connection = db_get_connection();		
		    String query = "" +
		    		" SHOW TABLES " ;		
		    try {
	    		PreparedStatement pstmt = connection.prepareStatement(query);	    		
			    ResultSet rs = pstmt.executeQuery();
			    ArrayList<String> results = new ArrayList<String>();
		    while(rs.next()) {
		        String name = rs.getString(1);
		        if (!name.contains("__system_")) {
		        	 results.add(name);
				}
		    }
		    pstmt.close();		
		    return results;
			 } catch (Exception e) {
			      CustomLog.log("Errore durante la get_groupings : query --> "+ query, e);
			      throw new CustomException(
			          "Errore database");
		    }	 
			
		}

		public void insertAllResultSets() throws  CustomException  {
			ArrayList<String> tablesList = getAllTablesList();
			for (Iterator<String> iterator = tablesList.iterator(); iterator.hasNext();) {
				String string = (String) iterator.next();
				insertSimpleResultSet(string );
				
			}
		}

		public void insertSimpleResultSet(String tableName ) throws  CustomException  {
			String name = tableName;
			String alias = tableName;
			String statement = "SELECT * FROM " + tableName;
			insertResultSet (name, alias, statement );
		}		

		public void insertResultSet(String resultset_name, String resultset_alias, String resultset_statement ) throws  CustomException  {
	           /* Esegui query per prendere i campi dal resultset */
			 ArrayList<ResourceField> resourcesArray = get_fields_from_query(resultset_statement);
//		           resource_fields = get_fields_from_query(resultset_name);
		
		           /* Inserisci il resultset in resources e prendine l'id */
			 int resultset_id = insert_resource(resultset_name, resultset_alias);
		
			 Resultset resultset = new Resultset( resultset_id, resultset_name,
			  resultset_alias, resultset_statement);
		
	           /* Salva il resultset nella tabella resultset */
			 insert_resultset(resultset);

	           /* Inserisci nella tabella resource
	            * e field tutte le risorse con alias = name */
	           for (ResourceField resource_field : resourcesArray ) {
				 String resource_name = resource_field.get_name();
	/*
	 * 
	 * 
	 * DA RISOLVERE
	 * 
	 */
				 String resource_type = resource_field.getJavaSqlType();
				 // String resource_type = "DUMMY-TYPE";
				 String resource_def  = "DUMMY-DEF";
				 
//				 String resource_type = resource_field.get_type();
//				 String resource_def  = resource_field.get_def();
				 int resource_id   = insert_resource (resource_name, resource_name);
				 insert_field(resource_id, resultset_id, resource_type, resource_def);		 
				 //String resultset_name = request.getParameter("resultset_name");
	           }
			
//			String resultset_alias = request.getParameter("resultset_alias");
//			 dbUtils.insert_resultset(resultset_name, resultset_alias);

		}
		
		public void insert_field(int id, int resultset_id, String type, String def) throws  CustomException  {
		    Connection connection = db_get_connection();
		    String query = "" +
		    		" INSERT " +
		    		" into " +
		    		Db_table_names.T_FIELD +
		    		" " +
		        " (`id`, `default_header`, `id_resultset`, `type`, `defaultvalue` )" +
		        " VALUES " + 
		        " (" +
		        id +
		        ", " +
		        "1, " +
		        resultset_id +
		        ", '" +
		        addslashes(type) +
		        "', '" +
		        addslashes(def) +
		        "')";
			    try {
		    		PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
					pstmt.executeUpdate();
					pstmt.close();
				 } catch (Exception e) {
				      CustomLog.log("Errore durante la insert_field : query --> "+ query, e);
				      throw new CustomException(
				          "Errore database");
			    }	 			
		}
		
		public void modify_field(int id, int default_header, int search_grouping, int id_grouping) throws  CustomException  {
		    Connection connection = db_get_connection();
		    String query = "" +
		    		" UPDATE " +
		    		Db_table_names.T_FIELD +
		    		" SET " +
		    		" `default_header` = " +
		    		default_header +
		    		"," +
		    		" `search_grouping`=" +
		    		search_grouping +
		    		"," +
		    		" `id_grouping`=" +
		    		id_grouping + 
		    		" WHERE " +
		    		" id=" +
		    		id;
		    try {
	    		PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
				pstmt.executeUpdate();
				pstmt.close();
			 } catch (Exception e) {
			      CustomLog.log("Errore durante la edit_field : query --> "+ query, e);
			      throw new CustomException(
			          "Errore database");
		    }	 
		}		
		
		public void insert_resultset(Resultset resultset)  throws  CustomException {
		    Connection connection = db_get_connection();		
		    int id = resultset.get_id();
		    String statement = resultset.get_statement();		
		    String query = "" +
		    		" INSERT " +
		    		" into " +
		    		Db_table_names.T_RESULTSET +
		    		" " + 
		    		" (`id`, `statement`) " +
		    		" VALUES " +
		    		" ('" +
		    		id +
		    		"', '" +
		    		addslashes(statement) +
		    		"')";
		    try {
	    		PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
				pstmt.executeUpdate();
				pstmt.close();
			 } catch (Exception e) {
			      CustomLog.log("Errore durante la insert_grouping : query --> "+ query, e);
			      throw new CustomException(
			          "Errore database");
		    }	 			
		}
		
		public void insert_notify(Notify notify)  throws  CustomException {
		    Connection connection = db_get_connection();	
		    int resultset_id = notify.get_id_resultset();
		    String notify_name = notify.get_notify_name();
		    String address_statement = notify.get_address_statement();
		    String data_statement = notify.get_data_statement();
		    String xslt = notify.get_xslt();
		    String bmdid = notify.get_bmdid();
		
		    String query = "" +
		    		" INSERT " +
		    		" into " +
		    		Db_table_names.T_NOTIFY + 
		    		" " + 
		    		" (`id_resultset`, `name`, `address_statement`, `data_statement`, `xslt`, `link_id`) " +
		    		" VALUES " +
		    		"(" +
		    		resultset_id +
		    		", '" +
		    		notify_name +
		    		"', '" +
		    		address_statement +
		    		"', '" +
		    		data_statement +
		    		"', '" +
		    		xslt +
		    		"', '" +
		    		bmdid +
		    		"')";
		    try {
	    		PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
				pstmt.executeUpdate();
				pstmt.close();
			 } catch (Exception e) {
			      CustomLog.log("Errore durante la insert_notify : query --> "+ query, e);
			      throw new CustomException(
			          "Errore database");
		    }	 			
		}
		
		public int insert_resource(String name, String alias) throws  CustomException  {
		    Connection connection = db_get_connection();		
		    String query = "" +
		    		" INSERT " +
		    		" into " +
		    		Db_table_names.T_RESOURCE +
		    		" " + 
		    		" (`name`, `alias`) " +
		    		" VALUES " +
		    		" ('" +
		    		addslashes(name) +
		    		"', '" +
		    		addslashes(alias) +
		    		"')";	
		    try {
	    		PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
	    		int modifiedRowsNumber = pstmt.executeUpdate();
	    		if (modifiedRowsNumber == 1) {
		    		ResultSet rsgenKeys =  pstmt.getGeneratedKeys();
					rsgenKeys.next();
					int genId = rsgenKeys.getInt(1);
					pstmt.close();
					return genId;
				}
				pstmt.close();
				throw new CustomException("Errore inseriment nuova risorsa "  + query );
			 } catch (Exception e) {
			      CustomLog.log("Errore durante la insert_notify : query --> "+ query, e);
			      throw new CustomException(
			          "Errore database");
		    }	 			
		}

		
		public Resultset get_resultset(int id_resultset)  throws  CustomException {
			return get_resultsets(id_resultset).get(0);
		}

		public ArrayList<Resultset> get_resultsets()  throws  CustomException {
			return get_resultsets(0);
		}
		
		public ArrayList<Resultset> get_resultsets(int id_resultset)  throws  CustomException {
		    Connection connection = db_get_connection();		
		    String WHERE_CLAUSE = "";
		    if(id_resultset!=0) 
		    	WHERE_CLAUSE = " AND (rs.id = " + id_resultset + ") ";		
		    String query = "" +
		    		" SELECT " +
		    		" * " +
		    		" FROM " + 
		    		Db_table_names.T_RESULTSET +
		    		" rs " +
		    		" , " +
		    		Db_table_names.T_RESOURCE +
		    		" r " + 
		    		" WHERE " +
		    		" (r.id = rs.id) " +
		    		WHERE_CLAUSE ;	    
		    try {
	    		PreparedStatement pstmt = connection.prepareStatement(query);	    		
			    ResultSet rs = pstmt.executeQuery();
		    ArrayList<Resultset> results = new ArrayList<Resultset>();
		    while(rs.next()) {
		        int id = rs.getInt("id");
		        String name = rs.getString("name");
		        String alias = rs.getString("alias");
		        String statement = rs.getString("statement");
		        Resultset newResultset =  new Resultset(id, name, alias, statement); 
		        results.add(newResultset);
		    }
		    pstmt.close();		
		    return results;
			 } catch (Exception e) {
			      CustomLog.log("Errore durante la get_groups : query --> "+ query, e);
			      throw new CustomException(
			          "Errore database");
		    }	 
		}		
		
		public String get_resultset_name(int resultset_id) throws  CustomException  {
		    Connection connection = db_get_connection();	
		    String query = "" +
		    		" SELECT " +
		    		" r.name as `name` " +
		    		" FROM " +
		    		Db_table_names.T_RESULTSET + " rs JOIN " +
		    		Db_table_names.T_RESOURCE + " r " + 
		    		" ON (r.id = rs.id) " +
		    		" WHERE " +
		    		" rs.id=" + resultset_id;
		
		    try {
	    		PreparedStatement pstmt = connection.prepareStatement(query);	    		
			    ResultSet rs = pstmt.executeQuery();
		    if (rs.next()) {
		        String name = rs.getString("name");
		        pstmt.close();		
		        return name;
		    } else {
		    	 throw new CustomException(
		          "ResultSet Nontrovato");
		    }
		    
			 } catch (Exception e) {
			      CustomLog.log("Errore durante la get_resultset_name : query --> "+ query, e);
			      throw new CustomException(
			          "Errore database");
		    }	 		    
		}
		
		public Toolbar get_toolbar_from_ids( int resultset_id, int group_id) throws  CustomException  {		
		    Connection connection = db_get_connection();		
		    String query = "" +
		    		" SELECT " +
		    		" * " +
		    		" FROM " +
		    		Db_table_names.T_TOOLBAR +
		    		" WHERE " +
		    		" `id_resultset`='" + resultset_id +
		    		" ' AND " +
		    		" `id_group`='" + group_id + 
		    		"'";			    
		    try {
	    		PreparedStatement pstmt = connection.prepareStatement(query);	    		
			    ResultSet rs = pstmt.executeQuery();
			    if (rs.next()) {
			        String tools = rs.getString("tools");
			    	Toolbar toolbar = new Toolbar(resultset_id, group_id, tools);
			    	pstmt.close();		
			    	return toolbar;
			    } else {
			    	Toolbar toolbar = new Toolbar(resultset_id, group_id, "");
			    	pstmt.close();		
			    	return toolbar;
			    }
			 } catch (Exception e) {
			      CustomLog.log("Errore durante la get_resultset_name : query --> "+ query, e);
			      throw new CustomException(
			          "Errore database");
		    }	 
 		}
		
		public ArrayList<Notify> get_notify()  throws  CustomException {
		    Connection connection = db_get_connection();		
		    String query = "" +
		    		" SELECT " +
		    		" * " +
		    		" FROM " +
		    		Db_table_names.T_NOTIFY +
		    		" WHERE 1";		
		    try {
	    		PreparedStatement pstmt = connection.prepareStatement(query);	    		
			    ResultSet rs = pstmt.executeQuery();
			    ArrayList<Notify> results = new ArrayList<Notify>();
			    while(rs.next()) {
			        int id_resultset = rs.getInt("id_resultset");			        
			        int id_notify = rs.getInt("id");			        
			        String notify_name = rs.getString("name");			        
			        String address_statement = rs.getString("address_statement");
			        String data_statement = rs.getString("data_statement");
			        String xslt = rs.getString("xslt");
			        String bmdid = rs.getString("link_id");			        
			        Notify newNotify =  new Notify(id_resultset, notify_name, address_statement, data_statement, xslt, id_notify, bmdid);
			        results.add(newNotify);
			    }
			    pstmt.close();		
			    return results;
			 } catch (Exception e) {
			      CustomLog.log("Errore durante la get_notify : query --> "+ query, e);
			      throw new CustomException(
			          "Errore database");
		    }	 
		}

		public void delete_notify(int id_notify) throws  CustomException  {
		    Connection connection = db_get_connection();		
		    if(id_notify!=0) {		
		        String query = "" +
		        		" DELETE " +
		        		" from " +
		        		Db_table_names.T_NOTIFY +
		        		" where " +
		        		" `id` = " +
		        		id_notify +
		        		" LIMIT 1";
			    try {
		    		PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
					pstmt.executeUpdate();					
					pstmt.close();
				 } catch (Exception e) {
				      CustomLog.log("Errore durante la delete_notify : query --> "+ query, e);
				      throw new CustomException(
				          "Errore database");
			    }
		    }
		}
		
		public ArrayList<Resource> get_fields_from_resultsetid(int resultset_id) throws  CustomException  {
		    Connection connection = db_get_connection();		
		    String query = "" +
		    		" SELECT " +
		    		" * " +
		    		" FROM " +
		    		Db_table_names.T_RESOURCE +
		    		" r JOIN " +
		    		Db_table_names.T_FIELD +
		    		" f USING (`id`) " + 
		    		" WHERE " +
		    		" f.`id_resultset` = " + resultset_id;
		    try {
	    		PreparedStatement pstmt = connection.prepareStatement(query);	    		
			    ResultSet rs = pstmt.executeQuery();
		    ArrayList<Resource> results = new ArrayList<Resource>();
		    while(rs.next()) {
		        int id = rs.getInt("id");
		        String name = rs.getString("name");
		        String alias = rs.getString("alias");
		        String type = rs.getString("type");
		        String  defaultvalue = rs.getString("defaultvalue");
		        int default_header = rs.getInt("default_header");		        
		        int search_grouping = rs.getInt("search_grouping");		        
		        Resource newResource =  new Resource
		        (id, name, alias,type, defaultvalue ,default_header, search_grouping, search_grouping); 
		        results.add(newResource);
		    }
		    pstmt.close();		
		    return results;
			 } catch (Exception e) {
			      CustomLog.log("Errore durante la get_usersOfAGroup : query --> "+ query, e);
			      throw new CustomException(
			          "Errore database");
		    }	 
 		}
		
		
		
		public ArrayList<ResourceWithGroupPermissions> get_fields_with_permissions_from_resultsetid(int resultset_id, int group_id) throws  CustomException  {
		    Connection connection = db_get_connection();		
		    String query = "" +
		    		" SELECT " +
		    		" * " +
		    		" FROM " +
		    		Db_table_names.T_RESOURCE +
		    		" r JOIN " +
		    		Db_table_names.T_FIELD +
		    		" f USING (`id`) " + 
		    		" WHERE " +
		    		" f.`id_resultset` = " +
		    		resultset_id;
		    try {
	    		PreparedStatement pstmt = connection.prepareStatement(query);	    		
			    ResultSet rs = pstmt.executeQuery();
		    ArrayList<ResourceWithGroupPermissions> results = new ArrayList<ResourceWithGroupPermissions>();
		    while(rs.next()) {
		        int id = rs.getInt("id");
		        String name = rs.getString("name");
		        String alias = rs.getString("alias");
		        String type = rs.getString("type");
		        String  defaultvalue = rs.getString("defaultvalue");
		        int default_header = rs.getInt("default_header");		        
		        int search_grouping = rs.getInt("search_grouping");		        
		        int id_grouping = rs.getInt("id_grouping");		        
		        ResourceGroupPermissions resourceGroupPermissions = getResourceGroupPermissions(id, group_id);		        
		        ResourceWithGroupPermissions newResourceWithGroupPermissions =  new ResourceWithGroupPermissions
		        (id, name, alias,type, defaultvalue ,default_header, search_grouping, search_grouping,  resourceGroupPermissions); 
		        results.add(newResourceWithGroupPermissions);
		    }
		    pstmt.close();		
		    return results;
			 } catch (Exception e) {
			      CustomLog.log("Errore durante la get_usersOfAGroup : query --> "+ query, e);
			      throw new CustomException(
			          "Errore database");
		    }	 
 		}

		public ArrayList<Resource> get_fields_from_id_grouping(int id_grouping) throws  CustomException  {
		    Connection connection = db_get_connection();		
		    String query = "" +
		    		" SELECT " +
		    		" *" +
		    		" FROM " +
		    		Db_table_names.T_RESOURCE +
		    		" r JOIN " +
		    		Db_table_names.T_FIELD +
		    		" f USING (`id`) " + 
		    		" WHERE " +
		    		" f.`id_grouping` = " + id_grouping;
		    try {
	    		PreparedStatement pstmt = connection.prepareStatement(query);	    		
			    ResultSet rs = pstmt.executeQuery();
			    ArrayList<Resource> results = new ArrayList<Resource>();
			    while(rs.next()) {
			        int id = rs.getInt("id");
			        String name = rs.getString("name");
			        String alias = rs.getString("alias");
			        String type = rs.getString("type");
			        String  def = rs.getString("defaultvalue");
			        int header = rs.getInt("default_header");		        
			        int search = rs.getInt("search_grouping");		        
			        //int id_grouping = rs.getInt("id_grouping");		        
			        Resource newResource =  new Resource(id, name, alias, type, def, header, search, id_grouping); 
			        results.add(newResource);
			    }
		    pstmt.close();		
		    return results;
			 } catch (Exception e) {
			      CustomLog.log("Errore durante la get_usersOfAGroup : query --> "+ query, e);
			      throw new CustomException(
			          "Errore database");
		    }	 
		}				
		
		public ArrayList<ResourceField> get_fields_from_query(String query) throws  CustomException  {
		    Connection connection = db_get_connection();
		    try {
			    ArrayList<ResourceField> results = new ArrayList<ResourceField>();
	    		PreparedStatement pstmt = connection.prepareStatement(query  +  " LIMIT 0,1");	    		
			    ResultSet rs = pstmt.executeQuery();
		        ResultSetMetaData metaData = rs.getMetaData();
		        int rowCount = metaData.getColumnCount();
//		            System.out.println("Table Name : " + metaData.getTableName(2));
//		            System.out.println("Field  \tsize\tDataType");
	            for (int i = 0; i < rowCount; i++) {
	                String name = metaData.getColumnName(i + 1) ;
	                String type = metaData.getColumnTypeName(i + 1) ;	                
//	            	System.out.print(metaData.getColumnName(i + 1) + "  \t");
//	                System.out.print(metaData.getColumnDisplaySize(i + 1) + "\t");
//	                System.out.println(metaData.getColumnTypeName(i + 1));
//			        Resource newResource =  new Resource(null, meta.name, null, meta.type, meta.def, meta.header, meta.search, meta.grouping); 
	                /*
	                 * 
	                 * DA CORREGGERE!!!!!!
	                 * 
	                 */
	                
	                ResourceField newResource =  new ResourceField (0, name, name, type ); 
			        results.add(newResource);
	            }
		    pstmt.close();		
		    return results;
			 } catch (Exception e) {
			      CustomLog.log("Errore durante la get_groupings : query --> "+ query, e);
			      throw new CustomException(
			          "Errore database");
		    }	 	    
		}	
		
//		public void get_table_type(String resultset_name) {
//		    Connection connection = db_get_connection();		
//		    String query = "SHOW CREATE TABLE `" + resultset_name +  "`";
//		    result = mysql_query(query)
//		        or die("Query <pre><b>query</b></pre> failed: "  +  mysql_error());
//		
//		    //    type = 'Table';
//		    // interessa solo il primo campo
//		    row = mysql_fetch_field(result);
//		    if (row) {
//		        type = row.name;
//		    }
//		
//		    return type;
//		
//		}
		
		//public void get_fields_from_query(table_name) {
		//    Connection connection = db_get_connection();
		//
		//    /* Definizione query . uguale a statement */
		//
		//    /* Esegui la query */
		//    String query = "SHOW COLUMNS FROM `" +  table_name  + "`";
		//    result = mysql_query(query)
		//        or die("Query <pre><b>query</b></pre> failed: "  +  mysql_error());
		//
		//    results = array();
		//    i = 0;
		//    while (row = mysql_fetch_array(result, MYSQL_ASSOC)) {
		//
		//        /* Inserisci il nome del campo nei risultati */
		//        results[i++] =
		//            new Resource(null, row['Field'], null, row['Type'], row['Default'], null, null, null);
		//    }
		//
		//    //mysql_free_result(result);
		//    pstmt.close();
		//
		//    /* Ritorna i risultati */
		//    return results;
		//}
		
		
//		public void print_result_metadata(result) {
//		    i = 0;
//		    while (i < mysql_num_fields(result)) {
//		        echo "Information for column i:<br />\n";
//		        meta = mysql_fetch_field(result, i);
//		        if (!meta) {
//		            echo "No information available<br />\n";
//		        }
//		        echo "<pre>
//		blob:         meta.blob
//		max_length:   meta.max_length
//		multiple_key: meta.multiple_key
//		name:         meta.name
//		not_null:     meta.not_null
//		numeric:      meta.numeric
//		primary_key:  meta.primary_key
//		table:        meta.table
//		type:         meta.type
//		default:      meta.def
//		unique_key:   meta.unique_key
//		unsigned:     meta.unsigned
//		zerofill:     meta.zerofill
//		</pre>";
//		        i++;
//		    }
//		}

		
		public void modifyResourceAlias(int id, String newAlias) throws  CustomException  {
		    Connection connection = db_get_connection();
		    String query = "" +
		    		" UPDATE " +
		    		Db_table_names.T_RESOURCE +
		    		" SET " +
		    		" `alias` = '" +
		    		newAlias +
		    		"' " +
		    		" WHERE " +
		    		" id=" +
		    		id;
		    try {
	    		PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
				pstmt.executeUpdate();
				pstmt.close();
			 } catch (Exception e) {
			      CustomLog.log("Errore durante la updateResourceAlias : query --> "+ query, e);
			      throw new CustomException(
			          "Errore database");
		    }	 
		}		


		public ArrayList<ResourceMinimal> get_Allresources()  throws  CustomException {
		    Connection connection = db_get_connection();
		    String query = "" +
		    		" SELECT" +
		    		" * " +
		    		" FROM " +
		    		Db_table_names.T_RESOURCE  ;		
		    try {
	    		PreparedStatement pstmt = connection.prepareStatement(query);	    		
			    ResultSet rs = pstmt.executeQuery();
			    ArrayList<ResourceMinimal> results = new ArrayList<ResourceMinimal>();
			    while (rs.next()) {
			        int id = rs.getInt("id");
			        String name = rs.getString("name");
			        String alias = rs.getString("alias");
			        ResourceMinimal newResource =  new ResourceMinimal(id, name, alias); 
			        results.add(newResource);			        
			    }	
			    pstmt.close(); 
			    return results;
			 } catch (Exception e) {
			      CustomLog.log("Errore durante la get_resource_from_id : query --> "+ query, e);
			      throw new CustomException(
			          "Errore database");
		    }	 
		}
		
		
		
		public ResourceMinimal get_resource_from_id(int resource_id)  throws  CustomException {
		    Connection connection = db_get_connection();
		    String WHERE_CLAUSE = "";
		    if(resource_id!=0) 
		    	WHERE_CLAUSE = " id = " + resource_id + " ";		
		    String query = "" +
		    		" SELECT" +
		    		" * " +
		    		" FROM " +
		    		Db_table_names.T_RESOURCE +
		    		WHERE_CLAUSE ;		
		    try {
	    		PreparedStatement pstmt = connection.prepareStatement(query);	    		
			    ResultSet rs = pstmt.executeQuery();
		    if (rs.next()) {
		        int id = rs.getInt("id");
		        String name = rs.getString("name");
		        String alias = rs.getString("alias");
		        int type = rs.getInt("type");
		        ResourceMinimal newResource =  new ResourceMinimal(id, name, alias); 
			    pstmt.close();		
		        return newResource;
		    }
		    pstmt.close();		
		    return null;
			 } catch (Exception e) {
			      CustomLog.log("Errore durante la get_resource_from_id : query --> "+ query, e);
			      throw new CustomException(
			          "Errore database");
		    }	 
		}
		

		public void delete_only_resultset_by_id(int resultset_id) throws  CustomException  {
		    Connection connection = db_get_connection();		
		    String query = "" +
		    		" DELETE " +
		    		" FROM " +
		    		Db_table_names.T_RESULTSET +
		    		" WHERE " +
		    		" `id` = " + resultset_id  +
		    		" LIMIT 1";		
		    try {
	    		PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
				pstmt.executeUpdate();
		    	pstmt.close();
			 } catch (Exception e) {
			      CustomLog.log("Errore durante la delete_grouping : query --> "+ query, e);
			      throw new CustomException(
			          "Errore database");
		    }	 
		}
		
		public void delete_notify_by_id(int id)  throws  CustomException {
		    Connection connection = db_get_connection();		
		    String query = "" +
		    		" DELETE " +
		    		" FROM " + Db_table_names.T_NOTIFY +
		    		" WHERE" +
		    		" `id` =" + id  +
		    		" LIMIT 1";		
		    try {
	    		PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
				pstmt.executeUpdate();
		    	pstmt.close();
			 } catch (Exception e) {
			      CustomLog.log("Errore durante la delete_grouping : query --> "+ query, e);
			      throw new CustomException(
			          "Errore database");
		    }	 
		}	
		
		public void delete_resource_by_id(int resource_id) throws  CustomException  {
		    Connection connection = db_get_connection();		
		    String query = "" +
		    		" DELETE " +
		    		" FROM " +
		    		Db_table_names.T_RESOURCE + 
		    		" WHERE" +
		    		" `id` =" + resource_id +
		    		" LIMIT 1";		
		    try {
	    		PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
				pstmt.executeUpdate();
		    	pstmt.close();
			 } catch (Exception e) {
			      CustomLog.log("Errore durante la delete_grouping : query --> "+ query, e);
			      throw new CustomException(
			          "Errore database");
		    }	 
		}
		
		public void delete_fields_by_resultset_id(int resultset_id)  throws  CustomException {
		    Connection connection = db_get_connection();		
		    String query = "" +
		    		" DELETE" +
		    		" FROM " +
		    		Db_table_names.T_FIELD +
		    		" WHERE " +
		    		" `id_resultset` = " + resultset_id;		
		    try {
	    		PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
				pstmt.executeUpdate();
		    	pstmt.close();
			 } catch (Exception e) {
			      CustomLog.log("Errore durante la delete_grouping : query --> "+ query, e);
			      throw new CustomException(
			          "Errore database");
		    }	 
		}

		public void delete_management_permission_by_resource_id(int resource_id) throws  CustomException  {
			delete_management_permission_by_id_resource_id_and_goup_id(0, resource_id, 0);
		}		
		
		public void delete_management_permission_by_id(int id) throws  CustomException  {
			delete_management_permission_by_id_resource_id_and_goup_id(id,0,0);
		}
		
		public void delete_management_permission_by_id_resource_id_and_goup_id(int id, int resource_id, int group_id) throws  CustomException  {
		    Connection connection = db_get_connection();		
		    String WHERE_CLAUSEID = " ";
		    if(id!=0) {
		    	WHERE_CLAUSEID = " AND  (id = " + id + ") ";
		    }	
		    String WHERE_CLAUSERESID = " ";
		    if(resource_id!=0) {
		    	WHERE_CLAUSERESID = " AND  (id_resource = " + resource_id + ") ";
		    }	
		    String WHERE_CLAUSEIDGROUP = " ";
		    if(group_id!=0) {
		    	WHERE_CLAUSEIDGROUP = " AND  (id_group = " + group_id + ") ";
		    }	
		    String query = "" +
		    		" DELETE " +
		    		" FROM " +
		    		Db_table_names.T_MANAGEMENT +
		    		" WHERE 1 " +
		    		WHERE_CLAUSEID +
		    		WHERE_CLAUSERESID +
		    		WHERE_CLAUSEIDGROUP	;		
		    try {
	    		PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
				pstmt.executeUpdate();
		    	pstmt.close();
			 } catch (Exception e) {
			      CustomLog.log("Errore durante la delete_grouping : query --> "+ query, e);
			      throw new CustomException(
			          "Errore database");
		    }	 
		}
		
		public void delete_resources_of_resultset_by_resultset_id(int resultset_id) throws  CustomException  {
		    ArrayList<Resource> resource_list = get_fields_from_resultsetid(resultset_id);		
		    for ( Resource resource : resource_list ) {
		        delete_resource_by_id(resource.get_id());
		    }		
		            /* Il resultset _deve_ essere eliminato per ultimo */
		    delete_resource_by_id(resultset_id);
		}
		
		public void delete_management_permissions_by_resultset_id(int resultset_id) throws  CustomException  {
			ArrayList<Resource> resource_list = get_fields_from_resultsetid(resultset_id);
		    for (Resource resource : resource_list) {
		        delete_management_permission_by_resource_id(resource.get_id());
		    }
		}
		
		public void delete_toolbar_by_resultset_id(int resultset_id)  throws  CustomException {
		    Connection connection = db_get_connection();
		    String query = "" +
		    		" DELETE " +
		    		" FROM " +
		    		Db_table_names.T_TOOLBAR +
		    		" WHERE " +
		    		" `id_resultset` =" + 
		    		resultset_id;
				    try {
			    		PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
						pstmt.executeUpdate();
				    	pstmt.close();
					 } catch (Exception e) {
					      CustomLog.log("Errore durante la delete_grouping : query --> "+ query, e);
					      throw new CustomException( "Errore database");
				    }	 
		}
		
		public void delete_All_resultset() throws  CustomException  {
			ArrayList <Resultset> resList = get_resultsets();
			for (Resultset resultset : resList) {
				delete_resultset_complete_by_id (resultset.get_id());
			}
		}		

		public void delete_resultset_complete_by_id(int resultset_id) throws  CustomException  {
		    delete_management_permissions_by_resultset_id (resultset_id);
		    delete_resources_of_resultset_by_resultset_id(resultset_id); //prima dei fields
		    delete_fields_by_resultset_id(resultset_id);
		    //remove_toolbar_by_resultset_id(resultset_id);
		    delete_only_resultset_by_id(resultset_id);
		}

		
		public ResourceGroupPermissions getResourceGroupPermissions(int resourceId, int groupId)  throws  CustomException {
		    Connection connection = db_get_connection();		
		    String query = "" +
		    		" SELECT " +
		    		" * " +
		    		" FROM " + 
		    		Db_table_names.T_MANAGEMENT +
		    		" WHERE " +
		    		" (id_resource = " +
		    		resourceId +
		    		") " +
		    		" AND " +
		    		"(id_group =" +
		    		groupId +
		    		")" ;	    
		    try {
	    		PreparedStatement pstmt = connection.prepareStatement(query);	    		
			    ResultSet rs = pstmt.executeQuery();
		    if(rs.next()) {		        
		        int r =  rs.getInt("readperm");
		        int d =  rs.getInt("deleteperm");
		        int m =  rs.getInt("modifyperm");
		        int i =  rs.getInt("insertperm");
		        ResourceGroupPermissions newResourceGroupPermissions =  new ResourceGroupPermissions (r,d,m,i, groupId ); 
			    pstmt.close();		
			    return newResourceGroupPermissions;
		    }
		    return null;
			 } catch (Exception e) {
			      CustomLog.log("Errore durante la get_groups : query --> "+ query, e);
			      throw new CustomException(
			          "Errore database");
		    }	 
		}		
	
		
		
		private String addslashes(String s1){
			/*
			 * 
			 * 
			 * 
			 * 
			 * 
			 * 
			 * 
			 * 
			 * 
			 * 
			 * 
			 * 
			 * 
			 * 
			 * 
			 * 
			 * 
			 * 
			 * 
			 * 
			 * NON IMPLEMENTATA !!!!!!
			 * 
			 * 
			 * 
			 * 
			 * 
			 * 
			 * 
			 * 
			 * 
			 * 
			 * 
			 * 
			 * 
			 * 
			 * 
			 */
			String s2 = s1 ;
			return s2;
		}

		
		
}