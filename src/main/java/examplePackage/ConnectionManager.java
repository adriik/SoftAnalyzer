package examplePackage;

import java.sql.*;
import java.util.*;

/**
 * Menadżer zarządzania połączeniem z bazą danych
 * @author Adrian Plichta
 *
 */
public class ConnectionManager {

	static Connection con;
	static String url;

	/**
	 * Zwraca połączenie z bazą danych
	 * @return Connection połączenie z bazą danych
	 */
	public static Connection getConnection() {

		try {
			//String url = "jdbc:odbc:" + "DataSource";
			// assuming "DataSource" is your DataSource name
			
			String hostName = "softanalyzerdb.database.windows.net";
	        String dbName = "softanalyzerdb";
	        String user = "admindb";
	        String password = "stasiak12qw!";
	        String url = "jdbc:sqlserver://softanalyzerdb.database.windows.net:1433;database=SoftAnalyzerDB;user=admindb@softanalyzerdb;password=stasiak12qw!;encrypt=false;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";

	        
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

			try {
				con = DriverManager.getConnection(url);

				// assuming your SQL Server's username is "username"
				// and password is "password"

			}

			catch (SQLException ex) {
				ex.printStackTrace();
			}
		}

		catch (ClassNotFoundException e) {
			System.out.println(e);
		}

		return con;
	}
}
