package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {
	Connection connection;
	String mysql_driver = "com.mysql.jdbc.Driver";
	String mysql_host;
	String mysql_user;
	String mysql_database;
	String mysql_password;
	
	public void closeConnection (String mysql_host, String mysql_database , String mysql_user, String mysql_password) throws SQLException {
		connection.close();
	}	

	public void createConnection (String mysql_host, String mysql_database , String mysql_user, String mysql_password) throws CustomException {
		 this.mysql_host = mysql_host;
		 this.mysql_user = mysql_user;
		 this.mysql_database = mysql_database;
		 this.mysql_password = mysql_password;
		// TODO Auto-generated constructor stub
		 connection = do_open_db_get_connection();
	}

	public Connection getConnection() throws CustomException {
		try {
			if  ( (connection != null)  && (!connection.isClosed() )  ){
					return connection;
				} else {
					for (int i = 0; i < 3; i++) {
						Connection newConnection = do_open_db_get_connection();
						if ( (newConnection != null) && (!connection.isClosed() ) ){
							connection =  newConnection;
							return connection;
						} 
					}
				}
				throw new CustomException("errore impossibile aprire connessione al DB");
		} catch (SQLException e) {
			throw new CustomException ("errore impossibile aprire connessione al DB"); // TODO: handle exception
		}
	}		


	private Connection do_open_db_get_connection(	) throws CustomException {
    	String dbConnString = "jdbc:mysql://" + mysql_host + ":3306/" + mysql_database;
		Connection newConnection = null;
	    try {
			 if  ( (connection != null)  && (!connection.isClosed() )  ){
				 connection.close();
			 }
	    	Class.forName(mysql_driver);
	    	newConnection = (Connection) DriverManager.getConnection(dbConnString , mysql_user, mysql_password);
	    	return newConnection;
	    } catch (Exception e) {
	      CustomLog.log("Errore durante la creazione della connesione a database", e);
	      throw new CustomException( "Errore durante la creazione della connessione a database: dbConnString" + dbConnString + " user: "+ mysql_user);
	    }
	}

}
