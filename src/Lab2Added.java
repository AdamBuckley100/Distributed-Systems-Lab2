// This is the mySQL JDBC GUI connection.

import java.util.Properties;
import java.awt.*;
// not com.mysql.jdbc.Connection
import java.sql.*;
import javax.swing.*;
import java.awt.event.*;

public class Lab2Added {

	ResultSet rs = null;
	Statement s = null;
	
	/** The name of the MySQL account to use (or empty for anonymous) */
	private final String userName = "root";

	/** The password for the MySQL account (or empty for anonymous) */
	private final String password = "";

	/** The name of the computer running MySQL */
	private final String serverName = "localhost";

	/** The port of the MySQL server (default is 3306) */
	private final int portNumber = 3306;

	/** The name of the database we are testing with (this default is installed with MySQL) */
	private final String dbName = "test2_create_db";
	
	/** The name of the table we are testing with */
	private final String tableName = "JDBC_TEST";
	
	/**
	 * Get a new database connection
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Connection getConnection() throws SQLException {
		Connection conn = null;
		Properties connectionProps = new Properties();
		connectionProps.put("user", this.userName);
		connectionProps.put("password", this.password);

		conn = DriverManager.getConnection("jdbc:mysql://"
				+ this.serverName + ":" + this.portNumber + "/" + this.dbName,
				connectionProps);

		return conn;
	}
	
	/**
	 * Run a SQL command which does not return a recordset:
	 * CREATE/INSERT/UPDATE/DELETE/DROP/etc.
	 * 
	 * @throws SQLException If something goes wrong
	 */
	public boolean executeUpdate(Connection conn, String command) throws SQLException {
	    Statement stmt = null;
	    try {
	        stmt = conn.createStatement();
	        stmt.executeUpdate(command); // This will throw a SQLException if it fails
	        return true;
	    } finally {

	    	// This will run whether we throw an exception or not
	        if (stmt != null) { stmt.close(); }
	    }
	}
	
	/**
	 * Connect to MySQL and do some stuff.
	 */
	public void run() {

		// Connect to MySQL
		Connection conn = null;
		try {
			conn = this.getConnection();
			System.out.println("Connected to database");
		} catch (SQLException e) {
			System.out.println("ERROR: Could not connect to the database");
			e.printStackTrace();
			return;
		}

		// Create a table
		try {
		    String createString =
			        "CREATE TABLE " + this.tableName + " ( " +
			        "ID INTEGER NOT NULL, " +
			        "NAME varchar(40) NOT NULL, " +
			        "STREET varchar(40) NOT NULL, " +
			        "CITY varchar(20) NOT NULL, " +
			        "STATE char(2) NOT NULL, " +
			        "ZIP char(5), " +
			        "PRIMARY KEY (ID))";
			this.executeUpdate(conn, createString);
			System.out.println("Created a table");
	    } catch (SQLException e) {
			System.out.println("ERROR: Could not create the table");
			e.printStackTrace();
			return;
		}
		
		// Drop the table
		try {
		    String dropString = "DROP TABLE " + this.tableName;
			this.executeUpdate(conn, dropString);
			System.out.println("Dropped the table");
	    } catch (SQLException e) {
			System.out.println("ERROR: Could not drop the table");
			e.printStackTrace();
			return;
		}
	}

	private static int listOutDatabaseContents(ResultSet rs) throws SQLException
	{
		int count = 0;

		while (rs.next())
		{
			String idVal = rs.getString("id");

			if (idVal.length() == 0)
			{
				break;
			}

			String firstnameVal = rs.getString("firstname");
			String lastnameVal = rs.getString("lastname");
			String emailVal = rs.getString("email");
			System.out.println("id = " + idVal + ", firstname = " + firstnameVal + ", lastname = " + lastnameVal + ", email =" + emailVal);
			++count;
		}

		return count;
	}

	public static void main(String args[]) {
	
		//Lab2Added app = new Lab2Added();
		//app.run();

		// Connect to MySQL
		Connection conn = null;
		try {
			
			Class.forName("com.mysql.jdbc.Driver").newInstance();

			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test2_create_db", "root", "");
			
			Statement s = conn.createStatement();
			ResultSet rs = s.getResultSet ();
			
			//int count = listOutDatabaseContents(rs);
			//System.out.println (count + " rows were retrieved");

			if (!conn.isClosed())
			{
				System.out.println("Successfully connected to MySQL server...");
			}

			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test2_create_db", "root", "");
		} 

		catch(Exception e)
		{
			System.err.println("Exception: " + e.getMessage());

		}
	}
}