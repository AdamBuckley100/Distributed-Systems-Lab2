// This is the mySQL JDBC GUI connection.

import java.sql.Connection;   // not com.mysql.jdbc.Connection
import java.sql.DriverManager;
import java.sql.SQLException;

import java.awt.*;
import java.sql.*;
import javax.swing.*;
import java.awt.event.*;

public class Lab2 {

	ResultSet rs = null;
	Statement s = null;
	
	private static void preparedStatmentInsert(Connection con) throws SQLException
	{
		PreparedStatement s;
		
		s = con.prepareStatement ("INSERT INTO web_members4 (id, firstname, lastname, email");
		//s = con.prepareStatement ("INSERT INTO web_members4 (id, firstname, lastname, email VALUES(?,?)");
		
		s.setString (1, "idVal");
		s.setString (2, "firstNameVal");
		s.setString (3, "lastNameVal");
		s.setString (4, "emailVal");
		
		int count = s.executeUpdate ();
		
		s.close ();
		
		System.out.println (count +" rows have been inserted");
	}

	private static void InsertIntoATable(Statement s) throws SQLException
	{	
		int count;

		count = s.executeUpdate (
				"INSERT INTO web_members4 (id, firstname, lastname, email)"
						+ " VALUES"
						+ "('1', 'reptile', 'hi', 'h'),"
						+ "('2', 'j', 'l', 'm'),"
						+ "('3', 'r', 'k', 'b'),"
						+ "('4', 'p', 'i', 'z')");

		s.close ();

		System.out.println (count +" rows have been inserted");
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

	private static void createATable(Statement s) throws SQLException
	{	
		s.executeUpdate ("DROP TABLE IF EXISTS web_members4");

		s.executeUpdate ("CREATE TABLE web_members4 ("
				+ "id CHAR(4),"
				+ "firstname CHAR(10),"
				+ "lastname CHAR(10), email CHAR(20))");
	}

	public static void main(String args[]) {

		Connection con = null;

		try 
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();

			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test2_create_db", "root", "");

			if (!con.isClosed())
			{
				System.out.println("Successfully connected to MySQL server...");
			}

			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test2_create_db", "root", "");

			Statement s = con.createStatement();

			s.executeQuery("SELECT id, firstname, lastname, email " + "FROM web_members3");

			ResultSet rs = s.getResultSet ();

			int count = listOutDatabaseContents(rs);
			//createATable(s);
			//InsertIntoATable(s);
			preparedStatmentInsert(con);

			rs.close ();
			s.close ();
			System.out.println (count + " rows were retrieved");
		} 

		catch(Exception e)
		{
			System.err.println("Exception: " + e.getMessage());

		}
		finally
		{
			try 
			{
				if (con != null)
				{
					con.close();
				}
			} 
			catch(SQLException e)
			{
			}
		}
	}
}